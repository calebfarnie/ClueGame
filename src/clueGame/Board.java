package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C13A-2 Clue Init 1
 * 10 October 2020
 */

import java.util.Map;
import java.util.Scanner;

import experiment.TestBoardCell;

public class Board {
	
	// instance variables
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private BoardCell [][] grid;
	
	/*
     * variable and methods used for singleton pattern
     */
     private static Board theInstance = new Board();
     // constructor is private to ensure only one can be created
     private Board() {
            super() ;
     }
     // this method returns the only Board
     public static Board getInstance() {
            return theInstance;
     }
     /*
      * initialize the board (since we are using singleton pattern)
      */
     public void initialize() {
    	 
    	// load config files
  		try {
			loadConfigFiles();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
  		// create board  		
    	grid = new BoardCell[numRows][numColumns];
    	 
    	// fill board with cells
 		for(int r = 0; r < numRows; r++) {
 			for(int c = 0; c < numColumns; c++) {
 				grid[r][c] = new BoardCell(r,c);
 			}
 		}
 		
 		// get adjacency lists
 		for(int r = 0; r < numRows; r++) {
 			for(int c = 0; c < numColumns; c++) {
 				calcAdjList(r,c);
 			}
 		}	
    	 
     }
	
	// methods
    
     private void calcAdjList(int r, int c) {
 		// TODO Auto-generated method stub
 		
 	}
     
    public void setConfigFiles(String csv, String txt) {
    	layoutConfigFile = csv;
    	setupConfigFile = txt;
    }
	
	public void loadConfigFiles() throws FileNotFoundException {
		loadSetupConfig();
 		loadLayoutConfig();
	}
	
	public void loadSetupConfig() throws FileNotFoundException {
		// set up file reader and scanner
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);

		// add rooms
		if(in.next().equals("// Rooms and room cards")) {
			// read setup txt file
			while(in.hasNext()) {
				String[] tempRoom = in.next().split(", ");
				String roomName = tempRoom[1];
				char roomInitial = tempRoom[2].charAt(0);
				
				// TODO STOPPED HERE 10/10/2020
			}
		}
		

	}
	
	public void loadLayoutConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);
	}
	
	public BoardCell getCell(int row, int col) {
		return getCell(row, col);
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public Room getRoom(char initial) {
		// TODO
		return null;
	}
	
	public Room getRoom(BoardCell cell) {
		// TODO
		return null;
	}

}
