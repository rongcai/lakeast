/*
 * Copyright Eric Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.lakeast.model.ca;

/**
 * 根据评标方法-平均标价格进行依概率随机比较。
 *
 * @author Eric Wang
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
