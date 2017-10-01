package cellsociety_team02.gui;

import java.util.HashMap;
import javafx.scene.control.ComboBox;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
/**
 * @author supertony
 *
 */
public class GUI {

	protected GridPane pane;
	protected Font font = Font.getDefault();
	protected VBox paneBox;
	protected VBox SliderBox;
	
	// Create a HBox for Buttons
	protected Button resetButton;
	protected Button pauseButton;
	protected Button stepButton;
	protected Button goButton;
	protected HBox buttons;

	
	// boolean values for each button
	protected boolean isBack = false;
	protected boolean changeSize = false;
	// create a combo box to select a certain type of simulation
	protected ComboBox<String> simulationLoader;
	protected String simToLoad;
	protected HashMap<String, Double> values = new HashMap<String, Double>();
	public SliderBar slideSpeed;
	public SliderBar slideRatio;
	public SliderBar slideSize;
	//help to change the size of gridPane
	protected boolean changeSpeed = false;
	
	public GUI() {
		init();
	}
	
	protected void init() {
		ObservableList<String> list = FXCollections.observableArrayList("Fire","Segregation","Game of Life","Predator-Prey", "RPS", "Foraging");
		paneBox = new VBox(30);
		
		goButton = new Button("GO");
		resetButton = new Button("RESET");
		stepButton = new Button("STEP");
		pauseButton = new Button("PLAY");
		
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
		
		paneBox.setPadding(new Insets(30,30,30,70));
		paneBox.getChildren().add(LoaderBox);
		paneBox.getChildren().add(buttons);
		paneBox.setStyle("-fx-background-color: #336699;");
		paneBox.setLayoutX(0);
		paneBox.setLayoutY(276);
		paneBox.setPrefWidth(600);
		
		// add SlideBars
		SliderBox = new VBox();
		slideSpeed = new SliderBar("Speed", 0.0,1.0,20.0 );
		slideRatio = new SliderBar ("Blocks Ratio",0.0,0.0,1.0);
		slideSize = new SliderBar("Size",0.0,5.0,50.0);
		SliderBox.getChildren().add(slideRatio);
		SliderBox.getChildren().add(slideSize);
		SliderBox.getChildren().add(slideSpeed);
		SliderBox.setSpacing(20);
		SliderBox.setLayoutY(30);
		SliderBox.setLayoutX(400);
		SliderBox.setPrefWidth(180);
		SliderBox.setPrefHeight(200);
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
	
	
	protected class SliderBar extends VBox {
		private String name;
		private Label nameLabel;
		private Slider slider;
		private TextField valueField;
		int sideLength;
		int speed;
		
		protected SliderBar(String n, Double x, Double y, Double z) {
			name = n;
			slider = new Slider();
			slider.setValue(x);
			slider.setMin(y);
			slider.setMax(z);
			slider.setShowTickMarks(true);
			slider.setShowTickLabels(true);
			slider.setMajorTickUnit((slider.getMax() - slider.getMin()));
			nameLabel = new Label(name + ":");
			nameLabel.setFont(font);
			valueField = new TextField(String.format("%.2f", slider.getValue()));
			
			if (name == "Size") {
				String side = Integer.toString(((int)(slider.getValue()))) ;
				valueField = new TextField(side+"×"+side);
			}
			valueField.setPrefColumnCount(valueField.getText().length());
			valueField.setAlignment(Pos.CENTER);
			valueField.textProperty()
					.addListener((ObservableValue<? extends String> ob, String oldVal, String newVal) -> {
						valueField.setPrefColumnCount(valueField.getText().length());
					});
			valueField.setOnAction(e -> {
				try {
					String current = this.valueField.getCharacters().toString();
					double val = Double.parseDouble(current);
					if (val >= getMin() && val <= getMax()) {
						setVal(val);
						
						values.put(name, val);
		
					} else {
						
						valueField.setText(String.format("%.2f", this.slider.getValue()));
					}
				} catch (NumberFormatException d) {
					valueField.setText(String.format("%.2f", this.slider.getValue()));
				}
			});
			HBox lowerBox = new HBox();
			lowerBox.getChildren().addAll(slider, valueField);
			lowerBox.setSpacing(8);
			
			//lowerBox.setStyle("-fx-background-color: #336699;");
			this.getChildren().addAll(nameLabel, lowerBox);
			slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
				values.put(name, (Double) newVal);
				valueField.setText(String.format("%.2f", newVal));
				if (name == "Size") {
					String side = Integer.toString(((int)(slider.getValue()))) ;
					valueField.setText(side+"×"+side);

					
					slideRatio.sideLength = ((int)(slider.getValue()));
				    
				        changeSize = true; 
				}
				if (name == "Speed") {
				changeSpeed = true;
				String speedText = Integer.toString(((int)(slider.getValue()))) ;
				valueField.setText(speedText);

				
				slideSpeed.speed = ((int)(slider.getValue()));
				}
				
				
				
				
			});
		}

		public void setVal(double val) {
			this.slider.setValue(val);
		}

		public double getMax() {
			return this.slider.getMax();
		}

		public double getMin() {
			return this.slider.getMin();
		}
	}

	


	
}
