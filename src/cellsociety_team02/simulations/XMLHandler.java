package cellsociety_team02.simulations;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.paint.Color;

public class XMLHandler {
	private Document configDoc;
	
	//TODO possibly on front end, but make sure that it doesn't try to read non-xml or empty file
	//TODO add in specific cell location
	
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
	
	protected void addCellSet(List<Integer> cellSet, List<Color> colorSet) {
		NodeList cells = configDoc.getElementsByTagName("Cell");
		for(int i = 0; i< cells.getLength(); i++) {
			Element cell = (Element) cells.item(i);
			String proportionVal = cell.getElementsByTagName("Proportion").item(0).getTextContent();
			String color = cell.getElementsByTagName("Color").item(0).getTextContent();
			cellSet.add(Integer.parseInt(proportionVal));
			colorSet.add(Color.web(color));
		}
	}

}