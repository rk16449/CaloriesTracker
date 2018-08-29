/**
 * 
 */
package unit;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import model.Food;

/**
 * @author Raj
 * 
 * Given[ExplainYourInput]When[WhatIsDone]Then[ExpectedResult]
 *
 */
public class FoodTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void throwsIllegalArgumentExceptionIfArrayEmpty() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid array size");
		Food f1 = new Food("Cake", new double[] {});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfArrayNegative() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Negative array values");
		Food f1 = new Food("Cake", new double[] {-1, -2, -3, 5});
	}
	
	@Test
	public void throwsIllegalArgumentExceptionIfAmountZero() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Amount cannot be 0");
		Food f1 = new Food("Cake", new double[] {0, 100, 50, 50});
	}
	
	@Test
	public void givenMacrosWhenCreatedCalcCalories() {
		Food f1 = new Food("Whole Milk", new double[] {100, 10, 10, 10});
		// carbs = 4 cals, protein = 4 cals, fats = 9 cals
		double sum = (f1.getCarbohydrates() * 4) + (f1.getProteins() * 4) + (f1.getFats() * 9);
		assertEquals(f1.getCalories(), sum, 0.1);
	}

}
