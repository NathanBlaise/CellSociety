package cellsociety_team02.gui;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


	
public class SliderBar extends VBox {
		protected String name;
		protected Label nameLabel;
		private Slider slider;
		protected TextField valueField;
		protected int sideLength;
		protected int speed;
		protected HBox lowerBox = new HBox();
		// constructor
		public SliderBar(String n, Double x, Double y, Double z) {
			name = n;
			slider = new Slider();
			slider.setValue(x);
			slider.setMin(y);
			slider.setMax(z);
			slider.setShowTickMarks(true);
			slider.setShowTickLabels(true);
			
			slider.setMajorTickUnit((slider.getMax() - slider.getMin()));
			nameLabel = new Label(name + ":");
			nameLabel.setFont(Font.getDefault());
			valueField = new TextField(String.format("%.2f", slider.getValue()));
			//Initialize the lowerBox
	
			lowerBox.getChildren().addAll(slider, valueField);
			lowerBox.setSpacing(8);
			this.getChildren().add(nameLabel);
			this.getChildren().add(lowerBox);
			
			
		}
		
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
						
						valueField.setText(String.format("%.2f", this.slider.getValue()));
					}
				} catch (NumberFormatException d) {
					valueField.setText(String.format("%.2f", this.slider.getValue()));
				}
			});
			
			
			
			
		}
		
		
		public void setVal(double val) {
			this.slider.setValue(val);
		}

		public double getMax() {
			return this.slider.getMax();
		}

		public double getMin() {
			return this.slider.getMin();
		}

		public void getValueFromTextField(GUI gui) {
			
			slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
				gui.values.put(name, (Double) newVal);
				valueField.setText(String.format("%.2f", newVal));
				
				
			});
		}
			
		


		public Slider getSlider() {
			return slider;
		}

		public void setSlider(Slider slider) {
			this.slider = slider;
		}
}
			/*if (name == "Size") {
				String side = Integer.toString(((int)(slider.getValue()))) ;
				valueField = new TextField(side+"×"+side);
			}
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
						
						//values.put(name, val);
		
					} else {
						
						valueField.setText(String.format("%.2f", this.slider.getValue()));
					}
				} catch (NumberFormatException d) {
					valueField.setText(String.format("%.2f", this.slider.getValue()));
				}
			});
			
			
			HBox lowerBox = new HBox();
			lowerBox.getChildren().addAll(slider, valueField);
			lowerBox.setSpacing(8);
			
			//lowerBox.setStyle("-fx-background-color: #336699;");
			this.getChildren().addAll(nameLabel, lowerBox);
			slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
				values.put(name, (Double) newVal);
				valueField.setText(String.format("%.2f", newVal));
				if (name == "Size") {
					String side = Integer.toString(((int)(slider.getValue()))) ;
					valueField.setText(side+"×"+side);

					
					slideRatio.sideLength = ((int)(slider.getValue()));
				    
				        changeSize = true; 
				}
				if (name == "Speed") {
				changeSpeed = true;
				String speedText = Integer.toString(((int)(slider.getValue()))) ;
				valueField.setText(speedText);

				
				slideSpeed.speed = ((int)(slider.getValue()));
				}
				
				
				
				
			});
		}

		public void setVal(double val) {
			this.slider.setValue(val);
		}

		public double getMax() {
			return this.slider.getMax();
		}

		public double getMin() {
			return this.slider.getMin();
		}
	}*/



