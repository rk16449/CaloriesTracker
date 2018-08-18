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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GoalsTabController implements Initializable {

	@FXML TextField tfCurrentGoal;
	
	@FXML Button btnMaintainWeight, btnGainWeight, btnLoseWeight;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}