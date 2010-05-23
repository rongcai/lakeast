/**
 * 
 */
package org.lakeast.ga.domain;

import org.lakeast.common.Functions;
import org.lakeast.common.InitializeException;
import org.lakeast.ga.skeleton.AbstractDomain;
import org.lakeast.ga.skeleton.ConstraintSet;
import org.lakeast.model.tsp.DistanceMatrix;

/**
 * @author WANG Zhen
 * 
 */
public class TSPDomain extends AbstractDomain {
	private final DistanceMatrix matrix;

	public TSPDomain(int dimension) throws InitializeException {
		this.matrix = new DistanceMatrix(dimension, true);
		ConstraintSet cSet = new ConstraintSet(dimension - 1);
		setConstraintSet(cSet);
	}

	/**
	 * @see org.lakeast.ga.skeleton.AbstractDomain#getValueOfFunction(double[])
	 */
	public double getValueOfFunction(double[] x) {
		int[] arr = new int[x.length];
		for (int i = 0; i < x.length; i++) {
			arr[i] = (int) x[i];
		}
		return Functions.TSP(arr, matrix.getValues());
	}

	@Override
	public String toString() {
		return "TSPDomain(" + getConstraintSet() + ")";
	}

	/*
	 * TSP问题中目标是找到一个f'(x)<<-1,x>>1的变换函数。 一方面要防止进化停滞，另一方面要防止陷入局部最优。
	 * 使得值越大的个体适应值越小，且使得适值之间的差异适当，不太大也不太小。
	 */
	@Override
	protected double getFitness(double value) {
		return -value + (this.getConstraintSet().getDimensionsCount() + 1)
				* DistanceMatrix.MAX + 0.00001;
	}
}
