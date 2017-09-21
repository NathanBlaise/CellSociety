package cellsociety_team02.grid;

import cellsociety_team02.cells.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class Grid {
	
	private int mySize;
	private Cell[][] myArray;
	private boolean diffSim; //Boolean used to determine which Cell type we use
	private Random rand;
	private int state;
	private Color[] cellColors;
	private Grid thisGrid;
			
	public Grid(int size, int[] propState, String simType, Color[] colors) {
		mySize = size;
		//Initialize array of certain cell and set up diffSim boolean
		if(simType.equals("Fire") || simType.equals("PredPrey")) {
			myArray = new PredPreyFireCell[size][size];
			diffSim = true;
		}
		else {
			myArray = new Cell[size][size];
			diffSim =false;
		}
		
		//Populate grid with appropriate cells
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				int randState = rand.nextInt(100);
				if(randState < propState[0]) state = 0;
				if(randState > propState[0] && randState < (propState[1] + propState[0])) state = 1;
				else state = 2;
				if(diffSim) myArray[i][k] = new PredPreyFireCell(i,k,state,colors,10,mySize,myArray); //Need to work out how to calculate Cell Size - TONY
				else myArray[i][k] = new Cell(i,k,state,colors,10,mySize,myArray);
			}
		}
	}
	
	public int getSize() {
		return mySize;
	}
	
	public Cell[][] getArr(){
		return myArray;
	}
	
}
