package cellsociety_team02.grid;

import java.util.ArrayList;

import cellsociety_team02.cells.Cell;
import javafx.scene.paint.Color;

public class ToroidalGrid extends Grid{

	public ToroidalGrid(int size, int[] propState, Color[] colors) {
		super(size,propState,colors);
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
	
}
