package tests;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
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

	@Test
	void testHandleSuggestion() {
		// Process all the players in turn, each to see if they can dispute the suggestion.
		// If return null, no player can dispute the suggestion. Otherwise return the first
		// card that disputed the suggestion.
		Solution suggestion = new Solution();
		suggestion.person = new Card("Darth Vader", CardType.PERSON);
		suggestion.weapon = new Card("Lightsaber", CardType.WEAPON);
		suggestion.room = new Card("Couruscant", CardType.ROOM);
		
		// add 3 test players to players AL
		ArrayList<Player> players = new ArrayList<Player>();
		Set<Card> hand = new HashSet<Card>();
		players.add(new HumanPlayer("myPlayer", "red", 0, 1));
		hand.add(new Card("Luke Skywalker", CardType.PERSON));
		hand.add(new Card("Blaster Pistol", CardType.WEAPON));
		hand.add(new Card("Han Solo", CardType.PERSON));
		players.get(0).setHand(hand);
		
		players.add(new ComputerPlayer("player1", "green", 0, 2));
		hand = new HashSet<Card>();
		hand.add(new Card("Yoda", CardType.PERSON));
		hand.add(new Card("Blaster Rifle", CardType.WEAPON));
		hand.add(new Card("Geonosis", CardType.ROOM));
		players.get(1).setHand(hand);
		
		players.add(new ComputerPlayer("player2", "blue", 0, 3));
		hand = new HashSet<Card>();
		hand.add(new Card("Obi-Wan Kenobi", CardType.PERSON));
		hand.add(new Card("Vibro Knife", CardType.WEAPON));
		hand.add(new Card("Kashyyyk", CardType.ROOM));
		players.get(2).setHand(hand);
		
		// Suggestion no one can disprove returns null
		assertEquals(board.handleSuggestion(players, players.get(1)), null);
		assertEquals(board.handleSuggestion(players, players.get(2)), null);

		// Suggestion only accusing player can disprove, returns null
		Card newCard = new Card("Couruscant", CardType.ROOM);
		players.get(2).updateHand(newCard);
		
		assertEquals(board.handleSuggestion(players, players.get(2)), null);
		
		// Suggestion only human can disprove, return answer (i.e. card that disproves suggestion)
		players.get(2).getHand().remove(newCard);
		newCard = new Card("Darth Vader", CardType.PERSON);
		players.get(0).updateHand(newCard);
		assertEquals(board.handleSuggestion(players, players.get(1)), newCard);

		// Suggestion that two players can disprove, correct player (next player in list) returns answer
		players.get(0).getHand().remove(newCard);
		Card lightsaberCard = new Card("Lightsaber", CardType.WEAPON);
		players.get(2).updateHand(lightsaberCard);
		players.get(0).updateHand(new Card("Couruscant", CardType.ROOM));

		assertEquals(board.handleSuggestion(players, players.get(1)), lightsaberCard);
	}

}
