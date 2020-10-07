package experiment;
/* 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C12A-2 Clue Paths 1
 * 05 October 2020
 */

import java.util.Set;
import java.util.TreeSet;

public class TestBoardCell implements Comparable<TestBoardCell>{
	
	private int row; 
	private int col; 
	private boolean isRoom;
	private boolean isOccupied;
	Set<TestBoardCell> adjList;

	public TestBoardCell(int row, int col) {
		this.row = row; 
		this.col = col; 
		adjList = new TreeSet();
		//adjList = new TreeSet<TestBoardCell>();
	}
	
	public Set<TestBoardCell> getAdjList() {
		//Set<TestBoardCell> genAdjList = new TreeSet<TestBoardCell>();
		
		if(row > 0) {
			adjList.add(TestBoard.getCell(row-1, col));
		}
		
		if(col > 0) {
			adjList.add(TestBoard.getCell(row, col-1));
		}
		
		if(row < TestBoard.ROWS-1) {
			adjList.add(TestBoard.getCell(row+1, col));
		}
		
		if(col < TestBoard.COLS-1) {
			adjList.add(TestBoard.getCell(row, col+1));
		}
		
		
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

	@Override
	public int compareTo(TestBoardCell o) {
		if(this.row == o.getRow() && this.col == o.getCol())
			return 0;
		return -1;
	}
}
