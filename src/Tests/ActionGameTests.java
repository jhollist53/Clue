package Tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ClueGame;
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
	
	//Set up board to prepare for tests.
	@BeforeClass
	public static void setUp() {
		game = new ClueGame("cluelayout.csv", "LegendFile.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

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

}
