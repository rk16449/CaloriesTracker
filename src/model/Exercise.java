package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Exercise extends Item {
	
	private int reps;
	private int sets;
	private double weight;
	private double caloriesBurned;
	private boolean custom;
	
	/**
	 * Constructor
	 * @param name
	 */
	public Exercise(String name) {
		super(name);
	}
	
	/**
	 * Constructor which takes array of numbers 
	 * @param name
	 * @param nums [reps, sets, weight, caloriesBurned]
	 */
	public Exercise(String name, Number[] nums) {
		super(name);
		
		validateArray(nums);
		setValues(nums);
	}
	
	/**
	 * Constructor which takes array of numbers & custom value
	 * @param name
	 */
	public Exercise(String name, Number[] nums, boolean custom) {
		super(name);
		
		validateArray(nums);
		setValues(nums);
		
		this.custom = custom;
	}

	/**
	 *  Copy constructor
	 * @param name
	 * @param exercise
	 */
	public Exercise(Exercise exercise) {
		super(exercise.getName());
		
		this.reps = exercise.getReps();
		this.sets = exercise.getSets();
		this.weight = exercise.getWeight();
		this.caloriesBurned = exercise.getCaloriesBurned();
		this.custom = exercise.getCustom();
	}

	private void validateArray(Number[] nums) {
		
		// Not enough array values
		if(nums.length != 4) throw new IllegalArgumentException("Invalid array size!");
		
		// Reps/Sets cannot be 0
		if(nums[0].intValue() == 0) throw new IllegalArgumentException("No reps set!");
		
		if(nums[1].intValue() == 0) throw new IllegalArgumentException("No sets set!");
		
		// weight is nums[2] which can be 0
		
		if(nums[3].doubleValue() == 0) throw new IllegalArgumentException("Calories burned not set!");
		
		// Negative array values
		for(int i=0; i<nums.length; i++) {
			if(nums[i].doubleValue() <= 0) throw new IllegalArgumentException("Negative array values!");
		}
		
		
	}
	
	/**
	 * 
	 * @param nums [reps, sets, weight, caloriesBurned]
	 */
	public void setValues(Number[] nums) {
		// Validate values
		validateArray(nums);
		
		
		// Set the values
		this.reps = nums[0].intValue();
		this.sets = nums[1].intValue();
		this.weight = nums[2].doubleValue();
		this.caloriesBurned = nums[3].doubleValue();
	}
	
	public String[] getStrVals() {
		String[] vals = {
				Integer.toString(this.getReps()),
				Integer.toString(this.getSets()),
				Double.toString(this.getWeight()),
				Double.toString(this.getCaloriesBurned())
		};
		
		return vals;
	}
	
	// TableView Getters
	
	public StringProperty getStrExercise() {
		return new SimpleStringProperty(this.getName());
	}
	
	public StringProperty getStrSets() {
		return new SimpleStringProperty(Integer.toString(sets));
	}
	
	public StringProperty getStrReps() {
		return new SimpleStringProperty(Integer.toString(reps));
	}
	
	public StringProperty getStrWeight() {
		return new SimpleStringProperty(Double.toString(weight));
	}
	
	public StringProperty getStrCaloriesBurned() {
		return new SimpleStringProperty(Double.toString(caloriesBurned));
	}
	
	// Getters & Setters
	
	public boolean getCustom() {
		return this.custom;
	}
	
	public void setCustom(boolean b) {
		this.custom = b;
	}
	
	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getCaloriesBurned() {
		return caloriesBurned;
	}

	public void setCaloriesBurned(double caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}
	
	// toString
	public String toString() {
		return "Name: " + this.getName() + " { sets: " + this.getSets() + ", reps: " + this.getReps() + ", weight: " + this.getWeight() + ", caloried burned: " + this.getCaloriesBurned() + " };";
	}

	public void setExercise(Exercise exercise) {
		this.reps = exercise.getReps();
		this.sets = exercise.getSets();
		this.weight = exercise.getWeight();
		this.caloriesBurned = exercise.getCaloriesBurned();
		this.custom = exercise.getCustom();
	}


}
