package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String name, CardType type) {
		this.cardName = name;
		this.cardType = type;
	}
	
	public String getName() {
		return this.cardName;
	}
	
	public CardType getType() {
		return this.cardType;
	}
	
	public boolean equals(Card target) {
		return this.cardName.equals(target.cardName) && this.cardType == target.cardType;
	}

}
