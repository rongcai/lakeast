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

package org.lakeast.model.knapsack;

import java.util.Comparator;

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
