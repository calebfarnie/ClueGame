package tests;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;
import jdk.jshell.SourceCodeAnalysis.Suggestion;

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

	@Test
	void testCreateSuggestion() {
		Player testPlayer = new ComputerPlayer("testPlayer", "blue", 2, 25);
		ArrayList<Card> testDeck = board.getDeck();
		
		// room matches current location
		String roomName = "Tatooine";
		assertTrue(testPlayer.createSuggestion(roomName, testDeck).room.equals(new Card(roomName, CardType.ROOM)));
		
		// if only one weapon not seen, then select it
		testPlayer.updateSeen(new Card("Blaster Pistol", CardType.WEAPON));
		testPlayer.updateSeen(new Card("Blaster Rifle", CardType.WEAPON));
		testPlayer.updateSeen(new Card("Rocket Launcher", CardType.WEAPON));
		testPlayer.updateSeen(new Card("Vibro Knife", CardType.WEAPON));
		testPlayer.updateSeen(new Card("Thermal Detonator", CardType.WEAPON));
		assertTrue(testPlayer.createSuggestion(roomName, testDeck).weapon.equals(new Card("Lightsaber", CardType.WEAPON)));
		
		// if only one person not seen, then select them (can be same test as above)
		testPlayer.updateSeen(new Card("Luke Skywalker", CardType.PERSON));
		testPlayer.updateSeen(new Card("Emperor Palpatine", CardType.PERSON));
		testPlayer.updateSeen(new Card("Yoda", CardType.PERSON));
		testPlayer.updateSeen(new Card("Darth Vader", CardType.PERSON));
		testPlayer.updateSeen(new Card("Han Solo", CardType.PERSON));
		assertTrue(testPlayer.createSuggestion(roomName, testDeck).person.equals(new Card("Obi-Wan Kenobi", CardType.PERSON)));
		
		// if multiple weapons not seen, randomly select one
		testPlayer = new ComputerPlayer("testPlayer", "blue", 2, 25);
		testPlayer.updateSeen(new Card("Blaster Pistol", CardType.WEAPON));
		testPlayer.updateSeen(new Card("Blaster Rifle", CardType.WEAPON));
		testPlayer.updateSeen(new Card("Rocket Launcher", CardType.WEAPON));
		testPlayer.updateSeen(new Card("Vibro Knife", CardType.WEAPON));
		
		int count1 = 0, count2 = 0;
		
		for(int i = 0; i < 100; i++) {
			if(testPlayer.createSuggestion(roomName, testDeck).weapon.equals(new Card("Thermal Detonator", CardType.WEAPON))) {
				count1++;
			}else if(testPlayer.createSuggestion(roomName, testDeck).weapon.equals(new Card("Lightsaber", CardType.WEAPON))) {
				count2++;
			}
		}
		
		assertTrue(count1 > 10 && count2 > 10);
				
		// if multiple persons not seen, randomly select one
		testPlayer.updateSeen(new Card("Luke Skywalker", CardType.PERSON));
		testPlayer.updateSeen(new Card("Emperor Palpatine", CardType.PERSON));
		testPlayer.updateSeen(new Card("Yoda", CardType.PERSON));
		testPlayer.updateSeen(new Card("Darth Vader", CardType.PERSON));
		
		count1 = count2 = 0;
		
		for(int i = 0; i < 100; i++) {
			if(testPlayer.createSuggestion(roomName, testDeck).person.equals(new Card("Han Solo", CardType.PERSON))) {
				count1++;
			}else if(testPlayer.createSuggestion(roomName, testDeck).person.equals(new Card("Obi-Wan Kenobi", CardType.PERSON))) {
				count2++;
			}
		}
		
		assertTrue(count1 > 10 && count2 > 10);
		
	}
	
	@Test
	void testSelectTarget() {
		Player testPlayer = new ComputerPlayer("testPlayer", "blue", 6, 18);
		board.calcTargets(board.getCell(6, 18), 1);
		Set<BoardCell> targets = board.getTargets();
		BoardCell testCell1 = new BoardCell(5, 18);
		BoardCell testCell2 = new BoardCell(7, 18);
		BoardCell testCell3 = new BoardCell(6, 19);
		BoardCell testCell4 = new BoardCell(6, 17);
		Map<Character, Room> roomMap = board.getRoomMap();
		
		
		// if no rooms in list, select target randomly
		int count1, count2, count3, count4;
		count1 = count2 = count3 = count4 = 0;

		for(int i = 0; i < 500; i++) {
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell1))
				count1++;
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell2))
				count2++;
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell3))
				count3++;
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell4))
				count4++;
		}
		
		assertTrue(count1 > 10 && count2 > 10 && count3 > 10 && count4 > 10);
		
		// if room in list that has not been seen, select it
		testPlayer = new ComputerPlayer("testPlayer", "blue", 22, 19);
		board.calcTargets(board.getCell(22, 19), 2);
		targets = board.getTargets();
		BoardCell test = testPlayer.selectTarget(targets, roomMap);
		
		assertEquals(board.getCell(25, 15), testPlayer.selectTarget(targets, roomMap));
		
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
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell1));
				count1++;
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell2));
				count2++;
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell3));
				count3++;
			if(testPlayer.selectTarget(targets, roomMap).equals(testCell4));
				count4++;
		}
		
		assertTrue(count1 > 10 && count2 > 10 && count3 > 10 && count4 > 10);
		
	}

}
