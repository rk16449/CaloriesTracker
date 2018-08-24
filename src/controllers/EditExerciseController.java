package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditExerciseController implements Initializable {

	@FXML
	TextField tfSets, tfReps, tfWeight, tfCaloriesBurned;
	
	@FXML
	Button btnSave;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	@FXML
	protected void handleSave(ActionEvent event) throws IOException {

	}
}
