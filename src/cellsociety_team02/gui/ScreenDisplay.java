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
		public static final int FRAMES_PER_SECOND = 1;
		public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
		public static final double SECOND_DELAY = 100.0/ FRAMES_PER_SECOND;
		
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
				System.out.println(1);
				int side = 5;
				
				drawNewGrid(side);
				this.root.getChildren().add(myGrid);
				myGrid.setStyle("-fx-grid-lines-visible: true");
				myGrid.setPadding(new Insets(40,40,40,70));
				
				if (gui.simToLoad.equals("Fire Simulation")) {
					sim = new FireSimulation();
					int [] propState = {10,70,20};
					Color[] colors = {Color.ORANGE,Color.GREEN,Color.RED};
			
					// initialize a cellList
					cellArray = new Grid(side,propState,"Fire",colors);
					
					for (int i= 0; i<5;i++) {
						for (int j = 0; j<5; j++) {
							myGrid.add(cellArray.getArr()[i][j].getShape(), i,j);
						}
					}
				}
				
				if (gui.simToLoad.equals("In the name of LOVE")) {
				
			
			
					// initialize a cellList
					
					
					for (int i= 0; i<5;i++) {
						for (int j = 0; j<5; j++) {
							Rectangle Shape = new Rectangle(40, 40, Color.PINK);
							
							if (i == 2 && j != 0 && j!=4) {
								myGrid.add(Shape, i, j);
							}
							
						}
					}
				}
				
				
				
				
				
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
					System.out.println(1);
					int side = 5;
					
					drawNewGrid(side);
					this.root.getChildren().add(myGrid);
					myGrid.setStyle("-fx-grid-lines-visible: true");
					myGrid.setPadding(new Insets(40,40,40,70));
					
					if (gui.simToLoad.equals("Fire Simulation")) {
						sim = new FireSimulation();
						int [] propState = {10,70,20};
						Color[] colors = {Color.ORANGE,Color.GREEN,Color.RED};
				
						// initialize a cellList
						cellArray = new Grid(side,propState,"Fire",colors);
						
						for (int i= 0; i<5;i++) {
							for (int j = 0; j<5; j++) {
								myGrid.add(cellArray.getArr()[i][j].getShape(), i,j);
							}
						}
					}
					}
					
					
				
					}
			}
			
			if (gui.changeSize && gui.isloaded) {
				if (this.root.getChildren().contains(myGrid)) {
				this.root.getChildren().remove(myGrid);
				}
				if (!this.root.getChildren().contains(myGrid)) {
						//draw a new grid pane
						int side = gui.slideRatio.sideLength;
						
						drawNewGrid (side);
						// Redraw the pane every time we change the size
					    //cellArray.setSize(side);
				        int [] propState = {40,40,20};
						Color[] colors = {Color.RED,Color.ORANGE,Color.GREEN};
				        cellArray = new Grid(gui.slideRatio.sideLength,propState,"Fire",colors);
						
						for (int i= 0; i<gui.slideRatio.sideLength;i++) {
							for (int j = 0; j<gui.slideRatio.sideLength; j++) {
								myGrid.add(cellArray.getArr()[i][j].getShape(), i,j);
							}
						}
				this.root.getChildren().add(myGrid);
				gui.changeSize = false;
				
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
