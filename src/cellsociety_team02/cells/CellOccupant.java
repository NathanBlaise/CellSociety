package cellsociety_team02.cells;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public abstract class CellOccupant {
	private List<Cell> viewableNeighbours;
	private List<Cell> nonViewableNeighbours;
	protected int viewScope;
	protected int desiredOccupancy;
	private Cell currentCell;
	
	public CellOccupant(Cell cell) {
		this.viewScope = viewScope;
		this.currentCell = cell;
		initLists();
		changeOrientation(cell.getNeighbours().get(0));
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
	
	public void move(Cell newLocation) {
		if(currentCell.atOccupancy()) return;
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
	
	private void offmapOrientation(Cell newLocation) {
		initLists();
		nonViewableNeighbours = newLocation.getNeighbours();
	}
	
	protected abstract Image drawImage();

	protected int getDesiredOccupancy() {
		return desiredOccupancy;
	}
}
