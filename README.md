# cellsociety

CompSci 308 Cell Society Project
Ben Welton (bw144), Heyao Yang (hy133), Nathan Lewis (nbl7)

Date Started: 17th September, 2017
Date Finished: 1st October, 2017
Estimated combined time spent: 110 hours

### Each Person's Role
As a smaller team of 3, at different points in development we were all able to contribute to different parts of the project, however most of the work completed was done within our specific role.
Ben handled the XML aspect of the program, designing the files, the handler and many other additional tasks it involved, such as linking it to the Simulation. He also played a big role in implementing the complex algorithms behind the different Simulations we included in our program and other general back-end tasks that were essential to his work.
Heyao formed almost the entirety of the front-end of this project, initializing the layout and handling all other interactive parts displayed on the screen, from the dynamic grid itself to the graph displaying population. At times Heyao also ventured into the back-end of the program and made alterations where necessary to his work.
Nathan's role consisted mostly of back-end work, creating classes associated with the Grid and Cells which would be used by the simulations and also contributing to some algorithms used by the simulations also. He also took on the main of integrating the front end with the back end, debugging where necessary and working with other members of the group, everyone also had some experience doing this at different points as well.

### Resources Used
* Many specific questions answered on StackOverflow
* www.java2s.com/Tutorials/
* https://www.gamedev.net/blogs/entry/2249737-another-cellular-automaton-video/
* http://cs.gmu.edu/~eclab/projects/mason/publications/alife04ant.pdf
* http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/
* http://nifty.stanford.edu/2011/scott-wator-world/
* http://nifty.stanford.edu/2007/shiflet-fire/
* http://web.stanford.edu/~cdebs/GameOfLife/

### Information about using the program
We hope we have produced quite a user-friendly interface for using our program. We have a drop down menu where you can choose your simulation type and radio buttons beneath where you can pick which grid type you want to use. Having initialized this information, pressing 'Go' will launch the simulation on the screen. There are sliders to the right of the displayed grid which have multiple different functions. The size and speed of the program can be changed at any time by using those sliders and depending on which simulation you choose, the appropriate sliders will appear to the right which allow you to take control of specific variables in the simulation e.g. the probability of fire spreading in the fire simulation. 
	Using the controls in the bottom pane of the program you can play the program at the specified speed, pause it after pressing play, step through the simulation once at a time by pressing step or reset the simulation entirely. If you want control of the simulation, at any time you can click on a cell to change the state/color of it; continue to click to cycle through all the possible states for that simulation. You can also change the XML files that exist in our data resources to change the specific beginning layout and save the current layout of the grid to an XML file - this layout can then be retrieved in the drop down menu by clicking 'Saved State' and the simulation will continue.
	
### Additional Features
We added a fair few additional features:

* We introduced 3 different edge types for the simulation. You can choose between normal (finite edges), toroidal where the neighbours of the cell wrap around to the other side of the grid and infinite where when a cell outside the grid becomes 'active' the grid will expand to accommodate that and the simulation will continue.
* We also implemented two additional simulations on top of the required ones, being Rock, Paper, Scissors Game and Foraging Ants.
* We allow the user to save the current state of the simulation into an XML file and continue this simulation using the drop down menu.
* We introduced error handling for the XML files (described below in detail).
* We also allow the starting position of the cells to be loaded in from an XML file or randomly generated using user inputted desired proportions of the cells.
* We also gave the user a lot of power to customize the simulations, being able to change colors or whether or not it is outlined within the XML and use sliders to change the speed of the simulation, the size of the grid and change specific variables for certain simulations.
* We added a graph to the right of the simulation showing real-time statistics about the population of the cells on the grid.
* The users can interact with the cells and change their states/color by clicking on a specific cell.

### Known Bugs & Errors
There are small bugs within our program, mainly that when resizing our grid - or it resizing itself with the infinite edge type - the grid tends to change size slightly. This is to do with our calculations in grid size and cell size being rounded and the issues we encountered when attempting to use doubles for these calculations. In addition to this the program also slows down and sometimes crashes if the simulation is too large - mainly when using infinite edge type. The last known error is that the foraging ants simulation, under certain circumstances, does not always satisfy the requirements, however in most scenarios works fine.

### Files
The file Main.java is used to start the program.
The majority of testing our files was done using the front-end visualization with the classes within the GUI package.
The XML file specifics are described below.

####XML Error Handling
Currently, the program is able to handle the following types of user errors in defining the xml file:

* Parameters
	1. If invalid parameter names are given, they will be ignored
	2. If required parameter names are absent, default values will be set
	3. If parameters are named but missing data, the default values will be set for those parameters
	
* Cells
	1. If no cell colors are specified, then a color is generated randomly for each cell
	2. If no cell proportions are specified, they are automatically set to 0, allowing either for specific location placement or for the user to click and select the cells later.
	
* General Structure
	1. If duplicated segments are provided, only the first block is considered
	2. If layout file cannot be turned to a document element, will load working default file instead

####XML Permutations
* Grids: `<Grid></Grid>`
	* Grid lines--`<Visible>true|false</Visible>` 
	* Grid size--`<Size>Integer</Size>`
	* Grid type--`<Type>Normal|Torodial|Infinite</Type>`

* Cells: `<Cells><Cell></Cell></Cells>`
	* Specific layout: `<Layout><Row>stringofints</Row><Layout>`
	* Probability distribution: `<Cell proportion="0-100"></Cell>`
	* Color: `<Cell color="#hexcode"></Cell>`