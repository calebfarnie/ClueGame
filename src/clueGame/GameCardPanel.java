package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class GameCardPanel extends JPanel {
	
	// instance variables
	Board board;
	Player player;
	Set<Card> peopleCards = new HashSet<Card>();
	Set<Card> roomCards = new HashSet<Card>();
	Set<Card> weaponCards = new HashSet<Card>();
	Set<Card> displayedCards = new HashSet<Card>();

	public GameCardPanel(Player player, Board board) {
		this.player = player;
		this.board = board;
		
		// make title and add options
		setLayout(new GridLayout(0,1));
		TitledBorder title = new TitledBorder(new EtchedBorder(), "Known Cards");
		title.setTitleJustification(TitledBorder.CENTER);
		setBorder(title);		

		// make sections for people, rooms, and weapons
		add(createPeople());
		add(createRooms());
		add(createWeapons());
	}
	
	public JPanel createPeople() {
		// add people header
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(0,1));
		
		// clear panel
		panel.removeAll();
		
		// add hand label and display PERSON cards in hand
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		displayCards(panel, player.getHand(), CardType.PERSON);
		
		// add seen label and display seen PERSON cards
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		displayCards(panel, player.getSeen(), CardType.PERSON);
		
		return panel;
	}
	
	public JPanel createRooms() {
		// add rooms header
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(0,1));
		
		// clear panel
		panel.removeAll();
		
		// add hand label and display ROOM cards in hand
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		displayCards(panel, player.getHand(), CardType.ROOM);
		
		// add seen label and display seen PERSON cards
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		displayCards(panel, player.getSeen(), CardType.ROOM);
		
		return panel;
	}
	
	public JPanel createWeapons() {
		// add weapons header
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(0,1));
		
		// clear panel
		panel.removeAll();
		
		// add hand label and display WEAPON cards in hand
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		displayCards(panel, player.getHand(), CardType.WEAPON);
		
		// add seen label and display seen WEAPON cards
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		displayCards(panel, player.getSeen(), CardType.WEAPON);
		
		return panel;
	}
	
	private void displayCards(JPanel panel, Set<Card> cards, CardType type) {
		boolean cardsPresent = false;
		
		// iterate through each card, then add that card to the JPanel
		for(Card card : cards) {
			if(card.getType() == type && !displayedCards.contains(card)) {
				JTextField person = new JTextField(card.getName());
				person.setEditable(false);
				person.setBackground(getColor(card));
				panel.add(person);
				displayedCards.add(card);
				cardsPresent = true;
			}
		}
		
		// if no cards are present in the previous iteration, add a None placeholder
		if(!cardsPresent) {
			JTextField person = new JTextField("None");
			person.setEditable(false);
			person.setBackground(Color.WHITE);
			panel.add(person);
		}
	}
	
	public Color getColor(Card card) {
		// iterate through each player and test if that card is in their hand
		for(Player player : board.getPlayers().values()) {
			if(player.getHand().contains(card)) {
				// if so, return the player's color
				return player.getColor();
			}
		}
		// else return white
		return Color.WHITE;
	}

	public static void main(String[] args) {
		// get the instance of the game board
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		// get the human player, Luke Skywalker
		Player person = board.getPlayer("Luke Skywalker");
		
		// add cards to his seen list
		person.updateSeen(new Card("Obi-Wan Kenobi", CardType.PERSON));
		person.updateSeen(new Card("Lightsaber", CardType.WEAPON));
		person.updateSeen(new Card("Kashyyyk", CardType.ROOM));
		person.updateSeen(new Card("Endor", CardType.ROOM));
		person.updateSeen(new Card("Darth Vader", CardType.PERSON));
		person.updateSeen(new Card("Vibro Knife", CardType.WEAPON));
		
		// create the JPanel
		GameCardPanel panel = new GameCardPanel(person, board);  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(200, 750);  // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setVisible(true); // make it visible
	}

}
