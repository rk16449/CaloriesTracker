package model;

public class Person {

	private static Person instance = new Person();

	/*
	 * Keep track of users: age, height, weight
	 */
	private int age;
	private double weight;
	private double height;

	/* Singleton class */
	private Person() {

	}
	
	//static block initialization for exception handling
    static{
        try{
            instance = new Person();
        }catch(Exception e){
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
}
