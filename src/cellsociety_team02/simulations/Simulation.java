package cellsociety_team02.simulations;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team02.cells.Cell;
import javafx.scene.paint.Color;

public abstract class Simulation {
	private Cell cellType;
	private Map<String, String> simulationAttributes;
	private List<Integer> cellAttributes;
	private List<Color> colorAttributes;
	private int gridSize;
	
	protected String layoutFile;
	
	
	public Simulation(){
		initAttributes();
	}
	
	private void initAttributes() {
		simulationAttributes = new HashMap<>();
		cellAttributes = new ArrayList<>();
		colorAttributes = new ArrayList<>();
	}
	
	protected void loadAttributes() {
		XMLHandler parser = new XMLHandler(this);
		parser.addAttributeSet(simulationAttributes);
		parser.addCellSet(cellAttributes, colorAttributes);
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
	
	public int[] cellFrequencies(){
		int[] freq = new int[cellAttributes.size()];
		for(int i = 0; i<cellAttributes.size();i++) {
			freq[i] = cellAttributes.get(i);
		}
		return freq;
	}
	
	public Color[] cellColors(){
		return colorAttributes.toArray(new Color[colorAttributes.size()]);
	}
	
	public void changeCellType(Cell type) {
		cellType = type;
	}
	
}