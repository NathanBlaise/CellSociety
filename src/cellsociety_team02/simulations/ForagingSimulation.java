package cellsociety_team02.simulations;

import java.util.List;
import java.util.Random;

import cellsociety_team02.cells.Ant;
import cellsociety_team02.cells.Cell;
import cellsociety_team02.cells.CellOccupant;

public class ForagingSimulation extends Simulation{
	private final int EMPTY = 0;
	private final int FOOD = 1;
	private final int NEST = 2;
	
	private Random rand = new Random();
	private int newAntsBorn = 2;
	private int maxPheromones = 100;
	private String layoutFile = "data/Foraging.xml";
	
	public ForagingSimulation() {
		super();
		super.layoutFile = this.layoutFile;
		super.defaultFile = this.layoutFile;
		super.changeInitConfig(layoutFile);
	}
	
	@Override
	public void primeCell(Cell cell) {
		//TODO non hard programmed nest and food location, pull this from xml
		if(cell.getX() == 0 && cell.getY() == 0) {
			cell.setNextState(FOOD);
			cell.updateState();
		}else if(cell.getX() == 4 && cell.getY() == 4) {
			cell.setNextState(NEST);
			cell.updateState();
		}else
			cell.setNextState(EMPTY);
			cell.updateState();
	}
	
	@Override
	public void updateCell(Cell cell) {
		if(cell.isOccupied()) {
			for(CellOccupant ant:cell.getOccupants()) {
				updateAnt((Ant) ant, cell);
			}	
		}
		if(cell.getCurrentState() == NEST) {
			cell.addOccupants(new Ant(cell), newAntsBorn);
		}
	}
	
	private void updateAnt(Ant ant, Cell cell) {
		if(ant.hasFood()) {
			returnToNest(ant, cell);
		}
		else {
			findFood(ant, cell);
		}
	}

	private void returnToNest(Ant ant, Cell cell) {
		if(cell.getCurrentState() == FOOD) {
			Cell newOrientation = mostHomePheromones(cell.getNeighbours());
			ant.changeOrientation(newOrientation);
		}
		Cell newLocation = mostHomePheromones(ant.getViewableNeighbours());
		if(newLocation == null) {
			newLocation = mostHomePheromones(ant.getNonViewableNeighbours());
		}
		if(newLocation != null) {
			dropFoodPheromones(cell);
			ant.move(newLocation);
			if(newLocation.getCurrentState() == NEST) {
				ant.foodDropped();
			}
		}
	}

	private void findFood(Ant ant, Cell cell) {
		if(cell.getCurrentState() == NEST) {
			Cell newOrientation = mostFoodPheromones(cell.getNeighbours());
			ant.changeOrientation(newOrientation);
		}
		Cell newLocation = mostFoodPheromones(ant.getViewableNeighbours());
		if(newLocation == null) {
			newLocation = mostFoodPheromones(ant.getNonViewableNeighbours());
		}
		if(newLocation != null) {
			dropHomePheromones(cell);
			ant.move(newLocation);
			if(newLocation.getCurrentState() == FOOD) {
				ant.foundFood();
			}
		}
	}
	
	
	private Cell mostHomePheromones(List<Cell> neighbours) {
		if(neighbours.size() <= 0) return null;
		
		int max = 0;
		Cell newLocation = null;
		for(Cell neighbour:neighbours) {
			if(neighbour.survivalTime() > max) {
				newLocation = neighbour;
				max = neighbour.survivalTime();
			}
		}
		return (newLocation != null) ? newLocation : neighbours.get(rand.nextInt(neighbours.size()));
	}
	
	private Cell mostFoodPheromones(List<Cell> neighbours) {
		if(neighbours.size() <= 0) return null;
		
		int max = 0;
		Cell newLocation = null;
		for(Cell neighbour:neighbours) {
			if(neighbour.replicationTime() > max) {
				newLocation = neighbour;
				max = neighbour.replicationTime();
			}
		}
		if(newLocation == null) newLocation = neighbours.get(rand.nextInt(neighbours.size()));
		return (newLocation != null) ? newLocation : neighbours.get(rand.nextInt(neighbours.size()));
	}
	
	private int maxHomePheromonesVal(Cell newLocation) {
		int max = 0;
		for(Cell neighbour:newLocation.getNeighbours()) {
			max = Math.max(max, neighbour.survivalTime());
		}
		return max;
	}
	
	private int maxFoodPheromonesVal(Cell newLocation) {
		int max = 0;
		for(Cell neighbour:newLocation.getNeighbours()) {
			max = Math.max(max, neighbour.replicationTime());
		}
		return max;
	}
	
	private void dropHomePheromones(Cell current) {
		if(current.getCurrentState() == NEST) {
			current.setSurvivalTime(maxPheromones);
		}else {
			int desiredVal = maxHomePheromonesVal(current) - 2 - current.survivalTime();
			if(desiredVal>0) current.setSurvivalTime(current.survivalTime() + desiredVal);
		}
	}
	
	private void dropFoodPheromones(Cell current) {
		if(current.getCurrentState() == FOOD) {
			current.setReplicationTime(maxPheromones);
		}else {
			int desiredVal = maxFoodPheromonesVal(current) - 2 - current.replicationTime();
			if(desiredVal>0) current.setReplicationTime(current.replicationTime() + desiredVal);
		}
	}


}
