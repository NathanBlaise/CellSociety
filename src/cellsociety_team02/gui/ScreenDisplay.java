package cellsociety_team02.gui;

	import javafx.animation.KeyFrame;
	import javafx.animation.Timeline;
	import javafx.application.Application;
	import javafx.geometry.Insets;
	import javafx.scene.Group;
	import javafx.scene.Scene;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import javafx.scene.input.KeyCode;
	import javafx.scene.layout.ColumnConstraints;
	import javafx.scene.layout.GridPane;
	import javafx.scene.layout.RowConstraints;
	import javafx.scene.paint.Color;
	import javafx.scene.paint.Paint;
	import javafx.scene.shape.Rectangle;
	import javafx.scene.shape.Shape;
	import javafx.stage.Stage;
	import javafx.util.Duration;
	import cellsociety_team02.gui.GUI;
	import cellsociety_team02.simulations.*;
	import cellsociety_team02.cells.Cell;
	import cellsociety_team02.grid.*;

	public class ScreenDisplay{
		public static final String TITLE = "Example JavaFX";
		public static final int SIZE = 400;
		public static final Paint BACKGROUND = Color.WHITE;
		
		public double FRAMES_PER_SECOND = 1;
		public double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
		public double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;
	
		public Scene Scene;
		public Group root = new Group();
		public GUI gui;
		public GridPane myGrid;
		public Timeline animation = new Timeline();
		public KeyFrame frame;
		public Grid cellArray;
		private boolean isStarting = true;
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
			 myGrid = new GridPane();
			  for(int i = 0; i < 5; i++) {
		            ColumnConstraints column = new ColumnConstraints(40);
		            myGrid.getColumnConstraints().add(column);
		        }

		        for(int i = 0; i < 5; i++) {
		            RowConstraints row = new RowConstraints(40);
		            myGrid.getRowConstraints().add(row);
		        }
		        
		        myGrid.setStyle("-fx-grid-lines-visible: true");
		        myGrid.setPadding(new Insets(40,40,40,70));

			gui = new GUI();
			
			if (isStarting ) {
			
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
				if (!this.root.getChildren().contains(myGrid)) {
					drawGridType();
				}
				gui.isLoading = false;
				gui.isloaded = true;
				
				
			}
			
			
			if (gui.isReset) {
				if (this.root.getChildren().contains(myGrid)) {
					this.root.getChildren().remove(myGrid);
				
					gui.isReset = false;
					gui.isloaded = false;
					
					this.root.getChildren().remove(myGrid);
					if (!this.root.getChildren().contains(myGrid)) {
						drawGridType();
					}
					
					
				
					}
			}
			
			if (gui.changeSize && gui.isloaded) {
				if (this.root.getChildren().contains(myGrid)) {
				this.root.getChildren().remove(myGrid);
				}
				if (!this.root.getChildren().contains(myGrid)) {
					//draw a new grid pane
					drawNewGrid(gui.slideRatio.sideLength);
				    addCellsToGrid(gui.slideRatio.sideLength,gui.simToLoad);
				    this.root.getChildren().add(myGrid);
				    gui.changeSize = false;
				}
				
			}

		}

		private void drawGridType() {
			if (gui.simToLoad.equals("Fire")) {
				System.out.println(2);
				sim = new FireSimulation();
				
				drawNewGrid(sim.simulationSize());
				this.root.getChildren().add(myGrid);
				addCellsToGrid(sim.simulationSize(),"Fire");
			}
			if (gui.simToLoad.equals("Segregation")) {
				sim = new SegregationSimulation();
				drawNewGrid(sim.simulationSize());
				this.root.getChildren().add(myGrid);
				addCellsToGrid(sim.simulationSize(),"Segregation");
				
			}
			if (gui.simToLoad.equals("Predator-Prey")) {
				sim = new PredatorPreySimulation();
				drawNewGrid(sim.simulationSize());
				this.root.getChildren().add(myGrid);
				addCellsToGrid(sim.simulationSize(),"Predator-Prey");
			}
			if (gui.simToLoad.equals("Game of Life")) {
				sim = new LifeSimulation();
				drawNewGrid(sim.simulationSize());
				this.root.getChildren().add(myGrid);
				addCellsToGrid(sim.simulationSize(),"Game of Life");
			}
		}

		private void addCellsToGrid(int size, String type) {
			cellArray = new Grid(size,sim.cellFrequencies(),type,sim.cellColors());
			
			for (int i= 0; i<size;i++) {
				for (int j = 0; j<size; j++) {
					myGrid.add(cellArray.getArr()[i][j].getShape(), i,j);
					
					if(type == "Segregation" && cellArray.getArr()[i][j].getCurrentState() == 0) {
						sim.addEmptyCell(cellArray.getArr()[i][j]);
					}
				}
			}
		}

		public void drawNewGrid(int side) {
			myGrid = new GridPane();
			 
			  for(int i = 0; i < side ; i++) {
			        ColumnConstraints column = new ColumnConstraints(200/(side));
			        myGrid.getColumnConstraints().add(column);
			    }

			    for(int i = 0; i < side  ; i++) {
			        RowConstraints row = new RowConstraints(200/(side));
			        myGrid.getRowConstraints().add(row);
			    }
			    
			    myGrid.setStyle("-fx-grid-lines-visible: true");
			    myGrid.setPadding(new Insets(40,40,40,70));
			    
		}

		// method update CellArray every time
		public void updateCellArray() {
			for (int i= 0; i<cellArray.getSize();i++) {
				for (int j = 0; j<cellArray.getSize(); j++) {
					sim.updateCell(cellArray.getArr()[i][j]);
				}
			}
			System.out.println("one");
			for (int i= 0; i<cellArray.getSize();i++) {
				for (int j = 0; j<cellArray.getSize(); j++) {
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
