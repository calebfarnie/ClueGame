package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	protected Set<Card> hand;
		
	public abstract void updateHand(Card card);
	public abstract void updateSeen(Card seenCard);
	
	public Player(String name, String color, int startRow, int startCol) {
		this.name = name;
		
		switch(color.toLowerCase()) {
		case "white":
			this.color = Color.white;
			break;
		case "blue":
			this.color = Color.blue;
			break;
		case "green":
			this.color = Color.green;
			break;
		case "red":
			this.color = Color.red;
			break;
		case "brown":
			this.color = Color.orange;
			break;
		case "black":
			this.color = Color.black;
			break;
		}
		
		this.row = startRow;
		this.column = startCol;
		this.hand = new HashSet<Card>();
	}
	
	public Card disproveSuggestion(Card person, Card room, Card weapon) {
		// TODO
		return new Card("test", null);
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.column;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Set<Card> getHand(){
		return hand;
	}

}
