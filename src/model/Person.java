package model;

public class Person {

	private static Person instance = new Person();

	/*
	 * Keep track of users: age, height, weight
	 */
	private Goal currentGoal = new Goal("Maintain Weight", 1.0);
	private  double goalCalories;
	private Activity activityLevel = new Activity("Sedentary", 1.2);
	private int age = 20;
	private double weight = 70;
	private double height = 183;
	private double bodyfat = 19.0;
	private double waist = 85;
	private String gender = "Male";
	private String firstName = "Hello";
	private String lastName = "World";
	private double BMI = 0;
	private double BMR = 0;
	private double TDEE = 0;
	private String units = "Metric";
	

	/* Singleton class */
	private Person() {

	}
	
	public Activity getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(Activity activityLevel) {
		this.activityLevel = activityLevel;
	}

	public void setBodyfat(double bodyfat) {
		this.bodyfat = bodyfat;
	}

	public void setWaist(double waist) {
		this.waist = waist;
	}
	
	public void setTDEE(double value) {
		this.TDEE = value;
	}

	public double getTDEE() {
		return TDEE;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	// static block initialization for exception handling
	static {
		try {
			instance = new Person();
		} catch (Exception e) {
			throw new RuntimeException("Exception occured in creating singleton user instance");
		}
	}

	public static Person getInstance() {
		return instance;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setGender(String newValue) {
		this.gender = newValue;
	}

	public double getBodyfat() {
		return bodyfat;
	}

	public double getWaist() {
		return waist;
	}

	public double getGoalCalories() {
		return goalCalories;
	}
	
	public void setGoalCalories(double value) {
		this.goalCalories = value;
	}

	public Goal getCurrentGoal() {
		return currentGoal;
	}

	public void setCurrentGoal(Goal currentGoal) {
		this.currentGoal = currentGoal;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public double getBMR() {
		return BMR;
	}

	public void setBMR(double bMR) {
		BMR = bMR;
	}

	public double getBMI() {
		return BMI;
	}

	public void setBMI(double bMI) {
		BMI = bMI;
	}
}
