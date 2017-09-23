

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import cellsociety_team02.gui.ScreenDisplay;

public class Main extends Application {
	public static final String TITLE = "Example JavaFX";
	public static final int SIZE = 400;
	public static final int WIDTH = 600;
	public static final Paint BACKGROUND = Color.WHITE;
	public static final int FRAMES_PER_SECOND = 60;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	
	
	/**
	 * Initialize what will be displayed and how it will be updated.
	 */
	@Override
	public void start (Stage s) {
		
		ScreenDisplay myScene = new ScreenDisplay(WIDTH,SIZE,BACKGROUND);
		s.setScene(myScene.getScene());
		s.setTitle(TITLE);
		s.show();
		// attach "game loop" to timeline to play it
		
		
		
		myScene.animation.play();
	}


	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
}