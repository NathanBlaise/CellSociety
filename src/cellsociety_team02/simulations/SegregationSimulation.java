package cellsociety_team02.simulations;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cellsociety_team02.cells.Cell;

public class SegregationSimulation extends Simulation{
	
	private final int EMPTY = 0;
	private final int POPULATION_1 = 1;
	private final int POPULATION_2 = 2;
	
	private double percentOfNeighbors = 0.5;
	private Queue<Cell> emptyCells;
	private String layoutFile = "data/Segregation.xml";
	
	public SegregationSimulation() {
		super();
		super.layoutFile = this.layoutFile;
		super.defaultFile = this.layoutFile;
		super.changeInitConfig(layoutFile);
		emptyCells = new LinkedList<>();
		if(queryAttributes("percentOfNeighbors") != null) {
			percentOfNeighbors = Double.parseDouble(queryAttributes("percentOfNeighbors"));
		}
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
		if((double) total/occupiedSpots < percentOfNeighbors) {
			moveToEmptyLocation(cell);
		}
	}
	
	public void addEmptyCell(Cell cell) {
		emptyCells.add(cell);
	}
	
	private void moveToEmptyLocation(Cell cell) {
		Cell newLocation = emptyCells.poll();
		newLocation.setNextState(cell.getCurrentState());
		cell.setNextState(EMPTY);
		this.addEmptyCell(cell);
	}
	
	//for later gui interactivity
	public void changePercentOfNeighbors(int newPercent) {
		percentOfNeighbors = newPercent;
	}
}
