package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.Helper;

public class Food extends Item {

	private int amount;
	private double proteins;
	private double carbohydrates;
	private double fats;
	
	// Calculated from proteins, carbohydrates and fats
	private double calories;
	private double quantity;
	
	// static values
	private final int ogAmount;
	private final double ogCalories, ogProteins, ogCarbohydrates, ogFats;
	
	public Food(String name, int amount, double[] values) {
		super(name);

		// Needs validation 
		this.amount = amount;
		this.carbohydrates = values[0];
		this.fats = values[1];
		this.proteins = values[2];
		this.calories = (this.proteins*4) + (this.carbohydrates*4) + (this.fats*9);
		
		// Store static values (that will never change)
		ogAmount = amount;
		ogCarbohydrates = values[0];
		ogFats = values[1];
		ogProteins = values[2];
		ogCalories = (proteins*4) + (carbohydrates*4) + (fats*9);
	}
	

	public void setQuantity(double quantity) {
		this.quantity = quantity;
		
		// Multiply everything else by quantity automatically
		this.amount = (int) (ogAmount * quantity);
		this.calories = ogCalories * quantity;
		this.carbohydrates = ogCarbohydrates * quantity;
		this.fats = ogFats * quantity;
		this.proteins = ogProteins * quantity;
		
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
		return new SimpleStringProperty(Double.toString(Helper.round(calories, 2)));
	}
	
	public StringProperty getStrCarbs() {
        return new SimpleStringProperty(Double.toString(Helper.round(carbohydrates, 2)));
    }
	
	public StringProperty getStrFats() {
        return new SimpleStringProperty(Double.toString(Helper.round(fats, 2)));
    }
	
	public StringProperty getStrProts() {
        return new SimpleStringProperty(Double.toString(Helper.round(proteins, 2)));
    }
	
	public StringProperty getStrQuantity() {
		return new SimpleStringProperty(Double.toString(quantity));
	}
	
	


	public double getQuantity() {
		return this.quantity;
	}
}
