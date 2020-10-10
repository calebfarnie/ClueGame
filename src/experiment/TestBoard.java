/* 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C13A-1 Clue Paths 2
 * 05 October 2020
 */

package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {

	final static int COLS = 4;
	final static int ROWS = 4;
	
	private TestBoardCell [][] board;
	
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard() {
		// create board
		board = new TestBoardCell[ROWS][COLS];
		
		// fill board with cells
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				board[r][c] = new TestBoardCell(r,c);
			}
		}
		
		// get adjacency lists
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLS; c++) {
				calcAdjList(r,c);
			}
		}
	}
	
	public void calcAdjList(int row, int col){
		TestBoardCell cell = getCell(row, col);

		if(row > 0) {
			cell.addAdj(getCell(row-1, col));
		}

		if(col > 0) {
			cell.addAdj(getCell(row, col-1));
		}

		if(row < TestBoard.ROWS-1) {
			cell.addAdj(getCell(row+1, col));
		}

		if(col < TestBoard.COLS-1) {
			cell.addAdj(getCell(row, col+1));
		}
	}
	
	public void calcTargets( TestBoardCell startCell, int pathLength) {
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(TestBoardCell startCell, int numSteps) {
		// recursively find target cells
		for(TestBoardCell c : startCell.getAdjList()) {
			if(!visited.contains(c) && !c.getOccupied()) {
				visited.add(c);
				if(numSteps ==1) {
					targets.add(c);
				}else {
					findAllTargets(c, numSteps -1);
				}
				visited.remove(c);
			}
		}
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell( int row, int col ) {
		return board[row][col];
	}
}
