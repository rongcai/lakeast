package org.lakeast.pso.domain;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.common.InitializeException;
import org.lakeast.model.tsp.DistanceMatrix;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;
import org.lakeast.pso.skeleton.Location;

/**
 * 由于TSP问题中各个分量对全局适值的影响过分互相依赖，因而PSO的这种计算速度时单独计算各个 分量的方法效果不是很好。
 * 
 * @author WANG Zhen
 * 
 */
public class TSPDomain extends AbstractDomain {
	private final DistanceMatrix matrix;
	private static final double ELIPSON = 0.00000000001;

	public TSPDomain(int dimension) throws InitializeException {
		matrix = new DistanceMatrix(dimension, true);
		Constraint dc = new Constraint(1, dimension - ELIPSON,
				BoundaryType.STICK);// 使得取整后位置在{1,...,dimension-1}平均分布
		Constraint vc = new Constraint(-dimension + 2, dimension - 2,
				BoundaryType.STICK);// [-13,13]
		ConstraintSet cSet = new ConstraintSet(dimension - 1, dc, vc);
		setConstraintSet(cSet);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TSPDomain(" + getConstraintSet() + ")";
	}

	/*
	 * @see org.lakeast.pso.skelecton.AbstractDomain#calculateFitness(org
	 * .starf.algorithm.pso.skelecton.Location)
	 */
	@Override
	public double calculateFitness(Location location) {
		repair(location);
		return super.calculateFitness(location);
	}

	/**
	 * 修补位置向量为一个序列。
	 * 
	 * @param location
	 */
	private void repair(Location location) {
		int len = location.getDimensionsCount();
		int[] x = new int[len];
		for (int i = 0; i < len; i++) {
			x[i] = (int) location.getCoordinate(i + 1);
		}
		Functions.repairSequence(x);
		for (int i = 0; i < len; i++) {
			location.setCoordinate(i + 1, x[i]);
		}
	}

	/*
	 * @see org.lakeast.pso.skelecton.AbstractDomain#decode(org.lakeast
	 * .pso.skelecton.Location)
	 */
	@Override
	public double[] decode(Location location) {
		int len = location.getDimensionsCount();
		double x[] = new double[len];
		for (int i = 0; i < x.length; i++) {
			x[i] = Math.floor(location.getCoordinate(i + 1));
		}
		return x;
	}

	@Override
	protected double getFitness(double v) {
		return -v;
	}

	public double getValueOfFunction(double[] x) {
		int[] arr = new int[x.length];
		for (int i = 0; i < x.length; i++) {
			arr[i] = (int) x[i];
		}
		return Functions.TSP(arr, matrix.getValues());
	}
}
