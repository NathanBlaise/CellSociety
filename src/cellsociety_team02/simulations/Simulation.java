package cellsociety_team02.simulations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team02.cells.Cell;

public abstract class Simulation {
	private String layoutFile = "data/test.xml";
	private Cell cellType;
	private Map<String, String> simulationAttributes;
	private List<Integer> cellAttributes;
	private int gridSize;
	
	
	public Simulation(){
		simulationAttributes = new HashMap<>();
		cellAttributes = new ArrayList<>();
		loadAttributes();
	}
	
	private void loadAttributes() {
		XMLHandler parser = new XMLHandler(this);
		parser.addAttributeSet(simulationAttributes);
		parser.addCellSet(cellAttributes);
		gridSize = parser.addGridParameters();
	}
	
	protected File loadInitConfig() {
		return new File(layoutFile);
	}
	
	public abstract void updateCell(Cell cell);
	
	public int simulationSize(){
		return gridSize;
	}
	
	public List<Integer> cellFrequencies(){
		return cellAttributes;
	}
	
	public void changeCellType(Cell type) {
		cellType = type;
	}

	
}