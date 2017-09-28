
## Refactoring Discussion
#### Duplication
* We had some duplication between our Fire Cell and PredatorPrey Cell subclasses, as they both employed the same overriding getNeighbours() method. To solve this we create a new method getAdjacentNeighbour() in the parent class Cell(), which in turn made the class FireCell unnecessary as that was the only class contained within it. This then removed certain parts of code elsewhere in our program such as the Grid class and ScreenDisplay class.
* There was also some minor overlap of code in the PredatorPreySimulation, in methods such as updateSharkCell() and updateFishCell().

#### General Refactoring
* Instead of using boolean values for the handler events of the buttons in the GUI class, we've decided to remove the boolean values we used in the step method to check whether they were clicked. Instead we pass the event handler itself to the step method.
* In the Grid class there was some unnecessary code when initializing the 2D array used for the grid.


#### GUI and ScreenDisplay Classes
* The majority of our refactoring revolved around these two classes. For all of our buttons we'd traditionally had boolean values that changed state upon the mouse click event handlers.  We changed this to utilize the event handlers within the ScreenDisplay which also removed the function we had from the step method - reducing it in size and making it more efficient.
* A lot of minor changes were made surrounding the availability of variables, making as many variables private as possible and the few that were public to protected.
* The code that handled our sliders was heavily refactored to remove unnecessary code and improve structure and calling of events. We also plan to move the class SliderBar into a separate class and create subclasses for each type of slider as opposed to using if statements.