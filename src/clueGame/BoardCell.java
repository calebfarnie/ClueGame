package clueGame;

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
	private boolean inRoom;
	private boolean isOccupied;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	public BoardCell() {
		// TODO Auto-generated constructor stub
	}
	
	public void addAdj(BoardCell adj) {
		// TODO
	}
	
	public Set<BoardCell> getAdjList(){
		return adjList;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.inRoom = isRoom;
	}
	
	public boolean isRoom() {
		return inRoom;
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
		// TODO Auto-generated method stub
		return DoorDirection.NONE;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
