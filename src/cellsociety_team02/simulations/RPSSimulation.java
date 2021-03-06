package cellsociety_team02.simulations;

import cellsociety_team02.cells.Cell;

/**
 * Rock paper scissors simulation that has three types of cells where each beats one and loses to the
 * other. Colors spawn offspring in empty locations and can take several "hits" before they lose to 
 * the other cell type
 * @author benwelton
 *
 */
public class RPSSimulation extends Simulation {
	private final int EMPTY = 0;
	private final int ROCK = 1;
	private final int PAPER = 2;
	private final int SCISSORS = 3;
	
	private String layoutFile = "data/RPS.xml";
	private String[] vars = {"lifeLength"};
	private double[] vals = {10};
	private double[] maxs = {10};
	private int lifeLengthIndex;
	
	public RPSSimulation() {
		super();
		super.setDefaultVariables(layoutFile, vars, vals, maxs);
		super.changeInitConfig(layoutFile);
		lifeLengthIndex = variables.indexOf("lifeLength");
	}
	
	@Override
	public void primeCell(Cell cell) {
		cell.setSurvivalTime(variableVals.get(lifeLengthIndex));
	}
	
	@Override
	public void updateCell(Cell cell) {
		Cell randNeighbour = cell.chooseRandomNeighbour(cell.getNeighbours());
		
		if(cell.getCurrentState() == randNeighbour.getCurrentState()) {
			return; //draw
		}
		else if(cell.getCurrentState() == EMPTY) {
			spawnOffspring(cell, randNeighbour);
		}else {
			cellsFight(cell, randNeighbour);
		}
	}
	
	private void cellsFight(Cell cell, Cell randNeighbour) {
		if(firstCellWins(cell, randNeighbour)) {
			cell.setSurvivalTime(cell.survivalTime() + 1);
		}else if(firstCellWins(randNeighbour, cell)) {
			cell.setSurvivalTime(cell.survivalTime() - 1);
		}
		
		if(cell.survivalTime() <= 0) cell.setNextState(randNeighbour.getCurrentState());
	}

	private void spawnOffspring(Cell cell, Cell neighbour) {
		if(neighbour.survivalTime() <= 1) return;
		
		cell.setNextState(neighbour.getCurrentState());
		cell.setSurvivalTime(neighbour.survivalTime() - 1);
	}
	
	private boolean firstCellWins(Cell cellA, Cell cellB) {
		return (cellA.getCurrentState() == ROCK && cellB.getCurrentState() == SCISSORS
				|| cellA.getCurrentState() == PAPER && cellB.getCurrentState() == ROCK
				|| cellA.getCurrentState() == SCISSORS && cellB.getCurrentState() == PAPER);
	}
}
