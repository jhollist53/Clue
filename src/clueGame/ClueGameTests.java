package clueGame;

import static org.junit.Assert.*;

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
		assertEquals(NUM_ROOMS, 0);
	}

}
