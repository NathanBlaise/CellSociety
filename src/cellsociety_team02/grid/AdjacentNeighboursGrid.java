package cellsociety_team02.grid;

import cellsociety_team02.cells.Cell;
import javafx.scene.paint.Color;

public class AdjacentNeighboursGrid extends Grid{
	
	public AdjacentNeighboursGrid(int size, int[] propState, Color[] colors) {
		super(size,propState,colors);
	}
	
	@Override
	protected void storeNeighbours(Cell cell){
		cellNeighbours.clear();
		if(cell.getY()<mySize - 1) cellNeighbours.add(myArray[cell.getX()][cell.getY()+1]); //East
		if(cell.getY()>0) cellNeighbours.add(myArray[cell.getX()][cell.getY()-1]); //West
		if(cell.getX()<mySize - 1) cellNeighbours.add(myArray[cell.getX()+1][cell.getY()]); //South
		if(cell.getX()>0) cellNeighbours.add(myArray[cell.getX()-1][cell.getY()]); //North
		cell.setMyNeighbours(cellNeighbours);
	}
	
}
