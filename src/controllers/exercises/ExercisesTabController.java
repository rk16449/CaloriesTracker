package controllers.exercises;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

import controllers.MainProgramController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Day;
import model.Exercise;
import model.ExerciseChartData;
import model.ExerciseChartDay;
import model.Person;

public class ExercisesTabController implements Initializable {

	// FXML Table
	@FXML
	TableView<Exercise> tvExercises;

	// FXML Columns
	@FXML
	TableColumn<Exercise, String> tcExercise, tcSets, tcReps, tcWeight, tcCaloriesBurned;

	@FXML
	DatePicker dpExercises;

	@FXML
	Button btnAddExercise, btnCustom, btnEdit, btnDelete;

	@FXML
	LineChart<?, ?> lineChartExercises;

	@FXML
	CategoryAxis categoryAxisDate;

	@FXML
	NumberAxis numberAxisWeight;

	@FXML
	ChoiceBox<String> choiceBoxTimeLine;

	// Hold the food data on the table in text form
	private ObservableList<Exercise> exerciseData = FXCollections.observableArrayList();
	// Hold the objects of foods local memory
	private ArrayList<Exercise> addedExercises = new ArrayList<Exercise>();
	// Used to check the current loaded date and day
	private static LocalDate currentDate;
	private static Day currentDay;
	// Stores the current LineChart data inside an arrayList
	private ArrayList<XYChart.Series<?, ?>> rgCharts = new ArrayList<XYChart.Series<?, ?>>();
	// Stores the LineChart current view mode
	private String currentMode = "Weekly";

	// Stores ExerciseChartDay; weekly, monthly, yearly
	private ArrayList<ExerciseChartData> weeklyData = new ArrayList<ExerciseChartData>();
	private ArrayList<LocalDate> weeklyDates = new ArrayList<LocalDate>();

	// Start of ExercisesTabController runs on creation
	public void initialize(URL arg0, ResourceBundle arg1) {

		setupDay();
		setupDatePicker();
		setupTable();
		setupChoiceBox();

		setupLineChart();
	}

