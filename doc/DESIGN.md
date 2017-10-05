# Cell Society Design
Names: 
Nathan Lewis (nbl7@duke.edu)
Ben Welton (bw144@duke.edu)
Heyao Yang (hy133@duke.edu)

# After-Project Update

### High-Level Overview
The high-level overview of our project has changed from the initial design we conceptualized. To begin with we had the idea of having sub-classes for our cells, after discussing our design however we realized these sub- classes would be redundant. This is because the state of the cell was stored by an integer 0,1 or 2 (or just 0 and 1) which is common for all cells and the majority of simulations had the same method for retrieving neighbours. This is design feature apparent in our setNextState function:
```java
public void setNextState(int state) {
		nextState = state;
		stateChanged = true;
	}
```
This code takes the input of an integer and sets the next state of the cell to equal that - the same process used by every cell in every simulation type. Another big overhaul in our higher-level design was that initially we used methods in our Cell class to retrieve its neighbours which would be called by the running simulation. We then realized that the location of cells never actually changes within the grid so the neighbours for each cell could simply be set within the Grid class when the grid is initialized and populated with the appropriate cells. The getAdjacentNeighbours method remained however as it was necessary for the Foraging Ants Simulation.

The Grid Class itself mainly contains many helper methods used in the setup of the grid and get methods. The grid we used to hold the cells is a 2D array which is subsequently used by the GUI to display the cells in a grid pane. The grid was designed to flexible enough so it could be created to any specified size and populate itself with cells of a specific proportion or even specific layout. As this only really occurs once in the program - when a new grid size or simulation is selected to run - the majority of the Grid's code and functionality lies within it's constructor.

The code to handle the rules for each different simulation is stored in the different subclasses of the Simulation class. The parent Simulation class contains all appropriate methods and variables to store essential information needed to run - mostly retrieved from the XML file. The simulation subclasses then contain all the specific variables and methods needed for the simulation to run.

The GUI of the program is mainly handled by a select few classes, GUI to set up most objects displayed on the screen and Screen Display to run the animation and other methods that execute on event handlers and in the step function. Many other classes were made in this area as a result of refactoring of the Slider Bar and Graph for instance.

The XML handling and XML building (required for saving the current state) make are held in two corresponding classes and contain all required methods and variables needed to retrieve necessary data from the XML file. These two classes are heavily used/depended on by the Simulation class(es).

### Tradeoffs
The pro to having a singular Cell class was that we lacked redundant subclasses and it made initialization of the grid and input of XML data much easier. It was a tradeoff however that instead of cells physically moving in the grid, we instead updated the state which made some algorithms for simulations harder to implement. We also had trouble when introducing the Foraging Ant simulation as it required an area of cells to be considered, which lead to extensions of the Class as opposed to being able to use many subclasses and more inheritance to solve the issue.

Having the grid contain methods for retrieving and setting neighbours of cells was a pro as it saved the repeated calling on cells to retrieve their neighbours through a time consuming algorithm, thus making our program more efficient, but also allowed the meant new grid types could be introduced as subclasses which could override these neighbour retrieval algorithms. It was only a con however when implementing the Foraging Ant Simulation which need access to both its immediate adjacent neighbours and omnidirectional neighbours simultaneously, which could not be down with our design, leading to the introduction of a method in the cell class.

### Adding New Simulations
Three steps are required to add a new simulation. First, the `GUI` needs to have a new element added to the drop down list so the new simulation can be selected. Second, the `ScreenDisplay` class needs to have the simulation referenced in the `drawNewGridType()` method as seen below:

```java
...
if (gui.simToLoad.equals("Fire")) sim = new FireSimulation();
		else if (gui.simToLoad.equals("Segregation")) sim = new SegregationSimulation();
		else if (gui.simToLoad.equals("Predator-Prey")) sim = new PredatorPreySimulation();
...
```
Finally, a new subclass needs to be added in the Simulation package that extends the Simulation superclass. This simulation must override the updateCell method with the specified algorithm and should override the primeCell method if initial states or cell occupants need to be added. A default file must also be specified here, as well as any default variables that are desired. In the absence of these default variables, the simulation will not be able to double check to ensure that the appropriate variables are being assigned in the XML file. An example from the fire simulation can be seen below:
```java
	private String[] vars = {"spreadChance"};
	private double[] vals = {0.5};
	private double[] maxs = {1.0};
	...
	public FireSimulation() {
		super();
		super.setDefaultVariables(layoutFile, vars, vals, maxs);
		super.changeInitConfig(layoutFile);
		spreadChanceIndex = variables.indexOf("spreadChance");
	}
```
This part is not ideal, but was largely implemented as a check to ensure that the user has the right values and to work with the variable sliders for the UI. 

