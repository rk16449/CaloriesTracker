package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Food extends Item {

	private int amount;
	private double proteins;
	private double carbohydrates;
	private double fats;
	
	// Calculated from proteins, carbohydrates and fats
	private double calories;
	private double quantity;
	
	public Food(String name, int amount, double[] values) {
		super(name);

		// Needs validation 
		this.amount = amount;
		this.carbohydrates = values[0];
		this.fats = values[1];
		this.proteins = values[2];
		this.calories = (this.proteins*4) + (this.carbohydrates*4) + (this.fats*9);
	}
	

	public void setQuantity(double quantity) {
		this.quantity = quantity;
		
		// Multiply everything else by quantity automatically
		this.amount *= quantity;
		this.calories *= quantity;
		this.carbohydrates *= quantity;
		this.fats *= quantity;
		this.proteins *= quantity;
		
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getProteins() {
		return proteins;
	}

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public double getFats() {
		return fats;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	/*
	 * 
	 * String methods (for viewing on the table, but not their direct values)
	 * 
	 */
	public StringProperty getStrFood() {
		return new SimpleStringProperty(this.getName());
	}
	
	public StringProperty getStrAmount() {
		return new SimpleStringProperty(Integer.toString(amount));
	}
	
	public StringProperty getStrCalories() {
		return new SimpleStringProperty(Double.toString(round(calories, 2)));
	}
	
	public StringProperty getStrCarbs() {
        return new SimpleStringProperty(Double.toString(round(carbohydrates, 2)));
    }
	
	public StringProperty getStrFats() {
        return new SimpleStringProperty(Double.toString(round(fats, 2)));
    }
	
	public StringProperty getStrProts() {
        return new SimpleStringProperty(Double.toString(round(proteins, 2)));
    }
	
	public StringProperty getStrQuantity() {
		return new SimpleStringProperty(Double.toString(quantity));
	}
	
	// Taken from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	// Used to round numbers before they are converted into text above
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
