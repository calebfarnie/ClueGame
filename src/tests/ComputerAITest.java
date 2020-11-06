package tests;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

class ComputerAITest {
	
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

	//@Test
	void testCreateSuggestion() {
		Set<Card> seenCards = new HashSet<Card>();
		
		// room matches current location
		
		// if only one weapon not seen, then select it
		
		// if only one person not seen, then select them (can be same test as above)
		
		// if multiple weapons not seen, randomly select one
		
		// if multiple persons not seen, randomly select one
		
	}
	
	@Test
	void testSelectTargets() {
		Player testPlayer = new ComputerPlayer("testPlayer", "blue", 6, 18);
		board.calcTargets(board.getCell(6, 18), 1);
		Set<BoardCell> targets = board.getTargets();
		BoardCell testCell1 = new BoardCell(5, 18);
		BoardCell testCell2 = new BoardCell(4, 18);
		BoardCell testCell3 = new BoardCell(6, 19);
		BoardCell testCell4 = new BoardCell(6, 17);
		
		// if no rooms in list, select target randomly
		int count1, count2, count3, count4;
		count1 = count2 = count3 = count4 = 0;

		for(int i = 0; i < 500; i++) {
			if(testPlayer.selectTarget(targets).equals(testCell1));
				count1++;
			if(testPlayer.selectTarget(targets).equals(testCell2));
				count2++;
			if(testPlayer.selectTarget(targets).equals(testCell3));
				count3++;
			if(testPlayer.selectTarget(targets).equals(testCell4));
				count4++;
		}
		
		assertTrue(count1 > 10 && count2 > 10 && count3 > 10 && count4 > 10);
		
		// if room in list that has not been seen, select it
		testPlayer = new ComputerPlayer("testPlayer", "blue", 22, 19);
		board.calcTargets(board.getCell(22, 19), 3);
		targets = board.getTargets();
		
		assertEquals(board.getCell(25, 15), testPlayer.selectTarget(targets));
		
		// if room in list that has been seen, each target (including room) selected randomly
		testPlayer = new ComputerPlayer("testPlayer", "blue", 15, 8);
		testPlayer.updateSeen(new Card("Kashyyyk", CardType.ROOM));
		board.calcTargets(board.getCell(15, 8), 2);
		targets = board.getTargets();
		
		testCell1 = new BoardCell(16, 3);
		testCell2 = new BoardCell(17, 8);
		testCell3 = new BoardCell(16, 9);
		testCell4 = new BoardCell(14, 9);
		
		count1 = count2 = count3 = count4 = 0;

		for(int i = 0; i < 500; i++) {
			if(testPlayer.selectTarget(targets).equals(testCell1));
				count1++;
			if(testPlayer.selectTarget(targets).equals(testCell2));
				count2++;
			if(testPlayer.selectTarget(targets).equals(testCell3));
				count3++;
			if(testPlayer.selectTarget(targets).equals(testCell4));
				count4++;
		}
		
		assertTrue(count1 > 10 && count2 > 10 && count3 > 10 && count4 > 10);
		
	}

}
