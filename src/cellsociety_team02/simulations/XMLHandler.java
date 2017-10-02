package cellsociety_team02.simulations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.paint.Color;

/**
 * Parses the configuration file and returns all variables and properties to the simulation.
 * Does not interact with any other classes. General notes about error handling can be found in
 * the readme. 
 * @author benwelton
 *
 */
public class XMLHandler {
	private Document configDoc;
	
	protected void tryToLoadDoc(String defaultFile, String layoutFile) {
		File layout = new File(layoutFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //www.tutorialspoint.com
        try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(layout);
	        doc.getDocumentElement().normalize();
	        configDoc = doc;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			if(!defaultFile.equals(layoutFile)) {
				tryToLoadDoc(defaultFile, defaultFile);
				System.out.println("There was a problem parsing. Default configuration was used instead\n");
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Add values from an arbitrary valName node into a corresponding map
	 * @param simulationValues
	 * @param valName
	 */
	protected void addValueSet(Map<String, String> simulationValues, String valName) {
		NodeList allAttributes = configDoc.getElementsByTagName(valName);
		
		if(allAttributes.getLength() == 0) {
			System.out.println("The " + valName +  " section was not found\n");
			return;
		}
		if(allAttributes.getLength() > 1) {
			System.out.println("There should not be more than one " + valName + " section.\n");
			System.out.println("Only the first block will be used.\n");
		}
		
		Element attributes = (Element) allAttributes.item(0);
		NodeList vals = attributes.getElementsByTagName("*");
		for(int i = 0; i<vals.getLength(); i++) {
			simulationValues.put(vals.item(i).getNodeName(), vals.item(i).getTextContent());
		}
	}
	
	/**
	 * Return the lists of variables, their values, and their maximums
	 * @param vars
	 * @param vals
	 * @param maxs
	 */
	protected void addVariableSet(List<String> vars, List<Double> vals, List<Double> maxs) {
		NodeList variables = configDoc.getElementsByTagName("Variable");
		for(int i = 0; i<variables.getLength(); i++) {
			Element variable = (Element) variables.item(i);
			String varName = variable.getAttribute("name");
			String varVal = variable.getAttribute("value");
			String varMax = variable.getAttribute("maximum");
			if(varVal.isEmpty() || varName.isEmpty()) continue;
			vars.add(varName);
			vals.add(Double.parseDouble(varVal));
			maxs.add(Double.parseDouble(varMax));
		}
	}
	
	/**
	 * Reads a general layout of cells and returns their colors and, if specified, their
	 * proportions in the overall layout 
	 * @param cellSet
	 * @param colorSet
	 */
	protected void addCellSet(List<Integer> cellSet, List<Color> colorSet) {
		NodeList cells = configDoc.getElementsByTagName("Cell");
		for(int i = 0; i< cells.getLength(); i++) {
			Element cell = (Element) cells.item(i);
			if(cell.hasAttribute("proportion")) {
				String proportionVal = cell.getAttribute("proportion");
				cellSet.add(Integer.parseInt(proportionVal));
			}else {
				cellSet.add(0);
			}
			
			if(cell.hasAttribute("color")) {
				String color = cell.getAttribute("color");
				colorSet.add(Color.web(color));
			}else {
				Random rand = new Random(); //https://stackoverflow.com/questions/4246351/creating-random-colour-in-java
				colorSet.add(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			}
		}
	}
	
	/**
	 * Reads a specific layout of cells and returns it in the form of a two dimensional list.
	 * Handles light error checking on square size and fit into the current grid.
	 * @param cellLayout
	 * @param gridSize
	 */
	protected void readSpecificLayout(List<List<Integer>> cellLayout, int gridSize) {
		NodeList layouts = configDoc.getElementsByTagName("Layout");
		if(layouts.getLength()<=0) {
			cellLayout = null;
			return;
		}
		
		Element layout = (Element) layouts.item(0);
		NodeList rows = layout.getElementsByTagName("Row");
		for(int i = 0; i<rows.getLength(); i++) {
			String rowVals = rows.item(i).getTextContent();
			if(rowVals.length() != gridSize) {
				cellLayout = null;
				return;
			}
			
			List<Integer> row = new ArrayList<>();
			for(int j = 0; j<rowVals.length(); j++) {
				row.add(rowVals.charAt(j) - '0');
			}
			cellLayout.add(row);
		}
		if(cellLayout.size() != gridSize) {
			cellLayout = null;
		}
	}
}