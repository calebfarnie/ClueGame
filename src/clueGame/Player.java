package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.awt.Color;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	private Set<Card> hand;
		
	public abstract void updateHand(Card card);
	
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

}
