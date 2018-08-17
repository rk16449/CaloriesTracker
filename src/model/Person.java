package model;

public class Person {

	private static Person instance = new Person();

	/*
	 * Keep track of users: age, height, weight
	 */
	private int age;
	private double weight;
	private double height;
	private String gender;
	private String firstName;
	private String lastName;

	/* Singleton class */
	private Person() {

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

	public void setGender(String newValue) {
		this.gender = newValue;
	}


	public double getBodyfat() {
		// TODO Auto-generated method stub
		return 0;
	}


	public double getWaist() {
		// TODO Auto-generated method stub
		return 0;
	}
}
