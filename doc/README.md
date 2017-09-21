# cellsociety 

Put any written documents related to your project here, including lab discussions.

# Lab Discussion 
### Tony Yang, Nathan Lewis, Ben Welton
####Inheritance Review
We found that all three of us ended up having too much code in our main game class because we did not implement any inheritance in our code. Implementing a super class for objects that had different types or functions, such as the blocks and power-ups, would make the code much more readable and decrease the amount of dependancies if another developer wanted to add a block on type. We went through and decided what a block class in particular would look like to take advantage of the functionality that we were talking about. 

```
public class Block{
	public Block(double x, double y, ImageView image){
		//draw block onto level
	}

	public void changePosition(double newX, double newY){
		//update behavior
	}

	public boolean checkForCollision(Object object){
		//use minx, miny, width here
	}

	public void blockIsHit(){
		//updates hit count
	}
	
	public void activateBehavior(){
		//activate any special functionality
	}
}
```
#### High Level Design
1. We think there are two ways that we might consider how a cell will decide what rules to use. Either we will have a specific type of cell that acts in a certain way and modifies adjacent cells to become various new types of cell, or we will have several rule classes that change the behavior of a larger cell class. We are leaning towards the latter option, as this would create an opportunity to only add one additional rule set for each simulation, rather than trying to add multiple new cell types.
2. We could create a grid to store all cells, meaning each cell would have an integer x,y co-ordinate. Then within our Cell's class we could have a method to getCell from a specified co-ordinate e.g. getNorthCell would retrieve information from the cell with the co-ordinates (x,y+1) of the current cell. To update the cell without affecting its neighbors we plan to retrieve the necessary information from the neighbors, then update the cell using that information through several set___() methods.
3. We are planning to have the grid function as the updating object. It will handle all of the individual cells and drawing and updating their changed states. As a result, the updating cells should not have any knowledge of the grid; the UI, however, will probably need to interact with the grid so that it can call state updates, change speed, record data, etc depending on what we decide to do with our UI.
4. We will need to know the:
* Type of Simulation
* Color of the Cells
* Time for Simulation Update
5. We are thinking of either having the GUI handled by the grid, which can update the states of the cells, or by having a larger GUI class that could handle not only the cells but also any changes that we might add in with extending our user interface to have other functionality.

#### CRC Cards

#### Use Cases




