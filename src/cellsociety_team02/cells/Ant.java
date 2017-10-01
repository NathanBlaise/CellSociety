package cellsociety_team02.cells;

import javafx.scene.image.Image;

public class Ant extends CellOccupant {
	private final int ANT_VIEW_SCOPE = 3;
	private final int DESIRED_OCCUPANCY = 10;
	private final String ANT_IMAGE = "ant.png";
	
	private boolean foodItem = false;
	
	public Ant(Cell cell) {
		super(cell);
		super.viewScope = ANT_VIEW_SCOPE;
		super.desiredOccupancy = DESIRED_OCCUPANCY;
	}
	
	public boolean hasFood() {
		return foodItem;
	}
	
	public void foundFood() {
		foodItem = true;
	}
	
	public void foodDropped() {
		foodItem = false;
	}

	@Override
	protected Image drawImage() {
		return new Image(getClass().getClassLoader().getResourceAsStream(ANT_IMAGE));
	}

}
