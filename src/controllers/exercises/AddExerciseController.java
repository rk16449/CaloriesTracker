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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Exercise;

public class AddExerciseController extends BaseExerciseController implements Initializable {
	
	@FXML
	TableView<Exercise> tvExercises;
	
	@FXML
	TableColumn<Exercise, String> tcExercise;
	
	@FXML
	Button btnAddExercise, btnSearch;
	
	@FXML
	TextField tfSearch;

	// The current selected Exercise we need to pass back to the DietTabController
	private Exercise returnExerciseData;
	// Hold the Exercise data on the table in text form
	public static ObservableList<Exercise> exerciseData = FXCollections.observableArrayList();
	// Hold the objects of Exercises
	public static ArrayList<Exercise> addedExercises = new ArrayList<Exercise>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		// Create Dummy Data for table 
		/* (commenting this out will break a guideline)
		if(addedExercises.size() == 0) {
			Exercise e1 = new Exercise("Bench Press");
			Exercise e2 = new Exercise("Squat");
			Exercise e3 = new Exercise("Deadlift");
			Exercise e4 = new Exercise("Overhead Press");
			Exercise e5 = new Exercise("Barbell Row");
			
			addedExercises.addAll(Arrays.asList(e1, e2, e3, e4, e5));
			exerciseData.addAll(Arrays.asList(e1, e2, e3, e4, e5));
		}*/
		
		FilteredList<Exercise> flExercises = new FilteredList<Exercise>(exerciseData, p -> true);
		
		// Setup column values
		tcExercise.setCellValueFactory(e -> e.getValue().getStrExercise());
		
		// Add filtered list data to the table
		tvExercises.setItems(flExercises);
		
		
		// Setup textfield filter (based off food name)
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
	protected void handleAddExercise(ActionEvent event) throws IOException {
		System.out.println("Add the food to the table behind us!");
		try {
			Exercise selectedExercise = tvExercises.getSelectionModel().getSelectedItem();
			Exercise newEx = createExercise(selectedExercise);
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
