package Tests;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
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
		testList = board.getAdjList(18, 13);
		assertEquals(0, testList.size());
		testList = board.getAdjList(22, 11);
		assertEquals(0, testList.size());
		testList = board.getAdjList(6, 20);
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
		assertTrue(testList.contains(board.getCellAt(0, 5)));
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
		assertTrue(testList.contains(board.getCellAt(22, 15)));
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
		board.calcTargets(0, 5, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 6)));
		assertTrue(targets.contains(board.getCellAt(2, 5)));

		board.calcTargets(0, 13, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 13)));
		assertTrue(targets.contains(board.getCellAt(1, 14)));			
		assertTrue(targets.contains(board.getCellAt(1, 12)));
	}
	@Test
	public void testTargets4() {
		board.calcTargets(17, 22, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 21)));
		assertTrue(targets.contains(board.getCellAt(17, 20)));
		assertTrue(targets.contains(board.getCellAt(18, 19)));
		assertTrue(targets.contains(board.getCellAt(17, 18)));
		board.calcTargets(18, 6, 4);
		targets= board.getTargets();
		assertEquals(22, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		assertTrue(targets.contains(board.getCellAt(16, 4)));
		assertTrue(targets.contains(board.getCellAt(16, 6)));
		assertTrue(targets.contains(board.getCellAt(16, 8)));
		assertTrue(targets.contains(board.getCellAt(17, 3)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 7)));
		assertTrue(targets.contains(board.getCellAt(17, 9)));
		assertTrue(targets.contains(board.getCellAt(18, 2)));
		assertTrue(targets.contains(board.getCellAt(18, 4)));
		assertTrue(targets.contains(board.getCellAt(18, 8)));
		assertTrue(targets.contains(board.getCellAt(18, 10)));
		assertTrue(targets.contains(board.getCellAt(19, 3)));
		assertTrue(targets.contains(board.getCellAt(19, 5)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 9)));
		assertTrue(targets.contains(board.getCellAt(20, 4)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 8)));
		assertTrue(targets.contains(board.getCellAt(21, 5)));
		assertTrue(targets.contains(board.getCellAt(21, 7)));
		assertTrue(targets.contains(board.getCellAt(22, 6)));
	}
	@Test
	public void testTargets6() {
		board.calcTargets(2, 22, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 18)));
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		assertTrue(targets.contains(board.getCellAt(4, 22)));
		assertTrue(targets.contains(board.getCellAt(3, 21)));
		assertTrue(targets.contains(board.getCellAt(5, 21)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
	}
	@Test
	public void testTargetsEnter()
	{
		board.calcTargets(6, 1, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 0)));
		assertTrue(targets.contains(board.getCellAt(6, 3)));
		assertTrue(targets.contains(board.getCellAt(5, 2)));
		assertTrue(targets.contains(board.getCellAt(7, 2)));
		assertTrue(targets.contains(board.getCellAt(4, 1)));
		assertTrue(targets.contains(board.getCellAt(8, 1)));
		assertTrue(targets.contains(board.getCellAt(5, 0)));

		board.calcTargets(1, 13, 2);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 12)));
		assertTrue(targets.contains(board.getCellAt(2, 12)));
		assertTrue(targets.contains(board.getCellAt(2, 14)));
		assertTrue(targets.contains(board.getCellAt(1, 14)));
		assertTrue(targets.contains(board.getCellAt(3, 13)));
	}
	@Test
	public void testTargetsShortenTurn()
	{
		board.calcTargets(7, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		assertTrue(targets.contains(board.getCellAt(8, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 8)));
		assertTrue(targets.contains(board.getCellAt(8, 9)));
		assertTrue(targets.contains(board.getCellAt(7, 10)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
	}
	@Test
	public void testRoomExit()
	{
		board.calcTargets(3, 18, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 18)));
		board.calcTargets(23, 3, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 5)));
		assertTrue(targets.contains(board.getCellAt(24, 4)));
		assertTrue(targets.contains(board.getCellAt(22, 4)));
		assertTrue(targets.contains(board.getCellAt(24, 4)));
		assertTrue(targets.contains(board.getCellAt(23, 5)));
	}
}
