package model;

public abstract class Item {
	
	protected String name;
	protected static int start_id = 0;
	private int id = 0;
	
	
	public Item(String name) {
		this.name = name;
		
		if(start_id >= Integer.MAX_VALUE) throw new IllegalArgumentException("Maximum items reached!");
		
		this.id = start_id;
		start_id++;
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
