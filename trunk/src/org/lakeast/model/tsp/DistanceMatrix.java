package org.lakeast.model.tsp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.lakeast.common.InitializeException;
import org.lakeast.common.Randoms;

public class DistanceMatrix {
	public static final double MIN = 1000;
	public static final double MAX = 10000;

	/**
	 * 距离矩阵给定，该TSP问题图的几何形状就完全确定了。
	 */
	private final double[][] values;

	public double[][] getValues() {
		return values;
	}

	public DistanceMatrix(int dimension, boolean fromFile)
			throws InitializeException {
		try {
			if (!fromFile) {
				values = new double[dimension][dimension];
				for (int i = 0; i < dimension; i++) {
					for (int j = i + 1; j < dimension; j++) {
						values[i][j] = values[j][i] = Randoms.doubleInRange(
								MIN, MAX);
					}
				}
				ObjectOutputStream out = new ObjectOutputStream(
						new FileOutputStream(new File("D:\\tmp.ser")));
				out.writeObject(values);
				out.close();
			} else {
				ObjectInputStream in = new ObjectInputStream(
						new FileInputStream(new File("D:\\tmp.ser")));
				values = (double[][]) in.readObject();
				in.close();
			}
		} catch (Exception e) {
			throw new InitializeException(e);
		}
	}
}
