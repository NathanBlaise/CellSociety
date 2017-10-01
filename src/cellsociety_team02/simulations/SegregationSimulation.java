package cellsociety_team02.simulations;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cellsociety_team02.cells.Cell;

public class SegregationSimulation extends Simulation{
	
	private final int EMPTY = 0;
	private final int POPULATION_1 = 1;
	private final int POPULATION_2 = 2;
	
	private Queue<Cell> emptyCells;
	private String layoutFile = "data/Segregation.xml";
	private String[] vars = {"percentOfNeighbours"};
	private double[] vals = {0.5};
	private double[] maxs = {1.0};
	private int percentIndex;
	
	public SegregationSimulation() {
		super();
		super.setDefaultVariables(layoutFile, vars, vals, maxs);
		emptyCells = new LinkedList<>();
		super.changeInitConfig(layoutFile);
		percentIndex = super.variables.indexOf("percentOfNeighbours");
	}

	@Override
	public void primeCell(Cell cell) {
		if(cell.getCurrentState() == EMPTY) emptyCells.add(cell);
	}
	
	@Override
	public void clearValues() {
		emptyCells = new LinkedList<>();
	}
	
	@Override
	public void updateCell(Cell cell) {
		if(cell.getCurrentState() == EMPTY) return;
		
		List<Cell> neighbours = cell.getNeighbours();
		int total = 0;
		int occupiedSpots = 0;
		
		for(Cell neighbour:neighbours) {
			if(neighbour.getCurrentState() == cell.getCurrentState()) {
				total++;
				occupiedSpots++;
			}else if(neighbour.getCurrentState() != EMPTY) {
				occupiedSpots++;
			}
		}
		if(occupiedSpots == 0) return;
		if((double) total/occupiedSpots < variableVals.get(percentIndex)) {
			moveToEmptyLocation(cell);
		}
	}
	
	private void moveToEmptyLocation(Cell cell) {
		Cell newLocation = emptyCells.poll();
		newLocation.setNextState(cell.getCurrentState());
		cell.setNextState(EMPTY);
		emptyCells.add(cell);
	}


}
