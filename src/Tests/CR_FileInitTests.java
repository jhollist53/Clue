package Tests;
// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.RoomCell;
import clueGame.RoomCell.DoorDirection;

public class CR_FileInitTests {
	// I made this static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("CR_ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// Test retrieving a few from the hash, including the first
		// and last in the file and a few others
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getyDim());
		assertEquals(NUM_COLUMNS, board.getxDim());		
	}
	
	// Test a doorway in each direction, plus two cells that are not
	// a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		// Test one each RIGHT/LEFT/UP/DOWN
		RoomCell room = board.getRoomCellAt(3, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(8, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(18, 15);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = board.getRoomCellAt(11, 14);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(14, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(6, 0);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		int totalCells = board.getyDim() * board.getxDim();
		assertEquals(506, totalCells);
		for (int row=0; row<board.getxDim(); row++)
			for (int col=0; col<board.getyDim(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway()) {
					numDoors++;
				}
			}
		assertEquals(16, numDoors);
	}
	

	// Test a few room cells to ensure the room initial is
	// correct.
	@Test
	public void testRoomInitials() {
		assertEquals('C', board.getRoomCellAt(0, 0).getInitial());
		assertEquals('R', board.getRoomCellAt(8, 4).getInitial());
		assertEquals('B', board.getRoomCellAt(0, 9).getInitial());
		assertEquals('O', board.getRoomCellAt(22, 21).getInitial());
		assertEquals('K', board.getRoomCellAt(0, 21).getInitial());
	}
	
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Game ctor takes config file names
		ClueGame game = new ClueGame("ClueLayoutBadColumns.csv", "ClueLegend.txt");
		// You may change these calls if needed to match your function names
		// My loadConfigFiles has a try/catch, so I can't call it directly to
		// see test throwing the BadConfigFormatException
		game.loadConfigFiles();
		Board board = game.getBoard();
		board.verifyBoard();
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		Board board = game.getBoard();
		board.VerifyBoard();
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegendBadFormat.txt");
		game.loadConfigFiles();
		Board board = game.getBoard();
		board.VerifyBoard();
	}
}
