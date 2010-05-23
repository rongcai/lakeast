package org.lakeast.pso.skeleton;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public interface Domain {
	public void reset();

	public ConstraintSet getConstraintSet();

	/**
	 * 计算适应值。
	 * 
	 * @param location
	 * @return
	 */
	public double calculateFitness(Location location);

	public double[] getSolution();

	public double getBestValue();

	/**
	 * 直接返回目标函数值。
	 * 
	 * @param x
	 * @return
	 */
	public double getValueOfFunction(double[] x);
}