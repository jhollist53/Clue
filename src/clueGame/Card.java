package clueGame;

public class Card {
	
	enum cardType{
		WEAPON, PERSON, ROOM;
	}
	
	private String name;
	private cardType type;
	
	public Card(String name, cardType type) {
		this.name = name;
		this.type = type;
	}
	
	public Card() {
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public cardType getType() {
		return type;
	}
	public void setType(cardType type) {
		this.type = type;
	}
	
	public boolean equals(Card card1) {
		return true;
	}
	
	
	

}
