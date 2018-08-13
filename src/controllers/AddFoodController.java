package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Food;


public class AddFoodController implements Initializable {

	@FXML
	private ListView<Food> listviewFoods;
	
	@FXML
	private TextField textfieldSearch;
	
	@FXML
	private Button buttonSearch, buttonAddFood;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Fill listview with dummy data
	}
}