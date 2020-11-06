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
	
	@Override
	public Solution createSuggestion(String room, ArrayList<Card> deck) {
		Solution mySoln = new Solution();
		ArrayList<Card> potentialWeapon = new ArrayList<Card>();
		ArrayList<Card> potentialPerson = new ArrayList<Card>();
		
		// set room
		mySoln.room = new Card(room, CardType.ROOM);
		
		// set weapon
		for(Card card : deck) {
			if(!seenCards.contains(card) && card.getType() == CardType.WEAPON) {
				potentialWeapon.add(card);
			}
			
			if(!seenCards.contains(card) && card.getType() == CardType.PERSON) {
				potentialPerson.add(card);
			}
		}
		
		// return random weapon (still returns the only card if only one)
		Collections.shuffle(potentialWeapon);
		mySoln.weapon = potentialWeapon.get(0);
		
		// return random person (still returns the only card if only one)
		Collections.shuffle(potentialPerson);
		mySoln.person = potentialPerson.get(0);
		
		return mySoln;
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
