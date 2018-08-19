package controllers;

import java.io.IOException;
/* Import java, javafx, mainPackage */
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Activity;
import model.Goal;
import model.Person;

public class GoalsTabController implements Initializable {

	@FXML
	TextField tfCurrentGoal, tfTDEE, tfBMR, tfCaloricReqs;

	@FXML
	Button btnMaintainWeight, btnGainWeight, btnLoseWeight;

	@FXML
	ChoiceBox<Activity> cbActivityLevel;

	private Double BMR;
	private Goal currentGoal;
	private Activity currentActivity;

	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private ArrayList<Goal> goals = new ArrayList<Goal>();

	private void calculateBMR() {
		Person p = Person.getInstance();

		if (p.getGender().equals("Male")) {
			// Formula - BMR = 66 + (13.75 x weight in kg) + (5 x height in cm) – (6.8 x age
			// in yrs)
			BMR = 66 + (13.75 * p.getWeight()) + (5 * p.getHeight()) - (6.8 * p.getAge());
		} else if (p.getGender().equals("Female")) {
			// Formula - BMR = 655 + (9.6 x weight in kg) + (1.8 x height in cm) – (4.7 x
			// age in Yrs)
			BMR = 655 + (9.6 * p.getWeight()) + (1.8 * p.getHeight()) - (4.7 * p.getAge());
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		setupActivityLevels();
		setupGoals();
	}

	private void setupActivityLevels() {
		Activity sedentary = new Activity("Sedentary", 1.2);
		Activity lightlyActive = new Activity("Lightly Active", 1.375);
		Activity moderateActive = new Activity("Moderately Active", 1.55);
		Activity veryActive = new Activity("Very Active", 1.725);
		Activity extremelyActive = new Activity("Extremely Active", 1.9);

		activities.addAll(Arrays.asList(sedentary, lightlyActive, moderateActive, veryActive, extremelyActive));

		// Fill choicebox with Activities
		cbActivityLevel.setItems(FXCollections.observableArrayList(activities));

		// Add event listener
		ChangeListener<Activity> changeListener = new ChangeListener<Activity>() {
			@Override
			public void changed(ObservableValue<? extends Activity> observable, //
					Activity oldValue, Activity newValue) {
				if (newValue != null) {
					System.out.println("Activity: " + newValue);
					currentActivity = newValue;
				}
			}
		};
		// Selected Item Changed.
		cbActivityLevel.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	private void setupGoals() {
		// Create the types of goals
		Goal loseWeight = new Goal("Lose Weight", 0.8);
		Goal maintainWeight = new Goal("Maintain Weight", 1.0);
		Goal gainWeight = new Goal("Gain Weight", 1.2);

		goals.addAll(Arrays.asList(loseWeight, maintainWeight, gainWeight));
	}

	private Goal getGoal(String name) {
		for (int i = 0; i < goals.size(); i++) {
			if (goals.get(i).getName().equals(name)) {
				return goals.get(i);
			}
		}
		return null;
	}

	private void updateGoal(String name) {
		currentGoal = getGoal(name);
		tfCurrentGoal.setText(currentGoal.getName());

		calculateBMR();
		tfBMR.setText(BMR.toString());
	}

	@FXML
	protected void handleLoseWeight(ActionEvent event) throws IOException {
		updateGoal("Lose Weight");
	}

	@FXML
	protected void handleGainWeight(ActionEvent event) throws IOException {
		updateGoal("Gain Weight");
	}

	@FXML
	protected void handleMaintainWeight(ActionEvent event) throws IOException {
		updateGoal("Maintain Weight");
	}
}