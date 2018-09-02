/**
 * 
 */
package unit;

import org.junit.Assert;
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
		Activity a = new Activity("Sedentary", -5);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfSetNegativeActivityLevel() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative activity level set!");
		Activity a = new Activity("Sedentary", 1.2);
		a.setActivityLevel(-5);
	}
	
	@Test
	public void testGetActivityLevelFromConstructor() {
		Activity a = new Activity("Sedentary", 1.2);
		Assert.assertEquals(1.2, a.getActivityLevel(), 0.0);
	}

	@Test
	public void testGetActivityLevelFromSetter() {
		Activity a = new Activity("Sedentary", 1.2);
		a.setActivityLevel(1.8);
		Assert.assertEquals(1.8, a.getActivityLevel(), 0.0);
	}
	
	@Test
	public void testToStringGivesActivityName() {
		Activity a = new Activity("Sedentary", 1.2);
		Assert.assertEquals("Sedentary", a.toString());
	}
}
