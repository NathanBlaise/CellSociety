package cellsociety_team02.grid;

import java.util.List;
import java.util.Stack;

import cellsociety_team02.cells.Cell;
import javafx.scene.paint.Color;

public class InfiniteGrid extends Grid{
	
	private Stack<Cell> outsideCells;
	private int[] myPropState;
	private Color[] myColors;
	
	public InfiniteGrid(int size, int[] propState, Color[] colors, List<List<Integer>> cellLayout) {
		super(size+2,propState,colors, cellLayout);
		myColors = colors;
		myPropState = propState;
		
	}
	
	@Override
	protected void populateGrid(int size, Color[] colors, int[] propDistribution) {
		outsideCells = new Stack<Cell>();
		for(int i=0; i<size; i++) {
			for(int k=0; k<size; k++) {
				if(i==0 || i == size-1 || k == 0 || k == size-1) {
					myArray[i][k] = new Cell(i,k,0,colors,200/(mySize-1),mySize,myArray);
					outsideCells.add(myArray[i][k]);
					return;
				}
				assignPropState(propDistribution);
				myArray[i][k] = new Cell(i,k,state,colors,200/(mySize-1),mySize,myArray);
			}
		}
	}
	
	public Grid expandGridArray() {
		while(!outsideCells.isEmpty()) {
			if(outsideCells.pop().getCurrentState() != 0) { //Current State or NextState?
				Grid updatedGrid = new InfiniteGrid(mySize-1,myPropState,myColors,null);
				for(int i=0; i<mySize; i++) {
					for(int k=0; k<mySize; k++) {
						updatedGrid.myArray[i+1][k+1] = myArray[i][k];
					}
				}
				return updatedGrid;
			}
		}
		return new Grid();
	}
	
}
