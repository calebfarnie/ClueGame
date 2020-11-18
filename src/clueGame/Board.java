package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.List;  

public class Board extends JPanel implements MouseListener{

	// instance variables
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Map<String, Player> playerMap;
	private ArrayList<Player> playersList;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private ArrayList<Card> deck;
	private Solution theAnswer;
	private BoardCell[][] grid;
	public static int gameTurnCounter;
	private boolean targetSelected;
	private boolean accusationMade;
	private boolean isFinished;
	private Map<Character, ArrayList<BoardCell>> roomCells;
	private ArrayList<BoardCell> highlightedCells;

	// variable and methods used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	// initialize the board (since we are using singleton pattern)
	public void initialize() {
		// load config files
		try {
			loadConfigFiles();
			generateAdjLists();
			gameTurnCounter = 0;
			addMouseListener(this);

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}

	// private methods
	private void generateAdjLists() {
		// get adjacency lists
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				calcAdjList(r, c);
			}
		}
	}

	private void calcAdjList(int row, int col) {
		BoardCell cell = getCell(row, col);

		// normal walkway
		if (!cell.isWalkway())
			return;

		// if doorway, test which direction and add that room center to adj list.
		// also add doorway to room center's adj list
		if (cell.isDoorway()) {
			processDoorDirection(row, col, cell);
		}

		// if not room center, add it to the adj list
		if (!cell.isRoomCenter()) {
			addRoomCenterAdjList(row, col, cell);
		} else {
			// add secret passage if applicable
			char current = cell.getInitial();
			char secret = roomMap.get(current).getSecretPassage();
			if (secret != '0') {
				cell.addAdj(roomMap.get(secret).getCenterCell());
			}
		}

	}

	private void addRoomCenterAdjList(int row, int col, BoardCell cell) {
		// left
		if (row > 0 && getCell(row - 1, col).isWalkway()) {
			cell.addAdj(getCell(row - 1, col));
		}

		// up
		if (col > 0 && getCell(row, col - 1).isWalkway()) {
			cell.addAdj(getCell(row, col - 1));
		}

		// right
		if (row < numRows - 1 && getCell(row + 1, col).isWalkway()) {
			cell.addAdj(getCell(row + 1, col));
		}

		// down
		if (col < numColumns - 1 && getCell(row, col + 1).isWalkway()) {
			cell.addAdj(getCell(row, col + 1));
		}
	}

	private void processDoorDirection(int row, int col, BoardCell cell) {
		// add center of room door points to
		// handle other 3 directions like walkway
		if (cell.getDoorDirection().equals(DoorDirection.UP)) {
			BoardCell roomCell = getCell(row - 1, col);
			addDoorAdjList(cell, roomCell);
		} else if (cell.getDoorDirection().equals(DoorDirection.DOWN)) {
			BoardCell roomCell = getCell(row + 1, col);
			addDoorAdjList(cell, roomCell);
		} else if (cell.getDoorDirection().equals(DoorDirection.LEFT)) {
			BoardCell roomCell = getCell(row, col - 1);
			addDoorAdjList(cell, roomCell);
		} else { // direction is RIGHT
			BoardCell roomCell = getCell(row, col + 1);
			addDoorAdjList(cell, roomCell);
		}
	}

	private void addDoorAdjList(BoardCell currentCell, BoardCell roomCell) {
		char roomCellInitial = roomCell.getInitial();
		BoardCell roomCenterCell = roomMap.get(roomCellInitial).getCenterCell();

		// adds the room's center cell to currentCell's adj list
		currentCell.addAdj(roomMap.get(roomCellInitial).getCenterCell());

		// adds currentCell to roomCell's adj list
		roomCenterCell.addAdj(currentCell);
	}

	// public methods
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
		playerMap = new HashMap<String, Player>();
		playersList = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		theAnswer = new Solution();
		
		// set up file reader and scanner
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);

		// add rooms
		while (in.hasNextLine()) {
			String rowData = in.nextLine();

			if (rowData.substring(0, 2).equals("//"))
				continue;

			String[] tempRoom = rowData.split(", ");

			// throw exception if not a room or space
			if (!tempRoom[0].equals("Room") && !tempRoom[0].equals("Space") && !tempRoom[0].equals("Player") && !tempRoom[0].equals("Weapon"))
				throw new BadConfigFormatException("Setup file contains room type that is not Room or Space.");

			if (tempRoom[0].equals("Player")) {
				// do Player stuff
				Player player;
				String playerName = tempRoom[1];
				String color = tempRoom[2];
				String playerType = tempRoom[3];
				int row = Integer.parseInt(tempRoom[4]);
				int col = Integer.parseInt(tempRoom[5]);
				
				if(playerType.equals("human")) {
					player = new HumanPlayer(playerName, color, row, col);
				}else {
					player = new ComputerPlayer(playerName, color, row, col);
				}
				
				playerMap.put(playerName, player);
				playersList.add(player);
				
				Card card = new Card(playerName, CardType.PERSON);
				deck.add(card);
				
				// do weapon stuff
			}else if(tempRoom[0].equals("Weapon")){
				String name = tempRoom[1];
				Card card = new Card(name, CardType.WEAPON);
				deck.add(card);
				
				// do Room/Space stuff
			}else {
				String roomName = tempRoom[1];
				char roomInitial = tempRoom[2].charAt(0);
				Room room = new Room(roomName);
				roomMap.put(roomInitial, room);
				
				if(!tempRoom[0].equals("Space")) {
					Card card = new Card(roomName, CardType.ROOM);
					deck.add(card);
				}
			}
		}

		in.close();
		
		// shuffle and deal cards
		if(!setupConfigFile.contains("306")) {
			shuffleDeck();
			dealCards();
		}
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		// create arraylist for rows
		ArrayList<String[]> rows = new ArrayList<String[]>();
		FileReader reader = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(reader);
		roomCells = new HashMap<Character, ArrayList<BoardCell>>();
		highlightedCells = new ArrayList<BoardCell>();

		// add each row to arraylist
		while (in.hasNextLine()) {
			String[] splitRow = in.nextLine().split(",");
			rows.add(splitRow);
		}

		// close scanner
		in.close();

		// test for equal columns, throw exception if invalid
		testRowColSize(rows);

		// set num rows and cols
		numRows = rows.size();
		numColumns = rows.get(0).length;

		// allocate memory for grid array
		grid = new BoardCell[numRows][numColumns];

		// fill board
		fillBoard(rows);
	}

	private void fillBoard(ArrayList<String[]> rows) throws BadConfigFormatException {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				createCell(rows, row, col);
			}
		}
	}

	private void createCell(ArrayList<String[]> rows, int row, int col) throws BadConfigFormatException {
		// extract cell from row and column
		String[] currentRowData = rows.get(row);
		String cellData = currentRowData[col];
		char initial = cellData.charAt(0), symbol = '0';


		// instantiate boolean values to false
		// will be changed in switch-case below.
		boolean label, center, doorway, isSecretPassage;
		label = center = doorway = isSecretPassage = false;
		DoorDirection direction = DoorDirection.NONE;

		// test for initial in roomMap. Throw exception if initial not in map.
		if (!roomMap.containsKey(initial))
			throw new BadConfigFormatException("Room type not declared in " + setupConfigFile);

		// if initial length is 1, then it's a normal room.
		if (cellData.length() == 1) {
			direction = DoorDirection.NONE;

			// if initial length >1, then process second char value
		} else {
			symbol = cellData.charAt(1);
			switch (symbol) {
			case '*':
				center = true;
				break;
			case '#':
				label = true;
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

		// set room attributes
		
		// set secret passage
		if (isSecretPassage) {
			roomMap.get(initial).setSecretPassage(symbol);
			cell.setSecretPassage(symbol);
		}

		// set room's center cell
		if (center) {
			roomMap.get(initial).setCenterCell(cell);
			roomCells.put(initial, new ArrayList<BoardCell>());
		}

		// set room's label
		if (label) {
			roomMap.get(initial).setLabelCell(cell);
		}

		// set room walkway
		if (initial == 'W') {
			roomMap.get(initial).setWalkway();
		}
	}

	private void testRowColSize(ArrayList<String[]> rows) throws BadConfigFormatException {
		int rowCount = rows.get(0).length;
		for (String[] row : rows) {
			if (row.length != rowCount) {
				throw new BadConfigFormatException("Expected row: " + rowCount + ". Actual row: " + row.length);
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
		if (cell.isRoom()) {
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
		for (BoardCell c : startCell.getAdjList()) {
			if (!visited.contains(c) && !c.getOccupied()) {
				visited.add(c);
				if (numSteps == 1 && !c.getOccupied() || c.isRoomCenter()) {
					targets.add(c);
				} else {
					findAllTargets(c, numSteps - 1);
				}
				visited.remove(c);
			}
		}
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	public void dealCards() {
		ArrayList<Card> dealingDeck = new ArrayList<Card>(deck);
		ArrayList<Player> players = new ArrayList<Player>(playerMap.values());
		
		// give Answer 3 cards
		for(int i = 0; i < dealingDeck.size(); i++) {
			if(dealingDeck.get(i).getType() == CardType.PERSON) {
				theAnswer.person = dealingDeck.get(i);
				dealingDeck.remove(i);
				break;
			}
		}
		
		for(int i =0; i< dealingDeck.size(); i++){
			if(dealingDeck.get(i).getType() == CardType.WEAPON) {
				theAnswer.weapon = dealingDeck.get(i);
				dealingDeck.remove(i);
				break;
			}
		}
		
		for(int i = 0; i < dealingDeck.size(); i++) {
			if(dealingDeck.get(i).getType() == CardType.ROOM) {
				theAnswer.room = dealingDeck.get(i);
				dealingDeck.remove(i);
				break;
			}
		}
		
		// deal remaining cards to players
		while(dealingDeck.size() > 0) {
			for(int j = 0; j < players.size(); j++) {
				if(dealingDeck.size() > 0) {
					players.get(j).updateHand(dealingDeck.get(0));
					dealingDeck.remove(0);
				}
			}
		}
		
	}
	
	public boolean checkAccusation(Card person, Card room, Card weapon) {
		return person.equals(theAnswer.person)	&&
			   room.equals(theAnswer.room)		&&
			   weapon.equals(theAnswer.weapon);
	}
	
	public void setAnswer(Solution myAnswer) {
		theAnswer.person = myAnswer.person;
		theAnswer.room = myAnswer.room;
		theAnswer.weapon = myAnswer.weapon;
	}
	
	public Card handleSuggestion(ArrayList<Player> players, Player accuser) {
		
		// re-orient the players arraylist to start at the accuser
		ArrayList<Player> newPlayers = new ArrayList<Player>(players.subList(players.indexOf(accuser), players.size()));
		newPlayers.addAll(players.subList(0, players.indexOf(accuser)));

		// iterate through players to disprove suggestion
		for(Player player : newPlayers) {
			if(player.disproveSuggestion(theAnswer) == null || player.equals(accuser)) {
				continue;
			} else {
				return player.disproveSuggestion(theAnswer);
			}
		}
		
		return null;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// get cell sizes
		int cellWidth = getWidth()/numColumns;
		int cellHeight = getHeight()/numRows;
		
		// draw board cells
		drawBoardCells(g, cellWidth, cellHeight);
		
		// draw room labels
		Font font = new Font("Gill Sans MT Bold Italic", Font.BOLD, 18);
		g.setFont(font);
		g.setColor(Color.black);
		drawLabels(g, cellWidth, cellHeight);
		
		// draw players
		int indexVariable = 0;
		for(Player player : playersList) {			
			player.draw(g, cellWidth, cellHeight, indexVariable);
			indexVariable++;
		}
	}

	private void drawLabels(Graphics g, int cellWidth, int cellHeight) {
		for(Room room : roomMap.values()) {
			if(room.getLabelCell() == null) {
				continue;
			}
			int y = room.getLabelCell().getRow();
			int x = room.getLabelCell().getCol();
			String roomName = room.getName();
			
			g.drawString(roomName, x*cellWidth, y*cellHeight);
		}
	}

	private void drawBoardCells(Graphics g, int cellWidth, int cellHeight) {
		for(int r = 0; r < numRows; r++) {
			for(int c = 0; c < numColumns; c++) {
				grid[r][c].draw(g, cellWidth, cellHeight, c*cellWidth, r*cellHeight);
			}
		}
	}
	
	public void nextTurn() {
		if(!isFinished) {
			JOptionPane.showMessageDialog(null, "Please finish your turn!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			processTurn();
		}
	}
	
	public void processTurn() {
		int numPlayers = playerMap.size();
		int indexVariable = gameTurnCounter % numPlayers;
		
		Player turn = playersList.get(indexVariable);
		int roll = rollDice();
		GameControlPanel.setTurn(turn, roll);

		
		if(indexVariable == 0) {
			humanPlayerTurn(indexVariable, roll);
		} else {
			computerPlayerTurn(indexVariable, roll);
		}
	}
	
	private void humanPlayerTurn(int indexVariable, int roll) {
		isFinished = false;
		Player playerTurn = playersList.get(indexVariable);
		highlightTargets(indexVariable, roll);
	}
	
	/*
	public boolean processTurn() {
		int numPlayers = playerMap.size();
		int indexVariable = gameTurnCounter % numPlayers;
		
		Player turn = playersList.get(indexVariable);
		int roll = rollDice();
		
		GameControlPanel.setTurn(turn, roll);
		
		if(indexVariable == 0) {
			highlightTargets(indexVariable, roll);
			return checkHumanPlayerTurn();
		}else {
			computerPlayerTurn(indexVariable, roll);
			return true;
		}
	}
	
	private boolean checkHumanPlayerTurn() {
		// check is finished
		
		// check if target selected or accusation made
		// if so, set playerFinished flag to true and update gameTurnCounter
		if(targetSelected || accusationMade) {			
			isFinished = true;
		}else {
			isFinished = false;
		}
		
		return isFinished;
	}
	*/

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public void mouseClicked(MouseEvent e) {
		
		if(isFinished) {
			//JOptionPane.showMessageDialog(null, "It's not your turn!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int cellHeight = getHeight()/numRows;
		int cellWidth = getWidth()/numColumns;
		int row = (int) (Math.ceil(e.getY()*1.0 / cellHeight)) - 1;		// calculate row #
		int column = (int) (Math.ceil(e.getX()*1.0 / cellWidth)) - 1;	// calculate column #
		
		if(highlightedCells.contains(getCell(row, column))) {
			BoardCell cell = getCell(row, column);
			// find location
				//if(getCell(playersList.get(0).getRow(), playersList.get(0).getCol()).isRoomCenter()&&targets.size()>0) {
					// player was in a room
				//	roomMap.get(cell.getInitial()).subtractOccupant();
				//}
			
			cell.setOccupied(false);
			if(cell.getInitial() != 'W') {
				BoardCell roomCenter = roomMap.get(cell.getInitial()).getCenterCell();
				playersList.get(0).setLocation(roomCenter);
				playersList.get(0).setRoom(roomMap.get(cell.getInitial()));
				roomCenter.setOccupied(true);
			}else {
				playersList.get(0).setLocation(row, column);
				getCell(row, column).setOccupied(true);
				playersList.get(0).setRoom(null);
			}
			//targetSelected = true;
			
			// if turn is over
			for(BoardCell cellToBeUnhighlighted : highlightedCells) {
				cellToBeUnhighlighted.setIsHighlighted(false);
			}
			highlightedCells.clear();
			
			// turn is over
			gameTurnCounter++;
			isFinished = true;
		}else {
			JOptionPane.showMessageDialog(null, "You can't go there!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		repaint();
	}
	
	private void computerPlayerTurn(int indexVariable, int roll) {
		//isFinished = true;
		//targetSelected = false;
		
		Player computerTurn = playersList.get(indexVariable);
		BoardCell startCell = getCell(computerTurn.getRow(), computerTurn.getCol());
		calcTargets(startCell, roll);
		
		BoardCell selectedCell = computerTurn.selectTarget(targets, roomMap);
		
		//if(startCell.isRoomCenter()&&targets.size()>0) {
		//		roomMap.get(startCell.getInitial()).subtractOccupant();
			//}
		startCell.setOccupied(false);
		if(selectedCell.getInitial() != 'W') {
			computerTurn.setRoom(roomMap.get(selectedCell.getInitial()));
			//roomMap.get(selectedCell.getInitial()).addOccupant();
		}else {
			computerTurn.setRoom(null);
		}
		
		computerTurn.setLocation(selectedCell);
		selectedCell.setOccupied(true);
		
		gameTurnCounter++;
		
		repaint();
	}
	
	private void highlightTargets(int playerNum, int roll) {
		fillRoomCells();
		Player player = playersList.get(playerNum);
		BoardCell startCell = getCell(player.getRow(), player.getCol());
		calcTargets(startCell, roll);
		for(BoardCell cell : targets) {
			if(cell.getInitial()!='W') {
				highlightRoom(cell.getInitial(), true);
			} else {
				cell.setIsHighlighted(true);
				highlightedCells.add(cell);
			}
			
		}
		
		repaint();
	}
	
	private void fillRoomCells() {
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				BoardCell cell = getCell(r,c);
				if(cell.getInitial()!='W'&&cell.getInitial()!='X') {
					roomCells.get(cell.getInitial()).add(getCell(r,c));
				}
			}
		}
	}
	
	private void highlightRoom(char initial, boolean setHighlight) {
		for(BoardCell cell : roomCells.get(initial)) {
			cell.setIsHighlighted(setHighlight);
			if(setHighlight)
				highlightedCells.add(cell);
		}
	}
	
	public int rollDice() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Map<Character, Room> getRoomMap(){
		return roomMap;
	}
	
	public Map<String, Player> getPlayers(){
		return playerMap;
	}
	
	public Player getPlayer(String name){
		return playerMap.get(name);
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public Solution getAnswer() {
		return theAnswer;
	}

	public Boolean isTarget(BoardCell cell) {
		return targets.contains(cell);
	}
	
	public ArrayList<Player> getPlayersList(){
		return playersList;
	}
	
}
