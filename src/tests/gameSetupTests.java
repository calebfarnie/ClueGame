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
import clueGame.Card;
import clueGame.CardType;
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
		assertTrue(player != null);
		assertEquals(player.getColor(), Color.green);
	}
	
	@Test
	void testWeaponsCards() {
		// test amount of weapons
		int count = 0;
		Set<Card> cardList = board.getDeck();
		
		for(Card c : cardList) {
			if(c.getType() == CardType.WEAPON) {
				count++;
			}
		}
		
		assertEquals(count, 6);
		
		// test that blaster pistol is in the set
		Card testCard = new Card("Blaster Pistol", CardType.WEAPON);
		assertTrue(cardList.contains(testCard));
		
		// test that the type of the lightsaber card is CardType.WEAPON
		testCard = new Card("Light Saber", CardType.WEAPON);
		
		count = 0;
		for(Card c : cardList) {
			if(c.getType() == CardType.WEAPON && c.getName() == "Lightsaber") {
				count++;
			}
		}
		assertEquals(count, 1);
	}

}
