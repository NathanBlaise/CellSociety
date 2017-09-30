package cellsociety_team02.grid;

import cellsociety_team02.cells.*;
import javafx.scene.paint.Color;

import java.util.Random;

public class Grid {
	
	private int mySize;
	private Cell[][] myArray;
	private Random rand = new Random();
	private int state;
			
	public Grid(int size, int[] propState, Color[] colors) {
		mySize = size;
		myArray = new Cell[size][size];

		int[] propDistribution = generateStateDistr(propState);
		
		//Populate grid with appropriate cells
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				assignPropState(propDistribution);
				myArray[i][k] = new Cell(i,k,state,colors,200/mySize,mySize,myArray);
			}
		}
	}

	private void assignPropState(int[] propDistr) {
		state = propDistr[rand.nextInt(propDistr.length)];
	}
	
	private int[] generateStateDistr(int[] propState) {
		correctPercentages(propState);
		int[] distr = new int[100];
		int count = 0;
		for(int i=0; i<propState.length; i++) {
			for(int j=0; j<propState[i]; j++) {
				distr[count++] = i;
			}
		}
		return distr;
	}
		
	private void correctPercentages(int[] propState) {
		int totalPercent = 0;
		
		for(int prop:propState) {
			totalPercent += prop;
		}
		if(totalPercent == 100) return;
		
		for(int i = 0; i<propState.length; i++) {
			propState[i] = (int) (((double) propState[i]/totalPercent) * 100);
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
