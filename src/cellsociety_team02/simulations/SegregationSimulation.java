package cellsociety_team02.simulations;

import java.util.List;

import cellsociety_team02.cells.Cell;

public class SegregationSimulation extends Simulation{
	
	private final double PERCENTAGE_AGENT = 0.3;
	
	private int total;
	
	@Override
	public void updateCell(Cell cell) {
		List<Cell> neighbours = cell.getNeighbours();
		total = 0;
		for(Cell neighbour:neighbours) {
			if(neighbour.getCurrentState() == cell.getCurrentState()) total += 1;
		}
		if(total/neighbours.size() < PERCENTAGE_AGENT) {
			cell.moveToRandomPlace();
		}
	}
}
