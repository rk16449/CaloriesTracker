package model;

public abstract class Item {
	
	protected String name;
	protected static int start_id = 0;
	private int id = 0;
	
	
	public Item(String name) {
		this.name = name;
		validItem();
	}
	
	/**
	 * Decides if this item can be valid, i.e we haven't reached the maximum amount of items before we can set ID
	 */
	private void validItem() {
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
