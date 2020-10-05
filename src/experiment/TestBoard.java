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

	private Map<TestBoardCell, Set<TestBoardCell>> adjList;
	private TestBoardCell [][] board;
	
	public TestBoard() {
		board = new TestBoardCell[4][4];
		adjList = new TreeMap<TestBoardCell, Set<TestBoardCell>>();
	}
	
	public void calcTargets( TestBoardCell startCell, int pathlength) {
		// calculates legal targets for a move from startCell of length pathlength.
	}
	
	public Set<TestBoardCell> getTargets(){
		return new TreeSet<TestBoardCell>();
	}
	
	public TestBoardCell getCell( int row, int col ) {
		return new TestBoardCell(0,0);
	}
}
