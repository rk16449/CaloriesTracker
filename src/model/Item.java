package model;

public abstract class Item {

	private static int start_id = 0;
	
	private int id = 0;
	protected String name;
	
	public Item(String name) {
		this.name = name;
		this.id += start_id;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
