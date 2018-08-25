package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Helper;

public class EditCustomFoodController implements Initializable {

	private Stage stage;
	private boolean isValid = false;

	@FXML
	private Spinner<Double> spinnerQuantity;

	@FXML
	private TextField tfName, tfAmount, tfCarbohydrates, tfFats, tfProteins;

	@FXML
	private Button buttonSave;
	
	private SpinnerValueFactory<Double> defaultFactory;
	
	private double quantity = 1;

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		defaultFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100);
		spinnerQuantity.setValueFactory(defaultFactory);
	}

	public void setStageAndSetupListeners(Stage stage) {
		this.stage = stage;
	}

	@FXML
	protected void handleSave(ActionEvent event) throws IOException {

		// Make sure we have valid data to create
		if (this.valid()) {
			// Make sure to save everything
			System.out.println("We have valid data to work with");
			quantity = spinnerQuantity.getValue();
			
			// Close this window and continue (DietTabController)
			buttonSave.getScene().getWindow().hide();

		} else {
			System.out.println("We don't have valid data to create food");
		}
	}


	public boolean valid() {

		// Assume everything is valid
		boolean valid = true;

		// Make sure we have valid data
		if (tfName.getText().isEmpty() || tfName.getText().equals("")) {
			valid = false;
		}

		// Save textfields to an arraylist so we can loop and call same methods
		ArrayList<TextField> tfs = new ArrayList<TextField>();
		tfs.add(tfAmount);
		tfs.add(tfCarbohydrates);
		tfs.add(tfFats);
		tfs.add(tfProteins);

		// Loop through textfields, make sure they are not empty and they are doubles
		for (int i = 0; i < tfs.size(); i++) {
			if (isObjEmpty(tfs.get(i))) {
				valid = false;
				break;
			} else {
				System.out.println("Textfield wasn't empty so now check if its a number!");
				valid = Helper.isDouble(tfs.get(i).getText());
				if (!valid)
					break;
			}
		}

		// Check if we ticked add today and retrieve the quantity
		if (!(spinnerQuantity.getValue() >= 1 && spinnerQuantity.getValue() <= 100)) {
			valid = false;
		}

		return valid;
	}

	private boolean isObjEmpty(TextField tf) {
		return tf.getText().isEmpty() || tf.getText().equals("");
	}


	public String[] getValues() {
		// pre validation (make sure values are correct)
		return new String[] { tfName.getText(), tfAmount.getText(), tfCarbohydrates.getText(), tfFats.getText(),
					tfProteins.getText(), Double.toString(spinnerQuantity.getValue()) };
	}
	
	public void setTextFieldValues(String[] values) {
		
		tfName.setText(values[0]);
		tfAmount.setText(values[1]);
		tfCarbohydrates.setText(values[2]);
		tfFats.setText(values[3]);
		tfProteins.setText(values[4]);
		
		// tfName, tfAmount, tfCarbohydrates, tfFats, tfProteins;
		
	}

	public void setSpinnerValue(String value) {
		try {
			defaultFactory.setValue(Double.parseDouble(value));
		}catch(NumberFormatException e) {
			
		}
	}
	
	public double getQuantity() {
		quantity = spinnerQuantity.getValue();
		return quantity;
	}
}