package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class GameCardPanel extends JPanel {
	
	Board board;
	Player player;
	Set<Card> peopleCards = new HashSet<Card>();
	Set<Card> roomCards = new HashSet<Card>();
	Set<Card> weaponCards = new HashSet<Card>();
	Set<Card> displayedCards = new HashSet<Card>();

	public GameCardPanel(Player player, Board board) {
		this.player = player;
		
		setLayout(new GridLayout(0,1));
		TitledBorder title = new TitledBorder(new EtchedBorder(), "Known Cards");
		title.setTitleJustification(TitledBorder.CENTER);
		setBorder(title);		

		add(createPeople());
		add(createRooms());
		add(createWeapons());
	}
	
	public JPanel createPeople() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(0,1));
		
		panel.removeAll();
		
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		displayCards(panel, player.getHand(), CardType.PERSON);
		
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		displayCards(panel, player.getSeen(), CardType.PERSON);
		
		return panel;
	}
	
	public JPanel createRooms() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(0,1));
		
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		displayCards(panel, player.getHand(), CardType.ROOM);
		
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		displayCards(panel, player.getSeen(), CardType.ROOM);
		
		return panel;
	}
	
	public JPanel createWeapons() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(0,1));
		
		JLabel handLabel = new JLabel("In Hand:");
		panel.add(handLabel);
		displayCards(panel, player.getHand(), CardType.WEAPON);
		
		JLabel seenLabel = new JLabel("Seen:");
		panel.add(seenLabel);
		displayCards(panel, player.getSeen(), CardType.WEAPON);
		
		return panel;
	}
	
	private void displayCards(JPanel panel, Set<Card> cards, CardType type) {
		boolean cardsPresent = false;
		
		for(Card card : cards) {
			if(card.getType() == type && !displayedCards.contains(card)) {
				JTextField person = new JTextField(card.getName());
				person.setEditable(false);
				person.setBackground(Color.WHITE);
				panel.add(person);
				cardsPresent = true;
			}
		}
		
		if(!cardsPresent) {
			JTextField person = new JTextField("None");
			person.setEditable(false);
			person.setBackground(Color.WHITE);
			panel.add(person);
		}
	}

	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		
		Player person = board.getPlayer("Luke Skywalker");
		person.updateSeen(new Card("Obi-Wan Kenobi", CardType.PERSON));
		person.updateSeen(new Card("Lightsaber", CardType.WEAPON));
		person.updateSeen(new Card("Kashyyyk", CardType.ROOM));
		person.updateSeen(new Card("Endor", CardType.ROOM));
		person.updateSeen(new Card("Darth Vader", CardType.PERSON));
		person.updateSeen(new Card("Vibro Knife", CardType.WEAPON));
		GameCardPanel panel = new GameCardPanel(person, board);  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(200, 750);  // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setVisible(true); // make it visible
	}

}
