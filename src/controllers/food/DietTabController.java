package controllers.food;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controllers.MainProgramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Day;
import model.Food;
import model.Helper;

public class DietTabController extends BaseFoodController implements Initializable {

	// FXML Components
	@FXML
	TableView<Food> tvEntries;
	@FXML
	TableColumn<Food, String> tcFoods, tcAmount, tcCalories, tcCarbs, tcFats, tcProts,
			tcQuantity;
	@FXML
	Button btnAddEntry, btnEdit, btnDelete, btnCustom;
	@FXML
	TextField tfCalories, tfCarbs, tfFats, tfProtein;
	@FXML
	DatePicker dpDate;

	// Used to check the current loaded date and day
	private static LocalDate currentDate;
	private static Day currentDay;

	/**
	 * First method that will run and setup the Controller
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Setup the date to today and in turn only load that days data
		setupDay();
		setupTable();
		setupPieChart();
		setupDatePicker();

		// Load the foods into an ArrayList
		loadAddedFoods();
		// Loads the foods into the GUI table
		loadTableFoods();
		// Update the values of total calories, carbs, fats, protein etc
		updateTotalValues(LocalDate.now());
		// Update the GUI
		update();
	}

	/**
	 * Sets the current day based off the date picker value
	 */
	private void setupDay() {
		dpDate.setValue(LocalDate.now());
		currentDate = dpDate.getValue();
		currentDay = MainProgramController.getDay(currentDate);
	}

