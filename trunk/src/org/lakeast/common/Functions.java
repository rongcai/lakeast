package org.lakeast.common;

import java.util.HashSet;
import java.util.Set;

import org.lakeast.model.ca.QuotedPriceMatrix;

public final class Functions {
	private Functions() {
	}

	public static double TSP(int vector[], double[][] matrix) {
		assertTrue(vector.length == matrix.length - 1);
		double result = 0.0;
		int len = vector.length;
		result += matrix[0][vector[0]];
		for (int i = 0; i < len - 1; i++) {
			result += matrix[vector[i]][vector[i + 1]];
		}
		result += matrix[vector[len - 1]][0];
		return result;
	}

	private static final double ELIPSON = 0.000000001;

	private static final boolean isEqual(double[] a, double[] b) {
		if (distance(a, b) <= ELIPSON) {
			return true;
		}
		return false;
	}

	/**
	 * K-means聚类方法。基于vector向量让objArray数组中的对象聚成K类（最多是K类，可能更少）。
	 * 
	 * @param vector
	 * @param K
	 * @param objArray
	 * @return 聚类后的K个集合（可能存在空集合）
	 */
	@SuppressWarnings("unchecked")
	public static final Set<Object>[] KMeans(double[][] vector, final int K,
			Object[] objArray) {
		assertTrue(vector.length == objArray.length);
		Set<Object>[] clusters = new Set[K];
		int size = vector.length;
		int dimensions = vector[0].length;
		double[][] center = new double[K][];
		for (int i = 0; i < K; i++) {
			clusters[i] = new HashSet<Object>();
			center[i] = vector[i];
		}
		int[] type = new int[size];
		boolean finished;
		do {
			finished = true;
			/*
			 * 根据中心位置贴标签。
			 */
			for (int i = 0; i < size; i++) {
				double min = Double.POSITIVE_INFINITY;
				for (int j = 0; j < K; j++) {
					double d = distance(vector[i], center[j]);
					if (d < min) {
						min = d;
						type[i] = j;
					}
				}
			}
			/*
			 * 求和。
			 */
			double[][] newCenter = new double[K][dimensions];
			int[] clen = new int[K];// 保存各新簇大小
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < dimensions; j++) {
					newCenter[type[i]][j] += vector[i][j];
				}
				clen[type[i]]++;
			}
			/*
			 * 计算新中心。
			 */
			for (int i = 0; i < K; i++) {
				for (int j = 0; j < dimensions; j++) {
					if (clen[i] != 0) {
						newCenter[i][j] /= clen[i];
					} else {// 无该簇个体则直接复制旧中心到新中心。
						newCenter[i][j] = center[i][j];
					}
				}
			}
			/*
			 * 判断中心是否已经稳定。
			 */
			for (int i = 0; i < K; i++) {
				if (!isEqual(center[i], newCenter[i])) {
					finished = false;
					break;
				}
			}
			/*
			 * 设定新中心为旧中心，以便下次比较。
			 */
			for (int i = 0; i < K; i++) {
				center[i] = newCenter[i];
			}
		} while (!finished);
		for (int i = 0; i < size; i++) {
			clusters[type[i]].add(objArray[i]);
		}
		return clusters;
	}

	/**
	 * 基于最大熵原理随机修补一个非序列为序列。
	 * 
	 * @param s
	 */
	public static void repairSequence(double[] s) {
		int len = s.length;
		int[] x = new int[len];
		for (int i = 0; i < len; i++) {
			x[i] = (int) s[i];
		}
		for (int i = 0; i < len; i++) {
			assertTrue(x[i] <= len && x[i] >= 1);// 前置条件断言
		}
		int[] t = new int[len];
		boolean[] f = new boolean[len];
		int k = 0;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (x[i] == x[j]) {
					t[k++] = i;// 记录重元的位置
					break;
				}
			}
			f[x[i] - 1] = true;// 记录序列中存在的元素
		}
		int[] m = new int[k];
		int p = 0;
		for (int i = 0; i < len; i++) {
			if (!f[i]) {
				m[p++] = i + 1;// 记录缺少的序列元素
			}
		}
		assertTrue(k == p);// 不存在的元素个数应该等于重元的个数
		int[] s2 = Randoms.sequence(k);
		for (int i = 0; i < k; i++) {
			s[t[i]] = m[s2[i] - 1] + Math.random();// 把缺少的元素随机补充进去替换掉重元
		}
	}

	/**
	 * 基于最大熵原理随机修补一个非序列为序列。
	 * 
	 * @param s
	 */
	public static void repairSequence(int[] s) {
		int len = s.length;
		for (int i = 0; i < len; i++) {
			assertTrue(s[i] <= len && s[i] >= 1);// 前置条件断言
		}
		int[] t = new int[len];
		boolean[] f = new boolean[len];
		int k = 0;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (s[i] == s[j]) {
					t[k++] = i;// 记录重元的位置
					break;
				}
			}
			f[s[i] - 1] = true;// 记录序列中存在的元素
		}
		int[] m = new int[k];
		int p = 0;
		for (int i = 0; i < len; i++) {
			if (!f[i]) {
				m[p++] = i + 1;// 缺少的序列元素
			}
		}
		assertTrue(k == p);// 不存在的元素个数应该等于重元的个数
		int[] s2 = Randoms.sequence(k);
		for (int i = 0; i < k; i++) {
			s[t[i]] = m[s2[i] - 1];// 把缺少的元素随机补充进去替换掉重元
		}
	}

	/**
	 * 交换g1,g2中从from(inclusive)到to(inclusive)中的元素以改变g1,g2中元素顺序同时保证g1,g2所含有的元素不变。
	 * 
	 * @param g1
	 * @param g2
	 * @param from
	 * @param to
	 */
	public static void crossover(int[] g1, int[] g2, int from, int to) {
		int len = to - from + 1;
		boolean a[] = new boolean[len];
		boolean b[] = new boolean[len];
		// 挑选两个集合中的公共元素，并标记。
		// O(n*(n+1)/2)
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (!b[j] && g1[from + i] == g2[from + j]) {
					a[i] = b[j] = true;
					break;
				}
			}
		}
		int[] p = new int[2 * len / 3];// p中元素最多有2*len/3个。
		int[] q = new int[2 * len / 3];
		int m = 0;
		int n = 0;
		/*
		 * 交换并记录不该交换的元素。
		 */
		for (int i = 0; i < len; i++) {
			if (a[i]) {
				swap(g1, g2, from + i);
				if (!b[i]) {// g1中不应有的元素
					p[m++] = from + i;// 记录该元素被交换后在g1中的位置。
				}
			} else if (b[i]) {// g2中重复的元素
				q[n++] = from + i;// 记录该元素在g2中的位置。
			}
		}
		Functions.assertTrue(m == n);
		/*
		 * 把不该交换的元素换回来。就是以g1中不应有元素换g2中重复但是g1需要的元素。
		 */
		for (int i = 0; i < m; i++) {
			g1[p[i]] ^= g2[q[i]];
			g2[q[i]] ^= g1[p[i]];
			g1[p[i]] ^= g2[q[i]];
		}
	}

	public static double knapsack(double[] vector, double[] prices) {
		double result = 0.0;
		for (int i = 0; i < vector.length; i++) {
			result += (vector[i] == 1.0 ? prices[i] : 0.0);
		}
		return result;
	}

	public static double CA(double vector[], QuotedPriceMatrix matrix) {
		double result = 0.0;
		for (int i = 0; i < vector.length; i++) {
			result += (vector[i] == 1.0 ? matrix.getBid(i).getQuotedPrice()
					: 0.0);
		}
		return result;
	}

	public static double Sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public static double Sphere(double vector[]) {
		double result = 0.0;
		for (int i = 0; i < vector.length; i++) {
			result += vector[i] * vector[i];
		}
		return (result);
	}

	public static double Banana(double vector[]) {
		double result = 0;
		for (int i = 1; i < vector.length; i++) {
			double x1 = vector[i - 1];
			double x2 = vector[i];
			double p = x1 - x2 * x2;
			double q = x2 - 1;
			result += 100 * p * p + q * q;
		}
		return (result);
	}

	public static double Griewank(double vector[]) {
		double result1 = 0.0, result2 = 1.0;
		for (int i = 0; i < vector.length; i++) {
			result1 += vector[i] * vector[i];
		}
		for (int i = 0; i < vector.length; i++) {
			result2 *= Math.cos(vector[i] / Math.sqrt(i + 1));
		}
		double result = result1 / 4000 - result2 + 1;
		return (result);
	}

	public static double RasTrigrin(double vector[]) {
		double result = 0.0;
		for (int i = 0; i < vector.length; i++) {
			result += vector[i] * vector[i] - 10
					* Math.cos(2 * Math.PI * vector[i]) + 10;
		}
		return (result);
	}

	public static double ShafferF6(double vector[]) {
		assertTrue(vector.length == 2);
		double x = vector[0];
		double y = vector[1];
		double x2y2 = x * x + y * y;
		double p = Math.sin(Math.sqrt(x2y2));
		double n = p * p - 0.5;
		double q = 1.0 + 0.001 * x2y2;
		double d = q * q;
		double result = 0.5 + (n / d);
		return (result);
	}

	public static final void assertTrue(boolean b) {
		if (!b) {
			throw new AssertionError("False is not allowed.");
		}
	}

	public static final void assertTrue(boolean b, String msg) {
		if (!b) {
			throw new AssertionError(msg);
		}
	}

	public static final double distance(double[] a, double[] b) {
		assertTrue(a.length == b.length);
		int len = a.length;
		double sum = 0.0;
		for (int i = 0; i < len; i++) {
			double t = a[i] - b[i];
			sum += t * t;
		}
		return Math.sqrt(sum);
	}

	/**
	 * 判断该序列是否无重元。
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isUnrepeated(double[] s) {
		for (int i = 0; i < s.length; i++) {
			for (int j = i + 1; j < s.length; j++) {
				if (s[i] == s[j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 获取屏蔽位。
	 * 
	 * @param mask
	 * @param bit
	 * @return
	 */
	public static byte getMaskBit(long mask, int bit) {
		assertTrue(bit >= 1 && bit <= 64);
		return (byte) ((mask >>> (64 - bit)) & 1);
	}

	public static void swap(int[] g, int p1, int p2) {
		if (p1 == p2)
			return;
		g[p1] ^= g[p2];
		g[p2] ^= g[p1];
		g[p1] ^= g[p2];
	}

	public static void swap(byte[] g1, byte[] g2, int i) {
		Functions.assertTrue(g1 != g2);
		g1[i] ^= g2[i];
		g2[i] ^= g1[i];
		g1[i] ^= g2[i];
	}

	public static void swap(int[] g1, int[] g2, int i) {
		Functions.assertTrue(g1 != g2);
		g1[i] ^= g2[i];
		g2[i] ^= g1[i];
		g1[i] ^= g2[i];
	}

	public static void swap(double[] g1, double[] g2, int i) {
		Functions.assertTrue(g1 != g2);
		double tmp = g1[i];
		g1[i] = g2[i];
		g2[i] = tmp;
	}
}
