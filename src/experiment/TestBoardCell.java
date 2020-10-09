package experiment;
/* 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C12A-2 Clue Paths 1
 * 05 October 2020
 */

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class TestBoardCell{
	
	private int row; 
	private int col; 
	private boolean isRoom;
	private boolean isOccupied;
	Set<TestBoardCell> adjList = new HashSet<TestBoardCell>();

	public TestBoardCell(int row, int col) {
		this.row = row; 
		this.col = col; 
	}
	
	public void addAdj(TestBoardCell cell){
		adjList.add(cell);
	}
	
//	public void setAdjList(Set<TestBoardCell> setIn) {
//		adjList = setIn;
//	}
	
	public Set<TestBoardCell> getAdjList(){
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

//	@Override
//	public int compareTo(TestBoardCell o) {
//		if(this.row == o.getRow() && this.col == o.getCol())
//			return 0;
//		return 1;
//	}
}
