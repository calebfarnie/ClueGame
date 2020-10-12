package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C13A-2 Clue Init 1
 * 10 October 2020
 */

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	
	// instance variables
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public BoardCell(int row, int col, char initial, boolean roomLabel, boolean roomCenter, DoorDirection doorDirection, boolean isDoorway) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.roomLabel = roomLabel;
		this.roomCenter = roomCenter;
		this.doorDirection = doorDirection;
		this.isDoorway = isDoorway;
	}
	
	public char getInitial() {
		return initial;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public void addAdj(BoardCell adj) {
		// TODO
	}
	
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}
	
	

}
