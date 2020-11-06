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
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
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
		trueAnswer.room = new Card("Dagobah", CardType.ROOM);
		trueAnswer.weapon = new Card("Blaster Pistol", CardType.WEAPON);
		board.setAnswer(trueAnswer);
	}

	@Test
	void testCheckAccusation() {
		// test solution is correct
		Card accusee = new Card("Han Solo", CardType.PERSON);
		Card weapon = new Card("Blaster Pistol", CardType.WEAPON); 
		Card room = new Card("Dagobah", CardType.ROOM);
		
		assertTrue(board.checkAccusation(accusee, room, weapon));

		// test solution with wrong person
		accusee = new Card("Yoda", CardType.PERSON);
		assertTrue(board.checkAccusation(accusee, room, weapon) == false);
		
		// test solution with wrong weapon
		accusee = new Card("Han Solo", CardType.PERSON);
		weapon = new Card("Lightsaber", CardType.WEAPON); 
		assertTrue(board.checkAccusation(accusee, room, weapon) == false);

		// test solution with wrong room

		weapon = new Card("Blaster Pistol", CardType.WEAPON);
		room = new Card("Tatooine", CardType.ROOM);
		assertTrue(board.checkAccusation(accusee, room, weapon) == false);
	}

	@Test
	void testDisproveSolution() {
		Player testPlayer = new ComputerPlayer("test", "red", 0, 0);
		Solution suggestion = new Solution();
		suggestion.person = new Card("Darth Vader", CardType.PERSON);
		suggestion.weapon = new Card("Lightsaber", CardType.WEAPON);
		suggestion.room = new Card("Couruscant", CardType.ROOM);

		// if player has only one matching card, return that card
		Set<Card> hand = new HashSet<Card>();
		hand.add(new Card("Darth Vader", CardType.PERSON));
		hand.add(new Card("Blaster Pistol", CardType.WEAPON));
		hand.add(new Card("Han Solo", CardType.PERSON));
		testPlayer.setHand(hand);
		
		assertTrue(testPlayer.disproveSuggestion(suggestion.person, suggestion.room, suggestion.weapon).equals(new Card("Darth Vader", CardType.PERSON)));

		// if player has >1 matching card, randomly return a matching card
		hand.add(new Card("Lightsaber", CardType.WEAPON));
		hand.add(new Card("Obi-Wan Kenobi", CardType.PERSON));
		testPlayer.setHand(hand);
		int count1 = 0;
		int count2 = 0;
		
		for(int i = 0; i < 100; i++) {
			if(testPlayer.disproveSuggestion(suggestion.person, suggestion.room, suggestion.weapon).equals(new Card("Darth Vader", CardType.PERSON)))
				count1++;
			if(testPlayer.disproveSuggestion(suggestion.person, suggestion.room, suggestion.weapon).equals(new Card("Lightsaber", CardType.WEAPON)))
				count2++;
		}
		
		assertTrue(count1 > 10 && count2 > 10);

		// if player has no matching cards, return null
		hand = new HashSet<Card>();
		hand.add(new Card("Luke Skywalker", CardType.PERSON));
		hand.add(new Card("Blaster Pistol", CardType.WEAPON));
		hand.add(new Card("Han Solo", CardType.PERSON));
		testPlayer.setHand(hand);
		
		assertEquals(testPlayer.disproveSuggestion(suggestion.person, suggestion.room, suggestion.weapon), null);

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
