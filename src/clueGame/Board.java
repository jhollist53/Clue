package clueGame;

import java.util.*;

public class Board {
	private ArrayList<ArrayList<BoardCell>> boardLayout;
	private int numRows, numColumns;
	private Map<Character, String> rooms;
	
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

}
