package org.lakeast.model.ca;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.lakeast.common.InitializeException;

/**
 * 
 * 报价矩阵，实现输入文件的读取以及解空间的构造。
 * 
 * @author WANG Zhen
 * 
 */
public class QuotedPriceMatrix {
	final Bid[] bids;
	private final int bidsAmount;
	private final int goodsAmount;
	private final boolean[][] cache;
	private final double[] itemAverageValue;
	private final double[] itemMaxValue;
	private final int[] NH;
	private final int[] NL;
	private final int[] NM;

	public int getBidsAmount() {
		return bidsAmount;
	}

	public boolean isCompatible(int index1, int index2) {
		return cache[index1][index2];
	}

	private boolean checkCompatibility(Bid one, Bid another) {
		for (int i = 0; i < goodsAmount; i++) {
			if (one.goods[i] && another.goods[i])
				return false;
		}
		return true;
	}

	public Bid getBid(int index) {
		if (index < 0 || index >= bidsAmount)
			return null;
		return bids[index];
	}

	public double getBAVG(int index) {
		if (index < 0 || index >= bidsAmount)
			return 0.0;
		return bids[index].getQuotedPrice() / bids[index].getGoodsAmount();
	}

	public double getIVAG(int index) {
		if (index < 0 || index >= goodsAmount)
			return 0.0;
		return itemAverageValue[index];
	}

	public double getIMAX(int index) {
		if (index < 0 || index >= goodsAmount)
			return 0.0;
		return itemMaxValue[index];
	}

	public int getNH(int index) {
		if (index < 0 || index >= bidsAmount)
			return 0;
		return NH[index];
	}

	public int getNL(int index) {
		if (index < 0 || index >= bidsAmount)
			return 0;
		return NL[index];
	}

	public int getNM(int index) {
		if (index < 0 || index >= bidsAmount)
			return 0;
		return NM[index];
	}

	public QuotedPriceMatrix(String filename) throws InitializeException {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			throw new InitializeException(e);
		}
		try {
			sc.skip(Pattern.compile("((%[^\\n]+\\n)|(\\s*\\n))+",
					Pattern.DOTALL));
		} catch (NoSuchElementException ex) {
		}
		sc.findInLine("goods ([0-9]+)");
		goodsAmount = Integer.parseInt(sc.match().group(1));
		try {
			sc.skip(Pattern.compile("((%[^\\n]+\\n)|(\\s*\\n))+",
					Pattern.DOTALL));
		} catch (NoSuchElementException ex) {
		}
		sc.findInLine("bids ([0-9]+)");
		bidsAmount = Integer.parseInt(sc.match().group(1));
		try {
			sc.skip(Pattern.compile("((%[^\\n]+\\n)|(\\s*\\n))+",
					Pattern.DOTALL));
		} catch (NoSuchElementException ex) {
		}
		sc.findInLine("dummy ([0-9]+)");
		bids = new Bid[bidsAmount];

		itemAverageValue = new double[goodsAmount];
		itemMaxValue = new double[goodsAmount];

		int[] count = new int[goodsAmount];
		for (int i = 0; i < bidsAmount; i++) {
			// System.out.println(i);
			boolean[] g = new boolean[goodsAmount];
			try {
				sc.skip(Pattern.compile("((%[^\\n]+\\n)|(\\s*\\n))+",
						Pattern.DOTALL));
			} catch (NoSuchElementException ex) {
			}
			sc
					.findInLine("[0-9]+[\\t ]+([0-9]+(?:\\.[0-9]+)?)[\\t ]+((?:[0-9]+[\\t ]+)+)#");
			MatchResult mr = sc.match();
			String[] arr = mr.group(2).split("[\\t ]+");
			double price = Double.parseDouble(mr.group(1));
			for (int j = 0; j < arr.length; j++) {
				int itemId = Integer.parseInt(arr[j]);
				g[itemId] = true;
				double avg = price / arr.length;
				itemAverageValue[itemId] += avg;
				count[itemId]++;
				if (itemMaxValue[itemId] < avg) {
					itemMaxValue[itemId] = avg;
				}
			}
			bids[i] = new Bid(g, price, i);
		}
		sc.close();

		cache = new boolean[bidsAmount][bidsAmount];
		NH = new int[bidsAmount];
		NL = new int[bidsAmount];
		NM = new int[bidsAmount];

		for (int i = 0; i < goodsAmount; i++) {
			if (itemAverageValue[i] != 0.0) {
				itemAverageValue[i] /= count[i];
			}
		}

		for (int i = 0; i < bidsAmount; i++) {
			for (int j = i + 1; j < bidsAmount; j++) {
				cache[i][j] = cache[j][i] = checkCompatibility(bids[i], bids[j]);
			}
			for (int j = 0; j < goodsAmount; j++) {
				if (bids[i].goods[j]) {
					double avg = getBAVG(i);
					if (avg >= itemAverageValue[j]) {
						NH[i]++;
					} else {
						NL[i]++;
					}
					if (avg >= itemMaxValue[j]) {
						NM[i]++;
					}
				}
			}
		}
	}
}