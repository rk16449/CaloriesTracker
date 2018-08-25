package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import controllers.food.BaseFoodController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.Day;
import model.Helper;
import model.Person;

public class SummaryTabController extends BaseFoodController implements Initializable {
	@FXML
	private Label labelCalories;
	@FXML
	private ProgressBar progressBarCalories;
	@FXML
	private BarChart<String, Number> dailyProgress;
	@FXML
	private CategoryAxis categoryAxisDate;
	@FXML
	private NumberAxis numberAxisCalories;
	
	private ArrayList<XYChart.Series> charts = new ArrayList<XYChart.Series>();
	
	// value for max total calories needed on progress bar
	private double totalCalories = 3200;

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		person = Person.getInstance();

		// Setup pie chart
		setupPieChart();
		updateGUIPieChart();
		
		// Setup bar chart
		dailyProgress.setBarGap(-30);
		dailyProgress.setLegendVisible(false);

		// Setup bar chart based of total calories per day
		updateProgressBar();
		updateBarChart();
		updatePieChart();
	}
	
	private void updateProgressBar() {
		// Finds out the current goal of calories is
		totalCalories = person.getGoalCalories();
		
		// Changes the max values to the calorie goal
		double percentage = calories / totalCalories;
		
		// Adjusts the current total food calories to the value of the progress bar
		progressBarCalories.setProgress(percentage);
		
		// Text form of values 
		labelCalories.setText("Calories remaining: " + Double.toString(Helper.round(calories, 2)) + " / " + Double.toString(Helper.round(totalCalories, 2)));
	}
	
	public void update() {
		updateTotalValues();
		updatePieChart();
		updateBarChart();
		updateProgressBar();
	}

	private void updateBarChart() {
		// Sort the days in order
		Collections.sort(MainProgramController.days);

		// First clear the barchart
		charts.clear();
		dailyProgress.getData().clear();
		
		for (int i = 0; i < MainProgramController.days.size(); i++) {
			// Get this day
			Day day = MainProgramController.days.get(i);

			// Create a new chart
			XYChart.Series<String, Number> series1 = new XYChart.Series<>();
			series1.setName(day.getDate().toString());
			series1.getData().add(new XYChart.Data<>(day.getDate().toString(), day.getTotalFoodCalories()));

			charts.add(series1);
			
			// Setting the data to bar chart
			dailyProgress.getData().add(series1);
		}
	}
}