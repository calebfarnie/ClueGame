package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class ClueGame extends JFrame {

	public ClueGame(Player person, Board board) {
		setTitle("Clue");
		setSize(700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new GameCardPanel(person, board), BorderLayout.EAST);
		add(new GameControlPanel(), BorderLayout.SOUTH);
		add(Board.getInstance(), BorderLayout.CENTER);
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
	}

}
