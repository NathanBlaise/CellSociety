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
	protected Random rand;
	protected boolean movedCell;
	
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
		int[] xCoord = {xPos, xPos+1, xPos-1};
		int[] yCoord = {yPos, yPos+1, yPos-1};
		for(int x: xCoord) {
			for(int y: yCoord) {
				if(x>0 && y>0 && x<(myGridSize-1) && y<(myGridSize-1)) {
					myNeighbours.add(myGridArray[x][y]);
				}
			}
		}
		return myNeighbours;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	public Shape getShape() {
		return myShape;
	}
	
	
	public void updateState() {
		currentState = nextState;
		myShape.setFill(myColors[currentState]);
	}
	
	public void setNextState(int state) {
		nextState = state;
	}
	
	public int getNextState() {
		return nextState;
	}
	
	public int getCurrentState() {
		return currentState;
	}

	public void moveToRandomPlace() {
		movedCell = false;
		while(!movedCell) {
			int i = rand.nextInt(myGridSize);
			int j = rand.nextInt(myGridSize);
			if(myGridArray[i][j].getCurrentState() == 0 && myGridArray[i][j].getNextState() == 0) {
				myGridArray[i][j].setNextState(currentState);
				currentState = 0;
				movedCell = true;
			}
		}
	}
}
