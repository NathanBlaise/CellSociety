package cellsociety_team02.grid;

import java.util.List;
import java.util.Stack;

import cellsociety_team02.cells.Cell;
import cellsociety_team02.simulations.Simulation;
import javafx.scene.paint.Color;

/**
 * Subclass of the grid, expands in all four directions by one cell if an active cell is found at the
 * edge
 * @author benwelton
 *
 */
public class InfiniteGrid extends Grid{
	
	private Stack<Cell> outsideCells;
	private int[] myPropState;
	private Color[] myColors;
	private Simulation mySim;
	
	public InfiniteGrid(int size, int[] propState, Color[] colors, List<List<Integer>> cellLayout, Simulation sim) {
		super(size+2,propState,colors, cellLayout, sim);
		myColors = colors;
		myPropState = propState;
		mySim = sim;
	}
	
	@Override
	protected void populateGrid(int size, Color[] colors, int[] propDistribution) {
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				if(i==0 || i == size-1 || k == 0 || k == size-1) {
					myArray[i][k] = new Cell(i,k,0,colors,360/(mySize-2),mySize,myArray);
				}
				else{ 
					assignPropState(propDistribution);
					myArray[i][k] = new Cell(i,k,state,colors,360/(mySize-2),mySize,myArray);
				}
			}
		}
	}
	
	
	
	public Grid expandGridArray() {
		outsideCells = new Stack<Cell>();
		for(int i=0; i<mySize; i++) {
			for(int k=0; k<mySize; k++) {
				if(i==0 || i == mySize-1 || k == 0 || k == mySize-1) {
					outsideCells.add(myArray[i][k]);
				}
			}
		}
		while(!outsideCells.isEmpty()) {
			if(outsideCells.pop().getCurrentState() != 0) { //Current State or NextState?
				Grid updatedGrid = new InfiniteGrid(mySize,myPropState,myColors,null,mySim);
				for(int i=0; i<mySize; i++) {
					for(int k=0; k<mySize; k++) {
						updatedGrid.myArray[i+1][k+1].updateInfiniteVitals(myArray[i][k].getCurrentState(), myColors);
					}
				}
				return updatedGrid;
			}
		}
		return new Grid();
	}
	
}
