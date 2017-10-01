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
	}
	
	public void updateOrientation(Cell newLocation) {
		if(!currentCell.getNeighbours().contains(newLocation)) return;
		
		int index = currentCell.getNeighbours().indexOf(newLocation);
		initLists();
		List<Cell> neighbours = new ArrayList<>(currentCell.getNeighbours());
	}
	
	protected abstract Image drawImage();

	protected int getDesiredOccupancy() {
		return desiredOccupancy;
	}
}
