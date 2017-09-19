package cellsociety_team02.cells;

import java.util.ArrayList;

public class Cell {
	
	protected ArrayList<Cell> myNeighbours;
	protected int xPos;
	protected int yPos;
	protected int currentState;
	protected int nextState;
	protected int myShape;
	protected String[] myColors;
	
	public Cell(int xPos, int yPos, int startingState, String[] colors) {
		xPos = xPos;
		yPos = yPos;
		currentState = startingState;
		myColors = colors;
	}
	
	public ArrayList<Cell> getNeighbours(){
		
		return myNeighbours;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void updateState() {
		currentState = nextState;
	}
	
}
