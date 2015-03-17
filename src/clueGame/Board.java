package clueGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<ArrayList<BoardCell>> boardLayout;
	private int xDim, yDim;
	private Map<Character, String> rooms;
	private HashMap<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	
	public Board() {
		boardLayout = new ArrayList<ArrayList<BoardCell>>();
		adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>();
		rooms = new HashMap<Character, String>();
	}

	public void loadBoardConfig (String configFile, Map<Character, String> rooms) {
		readBoardFromFile(configFile);
		try{
			verifyBoard();
			transposeBoard();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
		this.rooms = rooms;
	}
	/*
	public void testLoadBoardConfig(String configFile) throws BadConfigFormatException{
		readBoardFromFile(configFile);
		verifyBoard();
	}*/

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

	public void transposeBoard(){
		ArrayList<ArrayList<BoardCell>> temp = new ArrayList<ArrayList<BoardCell>>();
		for (int i = 0; i < xDim; i++){
			ArrayList<BoardCell> temp2 = new ArrayList<BoardCell>();
			for (int j = 0; j < yDim; j ++){
				temp2.add(boardLayout.get(j).get(i));
			}
			temp.add(temp2);
		}
		boardLayout = temp;
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

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public RoomCell getRoomCellAt(int x, int y) {
		if (boardLayout.get(x).get(y).isRoom()) {
			return (RoomCell) boardLayout.get(x).get(y);
		}
		return null;
	}

	public BoardCell getCellAt(int x, int y) {
		return boardLayout.get(x).get(y);
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
		//This has turned into total software gore...
		for(int x = 0; x < xDim; x++){
			for(int y = 0; y < yDim; y++){
				LinkedList<BoardCell> ll = new LinkedList<BoardCell>();
				if(boardLayout.get(x).get(y).isWalkway()){
					if(y-1 >= 0){
						if(boardLayout.get(x).get(y-1).isWalkway() 
								|| boardLayout.get(x).get(y-1).isDoorway()){
							ll.addLast(boardLayout.get(x).get(y-1));
						}
					}
					if(x+1 < xDim){
						if(boardLayout.get(x+1).get(y).isWalkway() 
								|| boardLayout.get(x+1).get(y).isDoorway()){
							ll.addLast(boardLayout.get(x+1).get(y));
						}
					}
					if(y+1 < yDim){
						if(boardLayout.get(x).get(y+1).isWalkway() 
								|| boardLayout.get(x).get(y+1).isDoorway()){
							ll.addLast(boardLayout.get(x).get(y+1));
						}
					}
					if(x-1 >= 0){
						if(boardLayout.get(x-1).get(y).isWalkway() 
								|| boardLayout.get(x-1).get(y).isDoorway()){
							ll.addLast(boardLayout.get(x-1).get(y));
						}
					}
				}
				else if(boardLayout.get(x).get(y).isDoorway()){
					if(y-1 >= 0){
						if(boardLayout.get(x).get(y-1).isWalkway()) { 
							ll.addLast(boardLayout.get(x).get(y-1));
						}
					}
					if(x+1 < xDim){
						if(boardLayout.get(x+1).get(y).isWalkway()) {
							ll.addLast(boardLayout.get(x+1).get(y));
						}
					}
					if(y+1 < yDim){
						if(boardLayout.get(x).get(y+1).isWalkway()) {
							ll.addLast(boardLayout.get(x).get(y+1));
						}
					}
					if(x-1 >= 0){
						if(boardLayout.get(x-1).get(y).isWalkway()) {
							ll.addLast(boardLayout.get(x-1).get(y));
						}
					}
				}
				adjacencies.put(boardLayout.get(x).get(y), ll);
			}
		}
	}

	public LinkedList<BoardCell> getAdjList(int x, int y) {
		return adjacencies.get(boardLayout.get(x).get(y));
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


}


