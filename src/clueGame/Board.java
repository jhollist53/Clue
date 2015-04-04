package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;








import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.Arrays;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel implements MouseListener{
	public static final int sqsize = 35; 
	private ArrayList<ArrayList<BoardCell>> boardLayout;
	public static int xDim, yDim;
	private static Map<Character, String> rooms;
	private HashMap<BoardCell, LinkedList<BoardCell>> adjacencies;
	public static Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Graphics gr123;
	
	public Board() {
		
		
		boardLayout = new ArrayList<ArrayList<BoardCell>>();
		adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>();
		rooms = new HashMap<Character, String>(); 
		addMouseListener(this);
	}

	public void loadBoardConfig (String configFile, Map<Character, String> rooms) {

		
		readBoardFromFile(configFile);
		try{
			verifyBoard();
			//transposeBoard();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
		this.rooms = rooms;
		setSize(sqsize*xDim, sqsize*yDim); 
		
	}

	private void readBoardFromFile(String inFile){
		//Reads data from file, line by line.
		File file = new File(inFile);

		try {
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			String temp;
			
			
			
			while((temp = bReader.readLine()) != null){
				//Regular expression, matches any leading whitespace, trailing whitespace, then any other leading whitespace.
				boardLayout.add(new ArrayList<BoardCell>());
				for(String i : Arrays.asList(temp.split("\\s*,\\s*"))){
					if(i.charAt(0) == 'W'){
						boardLayout.get(boardLayout.size() - 1).add(
								//new WalkwayCell(boardLayout.size() - 1, boardLayout.get(boardLayout.size() - 1).size()));
								new WalkwayCell(boardLayout.get(boardLayout.size() - 1).size(), boardLayout.size() - 1));
					}
					else{
						DoorDirection dir = null;
						if(i.length() == 1){ dir = DoorDirection.NONE; }
						else{
							switch (i.charAt(1)) {
							case 'U': dir = DoorDirection.UP;
							break;
							case 'D': dir = DoorDirection.DOWN;
							break;
							case 'L': dir = DoorDirection.LEFT;
							break;
							case 'R': dir = DoorDirection.RIGHT;
							break;
							}	
						}
						boardLayout.get(boardLayout.size() - 1).add(
								//new RoomCell(boardLayout.size() - 1, boardLayout.get(boardLayout.size() - 1).size(), i.charAt(0), dir));
								new RoomCell(boardLayout.get(boardLayout.size() - 1).size(), boardLayout.size() - 1, i.charAt(0), dir));
					}
				}
			}
			bReader.close();
		} catch (FileNotFoundException e){
			System.out.println("File " + inFile + " not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void verifyBoard() throws BadConfigFormatException{
		//Board is not yet transposed, still in form (y,x)
		xDim = boardLayout.get(0).size();
		yDim = boardLayout.size();
		for(ArrayList<BoardCell> x : boardLayout){
			if (x.size() != xDim){ throw new BadConfigFormatException("Uneven row length detected.");}
		}
	}


	public ArrayList<ArrayList<BoardCell>> getBoardLayout() {
		return boardLayout;
	}

	public int getxDim() {
		return xDim;
	}

	public int getyDim() {
		return yDim;
	}

	public static Map<Character, String> getRooms() {
		return rooms;
	}

	public RoomCell getRoomCellAt(int x, int y) {
		if (boardLayout.get(y).get(x).isRoom()) {
			return (RoomCell) boardLayout.get(y).get(x);
		}
		return null;
	}

	public BoardCell getCellAt(int x, int y) {
		return boardLayout.get(y).get(x);
	}

	public void VerifyBoard() throws BadConfigFormatException{
		//Board is not yet transposed, still in form (y,x)
		xDim = boardLayout.get(0).size();
		yDim = boardLayout.size();
		for(ArrayList<BoardCell> x : boardLayout){
			if (x.size() != yDim){ throw new BadConfigFormatException("Uneven row length detected.");}
		}
	}
	public void calcAdjacencies() {
		for(int x = 0; x < xDim; x++){
			for(int y = 0; y < yDim; y++){
/*<<<<<<< HEAD
				LinkedList<BoardCell> ll = new LinkedList<BoardCell>();
				if(getCellAt(x,y).isWalkway()){
					if(y-1 >= 0){
						if(getCellAt(x,y-1).isWalkway() 
								|| getCellAt(x,y-1).isDoorway()){
							ll.addLast(getCellAt(x,y-1));
						}
					}
					if(x+1 < xDim){
						if(getCellAt(x+1,y).isWalkway() 
								|| getCellAt(x+1,y).isDoorway()){
							ll.addLast(getCellAt(x+1,y));
						}
					}
					if(y+1 < yDim){
						if(getCellAt(x,y+1).isWalkway() 
								|| getCellAt(x,y+1).isDoorway()){
							ll.addLast(getCellAt(x,y+1));
						}
					}
					if(x-1 >= 0){
						if(getCellAt(x-1,y).isWalkway() 
								|| getCellAt(x-1,y).isDoorway()){
							ll.addLast(getCellAt(x-1,y));
						}
					}
				}
				else if(getCellAt(x,y).isDoorway()){
					if(y-1 >= 0){
						if(getCellAt(x,y-1).isWalkway()) { 
							ll.addLast(getCellAt(x,y-1));
						}
					}
					if(x+1 < xDim){
						if(getCellAt(x+1,y).isWalkway()) {
							ll.addLast(getCellAt(x+1,y));
						}
					}
					if(y+1 < yDim){
						if(getCellAt(x,y+1).isWalkway()) {
							ll.addLast(getCellAt(x,y+1));
						}
					}
					if(x-1 >= 0){
						if(getCellAt(x-1,y).isWalkway()) {
							ll.addLast(getCellAt(x-1,y));
						}
					}
				}
				adjacencies.put(getCellAt(x,y), ll);
=======*/
				LinkedList<BoardCell> adjacencyList = new LinkedList<BoardCell>();
				if(getCellAt(x,y).isWalkway()){
					checkAdjWalkways(x,y,adjacencyList);
				}
				else if(getCellAt(x,y).isDoorway()){
					checkAdjDoorways(x,y,adjacencyList);
				}
				adjacencies.put(getCellAt(x,y), adjacencyList);
//>>>>>>> a9b2d340d3b13dc408550723e2bc17ba4021dc9e
			}
		}
	}

	private LinkedList<BoardCell> checkAdjDoorways(int x, int y, LinkedList<BoardCell> adjacencyList) {
		
		if(y-1 >= 0){
			if(getCellAt(x,y-1).isWalkway()) { 
				adjacencyList.addLast(getCellAt(x,y-1));
			}
		}
		if(x+1 < xDim){
			if(getCellAt(x+1,y).isWalkway()) {
				adjacencyList.addLast(getCellAt(x+1,y));
			}
		}
		if(y+1 < yDim){
			if(getCellAt(x,y+1).isWalkway()) {
				adjacencyList.addLast(getCellAt(x,y+1));
			}
		}
		if(x-1 >= 0){
			if(getCellAt(x-1,y).isWalkway()) {
				adjacencyList.addLast(getCellAt(x-1,y));
			}
		}
		return adjacencyList;

	}

	private LinkedList<BoardCell> checkAdjWalkways(int x, int y,
			LinkedList<BoardCell> adjacencyList) {
		if(y-1 >= 0){
			if(getCellAt(x,y-1).isWalkway() 
					|| getCellAt(x,y-1).isDoorway()){
				adjacencyList.addLast(getCellAt(x,y-1));
			}
		}
		if(x+1 < xDim){
			if(getCellAt(x+1,y).isWalkway() 
					|| getCellAt(x+1,y).isDoorway()){
				adjacencyList.addLast(getCellAt(x+1,y));
			}
		}
		if(y+1 < yDim){
			if(getCellAt(x,y+1).isWalkway() 
					|| getCellAt(x,y+1).isDoorway()){
				adjacencyList.addLast(getCellAt(x,y+1));
			}
		}
		if(x-1 >= 0){
			if(getCellAt(x-1,y).isWalkway() 
					|| getCellAt(x-1,y).isDoorway()){
				adjacencyList.addLast(getCellAt(x-1,y));
			}
		}
		return adjacencyList;
		
	}

	public LinkedList<BoardCell> getAdjList(int x, int y) {
		return adjacencies.get(getCellAt(x,y));
	}

	public void calcTargets(int x, int y, int len) {
		BoardCell cell = this.getCellAt(x, y);
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(cell);
		findAllTargets(cell, len);
		if(targets.contains(cell)){
			targets.remove(cell);
		}
	}
	
	private void findAllTargets(BoardCell startCell, int length){
		visited.add(startCell);
		for(BoardCell cell : adjacencies.get(startCell)){
			if (!visited.contains(cell) && cell.isDoorway())
				targets.add(cell);
			if(length == 1){
				if(!visited.contains(cell)){
					targets.add(cell);
					}
			}
			else {
				findAllTargets(cell, length - 1);
			}
			visited.remove(cell);
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public void startTarget( int row, int col, int numSteps) {}
	
	public void paintComponent(Graphics g){
		gr123=g;
		super.paintComponent(g);
		for(ArrayList<BoardCell> a : boardLayout){
			for(BoardCell b:a){
				b.draw(g);
			}
		}
		
		
		
		for (Player a: ClueGame.players){
			a.draw(g);
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {
		if (targets!= null && ClueGame.getCurrentPLayer().isHuman()){
			BoardCell cell = null;
			for (ArrayList<BoardCell> arr : boardLayout) {
				for (BoardCell Tcell : arr){
					if (Tcell.containsClick(e.getX(), e.getY())) {
						cell = Tcell;
						break;
					}
				}
			}
			
			// display some information just to show whether a box was clicked
			if (targets.contains(cell)) {
				ClueGame.getCurrentPLayer().startCol = cell.x;
				ClueGame.getCurrentPLayer().startRow = cell.y;
				if (cell instanceof RoomCell){
					suggestionPrompt((RoomCell)cell);
				}else{
					ClueGame.turnFinished = true;
				}
				targets = null;
				repaint();
			}
			else{
				JDialog error = new JDialog();
				error.setDefaultCloseOperation(error.DISPOSE_ON_CLOSE);
				error.setLayout(new GridLayout(1,1));
				error.setBounds(e.getX(), e.getY(), 150, 60);
				error.setVisible(true);
				error.setTitle("ERROR");
				JTextPane message = new JTextPane();
				message.setEditable(false);
				message.setText("NOT A VALID SQUARE");
				error.add(message);
			}
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e)  {}
	
	public void suggestionPrompt(RoomCell p){
		JDialog sugg = new JDialog();
		sugg.setDefaultCloseOperation(sugg.DO_NOTHING_ON_CLOSE);
		sugg.setLayout(new GridLayout(1,3));
		sugg.setBounds(200, 200, 600, 250);
		sugg.setTitle("Make a Suggestion!");
		
		JComboBox<String> persong = new JComboBox<String>();
		persong.addItem("Miss Scarlet");
		persong.addItem("Mr. Green");
		persong.addItem("Colonel Mustard");
		persong.addItem("Mrs. Peacock");
		persong.addItem("Mrs. White");
		persong.addItem("Professor Plumb");
		persong.setBorder(new TitledBorder (new EtchedBorder(), "Person: "));
		sugg.add(persong);
		
		JComboBox<String> weapg = new JComboBox<String>();
		weapg.addItem("Wrench");
		weapg.addItem("Revolver");
		weapg.addItem("Candlestick");
		weapg.addItem("Lead Pipe");
		weapg.addItem("Rope");
		weapg.addItem("Knife");
		weapg.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
	  	sugg.add(weapg);
	  	
	  	JButton confirm = new JButton("Confirm!");
	  	sugg.add(confirm);
	  	
		class NextButton implements ActionListener {
		    public void actionPerformed(ActionEvent e)
		    {
		    	ArrayList<Card> suggestion = new ArrayList<Card>();
		    	suggestion.add(new Card(p.getName(), Card.cardType.ROOM));
		    	suggestion.add(new Card((String) weapg.getSelectedItem(), Card.cardType.WEAPON));
		    	suggestion.add(new Card((String) persong.getSelectedItem(), Card.cardType.PERSON));
		    	
		    	
		    	ClueGame.ctrlpanel.suggestout(suggestion);
				ClueGame.checkSuggestion(suggestion);
		    	persong.getSelectedItem();
		    	
		    	sugg.dispose();
		    	ClueGame.turnFinished = true;
		    }
		}
		confirm.addActionListener(new NextButton());
		sugg.setVisible(true);
		
		
	}
}


