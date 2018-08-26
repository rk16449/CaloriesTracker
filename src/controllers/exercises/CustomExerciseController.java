package controllers.exercises;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.Exercise;

public class CustomExerciseController implements Initializable{
	
	@FXML 
	Button btnCreate;
	@FXML
	TextField tfName, tfSets, tfReps, tfWeight, tfCaloriesBurned;
	@FXML
	CheckBox cbAddToToday;
	
	private Exercise customExercise;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	// Setup Listener for ChoiceBox to enable or disable TextFields
	@FXML
	protected void handleAddToToday(ActionEvent event) throws IOException {
		tfSets.setDisable(!cbAddToToday.isSelected());
		tfReps.setDisable(!cbAddToToday.isSelected());
		tfWeight.setDisable(!cbAddToToday.isSelected());
		tfCaloriesBurned.setDisable(!cbAddToToday.isSelected());
	}

	public Exercise getExercise() {
		return customExercise;
	}

	public boolean addToTable() {
		return cbAddToToday.isSelected();
	}
	
	@FXML
	protected void handleCreate(ActionEvent event) throws IOException {
		// Create an Exercise object if we have at least a name typed in.. and return back to controller
		if(!tfName.getText().isEmpty() && !cbAddToToday.isSelected()) {
			customExercise = new Exercise(tfName.getText());
			btnCreate.getScene().getWindow().hide();	
		}
		// We need no empty name and we have add selected
		else if(!tfName.getText().isEmpty() && cbAddToToday.isSelected()) {
			
			
			customExercise = new Exercise(tfName.getText());
			
			// Make sure there are number values inserted
			try {
				// Get values from textfields and pass them into exercise
				customExercise.setSets(Integer.parseInt(tfSets.getText()));
				customExercise.setReps(Integer.parseInt(tfReps.getText()));
				customExercise.setWeight(Double.parseDouble(tfWeight.getText()));
				customExercise.setCaloriesBurned(Double.parseDouble(tfCaloriesBurned.getText()));
			}catch(NumberFormatException e) {
				System.out.println("Cannot convert textfield text to numbers");
				
				// Do not close the window/continue
				customExercise = null;
				return;
			}
			
			// Close the window - return back to ExercisesTabController
			btnCreate.getScene().getWindow().hide();	
		}else {
			// Set as null so we don't return any exercise object from this controller
			customExercise = null;
		}
	}

}
