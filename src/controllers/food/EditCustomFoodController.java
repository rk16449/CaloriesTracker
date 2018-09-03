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
	
	// Stores original macro values
	private String[] originalMacros = new String[5];

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		defaultFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 100);
		spinnerQuantity.setValueFactory(defaultFactory);
		
		
		// Sets up an ArrayList for the TextFields
		setupTextFieldArrayList();
	}
	
	@FXML
	protected void handleCheckBoxMacros(ActionEvent event) throws IOException {
		System.out.println("Handle check box pressed!");
		
		// If its selected, disable quantity and enable TextFields
		if(checkBoxMacros.isSelected()) {
			editMacros(true);
		}else {
			editMacros(false);
			
			// Reset macros back to original values
			resetMacros();
		}
	}
	
	// Used when the DietController needs to decide if we're changing quantity or macros
	public boolean getEditMacros() {
		return checkBoxMacros.isSelected();
	}
	
	private void resetMacros() {
		for(int i=0; i<super.rgTFs.size(); i++) {
			rgTFs.get(i).setText(originalMacros[i]);
		}
	}
	
	private void editMacros(boolean value) {
		spinnerQuantity.setDisable(value);
		spinnerQuantity.getValueFactory().setValue(1.0);
		
		tfName.setDisable(!value);
		tfAmount.setDisable(!value);
		tfCarbohydrates.setDisable(!value);
		tfProteins.setDisable(!value);
		tfFats.setDisable(!value);
		
		tfName.setEditable(value);
		tfAmount.setEditable(value);
		tfCarbohydrates.setEditable(value);
		tfProteins.setEditable(value);
		tfFats.setEditable(value);
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

		// Loop through TextFields ArrayList (inherited)
		for(int i=0; i<super.rgTFs.size(); i++) {
			// Save its value
			originalMacros[i] = values[i];
			// Set it into the TextField
			rgTFs.get(i).setText(values[i]);
		}
	}
}