package experiment;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class IntBoardTests {
	IntBoard board;

	@Before
	public void setUp() throws Exception {
		board = new IntBoard(4, 4);
		
	}

	@Test
	public void testAdjacencyTL() {
		BoardCell cell = board.getCell(0,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyBR() {
		BoardCell cell = board.getCell(3,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyRE() {
		BoardCell cell = board.getCell(3, 1);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3, 0)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacency2CM() {
		BoardCell cell = board.getCell(1, 1);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacencyBE() {
		BoardCell cell = board.getCell(2,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 3)));
		assertTrue(testList.contains(board.getCell(3, 3)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyM() {
		BoardCell cell = board.getCell(2,2);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets00_3()
	{
		//This test is correct if you account for backtracking.
		BoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}

	@Test
	public void testTargets22_4()
	{
		//This test is correct if you account for backtracking.
		BoardCell cell = board.getCell(2, 2);
		board.calcTargets(cell, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	@Test
	public void testTargets01_2()
	{
		//This test is correct if you account for backtracking.
		BoardCell cell = board.getCell(0, 1);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
	}
	
	@Test
	public void testIndexes()
	{
		assertEquals(2, board.getNumericCell(2,0));
		assertEquals(8, board.getNumericCell(0,2));
		assertEquals(14, board.getNumericCell(2,3));
		assertEquals(15, board.getNumericCell(3,3));
	}
}
