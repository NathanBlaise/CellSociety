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
	
	/**
	 * Modify the simulation parameters and configuration by passing in a new xml file
	 * @param newLayout
	 */
	public void changeInitConfig(String newLayout) {
		layoutFile = newLayout;
		initAttributes();
		loadAttributes(defaultFile, layoutFile);
	}
	
	/**
	 * Returns the grid size specified for the simulation
	 * @return
	 */
	public int simulationSize(){
		return Integer.parseInt(gridAttributes.getOrDefault("Size", DEFAULT_GRID_SIZE));
	}
	
	/**
	 * Returns the grid type specified for the simulation
	 * @return
	 */
	public String gridType() {
		return gridAttributes.getOrDefault("Type", DEFAULT_GRID_TYPE);
	}
	
	/**
	 * Returns the proportional frequencies for each cell type, returns an empty array otherwise
	 * @return
	 */
	public int[] cellFrequencies(){
		int[] freq = new int[cellAttributes.size()];
		for(int i = 0; i<cellAttributes.size();i++) {
			freq[i] = cellAttributes.get(i);
		}
		return freq;
	}
	
	/**
	 * Returns the colors for each cell type. Currently missing error check for when no colors
	 * are specified
	 * @return
	 */
	public Color[] cellColors(){
		return colorAttributes.toArray(new Color[colorAttributes.size()]);
	}
	
	/**
	 * Returns a copy of the variables names for the gui to interact with
	 * @return
	 */
	public List<String> variableList(){
		return new ArrayList<>(variables);
	}
	
	/**
	 * Returns a copy of the variable values for the gui to interact with
	 * @return
	 */
	public List<Double> variableValues(){
		return new ArrayList<>(variableVals);
	}
	
	/**
	 * Returns a copy of the variable maximums for the gui to interact with
	 * @return
	 */
	public List<Double> variableMaximums(){
		return new ArrayList<>(variableMaxs);
	}
	
	/**
	 * Modify the simulation parameter at the given index in the variable list. Does nothing if
	 * the index is out of bounds or the newValue is above the maximum
	 * @param index
	 * @param newValue
	 */
	public void changeVariables(int index, double newValue) {
		if(variables.size() > index && newValue < variableMaxs.get(index)) {
			variableVals.set(index, newValue);
		}
	}
	
	/**
	 * Error handling, sets variables to defaults if the correct values were not specified
	 */
	private void checkForVariableErrors() {
		if(defaultVariables == null) return;
		for(int i = 0; i<variables.size(); i++) {
			if(!defaultVariables.contains(variables.get(i))) {
				variables.remove(i);
				variableVals.remove(i);
				variableMaxs.remove(i);
			}
		}
		for(int i = 0; i<defaultVariables.size(); i++) {
			if(!variables.contains(defaultVariables.get(i))){
				variables.add(defaultVariables.get(i));
				variableVals.add(defaultVariableVals[i]);
				variableMaxs.add(defaultVariableMaxs[i]);
			}
		}
	}
	
	protected void setDefaultVariables(String layoutFile, String[] vars, double[] vals, double[] maxs) {
		if(vars == null) return;
		layoutFile = this.layoutFile;
		defaultFile = this.layoutFile;
		defaultVariables = new ArrayList<>(Arrays.asList(vars));
		defaultVariableVals = vals;
		defaultVariableMaxs = maxs;
	};
	
	/**
	 * Returns the value of the key attribute found int the xml file. Unused, would be useful if
	 * author, type, or name were needed
	 * @param key
	 * @return
	 */
	public String queryAttributes(String key) {
		return simulationAttributes.containsKey(key) ? simulationAttributes.get(key) : null;
	}
	
	/**
	 * Gives the rule algorithim for updating the cell
	 * @param cell
	 */
	public abstract void updateCell(Cell cell);
	
	/**
	 * Sets initial state for a cell
	 * @param cell
	 */
	public void primeCell(Cell cell) {};
	
	/**
	 * Clears the states and variables from a previous simulation run
	 */
	public void clearValues() {};

}