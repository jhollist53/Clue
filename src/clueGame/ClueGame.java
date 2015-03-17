package clueGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ClueGame {
	private Map<Character, String> rooms;
	private String configFile, legendFile;
	private Board board;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	
	public ClueGame(String configFile, String legendFile) {
		this.configFile = configFile;
		this.legendFile = legendFile;
		board = new Board();
		deck = new ArrayList<Card>();
		rooms = new HashMap<Character, String>();
		players = new ArrayList<Player>();
	}

	public Board getBoard () {
		return board;
	}
	
	public void loadConfigFiles () {
		try{
			loadLegend();
		} catch (BadConfigFormatException e){
			System.out.println(e);
		}
		board.loadBoardConfig(configFile, rooms);
		try {
			loadPlayers();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			loadCards();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void loadLegend() throws BadConfigFormatException{
		File file = new File(legendFile);
		try{
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			String line;
			while((line = bReader.readLine()) != null){
				//format is C, val
				rooms.put(line.charAt(0), line.substring(3, line.length()));
			}
			bReader.close();
		} catch (FileNotFoundException e){
			System.out.println("File " + legendFile + " not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	private void loadPlayers() throws FileNotFoundException {
		
		FileReader reader = null;
		reader = new FileReader("playerConfig.txt");
		
		Scanner s = new Scanner(reader);
		for(int i = 0; i < 6; i++) {
			String key = s.nextLine();
			Player player =  new Player(key, s.nextLine(), Integer.parseInt(s.nextLine()), Integer.parseInt(s.nextLine()));
			players.add(player);
		}
				
		s.close();
		
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	public void loadCards() throws FileNotFoundException {
		
		FileReader reader = null;
		reader = new FileReader("cards.txt");
		
		Scanner s = new Scanner(reader);
		int i = 0;
		for(; i < 6; i++) {
			deck.add( new Card(s.nextLine(), Card.cardType.WEAPON));
		}
		for(; i < 12; i++) {
			deck.add( new Card(s.nextLine(), Card.cardType.PERSON));
		}
		for(; i < 21; i++) {
			deck.add( new Card(s.nextLine(), Card.cardType.ROOM));
		}
				
		s.close();
	}
	
	public void deal() {
		ArrayList<HashSet<Card>> dealtCards = new ArrayList<HashSet<Card>>();
		int position1 = 0, position2 = 0;
		Random random = new Random();
		
		for (int i = 0; i < 6; i++) {
			dealtCards.add(new HashSet<Card>());
		}
		
		while(!deck.isEmpty()) {
			int dealtCard = random.nextInt(deck.size());
			dealtCards.get(position1 % 6).add(deck.get(dealtCard));
			deck.remove(dealtCard);
			position1++;
		}
		
		for (Player p: players) {
			p.setCards(dealtCards.get(position2));
			position2++;
		}
	}
	
	private void selectAnswer() {}
	
	private void handleSuggestion( String person, String room, String weapon, Player accusingPlayer ) {}
	
	private void checkAccusation(Solution solution) {}
	
	
}
