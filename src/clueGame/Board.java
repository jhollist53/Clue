package clueGame;

import java.io.*;
import java.util.*;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<ArrayList<BoardCell>> boardLayout;
	private int xDim, yDim;
	private Map<Character, String> rooms;
	private HashMap<BoardCell, LinkedList<BoardCell>> adjacencies;
	
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
								new WalkwayCell(boardLayout.size() - 1, boardLayout.get(boardLayout.size() - 1).size() - 1));
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
								new RoomCell(boardLayout.size() - 1, boardLayout.get(boardLayout.size() - 1).size() - 1, i.charAt(0), dir));
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

	private void transposeBoard(){
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
		//BoardCell cell = null;
		for(int x = 0; x < xDim; x++){
			for(int y = 0; y < yDim; y++){
				LinkedList<BoardCell> ll = new LinkedList<BoardCell>();
				if(boardLayout.get(x).get(y).isWalkway() || boardLayout.get(x).get(y).isDoorway()){
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
				adjacencies.put(boardLayout.get(x).get(y), ll);
			}
		}
	}

	public LinkedList<BoardCell> getAdjList(int x, int y) {
		return adjacencies.get(boardLayout.get(x).get(y));
	}

	public void calcTargets(int i, int j, int k) {
		// TODO Auto-generated method stub

	}

	public Set<BoardCell> getTargets() {
		Set<BoardCell> set = new HashSet<BoardCell>();
		return set;
	}


}


