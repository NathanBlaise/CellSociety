package cellsociety_team02.simulations;

import java.util.List;
import java.util.Queue;

import cellsociety_team02.cells.Cell;

public class SegregationSimulation extends Simulation{
	
	private final int EMPTY = 0;
	private final int GROUP1 = 1;
	private final int GROUP2 = 2;
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
