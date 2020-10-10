package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C13A-2 Clue Init 1
 * 10 October 2020
 */

public class Room {
	
	// instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;

	public Room(String name, BoardCell center, BoardCell label) {
		this.name = name;
		this.centerCell = center;
		this.labelCell = label;
	}
	
	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}

}
