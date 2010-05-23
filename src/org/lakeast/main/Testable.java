/**
 * 
 */
package org.lakeast.main;

import org.lakeast.common.Constraint;

/**
 * @author WANG Zhen
 * 
 */
public interface Testable {

	public static final int PRECISION = 50;

	public static final String DATAPATH = "C:\\";

	public boolean iterate(int numberOfIterations, Constraint exitCondition);

	public void reset();

	public int size();

	public int getDimensionsCount();

	public String getSimpleDescription();

	public int getGeneration();

	/**
	 * 返回最优解的适应值。基于同一个群体上的最优解。
	 * 
	 * @return 最优解的适应值。
	 */
	public double getGlobalBestFitness();

	/**
	 * 返回最优个体。基于同一个群体上的最优解。
	 * 
	 * @return 最优个体。
	 */
	public Object getGlobalBest();

	/**
	 * 返回最优的解向量。基于同一个Domain上的最优解。
	 * 
	 * @return 最优解的解向量。
	 */
	public double[] getSolution();

	/**
	 * 返回最优解的函数值。基于同一个Domain上的最优解。
	 * 
	 * @return 最优解的函数值。
	 */
	public double getBestValue();

	public boolean iterate(int numberOfIterations, Constraint exitCondition,
			double[] y);
}
