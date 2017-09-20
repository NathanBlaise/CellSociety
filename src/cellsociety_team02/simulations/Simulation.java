package cellsociety_team02.simulations;

import java.io.File;

import javafx.scene.paint.Color;

public class Simulation {
	private String layoutFile;
	
	public Simulation() {
		
	}
	
	public File loadInitConfig() {
		return new File(layoutFile);
	}
	
	public int setInitialState() {
		//give index for color array of new grid cell
		return 0;
	}
	
	public Color[] createColorArray() {
		//this will need to be different for sub classes so that the colors correspond to they type
		return null;
	}
}