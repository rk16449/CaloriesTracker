package controllers.exercises;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Exercise;
import model.Helper;
import model.Person;

public class AddExerciseController extends BaseExerciseController implements Initializable {
	
	@FXML
	TableView<Exercise> tvExercises;
	
	@FXML
	TableColumn<Exercise, String> tcExercise;
	
	@FXML
	Button btnAddExercise, btnSearch;
	
	@FXML
	TextField tfSearch;
	
	@FXML
	CheckBox checkBoxEstimate;

	// The current selected Exercise we need to pass back to the DietTabController
	private Exercise returnExerciseData;
	// Hold the Exercise data on the table in text form
	public static ObservableList<Exercise> exerciseData = FXCollections.observableArrayList();
	// Hold the objects of Exercises
	public static ArrayList<Exercise> addedExercises = new ArrayList<Exercise>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		// Create Dummy Data for table 
		// (commenting this out will break a guideline)
		if(addedExercises.size() == 0) {
			Exercise e1 = new Exercise("Bench Press");
			Exercise e2 = new Exercise("Squat");
			Exercise e3 = new Exercise("Deadlift");
			Exercise e4 = new Exercise("Overhead Press");
			Exercise e5 = new Exercise("Barbell Row");
			
			addedExercises.addAll(Arrays.asList(e1, e2, e3, e4, e5));
			exerciseData.addAll(Arrays.asList(e1, e2, e3, e4, e5));
		}
		
		FilteredList<Exercise> flExercises = new FilteredList<Exercise>(exerciseData, p -> true);
		
		// Setup column values
		tcExercise.setCellValueFactory(e -> e.getValue().getStrExercise());
		
		// Add filtered list data to the table
		tvExercises.setItems(flExercises);
		
		
		// Setup TextField filter (based off food name)
		tfSearch.setPromptText("Search here!");
		tfSearch.setOnKeyReleased(keyEvent -> {
			System.out.println("textfield search activated");
			flExercises.setPredicate(
					p -> p.getName().toLowerCase().contains(tfSearch.getText().toLowerCase().trim()));
		});
	}

	public Exercise getExercise() {
		return returnExerciseData;
	}
	
	@FXML
	protected void handleEstimateCaloriesBurned(ActionEvent event) throws IOException {
		
		
		// If checkBox Estimate is selected then we need to disable and calculate what the calories burned is
		if(checkBoxEstimate.isSelected()) {
			tfCaloriesBurned.setDisable(true);
			
			tfCaloriesBurned.setText(calculateCaloriesBurned());
			
		}else {
			tfCaloriesBurned.setDisable(false);
		}
		
	}
	
	/**
	 * This method attempts to estimate the calories burned during exercise
	 * It checks the persons weight(in lbs) and multiplies by the amount of minutes exercised
	 * It then multiplies this by the intensityLevel (which is calculated from the amount of weight lifted)
	 * @return
	 */
	private String calculateCaloriesBurned() {
		
		double weightLifted = 0, sets = 0, reps = 0;
		double timeExercised = 0, intensityLevel = 0, secondsPerRep = 10;
		
		// Try to convert the TextFields into doubles
		try {
			weightLifted = Double.parseDouble(tfWeight.getText());
			sets = Double.parseDouble(tfSets.getText());
			reps = Double.parseDouble(tfReps.getText());
		}catch(NumberFormatException e) {
			System.out.println("TextFields cannot be converted to a double");
		}

		
		// Get our weight but make sure its converted into pounds, also convert weightLifted into KG
		double personWeight = 0.0;
		if(Person.getInstance().getUnits().equals(("Metric"))){
			personWeight = Person.getInstance().getWeight() * 2.20462;
		}else {
			personWeight = Person.getInstance().getWeight();
			
			// convert weight lifted to kg
			weightLifted = weightLifted / 2.20462;
		}
		
		
		// Calculate intensity level (20kg barbell)
		if(weightLifted >=0 && weightLifted <= 20) {
			intensityLevel = 0.0475;
		}else if(weightLifted > 20 && weightLifted <= 40) {
			intensityLevel = 0.0525;
		}else if(weightLifted > 40 && weightLifted <= 80) {
			intensityLevel = 0.055;
		}else if(weightLifted > 80 && weightLifted <= 120) {
			intensityLevel = 0.06;
		}else if(weightLifted > 120 && weightLifted <= 200) {
			intensityLevel = 0.07;
		}
		

		// We assume it takes about on average 10 seconds per rep
		timeExercised = (((reps * secondsPerRep) * sets) / 60);
		
		// Calculate the calories burned
		double caloriesBurned = (timeExercised * personWeight) * intensityLevel;
		
		// Return the value rounded by 2 decimals in String form
		return Double.toString(Helper.round(caloriesBurned, 2));
	}
	
	@FXML
	protected void handleTextField(KeyEvent event) throws IOException {
		
		System.out.println("text field handler ---");
		
		// If we have estimate calories checked, update the calories value every time we change TextField
		if(checkBoxEstimate.isSelected()) {
			tfCaloriesBurned.setText(calculateCaloriesBurned());
		}
		
	}
	
	
	@FXML
	protected void handleAddExercise(ActionEvent event) throws IOException {
		System.out.println("Add the exercise to the table behind us!");
		try {
			Exercise selectedExercise = tvExercises.getSelectionModel().getSelectedItem();
			
			// Creates a new exercise based off the TextFields
			Exercise newEx = super.createExercise(selectedExercise);
			// Update the reference of the returnable object
			returnExerciseData = newEx;
	
			btnAddExercise.getScene().getWindow().hide();
		} catch (NullPointerException e) {
			System.out.println("A table row wasn't selected");
		} catch(NumberFormatException e) {
			System.out.println("Errors in converting textfields to numbers");
		}
	}

}
