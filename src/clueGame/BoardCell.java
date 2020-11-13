package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/** 
 * @author Caleb Farnie
 * @author Joshua Josey
 */

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	// instance variables
	private int row;
	private int col;
	private char initial;
	private char secretPassage;
	private boolean roomLabel;
	private boolean roomCenter;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private DoorDirection doorDirection;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();

	// constructors
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.isOccupied = false;
	}
	
	public BoardCell(int row, int col, char initial, boolean roomLabel, boolean roomCenter, DoorDirection doorDirection, boolean isDoorway) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		this.roomLabel = roomLabel;
		this.roomCenter = roomCenter;
		this.doorDirection = doorDirection;
		this.isDoorway = isDoorway;
		this.isOccupied = false;
	}
	
	// public methods
	public char getInitial() {
		return this.initial;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public void addAdj(BoardCell adj) {
		this.adjList.add(adj);
	}
	
	public Set<BoardCell> getAdjList(){
		return this.adjList;
	}
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isRoom() {
		return this.isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		if(this.roomCenter) {
			return false;
		}else {
			return this.isOccupied;
		}
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}

	public DoorDirection getDoorDirection() {
		return this.doorDirection;
	}

	public boolean isDoorway() {
		return this.isDoorway;
	}

	public boolean isLabel() {
		return this.roomLabel;
	}

	public boolean isRoomCenter() {
		return this.roomCenter;
	}

	public char getSecretPassage() {
		return this.secretPassage;
	}
	
	public boolean isWalkway() {
		return (this.initial == 'W' || this.isDoorway || this.roomCenter);
	}
	
	public boolean equals(BoardCell cell) {
		return this.row == cell.getRow() && this.col == cell.getCol();
	}
	
	public void draw(Graphics g, int width, int height, int dx, int dy) {
		
		// draw cells
		if(initial == 'W') {
			g.setColor(Color.black);
			g.fillRect(dx, dy, width, height);
			g.setColor(Color.white);
			g.drawRect(dx, dy, width, height);
		}else if(initial == 'C') {				//Coruscant
			g.setColor(Color.gray);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'G') {				//Geonosis
			g.setColor(Color.orange);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'T') {				//Tattoine
			g.setColor(Color.yellow);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'K') {				//Geonosis
			g.setColor(Color.green);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'D') {				//Degobah
			g.setColor(Color.magenta);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'E') {				//Endor
			g.setColor(Color.green);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'B') {				//Bespin
			g.setColor(Color.orange);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'Y') {				//Yavin 4
			g.setColor(Color.blue);
			g.fillRect(dx, dy, width, height);
		}else if(initial == 'H') {				//Hoth
			g.setColor(Color.white);
			g.fillRect(dx, dy, width, height);
		}else{									//Everything else
			g.setColor(Color.darkGray);
			g.fillRect(dx, dy, width, height);
		}
		
		if(isDoorway()) {
			g.setColor(Color.red);
			switch(doorDirection) {
			case UP:
				g.fillRect(dx, dy, width, height/5);
				break;
			case DOWN:
				g.fillRect(dx, dy+height*4/5, width, height/5);
				break;
			case LEFT:
				g.fillRect(dx, dy, width/5, height);
				break;
			case RIGHT:
				g.fillRect(dx+width*4/5, dy, width/5, height);
				break;
			}
				
		}
	}
	
	/*
	 * Couruscant = grey/ purple
	 * geonosis = oarnge
	 * 
	 */
}
