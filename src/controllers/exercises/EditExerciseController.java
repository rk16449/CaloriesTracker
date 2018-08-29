package controllers.exercises;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Exercise;

public class EditExerciseController implements Initializable {

	@FXML
	TextField tfSets, tfReps, tfWeight, tfCaloriesBurned;
	
	@FXML
	Button btnSave;
	
	// The exercise we want to edit
	private Exercise editExercise;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void setEditExercise(Exercise edit) {
		this.editExercise = edit;
		
		// Now set the values of the textfields
		tfSets.setText(Integer.toString(editExercise.getSets()));
		tfReps.setText(Integer.toString(editExercise.getReps()));
		tfWeight.setText(Double.toString(editExercise.getWeight()));
		tfCaloriesBurned.setText(Double.toString(editExercise.getCaloriesBurned()));
		
	}
	
	public Exercise getEditExercise() {
		return this.editExercise;
	}
	
	@FXML
	protected void handleSave(ActionEvent event) throws IOException {
		try {
			
			// Save values into editExercise
			Number[] nums = {
					Integer.parseInt(tfReps.getText()),
					Integer.parseInt(tfSets.getText()),
					Double.parseDouble(tfWeight.getText()),
					Double.parseDouble(tfCaloriesBurned.getText())
					};
			editExercise.setValues(nums);
			
			// Close this window and return back to ExercisesTabController
			btnSave.getScene().getWindow().hide();
		} catch (NullPointerException e) {
			System.out.println("A table row wasn't selected");
		} catch(NumberFormatException e) {
			System.out.println("couldn't convert text to numbers");
		}
	}
}
