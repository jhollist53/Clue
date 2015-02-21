package clueGame;

import java.util.*;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private ArrayList<ArrayList<BoardCell>> boardLayout;
	private int xDim, yDim;
	private Map<Character, String> rooms = new HashMap<Character, String>();

	Board() {

	}

	public void loadBoardConfig (String configFile) {
		Scanner scanner = new Scanner(configFile);
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
		return new RoomCell();
	}

	public BoardCell getCellAt(int x, int y) {
		return boardLayout.get(x).get(y);
	}

}
