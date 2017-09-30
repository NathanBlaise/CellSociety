package cellsociety_team02.simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team02.cells.Cell;
import javafx.scene.paint.Color;

public abstract class Simulation {
	private final String DEFAULT_GRID_SIZE = "5";
	
	private XMLHandler parser;
	private Map<String, String> simulationAttributes;
	private Map<String, String> gridAttributes;
	private List<Integer> cellAttributes;
	private List<Color> colorAttributes;
	
	protected String layoutFile;
	protected String defaultFile;
	
	
	public Simulation(){
		parser = new XMLHandler();
		initAttributes();
	}
	
	private void initAttributes() {
		simulationAttributes = new HashMap<>();
		gridAttributes = new HashMap<>();
		cellAttributes = new ArrayList<>();
		colorAttributes = new ArrayList<>();
	}
	
	private void loadAttributes(String defaultFile, String layoutFile) {
		parser.tryToLoadDoc(defaultFile, layoutFile);
		parser.addValueSet(simulationAttributes, "Attributes");
		parser.addValueSet(gridAttributes, "Grid");
		parser.addCellSet(cellAttributes, colorAttributes);
	}
	
	public void changeInitConfig(String newLayout) {
		layoutFile = newLayout;
		initAttributes();
		loadAttributes(defaultFile, layoutFile);
	}
	
	public int simulationSize(){
		return Integer.parseInt(gridAttributes.getOrDefault("Size", DEFAULT_GRID_SIZE));
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
	
	public String queryAttributes(String key) {
		if(simulationAttributes.containsKey(key)) {
			return simulationAttributes.get(key);
		}else {
			return null;
		}
	}
	
	public abstract void updateCell(Cell cell);
	
	public void primeCell(Cell cell) {};
	
	public void clearValues() {};

}