package cellsociety_team02.simulations;

import cellsociety_team02.cells.Ant;
import cellsociety_team02.cells.Cell;
import cellsociety_team02.cells.CellOccupant;

public class ForagingSimulation extends Simulation{
	private final int EMPTY = 0;
	private final int FOOD = 1;
	private final int NEST = 2;
	
	private String layoutFile = "data/Foraging.xml";
	
	public ForagingSimulation() {
		super();
		super.layoutFile = this.layoutFile;
		super.defaultFile = this.layoutFile;
		super.changeInitConfig(layoutFile);
	}
	
	@Override
	public void primeCell(Cell cell) {
		if(cell.getX() == 2 && cell.getY() == 2) {
			cell.setNextState(FOOD);
			cell.updateState();
		}else if(cell.getX() == 4 && cell.getY() == 4) {
			cell.setNextState(NEST);
			cell.addOccupants(new Ant(cell), 2);
			cell.updateState();
		}
	}
	
	@Override
	public void updateCell(Cell cell) {
		if(cell.isOccupied()) {
			for(CellOccupant ant:cell.getOccupants()) {
				updateAnt((Ant) ant, cell);
			}	
		}
	}
	
	private void updateAnt(Ant ant, Cell cell) {
		
	}

}
