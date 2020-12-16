package tests;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C14A-2 Clue Board 1
 * 12 October 2020
 */

import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	private TestBoard board;

	@BeforeEach
	public void setUp() {
		// board should create adjacency list
		board = new TestBoard();
	}

	// test for top left corner
	@Test
	public void testTopLeftCornerAdj() {
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
	}

	// test for bottom right corner
	@Test
	public void testBottomRightCornerAdj() {
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
	}

	// test for a right edge
	@Test
	public void testRightEdge() {
		TestBoardCell cell = board.getCell(1, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertEquals(3, testList.size());
	}

	// test for a left edge
	@Test
	public void testLeftEdge() {
		TestBoardCell cell = board.getCell(2, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertEquals(3, testList.size());
	}

	// test starting location and their targets
	@Test
	public void testStartingPoint1() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertEquals(6, testList.size());
	}

	// test starting location and their targets
	@Test
	public void testStartingPoint2() {
		TestBoardCell cell = board.getCell(2, 1);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(6, testList.size());

	}

	// test starting location and their targets
	@Test
	public void testStartingPoint3() {
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(6, testList.size());
	}

	// test for room and occupancy #1
	@Test
	public void testTargetsMixed1() {
		// set up occupied cells
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		TestBoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(1, 1)));
		Assert.assertTrue(testList.contains(board.getCell(2, 2)));
		Assert.assertTrue(testList.contains(board.getCell(3, 3)));
		Assert.assertEquals(3, testList.size());
	}

	// test for room and occupancy #2
	@Test
	public void testTargetsMixed2() {
		board.getCell(1, 3).setOccupied(true);
		board.getCell(3, 3).setIsRoom(true);
		TestBoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> testList = board.getTargets();
		Assert.assertTrue(testList.contains(board.getCell(0, 0)));
		Assert.assertTrue(testList.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList.contains(board.getCell(1, 1)));
		Assert.assertTrue(testList.contains(board.getCell(3, 1)));
		Assert.assertTrue(testList.contains(board.getCell(0, 2)));
		Assert.assertTrue(testList.contains(board.getCell(3, 3)));
		Assert.assertEquals(6, testList.size());
	}
}
