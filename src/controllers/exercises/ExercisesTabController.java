package controllers.exercises;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

import controllers.MainProgramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Day;
import model.Exercise;
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
	NumberAxis numberAxisWeight;
	
	
	// Hold the food data on the table in text form
	private ObservableList<Exercise> exerciseData = FXCollections.observableArrayList();
	// Hold the objects of foods local memory
	private ArrayList<Exercise> addedExercises = new ArrayList<Exercise>();
	// Used to check the current loaded date and day
	private static LocalDate currentDate;
	private static Day currentDay;
	// Stores the current LineChart data inside an arrayList
	private ArrayList<XYChart.Series<?, ?>> rgCharts = new ArrayList<XYChart.Series<?, ?>>();
	
	
	// Start of ExercisesTabController runs on creation
	public void initialize(URL arg0, ResourceBundle arg1) {
		setupDay();
		setupDatePicker();
		setupTable();
		setupLineChart();
		updateLineChart();
	}
	
	/**
	 * Based off the Persons Units, change the text of the LineChart y axis
	 */
	private void setupLineChartAxis() {
		if(Person.getInstance().getUnits().equals(("Metric"))){
			numberAxisWeight.setLabel("Weight (kg) ");
		}else {
			numberAxisWeight.setLabel("Weight (lbs) ");
		}
	}
	
	/**
	 * Called once in initialize, sets up the LineChart
	 * 
	 */
	private void setupLineChart() {
		lineChartExercises.setTitle("Progressive Overload");
	}
	
	/**
	 * Calculates all the exercises weights that is currently added to days and represents them in LineChart
	 */
	public void updateLineChart() {

		// Change the title of weight to either (kg) or (lb) depending on persons unit
		setupLineChartAxis();

		// Re-Sort the days in order
		Collections.sort(MainProgramController.days);
		
		// Clear this lineChart (since this is recalled)
		rgCharts.clear();
		lineChartExercises.getData().clear();
		
		// GUI representation
		createLineChart();
	}
	
	/**
	 * Goes through all the days, their exercises (weight) and converts to LineChart points
	 */
	@SuppressWarnings("unchecked")
	private void createLineChart() {
		// Checkout how many different exercises we have ever added
		// For each exercise, of each week add a point in the line chart
		for(int i=0; i<MainProgramController.addedExercises.size(); i++) {
			
			Exercise lineExercise = MainProgramController.addedExercises.get(i);
			
			@SuppressWarnings("rawtypes")
			XYChart.Series series = new XYChart.Series();
	        series.setName(lineExercise.getName());
			
			// Loop through every day and get the exercise weight data on this date
			for(int z=0; z<MainProgramController.days.size(); z++) {
				
				// reference to current 'day' 
				Day currentDay = MainProgramController.days.get(z);
				
				// Loop through the exercises on this day
				for(int p=0; p<currentDay.getExercises().size(); p++) {
					
					// reference to current 'exercise'
					Exercise currentExercise = currentDay.getExercises().get(p);
					
					// Check if this is the same one
					if(currentExercise.getName().equals(lineExercise.getName())) {
						
						// Convert the date format
						DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM");
						// Add the data
						series.getData().add(new XYChart.Data<String, Double>(currentDay.getDate().format(sdf).toString(), currentExercise.getWeight()));
						// Save reference to rgCharts ArrayList
						rgCharts.add(series);
					}
				}
			}
			
			// Finally add it to the graph
			lineChartExercises.getData().addAll(series);
		} 
	}
	
	private void setupLineChartCurrentWeek() {
		
		// Loop through all the days until we are in the current month
		for(int i=0; i<MainProgramController.days.size(); i++) {
			
			Day day = MainProgramController.days.get(i);
			
			// Convert LocalDate to Date so we can check with between method
			Date date = Date.from(day.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
			// Add 1 second to this date so that we can fit the interval
			date.setSeconds(1);
			
			// Check that the date is in the correct range
			if(between(date, getMonthStart(), getMonthEnd())) {
				
			}
			
			// If there are no exercises set this month, then pre-load days of the current week
			
		}
		
		// Loop through all the exercises of the current week and show it
		
	}
	
	private Date getMonthStart() {
		
		Calendar c = Calendar.getInstance();   // this takes current date
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    
	    // set the hours, mins, seconds
	    c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
	    c.set(Calendar.SECOND, 0);
	    
		return c.getTime();
	}
	
	private Date getMonthEnd() {
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		
		// Set time to 23:59:59
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		return cal.getTime();
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
		    }else {
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
	 * Is called whenever we switch to this tab, to refresh and update values
	 * which might have changed elsewhere (profile for e.g.)
	 */
	public void update() {
		tvExercises.refresh();
		updateLineChart();
	}
	

	/**
	 * FXML button handler for adding exercises
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleAddExercise(ActionEvent event) throws IOException {
		System.out.println("Open add Exercise window");
		// Try opening 'add exercises window' which will load stored exercises from database
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
	 * Is called within the FXML button handler 'handleAddExercise'
	 * it handles adding the exercise into the table itself
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
	 * FXML button handler for adding Custom Exercises to either the table or todays entry
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
	 * Called inside the FXML button handler 'handleCustom'
	 * handles either adding the Custom Exercise or storing it into the 'AddExerciseController' table
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
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * FXML button handle for Editing exercises
	 * will retrieve the Exercise object that is associated to the table row
	 * and edit the values directly
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
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
}