/**
 * 
 */
package org.lakeast.pso.domain;

import java.util.Arrays;

import org.lakeast.common.Functions;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.pso.skeleton.Location;

/**
 * 基于PM-DPSO求解WDP问题。
 * 
 * @author WANG Zhen
 * 
 */
public class CADomain extends BasicCADomain {

	public CADomain(QuotedPriceMatrix matrix, double probability) {
		super(matrix, probability);
	}

	public CADomain(QuotedPriceMatrix matrix) {
		super(matrix);
	}

	/**
	 * 
	 * @param location
	 * @param dimension
	 * @return
	 */
	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getCoordinate(dimension));
	}

	/**
	 * 随机解码。
	 * 
	 * @see org.lakeast.pso.skeleton.AbstractDomain#decode(org.lakeast.pso.skeleton.Location)
	 */
	/*
	 * 耗费cpu50%以上。
	 */
	@Override
	public double[] decode(Location location) {
		int len = location.getDimensionsCount();
		double[] x = new double[len];
		Integer[] ids = new Integer[len];
		int sid = 0;
		for (int i = 0; i < len; i++) {
			if (isOne(location, i + 1)) {
				ids[sid++] = i;
			}
		}
		/*
		 * 排序大约占用%40的cpu时间。
		 */
		if (needSort)
			Arrays.sort(ids, 0, sid, comparator);
		if (sid > 0) {
			/*
			 * 存储有效的标的id号。
			 */
			int[] t = new int[len];
			int k = 0;// 有效标的数目
			t[k++] = ids[0];
			for (int i = 1; i < sid; i++) {
				boolean legal = true;
				for (int j = 0; j < k; j++) {
					if (!matrix.isCompatible(ids[i], t[j])) {
						legal = false;
						break;
					}
				}
				if (legal)
					t[k++] = ids[i];
			}
			for (int i = 0; i < k; i++) {
				x[t[i]] = 1.0;
			}
		}
		return x;
	}

	@Override
	public String toString() {
		return "CADomain(ConstraintSet: " + getConstraintSet()
				+ ", Comparator: " + comparator + ")";
	}
}