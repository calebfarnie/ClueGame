package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C15A Clue Board 2
 * 16 October 2020
 */

public class Room {
	
	// instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private char secretPassage = '0';
	private boolean isWalkway;

	public Room(String name) {
		this.name = name;

	}
	
	public void setCenterCell(BoardCell center) {
		this.centerCell = center;
	}
	
	public void setLabelCell(BoardCell label) {
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
	
	public void setWalkway() {
		this.isWalkway = true;
	}
	
	public boolean getWalkway() {
		return isWalkway;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}

}
