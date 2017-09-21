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
	import cellsociety_team02.gui.GuiUpdate;

	public class ScreenDisplay{
		public static final String TITLE = "Example JavaFX";
		public static final int SIZE = 400;
		public static final Paint BACKGROUND = Color.WHITE;
		public static final int FRAMES_PER_SECOND = 60;
		public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
		public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
		
		public Scene Scene;
		public Group root = new Group();
		public GuiUpdate gui;
		public GridPane myGrid;
		public Timeline animation = new Timeline();
		public KeyFrame frame;
		
		
		/**
		 * Constructor: Screen Display class
		 */

		ScreenDisplay (int width, int height, Paint background) {
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
		        myGrid.setPadding(new Insets(40,40,40,30));

			gui = new GuiUpdate(myGrid);
			
			root.getChildren().addAll(gui.paneBox);
			
		

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
			
			
			if (gui.isLoading) {
				if (!this.root.getChildren().contains(myGrid)) {
					System.out.println("yes");
				this.root.getChildren().add(myGrid);
				}
			}
			
			if (gui.isReset) {
				if (this.root.getChildren().contains(myGrid)) {
					this.root.getChildren().remove(myGrid);
					
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
