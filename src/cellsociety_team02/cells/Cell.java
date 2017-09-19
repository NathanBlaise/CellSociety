package cellsociety_team02.cells;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cell {
	
	protected ArrayList<Cell> myNeighbours;
	protected int xPos;
	protected int yPos;
	protected int currentState;
	protected int nextState;
	protected Shape myShape;
	protected Color[] myColors;
	
	public Cell(int xPos, int yPos, int startingState, Color[] colors, int sideLength) {
		xPos = xPos;
		yPos = yPos;
		currentState = startingState;
		myColors = colors;
		myShape = new Rectangle(sideLength, sideLength, myColors[currentState]);
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
		myShape.setFill(myColors[currentState]);
	}
	
	public int getCurrentState() {
		return currentState;
	}
	
	
}
