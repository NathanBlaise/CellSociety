package cellsociety_team02.simulations;

import java.util.List;
import java.util.Random;

import cellsociety_team02.cells.Ant;
import cellsociety_team02.cells.Cell;
import cellsociety_team02.cells.CellOccupant;

/**
 * Not fully working, appears to be some slight bug with the occupancy or moving algorithm. In general
 * models a path of ants back and forth between food and a nest where "crumbs" are left in
 * the form of pheromones to travel back and forth. The current implementation tends to correctly
 * find the path between any nest and food source but then stops spawning ants for some reason.
 * @author benwelton
 *
 */
public class ForagingSimulation extends Simulation{
	private final int EMPTY = 0;
	private final int FOOD = 1;
	private final int NEST = 2;
	
	private Random rand = new Random();
	private int antsBornIndex;
	private int maxPheromonesIndex;
	private String layoutFile = "data/Foraging.xml";
	private String[] vars = {"newAntsBorn", "maxPheromones"};
	private double[] vals = {2, 10};
	private double[] maxs = {5, 100};
	
	public ForagingSimulation() {
		super();
		super.setDefaultVariables(layoutFile, vars, vals, maxs);
		super.changeInitConfig(layoutFile);
		antsBornIndex = variables.indexOf("newAntsBorn");
		maxPheromonesIndex = variables.indexOf("maxPheromones");
	}
	
	@Override
	public void primeCell(Cell cell) {
		if(cell.getX() == 1 && cell.getY() == 1) {
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
			cell.addOccupants(new Ant(cell), (int) variableVals.get(antsBornIndex).doubleValue());
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
		
		double max = 0;
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
		
		double max = 0;
		Cell newLocation = null;
		for(Cell neighbour:neighbours) {
			if(neighbour.replicationTime() > max) {
				newLocation = neighbour;
				max = neighbour.replicationTime();
			}
		}
		return (newLocation != null) ? newLocation : neighbours.get(rand.nextInt(neighbours.size()));
	}
	
	private int maxHomePheromonesVal(Cell newLocation) {
		double max = 0;
		for(Cell neighbour:newLocation.getNeighbours()) {
			max = Math.max(max, neighbour.survivalTime());
		}
		return (int) max;
	}
	
	private double maxFoodPheromonesVal(Cell newLocation) {
		double max = 0;
		for(Cell neighbour:newLocation.getNeighbours()) {
			max = Math.max(max, neighbour.replicationTime());
		}
		return max;
	}
	
	private void dropHomePheromones(Cell current) {
		if(current.getCurrentState() == NEST) {
			current.setSurvivalTime(variableVals.get(maxPheromonesIndex));
		}else {
			double desiredVal = maxHomePheromonesVal(current) - 2 - current.survivalTime();
			if(desiredVal>0) current.setSurvivalTime(current.survivalTime() + desiredVal);
		}
	}
	
	private void dropFoodPheromones(Cell current) {
		if(current.getCurrentState() == FOOD) {
			current.setReplicationTime(variableVals.get(maxPheromonesIndex));
		}else {
			double desiredVal = maxFoodPheromonesVal(current) - 2 - current.replicationTime();
			if(desiredVal>0) current.setReplicationTime(current.replicationTime() + desiredVal);
		}
	}


}
