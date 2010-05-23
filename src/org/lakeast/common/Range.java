package org.lakeast.common;

public final class Range {

	private static final String MIN_LT_MAX = "Minimum must be less than maximum.";

	private static final double EPSILON = 0.0000000000000005d;

	private Range() {
	}

	/**
	 * Makes a double value fit within a range by "sticking it" to an exceeded
	 * boundary (if any) -- for example, the value of 12 is fit into the range
	 * 0-to-10 by setting it to 10.
	 * 
	 * @return a number within the specified range.
	 */
	public static double constrict(double n, double min, double max) {
		if (n <= min)
			return min;
		if (n >= max)
			return max;
		return n;
	}

	/**
	 * Makes a double value fit within a range by "sticking it" to an exceeded
	 * boundary (if any) -- for example, the value of 12 is fit into the range
	 * 0-to-10 by setting it to 10.
	 * 
	 * @return a number within the specified range.
	 */
	public static double stick(double n, double min, double max) {
		return constrict(n, min, max);
	}

	/**
	 * Makes a double value fit within a range by "sticking it" to an exceeded
	 * boundary (if any) -- for example, the value of 12 is fit into the range
	 * 0-to-10 by setting it to 10. If <code>withEpsilon</code> is
	 * <code>true</code>, the value is compared against the range boundaries
	 * narrowed by the value of static final field EPSILON.
	 * 
	 * @return a number within the specified range.
	 */
	public static double constrict(double n, double min, double max,
			boolean withEpsilon) {
		if (!withEpsilon)
			return constrict(n, min, max);
		if (n < min + EPSILON)
			return min;
		if (n > max - EPSILON)
			return max;
		return n;
	}

	/**
	 * Makes a double value fit within a range using a "bounce method" -- for
	 * example, the value 12 is fit into the range 0-to-10 by repeatedly
	 * wrapping it around a continuous <code>0,...,9,10,9,...,1,0,1,...</code>
	 * range, yielding a result of 8.
	 * 
	 * @return a number within the specified range.
	 */
	public static double bounce(double n, double min, double max) {
		if (inRange(n, min, max))
			return n;
		double range = max - min;
		double err = (n < min ? min - n : n - max);
		double bounces = Math.floor(err / range);
		double remainder = err - (bounces * range);
		bounces = bounces % 2.0;
		if (n < min)
			return (bounces == 0.0 ? min + remainder : max - remainder);
		else
			return (bounces == 0.0 ? max - remainder : min + remainder);
	}

	/**
	 * Makes an integer value fit within a range using a "wrap method" -- for
	 * example, the value 22 is fit into the range 0-to-10 by repeatedly
	 * wrapping it around a continuous <code>0,...,10,0,...,10,0,...</code>
	 * range, yielding a result of 2.
	 * 
	 * @return a number within the specified range.
	 */
	public static int wrap(int n, int min, int max) {
		int range = max - min + 1;
		if (n >= min)
			return n - (n - min) / range * range;
		else
			return n + (max - n) / range * range;
	}

	/**
	 * Makes a double value fit within a range using a "wrap method" -- for
	 * example, the value 22 is fit into the range 0-to-10 by repeatedly
	 * wrapping it around a continuous <code>0,...,10,0,...,10,0,...</code>
	 * range, yielding a result of 2.
	 * 
	 * @return a number within the specified range: [min,max).
	 */
	public static double wrap(double n, double min, double max) {
		if (n >= min && n <= max) {
			return n;
		}
		double range = max - min;
		if (n >= min)
			return n - Math.floor((n - min) / range) * range;
		else
			return n + Math.floor((max - n) / range) * range;
	}

	/** Returns true if n is inside range min..max <b>inclusive</b>. */
	public static boolean inRange(double n, double min, double max) {
		if (min > max)
			throw new IllegalArgumentException(MIN_LT_MAX);
		return ((n >= min) && (n <= max));
	}

	/** Returns true if n is inside range min..max <b>inclusive</b>. */
	public static boolean inRange(int n, int min, int max) {
		if (min > max)
			throw new IllegalArgumentException(MIN_LT_MAX);
		return ((n >= min) && (n <= max));
	}

	/** Returns true if n is inside range min..max <b>exclusive</b>. */
	public static boolean inBounds(double n, double min, double max) {
		if (min >= max)
			throw new IllegalArgumentException(MIN_LT_MAX);
		return ((n > min) && (n < max));
	}

}
