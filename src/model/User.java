package model;

public class User {

	private static User instance = new User();

	/*
	 * Keep track of users: age, height, weight
	 */
	private int age;
	private double weight;
	private double height;

	/* Singleton class */
	private User() {

	}
	
	//static block initialization for exception handling
    static{
        try{
            instance = new User();
        }catch(Exception e){
            throw new RuntimeException("Exception occured in creating singleton user instance");
        }
    }

	public static User getInstance() {
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
}
