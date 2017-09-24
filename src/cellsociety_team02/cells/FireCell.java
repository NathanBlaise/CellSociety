package cellsociety_team02.cells;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class FireCell extends Cell {
	
	public FireCell(int xPosition, int yPosition, int startingState, Color[] colors, int sideLength, int gridSize, Cell[][] gridArray){
		super(xPosition, yPosition, startingState, colors, sideLength, gridSize, gridArray);
	}
	
	@Override
	public ArrayList<Cell> getNeighbours(){
		 myNeighbours.clear();
		if(yPos<myGridSize - 1) myNeighbours.add(myGridArray[xPos][yPos+1]); //East
		if(yPos>0) myNeighbours.add(myGridArray[xPos][yPos-1]); //West
		if(xPos<myGridSize - 1) myNeighbours.add(myGridArray[xPos+1][yPos]); //South
		if(xPos>0) myNeighbours.add(myGridArray[xPos-1][yPos]); //North
		
		return myNeighbours;
	}
	
}
