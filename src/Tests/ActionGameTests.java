package Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ClueGame;

public class ActionGameTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("cluelayout.csv", "LegendFile.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
