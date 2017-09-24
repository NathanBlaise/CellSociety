package cellsociety_team02.simulations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team02.cells.Cell;

public abstract class Simulation {
	private Cell cellType;
	private Map<String, String> simulationAttributes;
	private List<Integer> cellAttributes;
	private int gridSize;
	
	protected String layoutFile;
	
	
	public Simulation(){
		initAttributes();
	}
	
	private void initAttributes() {
		simulationAttributes = new HashMap<>();
		cellAttributes = new ArrayList<>();
	}
	
	protected void loadAttributes() {
		XMLHandler parser = new XMLHandler(this);
		parser.addAttributeSet(simulationAttributes);
		parser.addCellSet(cellAttributes);
		gridSize = parser.addGridParameters();
	}
	
	protected File loadInitConfig() {
		return new File(layoutFile);
	}
	
	public void changeInitConfig(String file) {
		layoutFile = file;
		initAttributes();
		loadAttributes();
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