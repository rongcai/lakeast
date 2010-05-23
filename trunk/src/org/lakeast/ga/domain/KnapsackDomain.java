/**
 * 
 */
package org.lakeast.ga.domain;

import java.util.Arrays;

import org.lakeast.common.Functions;
import org.lakeast.ga.skeleton.AbstractChromosome;
import org.lakeast.ga.skeleton.AbstractDomain;
import org.lakeast.ga.skeleton.ConstraintSet;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.ga.skeleton.Population.RepairCallback;
import org.lakeast.model.knapsack.RandomizedPriceComparator;
import org.lakeast.model.knapsack.RandomizedUnitPriceComparator;

/**
 * @author WANG Zhen
 * 
 *         May 21, 2007
 */
public class KnapsackDomain extends AbstractDomain {
	private final double[] prices;
	private final double[] weights;
	private final double capacity;
	private final RandomizedPriceComparator comparator;
	private final boolean needSort;

	public KnapsackDomain(double[] prices, double[] weights, double capacity,
			double probability) {
		ConstraintSet cSet = new ConstraintSet(prices.length);
		setConstraintSet(cSet);
		this.prices = prices;
		this.weights = weights;
		this.capacity = capacity;
		this.comparator = new RandomizedUnitPriceComparator(prices, weights,
				probability);
		this.needSort = true;
	}

	public KnapsackDomain(double[] prices, double[] weights, double capacity) {
		ConstraintSet cSet = new ConstraintSet(prices.length);
		setConstraintSet(cSet);
		this.prices = prices;
		this.weights = weights;
		this.capacity = capacity;
		this.comparator = null;
		this.needSort = false;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KnapsackDomain(ConstraintSet: " + getConstraintSet()
				+ ", Comparator: " + comparator + ")";
	}

	public double getValueOfFunction(double[] x) {
		return Functions.knapsack(x, prices);
	}

	@Override
	public void calculateFitness(Population p) {
		p.repair(new RepairCallback() {

			public void repair(AbstractChromosome chromosome) {
				int len = chromosome.getDimensionsCount();
				Integer[] ids = new Integer[len];
				int sid = 0;
				for (int i = 0; i < chromosome.getDimensionsCount(); i++) {
					if (chromosome.getValueOfDimension(i + 1) == 1.0) {
						ids[sid++] = i;
					}
					chromosome.setValueOfDimension(i + 1, 0.0);
				}
				if (needSort) {
					Arrays.sort(ids, 0, sid, comparator);
				}
				if (sid > 0) {
					double sum = 0.0;
					for (int i = 0; i < sid; i++) {
						sum += weights[ids[i]];
						if (sum <= capacity) {
							chromosome.setValueOfDimension(ids[i] + 1, 1.0);
						} else
							break;
					}
				}
			}

		});
		super.calculateFitness(p);
	}
}
