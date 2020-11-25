package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class AccusationDialog extends JDialog {

	Board board;
	private static JComboBox<String> room, person, weapon;
	private String selectedRoom, selectedPerson, selectedWeapon;
	private Solution accusation = new Solution();

	public AccusationDialog() {
		board = Board.getInstance();

		// human = hPlayer;
		setLayout(new GridLayout(0, 2));
		setMinimumSize(new Dimension(300, 150));
		setTitle("Make an Accusation");

		// shuffle deck
		board.shuffleDeck();

		// make Room text field
		add(new JLabel("Room"));
		room = new JComboBox<String>();
		Card firstRoom = null;
		for (Card card : board.getDeck()) {
			if (card.getType().equals(CardType.ROOM)) {
				room.addItem(card.getName());
				if (firstRoom == null) {
					firstRoom = card;
				}
			}
		}
		room.addActionListener(new ComboListener());
		add(room);

		accusation.room = new Card(firstRoom.getName(), CardType.ROOM);

		// make Person combo box
		add(new JLabel("Person"));
		person = new JComboBox<String>();
		for (Player player : board.getPlayersList()) {
			person.addItem(player.getName());
		}
		person.addActionListener(new ComboListener());
		add(person);

		// set default option for person to first item in list
		accusation.person = new Card(board.getPlayersList().get(0).getName(), CardType.PERSON);

		// make Weapon combo box
		add(new JLabel("Weapon"));
		weapon = new JComboBox<String>();
		Card firstWeapon = null;
		for (Card card : board.getDeck()) {
			if (card.getType().equals(CardType.WEAPON)) {
				weapon.addItem(card.getName());
				if (firstWeapon == null) {
					firstWeapon = card;
				}
			}
		}
		weapon.addActionListener(new ComboListener());
		add(weapon);

		// set default option for weapon to first item in list
		accusation.weapon = new Card(firstWeapon.getName(), CardType.WEAPON);

		// add buttons
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitButtonListener());
		add(submit);

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelButtonListener());
		add(cancel);
	}

	public String getSelectedPerson() {
		return selectedPerson;
	}

	public String getSelectedWeapon() {
		return selectedWeapon;
	}

	public String getSelectedRoom() {
		return selectedRoom;
	}

	private class ComboListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedRoom = room.getSelectedItem().toString();
			selectedPerson = person.getSelectedItem().toString();
			selectedWeapon = weapon.getSelectedItem().toString();
		}
	}

	private class SubmitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// process submission

			// change the suggestions if they return null
			if (selectedRoom != null) {
				accusation.room = new Card(selectedRoom, CardType.ROOM);
			}

			if (selectedPerson != null) {
				accusation.person = new Card(selectedPerson, CardType.PERSON);
			}

			if (selectedWeapon != null) {
				accusation.weapon = new Card(selectedWeapon, CardType.WEAPON);
			}

			board.setCurrentAccusation(accusation);
			dispose();
			board.checkAccusation();
		}
	}

	private class CancelButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// close window -> turn is now over
			dispose();
		}

	}

}
