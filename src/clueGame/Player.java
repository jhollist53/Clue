package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	
	protected String name;
	protected Set<Card> cards;
	protected String color;
	protected int startCol;
	protected int startRow;
	
	public Player() {
		name = "";
		color = "";
		startCol = 0;
		startRow = 0;
	}
	
	public Player( String name, String color, int startRow, int startCol) {
		this.name = name;
		this.color = color;
		this.startCol = startCol;
		this.startRow = startRow;
	}
	
	public int getStartCol() {
		return startCol;
	}

	public int getStartRow() {
		return startRow;
	}

	public Card disproveSuggestion(Card person, Card weapon, Card room) {
		ArrayList<Card> possibleReturnValues =  new ArrayList<Card>();
		Random rnd = new Random();
		
		for (Card c: cards) {
			if (c.equals(person) || c.equals(weapon) || c.equals(room)) {
				possibleReturnValues.add(c);
			}
		}
		
		if (!possibleReturnValues.isEmpty()) {
			return possibleReturnValues.get(rnd.nextInt(possibleReturnValues.size()));
		} else {
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public Set<Card> getCards() {
		return cards;
	}

	public String getColor() {
		return color;
	}
	
	public void setCards( Set<Card> cards) {
		this.cards = cards;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player player = (Player) o;
			return player.name.equals(name) && player.color.equals(color) && player.startCol == startCol && player.startRow == startRow;
		}
		return false;
	}
	

}
