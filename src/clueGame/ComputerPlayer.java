package clueGame;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import clueGame.Card.cardType;

public class ComputerPlayer extends Player {
	private HashSet<Card> seenCards;
	private ArrayList<Card> seen =  new ArrayList<Card>();
	private char lastRoomVisited;
	
	public ComputerPlayer() {
		super();
		seenCards = new HashSet<Card>();
	}

	public ComputerPlayer(String name, String color, int startCol, int startRow) {
		super(name, color, startCol, startRow);
		lastRoomVisited = 'X';
		seenCards = new HashSet<Card>();
	}
	
	public ComputerPlayer(String name, String color, int startCol, int startRow, char lastRoom) {
		super(name, color, startCol, startRow);
		lastRoomVisited = lastRoom;
		seenCards = new HashSet<Card>();
	}

	
	public void pickLocation( Set<BoardCell> targets) {
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
			startCol = possibleRoomLocations.get(rnd.nextInt(possibleRoomLocations.size())).x;
		} else {
			startRow =  possibleWalkwayLocations.get(rnd.nextInt(possibleWalkwayLocations.size())).y;
		}
		

	}
	
	public ArrayList<Card> createSuggestion(RoomCell location) {
		ArrayList<Card> suggest = new ArrayList<Card>();
		suggest.add(new Card(location.getName(), Card.cardType.ROOM));
		
		Random random = new Random();
		
		ArrayList<Card> tempdeck = new ArrayList<Card>(ClueGame.getDeck());
		if (seenCards == null){
			seenCards = new HashSet<Card>();
		}
		
		tempdeck.removeAll(seenCards);
		int i =0;
		while(tempdeck.get(i).getType()==Card.cardType.WEAPON){
			i++;
		}
		suggest.add(tempdeck.get(random.nextInt(i)));
		int j = 0;
		while(tempdeck.get(i+j).getType()==Card.cardType.PERSON){
			j++;
		}

		
		suggest.add(tempdeck.get(random.nextInt(j)+i));
		return suggest;
	}
	
	public void updateSeen(Card seen) {
		this.seen.add(seen);
	}
	
	//For testing purpose only.
	public void setLastRoomVisited(char l) {
		lastRoomVisited = l;
	}

	public HashSet<Card>  getSeenCards() {
		return seenCards;
	}
	
}
