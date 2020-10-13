package tests;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C14A-2 Clue Board 1
 * 12 October 2020
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesPlanets()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		Set<BoardCell> testList = board.getAdjList(2, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(4, 6)));
		assertTrue(testList.contains(board.getCell(20, 19)));
		
		// now test the ballroom (note not marked since multiple test here)
		testList = board.getAdjList(20, 11);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(16, 9)));
		
		// one more room, the kitchen
		testList = board.getAdjList(20, 19);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 18)));
		assertTrue(testList.contains(board.getCell(2, 2)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyPortal()
	{
		Set<BoardCell> testList = board.getAdjList(16, 7);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 7)));
		assertTrue(testList.contains(board.getCell(17, 7)));
		assertTrue(testList.contains(board.getCell(16, 8)));
		assertTrue(testList.contains(board.getCell(14 ,3)));

		testList = board.getAdjList(6, 13);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(6, 12)));
		assertTrue(testList.contains(board.getCell(6, 14)));
		assertTrue(testList.contains(board.getCell(7, 13)));
		assertTrue(testList.contains(board.getCell(2, 13)));

		testList = board.getAdjList(22, 18);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(22, 19)));
		assertTrue(testList.contains(board.getCell(21, 18)));
		assertTrue(testList.contains(board.getCell(25, 15)));
		
		testList = board.getAdjList(14, 27);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(14, 26)));
		assertTrue(testList.contains(board.getCell(10, 25)));
		}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencySpaces()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(1, 7);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(1, 6)));
		assertTrue(testList.contains(board.getCell(1, 8)));
		assertTrue(testList.contains(board.getCell(2, 7)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(3, 19);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 19)));
		assertTrue(testList.contains(board.getCell(4, 19)));
		assertTrue(testList.contains(board.getCell(3, 18)));
		assertTrue(testList.contains(board.getCell(3, 20)));

		// Test adjacent to walkway and  closet
		testList = board.getAdjList(25, 10);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(24, 10)));
		assertTrue(testList.contains(board.getCell(25, 11)));

		// Test next to closet
		testList = board.getAdjList(26, 20);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(26, 19)));
		assertTrue(testList.contains(board.getCell(26, 21)));
		assertTrue(testList.contains(board.getCell(25, 20)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOnYavin4() {
		// test a roll of 1
		board.calcTargets(board.getCell(18, 25), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(15, 23)));
		assertTrue(targets.contains(board.getCell(20, 26)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(18, 25), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(22, 26)));
		assertTrue(targets.contains(board.getCell(21, 25)));	
		assertTrue(targets.contains(board.getCell(13, 23)));
		assertTrue(targets.contains(board.getCell(14, 24)));	
		assertTrue(targets.contains(board.getCell(14, 22)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(18, 25), 4);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(14, 25)));
		assertTrue(targets.contains(board.getCell(13, 24)));	
		assertTrue(targets.contains(board.getCell(12, 23)));
		assertTrue(targets.contains(board.getCell(13, 22)));	
		assertTrue(targets.contains(board.getCell(14, 21)));
	}
	
	@Test
	public void testTargetsInCoruscant() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 13), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(6, 12)));
		assertTrue(targets.contains(board.getCell(6, 14)));
		assertTrue(targets.contains(board.getCell(7, 13)));

		
		// test a roll of 3
		board.calcTargets(board.getCell(2, 13), 3);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(6, 10)));
		assertTrue(targets.contains(board.getCell(5, 11)));
		assertTrue(targets.contains(board.getCell(7, 11)));
		assertTrue(targets.contains(board.getCell(8, 12)));
		assertTrue(targets.contains(board.getCell(7, 13)));
		assertTrue(targets.contains(board.getCell(9, 13)));
		assertTrue(targets.contains(board.getCell(8, 14)));
		assertTrue(targets.contains(board.getCell(7, 15)));
		assertTrue(targets.contains(board.getCell(6, 12)));
		assertTrue(targets.contains(board.getCell(6, 14)));
		assertTrue(targets.contains(board.getCell(6, 16)));
		assertTrue(targets.contains(board.getCell(5, 15)));
		assertTrue(targets.contains(board.getCell(7, 15)));
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtPortal() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(23, 24), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(26, 25)));
		assertTrue(targets.contains(board.getCell(22, 24)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(23, 24), 3);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(22, 26)));
		assertTrue(targets.contains(board.getCell(21, 25)));
		assertTrue(targets.contains(board.getCell(20, 24)));	
		assertTrue(targets.contains(board.getCell(21, 23)));
		assertTrue(targets.contains(board.getCell(22, 22)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(23, 24), 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(21, 26)));
		assertTrue(targets.contains(board.getCell(21, 22)));
		assertTrue(targets.contains(board.getCell(21, 24)));
		assertTrue(targets.contains(board.getCell(22, 21)));	
		assertTrue(targets.contains(board.getCell(22, 23)));
		assertTrue(targets.contains(board.getCell(22, 25)));	
	}

	@Test
	public void testTargetsInSpace1() {
		// test a roll of 1
		board.calcTargets(board.getCell(17, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(18, 2)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(17, 2), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 3)));
		assertTrue(targets.contains(board.getCell(19, 3)));	
		assertTrue(targets.contains(board.getCell(20, 2)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(20, 19), 4);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(20, 3)));
		assertTrue(targets.contains(board.getCell(19, 4)));	
		assertTrue(targets.contains(board.getCell(18, 3)));
		assertTrue(targets.contains(board.getCell(18, 5)));
		assertTrue(targets.contains(board.getCell(19, 2)));
	}

	@Test
	public void testTargetsInSpace2() {
		// test a roll of 1
		board.calcTargets(board.getCell(4, 22), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(4, 21)));
		assertTrue(targets.contains(board.getCell(5, 22)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(4, 22), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(4, 19)));
		assertTrue(targets.contains(board.getCell(4, 21)));	
		assertTrue(targets.contains(board.getCell(5, 20)));
		assertTrue(targets.contains(board.getCell(5, 22)));	
		assertTrue(targets.contains(board.getCell(6, 21)));	
		assertTrue(targets.contains(board.getCell(7, 22)));	
		assertTrue(targets.contains(board.getCell(6, 23)));	
		assertTrue(targets.contains(board.getCell(5, 24)));
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 2, from adjacent block left of it
		board.getCell(19, 5).setOccupied(true);
		board.calcTargets(board.getCell(19, 4), 2);
		board.getCell(19, 5).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(18, 3)));
		assertTrue(targets.contains(board.getCell(20, 3)));
		assertTrue(targets.contains(board.getCell(19, 2)));	
		assertFalse( targets.contains( board.getCell(19, 5))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(14, 3).setOccupied(true);
		board.getCell(10, 3).setOccupied(true);
		board.calcTargets(board.getCell(10, 4), 1);
		board.getCell(14, 3).setOccupied(false);
		board.getCell(10, 3).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 3)));	
		assertTrue(targets.contains(board.getCell(9, 4)));	
		assertTrue(targets.contains(board.getCell(10, 5)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(18, 4).setOccupied(true);
		board.calcTargets(board.getCell(14, 3), 1);
		board.getCell(18, 4).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(18, 4)));
		assertTrue(targets.contains(board.getCell(14, 8)));	
		assertTrue(targets.contains(board.getCell(10, 4)));

	}
}
