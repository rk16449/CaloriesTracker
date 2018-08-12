package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Food;

public class DietTabController implements Initializable {

	
	@FXML
	private TableView<Food> tableDiets;
	
	@FXML
    private TableColumn<Food, String> foodsColumn;
    @FXML
    private TableColumn<Food, String> carbsColumn;
    @FXML
    private TableColumn<Food, String> fatsColumn;
    @FXML
    private TableColumn<Food, String> protsColumn;
	
	private ObservableList<Food> foodData = FXCollections.observableArrayList();
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		 // Add some sample data
		foodData.add(new Food("Milk", 100, new double[] {6.7, 5.5, 4}));
		
		

		// Initialize the person table with the two columns.
		foodsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFood());
		carbsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrCarbs());
		fatsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrFats());
		protsColumn.setCellValueFactory(cellData -> cellData.getValue().getStrProts());
		
		
		
		
		// Add observable list data to the table
		tableDiets.setItems(foodData);
	}
}