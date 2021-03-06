package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Person;

public class ProfileTabController extends BaseController implements Initializable {

	// FXML Components
	@FXML
	TextField tfFirstName, tfLastName, tfAge, tfHeight, tfWeight, tfBodyfat, tfWaist;
	@FXML
	ChoiceBox<String> cbGender;
	@FXML
	Button btnEditProfile, btnMetric, btnImperial;
	@FXML
	ImageView imageViewGender;

	// Reference to TextFields above
	private ArrayList<Node> refTF = new ArrayList<Node>();
	// Used to tell what units we are using
	private String units = "Metric";
	// Used to tell if we need to unlock TextFields or not
	private boolean editMode = false;
	// References to images
	private Image maleGender, femaleGender;

	/**
	 * First method this class runs, sets up the controller
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Update reference to person
		person = Person.getInstance();

		// Load images
		loadGenderImages();

		// Setup values
		tfFirstName.setText(person.getFirstName());
		tfLastName.setText(person.getLastName());
		tfAge.setText(Integer.toString(person.getAge()));
		tfWeight.setText(Double.toString(person.getWeight()));
		tfHeight.setText(Double.toString(person.getHeight()));
		tfBodyfat.setText(Double.toString(person.getBodyfat()));
		tfWaist.setText(Double.toString(person.getWaist()));

		cbGender.setValue(person.getGender());

		// Add Textfields into a reference ArrayList (so we can loop better)
		refTF.addAll(Arrays.asList(tfAge, tfFirstName, tfLastName, tfAge, tfHeight, tfWeight, tfBodyfat, tfWaist,
				btnMetric, btnImperial, cbGender));

		// Set all components disabled
		disableTF(true);

		// Setup button underlines for units
		updateButtonUnderline();

		// Setup ChoiceBox
		setupChoiceBox();
	}

	/**
	 * Sets up the ChoiceBox 'cbGender' with values and it's event listener, which
	 * updates to Person's gender
	 */
	private void setupChoiceBox() {
		cbGender.setItems(FXCollections.observableArrayList("Male", "Female"));

		// Add event listener
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					person.setGender(newValue);

					// Depending on the value we select above, change the image of the gender
					updateImageGender(newValue);
				}
			}
		};
		// Selected Item Changed.
		cbGender.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	/**
	 * Load the images for male and female genders, so the imageViewGender can use
	 * them and switch between either
	 */
	private void loadGenderImages() {
		try {
			maleGender = new Image("file:media/male.png");
			femaleGender = new Image("file:media/female.jpg");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method which updates the imageViewGender FXML component with either
	 * female/male image
	 * 
	 * @param value
	 *            the gender name for image
	 */
	private void updateImageGender(String value) {
		if (value.equals("Male")) {
			imageViewGender.setImage(maleGender);
		} else if (value.equals("Female")) {
			imageViewGender.setImage(femaleGender);
		}
	}

	/**
	 * Updates whether or not the button 'Metric' or 'Imperial' switches to
	 * underline based off the units value
	 */
	private void updateButtonUnderline() {
		// Setup buttons
		if (units.equals("Metric")) {
			btnMetric.setUnderline(true);
			btnImperial.setUnderline(false);
		} else {
			btnMetric.setUnderline(false);
			btnImperial.setUnderline(true);
		}
	}

	/**
	 * Method to disable or enable TextFields
	 * 
	 * @param value
	 *            true if disable TextField
	 */
	private void disableTF(boolean value) {
		// Activate the TextFields based on value
		for (int i = 0; i < refTF.size(); i++) {
			refTF.get(i).setDisable(value);
		}
	}

	/**
	 * FXML button handler for editing the profile values
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleEditProfile(ActionEvent event) throws IOException {

		// Flip
		editMode = !editMode;

		// If we are in edit mode
		if (editMode) {

			System.out.println("Edit mode activated");

			btnEditProfile.setText("Save Changes");

			// Activate TextFields
			disableTF(false);
		} else {
			// Save changes to Profile
			person.setUnits(units);
			person.setFirstName(tfFirstName.getText());
			person.setLastName(tfLastName.getText());
			person.setAge(Integer.parseInt(tfAge.getText()));
			person.setWeight(Double.parseDouble(tfWeight.getText()));
			person.setHeight(Double.parseDouble(tfHeight.getText()));
			person.setGender(cbGender.getValue());
			person.setWaist(Double.parseDouble(tfWaist.getText()));
			person.setBodyfat(Double.parseDouble(tfBodyfat.getText()));

			btnEditProfile.setText("Edit Profile");

			// Disable the TextFields
			disableTF(true);
		}
	}

	/**
	 * FXML button handler for changing to Metric units
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleMetric(ActionEvent event) throws IOException {

		// Change values to metric if we was on Imperial before
		if (units.equals("Imperial")) {
			double metricWeight = Double.parseDouble(tfWeight.getText()) / 2.20462;
			tfWeight.setText(Double.toString(metricWeight));

			double metricHeight = Double.parseDouble(tfHeight.getText()) / 0.0328084;
			tfHeight.setText(Double.toString(metricHeight));

			double metricWaist = Double.parseDouble(tfWaist.getText()) / 0.393701;
			tfWaist.setText(Double.toString(metricWaist));
		}

		units = "Metric";
		updateButtonUnderline();
	}

	/**
	 * FXML button handler for changing to Imperial units
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleImperial(ActionEvent event) throws IOException {

		// Change values to Imperial if we was on Metric before
		if (units.equals("Metric")) {
			double imperialWeight = Double.parseDouble(tfWeight.getText()) * 2.20462;
			tfWeight.setText(Double.toString(imperialWeight));

			double imperialHeight = Double.parseDouble(tfHeight.getText()) * 0.0328084;
			tfHeight.setText(Double.toString(imperialHeight));

			double imperialWaist = Double.parseDouble(tfWaist.getText()) * 0.393701;
			tfWaist.setText(Double.toString(imperialWaist));
		}

		units = "Imperial";
		updateButtonUnderline();
	}
}