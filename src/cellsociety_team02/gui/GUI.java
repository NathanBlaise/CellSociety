package cellsociety_team02.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import javafx.scene.control.ComboBox;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
/**
 * @author supertony
 *
 */
public class GUI {

	protected GridPane pane;
	protected Font font = Font.getDefault();
	protected VBox paneBox;
	
	// Create a HBox for Buttons
	protected Button resetButton;
	protected Button pauseButton;
	protected Button stepButton;
	protected Button goButton;
	protected HBox buttons;

	
	// boolean values for each button
	protected boolean isReset = false;
	protected boolean isPause = true;
	protected boolean isBack = false;
	protected boolean isStep = false;
	protected boolean isLoading = false;

	// create a combo box to select a certain type of simulation
	private ComboBox<String> simulationLoader;
	private String simToLoad;
	public GUI(GridPane pane) {
		init();
	}
	
	protected void init() {
		ObservableList<String> list = FXCollections.observableArrayList("Fire Simulation","Segregation Simulation","Game of Life","WatorWorld");
		paneBox = new VBox(30);
		
		goButton = new Button("GO");
		goButton.setOnAction((event) -> {
			this.isLoading = true;
			simToLoad = simulationLoader.getValue();
			System.out.println(simToLoad);
			});
		
		resetButton = new Button("RESET");
		resetButton.setOnAction((event) -> {
		this.isReset = true;
		});
		stepButton = new Button("STEP");
		stepButton.setOnAction((event) -> {
		this.isStep = true;
		});
		pauseButton = new Button("PLAY");
		pauseButton.setOnAction((event) -> {
			this.isPause = !this.isPause;
			
			if (this.isPause) {
				pauseButton.setText("PLAY");
				buttons.getChildren().add(stepButton);
			} else {
				pauseButton.setText("PAUSE");
				buttons.getChildren().remove(stepButton);
			}
		});
		
		
		buttons = new HBox();
		buttons.getChildren().add(resetButton);
		buttons.getChildren().add(pauseButton);
		buttons.getChildren().add(stepButton);

		//initialize simulation loader
		HBox LoaderBox = new HBox();
		simulationLoader = new ComboBox<String>();
		simulationLoader.setPromptText("Choose Simulation");
		simulationLoader.setEditable(true);
		simulationLoader.setVisibleRowCount(3);
		simulationLoader.setItems(list);
		LoaderBox.getChildren().add(simulationLoader);
		LoaderBox.getChildren().add(goButton);
		// add all elements in the VBox
		
		//Insets(double top, double right, double bottom, double left)
		
		paneBox.setPadding(new Insets(30,30,30,30));
		paneBox.getChildren().add(LoaderBox);
		paneBox.getChildren().add(buttons);
		paneBox.setStyle("-fx-background-color: #336699;");
		paneBox.setLayoutX(0);
		paneBox.setLayoutY(276);
		System.out.println();
		paneBox.setPrefWidth(400);
	}

	
	
	/**
	 * @param: adjust spacing of buttons hbox
	 * @param: adjust padding of buttons hbox
	 */
	public void setupButtonLayout (double spacing, double padding) {
		this.buttons.setSpacing(spacing);
		this.buttons.setPadding(new Insets(padding));

	}
	
	public void setButtonPos(double xPos, double yPos, double width) {
		paneBox.setLayoutX(xPos);
		paneBox.setLayoutY(yPos);
		paneBox.setPrefWidth(width);
	}
	
	
	
}
