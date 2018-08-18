package model;

public class Activity extends Item {
	// Used to calculate TDEE based off activity levels
	
	private double activityLevel;
	
	public Activity(String name, double level) {
		super(name);
		this.setActivityLevel(level);
	}

	public double getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(double activityLevel) {
		this.activityLevel = activityLevel;
	}
}
