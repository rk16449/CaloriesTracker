package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import main.Helper;
import model.Day;
import model.Food;

public class DietTabController implements Initializable {

	@FXML
	private TableView<Food> tableviewEntries;

	@FXML
	private TableColumn<Food, String> foodsColumn, amountColumn, caloriesColumn, carbsColumn, fatsColumn, protsColumn,
			quantityColumn;

	@FXML
	private PieChart pieChartMacros;

	@FXML
	private Button buttonAddEntry, buttonEdit, buttonDelete, buttonCustom;

	@FXML
	private TextField tfCalories, tfCarbs, tfFats, tfProtein;

	@FXML
	private DatePicker datePickerDiet;

	
	// Object holding values of doubles
	private Double calories = (double) 0, protein = (double) 0, fats = (double) 0, carbs = (double) 0;

	// Pie chart data
	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	private ArrayList<PieChart.Data> addedSlices = new ArrayList<PieChart.Data>();

	// Hold the food data on the table in text form
	private ObservableList<Food> foodData = FXCollections.observableArrayList();
	// Hold the objects of foods local memory
	private ArrayList<Food> addedFoods = new ArrayList<Food>();

	
	// Used to check the current loaded date and day
	private static LocalDate currentDate;
	private static Day currentDay;



	public void initialize(URL arg0, ResourceBundle arg1) {

		// Setup the date to today and in turn only load that days data
		setupDay();
		setupTable();
		setupPieChart();
		setupDatePicker();

		// Load the foods into an arraylist
		loadAddedFoods();
		// Loads the foods into the GUI table
		loadTableFoods();
		// Update the values of total calories, carbs, fats, protein etc
		updateTotalValues();
		// Update the GUI
		update();
	}

	private void setupDay() {
		datePickerDiet.setValue(LocalDate.now());
		currentDate = datePickerDiet.getValue();
		currentDay = MainProgramController.getDay(currentDate);
	}

