package experiment;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TestBoard {

	Map<TestBoardCell, Set<TestBoardCell>> adjList;
	
	public TestBoard() {
		adjList = new TreeMap<TestBoardCell, Set<TestBoardCell>>();
	}
	
	void calcTargets( TestBoardCell startCell, int pathlength) {
		// calculates legal targets for a move from startCell of length pathlength.
	}
	
	Set<TestBoardCell> getTargets(){
		return new TreeSet<TestBoardCell>();
	}
	
	TestBoardCell getCell( int row, int col ) {
		return new TestBoardCell(0,0);
	}
}
