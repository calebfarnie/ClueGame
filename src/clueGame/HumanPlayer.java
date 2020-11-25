package clueGame;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class HumanPlayer extends Player {
	
	public HumanPlayer(String name, String color, int startRow, int startCol) {
		super(name, color, startRow, startCol);
	}

	@Override
	public void updateHand(Card card) {
		super.hand.add(card);
	}
	
	@Override
	public void updateSeen(Card seenCard) {
		super.seenCards.add(seenCard);
	}

	@Override
	public BoardCell selectTarget(Set<BoardCell> targets, Map<Character, Room> roomMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Solution createSuggestion(String room, ArrayList<Card> deck) {
		// display suggestion dialog
		SuggestionDialog panel = new SuggestionDialog(this);
		panel.setLocationRelativeTo(null);
		panel.setVisible(true);
		panel.setRoom(inRoom.getName());

		return null;
	}

}
