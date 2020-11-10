package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	
	private JTextField turnName;
	private JTextField guessStr;
	private JTextField guessResult;
	private JTextField rollNum;

	public GameControlPanel() {
		setLayout(new GridLayout(2,0));
		
		// split the contro panel into two rows and add them to the panel 
		add(createControlPanelTopRow());
		add(createControlPanelBottomRow());
	}
	
	private JPanel createControlPanelTopRow() {
		JPanel panel = new JPanel();
		
		// add the 'whose turn' and 'roll' number to the row
		panel.setLayout(new GridLayout(1,4));
		panel.add(createWhoseTurn());
		panel.add(createRoll());
		
		// add buttons
		JButton accuse = new JButton("Make Accusation");
		panel.add(accuse);
		JButton next = new JButton("NEXT!");
		panel.add(next);
		
		return panel;
	}
	
	private JPanel createControlPanelBottomRow() {
		JPanel panel = new JPanel();
		
		// add guess and guess result panels
		panel.setLayout(new GridLayout(0,2));
		panel.add(createGuess(), BorderLayout.WEST);
		panel.add(createGuessResult(), BorderLayout.EAST);
		
		return panel;
	}
	
	private JPanel createGuess() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(1,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		
		guessStr = new JTextField();
		guessStr.setEditable(false);
		
		panel.add(guessStr);
		
		return panel;
	}
	
	private JPanel createGuessResult() {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(1,0));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		
		guessResult = new JTextField();
		guessResult.setEditable(false);
		
		panel.add(guessResult);
		
		return panel;
	}
	
	private JPanel createWhoseTurn() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(0,1));
		JLabel nameLabel = new JLabel("Whose turn?", SwingConstants.CENTER);	// second param makes label center
		panel.add(nameLabel, BorderLayout.CENTER);
		
		turnName = new JTextField();
		turnName.setEditable(false);
		panel.add(turnName, BorderLayout.WEST);
		
		return panel;
	}
	
	private JPanel createRoll() {
		JPanel panel = new JPanel();
		
		JLabel nameLabel = new JLabel("Roll:");
		panel.add(nameLabel);
		
		rollNum = new JTextField();
		rollNum.setEditable(false);
		
		panel.add(rollNum);
		
		return panel;
	}
	
	// setter methods
	private void setTurn(Player player, int roll){
		this.turnName.setText(player.getName());
		this.turnName.setBackground(player.getColor());
		this.rollNum.setText(String.valueOf(roll));
	}
	
	private void setGuess(String guess){
		this.guessStr.setText(guess);
	}
	
	private void setGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame
		frame.setContentPane(panel); // put the panel in the frame
        frame.setSize(750, 180);  // size the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        frame.setVisible(true); // make it visible

        // test filling in the data
        panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0), 5);
        panel.setGuess( "I have no guess!");
        panel.setGuessResult( "So you have nothing?");
	}

}
