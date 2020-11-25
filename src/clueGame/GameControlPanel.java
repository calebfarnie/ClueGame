package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
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

public class GameControlPanel extends JPanel {

	Board board;
	private static JTextField turnName;
	private static JTextField guessStr;
	private static JTextField guessResultString;
	private static JTextField rollNum;

	public GameControlPanel() {
		setLayout(new GridLayout(2, 0));

		// split the control panel into two rows and add them to the panel
		this.board = Board.getInstance();
		add(createControlPanelTopRow());
		add(createControlPanelBottomRow());
	}

	private JPanel createControlPanelTopRow() {
		JPanel panel = new JPanel();

		// add the 'whose turn' and 'roll' number to the row
		panel.setLayout(new GridLayout(1, 4));
		panel.add(createWhoseTurn());
		panel.add(createRoll());

		// add buttons
		JButton accuse = new JButton("Make Accusation");
		accuse.addActionListener(new AccusationButtonListener());
		panel.add(accuse);
		JButton next = new JButton("NEXT!");
		next.addActionListener(new NextButtonListener());
		panel.add(next);

		return panel;
	}

	private JPanel createControlPanelBottomRow() {
		JPanel panel = new JPanel();

		// add guess and guess result panels
		panel.setLayout(new GridLayout(0, 2));
		panel.add(createGuess(), BorderLayout.WEST);
		panel.add(createGuessResult(), BorderLayout.EAST);

		return panel;
	}

	private JPanel createGuess() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(1, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		guessStr = new JTextField();
		guessStr.setEditable(false);

		panel.add(guessStr);

		return panel;
	}

	private JPanel createGuessResult() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(1, 0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));

		guessResultString = new JTextField();
		guessResultString.setEditable(false);

		panel.add(guessResultString);

		return panel;
	}

	private JPanel createWhoseTurn() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(0, 1));
		JLabel nameLabel = new JLabel("Whose turn?", SwingConstants.CENTER); // second param makes label center
		panel.add(nameLabel);

		turnName = new JTextField();
		turnName.setEditable(false);
		panel.add(turnName);

		return panel;
	}

	private JPanel createRoll() {
		JPanel panel = new JPanel();

		JLabel nameLabel = new JLabel("Roll:");
		panel.add(nameLabel);

		rollNum = new JTextField();
		rollNum.setEditable(false);
		rollNum.setPreferredSize(new Dimension(50, 20));

		panel.add(rollNum);

		return panel;
	}

	// setter methods
	public static void setTurn(Player player, int roll) {
		turnName.setText(player.getName());
		turnName.setBackground(player.getColor());
		rollNum.setText(String.valueOf(roll));
	}

	public static void setGuess(String guess) {
		guessStr.setText(guess);
	}

	public static void setGuess(Solution suggestion) {
		String out = suggestion.person.getName() + " with a " + suggestion.weapon.getName() + " in "
				+ suggestion.room.getName();
		guessStr.setText(out);
	}

	public static void setGuessResult(String guessResult, Color color) {
		guessResultString.setText(guessResult);
		guessResultString.setBackground(color);

	}

	public static void setGuessResult(ArrayList<Player> players, Card card) {
		guessResultString.setText(card.getName());

		for (Player player : players) {
			if (player.getHand().contains(card)) {
				guessResultString.setBackground(player.getColor());
			}
		}
	}

	private class NextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			board.nextTurn();
		}
	}

	private class AccusationButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			board.processAccusation();
		}

	}

}
