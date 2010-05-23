package org.lakeast.model.ca;

/**
 * 代表一个竞标，包括标价格和标所包含的物品，以布尔量表示，某位为true， 表示对应物品在这个竞标中。
 * 
 * @author WANG Zhen
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
