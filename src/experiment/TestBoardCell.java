package experiment;

import java.util.Set;

public class TestBoardCell {
	
	private int row; 
	private int col; 
	private boolean isRoom;
	private boolean isOccupied;

	public TestBoardCell(int row, int col) {
		this.row = row; 
		this.col = col; 
	}
	
	public Set<TestBoardCell> getAdjList() {
		return null;
	}
	
	public void setRoom(boolean isRoom) {
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
	
	
}
