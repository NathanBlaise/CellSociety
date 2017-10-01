package cellsociety_team02.simulations;

import java.util.List;

import cellsociety_team02.cells.*;

public class FireSimulation extends Simulation{
	private final int EMPTY = 0;
	private final int TREE = 1;
	private final int BURNING = 2;
	
	private String layoutFile = "data/Fire.xml";
	private String[] vars = {"spreadChance"};
	private double[] vals = {0.5};
	private double[] maxs = {1.0};
	private int spreadChanceIndex;
	
	public FireSimulation() {
		super();
		super.setDefaultVariables(layoutFile, vars, vals, maxs);
		super.changeInitConfig(layoutFile);
		spreadChanceIndex = variables.indexOf("spreadChance");
	}

	
	@Override
	public void updateCell(Cell cell) {
		if(cell.getCurrentState() == BURNING) cell.setNextState(EMPTY);
		else if(cell.getCurrentState() == TREE) {
			checkSpread(cell);
		}
	}
	
	private void checkSpread(Cell cell) {
		List<Cell> neighbours = cell.getAdjacentNeighbours();

		if(Math.random() > variableVals.get(spreadChanceIndex)) return;

		for(Cell neighbour:neighbours) {
			if(neighbour.getCurrentState() == BURNING) {
				cell.setNextState(BURNING);
				break;
			}
		}
	}

}
