package cellsociety_team02.gui;

import java.util.HashMap;
import javafx.scene.control.ComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
	
	//Initializes the GridPane we use to display our grid
	protected GridPane pane;
	//Default font used
	protected Font font = Font.getDefault();
	//Pane Box and Slider Box VBoxes intialized
	protected VBox paneBox;
	protected VBox SliderBox;
	//Slider Title
	protected Text SlidTittle = new Text();
	
	// Initialize buttons and create HBox for them
	protected Button resetButton;
	protected Button pauseButton;
	protected Button stepButton;
	protected Button goButton;
	protected Button saveButton;
	protected HBox buttons;
	private final int BUTTON_HEIGHT = 476;
	
	//Initialize radiobuttons, create HBox for them and instantiate toggle group
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
	
	//Specific slider bar variables instantiated
	protected SpeedSliderBar slideSpeed;
	protected SliderBar slideRatio;
	protected SizeSliderBar slideSize;
	
	//help to change the size of gridPane
	protected boolean changeSpeed = false;
	
	//Instance of class used to hold the specific variable sliders
	protected simVarSliderBox varSliderBox = new simVarSliderBox(null,this);
	
	public GUI() {
		init();
	}
	
	protected void init() {
		//List of drop down menu items
		ObservableList<String> list = FXCollections.observableArrayList("Fire","Segregation","Game of Life","Predator-Prey", "RPS", "Foraging", "Saved State");
		paneBox = new VBox(30);
		
		//Add buttons
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
		
		//Also add buttons to toggle group, allowing one selection at a time
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
		//Pane Box set and adding children to it
		paneBox.setPadding(new Insets(30,30,30,50));
		paneBox.getChildren().add(LoaderBox);
		paneBox.getChildren().add(radioButtons);
		paneBox.setStyle("-fx-background-color: #336699;");
		paneBox.setLayoutX(0);
		paneBox.setLayoutY(BUTTON_HEIGHT);
		paneBox.setPrefWidth(1000);
		
		//Setting buttons to the right of Pane Box
		buttons.setPadding(new Insets(30,30,30,50));
		buttons.setLayoutX(300);
		buttons.setLayoutY(BUTTON_HEIGHT);
		
		// add SlideBars
		SliderBox = new VBox();
		slideSpeed = new SpeedSliderBar("Speed", 0.0,1.0,20.0 );
		slideSize = new SizeSliderBar("Size",0.0,5.0,50.0);
		slideSpeed.getValueFromTextField(this);
		slideSize.getValueFromTextField(this);
		slideSpeed.setUpValueField(this);
		slideSize.setUpValueField(this);
		
		
		//Modify Slider Specifics
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
}
