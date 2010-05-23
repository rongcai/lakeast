/**
 * 
 */
package org.lakeast.model.knapsack;

import java.util.Comparator;

/**
 * @author WANG Zhen
 * 
 */
public class RandomizedPriceComparator implements Comparator<Integer> {
	final double[] prices;
	final double probability;

	@Override
	public String toString() {
		return "RandomizedPriceComparator(Probability: " + probability + ")";
	}

	public RandomizedPriceComparator(double[] prices, double probability) {
		this.prices = prices;
		this.probability = probability;
	}

	/**
	 * 降序排列
	 */
	public int compare(Integer a, Integer b) {
		if (prices[a] - prices[b] > 0) {
			return Math.random() < probability ? -1 : 1;
		} else {
			return Math.random() < probability ? 1 : -1;
		}
	}
}
