package controllers.food;

import java.io.IOException;
/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import model.Food;

public class EditCustomFoodController extends BaseFoodController implements Initializable {

	// FXML Components
	@FXML
	Spinner<Double> spinnerQuantity;
	@FXML
	Button buttonSave;
	@FXML
	CheckBox checkBoxMacros;
	
	// Controller Variables
	private double quantity = 1;
	private SpinnerValueFactory<Double> defaultFactory;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		defaultFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100);
		spinnerQuantity.setValueFactory(defaultFactory);
	}
	
	@FXML
	protected void handleCheckBoxMacros(ActionEvent event) throws IOException {
		
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
		return new String[] { tfName.getText(), tfAmount.getText(), tfCarbohydrates.getText(),
					tfProteins.getText(), tfFats.getText(), Double.toString(spinnerQuantity.getValue()) };
	}

	public void setSpinnerValue(String value) {
		try {
			defaultFactory.setValue(Double.parseDouble(value));
		}catch(NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	public double getQuantity() {
		quantity = spinnerQuantity.getValue();
		return quantity;
	}


	public void setFood(Food selectedFood) {
		String[] values = selectedFood.getStrValues();
		tfName.setText(values[0]);
		tfAmount.setText(values[1]);
		tfCarbohydrates.setText(values[2]);
		tfProteins.setText(values[3]);
		tfFats.setText(values[4]);
	}
}