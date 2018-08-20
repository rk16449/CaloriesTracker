package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import model.Day;
import model.Food;

public class SummaryTabController implements Initializable {
	@FXML
	private Label labelCalories;
	@FXML
	private ProgressBar progressBarCalories;

	@FXML
	private PieChart dailyMacros;

	@FXML
	private BarChart<String, Number> dailyProgress;
	@FXML
	private CategoryAxis categoryAxisDate;
	@FXML
	private NumberAxis numberAxisCalories;
	
	private ArrayList<XYChart.Series> charts = new ArrayList<XYChart.Series>();
	
	// Object holding values of doubles
	private Double calories = (double) 0, protein = (double) 0, fats = (double) 0, carbs = (double) 0;
	private double totalCalories = (double) 3200;
	
	
	// Pie chart data
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	private ArrayList<PieChart.Data> addedSlices = new ArrayList<PieChart.Data>();

	public void initialize(URL arg0, ResourceBundle arg1) {

		// Setup pie chart
		//ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Protein", 40),
		//		new PieChart.Data("Fats", 20), new PieChart.Data("Carbohydrates", 40));

		//dailyMacros.setData(pieChartData);
		//dailyMacros.setTitle("Daily Macros");
		setupPieChart();
		
		
		dailyProgress.setBarGap(-30);
		dailyProgress.setLegendVisible(false);

		// Setup bar chart based of total calories per day
		updateProgressBar();
		updateBarChart();
		updatePieChart();
	}
	
	private void updateProgressBar() {
		// Finds out the current goal of calories is
		totalCalories = GoalsTabController.GoalCalories;
		// Changes the max values to the calorie goal
		
		// Adjusts the current total food calories to the value of the progress bar
		progressBarCalories.setProgress(calories / totalCalories);
	}
	
	private void setupPieChart() {
		// Setup pie chart
		PieChart.Data sliceProteins = new PieChart.Data("Protein", protein);
		PieChart.Data sliceFats = new PieChart.Data("Fats", fats);
		PieChart.Data sliceCarbs = new PieChart.Data("Carbohydrates", carbs);
		addedSlices.add(sliceProteins);
		addedSlices.add(sliceFats);
		addedSlices.add(sliceCarbs);
		pieChartData.add(sliceProteins);
		pieChartData.add(sliceFats);
		pieChartData.add(sliceCarbs);
		dailyMacros.setData(pieChartData);
		dailyMacros.setTitle("Daily Macros");
	}
	
	public void update() {
		updateTotalValues();
		updatePieChart();
		updateBarChart();
		updateProgressBar();
	}
	
	private void updateTotalValues() {
		protein = (double) 0;
		carbs = (double) 0;
		fats = (double) 0;

		// Add up the total from the foods on the table
		for (int i = 0; i < DietTabController.getDay(LocalDate.now()).getFoods().size(); i++) {
			Food f = DietTabController.getDay(LocalDate.now()).getFoods().get(i);
			protein += f.getProteins();
			carbs += f.getCarbohydrates();
			fats += f.getFats();
		}

		// Calculate calories
		calories = (protein * 4) + (carbs * 4) + (fats * 9);
	}

	private void updateGUIPieChart() {
		// now update the slices manually (good enough for such small amount of slices)
		addedSlices.get(0).setPieValue(protein);
		addedSlices.get(1).setPieValue(fats);
		addedSlices.get(2).setPieValue(carbs);
	}
	
	private void updatePieChart() {
		// Update the pie chart with the current day values
		updateTotalValues();
		updateGUIPieChart();
	}

	private void updateBarChart() {
		
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