package org.lakeast.common;

import static org.lakeast.common.Functions.repairSequence;

import org.junit.Test;

public class FunctionsTest {

	@Test
	public final void testRepairSequence() {
		int[] x = new int[] { 1, 2, 3, 2 };
		repairSequence(x);
		print(x);

	}

	private void print(int[] x) {
		for (int a : x) {
			System.out.println(a);
		}
	}
}
