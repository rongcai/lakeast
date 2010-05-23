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
 * 基于K-DPSO求解01背包问题
 * 
 * @author WANG Zhen
 * 
 */
public class KnapsackDomainOfK extends AbstractDomain {
	private final double[] prices;
	private final double[] weights;
	private final double capacity;
	private final RandomizedPriceComparator comparator;
	private final boolean needSort;

	public KnapsackDomainOfK(double[] prices, double[] weights,
			double capacity, double probability) {
		Constraint dc = new Constraint(0, 1, BoundaryType.STICK);
		Constraint vc = new Constraint(-10, 10, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(prices.length, dc, vc);
		setConstraintSet(cSet);
		this.prices = prices;
		this.weights = weights;
		this.capacity = capacity;
		this.comparator = new RandomizedUnitPriceComparator(prices, weights,
				probability);
		this.needSort = true;
	}

	public KnapsackDomainOfK(double[] prices, double[] weights, double capacity) {
		Constraint dc = new Constraint(0, 1, BoundaryType.STICK);
		Constraint vc = new Constraint(-10, 10, BoundaryType.STICK);
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
		return "KnapsackDomainOfK(ConstraintSet: " + getConstraintSet()
				+ ", Comparator: " + comparator + ")";
	}

	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getVelocity(dimension));
	}

	@Override
	public double calculateFitness(Location location) {
		repair(location);
		return super.calculateFitness(location);
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
			double sum = 0.0;
			for (int i = 0; i < sid; i++) {
				sum += weights[ids[i]];
				if (sum <= capacity) {
					location.setCoordinate(ids[i] + 1, 1.0);
				} else
					break;
			}
		}
	}

	public double getValueOfFunction(double[] x) {
		return Functions.knapsack(x, prices);
	}
}
