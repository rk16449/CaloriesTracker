package unit;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Item;

public class ItemTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionIfMaxItems() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Maximum items reached!");
		
		
		BItem bItem = new BItem("First Item");
		// Reset the ITEM counter to max
		bItem.setIDCounter(Integer.MAX_VALUE);
		
		// Create another item in front of max value
		BItem bItem2 = new BItem("Second Item");
	}
	
	class BItem extends Item{
		public BItem(String name) {
			super(name);
		}
		
		public void setIDCounter(int value) {
			Item.start_id = value;
		}
	}

}
