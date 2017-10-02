package cellsociety_team02.gui;

import java.util.ArrayList;

import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class lineChart extends LineChart {
	
	private NumberAxis myX = new NumberAxis();
    private NumberAxis myY = new NumberAxis();
	protected ArrayList<XYChart.Series> seriesList = new ArrayList <XYChart.Series>();
    
	public lineChart(NumberAxis xAxis, NumberAxis yAxis) {
		super(xAxis, yAxis);
		myX = xAxis;
		myY = yAxis;
		myX.setLabel("Time/Round");
		myY.setLabel("Percentage");
		this.setCreateSymbols(false);
		
	}
	
	public void updateLineChart(int round, double prop, XYChart.Series line) {

		line.getData().add(new XYChart.Data(round, prop));
	}
	
}
