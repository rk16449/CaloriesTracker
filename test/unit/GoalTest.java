package unit;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Goal;

public class GoalTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void throwsIllegalArgumentExceptionIfMultiplierNegative() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative multiplier set!");
		Goal g1 = new Goal("Maintain Weight", -1000);
	}

}
