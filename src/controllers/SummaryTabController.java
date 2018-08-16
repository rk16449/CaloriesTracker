package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import javafx.scene.chart.XYChart;
import model.Day;

public class SummaryTabController implements Initializable {

	@FXML
	private PieChart dailyMacros;

	@FXML
	private BarChart<String, Number> dailyProgress;
	@FXML
	private CategoryAxis categoryAxisDate;
	@FXML
	private NumberAxis numberAxisCalories;
	
	private ArrayList<XYChart.Series> charts = new ArrayList<XYChart.Series>();

	public void initialize(URL arg0, ResourceBundle arg1) {

		// Setup pie chart
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Protein", 40),
				new PieChart.Data("Fats", 20), new PieChart.Data("Carbohydrates", 40));

		dailyMacros.setData(pieChartData);
		dailyMacros.setTitle("Daily Macros");

		// Setup bar chart based of total calories per day
		updateBarChart();
	}

	public void updateBarChart() {
		
		// Sort the days in order
		Collections.sort(DietTabController.days);
		
		
		// First clear the barchart
		charts.clear();
		dailyProgress.getData().clear();
		
		for (int i = 0; i < DietTabController.days.size(); i++) {

			System.out.println("Checking days.......................");
			
			Day day = DietTabController.days.get(i);
			

			// Create a new chart
			XYChart.Series<String, Number> series1 = new XYChart.Series<>();
			series1.setName(day.getDate().toString());
			series1.getData().add(new XYChart.Data<>(day.getDate().toString(), day.getTotalCalories()));

			charts.add(series1);
			
			// Setting the data to bar chart
			dailyProgress.getData().add(series1);
		}
	}
}