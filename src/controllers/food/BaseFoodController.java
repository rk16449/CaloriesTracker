package controllers.food;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import controllers.BaseController;
import controllers.MainProgramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import model.Food;
import model.Helper;

public class BaseFoodController extends BaseController {
	

	// FXML Components
	@FXML
	protected PieChart pieChartMacros;
	@FXML
	protected TextField tfName, tfAmount, tfCarbohydrates, tfProteins, tfFats;
	
	// Stores TextField references in an ArrayList so we can easily loop through
	protected ArrayList<TextField> rgTFs = new ArrayList<TextField>();
	
	// Object holding values of doubles
	protected Double calories = (double) 0, carbs = (double) 0, protein = (double) 0, fats = (double) 0; 
	// Pie chart data
	protected ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
	protected ArrayList<PieChart.Data> addedSlices = new ArrayList<PieChart.Data>();

	// Hold the food data on the table in text form
	protected ObservableList<Food> foodData = FXCollections.observableArrayList();
	// Hold the objects of foods local memory
	protected ArrayList<Food> addedFoods = new ArrayList<Food>();
	
	protected void setupTextFieldArrayList() {
		rgTFs.addAll(Arrays.asList(tfName, tfAmount, tfCarbohydrates, tfProteins, tfFats));
	}
	
	protected void setupPieChart() {
		// Setup pie chart
		PieChart.Data sliceProteins = new PieChart.Data("Protein", protein);
		PieChart.Data sliceCarbs = new PieChart.Data("Carbohydrates", carbs);
		PieChart.Data sliceFats = new PieChart.Data("Fats", fats);
		
		addedSlices.add(sliceProteins);
		addedSlices.add(sliceCarbs);
		addedSlices.add(sliceFats);
		
		pieChartData.add(sliceProteins);
		pieChartData.add(sliceCarbs);
		pieChartData.add(sliceFats);
		
		pieChartMacros.setData(pieChartData);
		pieChartMacros.setTitle("Daily Macros");
	}

	protected void updatePieChart(LocalDate date) {
		// Update the pie chart with the current day values
		updateTotalValues(date);
		updateGUIPieChart();
	}
	
	protected void updateTotalValues(LocalDate date) {
		protein = (double) 0;
		carbs = (double) 0;
		fats = (double) 0;

		// Add up the total from the foods on the table
		for (int i = 0; i < MainProgramController.getDay(date).getFoods().size(); i++) {
			Food f = MainProgramController.getDay(date).getFoods().get(i);
			protein += f.getProteins();
			carbs += f.getCarbohydrates();
			fats += f.getFats();
		}

		// Calculate calories
		calories = (protein * 4) + (carbs * 4) + (fats * 9);
	}

	protected void updateGUIPieChart() {
		// now update the slices manually (good enough for such small amount of slices)
		addedSlices.get(2).setPieValue(carbs);
		addedSlices.get(0).setPieValue(protein);
		addedSlices.get(1).setPieValue(fats);
	}
	
	/**
	 * Method to make sure the TextField data are valid
	 * @return
	 */
	protected boolean valid() {

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
