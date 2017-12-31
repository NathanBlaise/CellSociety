package cellsociety_team02.grid;

import java.util.ArrayList;
import java.util.List;

import cellsociety_team02.cells.Cell;
import cellsociety_team02.simulations.Simulation;
import javafx.scene.paint.Color;

/**
 * Subclass of the grid, wraps each edge of the grid to the opposite end.
 * @author benwelton nathan.lewis
 *
 */
public class ToroidalGrid extends Grid{

	public ToroidalGrid(int size, int[] propState, Color[] colors, List<List<Integer>> cellLayout,Simulation sim) {
		super(size,propState,colors, cellLayout,sim);
	}
	
	@Override
	protected void storeNeighbours(Cell cell){
		ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
		int[] xCoord = {cell.getX(), cell.getX()+1, cell.getX()-1};
		int[] yCoord = {cell.getY(), cell.getY()+1, cell.getY()-1};
		for(int x: xCoord) {
			for(int y: yCoord) {
				if(x == -1 || y == -1 || x == mySize || y == mySize) {
					x = switchPosition(x);
					y = switchPosition(y);
					cellNeighbours.add(myArray[x][y]);
				}
				if(x>-1 && y>-1 && x<(mySize) && y<(mySize)) {
					cellNeighbours.add(myArray[x][y]);
				}
			}
		}
		cellNeighbours.remove(myArray[cell.getX()][cell.getY()]);
		cell.setMyNeighbours(cellNeighbours);
	}
	
	private int switchPosition(int x) {
		if(x == -1) x = mySize -1;
		if(x == mySize) x = 0;
		return x;
	}
	
	@Override
	protected void storeAdjacentNeighbours(Cell cell) {
		ArrayList<Cell> cellNeighbours = new ArrayList<Cell>();
		if(cell.getY() == mySize-1) cellNeighbours.add(myArray[cell.getX()][0]); //East
		else cellNeighbours.add(myArray[cell.getX()][cell.getY()+1]);
		if(cell.getY() == 0) cellNeighbours.add(myArray[cell.getX()][mySize-1]); //West
		else cellNeighbours.add(myArray[cell.getX()][cell.getY()-1]);
		if(cell.getX() == mySize - 1) cellNeighbours.add(myArray[0][cell.getY()]); //South
		else cellNeighbours.add(myArray[cell.getX()+1][cell.getY()]);
		if(cell.getX() == 0) cellNeighbours.add(myArray[mySize-1][cell.getY()]); //North
		else cellNeighbours.add(myArray[cell.getX()-1][cell.getY()]);
		cell.setMyNeighbours(cellNeighbours);
	}
}
