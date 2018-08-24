package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
	

	
	
	// Start of ExercisesTabController runs on creation
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setupDatePicker();
		setupTable();
		
	}
	
	private void setupDatePicker() {
		dpExercises.setValue(LocalDate.now());
	}
	
	private void setupTable() {
		
		tcExercise.setCellValueFactory(e -> e.getValue().getStrExercise());
		tcSets.setCellValueFactory(e -> e.getValue().getStrSets());
		tcReps.setCellValueFactory(e -> e.getValue().getStrReps());
		tcWeight.setCellValueFactory(e -> e.getValue().getStrWeight());
		tcCaloriesBurned.setCellValueFactory(e -> e.getValue().getStrCaloriesBurned());
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
			System.out.println("ExercieTabController: " + controller.getExercise().getName());
			boolean found = false;
			// Check if this food already exists in the table, if it does increase its
			// quantity instead
			
			/*
			for (int i = 0; i < currentDay.getFoods().size(); i++) {
				// Assumes we don't have foods with exactly the same name.. (try adding id in
				// later)
				if (currentDay.getFoods().get(i).getName().equals(controller.getFood().getName())) {
					found = true;
					
					System.out.println("Updating quantity on addEntry");
					
					currentDay.getFoods().get(i).setQuantity(currentDay.getFoods().get(i).getQuantity() + controller.getQuantity());
					break;
				}
			}

			// Add a new row entry if same food isn't already added
			if (!found) {
				System.out.println("No Food was found, creating a new entry here!");
				
				// Copy the object
				Food newFood = new Food(controller.getFood().getName(), controller.getFood());
				newFood.setQuantity(controller.getQuantity()); // maybe do this automatically on getFood()

				// Add values to the table!
				addedFoods.add(newFood);
				foodData.add(newFood);
				currentDay.addFood(newFood);
			}

			// Update GUI
			update();
			*/
		} catch (NullPointerException e) {
			System.out.println("Nullpointerexception, probably because we hit the X/ no exercise selected");
		}
	}
	
	
	
	@FXML
	protected void handleDelete(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	protected void handleCustom(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	protected void handleEdit(ActionEvent event) throws IOException {
		
	}
}