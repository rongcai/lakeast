package org.lakeast.common;

import java.util.Random;

public final class Randoms {
	public final static Random random = new Random();

	/** Cannot create a new instance of Randoms */
	private Randoms() {
	}

	/**
	 * n1(inclusive) and n2(exclusive)
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */
	public static double doubleInRange(double n1, double n2) {
		if (n1 == n2)
			return n1;
		double temp;
		if (n1 < n2) {
			temp = (n2 - n1);
			return n1 + (random.nextDouble() * temp);
		} else {
			temp = (n1 - n2);
			return n2 + (random.nextDouble() * temp);
		}
	}

	public static int intInRange(int n1, int n2) {
		return n1 + random.nextInt(n2 - n1 + 1);
	}

	/**
	 * 基于随机交换快速发生长为len的无重复伪随机序列[1-len]。服从平均分布。
	 * 
	 * @param len
	 *            序列长度
	 * @return 随机序列
	 */
	public static int[] sequence(int len) {
		int[] s = new int[len];
		for (int i = 0; i < len; i++) {
			s[i] = i + 1;
		}
		for (int i = 0; i < len; i++) {
			swap(s, i, intInRange(0, len - 1));
		}
		return s;
	}

	private static void swap(int[] arr, int a, int b) {
		if (a == b)
			return;
		arr[a] ^= arr[b];
		arr[b] ^= arr[a];
		arr[a] ^= arr[b];
	}
}
