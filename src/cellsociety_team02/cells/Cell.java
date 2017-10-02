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

/**
 * Main cell class. Holds a "patch of ground" in the form of a cell background and a cell overlay
 * that represents a cell occupant. If new shapes were to be implemented, they would be changed here
 * with sub classes that modify the shape for the patch of ground. Functionally holds data about its
 * state, its occupants, and its neighbours.
 * @author benwelton
 *
 */
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
	
	/**
	 * Return the designated neighbours, as fed in by the grid subclass
	 * @return
	 */
	public List<Cell> getNeighbours(){
		return myNeighbours;
	}
	
	/**
	 * Set the surrounding neighbours
	 * @param neighbours
	 */
	public void setMyNeighbours(ArrayList<Cell> neighbours) {
		myNeighbours = neighbours;
	}
	
	/**
	 * Return the nearest neighbour in the x, y direction. If none exists, return null
	 * @param x
	 * @param y
	 * @return
	 */
	protected Cell getNeighbour(int x, int y) {
		for(Cell cell:this.getNeighbours()) {
			if(cell.getX() == x && cell.getY() == y) {
				return cell;
			}
		}
		return null;
	}
	
	/**
	 * Get the adjacent neighbours. Would eventually be calculated by the grid class and only return
	 * the corresponding list.
	 * @return
	 */
	public List<Cell> getAdjacentNeighbours(){
		 myAdjacentNeighbours.clear();
		if(yPos<myGridSize - 1) myAdjacentNeighbours.add(myGridArray[xPos][yPos+1]); //East
		if(yPos>0) myAdjacentNeighbours.add(myGridArray[xPos][yPos-1]); //West
		if(xPos<myGridSize - 1) myAdjacentNeighbours.add(myGridArray[xPos+1][yPos]); //South
		if(xPos>0) myAdjacentNeighbours.add(myGridArray[xPos-1][yPos]); //North
		return myAdjacentNeighbours;
	}

	/**
	 * Finds a random neighbour from the neighbour set
	 * @param neighbours
	 * @return
	 */
	public Cell chooseRandomNeighbour(List<Cell> neighbours) {
		Random rand = new Random();
		return neighbours.get(rand.nextInt(neighbours.size()));
	}
	
	/**
	 * Checks if there are any occupants on the cell
	 * @return
	 */
	public boolean isOccupied() {
		return(occupants.size() > 0);
	}
	
	/**
	 * Check if the cell is at maximum occupancy
	 * @return
	 */
	public boolean atOccupancy() {
		return(occupants.size() >= maxOccupancy);
	}
	
	/**
	 * Return a copy of the cell occupant list
	 * @return
	 */
	public List<CellOccupant> getOccupants(){
		return new ArrayList<CellOccupant>(occupants);
	}
	
	/**
	 * Add a specified number of new occupants to the cell or until the cell is at occupancy
	 * @param newOccupant
	 * @param number
	 */
	public void addOccupants(CellOccupant newOccupant, int number) {
		if(occupants.size() == 0) cellNowOccupied(newOccupant);
		for(int i = 0; i<number; i++) {
			if(atOccupancy()) break;
			occupants.add(newOccupant);
		}
	}
	
	/**
	 * Remove a designated occupant and update the image if no occupants remain
	 * @param occupant
	 */
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
	
	/**
	 * Change the state of the patch of ground
	 */
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

	public Color[] getMyColors() {
		return myColors;
	}
	
	//methods to view basic items held on the patch of ground
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
	
	/**
	 * update cell color for infinite grid case
	 * @param state
	 * @param colors
	 */
	public void updateInfiniteVitals(int state, Color[] colors) {
		currentState = state;
		cellBackground.setFill(colors[state]);
	}

}
