package experiment;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TestBoard {

	Map<TestBoardCell, Set<TestBoardCell>> adjList;
	
	public TestBoard() {
		adjList = new TreeMap<TestBoardCell, Set<TestBoardCell>>();
	}
	
	void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	
	Set<TestBoardCell> getTargets(){
		return null;
	}
	
	TestBoardCell getCell( int row, int col ) {
		return null;
	}
}
