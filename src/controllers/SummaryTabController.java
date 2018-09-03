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
import javafx.scene.control.TextField;
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
	@FXML
	TextField tfCurrentGoal;

	// Holds bar chart data points
	@SuppressWarnings("rawtypes")
	private ArrayList<XYChart.Series> charts = new ArrayList<XYChart.Series>();

	// value for max total calories needed on progress bar (dynamic)
	private double totalCalories = 3200;

	/**
	 * First method this class runs, sets up the controller
	 */
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
	
	private void updateCurrentGoalTF() {
		tfCurrentGoal.setText(person.getCurrentGoal().getName());
	}

	/**
	 * Sets up the bar chart values, called once in initialize
	 */
	private void setupBarChart() {
		dailyProgress.setBarGap(-30);
		dailyProgress.setLegendVisible(false);
	}

	/**
	 * Temporarily creates 365 'empty' day objects
	 */
	private void setupDays() {
		// Create 365 days if they don't already exist
		LocalDate start = LocalDate.parse("2018-01-01"), end = LocalDate.parse("2018-12-31");
		LocalDate next = start.minusDays(1);
		while ((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
			System.out.println(next);
			MainProgramController.days.add(new Day(next));
		}
	}

	/**
	 * Updates the progress bar percentage value and the TextField below it with
	 * current and max calories of Person
	 */
	private void updateProgressBar() {
		// Finds out the current goal of calories is
		totalCalories = person.getGoalCalories();

		// Changes the max values to the calorie goal
		double percentage = calories / totalCalories;

		// Adjusts the current total food calories to the value of the progress bar
		progressBarCalories.setProgress(percentage);

		// Text form of values
		labelCalories.setText("Calories remaining: " + Double.toString(Helper.round(calories, 2)) + " / "
				+ Double.toString(Helper.round(totalCalories, 2)));
	}

	/**
	 * Is called whenever switching to this tab, recalculates pie chart/bar
	 * chart/progress bar data
	 */
	public void update() {
		updatePieChart(LocalDate.now());
		updateGUIPieChart();
		updateBarChart();
		updateProgressBar();
		updateCurrentGoalTF();
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

		// Loop through every day
		for (int i = 0; i < MainProgramController.days.size(); i++) {

			// Reference to Day
			Day day = MainProgramController.days.get(i);

			// Convert LocalDate to Date so we can check with between method
			Date date = Date.from(day.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant().plusSeconds(1));

			// Check if this day is between certain values of the week only
			if (between(date, getFirstDayOfWeek(), getLastDayOfWeek())) {
				// Create a new chart
				XYChart.Series<String, Number> series1 = new XYChart.Series<>();

				// Set the name of the bar
				series1.setName(day.getDate().toString());

				// Convert the LocalDate format
				DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM");
				series1.getData().add(new XYChart.Data<>(day.getDate().format(sdf), day.getTotalFoodCalories()));

				// Save a reference to ArrayList
				charts.add(series1);

				// Setting the data to bar chart
				dailyProgress.getData().add(series1);
			}
		}
	}

	/**
	 * Checks if a date is between two dates
	 * 
	 * @param date
	 *            the date we want to check
	 * @param dateStart
	 *            start date
	 * @param dateEnd
	 *            end date
	 * @return true if the date specified is between the start and end dates
	 */
	public static boolean between(Date date, Date dateStart, Date dateEnd) {

		boolean valid = false;

		if (date != null && dateStart != null && dateEnd != null) {
			if (date.after(dateStart) && date.before(dateEnd)) {
				valid = true;
			} else if (date.equals(dateStart) || date.equals(dateEnd)) {
				valid = true;
			}
		}

		return valid;
	}

	/**
	 * Creates a Date object with time 00:00:00 of the 'Monday' of the current week
	 * 
	 * @return Monday Date object
	 */
	public static Date getFirstDayOfWeek() {

		Calendar c = Calendar.getInstance(new Locale("en", "UK"));
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		return c.getTime();
	}

	/**
	 * Creates a Date object of the 'Sunday' of the current week with 23:59:59 time
	 * 
	 * @return Sunday Date object
	 */
	public Date getLastDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_YEAR);
		while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			cal.set(Calendar.DAY_OF_YEAR, ++day);
		}
		
		// Set time to 23:59:59
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		return cal.getTime();
	}
}