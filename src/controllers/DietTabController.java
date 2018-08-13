package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Food;

public class DietTabController implements Initializable {

	@FXML
	private TableView<Food> tableDiets;

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
	private Button addEntryButton;

	// Hold the food data on the table in text form
	private ObservableList<Food> foodData = FXCollections.observableArrayList();
	// Hold the objects of foods
	private ArrayList<Food> addedFoods = new ArrayList<Food>();
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Food food1 = new Food("Whole Milk", 100, new double[] { 4.70, 3.70, 3.50 });
		food1.setQuantity(3.6);
		
		Food food2 = new Food("Protein Powder", 30, new double[] { 3.77, 0.2, 23.71 });
		food2.setQuantity(2);
		
		// add to arraylist
		addedFoods.add(food1);
		addedFoods.add(food2);
		
		double protein = 0, fats = 0, carbs = 0;
		
		// Add sample data
		for(int i=0; i<addedFoods.size(); i++) {
			
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
		tableDiets.setItems(foodData);
		
		

		// Setup pie chart
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Protein", protein),
                new PieChart.Data("Fats", fats),
                new PieChart.Data("Carbohydrates", carbs)
                );
                
		pieChartMacros.setData(pieChartData);
		pieChartMacros.setTitle("Daily Macros");
	}
	
	@FXML
	protected void handleAddEntry(ActionEvent event) throws IOException {
		// Open a window which has a search bar to search for foods on the database
		System.out.println("Create add food window here");
		
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader();
	        fxmlLoader.setLocation(getClass().getResource("/view/dietTabAddFoodWindow.fxml"));
	        /* 
	         * if "fx:controller" is not set in fxml
	         * fxmlLoader.setController(NewWindowController);
	         */
	        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
	        Stage stage = new Stage();
	        
	        Stage parent = (Stage) addEntryButton.getScene().getWindow();
	        
	        stage.initOwner(parent);                       
	        stage.initModality(Modality.WINDOW_MODAL); 
	        
	        stage.setTitle("Add Entry");
	        stage.setScene(scene);
	        stage.show();
	        
	        AddFoodController controller = fxmlLoader.<AddFoodController>getController();
	        
	        stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent we) {
	            	
	            	int returnedQuantity = controller.getQuantity();
	                Food returnedFood = controller.getFood();
	                System.out.println(returnedFood.getName());
	                // static class can be used aswell -> System.out.println(Context.getMyString());
	                
	                // Add it to the 
	                addedFoods.add(returnedFood);
	            }
	        });
	        
	        
	    } catch (IOException e) {
	        System.out.println("failed to create a window");
	    }
		
		// There should also be a listbox to choose from the certain foods
	}

}