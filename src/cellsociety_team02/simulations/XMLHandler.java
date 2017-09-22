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

public class XMLHandler {
	private Document configDoc;
	
	protected XMLHandler(Simulation simulation) {
		File layoutFile = simulation.loadInitConfig();
		configDoc = createParsedDocument(layoutFile);
	}
	
	protected Document createParsedDocument(File layoutFile) {
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
	
	protected void addAttributeSet(Map<String, String> simulationAttributes) {
		NodeList allAttributes = configDoc.getElementsByTagName("Attributes");
		
		if(allAttributes.getLength() > 1) {
			System.out.println("Error. There should not be more than one attribute section");
			return;
		}
		
		Element attributes = (Element) allAttributes.item(0);
		NodeList vals = attributes.getElementsByTagName("*");
		for(int i = 0; i<vals.getLength(); i++) {
			simulationAttributes.put(vals.item(i).getNodeName(), vals.item(i).getTextContent());
		}
	}
	
	protected void addCellSet(List<Integer> cellSet) {
		NodeList cells = configDoc.getElementsByTagName("Cell");
		for(int i = 0; i< cells.getLength(); i++) {
			Element cell = (Element) cells.item(i);
			String proportionVal = cell.getElementsByTagName("Proportion").item(0).getTextContent();
			cellSet.add(Integer.parseInt(proportionVal));
		}
	}
	
	protected int addGridParameters() {
		NodeList grids = configDoc.getElementsByTagName("Grid");
		Element grid = (Element) grids.item(0);
		String size = grid.getElementsByTagName("Size").item(0).getTextContent();
		return Integer.parseInt(size);
	}

}