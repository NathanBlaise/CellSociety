package cellsociety_team02.gui;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cellsociety_team02.grid.Grid;
import cellsociety_team02.simulations.Simulation;

public class XMLBuilder {
	private Grid myGrid;
	private Document configDoc;
	private Simulation sim;
	private String file;
	
	public XMLBuilder(Grid grid, Simulation sim) {
		this.myGrid = grid;
		this.sim = sim;
		initializeDocument();
		setupNodes();
		createFile();
	}

	private void initializeDocument() {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //www.tutorialspoint.com
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			configDoc = dBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void setupNodes() {
		Element root = configDoc.createElement("Simulation");
		Element attributes = configDoc.createElement("Attributes");
		Element variables = configDoc.createElement("Variables");
		Element cells = configDoc.createElement("Cells");
		Element grid = configDoc.createElement("Grid");
		
		setupAttributes(attributes);
		variables = setupVariables(variables);
		setupCells(cells);
		setupGrid(grid);
		System.out.println(attributes);
		System.out.println(variables);
		System.out.println(cells);
		System.out.println(grid);
		
		root.appendChild(attributes);
		root.appendChild(variables);
		root.appendChild(cells);
		root.appendChild(grid);
		configDoc.appendChild(root);
	}

	private void setupAttributes(Element attributes) {
		//would be configured to write in author, title, and name to display in app
	}
	
	private Element setupVariables(Element variables) {
		for(int i = 0; i<sim.variableList().size(); i++) {
			Element newVariable = configDoc.createElement(sim.variableList().get(i));
			newVariable.setAttribute("name", sim.variableList().get(i));
			newVariable.setAttribute("value", Double.toString(sim.variableValues().get(i)));
			newVariable.setAttribute("maximum", Double.toString(sim.variableMaximums().get(i)));
			variables.appendChild(newVariable);
		}
		return variables;
	}
	
	private void setupCells(Element cells) {
		//cells would be configured as above and below to read in values from the sim
		setupLayout(cells);
	}
	
	private void setupLayout(Element cells) {
		Element layout = configDoc.createElement("Layout");
		
		for(int i = 0; i<myGrid.getSize(); i++) {
			Element row = configDoc.createElement("Row");
			String rowVals = "";
			for(int j=0; j<myGrid.getSize(); j++) {
				rowVals += myGrid.getArr()[i][j].getCurrentState();
			}
			row.appendChild(configDoc.createTextNode(rowVals));
			layout.appendChild(row);
		}
		cells.appendChild(layout);
	}

	private void setupGrid(Element grid) {
		Element size = configDoc.createElement("Size");
		Element type = configDoc.createElement("Type");
		Element visible = configDoc.createElement("Visibile");
		
		size.appendChild(configDoc.createTextNode(Integer.toString(sim.simulationSize())));
		type.appendChild(configDoc.createTextNode(sim.gridType()));
		visible.appendChild(configDoc.createTextNode(sim.gridVisibility()));
		
		grid.appendChild(size);
		grid.appendChild(type);
		grid.appendChild(visible);
	}
	
	private void createFile() {
		System.out.println(configDoc);
	}
	
	protected String getFile() {
		return new String(file);
	}
}
