# cellsociety

CompSci 308 Cell Society Project

####XML Error Handling
Currently, the program is able to handle the following types of user errors in defining the xml file:
*Parameters
	1. If invalid parameter names are given, they will be ignored
	2. If required parameter names are absent, default values will be set
	3. If parameters are named but missing data, the default values will be set for those parameters
	
*Cells
	1. If no cell colors are specified, then a color is generated randomly for each cell
	2. If no cell proportions are specified, they are automatically set to 0, allowing either for specific location placement or for the user to click and select the cells later.
	
*General Structure
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