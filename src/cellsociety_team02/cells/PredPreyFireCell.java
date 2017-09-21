package cellsociety_team02.cells;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import cellsociety_team02.grid.*;

public class PredPreyFireCell extends Cell {
	
	public PredPreyFireCell(int xPosition, int yPosition, int startingState, Color[] colors, int sideLength, Grid grid){
		super(xPosition, yPosition, startingState, colors, sideLength, grid);
	}
	
	@Override
	public ArrayList<Cell> getNeighbours(){
		if(yPos<myGrid.getSize - 1) myNeighbours.add(myGrid[xPos][yPos+1]); //East
		if(yPos>0) myNeighbours.add(myGrid[xPos][yPos-1]); //West
		if(xPos<myGrid.getSize - 1) myNeighbours.add(myGrid[xPos+1][yPos]); //South
		if(xPos>0) myNeighbours.add(myGrid[xPos-1][yPos]); //North
	}
	
}