	private void setupChoiceBox() {
		// Setup values into the ChoiceBox and place an event listener to choice the
		// amount that the line graph will show
		choiceBoxTimeLine.setItems(FXCollections.observableArrayList("Weekly", "Monthly", "Yearly"));

		choiceBoxTimeLine.setValue("Weekly");

		// Add event listener
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, //
					String oldValue, String newValue) {
				if (newValue != null) {

					// Change the current mode
					currentMode = newValue;

					// Depending on this value, change linechart view
					changeMode();
					update();
				}
			}
		};
		// Selected Item Changed.
		choiceBoxTimeLine.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	/**
	 * Called once in initialize, sets up the LineChart
	 * 
	 */
	private void setupLineChart() {

		System.out.println("{setupLineChart() : 1}");

		lineChartExercises.setTitle("Progressive Overload");
	}

	private void changeMode() {
		// Clear the Line Chart
		clearLineChart();

		// Create the Line Chart
		updateLineChart();
	}

	/**
	 * Clears all the values in the line chart so we can rebuild it
	 */
	private void clearLineChart() {
		rgCharts.clear();
		lineChartExercises.getData().clear();

		// remove stored chart data
		weeklyData.clear();
		weeklyDates.clear();

		// remove any fixed categories
		categoryAxisDate.getCategories().clear();
	}

	/**
	 * Based off the Persons Units, change the text of the LineChart y axis
	 */
	private void updateLineChartAxis() {
		if (Person.getInstance().getUnits().equals(("Metric"))) {
			numberAxisWeight.setLabel("Weight (kg) ");
		} else {
			numberAxisWeight.setLabel("Weight (lbs) ");
		}
	}

	/**
	 * Calculates all the exercises weights that is currently added to days and
	 * represents them in LineChart
	 */
	public void updateLineChart() {

		// Change the title of weight to either (kg) or (lb) depending on persons unit
		updateLineChartAxis();

		// Re-Sort the days in order
		Collections.sort(MainProgramController.days);

		// Clear this lineChart (since this is recalled)
		clearLineChart();

		// GUI representation
		createLineChart(currentMode);
	}

	/**
	 * Goes through all the days, their exercises (weight) and converts to LineChart
	 * points
	 */
	@SuppressWarnings("unchecked")
	private void createLineChart(String value) {

		System.out.println("Timeline selected: " + value);

		switch (value) {
		case "Weekly":
			createWeeklyLineChart();
			break;
		case "Monthly":
			createMonthlyLineChart();
			break;
		case "Yearly":
			createYearlyLineChart();
			break;
		}
	}

	/**
	 * First method that gets called to build a weekly chart
	 */
	private void createWeeklyLineChart() {
		// Loads ExerciseChartData objects
		loadWeeklyExerciseChartData();

		// Creates GUI representation of LineChart
		buildGUIWeeklyLineChart();
	}

	/**
	 * Method that gets invoked inside createWeeklyLineChart()
	 */
	private void loadWeeklyExerciseChartData() {

		// Create a date range between the current 'selected' week in date picker
		Date selectedDate = Date.from(currentDay.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Debug
		System.out.println("Date range for weekly was: " + selectedDate.toString());

		// Get start of the week from 'selectedDate'
		System.out.println("Week start date: " + getStartOfWeek(selectedDate));

		// Get end of the week from 'selectedDate'
		System.out.println("Week end date: " + getEndOfWeek(selectedDate));

		// Loop through the relevant day range
		// Find the exercises and create an ExerciseChartData object, storing the same
		// weight value
		// E.g. Day 1: {name: Deadlift, weight: 40}
		// Day 2: {name: Deadlift, weight: 45}
		// What would be saved is ExerciseChartData {name: Deadlift, weight[]: 40, 45 }

		Date start = getStartOfWeek(selectedDate);
		Date end = getEndOfWeek(selectedDate);

		// Returns the index in the ArrayList of the starting day
		int startIndex = getDateIndex(start);
		int endIndex = getDateIndex(end);

		// System.out.println("Found start index to be: " + startIndex + " end index to
		// be: " + endIndex);

		for (int i = startIndex; i <= endIndex; i++) {
			// Reference to the current day
			Day day = MainProgramController.days.get(i);

			// Save the dates into ArrayList
			weeklyDates.add(day.getDate());

			// Loop through its exercises and add create an ExerciseChartData
			for (int e = 0; e < day.getExercises().size(); e++) {
				createWeeklyExercise(day.getExercises().get(e), day.getDate());
			}
		}
	}

	/**
	 * Method gets invoked in loadWeeklyExerciseChartData Makes sure that if we have
	 * duplicate exercise names, we only increase the weight ArrayList
	 * 
	 * @param e
	 * @param date
	 */
	private void createWeeklyExercise(Exercise e, LocalDate date) {

		boolean found = false;
		// Loop through
		for (int i = 0; i < weeklyData.size(); i++) {

			// Check whether or not we already have this exercise added

			if (weeklyData.get(i).getName().equals(e.getName())) {
				found = true;

				// add into weeklyData
				weeklyData.get(i).addChartData(date, e.getWeight());

				break;
			}
		}

		if (!found) {

			ArrayList<LocalDate> rgDates = new ArrayList<LocalDate>();
			rgDates.add(date);

			ArrayList<Double> rgValues = new ArrayList<Double>();
			rgValues.add(e.getWeight());

			// add a new ExerciseChartData object
			ExerciseChartData ecd = new ExerciseChartData(e.getName(), rgDates, rgValues);
			weeklyData.add(ecd);
		}
	}

	/**
	 * Second method that gets invoked inside createWeeklyLineChart
	 */
	private void buildGUIWeeklyLineChart() {

		// Builds the category axis
		buildGUIWeeklyLineChartCategoryAxis();

		// Builds the number axis
		buildGUIWeeklyLineChartNumberAxis();

		// Builds the line points
		buildGUILinePoints();
	}

	/**
	 * First method that gets invoked in buildGUIWeeklyLineChart
	 */
	private void buildGUIWeeklyLineChartCategoryAxis() {
		// Store all the dates into a string ArrayList
		ArrayList<String> olDates = new ArrayList<String>();

		for (int i = 0; i < weeklyDates.size(); i++) {

			System.out.println("Adding ---> into ArrayList ---> " + weeklyDates.get(i));

			DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM");

			olDates.add(weeklyDates.get(i).format(sdf).toString());
		}

		ObservableList<String> olCategory = FXCollections.observableArrayList(olDates);

		System.out.println("Setup categories");

		// needs to be set to false so that we can get immediate changes to category
		// by default the axes have auto ranging enabled, so any change applied will be
		// overridden.
		numberAxisWeight.setAutoRanging(false);
		categoryAxisDate.setAutoRanging(false);

		categoryAxisDate.setCategories(olCategory);
	}

	/**
	 * Second method that gets invoked in buildGUIWeeklyLineChart
	 */
	private void buildGUIWeeklyLineChartNumberAxis() {
		numberAxisWeight.setLowerBound(0);
		numberAxisWeight.setUpperBound(200);
	}

	/**
	 * Third method that gets invoked in buildGUIWeeklyLineChart
	 */
	@SuppressWarnings("unchecked")
	private void buildGUILinePoints() {

		// Loop through weekly Data and get the points
		for (int i = 0; i < weeklyData.size(); i++) {

			ExerciseChartData ecd = weeklyData.get(i);

			@SuppressWarnings("rawtypes")
			XYChart.Series series = new XYChart.Series();
			series.setName(ecd.getName());

			// Loop through dates/values
			for (int p = 0; p < ecd.getDates().size(); p++) {

				System.out.println("DATES SIZE -------------------------------------------");

				// Get the CategoryAxis value
				DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM");

				String date = ecd.getDates().get(p).format(sdf).toString();
				double value = ecd.getValues().get(p);

				System.out.println("Date: " + date);
				System.out.println("value: " + value);

				// Add the data
				series.getData().add(new XYChart.Data(date, value));
			}

			// Finally add it to the graph
			lineChartExercises.getData().addAll(series);
		}

	}

	/**
	 * Method used to debug all the points on the weekly chart
	 */
	private void outputWeeklyChartData() {
		for (int i = 0; i < weeklyData.size(); i++) {

			// Format the output
			DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM");

			// shorter reference
			ExerciseChartData ecd = weeklyData.get(i);

			// shorter length reference
			int len = ecd.getDates().size();

			// loop through both dates/exercises (since they are same size)
			for (int p = 0; p < len; p++) {
				String categoryAxisDate = ecd.getDates().get(p).format(sdf).toString();
				double numberAxisWeight = ecd.getValues().get(p);

				// Actual debug info
				System.out.println("CategoryAxis: " + categoryAxisDate);
				System.out.println("NumberAxis: " + numberAxisWeight);
			}
		}
	}

	// ----------------------- MONTHLY CHART-------------------------------------

	private void createMonthlyLineChart() {

		// Loads ExerciseChartData objects
		loadMonthlyExerciseChartData();

		// Creates GUI representation of LineChart
		buildGUIMonthlyLineChart();
	}

	private void loadMonthlyExerciseChartData() {
		// Create a date range between the current 'selected' week in date picker
		Date selectedDate = Date.from(currentDay.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Debug
		System.out.println("Date range for monthly was: " + selectedDate.toString());

		// Get start of the month from 'selectedDate'
		System.out.println("Month start date: " + getStartOfMonth(selectedDate));

		// Get end of the month from 'selectedDate'
		System.out.println("Month end date: " + getEndOfMonth(selectedDate));

		Date start = getStartOfWeek(selectedDate);
		Date end = getEndOfWeek(selectedDate);
		int startIndex = getDateIndex(start);
		int endIndex = getDateIndex(end);

		for (int i = startIndex; i <= endIndex; i++) {
			// Reference to the current day
			Day day = MainProgramController.days.get(i);

			// Save the dates into ArrayList
			weeklyDates.add(day.getDate());

			// Loop through its exercises and add create an ExerciseChartData
			for (int e = 0; e < day.getExercises().size(); e++) {
				createWeeklyExercise(day.getExercises().get(e), day.getDate());
			}
		}
	}

	private void buildGUIMonthlyLineChart() {
		
	}

	private void buildGUIMonthlyLineChartCategoryAxis() {

	}

	private void buildGUIMonthlyLineChartNumberAxis() {

	}

	private void buildGUIMonthlyLinePoints() {

	}

	private void createYearlyLineChart() {
		// Create a date range between the current 'selected' week in date picker
		Date selectedDate = Date.from(currentDay.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Debug
		System.out.println("Date range for yearly was: " + selectedDate.toString());

		// Get start of the month from 'selectedDate'
		System.out.println("Year start date: " + getStartOfYear(selectedDate));

		// Get end of the month from 'selectedDate'
		System.out.println("Year end date: " + getEndOfYear(selectedDate));
	}

	public static LocalDate getLocalDateFromDate(Date date) {
		return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
	}

	private int getDateIndex(Date startDate) {

		for (int i = 0; i < MainProgramController.days.size(); i++) {

			Day day = MainProgramController.days.get(i);

			int num1 = getLocalDateFromDate(startDate).getDayOfYear();
			int num2 = day.getDate().getDayOfYear();

			// If we get a match, return the index in the ArrayList
			if (num1 == num2) {
				return i;
			}
		}

		// No index found
		return -1;
	}

	public Date convertToDateViaInstant(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	private Date getStartOfYear(Date selectedDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDate);

		cal.set(Calendar.DAY_OF_YEAR, 1);

		return cal.getTime();
	}

	private Date getEndOfYear(Date selectedDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDate);

		// Return the last day of the year
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));

		return cal.getTime();
	}

	private Date getEndOfMonth(Date selectedDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDate);

		// Set the day to the end of the month and time to 23:59:59
		cal.set(Calendar.DAY_OF_MONTH, getMonthDays(selectedDate));
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return cal.getTime();
	}

	private Date getStartOfMonth(Date selectedDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDate);

		// Ensure its the first day, at 00:00:00
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	private Date getStartOfWeek(Date selectedDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDate);

		// Go to the start of the week
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

		// Ensure time is set at 00:00:00
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	private Date getEndOfWeek(Date selectedDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(selectedDate);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);

		// ensure time is 23:59:59
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);

		return cal.getTime();
	}

	// return the amount of days in the current month
	private int getMonthDays(Date selectedDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(selectedDate);
		int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		return monthMaxDays;
	}

	public static boolean between(Date date, Date dateStart, Date dateEnd) {
		if (date != null && dateStart != null && dateEnd != null) {
			if (date.after(dateStart) && date.before(dateEnd)) {
				return true;
			} else if (date.equals(dateStart) || date.equals(dateEnd)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Sets up table so each column is linked with Exercise getters
	 */
	private void setupTable() {

		tcExercise.setCellValueFactory(e -> e.getValue().getStrExercise());
		tcSets.setCellValueFactory(e -> e.getValue().getStrSets());
		tcReps.setCellValueFactory(e -> e.getValue().getStrReps());
		tcWeight.setCellValueFactory(e -> e.getValue().getStrWeight());
		tcCaloriesBurned.setCellValueFactory(e -> e.getValue().getStrCaloriesBurned());

		// Add observable list data to the table
		tvExercises.setItems(exerciseData);

		// Disable buttons on start
		showBtns(false);

		// Setup a table handler which will show Edit/Delete if we select a food
		tvExercises.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				showBtns(true);
			} else {
				// Hide the buttons
				showBtns(false);
			}
		});
	}

	private void showBtns(boolean value) {
		btnDelete.setDisable(!value);
		btnDelete.setVisible(value);
		btnEdit.setDisable(!value);
		btnEdit.setVisible(value);
	}

	/**
	 * Sets up the current day to the selected date picker
	 */
	private void setupDay() {
		dpExercises.setValue(LocalDate.now());
		currentDate = dpExercises.getValue();
		currentDay = MainProgramController.getDay(currentDate);
	}

	/**
	 * Sets up the date picker
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setupDatePicker() {
		dpExercises.setOnAction(new EventHandler() {
			public void handle(Event t) {
				LocalDate date = dpExercises.getValue();

				// Update the currentDay
				currentDay = MainProgramController.getDay(date);

				// Clear the table
				addedExercises.clear();
				exerciseData.clear();

				// Load the exercise into the table
				Day.loadAddedExercises(currentDay, addedExercises);
				loadTableExercises();
				update();
			}
		});
	}

	/**
	 * loads the local memory arraylist into the GUI table arraylist
	 */
	private void loadTableExercises() {
		// Calculate total data
		for (int i = 0; i < addedExercises.size(); i++) {
			Exercise f = addedExercises.get(i);
			exerciseData.add(f);
		}
	}

	/**
	 * Is called whenever we switch to this tab, to refresh and update values which
	 * might have changed elsewhere (profile for e.g.)
	 */
	public void update() {
		tvExercises.refresh();
		updateLineChart();
	}

	@FXML
	protected void handleChoiceBoxTimeLine(ActionEvent event) throws IOException {

	}

	/**
	 * FXML button handler for adding exercises
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleAddExercise(ActionEvent event) throws IOException {
		System.out.println("Open add Exercise window");
		// Try opening 'add exercises window' which will load stored exercises from
		// database
		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/exercisesTabAddExerciseWindow.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 400);
			Stage stage = new Stage();
			Stage parent = (Stage) btnAddExercise.getScene().getWindow();
			stage.initOwner(parent);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Add Entry");
			stage.setScene(scene);

			// Setup controller
			AddExerciseController controller = fxmlLoader.<AddExerciseController>getController();

			// showAndWait will block execution until the window closes...
			stage.showAndWait();

			// continue on adding entry with controller
			addEntry(controller);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Is called within the FXML button handler 'handleAddExercise' it handles
	 * adding the exercise into the table itself
	 * 
	 * @param controller
	 */
	private void addEntry(AddExerciseController controller) {
		try {
			System.out.println("ExercieTabController: " + controller.getExercise());

			// Copy the object
			Exercise newExercise = new Exercise(controller.getExercise());

			// Add values to the table!
			addedExercises.add(newExercise);
			exerciseData.add(newExercise);
			currentDay.addExercise(newExercise);

			// Try to add it uniquely (used for LineChart)
			MainProgramController.addExercise(newExercise);

			// Update GUI
			update();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * FXML button handler for deleting exercises off the table
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleDelete(ActionEvent event) throws IOException {
		// Make sure we selected an entry on the table
		try {
			// Get the current Exercise
			Exercise selectedExercise = tvExercises.getSelectionModel().getSelectedItem();

			addedExercises.remove(selectedExercise);
			exerciseData.remove(selectedExercise);
			currentDay.deleteExercise(selectedExercise);

			update();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * FXML button handler for adding Custom Exercises to either the table or todays
	 * entry
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleCustom(ActionEvent event) throws IOException {
		try {
			// Create window
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/exercisesTabCustomExerciseWindow.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 430, 320);
			Stage stage = new Stage();
			Stage parent = (Stage) btnCustom.getScene().getWindow();
			stage.initOwner(parent);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Create Custom Exercise");
			stage.setScene(scene);

			// Controller access
			CustomExerciseController controller = fxmlLoader.<CustomExerciseController>getController();

			// showAndWait will block execution until the window closes...
			stage.showAndWait();

			// continue with the controller
			addCustom(controller);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called inside the FXML button handler 'handleCustom' handles either adding
	 * the Custom Exercise or storing it into the 'AddExerciseController' table
	 * 
	 * @param controller
	 */
	private void addCustom(CustomExerciseController controller) {

		// Try getting controller values and create a custom exercise in the database
		try {
			// Get exercise object from controller
			Exercise retrievedEx = controller.getExercise();

			// Copy it
			Exercise newEx = new Exercise(retrievedEx);
			// Make sure we apply it as a custom
			newEx.setCustom(true);

			// Add it to daily the table (if we selected to)
			if (controller.addToTable()) {
				System.out.println("Add to Table was selected");

				addedExercises.add(newEx);
				exerciseData.add(newEx);
				currentDay.addExercise(newEx);

				// Add it as a unique exercise
				MainProgramController.addExercise(newEx);
			}

			// Add to the local memory arraylist, the table gui and the currentDay arraylist
			AddExerciseController.addedExercises.add(newEx);
			AddExerciseController.exerciseData.add(newEx);

			update();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * FXML button handle for Editing exercises will retrieve the Exercise object
	 * that is associated to the table row and edit the values directly
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleEdit(ActionEvent event) throws IOException {
		// Create window
		try {
			Exercise selectedExercise = tvExercises.getSelectionModel().getSelectedItem();

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/exercisesTabEditExerciseWindow.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 580, 200);
			Stage stage = new Stage();
			Stage parent = (Stage) btnEdit.getScene().getWindow();
			stage.initOwner(parent);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Edit Exercise");
			stage.setScene(scene);

			// Controller access
			EditExerciseController controller = fxmlLoader.<EditExerciseController>getController();
			controller.setEditExercise(selectedExercise);
			// showAndWait will block execution until the window closes...
			stage.showAndWait();

			// Refresh reference
			selectedExercise.setExercise(controller.getEditExercise());

			update();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}