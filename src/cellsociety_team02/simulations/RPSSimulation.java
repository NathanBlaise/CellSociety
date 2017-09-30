package cellsociety_team02.simulations;

import cellsociety_team02.cells.Cell;

public class RPSSimulation extends Simulation {
	private final int EMPTY = 0;
	private final int ROCK = 1;
	private final int PAPER = 2;
	private final int SCISSORS = 3;
	
	@Override
	public void updateCell(Cell cell) {
		Cell randNeighbour = cell.chooseRandomNeighbour(cell.getNeighbours());
		
	}

}
