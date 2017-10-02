package cellsociety_team02.gui;

import java.util.HashMap;
import javafx.scene.control.ComboBox;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
/**
 * @author supertony
 *
 */
public class GUI {

	protected GridPane pane;
	protected Font font = Font.getDefault();
	protected VBox paneBox;
	protected VBox SliderBox;
	protected Text SlidTittle = new Text();
	
	// Create a HBox for Buttons
	protected Button resetButton;
	protected Button pauseButton;
	protected Button stepButton;
	protected Button goButton;
	protected Button saveButton;
	protected HBox buttons;
	
	protected RadioButton normalButton;
	protected RadioButton toroidalButton;
	protected RadioButton infiniteButton;
	protected HBox radioButtons;
	protected ToggleGroup radioGroup;

	//Create a line chart
	protected final NumberAxis xAxis = new NumberAxis();
    protected final NumberAxis yAxis = new NumberAxis();
    protected lineChart xyChart = new lineChart(xAxis,yAxis);
            
	
	// boolean values for each button
	protected boolean isBack = false;
	protected boolean changeSize = false;
	// create a combo box to select a certain type of simulation
	protected ComboBox<String> simulationLoader;
	protected String simToLoad;
	protected HashMap<String, Double> values = new HashMap<String, Double>();
	public SpeedSliderBar slideSpeed;
	public SliderBar slideRatio;
	public SizeSliderBar slideSize;
	//help to change the size of gridPane
	protected boolean changeSpeed = false;
	
	protected simVarSliderBox varSliderBox = new simVarSliderBox(null,this);
	
	public GUI() {
		init();
	}
	
	protected void init() {
		ObservableList<String> list = FXCollections.observableArrayList("Fire","Segregation","Game of Life","Predator-Prey", "RPS", "Foraging", "Saved State");
		paneBox = new VBox(30);
		
		goButton = new Button("GO");
		resetButton = new Button("RESET");
		stepButton = new Button("STEP");
		pauseButton = new Button("PLAY");
		saveButton = new Button("SAVE");
		
		buttons = new HBox(10);
		buttons.getChildren().add(saveButton);
		buttons.getChildren().add(resetButton);
		buttons.getChildren().add(pauseButton);
		buttons.getChildren().add(stepButton);
		
		// Add Radio Buttons
		normalButton = new RadioButton("Normal");
		normalButton.setSelected(true);
		toroidalButton = new RadioButton("Toroidal");
		infiniteButton = new RadioButton("Infinite");
		
		radioGroup = new ToggleGroup();
		radioButtons = new HBox(30);
		radioButtons.getChildren().add(normalButton);
		normalButton.setToggleGroup(radioGroup);
		radioButtons.getChildren().add(toroidalButton);
		toroidalButton.setToggleGroup(radioGroup);
		radioButtons.getChildren().add(infiniteButton);
		infiniteButton.setToggleGroup(radioGroup);
		

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
		
		paneBox.setPadding(new Insets(30,30,30,50));
		paneBox.getChildren().add(LoaderBox);
		paneBox.getChildren().add(radioButtons);
		paneBox.setStyle("-fx-background-color: #336699;");
		paneBox.setLayoutX(0);
		paneBox.setLayoutY(476);
		paneBox.setPrefWidth(1000);
		
		buttons.setPadding(new Insets(30,30,30,50));
		buttons.setLayoutX(300);
		buttons.setLayoutY(476);
		
		// add SlideBars
		SliderBox = new VBox();
		slideSpeed = new SpeedSliderBar("Speed", 0.0,1.0,20.0 );
		slideSize = new SizeSliderBar("Size",0.0,5.0,50.0);
		slideSpeed.getValueFromTextField(this);
		slideSize.getValueFromTextField(this);
		slideSpeed.setUpValueField(this);
		slideSize.setUpValueField(this);
		
		
		
		SlidTittle.setText("General Setting:");
		SlidTittle.setLayoutX(513);
		SlidTittle.setLayoutY(55);
		SlidTittle.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.REGULAR, 15));
		SlidTittle.setUnderline(true);  
		SliderBox.getChildren().add(slideSize);
		SliderBox.getChildren().add(slideSpeed);
		SliderBox.setSpacing(0);
		SliderBox.setLayoutY(85);
		SliderBox.setLayoutX(515);
		//SliderBox.setPrefWidth(250);
		SliderBox.setPrefHeight(200);
		
		
		//Initialize the line chart  
	  
       xyChart.setLayoutX(505);
       xyChart.setLayoutY(240);
       xyChart.setPrefWidth(450);
       xyChart.setPrefHeight(225);
       
      
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
