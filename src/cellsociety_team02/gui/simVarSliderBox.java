package cellsociety_team02.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellsociety_team02.simulations.Simulation;
import javafx.scene.layout.VBox;

public class simVarSliderBox extends VBox{
	
	private Simulation mySim;
	private List<String>simArrName = new ArrayList<String>(Arrays.asList("1", "2", "3"));
	private List<Double>simArrMax = new ArrayList<Double>(Arrays.asList(1.0,2.0,3.0));
	private	List<Double>simArrCurrent = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0));
	
	
	public simVarSliderBox(Simulation sim, GUI gui) {
		mySim = sim;
		if (sim!= null) {
		simArrName = sim.variableList();
	 	simArrMax = sim.variableMaximums() ;
	 	simArrCurrent = sim.variableValues();
		}
		
		for (int i = 0; i < simArrName.size();i++) {
			SimVariableSliderBar simVarBar = new SimVariableSliderBar(simArrName.get(i),simArrCurrent.get(i), 0.0, simArrMax.get(i), i, sim);
			this.getChildren().add(simVarBar);
			simVarBar.getValueFromTextField(gui);
			simVarBar.setUpValueField(gui);
			
		}
	}
}
	
	