package controllers;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.Helper;

public class BaseFoodController extends BaseController {
	@FXML
	protected TextField tfName, tfAmount, tfCarbohydrates, tfFats, tfProteins;
	
	/**
	 * Method to make sure the textfield data are valid
	 * @return
	 */
	public boolean valid() {

		// Assume everything is valid
		boolean valid = true;

		// Make sure we have valid data
		if (tfName.getText().isEmpty() || tfName.getText().equals("")) {
			valid = false;
		}

		// Save textfields to an arraylist so we can loop and call same methods
		ArrayList<TextField> tfs = new ArrayList<TextField>();
		tfs.addAll(Arrays.asList(tfAmount, tfCarbohydrates, tfFats, tfProteins));

		// Loop through textfields, make sure they are not empty and they are doubles
		for (int i = 0; i < tfs.size(); i++) {
			if (isTFEmpty(tfs.get(i))) {
				valid = false;
				break;
			} else {
				System.out.println("Textfield wasn't empty so now check if its a number!");
				valid = Helper.isDouble(tfs.get(i).getText());
				if (!valid)
					break;
			}
		}

		return valid;
	}
	
	private boolean isTFEmpty(TextField tf) {
		return tf.getText().isEmpty() || tf.getText().equals("");
	}
}
