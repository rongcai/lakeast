/**
 * 
 */
package org.lakeast.model.knapsack;

/**
 * @author WANG Zhen
 * 
 */
public class RandomizedUnitPriceComparator extends RandomizedPriceComparator {
	final double[] weights;

	@Override
	public String toString() {
		return "RandomizedUnitPriceComparator(Probability: " + probability
				+ ")";
	}

	public RandomizedUnitPriceComparator(double[] prices, double[] weights,
			double probability) {
		super(prices, probability);
		this.weights = weights;
	}

	/**
	 * 降序排列
	 */
	@Override
	public int compare(Integer a, Integer b) {
		if (prices[a] / weights[a] - prices[b] / weights[b] > 0) {
			return Math.random() < probability ? -1 : 1;
		} else {
			return Math.random() < probability ? 1 : -1;
		}
	}
}
