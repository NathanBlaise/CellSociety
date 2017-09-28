package cellsociety_team02.cells;

import javafx.scene.paint.Color;

public class PredatorPreyCell extends Cell{
	private int daysUntilDeath;
	private int daysUntilBreeding;
	
	public PredatorPreyCell(int xPosition, int yPosition, int startingState, Color[] colors, int sideLength, int gridSize, Cell[][] gridArray){
		super(xPosition, yPosition, startingState, colors, sideLength, gridSize, gridArray);
	}
	
	public int getDaysUntilDeath() {
		return daysUntilDeath;
	}
	
	public int getDaysUntilBreeding() {
		return daysUntilBreeding;
	}
	
	public void setDaysUntilDeath(int newDaysUntil) {
		daysUntilDeath = newDaysUntil;
	}
	
	public void setDaysUntilBreeding(int newDaysUntil) {
		daysUntilBreeding = newDaysUntil;
	}
	
	
}
