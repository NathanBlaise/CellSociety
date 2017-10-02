package cellsociety_team02.gui;

import java.util.ArrayList;

import cellsociety_team02.simulations.Simulation;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;

public class SimVariableSliderBar extends SliderBar {
	private int myIndex;
	private Simulation mySim;

	public SimVariableSliderBar(String n, Double x, Double y, Double z, int index, Simulation simulation) {
		super(n, x, y, z);
		setMyIndex(index);
		//myVarArr = simulation.getVariables();
		//this.name = myVarArr[myIndex];
		mySim = simulation;
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public void setUpValueField(GUI gui) {
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
					
					gui.values.put(name, val);
	
				} else {
					valueField.setText(String.format("%.2f", this.getSlider().getValue()));
				}
			} catch (NumberFormatException d) {
				valueField.setText(String.format("%.2f", this.getSlider().getValue()));
			}
		});
		
		
		
		
	}
	
	
	
	@Override
	public void getValueFromTextField(GUI gui) {
		
		getSlider().valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			gui.values.put(name, (Double) newVal);
			valueField.setText(String.format("%.2f", newVal));
			
			this.mySim.changeVariables(myIndex,(Double)newVal);
			
			
		});
	}

	
	
	public int getMyIndex() {
		return myIndex;
	}

	public void setMyIndex(int myIndex) {
		this.myIndex = myIndex;
	}


	
		
	


	
	
}
