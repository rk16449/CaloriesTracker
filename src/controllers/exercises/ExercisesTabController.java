package controllers.exercises;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Day;
import model.Exercise;
import model.Food;

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
	
	
	// Hold the food data on the table in text form
	private ObservableList<Exercise> exerciseData = FXCollections.observableArrayList();
	// Hold the objects of foods local memory
	private ArrayList<Exercise> addedExercises = new ArrayList<Exercise>();
	// Used to check the current loaded date and day
	private static LocalDate currentDate;
	private static Day currentDay;
	
	private ArrayList<XYChart.Series> charts = new ArrayList<XYChart.Series>();
	
	
	// Start of ExercisesTabController runs on creation
	public void initialize(URL arg0, ResourceBundle arg1) {
		setupDay();
		setupDatePicker();
		setupTable();
		setupLineChart();
	}
	
	private void setupLineChart() {
		// Whenever we click on an exercise on the table, we want to show the weight used each week
		// Assuming that's when we want to take our weight averages
		
		// The chart can have multiple lines, each line represents an entire exercise
		
		// Clear this lineChart
		charts.clear();
		lineChartExercises.getData().clear();
		
		lineChartExercises.setTitle("Weight of Exercises");
		
		// Checkout how many different exercises we have ever added into days
		
		// For each exercise, of each week add a point in the line chart
		for(int i=0; i<MainProgramController.addedExercises.size(); i++) {
			
			Exercise lineExercise = MainProgramController.addedExercises.get(i);
			
			XYChart.Series series1 = new XYChart.Series();
	        series1.setName(lineExercise.getName());
			
			// Loop through every day and get the exercise weight data on this date
			for(int z=0; z<MainProgramController.days.size(); z++) {
				
				Day currentDay = MainProgramController.days.get(z);
				
				// Loop through the excercises on this day
				for(int p=0; p<currentDay.getExercises().size(); p++) {
					
					Exercise currentExercise = currentDay.getExercises().get(p);
					
					// Check if this is the same one
					if(currentExercise.getName().equals(lineExercise.getName())) {
						// Add the data
						series1.getData().add(new XYChart.Data(currentDay.getDate().toString(), currentExercise.getWeight()));
						charts.add(series1);
					}
				}
			}
			
			lineChartExercises.getData().addAll(series1);
		} 
	}
	
	private void setupTable() {
		
		tcExercise.setCellValueFactory(e -> e.getValue().getStrExercise());
		tcSets.setCellValueFactory(e -> e.getValue().getStrSets());
		tcReps.setCellValueFactory(e -> e.getValue().getStrReps());
		tcWeight.setCellValueFactory(e -> e.getValue().getStrWeight());
		tcCaloriesBurned.setCellValueFactory(e -> e.getValue().getStrCaloriesBurned());
		
		// Add observable list data to the table
		tvExercises.setItems(exerciseData);
	}
	
	private void setupDay() {
		dpExercises.setValue(LocalDate.now());
		currentDate = dpExercises.getValue();
		currentDay = MainProgramController.getDay(currentDate);
	}
	
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
				loadAddedExercises();
				loadTableExercises();
				update();
			}
		});
	}
	
	private void loadAddedExercises() {
		// loop through the currentDay food
		for (int i = 0; i < currentDay.getExercises().size(); i++) {
			Exercise f = currentDay.getExercises().get(i);
			addedExercises.add(f);
		}
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
	
	private void update() {
		tvExercises.refresh();
	}
	

	
	@FXML
	protected void handleAddExercise(ActionEvent event) throws IOException {
		// Open add exercises window which will load stored exercises from database
		
		System.out.println("Open add Exercise window");
		
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
			System.out.println("Failed to create a window");
		}
		
		
	}
	
	
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
			System.out.println("Nullpointerexception, probably because we hit the X/ no exercise selected");
		}
	}
	
	
	
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
			System.out.println("Couldn't delete item, probably haven't selected anything");
		}
	}
	
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
			System.out.println("Couldn't make create food window..?");
		}
	}
	
	private void addCustom(CustomExerciseController controller) {
		// Get controller values and create a custom exercise in the database
		
		
		try {
			// Get exercise object from controller
			Exercise retrievedEx = controller.getExercise();
			
			// Copy it
			Exercise newEx = new Exercise(retrievedEx);
			// Make sure we apply it as a custom
			newEx.setCustom(true);

			// Add it to daily the table (if we selected to)
			if (controller.addToTable()) {
				addedExercises.add(newEx);
				exerciseData.add(newEx);
			}

			// Add to the local memory arraylist, the table gui and the currentDay arraylist
			AddExerciseController.addedExercises.add(newEx);
			AddExerciseController.exerciseData.add(newEx);
			currentDay.addExercise(newEx);
			update();
		}catch(NullPointerException e) {
			System.out.println("Null exercise set");
		}
	}
	
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
			selectedExercise = controller.getEditExercise();
			
			update();
		}catch(NullPointerException e) {
			System.out.println("Null exception");
		}

	}
}