package cellsociety_team02.cells;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cell {
	
	protected ArrayList<Cell> myNeighbours = new ArrayList<Cell>();
	protected int xPos;
	protected int yPos;
	protected int currentState;
	protected int nextState;
	protected Shape myShape;
	protected Color[] myColors;
	protected int myGridSize;
	protected Cell[][] myGridArray;
	protected Random rand = new Random();
	protected boolean movedCell;
	protected boolean stateChanged = false;
	
	public Cell(int xPosition, int yPosition, int startingState, Color[] colors, int sideLength, int gridSize, Cell[][] gridArray) {
		xPos = xPosition;
		yPos = yPosition;
		currentState = startingState;
		myColors = colors;
		myShape = new Rectangle(sideLength, sideLength, myColors[currentState]);
		myGridSize = gridSize;
		myGridArray = gridArray;
	}
	
	public ArrayList<Cell> getNeighbours(){
		myNeighbours.clear();
		int[] xCoord = {xPos, xPos+1, xPos-1};
		int[] yCoord = {yPos, yPos+1, yPos-1};
		for(int x: xCoord) {
			for(int y: yCoord) {
				if(x>-1 && y>-1 && x<(myGridSize) && y<(myGridSize)) {
					myNeighbours.add(myGridArray[x][y]);
				}
			}
		}
		myNeighbours.remove(myGridArray[xPos][yPos]);
		return myNeighbours;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public void updateState() {
		if(stateChanged) {
			currentState = nextState;
			myShape.setFill(myColors[currentState]);
			stateChanged = false;
		}
	}
	
	public void setNextState(int state) {
		nextState = state;
		stateChanged = true;
	}
	
	public int getNextState() {
		return nextState;
	}
	
	public int getCurrentState() {
		return currentState;
	}
	
	public Shape getShape() {
		return myShape;
	}

}
