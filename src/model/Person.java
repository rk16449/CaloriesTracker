package model;

public class Person {

	private static Person instance = new Person();

	/*
	 * Keep track of users: age, height, weight
	 */
	private Goal currentGoal;
	private String activityLevel = "Sedentary";
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

	/* Singleton class */
	private Person() {

	}
	
	public String getActivityLevel() {
		return activityLevel;
	}

	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}

	public void setBodyfat(double bodyfat) {
		this.bodyfat = bodyfat;
	}

	public void setWaist(double waist) {
		this.waist = waist;
	}

	private double getTDEE() {
		// Calculates TDEE and returns it
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
}
