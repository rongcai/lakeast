package org.lakeast.model.tsp;

import org.junit.Test;
import org.lakeast.common.Functions;
import org.lakeast.common.InitializeException;
import org.lakeast.common.Randoms;

public class DistanceMatrixTest {
	@Test
	public void testIt() {
		DistanceMatrix matrix;
		try {
			matrix = new DistanceMatrix(100, true);
			int[] t = getRealMin(matrix.getValues());
			for (int x : t)
				System.out.println(x);
			// print(matrix.getValues());
			System.out.println(Functions.TSP(t, matrix.getValues()));
			// int list[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14 };
			// perm(list, 0, 13, matrix.getValues());
			// System.out.println(min);
			// for (int x : best)
			// System.out.println(x);
		} catch (InitializeException e) {
			e.printStackTrace();
		}

	}

	int fact(int n) {
		if (n <= 1)
			return 1;
		return n * fact(n - 1);
	}

	private double min = Double.POSITIVE_INFINITY;

	// private int[] best;

	void perm(int[] list, int k, int m, double[][] arr) {
		int i;
		if (k == m)// 输出一个全排列
		{
			double t = Functions.TSP(list, arr);
			if (t < min) {
				min = t;
				// best = list.clone();
			}
		} else
			// list[k:m]有多个排列方式
			// 递归的产生这些排列方式
			for (i = k; i <= m; i++) {
				swap(list, k, i);// 交换位置，逐步前提
				perm(list, k + 1, m, arr);
				swap(list, k, i);// 将位置还回去，对下一次排列负责
			}
	}

	private void swap(int[] list, int a, int b) {
		int t = list[a];
		list[a] = list[b];
		list[b] = t;
	}

	private int[] getRealMin(double[][] arr) {
		double min = Double.POSITIVE_INFINITY;
		int[] r = null;
		for (int i = 0; i < 5000 * 53; i++) {
			int[] r2 = Randoms.sequence(arr.length - 1);
			double t = Functions.TSP(r2, arr);
			if (t < min) {
				min = t;
				r = r2;
			}
		}
		return r;
	}
}
