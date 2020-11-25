package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class ClueGame extends JFrame {
	public static final boolean JAR = false; // set to true if exporting JAR
	public static ClueGame gui;
	public static Board board;

	public ClueGame(Player person, Board board) {
		setTitle("Clue");
		setMinimumSize(new Dimension(820, 720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ClueGame.board = board;

		String iconDirectory = "clueIcon.png";
		Image icon;

		// set Clue icon
		if (ClueGame.JAR) {
			iconDirectory = "/data/" + iconDirectory;
			URL url = getClass().getResource(iconDirectory);
			icon = Toolkit.getDefaultToolkit().getImage(url);
		} else {
			iconDirectory = "src/data/" + iconDirectory;
			icon = Toolkit.getDefaultToolkit().getImage(iconDirectory);
		}

		setIconImage(icon);

		// create board panel
		JPanel boardPanel = Board.getInstance();
		boardPanel.setBackground(Color.darkGray);

		// create card and control panels
		GameCardPanel cardPanel = GameCardPanel.getInstance();
		cardPanel.initialize();
		cardPanel.setPreferredSize(new Dimension(150, 0));
		GameControlPanel controlPanel = new GameControlPanel();

		// create player/color map
		JPanel playerColorPanel = new JPanel();
		playerColorPanel.setLayout(new GridLayout(1, 0));

		// create title
		TitledBorder title = new TitledBorder(new EtchedBorder(), "Players");
		title.setTitleJustification(TitledBorder.CENTER);
		playerColorPanel.setBorder(title);

		// loop through playerslist and display their color as a key
		for (Player player : board.getPlayersList()) {
			JTextField playerField = new JTextField(player.getName());
			playerField.setHorizontalAlignment(SwingConstants.CENTER);
			playerField.setEditable(false);
			playerField.setBackground(player.getColor());
			playerColorPanel.add(playerField);
		}

		// add panels to frame
		add(playerColorPanel, BorderLayout.NORTH);
		add(cardPanel, BorderLayout.EAST);
		add(controlPanel, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		// get the instance of the game board
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();

		// get the human player, Luke Skywalker
		Player person = board.getPlayer("Luke Skywalker");
		gui = new ClueGame(person, board);
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
