package cellsociety_team02.simulations;

import java.util.ArrayList;
import java.util.List;

import cellsociety_team02.cells.Cell;

//TODO: Find a better way to handle animal death and spawning
//TODO: Check if implementation works
public class PredatorPreySimulation extends Simulation {
	
	private final int KELP = 0;
	private final int FISH = 1;
	private final int SHARK = 2;
	
	private int sharkBreedingDays = 1;
	private int fishBreedingDays = 1;
	private int sharkStarveDays = 1;
	private String layoutFile = "data/PredatorPrey.xml";
	
	public PredatorPreySimulation() {
		super();
		super.layoutFile = this.layoutFile;
		super.defaultFile = this.layoutFile;
		super.changeInitConfig(layoutFile);
		setLifeValues();
	}
	
	public void setLifeValues() {
		if(queryAttributes("sharkBreedingDays") != null) {
			sharkBreedingDays = Integer.parseInt(queryAttributes("sharkBreedingDays"));
		}
		if(queryAttributes("fishBreedingDays") != null) {
			fishBreedingDays = Integer.parseInt(queryAttributes("fishBreedingDays"));
		}
		if(queryAttributes("sharkStarveDays") != null) {
			sharkStarveDays = Integer.parseInt(queryAttributes("sharkStarveDays"));
		}
	}
	

	@Override
	public void primeCell(Cell cell) {
		if(cell.getCurrentState() == FISH) {
			cell.setReplicationTime(fishBreedingDays);
		}else if(cell.getCurrentState() == SHARK) {
			cell.setReplicationTime(sharkBreedingDays);
			cell.setSurvivalTime(sharkStarveDays);
		}
	}
	
	@Override
	public void updateCell(Cell cell) {
		if(cell.getCurrentState() == FISH) {
			updateFishLocation(cell);
		}
		else if(cell.getCurrentState() == SHARK) {
			updateSharkLocation(cell);
		}
	}

	private void updateSharkLocation(Cell cell) {
		List<Cell> neighbors = cell.getNeighbours();
		List<Cell> fishSpots = availableCells(neighbors, FISH, SHARK);
		List<Cell> emptySpots = availableCells(neighbors, KELP, SHARK);
		Cell newLocation;
		
		if(emptySpots.size() <= 0 && fishSpots.size() <= 0) newLocation = cell;
		else if(fishSpots.size() <= 0) newLocation = cell.chooseRandomNeighbour(emptySpots);
		else newLocation = cell.chooseRandomNeighbour(fishSpots);
		cell.setNextState(KELP);
		
		if(newLocation.getNextState() == FISH) fishIsEaten(newLocation);
		else if(newLocation.getNextState() == KELP){
			sharkSurvives(newLocation, cell);
			sharkIsStarving(newLocation, cell);
		}
		
	}

	private void updateFishLocation(Cell cell) {
		List<Cell> neighbors = cell.getNeighbours();
		List<Cell> emptySpots = availableCells(neighbors, KELP, FISH);
		Cell newLocation;
		
		if(emptySpots.size() <= 0) newLocation = cell;
		else {
			newLocation = cell.chooseRandomNeighbour(emptySpots);
			if(cell.getNextState() != SHARK) cell.setNextState(KELP);
		}
		
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
	
	private void fishIsEaten(Cell shark) {
		shark.setSurvivalTime(sharkStarveDays);
		shark.setNextState(SHARK);
	}
	
	private void fishSurvives(Cell newLocation, Cell oldLocation) {
		newLocation.setNextState(FISH);
		newLocation.setReplicationTime(oldLocation.replicationTime() - 1);
		if(newLocation.replicationTime() <= 0) {
			if(oldLocation.getNextState() == SHARK) {
				fishIsEaten(oldLocation);
			}
			else {
				oldLocation.setNextState(FISH);
				oldLocation.updateState();
				oldLocation.setReplicationTime(fishBreedingDays);
			}
			newLocation.setReplicationTime(fishBreedingDays);
		}
	}
	
	private void sharkSurvives(Cell newLocation, Cell oldLocation) {
		newLocation.setNextState(SHARK);
		newLocation.setReplicationTime(oldLocation.replicationTime() - 1);
		if(newLocation.replicationTime() <= 0) {
			oldLocation.setNextState(SHARK);
			oldLocation.updateState();
			oldLocation.setReplicationTime(sharkBreedingDays);
			oldLocation.setSurvivalTime(sharkStarveDays);
			newLocation.setReplicationTime(sharkBreedingDays);
		}
	}
	
	private void sharkIsStarving(Cell shark, Cell oldLocation) {
		shark.setSurvivalTime(oldLocation.survivalTime() - 1);
		if(shark.survivalTime() <= 0) shark.setNextState(KELP);
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
