package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

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
		assertTrue(player != null);
		assertEquals(player.getColor(), Color.green);
	}
	
	@Test
	void testWeaponsCards() {
		// test amount of weapons
		int count = 0;
		ArrayList<Card> cardList = board.getDeck();
		
		for(Card c : cardList) {
			if(c.getType() == CardType.WEAPON) {
				count++;
			}
		}
		
		assertEquals(count, 6);
		
		// test that blaster pistol is in the set
		Card testCard = new Card("Blaster Pistol", CardType.WEAPON);
		
		count = 0;
		for(Card c : cardList) {
			if(testCard.equals(c)) {
				count++;
			}
		}
		
		assertEquals(count, 1);
		
		// test that the type of the lightsaber card is CardType.WEAPON
		testCard = new Card("Lightsaber", CardType.WEAPON);
		
		count = 0;
		for(Card c : cardList) {
			if(testCard.equals(c)) {
				count++;
			}
		}
		assertEquals(count, 1);
	}

	
	@Test
	public void testDeck() {
		// test amount of PERSON cards
		int count = 0;
		ArrayList<Card> cardList = board.getDeck();
		
		for(Card c : cardList) {
			if(c.getType() == CardType.PERSON) {
				count++;
			}
		}
		
		assertEquals(count, 6);
		
		// test Obi-Wan Kenobi in deck and is PERSON
		Card testCard = new Card("Obi-Wan Kenobi", CardType.PERSON);
		count = 0;
		
		for(Card c : cardList) {
			if(c.equals(testCard)) {
				count++;
			}
		}
		
		assertEquals(count, 1);
		
		// test amount of ROOM cards
		count = 0;

		for(Card c : cardList) {
			if(c.getType() == CardType.ROOM) {
				count++;
			}
		}

		assertEquals(count, 9);
		
		// test Kashyyyk in deck and is ROOM
		testCard = new Card("Kashyyyk", CardType.ROOM);
		count = 0;

		for(Card c : cardList) {
			if(c.equals(testCard)) {
				count++;
			}
		}

		assertEquals(count, 1);
	}
	
	@Test
	public void testCardDealing() {
		// test that answer has 3 cards
		Solution answer = board.getAnswer();
		ArrayList<Card> deck = board.getDeck();
		Map<String, Player> players = board.getPlayers();
		assertFalse(answer.person == null);
		assertFalse(answer.weapon == null);
		assertFalse(answer.room == null);
		
		// test hand count is sufficient for each player
		int requirement = (deck.size() - 3) / players.values().size() - 1;
		int count = 0;
		
		for(Player player : players.values()) {
			if(player.getHand().size() >= requirement) {
				count++;
			}
		}
		
		assertEquals(count, players.size());
	}
}
