package cellsociety_team02.grid;

import java.util.Stack;

import cellsociety_team02.cells.Cell;
import javafx.scene.paint.Color;

public class InfiniteGrid extends Grid{
	
	private Stack<Cell> outsideCells;
	private int[] myPropState;
	private Color[] myColors;
	
	public InfiniteGrid(int size, int[] propState, Color[] colors) {
		super(size+2,propState,colors);
		myColors = colors;
		myPropState = propState;
		
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
				Grid updatedGrid = new InfiniteGrid(mySize,myPropState,myColors);
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
