package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ProfileTabController implements Initializable {
	
	@FXML
	TextField tfFirstName, tfLastName, tfAge, tfHeight, tfWeight, tfBodyfat, tfWaist;
	
	@FXML
	ChoiceBox cbGender;

	@FXML 
	Button btnEditProfile, btnMetric, btnImperial;
	

	// PROFILE TAB
	
	// TODO
	
	// Be able to select gender
	
	// Be able to set age of person
	
	// Be able to set weight of person
	
	// Be able to set height
	
	// Be able to set bodyfat %
	
	// Be able to set body measurements (waist, chest, neck, legs, biceps)
	
	// All this data will then be updated on the database and reflect on the calculators
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	
	@FXML
	protected void handleEditProfile(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	protected void handleMetric(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	protected void handleImperial(ActionEvent event) throws IOException {
		
	}
}