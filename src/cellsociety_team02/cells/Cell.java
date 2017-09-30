package cellsociety_team02.cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cell {
	
	protected ArrayList<Cell> myNeighbours = new ArrayList<Cell>();
	private int xPos;
	private int yPos;
	private int currentState;
	private int nextState;
	private int survivalVal;
	private int replicationVal;
	private Shape myShape;
	private Color[] myColors;
	private int myGridSize;
	private Cell[][] myGridArray;
	private List<CellOccupant> occupants;
	private boolean stateChanged = false;
	
	public Cell(int xPosition, int yPosition, int startingState, Color[] colors, int sideLength, int gridSize, Cell[][] gridArray) {
		xPos = xPosition;
		yPos = yPosition;
		currentState = startingState;
		myColors = colors;
		myShape = new Rectangle(sideLength, sideLength, myColors[currentState]);
		myGridSize = gridSize;
		myGridArray = gridArray;
	}
	
	public List<Cell> getNeighbours(){
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
	
	public List<Cell> getAdjacentNeighbours(){
		 myNeighbours.clear();
		if(yPos<myGridSize - 1) myNeighbours.add(myGridArray[xPos][yPos+1]); //East
		if(yPos>0) myNeighbours.add(myGridArray[xPos][yPos-1]); //West
		if(xPos<myGridSize - 1) myNeighbours.add(myGridArray[xPos+1][yPos]); //South
		if(xPos>0) myNeighbours.add(myGridArray[xPos-1][yPos]); //North
		return myNeighbours;
	}
	
	public Cell chooseRandomNeighbour(List<Cell> neighbours) {
		Random rand = new Random();
		return neighbours.get(rand.nextInt(neighbours.size()));
	}
	
	public boolean isOccupied() {
		return(occupants.size() > 0);
	}
	
	public List<CellOccupant> getOccupants(){
		return occupants;
	}
	
	public void addOccupants(CellOccupant newOccupant, int number) {
		if(occupants.size() == 0) cellNowOccupied(newOccupant);
		for(int i = 0; i<number; i++) {
			occupants.add(newOccupant);
		}
	}
	
	public void removeOccupant(CellOccupant occupant) {
		occupants.remove(occupant);
		if(occupants.size() <= 0) cellIsEmpty();
	}
	
	private void cellIsEmpty() {
		//remove the drawing
	}
	
	private void cellNowOccupied(CellOccupant newOccupant) {
		//add the drawing
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
	
	public int survivalTime() {
		return survivalVal;
	}
	
	public int replicationTime() {
		return replicationVal;
	}
	
	public void setSurvivalTime(int newDaysUntil) {
		survivalVal = newDaysUntil;
	}
	
	public void setReplicationTime(int newDaysUntil) {
		replicationVal = newDaysUntil;
	}

}
