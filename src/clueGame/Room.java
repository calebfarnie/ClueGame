package clueGame;

/**
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class Room {

	// instance variables
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private char secretPassage = '0';
	private boolean isWalkway;
	private int occupancy;

	public Room(String name) {
		this.name = name;

	}

	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}

	public void addOccupant() {
		occupancy++;
	}

	public void subtractOccupant() {
		occupancy--;
	}

	public int getOccupancy() {
		return occupancy;
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
