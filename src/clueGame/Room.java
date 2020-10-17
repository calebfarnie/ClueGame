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
	private char secretPassage = '0';
	private boolean isWalkway;

	public Room(String name) {
		this.name = name;

	}
	
	public void setCenterCell(BoardCell center) {
		this.centerCell = center;
	}
	
	/*
	 * public void setCenterCell(int row, int col) {
		this.centerCell = center;
	}
	 */
	
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
