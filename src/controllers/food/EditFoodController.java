package controllers.food;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Food;

public class EditFoodController implements Initializable {
	
	// FXML Components
	@FXML 
	private Button buttonSave;
	@FXML
	private TextField textfieldQuantity;
	
	private double quantity = 1;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		textfieldQuantity.setText("1");
	}
	
	@FXML
	protected void handleSaveButton(ActionEvent event) throws IOException {
		try {
			quantity = Double.parseDouble(textfieldQuantity.getText());
			buttonSave.getScene().getWindow().hide();
		} catch (NullPointerException e) {
			System.out.println("A table row wasn't selected");
		}
	}
	
	public void setTextFieldValue(String value) {
		System.out.println("Set new value here!!!");
		textfieldQuantity.setText(value);
	}
	
	public double getQuantity() {
		quantity = Double.parseDouble(textfieldQuantity.getText());
		return quantity;
	}
}