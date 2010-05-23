package org.lakeast.pso.domain;

import java.util.Arrays;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.model.knapsack.RandomizedPriceComparator;
import org.lakeast.model.knapsack.RandomizedUnitPriceComparator;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;
import org.lakeast.pso.skeleton.Location;

/**
 * 基于PM-DPSO求解01背包问题。
 * 
 * @author WANG Zhen
 * 
 */
public class KnapsackDomain extends AbstractDomain {
	private final double[] prices;
	private final double[] weights;
	private final double capacity;
	private final RandomizedPriceComparator comparator;
	private final boolean needSort;

	public KnapsackDomain(double[] prices, double[] weights, double capacity,
			double probability) {
		Constraint dc = new Constraint(-200, 200, BoundaryType.STICK);
		Constraint vc = new Constraint(-200, 200, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(prices.length, dc, vc);
		setConstraintSet(cSet);
		this.prices = prices;
		this.weights = weights;
		this.capacity = capacity;
		this.comparator = new RandomizedUnitPriceComparator(prices, weights,
				probability);
		this.needSort = true;
	}

	public KnapsackDomain(double[] prices, double[] weights, double capacity) {
		Constraint dc = new Constraint(-200, 200, BoundaryType.STICK);
		Constraint vc = new Constraint(-200, 200, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(prices.length, dc, vc);
		setConstraintSet(cSet);
		this.prices = prices;
		this.weights = weights;
		this.capacity = capacity;
		this.comparator = null;
		this.needSort = false;
	}

	@Override
	public String toString() {
		return "KnapsackDomain(ConstraintSet: " + getConstraintSet()
				+ ", Comparator: " + comparator + ")";
	}

	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getCoordinate(dimension));
	}

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
		if (needSort)
			Arrays.sort(ids, 0, sid, comparator);
		if (sid > 0) {
			double sum = 0.0;
			for (int i = 0; i < sid; i++) {
				sum += weights[ids[i]];
				if (sum <= capacity) {
					x[ids[i]] = 1.0;
				} else
					break;
			}
		}
		return x;
	}

	public double getValueOfFunction(double[] x) {
		return Functions.knapsack(x, prices);
	}
}
