package cellsociety_team02.gui;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import cellsociety_team02.grid.Grid;
import cellsociety_team02.simulations.Simulation;

public class XMLHandler {
	private Simulation simulation;
	
	public XMLHandler(Simulation simulation) {
		this.simulation = simulation;
	}
	
	protected void drawInitialState(Grid grid) {
		File layoutFile = simulation.loadInitConfig();
		Document doc = createParser(layoutFile);
		
		//handle parsed content here
	}
	
	protected Document createParser(File layoutFile) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //www.tutorialspoint.com
        try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(layoutFile);
	        doc.getDocumentElement().normalize();
	        return doc;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}
        
	}
	
	protected Simulation createRuleSet() {
		//parse through beginning and find the simulation type
		return null;
	}

}