/* 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C12A-2 Clue Paths 1
 * 05 October 2020
 */

package experiment;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TestBoard {

	final static int COLS = 4;
	final static int ROWS = 4;
	
	private static TestBoardCell [][] board;
	
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	private Map<TestBoardCell, Set<TestBoardCell>> adjList;
	
	public TestBoard() {
		board = new TestBoardCell[ROWS][COLS];
	}
	
	public static void calcTargets( TestBoardCell startCell, int pathlength) {
		// calculates legal targets for a move from startCell of length pathlength.
	}
	
	public static Set<TestBoardCell> getTargets(){
		return new TreeSet<TestBoardCell>();
	}
	
	public static TestBoardCell getCell( int row, int col ) {
		// PROBLEM HERE
		//return board[row][col];
		return new TestBoardCell(0,0);
	}
}
