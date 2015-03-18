package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.RoomCell;
import clueGame.Solution;

public class ActionGameTests {
	
	private static Board board;
	private static ClueGame game;
	private static Card mustardCard = new Card("Colonel Mustard", Card.cardType.PERSON);
	private static Card revolverCard = new Card("Revolver", Card.cardType.WEAPON);
	private static Card kitchenCard = new Card("Kitchen", Card.cardType.ROOM);
	private static Card scarletCard = new Card("Miss Scarlet", Card.cardType.PERSON);
	private static Card greenCard = new Card("Mr. Green", Card.cardType.PERSON);
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
		board.calcTargets(mustardPlayer.getStartCol(), mustardPlayer.getStartRow(), 4);
		
		for (BoardCell b: board.getTargets()) {
			System.out.println(b.getX()+ " " + b.getY());
		}
		
		for (int i = 0; i < 25; i++) {
			assertTrue(mustardPlayer.pickLocation(board.getTargets()) instanceof RoomCell);
		}
		
	}
	
	//This test checks to see that that a room will not be chosen if it was most recently visited.
	@Test
	public void checkRoomIsNotChosen() {
		board.calcTargets(mustardPlayer.getStartCol(), mustardPlayer.getStartRow(), 4);
		mustardPlayer.setLastRoomVisited('D');
		
		for (int i = 0; i < 25; i++) {
			assertFalse(mustardPlayer.pickLocation(board.getTargets()) instanceof RoomCell);
		}
	}
	
	//This test checks to ensure that when a room is not a valid choice and there are multiple options,
	//all options are chosen at a relatively equal rate.
	@Test
	public void checkRandomWalkway() {

		board.calcTargets(mustardPlayer.getStartCol(), mustardPlayer.getStartRow(), 1);
		
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
		board.calcTargets(mustardPlayer.getStartCol(), mustardPlayer.getStartRow(), 5);
		
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
	
	//This test will ensure that a single player will return one card if that player has just one card that matches the suggestion
	@Test
	public void testOneCardSuggestion() {
		HashSet<Card> specificDeal = new HashSet<Card>();
		specificDeal.add(mustardCard);
		specificDeal.add(revolverCard);
		specificDeal.add(kitchenCard);
		mustardPlayer.setCards(specificDeal);
		
		assertEquals(kitchenCard, mustardPlayer.disproveSuggestion(scarletCard, leadPipeCard, kitchenCard));
		assertEquals(revolverCard, mustardPlayer.disproveSuggestion(scarletCard, revolverCard, billiardRoomCard));
		assertEquals(mustardCard, mustardPlayer.disproveSuggestion(mustardCard, leadPipeCard, billiardRoomCard));
		assertEquals(null, mustardPlayer.disproveSuggestion(scarletCard, leadPipeCard, billiardRoomCard));
	}
	
	//This test will ensure that when multiple cards can be suggested that the the choice is at least semirandom
	@Test
	public void testMultipleCardSuggestion() {
		HashSet<Card> specificDeal = new HashSet<Card>();
		specificDeal.add(mustardCard);
		specificDeal.add(revolverCard);
		specificDeal.add(kitchenCard);
		mustardPlayer.setCards(specificDeal);
		
		int personTot = 0;
		int weaponTot = 0;
		int roomTot = 0;
		
		for (int i = 0; i < 100; i++) {
			Card returnCard = mustardPlayer.disproveSuggestion(mustardCard, revolverCard, kitchenCard);
			if (returnCard.equals(mustardCard))
				personTot++;
			else if (returnCard.equals(revolverCard))
				weaponTot++;
			else if (returnCard.equals(kitchenCard))
				roomTot++;
			else
				fail("Not possible to return otherwise");
		}
		
		assertEquals(100, personTot + weaponTot + roomTot);
		
		assertTrue(personTot > 10);
		assertTrue(weaponTot > 10);
		assertTrue(roomTot > 10);
	}
	
	//Ensure that the system will work for more than one player. All players except the calling player must be tested.
	@Test
	public void testMultiplePlayers () {
		HashSet<Card> specificDeal = new HashSet<Card>();
		HashSet<Card> specificDeal1 = new HashSet<Card>();
		HashSet<Card> specificDeal2 = new HashSet<Card>();
		HashSet<Card> specificDeal3 = new HashSet<Card>();
		
		specificDeal.add(mustardCard);
		specificDeal1.add(revolverCard);
		specificDeal2.add(kitchenCard);
		specificDeal3.add(scarletCard);
		
		HumanPlayer player1 =  new HumanPlayer();
		player1.setCards(specificDeal);
		
		ComputerPlayer player2 = new ComputerPlayer();
		player2.setCards(specificDeal1);
		
		ComputerPlayer player3 = new ComputerPlayer();
		player3.setCards(specificDeal2);
		
		ComputerPlayer player4 = new ComputerPlayer();
		player4.setCards(specificDeal3);
		
		HashSet<Player> playersList = new HashSet<Player>();
		playersList.add(player1);
		playersList.add(player2);
		playersList.add(player3);
		playersList.add(player4);
		
		game.setPlayers(playersList);
		
		//No person responds
		HashSet<Card> answerSet = new HashSet<Card>();
		assertEquals(answerSet, game.handleSuggestion(greenCard, billiardRoomCard, leadPipeCard, player1) );
		
		//Last person responds
		answerSet.add(scarletCard);
		assertEquals(answerSet, game.handleSuggestion(scarletCard, billiardRoomCard, leadPipeCard, player1));
		
		//Human player responds
		answerSet.clear();
		answerSet.add(mustardCard);
		assertEquals(answerSet, game.handleSuggestion(mustardCard, billiardRoomCard, leadPipeCard, player4));
		
		//Asking player does not have to reveal their own card.
		answerSet.clear();
		assertEquals(answerSet, game.handleSuggestion(mustardCard, billiardRoomCard, leadPipeCard, player1));
		
	}

}
