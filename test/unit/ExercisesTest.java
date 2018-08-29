package unit;

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
		Exercise e1 = new Exercise("Bench Press", new Number[] {-1, 5, 5, 5});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfRepsZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("No reps set!");
		Exercise e1 = new Exercise("Bench Press", new Number[] {0, 5, 5, 5});
	}

}
