package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C13A-2 Clue Init 1
 * 10 October 2020
 */

import java.util.Map;

public class Board {
	
	// instance variables
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private Map<Character, Room> roomMap;
	
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
     public void initialize()
     {
     }
	
	// methods
     
    public void setConfigFiles(String csv, String txt) {
    	
    }
	
	public void loadConfigFiles() {
		
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	public BoardCell getCell(int i, int j) {
		// TODO Auto-generated method stub
		return new BoardCell();
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return new Room();
	}
	
	public Room getRoom(BoardCell c) {
		// TODO Auto-generated method stub
		return new Room();
	}

}
