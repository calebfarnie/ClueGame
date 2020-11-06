package tests;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;


class GameSolutionTest {
	
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		// create true answer for tests
		Solution trueAnswer = new Solution();
		trueAnswer.person = new Card("Han Solo", CardType.PERSON);
		trueAnswer.weapon = new Card("Blaster Pistol", CardType.WEAPON);
		trueAnswer.room = new Card("Dagobah", CardType.ROOM);
	}

	@Test
	void testCheckAccusation() {
		// test solution is correct
		Card accusee = new Card("Han Solo", CardType.PERSON);
		Card weapon = new Card("Blaster Pistol", CardType.WEAPON); 
		Card room = new Card("Dagobah", CardType.ROOM);
		assertTrue(board.checkAccusation(accusee, weapon, room));

		// test solution with wrong person
		accusee = new Card("Yoda", CardType.PERSON);
		assertTrue(board.checkAccusation(accusee, weapon, room) == false);
		
		// test solution with wrong weapon
		accusee = new Card("Han Solo", CardType.PERSON);
		weapon = new Card("Lightsaber", CardType.WEAPON); 
		assertTrue(board.checkAccusation(accusee, weapon, room) == false);

		// test solution with wrong room

		weapon = new Card("Blaster Pistol", CardType.WEAPON);
		room = new Card("Tatooine", CardType.ROOM);
		assertTrue(board.checkAccusation(accusee, weapon, room) == false);
	}

	@Test
	void testDisproveSolution() {
		fail("Not yet implemented");

		// if player has only one matching card, return that card

		// if player has >1 matching card, randomly return a matching card

		// if player has no matching cards, return null

	}

	//@Test
	void testHandleSuggestion() {
		fail("Not yet implemented");

		// Suggestion no one can disprove returns null

		// Suggestion only accusing player can disprove, returns null

		// Suggestion only human can disprove, return answer (i.e. card that disproves suggestion)

		// Suggestion that two players can disprove, correct player (next player in list) returns answer

	}

}
