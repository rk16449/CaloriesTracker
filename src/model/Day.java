package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Day {
	
	private LocalDate date;
	
	public Day(LocalDate date) {
		this.date = date;
	}
	
	private ArrayList<Food> foods = new ArrayList<Food>();

	public ArrayList<Food> getFoods() {
		return foods;
	}

	public void setFoods(ArrayList<Food> foods) {
		this.foods = foods;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public boolean addFood(Food food) {
		return this.foods.add(food);
	}
	
	public boolean deleteFood(Food food) {
		
		// Loop backwards into food
		for(int i=foods.size()-1; i >= 0; i--) {
			
			if(foods.get(i).getName().equals(food.getName())) {
				return foods.remove(food);
			}
			
		}
		
		// Couldn't return a delete
		return false;
	}
}
