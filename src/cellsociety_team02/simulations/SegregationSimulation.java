package cellsociety_team02.simulations;

import java.util.List;

import cellsociety_team02.cells.Cell;

public class SegregationSimulation extends Simulation{
	
	private final double PERCENTAGE_AGENT = 0.3;
	
	private String layoutFile = "data/Segregation.xml";
	private int total;
	
	public SegregationSimulation() {
		super();
		super.layoutFile = this.layoutFile;
		super.loadAttributes();
	}
	
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
