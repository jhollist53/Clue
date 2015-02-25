package clueGame;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
public class CR_BoardAdjTargetTests {
	public static final String LAYOUT_FILE = "CR_ClueLayout.csv";
	public static final String LEGEND_FILE = "ClueLegend.txt";
	private static Board board;
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame(LAYOUT_FILE, LEGEND_FILE);
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}
	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		LinkedList<BoardCell> testList = board.getAdjList(0, 0);
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(0,4);
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(20, 15);
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(11, 18);
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(12, 14);
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(20, 5);
		Assert.assertEquals(0, testList.size());
	}
	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door
	// direction test.
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(6, 11);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(7, 11)));
		// TEST DOORWAY LEFT
		testList = board.getAdjList(17, 10);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(16, 10)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(15, 5);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(15, 6)));
		//TEST DOORWAY UP
		testList = board.getAdjList(15, 5);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(15, 6)));
	}
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		LinkedList<BoardCell> testList = board.getAdjList(4, 4);
		Assert.assertTrue(testList.contains(board.getCellAt(3, 4)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 4)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 5)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(15, 6);
		Assert.assertTrue(testList.contains(board.getCellAt(15, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(14, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(16, 6)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(17, 15);
		Assert.assertTrue(testList.contains(board.getCellAt(16, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(18, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(17, 14)));
		Assert.assertTrue(testList.contains(board.getCellAt(17, 16)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(11, 13);
		Assert.assertTrue(testList.contains(board.getCellAt(10, 13)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 13)));
		Assert.assertTrue(testList.contains(board.getCellAt(11, 12)));
		Assert.assertTrue(testList.contains(board.getCellAt(11, 14)));
		Assert.assertEquals(4, testList.size());
	}
	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		LinkedList<BoardCell> testList = board.getAdjList(4, 0);
		Assert.assertTrue(testList.contains(board.getCellAt(5, 0)));
		Assert.assertEquals(1, testList.size());
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(0, 6);
		Assert.assertTrue(testList.contains(board.getCellAt(0, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(1, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(0, 7)));
		Assert.assertEquals(3, testList.size());
		// Test between two rooms, walkways right and left
		testList = board.getAdjList(21, 6);
		Assert.assertTrue(testList.contains(board.getCellAt(20, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(22, 6)));
		Assert.assertEquals(2, testList.size());
		// Test surrounded by 4 walkways
		testList = board.getAdjList(7, 15);
		Assert.assertTrue(testList.contains(board.getCellAt(8, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(6, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 14)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 16)));
		Assert.assertEquals(4, testList.size());
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(15, 21);
		Assert.assertTrue(testList.contains(board.getCellAt(16, 21)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 20)));
		Assert.assertEquals(2, testList.size());
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(22, 14);
		Assert.assertTrue(testList.contains(board.getCellAt(21, 14)));
		Assert.assertTrue(testList.contains(board.getCellAt(22, 13)));
		Assert.assertEquals(2, testList.size());
		// Test on walkway next to door that is not in the needed
		// direction to enter
		testList = board.getAdjList(3, 5);
		Assert.assertTrue(testList.contains(board.getCellAt(2, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(3, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(3, 4)));
		Assert.assertEquals(4, testList.size());
	}
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(7, 21, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(7, 20)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 21)));
		board.calcTargets(0, 14, 1);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(1, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(0, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(0, 15)));
	}
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(7, 21, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(7, 19)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 20)));
		board.calcTargets(0, 14, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(0, 12)));
		Assert.assertTrue(targets.contains(board.getCellAt(2, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(1, 15)));
	}
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(7, 21, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(7, 17)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 19)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 18)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 20)));
		// Includes a path that doesn't have enough length
		board.calcTargets(0, 14, 4);
		targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(4, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(3, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(2, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(1, 15)));
	}
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(0, 14, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(6, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(3, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(4, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(2, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(4, 13)));
	}
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(16, 17, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		// directly left (can't go right 2 steps)
		Assert.assertTrue(targets.contains(board.getCellAt(14, 17)));
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCellAt(16, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 19)));
		// one up/down, one left/right
		Assert.assertTrue(targets.contains(board.getCellAt(17, 18)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 18)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 16)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 16)));
	}
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut()
	{
		board.calcTargets(7, 12, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(12, targets.size());
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCellAt(7, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 9)));
		// directly right (can't go left)
		Assert.assertTrue(targets.contains(board.getCellAt(10, 12)));
		// right then down
		Assert.assertTrue(targets.contains(board.getCellAt(9, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 13)));
		// down then left/right
		Assert.assertTrue(targets.contains(board.getCellAt(6, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(8, 14)));
		// right then up
		Assert.assertTrue(targets.contains(board.getCellAt(8, 10)));
		// into the rooms
		Assert.assertTrue(targets.contains(board.getCellAt(6, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 10)));
		//
		Assert.assertTrue(targets.contains(board.getCellAt(7, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(8, 12)));
	}
	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(20, 4, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(19, 4)));
		// Take two steps
		board.calcTargets(20, 4, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(19, 3)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 5)));
		Assert.assertTrue(targets.contains(board.getCellAt(18, 4)));
	}
}