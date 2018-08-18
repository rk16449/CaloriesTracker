package controllers;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import model.Goal;

public class GoalsTabController implements Initializable {

	@FXML TextField tfCurrentGoal;
	
	@FXML Button btnMaintainWeight, btnGainWeight, btnLoseWeight;
	
	
	private ArrayList<Goal> goals = new ArrayList<Goal>();
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Create the types of goals
		Goal loseWeight = new Goal("Lose Weight", 0.8);
		Goal maintainWeight = new Goal("Maintain Weight", 1.0);
		Goal gainWeight = new Goal("Gain Weight", 1.2);
		
		goals.addAll(Arrays.asList(loseWeight, maintainWeight, gainWeight));
	}
}