package clueGame;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.RoomCell.DoorDirection;

public class ClueGameTests {

	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLS = 27;
	public static final String LAYOUT_FILE = "ClueLayout.csv";
	public static final String LEGEND_FILE = "LegendFile.txt";
	public static final String LAYOUT_FILE_BAD_SIZE = "ClueLayoutBadColumns.csv";
	public static final String LAYOUT_FILE_BAD_ROOM = "ClueLayoutBadRoom.csv";
	public static final String LEGEND_FILE_BAD_FORMAT = "ClueLegendBadFormat.txt";
	
	@BeforeClass
	public static void setUp () {
		ClueGame game = new ClueGame(LAYOUT_FILE, LEGEND_FILE);
		game.loadConfigFiles();
		board = game.getBoard();
	}

	@Test
	public void roomNum() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
	}

	@Test
	public void roomMapping() {
		Map<Character, String> rooms = board.getRooms();
		assertEquals("Bathroom", rooms.get('B'));
		assertEquals("Study", rooms.get('S'));
		assertEquals("Closet", rooms.get('X'));
		assertEquals("Walkway", rooms.get('W'));		
	}

	@Test
	public void boardSize() {
		assertEquals(NUM_COLS, board.getxDim());
		assertEquals(NUM_ROWS, board.getyDim());
	}

	@Test
	public void doorDirections() {
		RoomCell room = board.getRoomCellAt(4, 1);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());

		room = board.getRoomCellAt(9, 2);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());

		room = board.getRoomCellAt(23, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());

		room = board.getRoomCellAt(17, 16);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());

		room = board.getRoomCellAt(0, 0);
		System.out.println(room.isDoorway());
		assertFalse(room.isDoorway());	

		BoardCell cell = board.getCellAt(26, 16);
		assertFalse(cell.isDoorway());
	}

	@Test
	public void numDoors() 
	{
		int numDoors = 0;
		int totalCells = board.getxDim() * board.getyDim();
		assertEquals(621, totalCells);
		for (int i = 0; i < board.getxDim(); i++)
			for (int j=0; j< board.getyDim(); j++) {
				BoardCell cell = board.getCellAt(i, j);
				if (cell.isWalkway()) {
					
				}
				else if (((RoomCell)cell).getDoorDirection() != DoorDirection.NONE)
					numDoors++;
			}
		assertEquals(36, numDoors);
	}
	
	@Test
	public void roomInitials() {
		assertEquals('B', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('S', board.getRoomCellAt(7, 22).getInitial());
		assertEquals('L', board.getRoomCellAt(0, 22).getInitial());
		assertEquals('C', board.getRoomCellAt(24, 11).getInitial());
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException {
		ClueGame gameBad = new ClueGame(LAYOUT_FILE_BAD_SIZE, LEGEND_FILE);
		testException(gameBad);
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException {
		ClueGame gameBad = new ClueGame(LAYOUT_FILE_BAD_ROOM, LEGEND_FILE);
		testException(gameBad);
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException {
		ClueGame gameBad = new ClueGame(LAYOUT_FILE, LEGEND_FILE_BAD_FORMAT);
		testException(gameBad);
	}
	
	public void testException(ClueGame g) throws BadConfigFormatException{
		g.loadConfigFiles();
		g.getBoard().verifyBoard();
	}
}
