package cellsociety_team02.grid;

import cellsociety_team02.cells.*;
import cellsociety_team02.simulations.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Grid superclass, handles assigning states to cells, placing them in the grid, and updating the
 * layout
 * @author benwelton nathan.lewis
 *
 */
public class Grid {
	
	protected int mySize;
	protected int state;
	protected Cell[][] myArray;
	private Random rand = new Random();
	private int[] cellAmounts;
	
	public Grid() {
		mySize = 0;
	}
			
	public Grid(int size, int[] propState, Color[] colors, List<List<Integer>> cellLayout, Simulation sim) {
		mySize = size;
		myArray = new Cell[size][size];
		cellAmounts = new int[colors.length];
		
		if(cellLayout==null || cellLayout.isEmpty() || cellLayout.size() != size) {
			int[] propDistribution = generateStateDistr(propState);
			populateGrid(size, colors, propDistribution);
		}else {
			loadSpecificLayout(size, colors, cellLayout);
		}
		
		//store neighbours
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				if(sim instanceof FireSimulation || sim instanceof PredatorPreySimulation) {
					storeAdjacentNeighbours(myArray[i][k]);
				}
				else storeNeighbours(myArray[i][k]);
			}
		}
	}

	private void loadSpecificLayout(int size, Color[] colors, List<List<Integer>> cellLayout) {
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				int state = cellLayout.get(i).get(j);
				if(state>=colors.length) state = 0;
				myArray[i][j] = new Cell(i,j,state,colors,360/mySize, mySize, myArray);
				cellAmounts[myArray[i][j].getCurrentState()]++;
			}
		}
	}

	protected void populateGrid(int size, Color[] colors, int[] propDistribution) {
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				assignPropState(propDistribution);
				myArray[i][k] = new Cell(i,k,state,colors,360/mySize, mySize, myArray);
				cellAmounts[myArray[i][k].getCurrentState()]++;
			}
		}
	}
	
	protected void storeNeighbours(Cell cell){
		ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
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
	
	protected void storeAdjacentNeighbours(Cell cell) {
		ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
		if(cell.getY()<mySize - 1) cellNeighbours.add(myArray[cell.getX()][cell.getY()+1]); //East
		if(cell.getY()>0) cellNeighbours.add(myArray[cell.getX()][cell.getY()-1]); //West
		if(cell.getX()<mySize - 1) cellNeighbours.add(myArray[cell.getX()+1][cell.getY()]); //South
		if(cell.getX()>0) cellNeighbours.add(myArray[cell.getX()-1][cell.getY()]); //North
		cell.setMyNeighbours(cellNeighbours);
	}

	protected void assignPropState(int[] propDistr) {
		state = propDistr[rand.nextInt(propDistr.length)];
	}
	
	//generate an integer array to approximate a probability distribution to place states at
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
		
	//normalize percentages when the user fails to get them adding to 100
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
	
	/**
	 * Update the grid according to the parameter's rule set
	 * @param sim
	 */
	public void updateCellArray(Simulation sim) {
		int size = this.getSize();
		for (int i= 0; i<size;i++) {
			for (int j = 0; j<size; j++) {
				sim.updateCell(this.getArr()[i][j]);
			}
		}
		for (int i= 0; i<size;i++) {
			for (int j = 0; j<size; j++) {
				Cell cell = this.getArr()[i][j];
				if(cell.stateHasChanged()) {
					cellAmounts[cell.getCurrentState()]--;
					cellAmounts[cell.getNextState()]++;
					cell.updateState();
				}
			}
		}
	}
	
	public int getSize() {
		return mySize;
	}
	
	public void setSize(int size) {
		mySize=size;
	}
	
	/**
	 * Returns the cell array held by the grid
	 * @return
	 */
	public Cell[][] getArr(){
		return myArray;
	}
	
	/**
	 * Access the proportion of each cell type in the current count
	 * @return
	 */
	public int[] getCellProportions() {
		int[] cellProportions = new int[cellAmounts.length];
		int cellCount = mySize * mySize;
		for(int i = 0; i<cellAmounts.length; i++) {
			cellProportions[i] = (int) (((double) cellAmounts[i]/cellCount) * 100);
		}
		return cellProportions;
	}
	
	
	
}
