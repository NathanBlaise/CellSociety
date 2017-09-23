package cellsociety_team02.simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cellsociety_team02.cells.Cell;
import cellsociety_team02.cells.PredatorPreyCell;

//TODO: Add in breeding calculation to spawn new fish and sharks
//TODO: Find a better way to handle animal death and spawning
//TODO: Check if implementation works
public class PredatorPreySimulation extends Simulation {
	
	private final int KELP = 0;
	private final int FISH = 1;
	private final int SHARK = 2;
	private int sharkBreedingDays;
	private int fishBreedingDays;
	private int sharkStarveDays;
	
	@Override
	public void updateCell(Cell cell) {
		if(cell.getCurrentState() == FISH) {
			updateFishLocation((PredatorPreyCell) cell);
		}
		else if(cell.getCurrentState() == SHARK) {
			updateSharkLocation((PredatorPreyCell) cell);
		}
	}

	private void updateSharkLocation(PredatorPreyCell cell) {
		List<Cell> neighbors = cell.getNeighbours();
		List<Cell> fishSpots = availableCells(neighbors, FISH, SHARK);
		List<Cell> emptySpots = availableCells(neighbors, KELP, SHARK);
		PredatorPreyCell newLocation;
		
		if(emptySpots.size() <= 0 && fishSpots.size() <= 0) return;
		else if(fishSpots.size() <= 0) newLocation = findNewCell(emptySpots);
		else newLocation = findNewCell(fishSpots);
		
		if(cell.getNextState() != FISH) cell.setNextState(KELP);
		if(newLocation.getNextState() == FISH) {
			fishIsEaten(newLocation);
			return;
		}
		newLocation.setNextState(SHARK);
		starving(newLocation);
	}

	private void updateFishLocation(Cell cell) {
		List<Cell> neighbors = cell.getNeighbours();
		List<Cell> emptySpots = availableCells(neighbors, KELP, FISH);
		PredatorPreyCell newLocation;
		
		if(emptySpots.size() <= 0) return;
		else newLocation = findNewCell(emptySpots);

		if(cell.getNextState() != SHARK) cell.setNextState(KELP);
		if(newLocation.getNextState() != SHARK) newLocation.setNextState(FISH);
		else fishIsEaten(newLocation);
	}
	
	private List<Cell> availableCells(List<Cell> neighbors, int allowed, int blocked){
		List<Cell> emptySpots = new ArrayList<>();
		for(Cell cell:neighbors) {
			if(cell.getCurrentState() == allowed && cell.getNextState() != blocked) {
				emptySpots.add(cell);
			}
		}
		return emptySpots;
	}
	
	private PredatorPreyCell findNewCell(List<Cell> cells) {
		Random rand = new Random();
		return (PredatorPreyCell) cells.get(rand.nextInt(cells.size()));
	}
	
	private void fishIsEaten(PredatorPreyCell shark) {
		shark.setDaysUntilDeath(sharkStarveDays);
	}
	
	private void starving(PredatorPreyCell shark) {
		shark.setDaysUntilDeath(shark.getDaysUntilDeath() - 1);
		if(shark.getDaysUntilDeath() <= 0) shark.setNextState(KELP);
	}
	
	//these methods will only be used when we implement different guis for different simulations
	public void changeDaysUntilStarvation(int daysUntil) {
		sharkStarveDays = daysUntil;
	}
	
	public void changeSharkBreedTime(int daysUntil) {
		sharkBreedingDays = daysUntil;
	}
	
	public void changeFishBreedTime(int daysUntil) {
		fishBreedingDays = daysUntil;
	}

}
