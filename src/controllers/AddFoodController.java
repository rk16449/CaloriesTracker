package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Food;

public class AddFoodController implements Initializable {
	
	private Stage stage;
	
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

	@FXML
	private Spinner<Integer> spinnerQuantity;

	
	// The current selected food we need to pass back to the DietTabController
	private Food returnFoodData;
	
	
	// Hold the food data on the table in text form
	private ObservableList<Food> foodData = FXCollections.observableArrayList();
	// Hold the objects of foods
	private ArrayList<Food> addedFoods = new ArrayList<Food>();

	
	
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Fill tableview with dummy data (later from database)
		Food food1 = new Food("Whole Milk", 100, new double[] { 4.70, 3.70, 3.50 });
		Food food2 = new Food("Protein Powder", 30, new double[] { 3.77, 0.2, 23.71 });
		Food food3 = new Food("White Rice", 100, new double[] { 78.90, 0.70, 6.70 });

		// add to arraylist
		addedFoods.add(food1);
		addedFoods.add(food2);
		addedFoods.add(food3);

		// Add sample data
		for (int i = 0; i < addedFoods.size(); i++) {
			foodData.add(addedFoods.get(i));
		}

		// Initialize the person table with the two columns.
		foodColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFood());

		amountColumn.setCellValueFactory(cellData -> cellData.getValue().getStrAmount());
		caloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getStrCalories());

		carbsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrCarbs());
		fatsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFats());
		proteinColumn.setCellValueFactory(cellData -> cellData.getValue().getStrProts());

		FilteredList<Food> flFoods = new FilteredList<Food>(foodData, p -> true);

		// Add filtered list data to the table
		tableviewFoods.setItems(flFoods);

		// Setup textfield filter (based off food name)
		textfieldSearch.setPromptText("Search here!");
		textfieldSearch.setOnKeyReleased(keyEvent -> {
			System.out.println("textfield search activated");
			flFoods.setPredicate(
					p -> p.getName().toLowerCase().contains(textfieldSearch.getText().toLowerCase().trim()));
		});

		// Setup spinner to increase quantity >= 1
		spinnerQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));

		// TODO(minor bug) Typing over 100 will result in an error NumberFormatException

	}

	@FXML
	protected void handleAddFood(ActionEvent event) throws IOException {
		System.out.println("Add the food to the table behind us!");
		try {
			Food selectedFood = tableviewFoods.getSelectionModel().getSelectedItem();
			System.out.println("We want to add: " + selectedFood.getName());
			System.out.println("The quantity to add is: " + spinnerQuantity.getValue());
			returnFoodData = selectedFood;
			stage.close();
		} catch (NullPointerException e) {
			System.out.println("A table row wasn't selected");
		}
	}

	public String getData() {
		return returnFoodData.getName();
	}

	public int getQuantity() {
		return spinnerQuantity.getValue();
	}

	public void setStageAndSetupListeners(Stage stage) {
		this.stage = stage;
	}
	
	public Food getFood() {
		return returnFoodData;
	}
}