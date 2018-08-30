package unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Exercise;

public class ExercisesTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionIfArrayEmpty() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid array size!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfArrayNegative() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative array values!");
		
		int counter = -1;
		// For loop between each values  -1000 < 0 
		for(int i=0; i<1000; i++) {
			Exercise e1 = new Exercise("Bench Press", new Number[] {counter, counter, counter, counter});
			counter--;
		}
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfRepsZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No reps set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {0, 5, 5.0, 5.0});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfSetsZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No sets set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 0, 5.0, 5.0});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfCaloriesBurnedZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Calories burned not set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 5, 5.0, 0.0});
	}
	
	@Test
	public void testConstructorReps() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		assertEquals(5, e1.getReps(), 1.0);
	}
	
	@Test
	public void testConstructorSets() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		assertEquals(10, e1.getSets(), 1.0);
	}
	
	@Test
	public void testConstructorWeight() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		assertEquals(50, e1.getWeight(), 1.0);
	}
	
	@Test
	public void testConstructorCaloriesBurned() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		assertEquals(200, e1.getCaloriesBurned(), 1.0);
	}
	
	@Test
	public void testConstructorName() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		assertEquals("Bench Press", e1.getName());
	}
	
	@Test
	public void testConstructorCustom() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0}, true);
		assertTrue(e1.getCustom());
	}
	
	@Test
	public void testConstructorStringValues() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		String[] values = {"5", "10", "50.0", "200.0"};
		assertArrayEquals(values, e1.getStrVals());
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfValsNegative() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative array values!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		e1.setValues(new Number[] {-1, -2, -3, -4});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfValsCaloriesZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Calories burned not set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		e1.setValues(new Number[] {10, 10, 10, 0});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfValsRepsZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No reps set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		e1.setValues(new Number[] {0, 10, 10, 10});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfValsSetsZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No sets set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		e1.setValues(new Number[] {10, 0, 10, 10});
	}
	
	@Test
	public void testCopyConstructorValues() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50.0, 200.0});
		
		Exercise e2 = new Exercise(e1);
		
		assertEquals(e1.getName(), e2.getName());
		assertEquals(e1.getReps(), e2.getReps());
		assertEquals(e1.getSets(), e2.getSets());
		assertEquals(e1.getWeight(), e2.getWeight(), 0.0);
		assertEquals(e1.getCaloriesBurned(), e2.getCaloriesBurned(), 0.0);
		assertEquals(e1.getCustom(), e2.getCustom());
	}
}
