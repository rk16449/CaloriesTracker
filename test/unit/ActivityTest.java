/**
 * 
 */
package unit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Activity;

/**
 * @author Raj
 *
 */
public class ActivityTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void throwsIllegalArgumentExceptionIfConstructorActivityNegative() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative activity level set!");
		Activity a = new Activity("Lose Weight", -5);
	}


}