	private void setupTable() {
		// Initialize the person table with the two columns.
		foodsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFood());
		amountColumn.setCellValueFactory(cellData -> cellData.getValue().getStrAmount());
		caloriesColumn.setCellValueFactory(cellData -> cellData.getValue().getStrCalories());
		carbsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrCarbs());
		fatsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFats());
		protsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrProts());
		quantityColumn.setCellValueFactory(cellData -> cellData.getValue().getStrQuantity());

		// Add observable list data to the table
		tableviewEntries.setItems(foodData);
	}

	private void setupPieChart() {
		// Setup pie chart
		PieChart.Data sliceProteins = new PieChart.Data("Protein", protein);
		PieChart.Data sliceFats = new PieChart.Data("Fats", fats);
		PieChart.Data sliceCarbs = new PieChart.Data("Carbohydrates", carbs);
		addedSlices.add(sliceProteins);
		addedSlices.add(sliceFats);
		addedSlices.add(sliceCarbs);
		pieChartData.add(sliceProteins);
		pieChartData.add(sliceFats);
		pieChartData.add(sliceCarbs);
		pieChartMacros.setData(pieChartData);
		pieChartMacros.setTitle("Daily Macros");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setupDatePicker() {
		datePickerDiet.setOnAction(new EventHandler() {
			public void handle(Event t) {
				LocalDate date = datePickerDiet.getValue();

				// Update the currentDay
				currentDay = MainProgramController.getDay(date);

				// Clear the table
				addedFoods.clear();
				foodData.clear();

				// Load the food into the table
				loadAddedFoods();
				loadTableFoods();
				updateTotalValues();
				update();
			}
		});
	}

	private void loadAddedFoods() {
		// loop through the currentDay food
		for (int i = 0; i < currentDay.getFoods().size(); i++) {
			Food f = currentDay.getFoods().get(i);
			addedFoods.add(f);
		}
	}


	/**
	 * loads the local memory arraylist into the GUI table arraylist
	 */
	private void loadTableFoods() {
		// Calculate total data
		for (int i = 0; i < addedFoods.size(); i++) {
			Food f = addedFoods.get(i);
			foodData.add(f);
		}
	}

	private void update() {
		updateTotalValues();
		updateGUIPieChart();
		updateGUIMacrosInfo();
		tableviewEntries.refresh();
	}

	private void updateTotalValues() {
		protein = (double) 0;
		carbs = (double) 0;
		fats = (double) 0;

		// Add up the total from the foods on the table
		for (int i = 0; i < foodData.size(); i++) {
			Food f = foodData.get(i);
			protein += f.getProteins();
			carbs += f.getCarbohydrates();
			fats += f.getFats();
		}

		// Calculate calories
		calories = (protein * 4) + (carbs * 4) + (fats * 9);
	}

	private void updateGUIPieChart() {
		// now update the slices manually (good enough for such small amount of slices)
		addedSlices.get(0).setPieValue(protein);
		addedSlices.get(1).setPieValue(fats);
		addedSlices.get(2).setPieValue(carbs);
	}

	private void updateGUIMacrosInfo() {
		tfCalories.setText(Double.toString(Helper.round(calories, 2)));
		tfCarbs.setText(Double.toString(Helper.round(carbs, 2)));
		tfFats.setText(Double.toString(Helper.round(fats, 2)));
		tfProtein.setText(Double.toString(Helper.round(protein, 2)));
	}

	@FXML
	protected void handleEdit(ActionEvent event) throws IOException {
		System.out.println("Create edit food window here");

		try {
			// Get the current selected food
			Food selectedFood = tableviewEntries.getSelectionModel().getSelectedItem();

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

	private void handleCustomEdit(Food selectedFood) throws IOException {
		// Create window
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/view/dietTabEditCustomFoodWindow.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 355, 270);
		Stage stage = new Stage();
		Stage parent = (Stage) buttonEdit.getScene().getWindow();
		stage.initOwner(parent);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Edit Food");
		stage.setScene(scene);
		// Controller access
		EditCustomFoodController controller = fxmlLoader.<EditCustomFoodController>getController();
		controller.setSpinnerValue(Double.toString(selectedFood.getQuantity()));

		// pass food values into textfield
		String[] values = new String[] {
				// Remove (custom)
				selectedFood.getName(), Double.toString(selectedFood.getAmount()),
				Double.toString(selectedFood.getCarbohydrates()), Double.toString(selectedFood.getFats()),
				Double.toString(selectedFood.getProteins()) };

		controller.setTextFieldValues(values);
		controller.setStageAndSetupListeners(stage);
		// showAndWait will block execution until the window closes...
		stage.showAndWait();

		// get the updated values and set them into the selected food
		String[] retVals = controller.getValues();

		// Get values back from controller and update them into the food object
		selectedFood.setName(retVals[0]);
		selectedFood.setAmount(Double.parseDouble(retVals[1]));
		selectedFood.setCarbohydrates(Double.parseDouble(retVals[2]));
		selectedFood.setFats(Double.parseDouble(retVals[3]));
		selectedFood.setProteins(Double.parseDouble(retVals[4]));

		// Quantity last value that gets updated because we need new values above
		selectedFood.setQuantity(controller.getQuantity());
	}

	private void handleNormalEdit(Food selectedFood) throws IOException {
		// Create window
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/view/dietTabEditFoodWindow.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 352, 156);
		Stage stage = new Stage();
		Stage parent = (Stage) buttonEdit.getScene().getWindow();
		stage.initOwner(parent);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Edit Food");
		stage.setScene(scene);
		// Controller access
		EditFoodController controller = fxmlLoader.<EditFoodController>getController();
		controller.setTextFieldValue(Double.toString(selectedFood.getQuantity()));
		controller.setStageAndSetupListeners(stage);
		// showAndWait will block execution until the window closes...
		stage.showAndWait();

		// Update the selectedFood Quantity
		selectedFood.setQuantity(controller.getQuantity());
	}

	@FXML
	protected void handleCustom(ActionEvent event) throws IOException {
		try {
			// Create window
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/dietTabCreateFoodWindow.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 355, 275);
			Stage stage = new Stage();
			Stage parent = (Stage) buttonCustom.getScene().getWindow();
			stage.initOwner(parent);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setTitle("Create Custom Food");
			stage.setScene(scene);

			// Controller access
			CreateFoodController controller = fxmlLoader.<CreateFoodController>getController();
			controller.setStageAndSetupListeners(stage);

			// showAndWait will block execution until the window closes...
			stage.showAndWait();

			// continue with the controller
			addCustom(controller);
		} catch (Exception e) {
			System.out.println("Couldn't make create food window..?");
		}
	}

	private void addCustom(CreateFoodController controller) {
		// Add values to the local database/memory table
		if (controller.valid()) {
			String[] rgFoodData = controller.getValues();

			// Debug values
			for (int i = 0; i < rgFoodData.length; i++) {
				System.out.println("----> " + rgFoodData[i]);
			}

			// Create the food
			String name = rgFoodData[0];
			double amount = Double.parseDouble(rgFoodData[1]);
			double[] values = { Double.parseDouble(rgFoodData[2]), Double.parseDouble(rgFoodData[3]),
					Double.parseDouble(rgFoodData[4]) };
			double quant = Double.parseDouble(rgFoodData[5]);

			// Create it in object form
			Food newFood = new Food(name, amount, values);
			newFood.setQuantity(quant);
			// Make sure we apply it as a custom food
			newFood.setCustom(true);

			// Add it to daily the table (if we selected to)
			if (controller.addToTable()) {
				addedFoods.add(newFood);
				foodData.add(newFood);
			}

			// Add to the local memory arraylist, the table gui and the currentDay arraylist
			AddFoodController.addedFoods.add(newFood);
			AddFoodController.foodData.add(newFood);
			currentDay.addFood(newFood);
			update();
		}
	}

	@FXML
	protected void handleAddEntry(ActionEvent event) throws IOException {
		// Open a window which has a search bar to search for foods on the database
		System.out.println("Create add food window here");

		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/view/dietTabAddFoodWindow.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 600, 400);
			Stage stage = new Stage();
			Stage parent = (Stage) buttonAddEntry.getScene().getWindow();
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

	private void addEntry(AddFoodController controller) {
		try {
			System.out.println("DietTabController: " + controller.getFood().getName());
			boolean found = false;
			// Check if this food already exists in the table, if it does increase its
			// quantity instead
			
			
			for (int i = 0; i < currentDay.getFoods().size(); i++) {
				// Assumes we don't have foods with exactly the same name.. (try adding id in
				// later)
				if (currentDay.getFoods().get(i).getName().equals(controller.getFood().getName())) {
					found = true;
					
					System.out.println("Updating quantity on addEntry");
					
					currentDay.getFoods().get(i).setQuantity(currentDay.getFoods().get(i).getQuantity() + controller.getQuantity());
					break;
				}
			}

			// Add a new row entry if same food isn't already added
			if (!found) {
				System.out.println("No Food was found, creating a new entry here!");
				
				// Copy the object
				Food newFood = new Food(controller.getFood().getName(), controller.getFood());
				newFood.setQuantity(controller.getQuantity()); // maybe do this automatically on getFood()

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

	@FXML
	protected void handleDeleteEntry(ActionEvent event) throws IOException {
		// Make sure we selected an entry on the table
		try {
			// Get the current selected food before we edit it so we can update its quantity
			// later
			Food selectedFood = tableviewEntries.getSelectionModel().getSelectedItem();

			addedFoods.remove(selectedFood);
			foodData.remove(selectedFood);
			currentDay.deleteFood(selectedFood);

			update();
		} catch (NullPointerException e) {
			System.out.println("Couldn't delete item, probably haven't selected anything");
		}
	}
}