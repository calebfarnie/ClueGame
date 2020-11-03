package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Player;
import clueGame.Room;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

class gameSetupTests {
	
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

	@Test
	void testPlayerSetup() {
		
		// test that 6 players are in the set
		Map<String, Player> testList = board.getPlayers();
		assertEquals(6, testList.size());
		
		// test that Luke Skywalker is in the correct location
		// just test a standard planet location
		Player player = board.getPlayer("Luke Skywalker");
		assertTrue( player != null );
		assertEquals( player.getRow(), 8);
		assertEquals( player.getCol(), 1);
		
		// test that Darth Vader is in the correct location
		player = board.getPlayer("Darth Vader");
		assertTrue( player != null );
		assertEquals(player.getRow(), 13);
		assertEquals(player.getCol(), 26);
		
		// test that Han Solo is in the correct location
		player = board.getPlayer("Han Solo");
		assertTrue( player != null );
		assertEquals(player.getRow(), 1);
		assertEquals(player.getCol(), 5);
		
		// ensure color of Yoda is green
		player = board.getPlayer("Yoda");
		assertTrue( player != null );
		assertEquals(player.getColor(), Color.green);
	}

}