Details about what can be included within the XML file are largely covered in the README, but the general format should look as below and must incorporate these elements to get maximum functionality:
```java
<Simulation>
	<Attributes>
		<Type></Type>
		<Title></Title>
		<Author></Author>
	</Attributes>
		
	<Variables>
		<Variable name="" value="" maximum=""></Variable>
		...
	</Variables>

	<Cells>
		<Cell color="" proportion="">
		</Cell>
...
<!-- Layout is optional -->
		<Layout>
			<Row>010101</Row>
			...
			<Row></Row>
		</Layout>
	</Cells>

	<Grid>
		<Size></Size>
	</Grid>

</Simulation> 
```


#Original Plan

### Introduction
The main aim of this project is to create a program that provides a visual simulation for multiple cell automata from which the user can choose from. The rules for each cell automata are already specified and we will implement these rules in numerous classes in order to update cells on a user-specified grid (dimensions and initial configuration). The calculations for each cell’s update status will be calculated in the back-end of the program and this information will be transformed to a GUI version which displays the grid and cells in different colours to represent state. This will be the most flexible part of the program, having initiated the rules of the simulation we can choose what control the users have over the program and how it looks.

### Overview
To implement the specific rules involved with the chosen cell automata we have decided to design our program to have classes for each type of cell e.g. FireCell with a parent class Cell and specific simulation classes to contain the specific rules e.g. SegregationSimulation with a parent class Simulation. The purpose of the cell class is to act an object, cell, which contains the specific information of each cell and methods to get cell specific information (e.g. its neighbors and current status) and allow itself to update itself based on the rules implemented in the appropriate simulation class. The simulation class for each cell automata will contain the specific rules needed to update the cells using methods which cycle through the appropriate cells and updating the cells status based on the outcome of the application of the rules.

We also chose to have one class which sets up and controls the grid. The grid will most likely be a 2D array of the object Cell and the class will contain necessary methods to update itself and retrieve necessary information based on the needs and outcomes of the simulation classes. Another necessary class is the GUI class which will handle setting up the appropriate JavaFX capabilities in order to offer the user options to choose a cell automata, input an XML file etc. (described below in UI). The GUI class will rely on the Grid class to display the visual representation of the grid and will also feed information to the simulation, cell and grid classes based on the User’s choices. Sub-classes for the GUI class may be implemented later on in development, however this will most likely occur due to refactoring of code.

![DESIGN 1](/images/Design Overview 1.jpg)
![DESIGN 2](/images/Design Overview 2.jpg)

### User Interface

Our design is somewhat similar and has two key components. The first component, which will occupy one half of the window, is going to be the updated simulation display. This is where the grid and cell elements are actually going to be drawn and maintained, but there will be no real user interaction here. The other half of the window will have the buttons and options that the user is actually going to interact with. This will include a drop down menu to use one of the pre-designed simulation types, a selection area to choose an XML file to load if the user does not want to use the default start parameters for the simulation, a speed task-bar to change the pace of the animation, play, pause, and stop buttons to interact with and initialize the animation, and a step button to allow the user to move through one iteration at a time.

A user should have to put in a very limited amount of information in order to interact with the simulation. Even if no actual XML file is specified, we will have a default configuration for each simulation that will run regardless. If a file is given, however, we will need to confirm that the file has both the correct simulation header to confirm that the parsing into block types will be correct and the correct file extension so that Java can parse through in the first place. With this in mind, there will also be a requirement that the user does choose a simulation type from the drop down menu. Beyond that, much of what is happening on screen is limited to simple button interactions, and the user would not be able to cause any additional errors.

![UI DESIGN](/images/UI Pic.jpg)

### Design Details

#### Cell Class

In the assignment cells are a crucial part to the running of the program. Each cell has its own identity and qualities based on the cell automata chosen however they also have common specific traits which can be included in the parent class. For example in our cell constructor we can include the location of the cell, x and y, on the grid. Other necessary methods include setting and updating the color of the cell (which can be decided by the subclass based on the cell automata chosen) and certain get methods such as retrieving the current location of the cell or returning the neighbors of the cell - if cell is an edge cell the ‘missing’ neighbors will be recognised and that information included in the returning array of cells for simulation to use.

