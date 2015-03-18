package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, String color, int startCol, int startRow) {
		super(name, color, startCol, startRow);
		lastRoomVisited = 'X';
	}
	
	public ComputerPlayer(String name, String color, int startCol, int startRow, char lastRoom) {
		super(name, color, startCol, startRow);
		lastRoomVisited = lastRoom;
	}

	private char lastRoomVisited;
	
	public BoardCell pickLocation( Set<BoardCell> targets) {
		return new WalkwayCell();
	}
	
	private void createSuggestion() {}
	
	private void updateSeen(Card seen) {}
	
	//For testing purpose only.
	public void setLastRoomVisited(char l) {
		lastRoomVisited = l;
	}
	
}
