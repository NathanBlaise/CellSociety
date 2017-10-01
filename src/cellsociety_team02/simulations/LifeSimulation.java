package cellsociety_team02.simulations;

import java.util.List;

import cellsociety_team02.cells.Cell;

public class LifeSimulation extends Simulation {
	private final int DEAD = 0;
	private final int ALIVE = 1;
	
	private String layoutFile = "data/Life.xml";
	
	public LifeSimulation() {
		super();
		super.setDefaultVariables(layoutFile, null, null, null);
		super.changeInitConfig(layoutFile);
	}
	
	@Override
	public void updateCell(Cell cell) {
		cell.setNextState(DEAD);
		calculateState(cell);
	}
	
	private void calculateState(Cell cell) {
		int total = getNumNeighbours(cell);
		if(cell.getCurrentState() == ALIVE && total == 2) cell.setNextState(ALIVE);
		if(total == 3) cell.setNextState(ALIVE);	
	}
	
	private int getNumNeighbours(Cell cell) {
		List<Cell> neighbours = cell.getNeighbours();
		int total = 0;
		for(Cell neighbour:neighbours) {
			total += neighbour.getCurrentState();
		}
		return total;
	}
	
}
