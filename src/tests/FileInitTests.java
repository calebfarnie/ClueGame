package tests;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C14A-2 Clue Board 1
 * 12 October 2020
 */

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTests {
	// Constants used to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 29;
	public static final int NUM_COLUMNS = 29;

	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few planets
		// from the hash, including the first and last in the file and a few others
		assertEquals("Coruscant", board.getRoom('C').getName());
		assertEquals("Geonosis", board.getRoom('G').getName());
		assertEquals("Hoth", board.getRoom('H').getName());
		assertEquals("Dagobah", board.getRoom('D').getName());
		assertEquals("Endor", board.getRoom('E').getName());
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumRows());
	}

	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell cell = board.getCell(3, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(6, 13);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(9, 21);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(22, 18);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// Test that space are not doors
		cell = board.getCell(15, 24);
		assertFalse(cell.isDoorway());
	}

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumRows(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(19, numDoors);
	}

	// Test a few planet cells to ensure the planet initial is correct.
	@Test
	public void testRooms() {
		// just test a standard planet location
		BoardCell cell = board.getCell(14, 6);
		Room room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Kashyyyk");
		assertFalse(cell.isLabel());
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isDoorway());

		// this is a label cell to test
		cell = board.getCell(17, 25);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Yavin 4");
		assertTrue(cell.isLabel());
		assertTrue(room.getLabelCell() == cell);

		// this is a planet center cell to test
		cell = board.getCell(2, 13);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Coruscant");
		assertTrue(cell.isRoomCenter());
		assertTrue(room.getCenterCell() == cell);

		// this is a secret passage (portal) test
		cell = board.getCell(0, 24);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Tatooine");
		assertTrue(cell.getSecretPassage() == 'G');

		// test a free space
		cell = board.getCell(7, 2);
		room = board.getRoom(cell);
		// Note for our purposes, walkways and closets are rooms
		assertTrue(room != null);
		assertEquals(room.getName(), "FreeSpace");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());

		// test an unused space
		cell = board.getCell(14, 14);
		room = board.getRoom(cell);
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused");
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());

	}

}
