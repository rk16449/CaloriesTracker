package model;

public class Helper {
	// Taken from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	// Used to round numbers before they are converted into text above
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static boolean isDouble(String value) {
		boolean valid = true;
		// Check if its numerical
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			// not a double
			valid = false;
		}
		return valid;
	}
}
