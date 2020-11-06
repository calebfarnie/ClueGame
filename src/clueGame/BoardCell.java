package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	// instance variables
	private int row;
	private int col;
	private char initial;
	private char secretPassage;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private DoorDirection doorDirection;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	// constructors
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.isOccupied = false;
	}
	
	public BoardCell(int row, int col, char initial, boolean roomLabel, boolean roomCenter, DoorDirection doorDirection, boolean isDoorway) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.roomLabel = roomLabel;
		this.roomCenter = roomCenter;
		this.doorDirection = doorDirection;
		this.isDoorway = isDoorway;
		this.isOccupied = false;
	}
	
	// public methods
	public char getInitial() {
		return this.initial;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public void addAdj(BoardCell adj) {
		this.adjList.add(adj);
	}
	
	public Set<BoardCell> getAdjList(){
		return this.adjList;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isRoom() {
		return this.isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		if(this.roomCenter) {
			return false;
		}else {
			return this.isOccupied;
		}
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}

	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}

	public boolean isDoorway() {
		return this.isDoorway;
	}

	public boolean isLabel() {
		return this.roomLabel;
	}

	public boolean isRoomCenter() {
		return this.roomCenter;
	}

	public char getSecretPassage() {
		return this.secretPassage;
	}
	
	public boolean isWalkway() {
		return (this.initial == 'W' || this.isDoorway || this.roomCenter);
	}
	
	public boolean equals(BoardCell cell) {
		return this.row == cell.getRow() && this.col == cell.getCol();
	}
}
