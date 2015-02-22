package clueGame;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class TestAdjandTargets {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	@Test
	public void testAdjinRooms()
	{
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		testList = board.getAdjList(4, 0);
		assertEquals(0, testList.size());
		testList = board.getAdjList(15, 20);
		assertEquals(0, testList.size());
		testList = board.getAdjList(18, 11);
		assertEquals(0, testList.size());
		testList = board.getAdjList(14, 12);
		assertEquals(0, testList.size());
		testList = board.getAdjList(5, 20);
		assertEquals(0, testList.size());
	}


	@Test
	public void testAdjExit()
	{
		LinkedList<BoardCell> testList = board.getAdjList(11, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 7)));
		testList = board.getAdjList(10, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(10, 16)));
		testList = board.getAdjList(5, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		testList = board.getAdjList(5, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 15)));

	}

	@Test
	public void testAdjDoor()
	{
		LinkedList<BoardCell> testList = board.getAdjList(15, 17);
		assertTrue(testList.contains(board.getCellAt(15, 16)));
		assertTrue(testList.contains(board.getCellAt(15, 18)));
		assertTrue(testList.contains(board.getCellAt(14, 17)));
		assertTrue(testList.contains(board.getCellAt(16, 17)));
		assertEquals(4, testList.size());
		testList = board.getAdjList(6, 15);
		assertTrue(testList.contains(board.getCellAt(5, 15)));
		assertTrue(testList.contains(board.getCellAt(6, 14)));
		assertTrue(testList.contains(board.getCellAt(6, 16)));
		assertEquals(3, testList.size());
		testList = board.getAdjList(13, 11);
		assertTrue(testList.contains(board.getCellAt(13, 10)));
		assertTrue(testList.contains(board.getCellAt(13, 12)));
		assertTrue(testList.contains(board.getCellAt(12, 11)));
		assertTrue(testList.contains(board.getCellAt(14, 11)));
		assertEquals(4, testList.size());
		testList = board.getAdjList(4, 4);
		assertTrue(testList.contains(board.getCellAt(4, 3)));
		assertTrue(testList.contains(board.getCellAt(4, 5)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertEquals(3, testList.size());
	}

	@Test
	public void testAdjWalkways()
	{
		LinkedList<BoardCell> testList = board.getAdjList(0, 4);
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertEquals(1, testList.size());

		testList = board.getAdjList(6, 0);
		assertTrue(testList.contains(board.getCellAt(5, 0)));
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertEquals(3, testList.size());

		testList = board.getAdjList(6, 21);
		assertTrue(testList.contains(board.getCellAt(6, 20)));
		assertTrue(testList.contains(board.getCellAt(6, 22)));
		assertEquals(2, testList.size());

		testList = board.getAdjList(15,7);
		assertTrue(testList.contains(board.getCellAt(15, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		assertTrue(testList.contains(board.getCellAt(15, 8)));
		assertTrue(testList.contains(board.getCellAt(16, 7)));
		assertEquals(4, testList.size());

		testList = board.getAdjList(21, 15);
		assertTrue(testList.contains(board.getCellAt(21, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertEquals(2, testList.size());

		testList = board.getAdjList(14, 22);
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 22)));
		assertEquals(2, testList.size());

		testList = board.getAdjList(5, 3);
		assertTrue(testList.contains(board.getCellAt(5, 2)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertEquals(3, testList.size());
	}

	@Test
	public void testTargets1() {
		board.calcTargets(21, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));	

		board.calcTargets(14, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));	
		assertTrue(targets.contains(board.getCellAt(15, 0)));			
	}

	@Test
	public void testTargets2() {
		board.calcTargets(21, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));

		board.calcTargets(14, 0, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));			
	}

	@Test
	public void testTargets4() {
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));

		board.calcTargets(14, 0, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	

	}	

	@Test
	public void testTargets6() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(13, 4)));
		assertTrue(targets.contains(board.getCellAt(15, 5)));	
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 4)));	
	
	}	


	@Test 
	public void testTargetsEnter()
	{
		board.calcTargets(17, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 15)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));
	}


	@Test
	public void testTargetsShortenTurn() 
	{
		board.calcTargets(12, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 7)));	
		assertTrue(targets.contains(board.getCellAt(11, 7)));		
		assertTrue(targets.contains(board.getCellAt(12, 8)));
		assertTrue(targets.contains(board.getCellAt(13, 9)));
		assertTrue(targets.contains(board.getCellAt(13, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		assertTrue(targets.contains(board.getCellAt(12, 10)));
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 6)));	
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		assertTrue(targets.contains(board.getCellAt(10, 8)));		

	}

	@Test
	public void testRoomExit()
	{
		board.calcTargets(4, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		board.calcTargets(4, 20, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));
	}

}
