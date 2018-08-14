package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Helper;
import model.Food;

public class DietTabController implements Initializable {

	@FXML
	private TableView<Food> tableviewEntries;

	@FXML
	private TableColumn<Food, String> foodsColumn;

	@FXML
	private TableColumn<Food, String> amountColumn;

	@FXML
	private TableColumn<Food, String> caloriesColumn;

	@FXML
	private TableColumn<Food, String> carbsColumn;
	@FXML
	private TableColumn<Food, String> fatsColumn;
	@FXML
	private TableColumn<Food, String> protsColumn;

	@FXML
	private TableColumn<Food, String> quantityColumn;

	@FXML
	private PieChart pieChartMacros;

	@FXML
	private Button buttonAddEntry, buttonEdit, buttonDelete;

	@FXML
	private TextField tfCalories, tfCarbs, tfFats, tfProtein;

	// Hold the food data on the table in text form
	private ObservableList<Food> foodData = FXCollections.observableArrayList();
	// Hold the objects of foods
	private ArrayList<Food> addedFoods = new ArrayList<Food>();

	private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	private ArrayList<PieChart.Data> addedSlices = new ArrayList<PieChart.Data>();

	// Object holding values of doubles
	private Double calories = (double) 0, protein = (double) 0, fats = (double) 0, carbs = (double) 0;

	public void initialize(URL arg0, ResourceBundle arg1) {

		// Create dummy starting data
		Food food1 = new Food("Whole Milk", 100, new double[] { 4.70, 3.70, 3.50 });
		food1.setQuantity(3.6);
		Food food2 = new Food("Protein Powder", 30, new double[] { 3.77, 0.2, 23.71 });
		food2.setQuantity(2);

		// add to Arraylist
		addedFoods.add(food1);
		addedFoods.add(food2);

		// Add sample data
		for (int i = 0; i < addedFoods.size(); i++) {
			Food f = addedFoods.get(i);
			// Add up sum total of proteins, carbs, fats
			protein += f.getProteins();
			carbs += f.getCarbohydrates();
			fats += f.getFats();
			foodData.add(f);
		}

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

		// Update the GUI
		update();
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

		System.out.println("total protein: " + protein);
		System.out.println("total carbs: " + carbs);
		System.out.println("total fats: " + fats);
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
			// Get the current selected food before we edit it so we can update its quantity
			// later
			Food selectedFood = tableviewEntries.getSelectionModel().getSelectedItem();

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
			System.out.println("New Quantity is: " + controller.getQuantity());
			selectedFood.setQuantity(controller.getQuantity());
			System.out.println("New Quantity is: " + selectedFood.getQuantity());

			// Refresh the table
			update();

		} catch (Exception e) {
			System.out.println("Couldn't create edit window..?");
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
			controller.setStageAndSetupListeners(stage);

			// showAndWait will block execution until the window closes...
			stage.showAndWait();

			System.out.println("DietTabController: " + controller.getFood().getName());
			boolean found = false;
			// Check if this food already exists in the table, if it does increase its
			// quantity instead
			for (int i = 0; i < addedFoods.size(); i++) {
				// Assumes we don't have foods with exactly the same name.. (try adding id in
				// later)
				if (addedFoods.get(i).getName().equals(controller.getFood().getName())) {
					found = true;
					addedFoods.get(i).setQuantity(addedFoods.get(i).getQuantity() + controller.getQuantity());
					break;
				}
			}

			// Add a new row entry if same food isn't already added
			if (!found) {
				controller.getFood().setQuantity(controller.getQuantity()); // maybe do this automatically on getFood()
				// Add values to the table!
				addedFoods.add(controller.getFood());
				foodData.add(controller.getFood());
			}

			// Update GUI
			update();
		} catch (IOException e) {
			System.out.println("Failed to create a window");
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

			update();
		} catch (NullPointerException e) {
			System.out.println("Couldn't delete item, probably haven't selected anything");
		}
	}
}