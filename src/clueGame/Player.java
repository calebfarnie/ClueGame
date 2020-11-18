package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Player {
	private String name;
	private Color color;
	private Room inRoom;
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
	
	public void setLocation(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public void setLocation(BoardCell targetCell) {
		this.row = targetCell.getRow();
		this.column = targetCell.getCol();
	}
	
	public void setRoom(Room room) {
		this.inRoom = room;
	}
	
	public Room getRoom() {
		return inRoom;
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
	
	public void draw(Graphics g, int width, int height, int indexVariable) {
		Board board = Board.getInstance();
		ArrayList<Player> players = board.getPlayersList();
//		indexVariable = indexVariable - 1;
		int offset = 0;

		for(int i = 0; i < indexVariable; i++) {
			Player player = players.get(i);
			
			if(this.equals(player)) {
				continue;
			}

			if(this.getRoom() == null || player.getRoom() == null) {
				continue;
			}

			if(player.getRoom().equals(this.getRoom())) {
				offset += width/2;
			}
		}

		g.setColor(color);
		g.fillOval(column*width + offset, row*height, width, height);
		g.setColor(Color.black);
		g.drawOval(column*width + offset, row*height, width, height);
		
	}

}
