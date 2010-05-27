package org.lakeast.common;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RangeTest {

	/**
	 * Test method for {@link org.lakeast.common.Range#wrap(int, int, int)}.
	 */
	@Test
	public final void testWrapIntIntInt() {
		assertTrue(Range.wrap(-5, 0, 39) == 35);
		assertTrue(Range.wrap(-4, 0, 39) == 36);
		assertTrue(Range.wrap(1, 0, 39) == 1);
		assertTrue(Range.wrap(40, 0, 39) == 0);
		assertTrue(Range.wrap(80, 0, 39) == 0);
		assertTrue(Range.wrap(-40, 0, 39) == 0);
		assertTrue(Range.wrap(-134, 0, 39) == 26);
		assertTrue(Range.wrap(134, 0, 39) == 14);
		assertTrue(Range.wrap(134, 1, 20) == 14);
		assertTrue(Range.wrap(134, 2, 39) == 20);
		assertTrue(Range.wrap(134, 6, 9) == 6);
	}

	/**
	 * Test method for
	 * {@link org.lakeast.common.Range#wrap(double, double, double)}.
	 */
	@Test
	public final void testWrapDoubleDoubleDouble() {
		assertTrue(Range.wrap(-5.0, 0, 39) == 34);
		assertTrue(Range.wrap(-40.0, 0, 39) == 38);
	}
}
