package boosting;

public class Calculator {
	public static double mid(double low, double high) {
		return low + (high - low)/2;
	}

	public static boolean isEqual(double a, double b) {
		return (Double.compare((a - b), 0.0000001) <= 0);
	}
}
