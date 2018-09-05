package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ExerciseChartData extends Item{
	
	private LocalDate date;
	private ArrayList<Double> values = new ArrayList<Double>();
	
	public ExerciseChartData(String name, LocalDate date) {
		super(name);
		this.date = date;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public ArrayList<Double> getValues(){
		return this.values;
	}
	
	public void addValues(double value) {
		this.values.add(value);
	}
}
