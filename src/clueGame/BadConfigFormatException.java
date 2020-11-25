package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * @author Caleb Farnie
 * @author Joshua Josey
 */

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Error: Invalid configuration file.");

		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(new File("logfile.txt"), true));
			out.println("Error: Invalid configuration file.");
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public BadConfigFormatException(String message) {
		super("Error: " + message);

		try {
			PrintWriter out = new PrintWriter(new FileOutputStream(new File("logfile.txt"), true));
			out.println("Error: Invalid configuration file. " + message);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

}
