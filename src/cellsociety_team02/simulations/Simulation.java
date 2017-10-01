package cellsociety_team02.simulations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team02.cells.Cell;
import javafx.scene.paint.Color;

public abstract class Simulation {
	private final String DEFAULT_GRID_SIZE = "5";
	private final String DEFAULT_GRID_TYPE = "Normal";
	
	private XMLHandler parser;
	private Map<String, String> simulationAttributes;
	private Map<String, String> gridAttributes;
	private List<Integer> cellAttributes;
	private List<Color> colorAttributes;
	
	protected String layoutFile;
	protected String defaultFile;
	protected List<String> variables;
	protected List<Double> variableVals;
	protected List<Double> variableMaxs;
	protected List<String> defaultVariables;
	protected double[] defaultVariableVals;
	protected double[] defaultVariableMaxs;
	
	
	public Simulation(){
		parser = new XMLHandler();
		initAttributes();
	}
	
	private void initAttributes() {
		simulationAttributes = new HashMap<>();
		gridAttributes = new HashMap<>();
		cellAttributes = new ArrayList<>();
		colorAttributes = new ArrayList<>();
		variables = new ArrayList<>();
		variableVals = new ArrayList<>();
		variableMaxs = new ArrayList<>();
	}
	
	private void loadAttributes(String defaultFile, String layoutFile) {
		parser.tryToLoadDoc(defaultFile, layoutFile);
		parser.addValueSet(simulationAttributes, "Attributes");
		parser.addValueSet(gridAttributes, "Grid");
		parser.addVariableSet(variables, variableVals, variableMaxs);
		checkForVariableErrors();
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
	
	public String gridType() {
		return gridAttributes.getOrDefault("Type", DEFAULT_GRID_TYPE);
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
	
	public List<String> variableList(){
		return new ArrayList<>(variables);
	}
	
	public List<Double> variableValues(){
		return new ArrayList<>(variableVals);
	}
	
	public List<Double> variableMaximums(){
		return new ArrayList<>(variableMaxs);
	}
	
	public void changeVariables(int index, double newValue) {
		if(variables.size() > index && newValue < variableMaxs.get(index)) {
			variableVals.set(index, newValue);
		}
	}
	
	private void checkForVariableErrors() {
		//unused variables 
		if(defaultVariables == null) return;
		for(int i = 0; i<variables.size(); i++) {
			if(!defaultVariables.contains(variables.get(i))) {
				variables.remove(i);
				variableVals.remove(i);
				variableMaxs.remove(i);
			}
		}
		//important variables
		for(int i = 0; i<defaultVariables.size(); i++) {
			if(!variables.contains(defaultVariables.get(i))){
				variables.add(defaultVariables.get(i));
				variableVals.add(defaultVariableVals[i]);
				variableMaxs.add(defaultVariableMaxs[i]);
			}
		}
	}
	
	public String queryAttributes(String key) {
		return simulationAttributes.containsKey(key) ? simulationAttributes.get(key) : null;
	}
	
	public abstract void updateCell(Cell cell);
	
	protected void setDefaultVariables(String layoutFile, String[] vars, double[] vals, double[] maxs) {
		if(vars == null) return;
		layoutFile = this.layoutFile;
		defaultFile = this.layoutFile;
		defaultVariables = new ArrayList<>(Arrays.asList(vars));
		defaultVariableVals = vals;
		defaultVariableMaxs = maxs;
	};
	
	public void primeCell(Cell cell) {};
	
	public void clearValues() {};

}