#### Cell Sub-classes

Each cell automata will have different cell behavior and colors, so each subclass will extend the parent Cell class and include more specific methods. Depending on the simulation picked the methods may differ however most classes will include a get method used to return the current state of the cell (e.g. for Fire either empty, burning or tree) and the necessary methods needed to update itself in terms of state and color.

#### Simulation Class

The Simulation classes will reference the cell methods frequently and update the grid at intervals as well. The parent class won’t contain too much information as each cell automata has largely varying rules and methods, however all use the same grid and will need to import it, perhaps within the constructor.

#### Simulation Sub-classes
Each simulation sub-class will be quite different algorithmically based on which cell automata is chosen. We aim to use an efficient algorithm to cycle through the appropriate cells within the grid (discussed in design considerations) and reference other methods within the class that apply the specific rules e.g. if a middle or edge cell is being checked in Game of Life, the method from the Cell class, getNeighbors, can be used and checked to see the number of dead cells and the cell can be updated appropriately. It is important to note that when a cell is updated we are ‘marking’ it before updating it so as to not affect calculations for each cell (discussed further in design considerations). Each subclass will also take in the appropriate parameters need to run in its constructor e.g. probCatch in the Fire simulation.

#### Grid

he cell grid class is an abstract class to store a 2-D array of Cells (obj) to represent the configuration.It generalizes what each simulation should have in common and is designed to pick information from cell and simulation classes to store the information of cells’ configuration for each simulation. CellGrid interacts with the Rule class by making an instance of it. The cell grid will be in charge of calling the for the Rule class to calculate the next state of the cells and then change the state of the cells.It contains a double array of Cells[][] as its instance variables. 

#### GUI
In the GUI class we will instantiate the Stage for the game and include methods to set up the scene by including all buttons and drop-down menus described in the User Interface section. Each button will have a method within the class causing the appropriate action and the code for handling the parsing of the chosen XML file will be included within the class. The grid that is created after the user has chosen their desired settings will be visualized using this class using the JavaFX GridPane() and update alongside with the Grid class updating itself. This class will also choose what subclasses of cell and simulation are used in the main simulation.

### Design Consideration

There were a few questions we faced when deciding how to design our grid class.
*How do you deal with cells whose neighbors have already been updated?
We had a few ideas for how to approach this. Initially, we discussed the idea of having a copy of this information. That is, there would be a second grid object (or a second 2D array with that grid object) that would have a copy of each cell for other cells to compare to and then would only update the states of the first grid. We ultimately decided that we found this to be inefficient for the extra space it wasted and for the lack of clarity in what we would be doing. Instead, we decided that it would make more sense to have the cells store this information, as it pertained more to them thematically (a cell’s new state) than to the grid (the grid’s old resident). The downside of this is that you would need to loop through the grid twice, once to set all of the new states, and once to make the change in the display and then switch the new and old state variables. We can mitigate this by having a list of updated cells and only looping through those to change the ones that switched.

* How do you minimize the need to loop through all elements in the grid?
One thing that we discussed was the possibility that some cells may be empty, dead, burned, etc and may no longer need to be checked depending on the rule set. We are still unsure exactly how to solve this problem (if we need to solve it at all), though we may flag certain states as dead and let the grid loop over them.

* Should rules be encoded into the cells or the rules?
Our ultimate conclusion here was a bit of both. We initially considered the pros and cons of having rules that get parsed into outcomes and specific cell types that perform a certain function when a state is activated or a condition met (such as having a certain number of neighbors). We decided that what really needed to be done was give each cell a certain attribute corresponding to a state, such as the color red for an active fire tile, and then have a rule set that caused changes to those states based off the rules. What this means is that we still use subclasses for an inherited cell class, but those specialized cells are mostly used to define the colors to three or four states (active, inactive, destroyed) so that the rule superclass can generally make a switch between several basic states that we expect all cell types to have at least two of.

### Team Responsibilities

While we’re going to start by coming together collectively to work on the parent subclasses and the basis of the GUI, the current plan for separating the code for creating the basic foundation is to break the project into three parts: the GUI, designing the grid and cell classes to be able to update their states, and designing the rules class that can parse through the xml file and update the way by which those cells are updating. As of now, Tony is going to work on the GUI, Nathan is working on the grids and cells, and Ben will be working on the rules class and XML parsing. Tony will have a secondary responsibility of handling the integration of the file parsing with the interface, while Ben and Nathan will work on integrating the rules to the grid and cell classes.



