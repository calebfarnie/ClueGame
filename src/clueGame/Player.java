package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	protected Set<Card> hand;
	protected ArrayList<Card> seenCards = new ArrayList<Card>();

	public abstract void updateHand(Card card);
	public abstract void updateSeen(Card seenCard);
	public abstract Solution createSuggestion(String room, ArrayList<Card> deck);
	public abstract BoardCell selectTarget(Set<BoardCell> targets, Map<Character, Room> roomMap);

	public Player(String name, String color, int startRow, int startCol) {
		this.name = name;

		switch(color.toLowerCase()) {
		case "white":
			this.color = Color.white;
			break;
		case "blue":
			this.color = new Color(154, 206, 223);
			break;
		case "green":
			this.color = new Color(181, 225, 174);
			break;
		case "red":
			this.color = new Color(225, 138, 147);
			break;
		case "orange":
			this.color = new Color(253, 202, 162);
			break;
		case "purple":
			this.color = new Color(193, 179, 215);
			break;
		default:
			this.color = Color.red;
			break;
		}

		this.row = startRow;
		this.column = startCol;
		this.hand = new HashSet<Card>();
	}

	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> proof = new ArrayList<Card>();

		// loop through cards, add matching cards to AL and randomly choose one, else return null
		for(Card card : hand) {
			if(card.equals(suggestion.person) || card.equals(suggestion.room) || card.equals(suggestion.weapon))
				proof.add(card);
		}

		if(proof.size() > 0) {
			Collections.shuffle(proof);
			return proof.get(0);
		}else
			return null;
	}

	public void setHand(Set<Card> hand) {
		this.hand = new HashSet<Card>(hand);
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.column;
	}

	public Color getColor() {
		return this.color;
	}

	public Set<Card> getHand(){
		return hand;
	}
	
	public String getName() {
		return name;
	}
	
	public Set<Card> getSeen() {
		return new HashSet<Card>(seenCards);
	}

}
