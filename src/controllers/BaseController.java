package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import model.Person;

public class BaseController {
	
	
	
	protected Stage stage;
	protected Person person;
	
	
	
	public BaseController(){
		person = Person.getInstance();
	}
	
	protected void setStageAndSetupListeners(Stage stage) {
		this.stage = stage;
	}
	
	
}
