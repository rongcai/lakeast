package org.lakeast.common;

import static org.junit.Assert.assertTrue;
import static org.lakeast.common.Functions.repairSequence;

import org.junit.Test;
import org.lakeast.model.ca.QuotedPriceMatrix;

public class FunctionsTest {

	@Test
	public final void testRepairSequence() {
		int[] x = new int[] { 1, 2, 3, 2 };
		repairSequence(x);
		print(x);

	}

	@Test
	public final void testCA() throws Exception {
		QuotedPriceMatrix matrix = new QuotedPriceMatrix(
				"D:\\Papers\\Impl\\CATS-windows\\100.txt");
		double[] t = new double[100];
		t[0] = 1.0;
		t[2] = 1.0;
		t[95] = 1.0;
		assertTrue((matrix.getBid(0).getQuotedPrice()
				+ matrix.getBid(2).getQuotedPrice() + matrix.getBid(95)
				.getQuotedPrice()) == Functions.CA(t, matrix));
	}

	private void print(int[] x) {
		for (int a : x) {
			System.out.println(a);
		}

	}

}
