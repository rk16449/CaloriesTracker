package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Exercise extends Item {
	
	public Exercise(String name) {
		super(name);
	}

	public StringProperty getStrExercise() {
		return new SimpleStringProperty(this.getName());
	}
	
	
}
