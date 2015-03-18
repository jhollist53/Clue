package clueGame;

import java.util.ArrayList;
import java.util.Random;
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
		Random rnd = new Random();
		ArrayList<BoardCell> possibleRoomLocations = new ArrayList<BoardCell>();
		ArrayList<BoardCell> possibleWalkwayLocations = new ArrayList<BoardCell>();
		
		for(BoardCell b: targets) {
			if (b instanceof RoomCell ) {
				if ( ((RoomCell) b).getInitial() != lastRoomVisited) {
					possibleRoomLocations.add(b);
				}
			} else {
				possibleWalkwayLocations.add(b);
			}
		}
		
		if (!possibleRoomLocations.isEmpty()) {
			return possibleRoomLocations.get(rnd.nextInt(possibleRoomLocations.size()));
		} else {
			return possibleWalkwayLocations.get(rnd.nextInt(possibleWalkwayLocations.size()));
		}

	}
	
	private void createSuggestion() {}
	
	private void updateSeen(Card seen) {}
	
	//For testing purpose only.
	public void setLastRoomVisited(char l) {
		lastRoomVisited = l;
	}
	
}
