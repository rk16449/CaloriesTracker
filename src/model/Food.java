package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Food extends Item {

	// Used to tell if this is the cutter (i.e the one that we select on the table)
	private boolean template = false;

	// Nutrition values
	private double amount;
	private double carbohydrates;
	private double proteins;
	private double fats;
	// Calculated from proteins, carbohydrates and fats
	private double calories;
	private double quantity;
	
	// Used to tell if this food was imported from the database or if we added it
	// ourselves
	private boolean custom;

	// static values
	private final double ogAmount;
	private final double ogCalories, ogCarbohydrates, ogProteins, ogFats;

	/**
	 * 
	 * @param name
	 * @param values {amount, carbs, protein, fats}
	 * @param temp {tells us if this is a template}
	 */
	public Food(String name, double[] values, boolean temp) {
		super(name);
		
		// Validates array values and throws certain exceptions
		validateArray(values);
		
		// Sets if we are template
		this.template = temp;

		// Calculate calories based off values
		this.calories = (this.carbohydrates * 4) + (this.proteins * 4) + (this.fats * 9);

		// Store static values (that will never change)
		ogAmount = values[0];
		ogCarbohydrates = values[1];
		ogProteins = values[2];
		ogFats = values[3];
		ogCalories = (ogCarbohydrates * 4) + (ogProteins * 4) + (ogFats * 9);
	}

	private void validateArray(double[] values) {
		
		// Make sure there is enough values, if there isn't then throw an exception
		if(values.length != 4) throw new IllegalArgumentException("Invalid array size");
				
		// We have enough values so now check if they are positive
		for(int i=0; i<values.length; i++) {
			if(values[i] < 0) throw new IllegalArgumentException("Negative array values");
		}
		
		// Else accept the values given
		this.amount = values[0];
		this.carbohydrates = values[1];
		this.proteins = values[2];
		this.fats = values[3];
	}

	// Copy constructor
	public Food(String name, Food food) {
		super(name);

		this.calories = food.getCalories();
		this.template = food.getTemplate();

		if (this.template) {
			this.quantity = 1;
		} else {
			this.quantity = food.getQuantity();
		}

		this.carbohydrates = food.getCarbohydrates();
		this.fats = food.getFats();
		this.proteins = food.getProteins();
		this.amount = food.getAmount();

		this.custom = food.getCustom();

		this.ogAmount = food.getOgAmount();
		this.ogCarbohydrates = food.getOgCarbohydrates();
		this.ogFats = food.getOgFats();
		this.ogProteins = food.getOgProteins();
		this.ogCalories = (ogProteins * 4) + (ogCarbohydrates * 4) + (ogFats * 9);
	}

	public boolean getTemplate() {
		return this.template;
	}

	private void setTemplate(boolean val) {
		this.template = val;
	}

	public double getOgAmount() {
		return ogAmount;
	}

	public double getOgCalories() {
		return ogCalories;
	}

	public double getOgProteins() {
		return ogProteins;
	}

	public double getOgCarbohydrates() {
		return ogCarbohydrates;
	}

	public double getOgFats() {
		return ogFats;
	}

	public double getCalories() {
		return this.calories;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;

		// Multiply everything else by quantity automatically
		this.amount = ogAmount * quantity;
		this.calories = ogCalories * quantity;
		this.carbohydrates = ogCarbohydrates * quantity;
		this.fats = ogFats * quantity;
		this.proteins = ogProteins * quantity;
	}
	
	public double getQuantity() {
		return this.quantity;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
		// This also means we should change the name to have (custom) in brackets
		this.setName(this.getName() + " (custom) ");
	}

	public boolean getCustom() {
		return this.custom;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
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
		return new SimpleStringProperty(Double.toString(amount));
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
}
