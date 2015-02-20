package clueGame;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class ClueGameTests {

	private static Board board;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLS = 27;

	@BeforeClass
	public static void setUp () {
		ClueGame game = new ClueGame("ClueLayout", "LegendFile");
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
		assertEquals(NUM_COLS, board.getNumColumns());
		assertEquals(NUM_ROWS, board.getNumRows());
	}

	@Test
	public void doorDirections() {
		RoomCell room = board.getRoomCellAt(1, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());

		room = board.getRoomCellAt(2, 9);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());

		room = board.getRoomCellAt(8, 23);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());

		room = board.getRoomCellAt(16, 17);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());

		room = board.getRoomCellAt(0, 0);
		assertFalse(room.isDoorway());	

		BoardCell cell = board.getCellAt(16, 26);
		assertFalse(cell.isDoorway());
	}

	@Test
	public void numDoors() 
	{
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		assertEquals(621, totalCells);
		for (int i = 0; i < board.getNumRows(); i++)
			for (int j=0; j<board.getNumColumns(); j++) {
				BoardCell cell = board.getCellAt(i, j);
				if (cell.isDoorway())
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
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		ClueGame game = new ClueGame("ClueLayoutBadSize", "LegendFile");
		game.loadConfigFiles();
		game.getBoard().loadBoardConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		ClueGame game = new ClueGame("ClueLayoutBadRoom", "LegendFile");
		game.loadConfigFiles();
		game.getBoard().loadBoardConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		ClueGame game = new ClueGame("ClueLayout", "LegendBadFormat");
		game.loadConfigFiles();
		game.getBoard().loadBoardConfig();
	}
}
