/**
 * 
 */
package org.lakeast.ga.skeleton;

/**
 * @author WANG Zhen
 * 
 */
public interface Domain {

	public void reset();

	public void calculateFitness(Population p);

	public ConstraintSet getConstraintSet();

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
