package cellsociety_team02.grid;

import cellsociety_team02.cells.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
	
	protected int mySize;
	protected Cell[][] myArray;
	protected ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
	private Random rand = new Random();
	protected int state;
	
	public Grid() {
		mySize = 0;
	}
			
	public Grid(int size, int[] propState, Color[] colors) {
		mySize = size;
		myArray = new Cell[size][size];

		int[] propDistribution = generateStateDistr(propState);
		
		//Populate grid with appropriate cells
		populateGrid(size, colors, propDistribution);
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				storeNeighbours(myArray[i][k]);
			}
		}
	}

	protected void populateGrid(int size, Color[] colors, int[] propDistribution) {
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				assignPropState(propDistribution);
				myArray[i][k] = new Cell(i,k,state,colors,200/mySize, mySize, myArray);
			}
		}
	}
	
	protected void storeNeighbours(Cell cell){
		cellNeighbours.clear();
		int[] xCoord = {cell.getX(), cell.getX()+1, cell.getX()-1};
		int[] yCoord = {cell.getY(), cell.getY()+1, cell.getY()-1};
		for(int x: xCoord) {
			for(int y: yCoord) {
				if(x>-1 && y>-1 && x<(mySize) && y<(mySize)) {
					cellNeighbours.add(myArray[x][y]);
				}
			}
		}
		cellNeighbours.remove(myArray[cell.getX()][cell.getY()]);
		cell.setMyNeighbours(cellNeighbours);
	}

	protected void assignPropState(int[] propDistr) {
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
