package model;

import java.time.LocalDate;
import java.util.ArrayList;

import controllers.food.AddFoodController;

public class Day implements Comparable<Day> {

	private LocalDate date;
	
	public static ArrayList<LocalDate> dates = new ArrayList<LocalDate>();

	public Day(LocalDate date) {
		
		validateDate(date);
		
		this.date = date;
	}
	
	private void validateDate(LocalDate date) {
		
		// Loop through dates arraylist to see if this date already exists
		for(int i=0; i<dates.size(); i++) {
			if(dates.get(i).equals(date)) {
				throw new IllegalArgumentException("This date already exists!");
			}
		}
		
		// Else, store it inside dates
		dates.add(date);
	}
	
	public static boolean updateQuantity(Day currentDay, AddFoodController controller, boolean found) {
		for (int i = 0; i < currentDay.getFoods().size(); i++) {
			// Assumes we don't have foods with exactly the same name.. (try adding id in
			// later)
			if (currentDay.getFoods().get(i).getName().equals(controller.getFood().getName())) {
				found = true;
				
				System.out.println("Updating quantity on addEntry");
				
				currentDay.getFoods().get(i).setQuantity(currentDay.getFoods().get(i).getQuantity() + controller.getQuantity());
				break;
			}
		}
		
		return found;
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
