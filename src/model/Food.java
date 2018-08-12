package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Food extends Item {

	private int amount;
	private double proteins;

	private double carbohydrates;
	private double fats;
	
	public Food(String name, int amount, double[] values) {
		super(name);

		this.amount = amount;
		this.proteins = values[0];
		this.carbohydrates = values[1];
		this.fats = values[2];
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
	
	public StringProperty getStrCarbs() {
        return new SimpleStringProperty(Double.toString(carbohydrates));
    }
	
	public StringProperty getStrFats() {
        return new SimpleStringProperty(Double.toString(fats));
    }
	
	public StringProperty getStrProts() {
        return new SimpleStringProperty(Double.toString(proteins));
    }
}
