package clueGame;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class TestAdjandTargets {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("ClueLayout.csv", "LegendFile.txt");
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
		LinkedList<BoardCell> testList = board.getAdjList(6, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(7, 9)));
		testList = board.getAdjList(8, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 16)));
		testList = board.getAdjList(21, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(20, 9)));
		testList = board.getAdjList(22, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(22, 18)));

	}

	@Test
	public void testAdjDoor()
	{
		LinkedList<BoardCell> testList = board.getAdjList(17, 17);
		assertTrue(testList.contains(board.getCellAt(17, 18)));
		assertTrue(testList.contains(board.getCellAt(17, 16)));
		assertTrue(testList.contains(board.getCellAt(18, 17)));
		assertEquals(3, testList.size());
		testList = board.getAdjList(5, 12);
		assertTrue(testList.contains(board.getCellAt(5, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 11)));
		assertTrue(testList.contains(board.getCellAt(6, 12)));
		assertTrue(testList.contains(board.getCellAt(4, 12)));
		assertEquals(4, testList.size());
		testList = board.getAdjList(20, 2);
		assertTrue(testList.contains(board.getCellAt(20, 1)));
		assertTrue(testList.contains(board.getCellAt(20, 3)));
		assertTrue(testList.contains(board.getCellAt(19, 2)));
		assertTrue(testList.contains(board.getCellAt(21, 2)));
		assertEquals(4, testList.size());
		testList = board.getAdjList(23, 18);
		assertTrue(testList.contains(board.getCellAt(23, 17)));
		assertTrue(testList.contains(board.getCellAt(23, 19)));
		assertTrue(testList.contains(board.getCellAt(22,18)));
		assertTrue(testList.contains(board.getCellAt(24, 18)));
		assertEquals(4, testList.size());
	}

	@Test
	public void testAdjWalkways()
	{
		LinkedList<BoardCell> testList = board.getAdjList(0, 5);
		assertTrue(testList.contains(board.getCellAt(0, 6)));
		assertTrue(testList.contains(board.getCellAt(1, 5)));
		assertEquals(2, testList.size());

		testList = board.getAdjList(0, 6);
		assertTrue(testList.contains(board.getCellAt(1, 6)));
		assertTrue(testList.contains(board.getCellAt(0, 7)));
		assertEquals(2, testList.size());

		testList = board.getAdjList(6, 14);
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertTrue(testList.contains(board.getCellAt(5, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 14)));
		assertEquals(4, testList.size());

		testList = board.getAdjList(15, 9);
		assertTrue(testList.contains(board.getCellAt(15, 8)));
		assertTrue(testList.contains(board.getCellAt(14, 9)));
		assertTrue(testList.contains(board.getCellAt(15, 10)));
		assertTrue(testList.contains(board.getCellAt(16, 9)));
		assertEquals(4, testList.size());

		testList = board.getAdjList(21, 15);
		assertTrue(testList.contains(board.getCellAt(21, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertTrue(testList.contains(board.getCellAt(20, 17)));
		assertEquals(3, testList.size());

		testList = board.getAdjList(14, 22);
		assertTrue(testList.contains(board.getCellAt(13, 22)));
		assertEquals(1, testList.size());

		testList = board.getAdjList(26, 8);
		assertTrue(testList.contains(board.getCellAt(26, 7)));
		assertEquals(1, testList.size());
	}

	@Test
	public void testTargets1() {
		board.calcTargets(21, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));
		assertTrue(targets.contains(board.getCellAt(22, 7)));

		board.calcTargets(18, 3, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 2)));
		assertTrue(targets.contains(board.getCellAt(18, 4)));	
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(19, 3)));
	}

	@Test
	public void testTargets2() {
		board.calcTargets(21, 7, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 8)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		assertTrue(targets.contains(board.getCellAt(21, 5)));
		assertTrue(targets.contains(board.getCellAt(22, 6)));
		assertTrue(targets.contains(board.getCellAt(23, 7)));

		board.calcTargets(0, 14, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 1)));
		assertTrue(targets.contains(board.getCellAt(14, 2)));			
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
		board.calcTargets(17, 17, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 18)));
		assertTrue(targets.contains(board.getCellAt(18, 19)));
		assertTrue(targets.contains(board.getCellAt(19, 18)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 16)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));
		
		board.calcTargets(1, 13, 2);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 12)));
		assertTrue(targets.contains(board.getCellAt(2, 12)));
		assertTrue(targets.contains(board.getCellAt(2, 14)));
		assertTrue(targets.contains(board.getCellAt(1, 15)));
		assertTrue(targets.contains(board.getCellAt(0, 14)));
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
		board.calcTargets(3, 18, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		assertTrue(targets.contains(board.getCellAt(2, 19)));
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));
		
		board.calcTargets(23, 3, 2);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 5)));
		assertTrue(targets.contains(board.getCellAt(24, 4)));
		assertTrue(targets.contains(board.getCellAt(25, 3)));
		assertTrue(targets.contains(board.getCellAt(24, 2)));
		assertTrue(targets.contains(board.getCellAt(23, 1)));
		assertTrue(targets.contains(board.getCellAt(22, 2)));
		assertTrue(targets.contains(board.getCellAt(21, 3)));
		assertTrue(targets.contains(board.getCellAt(22, 4)));
	}

}
