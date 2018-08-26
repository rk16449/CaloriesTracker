package controllers.food;

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
import model.Helper;

public class EditCustomFoodController extends BaseFoodController implements Initializable {

	// FXML Components
	@FXML
	private Spinner<Double> spinnerQuantity;
	@FXML
	private Button buttonSave;
	
	// Controller Variables
	private SpinnerValueFactory<Double> defaultFactory;
	private double quantity = 1;

	public void initialize(URL arg0, ResourceBundle arg1) {
		defaultFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100);
		spinnerQuantity.setValueFactory(defaultFactory);
	}

	
	@FXML
	protected void handleSave(ActionEvent event) throws IOException {

		// Make sure we have valid data to create
		if (this.valid()) {
			
			if (!(spinnerQuantity.getValue() >= 1 && spinnerQuantity.getValue() <= 100)) {
				return;
			}
			
			// Make sure to save everything
			System.out.println("We have valid data to work with");
			quantity = spinnerQuantity.getValue();
			
			// Close this window and continue (DietTabController)
			buttonSave.getScene().getWindow().hide();

		} else {
			System.out.println("We don't have valid data to create food");
		}
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