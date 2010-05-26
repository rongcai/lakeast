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
 * 代表一个竞标，包括标价格和标所包含的物品，以布尔量表示，某位为true， 表示对应物品在这个竞标中。
 *
 * @author Eric Wang
 *
 */
public class Bid {
	final boolean[] goods;
	private final double quotedPrice;
	private final int id;
	private final int amount;

	protected int getId() {
		return id;
	}

	public double getQuotedPrice() {
		return quotedPrice;
	}

	public Bid(boolean[] goods, double quotedPrice, int id) {
		this.id = id;
		this.goods = goods;
		this.quotedPrice = quotedPrice;
		int sum = 0;
		for (int i = 0; i < goods.length; i++) {
			if (goods[i])
				sum++;
		}
		this.amount = sum;
	}

	public int getGoodsAmount() {
		return amount;
	}
}
