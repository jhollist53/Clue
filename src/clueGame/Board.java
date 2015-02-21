package clueGame;

import java.util.*;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<ArrayList<BoardCell>> boardLayout;
	private int xDim, yDim;
	private Map<Character, String> rooms;

	public Board() {
		boardLayout = new ArrayList<ArrayList<BoardCell>>();
		rooms = new HashMap<Character, String>();
	}

	public void loadBoardConfig (String configFile) {
		readBoardFromFile(configFile);
		try{
			verifyBoard();
		} catch (BadConfigFormatException e) {
			System.out.println(e);
		}
		transposeBoard();
	}
	
	private void readBoardFromFile(String file){
		//Reads data from file, line by line.
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()){
			//Regular expression, matches any leading whitespace, trailing whitespace, then any other leading whitespace.
			List<String> temp = Arrays.asList(scanner.nextLine().split("\\s*,\\s*"));
			boardLayout.add(new ArrayList<BoardCell>());
			for(String i : temp){
				if(i.charAt(0) == 'W'){
					boardLayout.get(boardLayout.size() - 1).add(
							new WalkwayCell(boardLayout.size() -1, boardLayout.get(boardLayout.size()).size()));
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
					boardLayout.get(boardLayout.size()).add(
							new RoomCell(boardLayout.size() - 1, boardLayout.get(boardLayout.size()).size() - 1, i.charAt(0), dir));
				}
			}
		}
		scanner.close();
	}
	
	private void verifyBoard() throws BadConfigFormatException{
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

}
