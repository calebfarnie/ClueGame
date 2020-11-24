package clueGame;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class SuggestionDialog extends JDialog{
	
	// instance variables
	Board board;
	Player human;
	private static JTextField currentRoom;
	private static JComboBox<String> person, weapon;
	private String selectedRoom, selectedPerson, selectedWeapon;
	
	public SuggestionDialog(Player hPlayer) {
		board = Board.getInstance();
		human = hPlayer;
		
		setLayout(new GridLayout(0,2));
		setMinimumSize(new Dimension(300, 150));
		setTitle("Make a Suggestion");
		
		// make Room text field
		add(new JLabel("Current room"));
		currentRoom = new JTextField();
		currentRoom.setEditable(false);
		add(currentRoom);
		
		// make Person combo box
		add(new JLabel("Person"));
		person = new JComboBox<String>();
		for(Player player : board.getPlayersList()) {
			person.addItem(player.getName());
		}
		person.addActionListener(new ComboListener());
		add(person);
		
		// make Weapon combo box
		add(new JLabel("Weapon"));
		weapon = new JComboBox<String>();
		for(Card card : board.getDeck()) {
			if(card.getType().equals(CardType.WEAPON)) {
				weapon.addItem(card.getName());
			}
		}
		weapon.addActionListener(new ComboListener());
		add(weapon);
		
		// add buttons
		JButton submit = new JButton("Submit");
		submit.addActionListener(new SubmitButtonListener());
		add(submit);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new CancelButtonListener());
		add(cancel);
		
	}
	
	public void setRoom(String room) {
		currentRoom.setText(room);
		selectedRoom = room;
	}
	
	
	public String getSelectedPerson() {
		return selectedPerson;
	}
	
	
	public String getSelectedWeapon() {
		return selectedWeapon;
	}
	
	
	private class ComboListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedPerson = person.getSelectedItem().toString();
			selectedWeapon = weapon.getSelectedItem().toString();
		}
	}
	
	
	private class SubmitButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// process submission
			Solution suggestion = new Solution();
			suggestion.person = new Card(selectedPerson, CardType.PERSON);
			suggestion.weapon = new Card(selectedWeapon, CardType.WEAPON);
			suggestion.room = new Card(selectedRoom, CardType.ROOM);
			board.setCurrentSuggestion(suggestion);
			board.makeSuggestion(human);			
			dispose();
		}
	}

	private class CancelButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// close window -> turn is now over
//			selectedPerson = null;
//			selectedWeapon = null;
			dispose();
		}
		
	}
	public static void main(String[] args) {
//		SuggestionDialog panel = new SuggestionDialog();
//		panel.setVisible(true);
//		panel.setRoom("test");
	}

}
