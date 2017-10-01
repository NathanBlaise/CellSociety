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
	private List<CellOccupant> occupants;
	private boolean stateChanged = false;
	
	public Cell(int xPosition, int yPosition, int startingState, Color[] colors, int sideLength) {
		xPos = xPosition;
		yPos = yPosition;
		currentState = startingState;
		myColors = colors;
		myShape = new Rectangle(sideLength, sideLength, myColors[currentState]);
	}
	
	public List<Cell> getNeighbours(){
		return myNeighbours;
	}
	
	public void setMyNeighbours(ArrayList<Cell> neighbours) {
		myNeighbours = neighbours;
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

	// add an getmyColors() method
	public Color[] getMyColors() {
		return myColors;
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
