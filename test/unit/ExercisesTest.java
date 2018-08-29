package unit;

import static org.junit.Assert.assertEquals;

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
		Exercise e1 = new Exercise("Bench Press", new Number[] {0, 5, 5, 5});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfSetsZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No sets set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 0, 5, 5});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfCaloriesBurnedZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Calories burned not set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 5, 5, 0});
	}
	
	@Test
	public void testConstructorReps() {
		Exercise e1 = new Exercise("Bench Press", new Number[] {5, 10, 50, 200});
		assertEquals(5, e1.getReps(), 1.0);
	}
	
	

}
