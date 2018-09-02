package controllers;

/* Import java, javafx */
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
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
	Label labelCalories;
	@FXML
	ProgressBar progressBarCalories;
	@FXML
	BarChart<String, Number> dailyProgress;
	@FXML
	CategoryAxis categoryAxisDate;
	@FXML
	NumberAxis numberAxisCalories;
	
	// Holds bar chart data points
	@SuppressWarnings("rawtypes")
	private ArrayList<XYChart.Series> charts = new ArrayList<XYChart.Series>();
	
	// value for max total calories needed on progress bar (dynamic)
	private double totalCalories = 3200;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Creates 365 day objects
		setupDays();
		
		// Reference to the Person
		person = Person.getInstance();

		// Setup pie chart
		setupPieChart();
		
		// Setup bar chart
		setupBarChart();

		// Setup bar chart based of total calories per day
		update();
	}
	
	private void setupBarChart() {
		dailyProgress.setBarGap(-30);
		dailyProgress.setLegendVisible(false);
	}
	
	private void setupDays() {
		// Create 365 days if they don't already exist
		if(MainProgramController.days.size() == 0) {
			LocalDate start = LocalDate.parse("2018-01-01"),
			          end   = LocalDate.parse("2018-12-31");
			LocalDate next = start.minusDays(1);
			while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
			    System.out.println(next);
			    MainProgramController.days.add(new Day(next));
			}
		}
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
		updatePieChart(LocalDate.now());
		updateGUIPieChart();
		updateBarChart();
		updateProgressBar();
	}

	/**
	 * Loads only the 7 most recent data (up to the current week)
	 */
	private void updateBarChart() {
		// Sort the days in order
		Collections.sort(MainProgramController.days);

		// First clear the BarChart
		charts.clear();
		dailyProgress.getData().clear();
		
		System.out.println("------------------Updating Bar Chart-----------");
		
		
		for (int i = 0; i < MainProgramController.days.size(); i++) {
			
			
			// System.out.println("testing days");
			
			// Reference to Day
			Day day = MainProgramController.days.get(i);
			
			// Convert LocalDate to Date so we can check with between method
			Date date = Date.from(day.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			// Add 1 second to this date so that we can fit the interval
			date.setSeconds(1);
			
			
			
			// Check if this day is between certain values of the week only
			if(between(date, getWeekStartDate(), getWeekEndDate())) {
				
				
				System.out.println("Checking date: " + date.toString());
				System.out.println("Week Start Date: " + getWeekStartDate().toString());
				System.out.println("Week end Date: " + getWeekEndDate().toString());
				
				
				// Create a new chart
				XYChart.Series<String, Number> series1 = new XYChart.Series<>();
				
				
				series1.setName(day.getDate().toString());
				System.out.println("Setting name for: " + series1.getName());
				
				// Convert the format
				DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM");
				
				series1.getData().add(new XYChart.Data<>(day.getDate().format(sdf), day.getTotalFoodCalories()));

				charts.add(series1);
				
				
				
				// Setting the data to bar chart
				dailyProgress.getData().add(series1);
				
				System.out.println("Amount of barchart data: " + dailyProgress.getData().size());
			}
		}
	}
	
	public static boolean between(Date date, Date dateStart, Date dateEnd) {
	    if (date != null && dateStart != null && dateEnd != null) {
	        if (date.after(dateStart) && date.before(dateEnd)) {
	            return true;
	        }
	        else if(date.equals(dateStart) || date.equals(dateEnd)) {
	        	return true;
	        }
	        else {
	            return false;
	        }
	    }
	    return false;
	}
	
	public static Date getWeekStartDate() {
		Calendar c = Calendar.getInstance(new Locale("en","UK"));
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		// Set time to 00:00:00
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
		
	    return c.getTime();
	}

	public static Date getWeekEndDate() {
	    Calendar calendar = Calendar.getInstance();
	    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
	        calendar.add(Calendar.DATE, 1);
	    }
	    calendar.add(Calendar.DATE, -1);
	    
	    // Set time to 23:59:59
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    
	    return calendar.getTime();
	}
}