package cellsociety_team02.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import cellsociety_team02.gui.GUI;
import cellsociety_team02.simulations.*;

import java.util.ArrayList;
import java.util.Optional;

import cellsociety_team02.cells.Cell;
import cellsociety_team02.grid.*;

public class ScreenDisplay{
	public double FRAMES_PER_SECOND = 1;
	public double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;

	private Scene Scene;
	private Group root = new Group();
	private GUI gui;
	private GridPane myGrid;
	private Timeline animation = new Timeline();
	private KeyFrame frame;
	private boolean isStarting = true;
	private boolean isPaused = true;
	private int gridSize = 5;
	private Simulation sim;
	private Grid cellArray;
	private int round = 0;
	
	/**
	 * Constructor: Screen Display class
	 */

	public ScreenDisplay (int width, int height, Paint background) {
		frame  = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> this.step(SECOND_DELAY));
		
	    animation.setCycleCount(Timeline.INDEFINITE);
	    animation.getKeyFrames().add(frame);
	     
		Scene = new Scene(root, width, height, background);
		Scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		drawNewGrid();
		gui = new GUI();
		
		if (isStarting) {
			root.getChildren().addAll(gui.paneBox);
			root.getChildren().addAll(gui.SliderBox);
			root.getChildren().addAll(gui.xyChart);
			root.getChildren().addAll(gui.varSliderBox);
			root.getChildren().add(gui.SlidTittle);
		}
		
