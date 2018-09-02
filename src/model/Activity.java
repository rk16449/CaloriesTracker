package model;

public class Activity extends Item {

	// Used to calculate TDEE based off activity levels
	private double activityLevel;

	public Activity(String name, double level) {
		super(name);
		this.setActivityLevel(level);
	}

	private void validateLevel(double value) {
		if (value < 0)
			throw new IllegalArgumentException("Negative activity level set!");
	}

	public double getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(double activityLevel) {
		validateLevel(activityLevel);
		this.activityLevel = activityLevel;
	}

	public String toString() {
		return this.getName();
	}
}
