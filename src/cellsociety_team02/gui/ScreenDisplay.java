package cellsociety_team02.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import cellsociety_team02.gui.GUI;
import cellsociety_team02.simulations.*;
import cellsociety_team02.grid.*;

public class ScreenDisplay{
	private static final String TITLE = "Cell Society";
	private static final int SIZE = 400;
	private static final Paint BACKGROUND = Color.WHITE;
	
	public double FRAMES_PER_SECOND = 1;
	public double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;

	private Scene Scene;
	private Group root = new Group();
	private GUI gui;
	private GridPane myGrid;
	private Timeline animation = new Timeline();
	private KeyFrame frame;
	private Grid cellArray;
	private boolean isStarting = true;
	private int gridSize = 5;
	private Simulation sim;
	
	
	/**
	 * Constructor: Screen Display class
	 */

	public ScreenDisplay (int width, int height, Paint background) {
		frame  = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> this.step(SECOND_DELAY));
		
	    animation.setCycleCount(Timeline.INDEFINITE);
	    animation.getKeyFrames().add(frame);
	     
		Scene = new Scene(root, width, height, background);
		drawNewGrid();
		gui = new GUI();
		
		if (isStarting) {
			root.getChildren().addAll(gui.paneBox);
			root.getChildren().addAll(gui.SliderBox);
		}
	}
	
	public Scene getScene() {
		return Scene;
	}

	public void setScene(Scene scene) {
		Scene = scene;
	}

	public Group getRoot() {
		return root;
	}

	public void setRoot(Group root) {
		this.root = root;
	}
	
	public void startGameLoop() {
		animation.play();
	}

	public void step (double elapsedTime) {
		//change speed
		if (gui.changeSpeed) {
			FRAMES_PER_SECOND = 1+ gui.slideSpeed.speed;
			MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
			SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;
			frame  = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
					e -> this.step(SECOND_DELAY));
			
		    animation.getKeyFrames().add(frame);
		     
			System.out.println(FRAMES_PER_SECOND);
			
			gui.changeSpeed = false;
		}
		
		//for play button
		if (!gui.isPause) {
			updateCellArray();
		}
		
		// After pressing the button "STEP"
		if (gui.isStep) {
			updateCellArray();
			gui.isStep = !gui.isStep;
		}
		
		// After pressing the button "GO"
		if (gui.isLoading) {
			this.root.getChildren().remove(myGrid);
			drawGridType();
			gui.isLoading = false;
			gui.isloaded = true;
		}
		
		
		if (gui.isReset) {
			if (this.root.getChildren().contains(myGrid)) {
				this.root.getChildren().remove(myGrid);
			
				gui.isReset = false;
				gui.isloaded = false;

				resetGrid();
			}
		}
		
		if (gui.changeSize && gui.isloaded) {
			if (this.root.getChildren().contains(myGrid)) {
				this.root.getChildren().remove(myGrid);
			}
			if (!this.root.getChildren().contains(myGrid)) {
				//draw a new grid pane
				gridSize = gui.slideRatio.sideLength;
				drawNewGrid();
			    addCellsToGrid(gui.simToLoad);
			    this.root.getChildren().add(myGrid);
			    gui.changeSize = false;
			}
			
		}

	}

	private void drawGridType() {
		if (gui.simToLoad.equals("Fire")) sim = new FireSimulation();
		else if (gui.simToLoad.equals("Segregation")) sim = new SegregationSimulation();
		else if (gui.simToLoad.equals("Predator-Prey")) sim = new PredatorPreySimulation();
		else if (gui.simToLoad.equals("Game of Life")) sim = new LifeSimulation();
		gridSize = sim.simulationSize();
		resetGrid();
	}

	private void resetGrid() {
		drawNewGrid();
		this.root.getChildren().add(myGrid);
		addCellsToGrid(sim.queryAttributes("Type"));
		
		if(gui.simToLoad.equals("Predator-Prey")) {
			sim.initValues(cellArray);
		}
	}

	private void addCellsToGrid(String type) {
		cellArray = new Grid(gridSize,sim.cellFrequencies(),type,sim.cellColors());
		
		for (int i= 0; i<gridSize;i++) {
			for (int j = 0; j<gridSize; j++) {
				myGrid.add(cellArray.getArr()[i][j].getShape(), i,j);
				
				if(type.equals("Segregation") && cellArray.getArr()[i][j].getCurrentState() == 0) {
					sim.addEmptyCell(cellArray.getArr()[i][j]);
				}
			}
		}
	}

	public void drawNewGrid() {
		myGrid = new GridPane();
		 
		for(int i = 0; i < gridSize ; i++) {
			ColumnConstraints column = new ColumnConstraints(200/(gridSize));
			myGrid.getColumnConstraints().add(column);
		}

	    for(int i = 0; i < gridSize  ; i++) {
	        RowConstraints row = new RowConstraints(200/(gridSize));
	        myGrid.getRowConstraints().add(row);
	    }
	    
	    myGrid.setStyle("-fx-grid-lines-visible: true");
	    myGrid.setPadding(new Insets(40,40,40,70)); 
	}

	// method update CellArray every time
	public void updateCellArray() {
		int size = cellArray.getSize();
		for (int i= 0; i<size;i++) {
			for (int j = 0; j<size; j++) {
				sim.updateCell(cellArray.getArr()[i][j]);
			}
		}
		System.out.println("one");
		for (int i= 0; i<size;i++) {
			for (int j = 0; j<size; j++) {
				cellArray.getArr()[i][j].updateState();
			}
		}
	}


	// What to do each time a key is pressed
	public void handleKeyInput (KeyCode code) {
		
	}

	// What to do each time a key is pressed
	public void handleMouseInput (double x, double y) {
			
	}
}
