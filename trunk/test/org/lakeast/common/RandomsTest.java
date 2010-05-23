package org.lakeast.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RandomsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 测试伪随机序列发生器的平均分布性。
	 */
	@Test
	public final void testSequence() {
		int m = 10000000;
		int len = 10;
		int[][] t = new int[len][len];
		for (int j = 0; j < m; j++) {
			int[] x = Randoms.sequence(len);
			for (int i = 0; i < len; i++) {
				t[i][x[i] - 1]++;
			}
		}
		double sum = 0.0;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				System.out.print(t[i][j] / (double) m + " ");
				sum += t[i][j] / (double) m;
			}
			System.out.println();
		}
		System.out.println(sum);
	}
}
