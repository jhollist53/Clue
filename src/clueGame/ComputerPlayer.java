package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, String color, int startCol, int startRow) {
		super(name, color, startCol, startRow);
		// TODO Auto-generated constructor stub
	}

	private char lastRoomVisited;
	
	private void pickLocation( Set<BoardCell> targets) {
		
	}
	
	private void createSuggestion() {}
	
	private void updateSeen(Card seen) {}
	
}
