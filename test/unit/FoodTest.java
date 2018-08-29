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
	
	@Test
	public void givenTemplateWhenCreatedChangeQuantity() {
		// If we set a food as a template, we should not be able to change its quantity/cal/macros
		Food f1 = new Food("Brown Bread", new double[] {100, 10, 6, 2}, new boolean[] {true});
		
		// Try to change quantity
		f1.setQuantity(5);
		
		// Verify that its quantity never changed
		assertEquals(f1.getQuantity(), 1, 0.0);
		
		// Verify its Macros too
		assertEquals(f1.getCarbohydrates(), 10, 0.0);
		assertEquals(f1.getProteins(), 6, 0.0);
		assertEquals(f1.getFats(), 2, 0.0);
	}
	
	@Test
	public void givenCustomWhenCreatedModifyName() {
		// The name of a food should change with (custom) added to it
		Food f1 = new Food("Chicken", new double[] {100, 0, 22, 0.1}, new boolean[] {false, true});

		String customTag = " (custom) ";
		// Verify its name changed
		assertEquals(f1.getName(), "Chicken" + customTag);
	}

}
