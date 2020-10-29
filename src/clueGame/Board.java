package clueGame;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 * C15A Clue Board 2
 * 16 October 2020
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class Board {

	// instance variables
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
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

	private void calcAdjList(int row, int col) {
		BoardCell cell = getCell(row, col);

		// normal walkway
		if(!cell.isWalkway())
			return;

		// if doorway, test which direction and add that room center to adj list.
		// also add doorway to room center's adj list
		if(cell.isDoorway()) {
			processDoorDirection(row, col, cell);
		}
		
		// if not room center, add it to the adj list
		if(!cell.isRoomCenter()) {
			addRoomCenterAdjList(row, col, cell);
		} else {
			// add secret passage if applicable
			char current = cell.getInitial();
			char secret = roomMap.get(current).getSecretPassage();
			if(secret != '0') {
				cell.addAdj(roomMap.get(secret).getCenterCell());
			}
		}

	}

	private void addRoomCenterAdjList(int row, int col, BoardCell cell) {
		// left
		if(row > 0 && getCell(row-1, col).isWalkway()) {
			cell.addAdj(getCell(row-1, col));
		}

		// up
		if(col > 0 && getCell(row, col-1).isWalkway()) {
			cell.addAdj(getCell(row, col-1));
		}

		// right
		if(row < numRows-1 && getCell(row+1, col).isWalkway()) {
			cell.addAdj(getCell(row+1, col));
		}

		// down
		if(col < numColumns-1 && getCell(row, col+1).isWalkway()) {
			cell.addAdj(getCell(row, col+1));
		}
	}

	private void processDoorDirection(int row, int col, BoardCell cell) {
		// doorway adj
		// add center of room door points to
		// Handle other 3 directions like walkway
		if(cell.getDoorDirection().equals(DoorDirection.UP)) {
			BoardCell roomCell = getCell(row-1, col);
			addDoorAdjList(cell, roomCell);
		} else if(cell.getDoorDirection().equals(DoorDirection.DOWN)) {
			BoardCell roomCell = getCell(row+1, col);
			addDoorAdjList(cell, roomCell);
		} else if(cell.getDoorDirection().equals(DoorDirection.LEFT)) {
			BoardCell roomCell = getCell(row, col-1);
			addDoorAdjList(cell, roomCell);
		} else { // direction is RIGHT
			BoardCell roomCell = getCell(row, col+1);
			addDoorAdjList(cell, roomCell);
		}
	}

	private void addDoorAdjList(BoardCell cell, BoardCell roomCell) {
		char c = roomCell.getInitial();
		cell.addAdj(roomMap.get(c).getCenterCell());
		roomMap.get(c).getCenterCell().addAdj(cell);
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
					roomMap.get(cellData.charAt(0)).setSecretPassage(cellData.charAt(1));
					cell.setSecretPassage(cellData.charAt(1));
				}

				// mark room's center cell/label
				if(center) {
					roomMap.get(cellData.charAt(0)).setCenterCell(cell);
				}

				if(label) {
					roomMap.get(cellData.charAt(0)).setLabelCell(cell);
				}

				if(initial == 'W') {
					roomMap.get(cellData.charAt(0)).setWalkway();
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

	public void calcTargets(BoardCell startCell, int pathLength) {
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	private void findAllTargets(BoardCell startCell, int numSteps) {
		// recursively find target cells
		for(BoardCell c : startCell.getAdjList()) {
			if(!visited.contains(c) && !c.getOccupied()) {
				visited.add(c);
				if(numSteps == 1 && !c.getOccupied() || c.isRoomCenter()) {
					targets.add(c);
				}else {
					findAllTargets(c, numSteps -1);
				}
				visited.remove(c);
			}
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}



}
