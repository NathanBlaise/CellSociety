package cellsociety_team02.grid;

import cellsociety_team02.cells.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class Grid {
	
	private int mySize;
	private Cell[][] myArray;
	private Random rand = new Random();
	private int state;
			
	public Grid(int size, int[] propState, String simType, Color[] colors) {
		mySize = size;
		myArray = new Cell[size][size];
		
		//Populate grid with appropriate cells
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				assignPropState(propState);
				myArray[i][k] = new Cell(i,k,state,colors,200/mySize,mySize,myArray);
			}
		}
	}

	private void assignPropState(int[] propState) {
		//TODO what if four states
		int randState = rand.nextInt(100);
		if(randState < propState[0]) state = 0;
		else if(randState > propState[0] && randState < (propState[1] + propState[0])) state = 1;
		else if(randState > ((propState[1] + propState[0]))) state = 2;
	}
	
	private void assignSpecificState() {
		//TODO
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
