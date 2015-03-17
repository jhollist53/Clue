package clueGame;

import java.util.Set;

public class Player {
	
	protected String name;
	protected Set<Card> cards;
	protected String color;
	protected int startCol;
	protected int startRow;
	
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

	public Card disproveSuggestion(String person, String weapon, String room) {
		return null;
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
