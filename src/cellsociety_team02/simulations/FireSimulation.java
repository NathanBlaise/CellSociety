package cellsociety_team02.simulations;

import java.util.List;

import cellsociety_team02.cells.*;

public class FireSimulation extends Simulation{
	private final int EMPTY = 0;
	private final int TREE = 1;
	private final int BURNING = 2;
	
	private String layoutFile = "data/Fire.xml";
	private double spreadChance = 0.5;
	
	public FireSimulation() {
		super();
		super.layoutFile = this.layoutFile;
		super.defaultFile = this.layoutFile;
		super.changeInitConfig(layoutFile);
		if(super.queryAttributes("spreadChance") != null) {
			spreadChance = Double.parseDouble(queryAttributes("spreadChance"));
		}
	}
	
	@Override
	public void updateCell(Cell cell) {
		if(cell.getCurrentState() == BURNING) cell.setNextState(EMPTY);
		else if(cell.getCurrentState() == TREE) {
			checkSpread(cell);
		}
	}
	
	private void checkSpread(Cell cell) {
		List<Cell> neighbours = cell.getNeighbours();

		if(Math.random() > spreadChance) return;

		for(Cell neighbour:neighbours) {
			if(neighbour.getCurrentState() == BURNING) {
				cell.setNextState(BURNING);
				break;
			}
		}
	}
	
	//for later gui interactivity
	public void changeSpreadChance(int newChance) {
		spreadChance = newChance;
	}

}
