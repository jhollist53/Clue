package clueGame;

import java.util.*;

public class Board {
	private ArrayList<ArrayList<BoardCell>> boardLayout;
	private int numRows, numColumns;
	private Map<Character, String> rooms = new HashMap<Character, String>();
	
	Board() {
		
	}
	
	public void loadBoardConfig () {
		
	}

	public ArrayList<ArrayList<BoardCell>> getBoardLayout() {
		return boardLayout;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public RoomCell getRoomCellAt(int row, int col) {
		RoomCell roomCell = new RoomCell();
		return roomCell;
	}

	public BoardCell getCellAt(int row, int col) {
		BoardCell boardCell = new WalkwayCell();
		return boardCell;
	}

}
