package cellsociety_team02.simulations;

import java.util.List;

import cellsociety_team02.cells.*;

public class FireSimulation extends Simulation{
	private final int EMPTY = 0;
	private final int TREE = 1;
	private final int BURNING = 2;
	private double spreadChance = 0.3;
	
	@Override
	public void updateCell(Cell cell) {
		if(cell.getCurrentState() == BURNING) cell.setNextState(EMPTY);
		else if(cell.getCurrentState() == TREE) {
			checkSpread(cell);
		}
		
		
	}
	
	private void checkSpread(Cell cell) {
		List<Cell> neighbors = cell.getNeighbours();
		//System.out.println(neighbors.size());
		if(Math.random() > spreadChance) {
			cell.setNextState(TREE);
			return;
		}
		for(Cell neighbor:neighbors) {
			if(neighbor.getCurrentState() == BURNING) {
				cell.setNextState(BURNING);
				break;
			}
			else cell.setNextState(TREE);
		}
	}

}
