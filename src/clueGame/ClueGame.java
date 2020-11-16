package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ClueGame extends JFrame {

	public ClueGame(Player person, Board board) {
		setTitle("Clue");
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel boardPanel = Board.getInstance();
		boardPanel.setBackground(Color.darkGray);
		
		add(new GameCardPanel(person, board), BorderLayout.EAST);
		add(new GameControlPanel(), BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// get the instance of the game board
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		// get the human player, Luke Skywalker
		Player person = board.getPlayer("Luke Skywalker");
		ClueGame gui = new ClueGame(person, board);	
		gui.setVisible(true);
		
		// display splash screen
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JLabel line1 = new JLabel("You are " + person.getName() + ".");
		JLabel line2 = new JLabel("Can you find the solution");
		JLabel line3 = new JLabel("before the Computer players?");
		line1.setHorizontalAlignment(SwingConstants.CENTER);
		line2.setHorizontalAlignment(SwingConstants.CENTER);
		line3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(line1);
		panel.add(line2);
		panel.add(line3);
		JOptionPane.showMessageDialog(null, panel, "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
	}
}
