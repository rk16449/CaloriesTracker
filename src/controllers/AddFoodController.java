package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Food;


public class AddFoodController implements Initializable {

	/**********************************************************
	 * TABLE GUI
	 **********************************************************/
	@FXML
	private TableView<Food> tableviewFoods;
	@FXML
	private TableColumn<Food, String> foodColumn;
	@FXML
	private TableColumn<Food, String> amountColumn;
	@FXML
	private TableColumn<Food, String> caloriesColumn;
	@FXML
	private TableColumn<Food, String> carbsColumn;
	@FXML
	private TableColumn<Food, String> fatsColumn;
	@FXML
	private TableColumn<Food, String> proteinColumn;
	
	@FXML
	private TextField textfieldSearch;
	
	@FXML
	private Button buttonSearch, buttonAddFood;
	
	// Hold the food data on the table in text form
	private ObservableList<Food> foodData = FXCollections.observableArrayList();
	// Hold the objects of foods
	private ArrayList<Food> addedFoods = new ArrayList<Food>();
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Fill tableview with dummy data (later from database)
		
		Food food1 = new Food("Whole Milk", 100, new double[] { 4.70, 3.70, 3.50 });
		Food food2 = new Food("Protein Powder", 30, new double[] { 3.77, 0.2, 23.71 });
		
		// add to arraylist
		addedFoods.add(food1);
		addedFoods.add(food2);
		
		// Add sample data
		for(int i=0; i<addedFoods.size(); i++) {
			foodData.add(addedFoods.get(i));
		}
		
		// Initialize the person table with the two columns.
		foodColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFood());
		
		amountColumn.setCellValueFactory(cellData -> cellData.getValue().getStrAmount());
		caloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getStrCalories());
		
		carbsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrCarbs());
		fatsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFats());
		proteinColumn.setCellValueFactory(cellData -> cellData.getValue().getStrProts());
		

		// Add observable list data to the table
		tableviewFoods.setItems(foodData);
	}
}