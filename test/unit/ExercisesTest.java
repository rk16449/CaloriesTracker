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
		exception.expectMessage("Invalid array size");
		Exercise e1 = new Exercise("Cake", new Number[] {});
	}

}
