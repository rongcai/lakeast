/**
 * 
 */
package org.lakeast.model.ca;

import java.util.Comparator;

/**
 * @author WANG Zhen
 * 
 */
public class RandomizedPriceComparator implements Comparator<Integer> {

	@Override
	public String toString() {
		return "RandomizedBidComparator(Probability: " + probability + ")";
	}

	final double probability;
	final QuotedPriceMatrix matrix;

	public RandomizedPriceComparator(QuotedPriceMatrix matrix,
			double probability) {
		this.matrix = matrix;
		this.probability = probability;
	}

	/**
	 * 降序排列
	 */
	public int compare(Integer a, Integer b) {
		if (matrix.getBid(a).getQuotedPrice()
				- matrix.getBid(b).getQuotedPrice() > 0) {
			return Math.random() < probability ? -1 : 1;
		} else {
			return Math.random() < probability ? 1 : -1;
		}
	}
}
