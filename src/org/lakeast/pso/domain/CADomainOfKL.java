/**
 * 
 */
package org.lakeast.pso.domain;

import java.util.Arrays;

import org.lakeast.common.Functions;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.pso.skeleton.Location;

/**
 * @author WANG Zhen
 * 
 */
public class CADomainOfKL extends BasicCADomain {

	public CADomainOfKL(QuotedPriceMatrix matrix, double probability) {
		super(matrix, probability);
	}

	public CADomainOfKL(QuotedPriceMatrix matrix) {
		super(matrix);
	}

	/**
	 * @see org.lakeast.pso.skeleton.AbstractDomain#calculateFitness(org.lakeast.pso.skeleton.Location)
	 */
	@Override
	public double calculateFitness(Location location) {
		localSearch(location);
		return super.calculateFitness(location);
	}

	/**
	 * 
	 * @param location
	 * @param dimension
	 * @return
	 */
	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getVelocity(dimension));
	}

	/**
	 * 采用速度向量生成离散值并局部查找，然后替代当前粒子。
	 * 
	 * @param location
	 */
	private void localSearch(Location location) {
		int len = location.getDimensionsCount();
		Integer[] ids = new Integer[len];
		int sid = 0;
		for (int i = 0; i < len; i++) {
			if (isOne(location, i + 1)) {
				ids[sid++] = i;
			}
			location.setCoordinate(i + 1, 0.0);
		}
		if (needSort)
			Arrays.sort(ids, 0, sid, comparator);
		if (sid > 0) {
			double max = 0.0;
			int k = 0;
			int[] t = new int[len];
			int[] opt = new int[len];
			int opt_len = 0;
			for (int p = 0; p < Math.min(sid, 10); p++) {
				k = 0;
				t[k++] = ids[p];
				for (int i = 0; i < sid; i++) {
					if (i == p)
						continue;// unnecessary compatibility check
					boolean legal = true;
					for (int j = 0; j < k; j++) {
						if (!matrix.isCompatible(ids[i], t[j])) {
							legal = false;
							break;
						}
					}
					if (legal) {
						t[k++] = ids[i];
					}
				}
				double[] v = new double[len];
				for (int i = 0; i < k; i++) {
					v[t[i]] = 1.0;
				}
				double f = getValueOfFunction(v);
				if (f > max) {
					max = f;
					System.arraycopy(t, 0, opt, 0, k);
					opt_len = k;
				}
			}
			for (int i = 0; i < opt_len; i++) {
				location.setCoordinate(opt[i] + 1, 1.0);
			}
		}
	}

	@Override
	public String toString() {
		return "CADomainOfRL(" + getConstraintSet() + ")";
	}
}
