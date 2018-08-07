package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;

public class SummaryTabController implements Initializable {
	
	@FXML
	private PieChart dailyMacros;
	
	
	@FXML
	private BarChart<Number, Date> dailyProgress;
	@FXML
	private CategoryAxis categoryAxisDate;
	@FXML
	private NumberAxis numberAxisCalories;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Setup pie chart
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Protein", 40),
                new PieChart.Data("Fats", 20),
                new PieChart.Data("Carbohydrates", 40)
                );
                
		dailyMacros.setData(pieChartData);
		dailyMacros.setTitle("Daily Macros");
		
		// Setup bar chart
		
	}
}