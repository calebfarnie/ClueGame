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
	private BoardCell startingLocation;
	private boolean isHuman;
	private Set<Card> hand;
		
	public abstract void updateHand(Card card);
	
	public int getStartRow() {
		return 0;
	}
	
	public int getStartCol() {
		return 0;
	}

}
