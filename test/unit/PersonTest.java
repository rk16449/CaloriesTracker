package unit;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.css.PseudoClass;
import model.Person;

public class PersonTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	public static Person p;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p = Person.getInstance();
	}

	@Test
	public void throwsIllegalArgumentExceptionIfNegativeAgeSet() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative value set!");
		
		p.setAge(-50);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfNegativeWeightSet() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative value set!");
		
		p.setWeight(-450);
	}
	
	
	@Test
	public void throwsIllegalArgumentExceptionIfNegativeHeightSet() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative value set!");
		
		p.setHeight(-183);
	}

	
	@Test
	public void throwsIllegalArgumentExceptionIfNegativeCaloriesSet() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative value set!");
		
		p.setGoalCalories(-5000);
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfIllegalGenderSet() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Illegal gender set!");
		
		p.setGender("asldh2h32u3allajsd");
	}
	
	@Test
	public void settingMaleGender() {
		p.setGender("Male");
		Assert.assertEquals("Male", p.getGender());
	}
	
	@Test
	public void settingFemaleGender() {
		p.setGender("Female");
		Assert.assertEquals("Female", p.getGender());
	}
}
