package cellsociety_team02.cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Cell extends StackPane {
	
	protected ArrayList<Cell> myNeighbours = new ArrayList<Cell>();
	protected ArrayList<Cell> myAdjacentNeighbours = new ArrayList<Cell>();
	private int xPos;
	private int yPos;
	private int currentState;
	private int nextState;
	private double survivalVal;
	private double replicationVal;
	private Shape cellBackground;
	private ImageView cellOverlay;
	private Color[] myColors;
	private List<CellOccupant> occupants;
	private boolean stateChanged = false;
	private int maxOccupancy = 10;
	private int myGridSize;
	private Cell[][] myGridArray;
	
	public Cell() {
		
	}
	
	public Cell(int xPosition, int yPosition, int startingState, Color[] colors, int sideLength, int gridSize, Cell[][] gridArray) {
		xPos = xPosition;
		yPos = yPosition;
		currentState = startingState;
		myColors = colors;
		myGridSize = gridSize;
		myGridArray = gridArray;
		occupants = new ArrayList<>();
		cellBackground = new Rectangle(sideLength, sideLength, myColors[currentState]);
		this.getChildren().add(cellBackground);
		initializeStateCycler();
	}

	private void initializeStateCycler() {
		this.setOnMouseClicked((event) ->{
			if(++currentState >= myColors.length) currentState = 0;
			cellBackground.setFill(myColors[currentState]);
		});
	}
	
	public List<Cell> getNeighbours(){
		return myNeighbours;
	}
	
	public void setMyNeighbours(ArrayList<Cell> neighbours) {
		myNeighbours = neighbours;
	}
	
	public Cell getNeighbour(int x, int y) {
		for(Cell cell:this.getNeighbours()) {
			if(cell.getX() == x && cell.getY() == y) {
				return cell;
			}
		}
		return null;
	}
	
	public List<Cell> getAdjacentNeighbours(){
		 myAdjacentNeighbours.clear();
		if(yPos<myGridSize - 1) myAdjacentNeighbours.add(myGridArray[xPos][yPos+1]); //East
		if(yPos>0) myAdjacentNeighbours.add(myGridArray[xPos][yPos-1]); //West
		if(xPos<myGridSize - 1) myAdjacentNeighbours.add(myGridArray[xPos+1][yPos]); //South
		if(xPos>0) myAdjacentNeighbours.add(myGridArray[xPos-1][yPos]); //North
		return myAdjacentNeighbours;
	}


	
	public Cell chooseRandomNeighbour(List<Cell> neighbours) {
		Random rand = new Random();
		return neighbours.get(rand.nextInt(neighbours.size()));
	}
	
	public boolean isOccupied() {
		return(occupants.size() > 0);
	}
	
	public boolean atOccupancy() {
		return(occupants.size() >= maxOccupancy);
	}
	
	public List<CellOccupant> getOccupants(){
		return new ArrayList<CellOccupant>(occupants);
	}
	
	public void addOccupants(CellOccupant newOccupant, int number) {
		if(occupants.size() == 0) cellNowOccupied(newOccupant);
		for(int i = 0; i<number; i++) {
			if(atOccupancy()) break;
			occupants.add(newOccupant);
		}
	}
	
	protected void removeOccupant(CellOccupant occupant) {
		occupants.remove(occupant);
		if(occupants.size() <= 0) cellIsEmpty();
	}
	
	private void cellIsEmpty() {
		this.getChildren().remove(cellOverlay);
	}
	
	private void cellNowOccupied(CellOccupant newOccupant) {
		maxOccupancy = newOccupant.getDesiredOccupancy();

		Image image = newOccupant.drawImage();
		cellOverlay = new ImageView(image);
		cellOverlay.setFitHeight(cellBackground.getBoundsInParent().getHeight()/1.5);
		cellOverlay.setFitWidth(cellBackground.getBoundsInParent().getWidth()/1.5);
		this.getChildren().add(cellOverlay);
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
			cellBackground.setFill(myColors[currentState]);
			stateChanged = false;
		}
	}
	
	public boolean stateHasChanged() {
		return stateChanged;
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

	// add an getmyColors() method
	public Color[] getMyColors() {
		return myColors;
	}
	

	public double survivalTime() {
		return survivalVal;
	}
	
	public double replicationTime() {
		return replicationVal;
	}
	
	public void setSurvivalTime(double newDaysUntil) {
		survivalVal = newDaysUntil;
	}
	
	public void setReplicationTime(double newDaysUntil) {
		replicationVal = newDaysUntil;
	}
	
	public void updateInfiniteVitals(int state, Color[] colors) {
		currentState = state;
		cellBackground.setFill(colors[state]);
	}

}
