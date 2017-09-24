package cellsociety_team02.grid;

import cellsociety_team02.cells.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class Grid {
	
	private int mySize;
	private Cell[][] myArray;
	private int diffSim; //Integer used to determine which Cell type we use
	private Random rand = new Random();
	private int state;
			
	public Grid(int size, int[] propState, String simType, Color[] colors) {
		mySize = size;
		//Initialize array of certain cell and set up diffSim boolean
		if(simType.equals("Fire")) {
			myArray = new FireCell[size][size];
			diffSim = 0;
			System.out.println("!"+diffSim);
		}
		else if(simType.equals("Predator-Prey")) {
			myArray = new PredatorPreyCell[size][size];
			diffSim = 1;
		}
		else {
			myArray = new Cell[size][size];
			diffSim = 2;
		}
		
		//Populate grid with appropriate cells
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				int randState = rand.nextInt(100);
				if(randState < propState[0]) state = 0;
				else if(randState > propState[0] && randState < (propState[1] + propState[0])) state = 1;
				else if(randState > ((propState[1] + propState[0]))){
					System.out.println("Yeet");
					state = 2;
				}
			
				if(diffSim == 0) myArray[i][k] = new FireCell(i,k,state,colors,200/mySize,mySize,myArray);
				else if(diffSim == 1) myArray[i][k] = new PredatorPreyCell(i,k,state,colors,200/mySize,mySize,myArray);
				else myArray[i][k] = new Cell(i,k,state,colors,200/mySize,mySize,myArray);
			}
		}
	}
	
	public int getSize() {
		return mySize;
	}
	
	public void setSize(int size) {
		mySize=size;
	}
	
	public Cell[][] getArr(){
		return myArray;
	}
	
}
