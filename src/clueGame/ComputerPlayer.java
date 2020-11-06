package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		seenCards.add(seenCard);
	}
	
	public Solution createSuggestion(Room room) {
		// TODO
		return new Solution();
	}
	
	@Override
	public BoardCell selectTarget(Set<BoardCell> targets, Map<Character, Room> roomMap) {
		ArrayList<BoardCell> newTargets = new ArrayList<>(targets);
		
		// iterate through targets, return room if not in seen.
		for(BoardCell target : targets) {
			if(target.isRoomCenter()) {
				String roomName = roomMap.get(target.getInitial()).getName();
				Card roomCard = new Card(roomName, CardType.ROOM);
				if(!seenCards.contains(roomCard)) {
					return target;
				}
			}
		}
		
		// else return random target
		Collections.shuffle(newTargets);		
		return newTargets.get(0);
	}

}
