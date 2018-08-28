/**
 * 
 */
package unit;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Food;

/**
 * @author Raj
 *
 */
public class FoodTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionIfArrayEmpty() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid array size");
		Food f1 = new Food("Cake", new double[] {}, false);
	}

}
