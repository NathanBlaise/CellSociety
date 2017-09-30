package cellsociety_team02.cells;

import java.util.List;

public class CellOccupant {
	private List<Cell> viewableNeighbours;
	private Cell cellOccupied;
	protected int viewScope;
	
	public CellOccupant(Cell cell) {
		this.viewScope = viewScope;
		this.cellOccupied = cell;
	}
	
	public List<Cell> orientation() {
		return viewableNeighbours;
	}
	
	public void updateLocation() {} 
}
