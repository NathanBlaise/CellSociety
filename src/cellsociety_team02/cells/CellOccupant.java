package cellsociety_team02.cells;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

/**
 * Superclass for the occupants on the cells. These should hold information about direction and control
 * motion of the occupants from cell to cell
 * @author benwelton
 *
 */
public abstract class CellOccupant {
	private List<Cell> viewableNeighbours;
	private List<Cell> nonViewableNeighbours;
	protected int desiredOccupancy;
	private Cell currentCell;
	
	public CellOccupant(Cell cell) {
		this.currentCell = cell;
		initLists();
	}
	
	private void initLists() {
		viewableNeighbours = new ArrayList<>();
		nonViewableNeighbours = new ArrayList<>();
	}
	
	public List<Cell> getViewableNeighbours() {
		return viewableNeighbours;
	}
	
	public List<Cell> getNonViewableNeighbours(){
		return nonViewableNeighbours;
	}
	
	/**
	 * Moves an occupant to the new location and updates the orientation accordingly
	 * @param newLocation
	 */
	public void move(Cell newLocation) {
		currentCell.removeOccupant(this);
		newLocation.addOccupants(this, 1);
		
		int xDiff = newLocation.getX() - currentCell.getX();
		int yDiff = newLocation.getY() - currentCell.getY();
		int orientationX = newLocation.getX() + xDiff;
		int orientationY = newLocation.getY() + yDiff;
		Cell newOrientation = newLocation.getNeighbour(orientationX, orientationY);
		
		if(newOrientation != null) {
			changeOrientation(newOrientation);
		}else {
			offmapOrientation(newLocation);
		}
		
		currentCell = newLocation;
		
	}
	
	/**
	 * Change the direction that the occupant is facing by pointing them towards a new cell.
	 * Changes the viewable neighbours
	 * @param newLocation
	 */
	public void changeOrientation(Cell newLocation) {
		if(!currentCell.getNeighbours().contains(newLocation)) return;

		initLists();
		List<Cell> neighbours = new ArrayList<>(currentCell.getNeighbours());
		List<Cell> possibleViewables = new ArrayList<>(newLocation.getAdjacentNeighbours());
		for(Cell poss:neighbours) {
			if(possibleViewables.contains(poss)) {
				viewableNeighbours.add(poss);
			}else {
				nonViewableNeighbours.add(poss);
			}
		}
		viewableNeighbours.add(newLocation);
	}
	
	//edge case when there are no viewable neighbours
	private void offmapOrientation(Cell newLocation) {
		initLists();
		nonViewableNeighbours = newLocation.getNeighbours();
	}
	
	/**
	 * Must override this method to ensure that an image is specified for a new occupant
	 * @return
	 */
	protected abstract Image drawImage();

	protected int getDesiredOccupancy() {
		return desiredOccupancy;
	}
}
