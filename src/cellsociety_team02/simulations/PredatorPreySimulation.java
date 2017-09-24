package cellsociety_team02.simulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cellsociety_team02.cells.Cell;
import cellsociety_team02.cells.PredatorPreyCell;
import cellsociety_team02.grid.Grid;

//TODO: Add in breeding calculation to spawn new fish and sharks
//TODO: Find a better way to handle animal death and spawning
//TODO: Check if implementation works
public class PredatorPreySimulation extends Simulation {
	
	private final int KELP = 0;
	private final int FISH = 1;
	private final int SHARK = 2;
	
	private int sharkBreedingDays = 4;
	private int fishBreedingDays = 2;
	private int sharkStarveDays = 4;
	private String layoutFile = "data/PredatorPrey.xml";
	
	public PredatorPreySimulation() {
		super();
		super.layoutFile = this.layoutFile;
		super.loadAttributes();
	}
	
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
		cell.setNextState(KELP);
		
		if(newLocation.getNextState() == FISH) fishIsEaten(newLocation);
		else if(newLocation.getNextState() == KELP){
			sharkSurvives(newLocation, cell);
			sharkIsStarving(newLocation, cell);
		}
		
	}

	private void updateFishLocation(PredatorPreyCell cell) {
		List<Cell> neighbors = cell.getNeighbours();
		List<Cell> emptySpots = availableCells(neighbors, KELP, FISH);
		PredatorPreyCell newLocation;
		
		if(emptySpots.size() <= 0) return;
		else newLocation = findNewCell(emptySpots);
		if(cell.getNextState() != SHARK) cell.setNextState(KELP);
		
		if(newLocation.getNextState() != SHARK) fishSurvives(newLocation, cell);
		else if(newLocation.getNextState() == SHARK) fishIsEaten(newLocation);
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
	
	private void fishSurvives(PredatorPreyCell newLocation, PredatorPreyCell oldLocation) {
		newLocation.setNextState(FISH);
		newLocation.setDaysUntilBreeding(oldLocation.getDaysUntilBreeding() - 1);
		if(newLocation.getDaysUntilBreeding() <= 0) {
			if(oldLocation.getNextState() == SHARK) {
				fishIsEaten(oldLocation);
			}
			else {
				oldLocation.setNextState(FISH);
				oldLocation.updateState();
				oldLocation.setDaysUntilBreeding(fishBreedingDays);
			}
			newLocation.setDaysUntilBreeding(fishBreedingDays);
		}
	}
	
	private void sharkSurvives(PredatorPreyCell newLocation, PredatorPreyCell oldLocation) {
		newLocation.setNextState(SHARK);
		newLocation.setDaysUntilBreeding(oldLocation.getDaysUntilBreeding() - 1);
		if(newLocation.getDaysUntilBreeding() <= 0) {
			oldLocation.setNextState(SHARK);
			oldLocation.updateState();
			oldLocation.setDaysUntilBreeding(sharkBreedingDays);
			oldLocation.setDaysUntilDeath(sharkStarveDays);
			newLocation.setDaysUntilBreeding(sharkBreedingDays);
		}
	}
	
	private void sharkIsStarving(PredatorPreyCell shark, PredatorPreyCell oldLocation) {
		shark.setDaysUntilDeath(oldLocation.getDaysUntilDeath() - 1);
		if(shark.getDaysUntilDeath() <= 0) shark.setNextState(KELP);
	}
	
	public void initValues(Object object) {
		Grid grid = (Grid) object;
		for(int i = 0; i<grid.getArr().length; i++) {
			for(int j= 0; j<grid.getArr().length; j++) {
				PredatorPreyCell cell = (PredatorPreyCell) grid.getArr()[i][j];
				if(cell.getCurrentState() == FISH) {
					cell.setDaysUntilBreeding(fishBreedingDays);
				}else if(cell.getCurrentState() == SHARK) {
					cell.setDaysUntilBreeding(sharkBreedingDays);
					cell.setDaysUntilDeath(sharkStarveDays);
				}
			}
		}
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
