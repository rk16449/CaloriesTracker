package controllers.food;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Food;

public class AddFoodController extends BaseFoodController implements Initializable {
	
	/**********************************************************
	 * TABLE GUI
	 **********************************************************/
	@FXML
	private TableView<Food> tableviewFoods;
	
	@FXML
	private TableColumn<Food, String> foodColumn, 
	amountColumn, caloriesColumn, carbsColumn, fatsColumn, proteinColumn;

	@FXML
	private TextField textfieldSearch;

	@FXML
	private Button buttonSearch, buttonAddFood, buttonCreateCustom, buttonDelete;

	@FXML
	private Spinner<Double> spinnerQuantity;
	
	// The current selected food we need to pass back to the DietTabController
	private Food returnFoodData;
	// Hold the food data on the table in text form
	public static ObservableList<Food> foodData = FXCollections.observableArrayList();
	// Hold the objects of foods
	public static ArrayList<Food> addedFoods = new ArrayList<Food>();
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Temporarily used instead of database
		if(addedFoods.size() == 0) {
			System.out.println("ADDING WHOLE MILK...");
			// Fill tableview with dummy data (later from database)
			Food food1 = new Food("Whole Milk", 100, new double[] { 4.70, 3.70, 3.50 });
			Food food2 = new Food("Protein Powder", 30, new double[] { 3.77, 0.2, 23.71 });
			Food food3 = new Food("White Rice", 100, new double[] { 78.90, 0.70, 6.70 });
			Food food4 = new Food("Semi Skimmed Milk", 100, new double[] { 4.80, 1.80, 3.60 });
	
			// add to arraylist
			addedFoods.add(food1);
			addedFoods.add(food2);
			addedFoods.add(food3);
			addedFoods.add(food4);
	
			// Add sample data
			for (int i = 0; i < addedFoods.size(); i++) {
				foodData.add(addedFoods.get(i));
			}
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
		
		
		// Add event listener to the table rows
		
		
		// Check for if we clicked on a custom food, and if we did then show the delete button
		tableviewFoods.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        if(newSelection.getCustom()) {
		        	buttonDelete.setDisable(false);
		        	buttonDelete.setVisible(true);
		        }else {
		        	buttonDelete.setDisable(true);
		        	buttonDelete.setVisible(false);
		        }
		    }
		});
		
		
		
		
		
		
		

		// Setup textfield filter (based off food name)
		textfieldSearch.setPromptText("Search here!");
		textfieldSearch.setOnKeyReleased(keyEvent -> {
			System.out.println("textfield search activated");
			flFoods.setPredicate(
					p -> p.getName().toLowerCase().contains(textfieldSearch.getText().toLowerCase().trim()));
		});

		// Setup spinner to increase quantity >= 1
		spinnerQuantity.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100));

		// TODO(minor bug) Typing over 100 will result in an error NumberFormatException

	}
	
	@FXML
	protected void handleDelete(ActionEvent event) throws IOException {
		
		// Check if button is visible
		if(buttonDelete.isVisible()) {
			// Make sure something is selected
			try {
				Food selectedFood = tableviewFoods.getSelectionModel().getSelectedItem();
				
				// Make sure its a custom food
				if(selectedFood.getCustom()) {
					// Now delete it from the table
					addedFoods.remove(selectedFood);
					foodData.remove(selectedFood);

					update();
				}
				
			}catch(NullPointerException e) {
				
			}
		}
		
	}
	
	private void update() {
		tableviewFoods.refresh();
	}

	@FXML
	protected void handleAddFood(ActionEvent event) throws IOException {
		System.out.println("Add the food to the table behind us!");
		try {
			Food selectedFood = tableviewFoods.getSelectionModel().getSelectedItem();
			System.out.println("We want to add: " + selectedFood.getName());
			System.out.println("The quantity to add is: " + spinnerQuantity.getValue());
			returnFoodData = selectedFood;
			
			buttonAddFood.getScene().getWindow().hide();
		} catch (NullPointerException e) {
			System.out.println("A table row wasn't selected");
		}
	}

	public String getData() {
		return returnFoodData.getName();
	}

	public double getQuantity() {
		return spinnerQuantity.getValue();
	}

	public Food getFood() {
		return returnFoodData;
	}
}