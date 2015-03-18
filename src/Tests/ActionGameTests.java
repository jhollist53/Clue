package Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.RoomCell;
import clueGame.Solution;

public class ActionGameTests {
	
	private static Board board;
	private static ClueGame game;
	private static Card mustardCard = new Card("Colonel Mustard", Card.cardType.PERSON);
	private static Card revolverCard = new Card("Revolver", Card.cardType.WEAPON);
	private static Card kitchenCard = new Card("Kitchen", Card.cardType.ROOM);
	private static Card scarletCard = new Card("Miss Scarlet", Card.cardType.PERSON);
	private static Card leadPipeCard =  new Card("Lead Pipe", Card.cardType.WEAPON);
	private static Card billiardRoomCard =  new Card("Billiard Room", Card.cardType.ROOM);
	private static ComputerPlayer mustardPlayer = new ComputerPlayer("Colonel Mustard", "Yellow", 0, 17);
	
	//Set up board to prepare for tests.
	@BeforeClass
	public static void setUp() {
		game = new ClueGame("cluelayout.csv", "LegendFile.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	//This test covers all possible passing and failing cases of the checkAccusation function.
	@Test
	public void checkAccusationTest() {
		//Set solution to check against.
		game.setSolution(mustardCard.getName(), revolverCard.getName(), kitchenCard.getName());
		
		//Check that valid solution evaluates correctly.
		Solution testSolution = new Solution(mustardCard.getName(), revolverCard.getName(), kitchenCard.getName());
		assertTrue(game.checkAccusation(testSolution));
		
		//Incorrect person should fail.
		testSolution = new Solution(scarletCard.getName(), revolverCard.getName(), kitchenCard.getName());
		assertFalse(game.checkAccusation(testSolution));
		
		//Incorrect room should fail.
		testSolution = new Solution(mustardCard.getName(), revolverCard.getName(), billiardRoomCard.getName());
		assertFalse(game.checkAccusation(testSolution));
		
		//Incorrect weapon should fail.
		testSolution = new Solution(mustardCard.getName(), leadPipeCard.getName(), kitchenCard.getName());
		assertFalse(game.checkAccusation(testSolution));
	}
	
	//This test checks to see that if a room is a choice then it will be chosen.
	@Test
	public void checkRoomIsChosen() {
		board.calcTargets(mustardPlayer.getStartRow(), mustardPlayer.getStartRow(), 3);
		
		for (int i = 0; i < 25; i++) {
			assertTrue(mustardPlayer.pickLocation(board.getTargets()) instanceof RoomCell);
		}
		
	}
	
	//This test checks to see that that a room will not be chosen if it was most recently visited.
	@Test
	public void checkRoomIsNotChosen() {
		board.calcTargets(mustardPlayer.getStartRow(), mustardPlayer.getStartRow(), 3);
		mustardPlayer.setLastRoomVisited('D');
		
		for (int i = 0; i < 25; i++) {
			assertFalse(mustardPlayer.pickLocation(board.getTargets()) instanceof RoomCell);
		}
	}
	
	//This test checks to ensure that when a room is not a valid choice and there are multiple options,
	//all options are chosen at a relatively equal rate.
	@Test
	public void checkRandomWalkway() {

		board.calcTargets(mustardPlayer.getStartRow(), mustardPlayer.getStartRow(), 1);
		
		int loc_0_16Tot = 0;
		int loc_1_17Tot = 0;
		int loc_0_18Tot = 0;

		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = mustardPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(16, 0))
				loc_0_16Tot++;
			else if (selected == board.getCellAt(17, 1))
				loc_1_17Tot++;
			else if (selected == board.getCellAt(18, 0))
				loc_0_18Tot++;
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, loc_0_16Tot + loc_1_17Tot + loc_0_18Tot);
		// Ensure each target was selected more than once
		assertTrue(loc_0_16Tot > 10);
		assertTrue(loc_1_17Tot > 10);
		assertTrue(loc_0_18Tot > 10);							
	}
	
	//This test checks to ensure that when multiple rooms are valid choices
	//They are chosen at a relatively equal rate.
	@Test
	public void checkRandomRoom() {
		board.calcTargets(mustardPlayer.getStartRow(), mustardPlayer.getStartRow(), 4);
		
		int dTotal = 0;
		int kTotal = 0;

		// Run the test 100 times
		for (int i=0; i<100; i++) {
			BoardCell selected = mustardPlayer.pickLocation(board.getTargets());
			if (selected instanceof RoomCell) {
				if ( ((RoomCell) selected).getInitial() == 'K')
					kTotal++; 
				else if (((RoomCell) selected).getInitial() == 'D')
					dTotal++;
			}
			else
				fail("Invalid target selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, dTotal + kTotal);
		// Ensure each target was selected more than once
		assertTrue(kTotal > 25);
		assertTrue(dTotal > 25);
	
	}

}
