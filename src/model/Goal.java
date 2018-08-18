package model;

public class Goal extends Item {
	
	private double multiplier;
	
	public Goal(String name, double multiplier) {
		super(name);
		this.multiplier = multiplier;
	}
}
