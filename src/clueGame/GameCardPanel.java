package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;
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
	Set<Card> displayedCards;

	private static GameCardPanel theInstance = new GameCardPanel();

	private GameCardPanel() {
		super();
	}

	public void initialize() {
		this.board = Board.getInstance();
		this.player = board.getPlayersList().get(0);
		displayedCards = new HashSet<Card>();

		// make title and add options
		setLayout(new GridLayout(0, 1));
		TitledBorder title = new TitledBorder(new EtchedBorder(), "Known Cards");
		title.setTitleJustification(TitledBorder.CENTER);
		setBorder(title);

		updatePanels();
	}

	public static GameCardPanel getInstance() {
		return theInstance;
	}

	public void updatePanels() {
		removeAll();
		addAll();
		invalidate();
		validate();
	}

	public void addAll() {
		displayedCards.clear();
		add(createPeople());
		add(createRooms());
		add(createWeapons());
	}

	private JPanel createPeople() {
		// add people header
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.setLayout(new GridLayout(0, 1));

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

	private JPanel createRooms() {
		// add rooms header
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.setLayout(new GridLayout(0, 1));

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

	private JPanel createWeapons() {
		// add weapons header
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		panel.setLayout(new GridLayout(0, 1));

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
		for (Card card : cards) {
			if (card.getType() == type && !displayedCards.contains(card)) {
				JTextField person = new JTextField(card.getName());
				person.setEditable(false);
				person.setBackground(getColor(card));
				panel.add(person);
				displayedCards.add(card);
				cardsPresent = true;
			}
		}

		// if no cards are present in the previous iteration, add a None placeholder
		if (!cardsPresent) {
			JTextField person = new JTextField("None");
			person.setEditable(false);
			person.setBackground(Color.WHITE);
			panel.add(person);
		}
	}

	public Color getColor(Card card) {
		// iterate through each player and test if that card is in their hand
		for (Player player : board.getPlayers().values()) {
			if (player.getHand().contains(card)) {
				// if so, return the player's color
				return player.getColor();
			}
		}
		// else return white
		return Color.WHITE;
	}
}
