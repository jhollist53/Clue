package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.sun.prism.paint.Color;

public class ClueGame extends JFrame{
	private Map<Character, String> rooms;
	private String configFile, legendFile;
	private Board board;
	public static ArrayList<Player> players;
	private ArrayList<Card> deck;
	private Solution solution;
	
	public ClueGame(String configFile, String legendFile) {
		this.configFile = configFile;
		this.legendFile = legendFile;
		board = new Board();
		add(board,BorderLayout.CENTER);
		add(new ControlGUI(),BorderLayout.SOUTH);
		
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
		
		FileReader reader = new FileReader("playerConfig.txt");
		
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
		
		FileReader reader = new FileReader("cards.txt");
		
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
	
	public void selectAnswer() {}
	
	public HashSet<Card> handleSuggestion( Card person, Card room, Card weapon, Player accusingPlayer ) {
		HashSet<Card> revealed = new HashSet<Card>();
		
		for (Player p: players) {
			if (p != accusingPlayer) {
				revealed.add(p.disproveSuggestion(person, weapon, room));
			}
		}
		
		return revealed;
	}
	
	public boolean checkAccusation(Solution solutionPossible) { 
		
		return solutionPossible.getPerson().equals(solution.getPerson()) && solutionPossible.getRoom().equals(solution.getRoom()) &&
				solutionPossible.getWeapon().equals(solution.getWeapon());
	}

	//Used for testing purposes.
	public void setSolution(String person, String weapon, String room) {
		solution = new Solution(person, weapon, room );
	}
	
	public void setPlayers( Set<Player> players) {
		this.players.clear();
		
		for (Player e: players) {
			this.players.add(e);
		}
	}
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		game.setSize(Board.sqsize*Board.xDim+15, 6*Board.sqsize*Board.yDim/5+Board.sqsize-2);  
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.createFileMenu();
		game.setVisible(true);
	}
	
	private JMenu createFileMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File"); 
		menu.add(DetectiveNotes());
		menu.add(createFileExitItem());
		menuBar.add(menu);
		return menu;
	}
	
	private JMenuItem DetectiveNotes()
	{
	  JMenuItem item = new JMenuItem("Notes");
	  
	  JDialog notes = new JDialog();
	  notes.setDefaultCloseOperation(HIDE_ON_CLOSE);
	  notes.setLayout(new GridLayout(3,3));
	  notes.setBounds(1000, 200, 600, 600);
	  
	  JPanel people = new JPanel();
	  people.setLayout(new GridLayout(3,2));
	  people.add(new JRadioButton("Miss Scarlet"));
	  people.add(new JRadioButton("Mr. Green"));
	  people.add(new JRadioButton("Colonel Mustard"));
	  people.add(new JRadioButton("Mrs. Peacock"));
	  people.add(new JRadioButton("Mrs. White"));
	  people.add(new JRadioButton("Professor Plumb"));
	  people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
	  notes.add(people);
	  
	  JPanel rooms = new JPanel();
	  rooms.setLayout(new GridLayout(5,2));
	  rooms.add(new JRadioButton("Conservatory"));
	  rooms.add(new JRadioButton("Kitchen"));
	  rooms.add(new JRadioButton("Ballroom"));
	  rooms.add(new JRadioButton("Billiard Room"));
	  rooms.add(new JRadioButton("Library"));
	  rooms.add(new JRadioButton("Study"));
	  rooms.add(new JRadioButton("Lounge"));
	  rooms.add(new JRadioButton("Dining Room"));
	  rooms.add(new JRadioButton("Hall"));
	  rooms.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
	  notes.add(rooms);
	  
	  JPanel weapons = new JPanel();
	  weapons.setLayout(new GridLayout(3,2));
	  weapons.add(new JRadioButton("Wrench"));
	  weapons.add(new JRadioButton("Revolver"));
	  weapons.add(new JRadioButton("Candlestick"));
	  weapons.add(new JRadioButton("Lead Pipe"));
	  weapons.add(new JRadioButton("Rope"));
	  weapons.add(new JRadioButton("Knife"));
	  weapons.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
	  notes.add(weapons);
	  
	  JComboBox<String> persong = new JComboBox<String>();
	  persong.addItem("Miss Scarlet");
	  persong.addItem("Mr. Green");
	  persong.addItem("Colonel Mustard");
	  persong.addItem("Mrs. Peacock");
	  persong.addItem("Mrs. White");
	  persong.addItem("Professor Plumb");
	  persong.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
	  notes.add(persong);
	  
	  JComboBox<String> roomg = new JComboBox<String>();
	  roomg.addItem("Conservatory");
	  roomg.addItem("Kitchen");
	  roomg.addItem("Ballroom");
	  roomg.addItem("Billiard Room");
	  roomg.addItem("Library");
	  roomg.addItem("Study");
	  roomg.addItem("Lounge");
	  roomg.addItem("Dining Room");
	  roomg.addItem("Hall");
	  roomg.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
	  notes.add(roomg);
	  
	  JComboBox<String> weapg = new JComboBox<String>();
	  weapg.addItem("Wrench");
	  weapg.addItem("Revolver");
	  weapg.addItem("Candlestick");
	  weapg.addItem("Lead Pipe");
	  weapg.addItem("Rope");
	  weapg.addItem("Knife");
	  weapg.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
	  notes.add(weapg);
	  
	  
	  class MenuItemListener implements ActionListener {
	    public void actionPerformed(ActionEvent e)
	    {
	    	notes.setVisible(true);
	    }
	  }
	  item.addActionListener(new MenuItemListener());
	  return item;
	}
	
	
	
	
	
	private JMenuItem createFileExitItem()
	{
	  JMenuItem item = new JMenuItem("Exit");
	  class MenuItemListener implements ActionListener {
	    public void actionPerformed(ActionEvent e)
	    {
	       System.exit(0);
	    }
	  }
	  item.addActionListener(new MenuItemListener());
	  return item;
	}
	

	
	
}
