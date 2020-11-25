package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class ClueGame extends JFrame {
	public static final boolean JAR = false;	// set to true if exporting JAR

	public ClueGame(Player person, Board board) {
		setTitle("Clue");
		setSize(820, 665);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String iconDirectory = "clueIcon.png";
		Image icon;
		
		if(ClueGame.JAR) {
			iconDirectory = "/data/" + iconDirectory;
			URL url = getClass().getResource(iconDirectory);
			icon = Toolkit.getDefaultToolkit().getImage(url);
		}else {
			iconDirectory = "src/data/" + iconDirectory;
			icon = Toolkit.getDefaultToolkit().getImage(iconDirectory);
		}
		
		setIconImage(icon);

		JPanel boardPanel = Board.getInstance();
		boardPanel.setBackground(Color.darkGray);

		GameCardPanel cardPanel = GameCardPanel.getInstance();
		cardPanel.initialize();
		GameControlPanel controlPanel = new GameControlPanel();

		cardPanel.setPreferredSize(new Dimension(150, 0));

		add(cardPanel, BorderLayout.EAST);
		add(controlPanel, BorderLayout.SOUTH);
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
		gui.setLocationRelativeTo(null); // centers on screen
		gui.setVisible(true);

		// display splash screen
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		JLabel line1 = new JLabel("You are " + person.getName() + ".");
		JLabel line2 = new JLabel("Can you find the solution");
		JLabel line3 = new JLabel("before the Computer players?");
		line1.setHorizontalAlignment(SwingConstants.CENTER);
		line2.setHorizontalAlignment(SwingConstants.CENTER);
		line3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(line1);
		panel.add(line2);
		panel.add(line3);
		JOptionPane.showMessageDialog(gui, panel, "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);

		// start player's turn
		board.processTurn();
	}
}