		initResetButton();
		initStepButton();
		initGoButton();
		initPauseButton();
		
	}

	private void initPauseButton() {
		gui.pauseButton.setOnAction((event) -> {
			isPaused = !isPaused;
			
			if (isPaused) {
				animation.pause();
				gui.pauseButton.setText("PLAY");
				gui.buttons.getChildren().add(gui.stepButton);
			} else {
				animation.play();
				gui.pauseButton.setText("PAUSE");
				gui.buttons.getChildren().remove(gui.stepButton);
			}
		});
	}

	private void initGoButton() {
		gui.goButton.setOnAction((event) -> {
			gui.simToLoad = gui.simulationLoader.getValue();
			this.root.getChildren().remove(myGrid);
			drawGridType();
		});
	}

	private void initStepButton() {
		gui.stepButton.setOnAction((event) -> {
			updateCellArray();
		});
	}

	private void initResetButton() {
		gui.resetButton.setOnAction((event) -> {
			resetGrid();
		});
	}
	
	public Scene getScene() {
		return Scene;
	}

	public void step (double elapsedTime) {
		for (int i = 0; i < gui.xyChart.seriesList.size();i++) {
			gui.xyChart.updateLineChart(round, sim.cellFrequencies()[i],gui.xyChart.seriesList.get(i));
		}
		//gui.xyChart.updateLineChart(round, sim.cellFrequencies()[0]);
		updateCellArray();

		if (gui.changeSpeed) {
			animation.setRate(gui.slideSpeed.speed);
			gui.changeSpeed = false;
		}
		
		if (gui.changeSize) {
				
				gridSize = gui.slideSize.sideLength;
				resetGrid();
			    gui.changeSize = false;
		}	
	}

	private void drawGridType() {
		if(gui.simToLoad == null) return; //add text display here to user
		
		if (gui.simToLoad.equals("Fire")) sim = new FireSimulation();
		else if (gui.simToLoad.equals("Segregation")) sim = new SegregationSimulation();
		else if (gui.simToLoad.equals("Predator-Prey")) sim = new PredatorPreySimulation();
		else if (gui.simToLoad.equals("Game of Life")) sim = new LifeSimulation();
		else if (gui.simToLoad.equals("RPS")) sim = new RPSSimulation();
		gridSize = sim.simulationSize();
		gui.xyChart.setTitle(gui.simToLoad+ " Simulation");
		// want to change size before initializing the grid
		if (gui.changeSize) {
			gridSize = gui.slideSize.sideLength;
		    gui.changeSize = false;
	}	
		
		gui.slideSize.setVal(gridSize);
		resetGrid();
		
		for (int i = 0; i < sim.cellColors().length;i++) {
			XYChart.Series line = new XYChart.Series();
			gui.xyChart.getData().add(line);
			line.setName(Integer.toString(i));
			
			gui.xyChart.seriesList.add(line);
			line.getData().add(new XYChart.Data(0, sim.cellFrequencies()[i]));
			
		}
		
	}
	
	

	private void resetGrid() {
		this.root.getChildren().remove(myGrid);
		drawNewGrid();
		this.root.getChildren().add(myGrid);
		sim.clearValues();
		addCellsToGrid();
		gui.xyChart.getData().clear();
		gui.xyChart.seriesList.clear();
	}

	private void addCellsToGrid() {
		cellArray = new Grid(gridSize, sim.cellFrequencies(), sim.cellColors());
		
		for (int i= 0; i<gridSize;i++) {
			for (int j = 0; j<gridSize; j++) {
				Cell cell = cellArray.getArr()[i][j];
				myGrid.add(cell.getShape(), i,j);
				sim.primeCell(cell);
			}
		}
	}

	public void drawNewGrid() {
		myGrid = new GridPane();
		
		
		 
		for(int i = 0; i < gridSize ; i++) {
			ColumnConstraints column = new ColumnConstraints(360/(gridSize));
			myGrid.getColumnConstraints().add(column);
		}

	    for(int i = 0; i < gridSize  ; i++) {
	        RowConstraints row = new RowConstraints(360/(gridSize));
	        myGrid.getRowConstraints().add(row);
	    }
	    
	    myGrid.setStyle("-fx-grid-lines-visible: true");
	    //Insets(double top, double right, double bottom, double left)
	    //Make the border invisible
	    if (sim instanceof RPSSimulation) {
	    	myGrid.setStyle("-fx-grid-lines-visible: false");
	    }
	    myGrid.setPadding(new Insets(60,60,60,50)); 
	}

	public void updateCellArray() {
		round += 1;
		int size = cellArray.getSize();
		for (int i= 0; i<size;i++) {
			for (int j = 0; j<size; j++) {
				sim.updateCell(cellArray.getArr()[i][j]);
			}
		}
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
		boolean isPopedOut = false;
		int size = cellArray.getSize();
		for (int i= 0; i<size;i++) {
			for (int j = 0; j<size; j++) {
				//System.out.println(cellArray.getArr()[i][j].getShape().getLayoutX());
				
				double testX = x - cellArray.getArr()[i][j].getShape().getLayoutX();
				double testY = y - cellArray.getArr()[i][j].getShape().getLayoutY();
				
				if (testX < 0) {
					testX *= -1;
				}
				if (testY < 0){
					testY *= -1;
				}
				
				//System.out.println("testX: "+testX + ""+ "testY: "+testY);
				if (cellArray.getArr()[i][j].getShape().contains(testX, testY) && (!isPopedOut)) {
					Cell selected = cellArray.getArr()[i][j];
					isPopedOut = true;
					String type = gui.simToLoad;
					//Color color = cellArray.getArr()[i][j].getMyColors()[cellArray.getArr()[i][j].getCurrentState()];
					// pop up an alert box
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog to Change Color");
					alert.setHeaderText("Swaggy T helps you to change color!");
					alert.setContentText("This is a " + type + " cell"+ "\n" + "Choose the color you want to change\n" );
					Image image = new Image (getClass().getClassLoader().getResourceAsStream("dukeDqd.JPG"));
					ImageView imageView = new ImageView(image);
					alert.setGraphic(imageView);
					
					ButtonType buttonTypeOne = new ButtonType("One");
					ButtonType buttonTypeTwo = new ButtonType("Two");
					ButtonType buttonTypeThree = new ButtonType("Three");
					ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

					alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeThree, buttonTypeCancel);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == buttonTypeOne){
					    // ... user chose "One"
						selected.setNextState(0);
						selected.updateState();
						
					} else if (result.get() == buttonTypeTwo) {
					    // ... user chose "Two"
						selected.setNextState(1);
						selected.updateState();
						
					} else if (result.get() == buttonTypeThree) {
					    // ... user chose "Three"
						selected.setNextState(2);
						selected.updateState();
					} else {
					    // ... user chose CANCEL or closed the dialog
					}
				}
			}
		}
	}
}
