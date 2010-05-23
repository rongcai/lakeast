/**
 * 
 */
package org.lakeast.pso.domain;

import java.util.Arrays;

import org.lakeast.common.Functions;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.pso.skeleton.Location;

/**
 * 基于K-DSPO求解WDP问题
 * 
 * @author WANG Zhen
 * 
 */
public class CADomainOfK extends BasicCADomain {

	public CADomainOfK(QuotedPriceMatrix matrix, double probability) {
		super(matrix, probability);
	}

	public CADomainOfK(QuotedPriceMatrix matrix) {
		super(matrix);
	}

	/**
	 * @see org.lakeast.pso.skeleton.AbstractDomain#calculateFitness(org.lakeast.pso.skeleton.Location)
	 */
	@Override
	public double calculateFitness(Location location) {
		repair(location);
		return super.calculateFitness(location);
	}

	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getVelocity(dimension));
	}

	private void repair(Location location) {
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
			int[] t = new int[len];
			int k = 0;
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
				location.setCoordinate(t[i] + 1, 1.0);
			}
		}
	}

	@Override
	public String toString() {
		return "CADomainOfK(" + getConstraintSet() + ")";
	}
}
