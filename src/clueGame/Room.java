package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C14A-1 Clue Init 2
 * 12 October 2020
 */

public class Room {
	
	// instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;

	public Room(String name) {
		this.name = name;

	}
	
	public void setCenterCell(BoardCell center) {
		centerCell = center;
	}
	
	public void setLabelCell(BoardCell label) {
		labelCell = label;
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
