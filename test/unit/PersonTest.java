package unit;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Person;

public class PersonTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void throwsIllegalArgumentExceptionIfNegativeAge() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative age set!");
		Person p = Person.getInstance();
		p.setAge(-50);
	}

}
