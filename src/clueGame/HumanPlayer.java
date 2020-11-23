package clueGame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;

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
		SuggestionDialog panel = new SuggestionDialog();
		panel.setLocationRelativeTo(null);
		panel.setVisible(true);
		panel.setRoom(inRoom.getName());
		
		Solution suggestion = new Solution();
		suggestion.room = new Card(inRoom.getName(), CardType.ROOM);
		suggestion.person = new Card(panel.getSelectedPerson(), CardType.PERSON);
		suggestion.weapon = new Card(panel.getSelectedWeapon(), CardType.WEAPON);
		
		return suggestion;
	}

}
