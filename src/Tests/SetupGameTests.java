package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Card.cardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class SetupGameTests {
	
	private static Board board;
	private static Map<String, Player> players;
	private static ArrayList<Card> deck;
	
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("cluelayout.csv", "LegendFile.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		players = game.getPlayers();
		deck = game.getDeck();
		board.calcAdjacencies();
	}

	@Test
	public void loadPlayersFromFileTest() {
		
		Player player = players.get("JaJim");
		assertTrue(player.getName().equals("JaJim"));
		assertTrue(player.getColor().equals("Blue"));
		assertEquals(0, player.getStartRow());
		assertEquals(7, player.getStartCol());
		
		player = players.get("JaBob");
		assertTrue(player.getName().equals("JaBob"));
		assertTrue(player.getColor().equals("Green"));
		assertEquals(22, player.getStartRow());
		assertEquals(10, player.getStartCol());
		
		player = players.get("JaRichard");
		assertTrue(player.getName().equals("JaRichard"));
		assertTrue(player.getColor().equals("Brown"));
		assertEquals(5, player.getStartRow());
		assertEquals(0, player.getStartCol());
		
	}
	
	@Test
	public void loadDeckFromFileTest() {
		
		assertEquals(27, deck.size());
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
		
		assertEquals(9, weapons);
		assertEquals(9, people);
		assertEquals(9, rooms);
		
		Card card1 = new Card("Katana", Card.cardType.WEAPON);
		assertTrue(deck.contains(card1));
		card1 = new Card("JaJim", Card.cardType.PERSON);
		assertTrue(deck.contains(card1));
		card1 = new Card("Conservatory", Card.cardType.ROOM);
		assertTrue(deck.contains(card1));

	}
	
}
