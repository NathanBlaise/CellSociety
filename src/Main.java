

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import cellsociety_team02.gui.ScreenDisplay;

public class Main extends Application {
	public static final String TITLE = "Cell Society";
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
	}


	/**
	 * Start the program.
	 */
	public static void main (String[] args) {
		launch(args);
	}
}