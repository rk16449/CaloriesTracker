package controllers;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Food;

public class CreateFoodController implements Initializable {

	private Stage stage;
	private boolean isValid = false;

	@FXML
	private Spinner<Double> spinnerQuantity;

	@FXML
	private CheckBox checkboxToday;

	@FXML
	private TextField tfName, tfAmount, tfCarbohydrates, tfFats, tfProteins;

	@FXML
	private Button buttonCreate;

	public void initialize(URL arg0, ResourceBundle arg1) {
		spinnerQuantity.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100));
	}

	public void setStageAndSetupListeners(Stage stage) {
		this.stage = stage;
	}

	@FXML
	protected void handleCreate(ActionEvent event) throws IOException {
		
		// Make sure we have valid data to create
		if(this.valid()) {
			// Make sure to save everything
			System.out.println("We have valid data to work with");
			
			// Close this window and continue (DietTabController)
			buttonCreate.getScene().getWindow().hide();
			
		}else {
			System.out.println("We don't have valid data to create food");
		}
	}
	
	private boolean valid() {
		
		// Assume everything is valid
		boolean valid = true;
		
		// Make sure we have valid data
		if(tfName.getText().isEmpty() || tfName.getText().equals("")) {
			valid = false;
		}
		
		
		// Save textfields to an arraylist so we can loop and call same methods
		ArrayList<TextField> tfs = new ArrayList<TextField>();
		tfs.add(tfAmount);
		tfs.add(tfCarbohydrates);
		tfs.add(tfFats);
		tfs.add(tfProteins);
		
		// Loop through textfields, make sure they are not empty and they are doubles
		for(int i=0; i<tfs.size(); i++) {
			if(isObjEmpty(tfs.get(i))) {
				valid = false;
				break;
			}else {
				System.out.println("Textfield wasn't empty so now check if its a number!");
				valid = isDouble(tfs.get(i).getText());
				if(!valid) break;
			}
		}
		
		
		// Check if we ticked add today and retrieve the quantity
		if(checkboxToday.isSelected()) {
			if(!(spinnerQuantity.getValue() >= 1 &&  spinnerQuantity.getValue() <= 100)) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	private boolean isObjEmpty(TextField tf) {
		return tf.getText().isEmpty() || tf.getText().equals("");
	}
	
	private boolean isDouble(String value) {
		boolean valid = true;
		// Check if its numerical
		try
		{
			Double.parseDouble(value);
		}
		catch(NumberFormatException e)
		{
			//not a double
			valid = false;
		}
		return valid;
	}
	
	public String[] getValues() {
		// pre validation (make sure values are correct)
		if(!checkboxToday.isSelected()) {
			return new String[] {tfName.getText(), tfAmount.getText(), tfCarbohydrates.getText(), tfFats.getText(), tfProteins.getText(), "-1"};
		}else {
			return new String[] {tfName.getText(), tfAmount.getText(), tfCarbohydrates.getText(), tfFats.getText(), tfProteins.getText(), Double.toString(spinnerQuantity.getValue())};
		}
	}
	
	@FXML
	protected void handleCheckBoxToday(ActionEvent event) throws IOException{
		System.out.println("Checkbox pressed");
		spinnerQuantity.setDisable(!spinnerQuantity.isDisable());
	}
}