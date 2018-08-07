package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

public class SummaryTabController implements Initializable {
	
	@FXML
	private PieChart dailyMacros;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Protein", 40),
                new PieChart.Data("Fats", 20),
                new PieChart.Data("Carbohydrates", 40)
                );
                
		dailyMacros.setData(pieChartData);
		dailyMacros.setTitle("Daily Macros");
	}
}