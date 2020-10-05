package experiment;
/* 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C12A-2 Clue Paths 1
 * 05 October 2020
 */

import java.util.Set;
import java.util.TreeSet;

public class TestBoardCell implements Comparable{
	
	private int row; 
	private int col; 
	private boolean isRoom;
	private boolean isOccupied;

	public TestBoardCell(int row, int col) {
		this.row = row; 
		this.col = col; 
	}
	
	public Set<TestBoardCell> getAdjList() {
		return new TreeSet<TestBoardCell>();
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

	@Override
	public int compareTo(Object o) {
		if(this.row == ((TestBoardCell) o).getRow() && this.col == ((TestBoardCell) o).getCol())
			return 0;
		return -1;
	}
	
	
}
