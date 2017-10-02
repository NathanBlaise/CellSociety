package cellsociety_team02.gui;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class SizeSliderBar extends SliderBar{

	public SizeSliderBar(String n, Double x, Double y, Double z) {
		super(n, x, y, z);

	}
	
	@Override
	public void setUpValueField(GUI gui) {
		String side = Integer.toString(((int)(getSlider().getValue()))) ;
		valueField.setText(side+"×"+side);
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
			
			String side = Integer.toString(((int)(getSlider().getValue()))) ;
			valueField.setText(side+"×"+side);
			
			
			this.sideLength = ((int)(getSlider().getValue()));
			 
			//System.out.println(sideLength);
			
			gui.changeSize = true; 
		});
	}
}
