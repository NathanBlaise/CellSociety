### Part 1

*
* We are intending to strongly use inheritance within our simulation classes by having a subclass for each simulation and one parent class.
* The rules for each simulation will be closed as the are not needed to be accessed once selected, however the grid and cell class we plan to leave more open in order to access and change their information.
* We may face errors in the back-end when the XML file doesn't include sufficient information for the initial configuration. To combat this we could use default values for initial config.
* Currently I think our design is good as each main component has been seperated by class and for discrepencies between simulations we've utilised inheritance where we can. A good design in my opinion at the moment is efficiently structuring the program into particular components and allowing them to work in conjuction whilst remaining easily readable.


### Part 2

* Dependent on XML reading, to get starting configuration and other required information.
* No real leeway with the dependency on the XML file, all the information is required 
* Another dependency on the GUI input in order to instantiate Simulations and grid.
* The GUI also depends on my back-end class and this dependency can be minimized by mirroring the GUI grid to the back-end grid as much as possible.
* Can be minimized by limiting user inputs in the GUI class.
* XML class/handler dependency is hard to be minimized as it's essential information.
* Going over the cell super/sub classes it will be best to have as much information shared by the cells in the parent/super class e.g. implementing a system where state can be common amongst all cells.

### Part 3

* Updating cells on the edge of grid for segregation for example.
* Predator-Prey simulation with a shark in the corner.
* Segregation cells 50% same type, 50% empty.
* What to do if XML file is missing important information.
* Moving cells as opposed to updating their state.

* Excited to implement the rules for Predatory-Prey simulation and watching it work.
* Most worried about making cells move throughout the grid as opposed to updating state for certain simulations.

Pei Lin, Dianne, Madhapi, Nathan