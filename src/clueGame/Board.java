package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C14A-1 Clue Init 2
 * 12 October 2020
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C13A-2 Clue Init 1
 * 10 October 2020
 */

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {

	// instance variables
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
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
			generateAdjLists();	
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}


	}

	// methods

	private void generateAdjLists() {
		// get adjacency lists
		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numColumns; c++) {
				calcAdjList(r,c);
			}
		}
	}

	private void calcAdjList(int r, int c) {
		// TODO Auto-generated method stub

	}

	public void setConfigFiles(String csv, String txt) {
		layoutConfigFile = "data/" + csv;
		setupConfigFile = "data/" + txt;
	}

	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		loadSetupConfig();
		loadLayoutConfig();
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		// allocate memory for room map
		roomMap = new HashMap<Character, Room>();
				
		// set up file reader and scanner
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);

		// add rooms
		while(in.hasNextLine()) {
			String rowData = in.nextLine();

			if(rowData.substring(0,2).equals("//"))
				continue;

			String[] tempRoom = rowData.split(", ");

			// throw exception if not a room or space
			if(!tempRoom[0].equals("Room") && !tempRoom[0].equals("Space"))
				throw new BadConfigFormatException("Setup file contains room type that is not Room or Space.");

			String roomName = tempRoom[1];
			char roomInitial = tempRoom[2].charAt(0);
			Room room = new Room(roomName);
			roomMap.put(roomInitial, room);
		}

		in.close();
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException{
		// create arraylist for rows
		ArrayList<String[]> rows = new ArrayList<String[]>();
		FileReader reader = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(reader);
		
		// add each row to arraylist
		while(in.hasNextLine()) {
			String[] splitRow = in.nextLine().split(",");
			rows.add(splitRow);
		}
		in.close();

		// test for equal columns
		int rowCount = rows.get(0).length;
		for(String[] row : rows) {
			if (row.length != rowCount) {
				throw new BadConfigFormatException("Expected row: " + rowCount + ". Actual row: " + row.length);
			}
		}

		// set num rows and cols
		numRows = rows.size();
		numColumns = rows.get(0).length;

		// allocate memory for grid array
		grid = new BoardCell[numRows][numColumns];

		// fill board
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numColumns; col++) {
				String[] currentRowData = rows.get(row);
				String cellData = currentRowData[col];
				char initial = cellData.charAt(0);
				
				// set variables to false. Will be changed in switch-case below.
				boolean label = false;
				boolean center = false;
				boolean doorway = false;
				boolean isSecretPassage = false;
				DoorDirection direction = DoorDirection.NONE;

				// test for initial in roomMap. Throw exception if room not in map.
				if(!roomMap.containsKey(initial))
					throw new BadConfigFormatException("Room type not declared in " + setupConfigFile);

				// if initial length is 1, then it's a normal room.
				if(cellData.length() == 1) {
					label = false;
					center = false;
					direction = DoorDirection.NONE;
					
				// if initial length >1, then process second value
				} else {
					switch(cellData.charAt(1)) {
					case '*':
						center = true;
						direction = DoorDirection.NONE;
						break;
					case '#':
						label = true;
						direction = DoorDirection.NONE;
						break;
					case '^':
						doorway = true;
						direction = DoorDirection.UP;
						break;
					case '>':
						doorway = true;
						direction = DoorDirection.RIGHT;
						break;
					case '<':
						doorway = true;
						direction = DoorDirection.LEFT;
						break;
					case 'v':
						doorway = true;
						direction = DoorDirection.DOWN;
						break;
					default:
						isSecretPassage = true;
						break;
					}
				}

				// create board cell
				BoardCell cell = new BoardCell(row, col, initial, label, center, direction, doorway);
				grid[row][col] = cell;
				cell.setIsRoom(true);

				// set secret passage
				if(isSecretPassage) {
					cell.setSecretPassage(cellData.charAt(1));
				}

				// mark room's center cell/label
				if(center) {
					roomMap.get(cellData.charAt(0)).setCenterCell(cell);
				}

				if(label) {
					roomMap.get(cellData.charAt(0)).setLabelCell(cell);
				}

			}
		}
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Room getRoom(char initial) {
		return roomMap.get(initial);
	}

	public Room getRoom(BoardCell cell) {
		if(cell.isRoom()) {
			return roomMap.get(cell.getInitial());
		} else {
			return null;
		}
	}
	
	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList();
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		// TODO Auto-generated method stub
		
	}
	
	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return new HashSet<BoardCell>();
	}
	
	

}
