package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, String color, int startRow, int startCol) {
		super(name, color, startRow, startCol);
	}

	@Override
	public void updateHand(Card card) {
		super.hand.add(card);		
	}
	
	@Override
	public void updateSeen(Card seenCard) {
		// TODO
	}
	
	public Solution createSuggestion(Room room) {
		// TODO
		return new Solution();
	}
	
	public BoardCell selectTarget() {
		// TODO
		return new BoardCell(0, 0);
	}

}
