/**
 * 
 */
package org.lakeast.model.ca;

/**
 * 根据评标方法-平均标价格进行依概率随机比较。
 * 
 * @author WANG Zhen
 * 
 */
public class RandomizedAveragePriceComparator extends RandomizedPriceComparator {

	@Override
	public int compare(Integer a, Integer b) {
		if ((matrix.getBid(a).getQuotedPrice() / matrix.getBid(a)
				.getGoodsAmount())
				- (matrix.getBid(b).getQuotedPrice() / matrix.getBid(b)
						.getGoodsAmount()) > 0) {
			return Math.random() < probability ? -1 : 1;
		} else {
			return Math.random() < probability ? 1 : -1;
		}
	}

	public RandomizedAveragePriceComparator(QuotedPriceMatrix matrix,
			double probability) {
		super(matrix, probability);
	}

	@Override
	public String toString() {
		return "RandomizedAveragePriceComparator(Probability: " + probability
				+ ")";
	}

}
