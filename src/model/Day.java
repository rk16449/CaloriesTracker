package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Day implements Comparable<Day> {

	private LocalDate date;

	public Day(LocalDate date) {
		this.date = date;
	}

	private ArrayList<Food> foods = new ArrayList<Food>();
	private ArrayList<Exercise> exercises = new ArrayList<Exercise>();

	public ArrayList<Exercise> getExercises() {
		return exercises;
	}

	public boolean addExercise(Exercise ex) {
		return this.exercises.add(ex);
	}

	public ArrayList<Food> getFoods() {
		return foods;
	}

	public LocalDate getDate() {
		return date;
	}

	public boolean addFood(Food food) {
		return this.foods.add(food);
	}

	public boolean deleteFood(Food food) {

		// Loop backwards into food
		for (int i = foods.size() - 1; i >= 0; i--) {

			if (foods.get(i).getName().equals(food.getName())) {
				return foods.remove(food);
			}

		}

		// Couldn't return a delete
		return false;
	}

	public double getTotalFoodCalories() {
		// Loop through all foods and calculate the total calories
		double sum = 0;

		for (int i = 0; i < foods.size(); i++) {
			sum += foods.get(i).getCalories();
		}

		return sum;
	}
	
	public double getTotalExerciseCalories() {
		double sum = 0;
		
		for(int i=0; i <exercises.size(); i++) {
			sum += exercises.get(i).getCaloriesBurned();
		}
		
		return sum;
	}

	@Override
	public int compareTo(Day arg0) {
		return date.compareTo(arg0.getDate());
	}

	public boolean deleteExercise(Exercise exercise) {
		// Loop backwards into exercises
		for (int i = exercises.size() - 1; i >= 0; i--) {

			// Check if these are the same objects
			if (exercises.get(i).equals(exercise)) {
				return exercises.remove(exercise);
			}

		}

		// Couldn't return a delete
		return false;
	}
}
