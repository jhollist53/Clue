package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.Player;

public class SetupGameTests {
	
	private static Board board;
	private static ArrayList<Player> players;
	private static ArrayList<Card> deck;
	private static ClueGame game;
	
	@BeforeClass
	public static void setUp() {
		game = new ClueGame("cluelayout.csv", "LegendFile.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		players = game.getPlayers();
		deck = game.getDeck();
		board.calcAdjacencies();
	}

	@Test
	public void loadPlayersFromFileTest() {
		
		Player player = new Player("Mrs. Peacock", "Blue", 0, 7);
		assertTrue(players.contains(player));
		
		player = new Player("Mr. Green", "Green", 22, 10);
		assertTrue(players.contains(player));
		
		player = new Player("Ms. White", "White", 5, 0);
		assertTrue(players.contains(player));
		
	}
	
	@Test
	public void loadDeckFromFileTest() {
		
		assertEquals(21, deck.size());
		int weapons = 0, people = 0, rooms = 0;
		
		for( Card e: deck) {
			switch (e.getType()) {
				case WEAPON: 	weapons++;
								break;
				case PERSON:	people++;
								break;
				case ROOM:		rooms++;
								break;	
			}
		}
		
		assertEquals(6, weapons);
		assertEquals(6, people);
		assertEquals(9, rooms);
		
		Card card1 = new Card("Wrench", Card.cardType.WEAPON);
		assertTrue(deck.contains(card1));
		card1 = new Card("Professor Plum", Card.cardType.PERSON);
		assertTrue(deck.contains(card1));
		card1 = new Card("Conservatory", Card.cardType.ROOM);
		assertTrue(deck.contains(card1));

	}
	
	@Test
	public void dealCardsTest() {
		game.deal();
		
		assertTrue(deck.isEmpty());
		
		for (int j = 0; j < 6; j++) {
			for (Card c: players.get(j).getCards()) {
				for (int i = 0; i < 6; i++) {
					if (j != i) assertTrue(!players.get(i).getCards().contains(c));
				}
			}
		}
		
		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < 6; i++) {
				assertTrue(players.get(i).getCards().size() - players.get(j).getCards().size() <= 1);
			}
		}

	}
	
}
