package controllers;

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

	public Exercise getExercise() {
		return customExercise;
	}

	public boolean addToTable() {
		return false;
	}
	
	@FXML
	protected void handleCreate(ActionEvent event) throws IOException {
		
	}

}
