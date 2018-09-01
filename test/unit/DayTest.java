package unit;

import java.time.LocalDate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Day;

public class DayTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionIfDuplicateDayCreated() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("This date already exists!");
		
		// Create a LocalDate, and attempt to create two days with the same date
		LocalDate date = LocalDate.now();
		Day day1 = new Day(date);
		Day day2 = new Day(date);
	}

}
