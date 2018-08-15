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
}
