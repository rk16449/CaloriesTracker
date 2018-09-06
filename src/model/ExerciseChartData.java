package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExerciseChartData{
	
	String exerciseName;
	private ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
	private ArrayList<Double> values = new ArrayList<Double>();
	
	public ExerciseChartData(String name, ArrayList<LocalDate> dates, ArrayList<Double> values) {
		this.exerciseName = name;
		this.dates = dates;
		this.values = values;
	}
	
	public void addChartData(LocalDate date, double value) {
		this.dates.add(date);
		this.values.add(value);
	}
	
	public String getName() {
		return this.exerciseName;
	}
	
	public ArrayList<LocalDate> getDates() {
		return this.dates;
	}
	
	public ArrayList<Double> getValues(){
		return this.values;
	}
}
