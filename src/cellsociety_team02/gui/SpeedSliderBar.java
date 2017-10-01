package cellsociety_team02.gui;

import javafx.beans.value.ObservableValue;

public class SpeedSliderBar extends SliderBar {

	
	
	public SpeedSliderBar(String n, Double x, Double y, Double z) {
		super(n, x, y, z);
		this.valueField.setText(Integer.toString(((int)(this.getSlider().getValue()))));
	}

	@Override 
	public void getValueFromTextField(GUI gui) {
		
			this.getSlider().valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
			gui.values.put(name, (Double) newVal);
			valueField.setText(String.format("%.2f", newVal));
			
			gui.changeSpeed = true;
			String speedText = Integer.toString(((int)(this.getSlider().getValue()))) ;
			valueField.setText(speedText);

			
			this.speed = ((int)(this.getSlider().getValue()));
			
			});
	}
	
}
