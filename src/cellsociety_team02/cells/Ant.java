package cellsociety_team02.cells;

public class Ant extends CellOccupant {
	private final int ANT_VIEW_SCOPE = 3;
	
	public Ant(Cell cell) {
		super(cell);
		super.viewScope = ANT_VIEW_SCOPE;
	}

}