	/**
	 * Sets up the FXML table view with the Food string getters
	 */
	private void setupTable() {
		// Initialize the person table with the two columns.
		tcFoods.setCellValueFactory(cellData -> cellData.getValue().getStrName());
		tcAmount.setCellValueFactory(cellData -> cellData.getValue().getStrAmount());
		tcCalories.setCellValueFactory(cellData -> cellData.getValue().getStrCalories());
		tcCarbs.setCellValueFactory(cellData -> cellData.getValue().getStrCarbs());
		tcFats.setCellValueFactory(cellData -> cellData.getValue().getStrFats());
		tcProts.setCellValueFactory(cellData -> cellData.getValue().getStrProts());
		tcQuantity.setCellValueFactory(cellData -> cellData.getValue().getStrQuantity());

		// Add observable list data to the table
		tvEntries.setItems(foodData);
		
		// Disable buttons on start
		showBtns(false);
		
		
		// Setup a table handler which will show Edit/Delete if we select a food
		tvEntries.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	showBtns(true);
		    }else {
		    	// Hide the buttons
		    	showBtns(false);
		    }
		});
	}
	
	private void showBtns(boolean value) {
		btnDelete.setDisable(!value);
    	btnDelete.setVisible(value);
    	btnEdit.setDisable(!value);
    	btnEdit.setVisible(value);
	}

	/**
	 * Sets up the FXML Date Picker and creates a listener for whenever we change values
	 * if we do change values we will change the currentDay and update the table values
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setupDatePicker() {
		dpDate.setOnAction(new EventHandler() {
			public void handle(Event t) {
				LocalDate date = dpDate.getValue();
				
				System.out.println("Selecting a day: of value: " + date.toString());

				// Update the currentDay
				currentDay = MainProgramController.getDay(date);

				// Clear the table
				addedFoods.clear();
				foodData.clear();
				
				// Clear the PieChart
				

				// Load the food into the table
				loadAddedFoods();
				loadTableFoods();
				updateTotalValues(date);
				update();
			}
		});
	}

	/**
	 * Called to load the current days food values into the table
	 */
	private void loadAddedFoods() {
		// loop through the currentDay food
		for (int i = 0; i < currentDay.getFoods().size(); i++) {
			addedFoods.add(currentDay.getFoods().get(i));
		}
	}

	/**
	 * loads the local memory ArrayList into the GUI table ArrayList
	 */
	private void loadTableFoods() {
		// Calculate total data
		for (int i = 0; i < addedFoods.size(); i++) {
			foodData.add(addedFoods.get(i));
		}
	}

	/**
	 * Calls whenever we change a date or switching between tabs, updates the GUI components
	 */
	private void update() {
		updatePieChart(dpDate.getValue());
		updateGUIPieChart();
		updateTotalValues(dpDate.getValue());
		updateGUIMacrosInfo();
		tvEntries.refresh();
	}


	/**
	 * Updates the TextFields of Macros 
	 */
	private void updateGUIMacrosInfo() {
		tfCalories.setText(Double.toString(Helper.round(calories, 2)));
		tfCarbs.setText(Double.toString(Helper.round(carbs, 2)));
		tfFats.setText(Double.toString(Helper.round(fats, 2)));
		tfProtein.setText(Double.toString(Helper.round(protein, 2)));
	}

	/**
	 * FXML button handler for Editing Foods on the TableView
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleEdit(ActionEvent event) throws IOException {
		System.out.println("Create edit food window here");

		try {
			// Get the current selected food
			Food selectedFood = tvEntries.getSelectionModel().getSelectedItem();

			// Check if this is a custom food
			if (selectedFood.getCustom()) {
				handleCustomEdit(selectedFood);
			} else {
				handleNormalEdit(selectedFood);
			}

			// Refresh the table
			update();

		} catch (Exception e) {
			System.out.println("Couldn't create edit window..?");
		}
	}

	/**
	 * method which is called inside the FXML button handler 'handleEdit'
	 * opens a custom edit window to change its values
	 * @param selectedFood
	 * @throws IOException
	 */
	private void handleCustomEdit(Food selectedFood) throws IOException {
		// Create window
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/view/dietTabEditCustomFoodWindow.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 355, 270);
		Stage stage = new Stage();
		Stage parent = (Stage) btnEdit.getScene().getWindow();
		stage.initOwner(parent);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Edit Food");
		stage.setScene(scene);
		// Controller access
		EditCustomFoodController controller = fxmlLoader.<EditCustomFoodController>getController();
		controller.setSpinnerValue(Double.toString(selectedFood.getQuantity()));

		// Pass food values into TextFields
		controller.setFood(selectedFood);
		// showAndWait will block execution until the window closes...
		stage.showAndWait();

		// Get the updated values and set them into the selected food
		String[] retVals = controller.getValues();

		// Get values back from controller and update them into the food object
		double doubleVals[] = {Double.parseDouble(retVals[1]), Double.parseDouble(retVals[2]), Double.parseDouble(retVals[3]), Double.parseDouble(retVals[4]),  controller.getQuantity()};
		
		Food changedFood = new Food(retVals[0], doubleVals);
		
		// We selected to only update Macros and not quantity
		if(controller.getEditMacros()) {
			// Update reference of selectedFood
			selectedFood.setFood(changedFood, selectedFood.getQuantity());
		}else {
			// Update reference of selectedFood
			selectedFood.setFood(changedFood, controller.getQuantity());
		}
		
		
	}

	/**
	 * method which is called inside the FXML button handler 'handleEdit'
	 * opens a normal edit window for changing quantity of foods
	 * @param selectedFood
	 * @throws IOException
	 */
	private void handleNormalEdit(Food selectedFood) throws IOException {
		// Create window
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/view/dietTabEditFoodWindow.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 352, 156);
		Stage stage = new Stage();
		Stage parent = (Stage) btnEdit.getScene().getWindow();
		stage.initOwner(parent);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Edit Food");
		stage.setScene(scene);
		// Controller access
		EditFoodController controller = fxmlLoader.<EditFoodController>getController();
		controller.setTextFieldValue(Double.toString(selectedFood.getQuantity()));
		
		// showAndWait will block execution until the window closes...
		stage.showAndWait();

		// Update the selectedFood Quantity
		selectedFood.setQuantity(controller.getQuantity());
	}

	/**
	 * FXML button handler for adding custom foods to the TableView
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleCustom(ActionEvent event) throws IOException {
		try {
			// Create window
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/dietTabCustomFoodWindow.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 355, 275);
			Stage stage = new Stage();
			Stage parent = (Stage) btnCustom.getScene().getWindow();
			stage.initOwner(parent);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Create Custom Food");
			stage.setScene(scene);

			// Controller access
			CustomFoodController controller = fxmlLoader.<CustomFoodController>getController();

			// showAndWait will block execution until the window closes...
			stage.showAndWait();

			// continue with the controller
			addCustom(controller);
		} catch (Exception e) {
			System.out.println("Couldn't make create food window..?");
		}
	}

	/**
	 * method which is called inside the FXML button handler 'handleCustom'
	 * gets the values from the CustomFoodController and adds it to the table
	 * @param controller
	 * @throws Exception
	 */
	private void addCustom(CustomFoodController controller) throws Exception {
		// Add values to the local database/memory table
		if (controller.valid()) {
			// Copy a Food from controller
			Food newFood = new Food(controller.getFood());

			// Add it to DietTabController table (if we selected to)
			if (controller.addToTable()) {
				// Create a copy of newFood and also change its quantity
				Food tableFood = new Food(newFood, controller.getQuantity());

				// Add to the entries table
				addedFoods.add(tableFood);
				foodData.add(tableFood);
				currentDay.addFood(tableFood);
			}
			
			// Add to the AddFoodController table
			AddFoodController.addedFoods.add(newFood);
			AddFoodController.foodData.add(newFood);
			update();
		}
	}

	/**
	 * FXML button handler for adding normal foods to the table
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleAddEntry(ActionEvent event) throws IOException {
		// Open a window which has a search bar to search for foods on the database
		System.out.println("Create add food window here");

		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/dietTabAddFoodWindow.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 400);
			Stage stage = new Stage();
			Stage parent = (Stage) btnAddEntry.getScene().getWindow();
			stage.initOwner(parent);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Add Entry");
			stage.setScene(scene);

			// Setup controller
			AddFoodController controller = fxmlLoader.<AddFoodController>getController();

			// showAndWait will block execution until the window closes...
			stage.showAndWait();

			// continue on adding entry with controller
			addEntry(controller);

		} catch (IOException e) {
			System.out.println("Failed to create a window");
		}
	}

	/**
	 * method which is called inside the FXML method 
	 * @param controller
	 */
	private void addEntry(AddFoodController controller) {
		try {
			System.out.println("DietTabController: " + controller.getFood().getName());
			boolean found = false;
			// Check if this food already exists in the table, if it does increase its
			// quantity instead
			
			found = Day.updateQuantity(currentDay, controller, found);
			

			// Add a new row entry if same food isn't already added
			if (!found) {
				System.out.println("No Food was found, creating a new entry here!");
				
				// Copy the object
				Food newFood = new Food(controller.getFood(), controller.getQuantity());
				
				// Add values to the table!
				addedFoods.add(newFood);
				foodData.add(newFood);
				currentDay.addFood(newFood);
			}

			// Update GUI
			update();
		} catch (NullPointerException e) {
			System.out.println("Nullpointerexception, probably because we hit the X");
		}
	}

	/**
	 * FXML button handler for deleting foods off the table
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleDeleteEntry(ActionEvent event) throws IOException {
		// Make sure we selected an entry on the table
		try {
			// Get the current selected food before we edit it so we can update its quantity
			// later
			Food selectedFood = tvEntries.getSelectionModel().getSelectedItem();

			addedFoods.remove(selectedFood);
			foodData.remove(selectedFood);
			currentDay.deleteFood(selectedFood);

			update();
		} catch (NullPointerException e) {
			System.out.println("Couldn't delete item, probably haven't selected anything");
		}
	}
}