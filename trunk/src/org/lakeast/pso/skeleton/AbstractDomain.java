package org.lakeast.pso.skeleton;

/**
 * 这个的类的目的是提供一个接口，使得可以将粒子的位置向量解释到离散空间， 同时不干扰粒子的更新方式。
 * 
 * @author WANG Zhen
 * @date 2007-5-20
 */
public abstract class AbstractDomain implements Domain {
	private ConstraintSet cSet;
	private double[] solution;
	private double best;
	private double max = Double.NEGATIVE_INFINITY;

	public void reset() {
		max = Double.NEGATIVE_INFINITY;
	}

	/**
	 * @return the max
	 */
	public double getBestValue() {
		return best;
	}

	/**
	 * 默认适值计算模板，重载后要仍然应该调用该方法来进行适值计算。
	 */
	public double calculateFitness(Location location) {
		return calculateFitness2(location);
	}

	/**
	 * 默认适值计算方法。
	 * 
	 * @param location
	 * @return
	 */
	private double calculateFitness2(Location location) {
		double x[] = decode(location);
		double v = getValueOfFunction(x);
		double fitness = getFitness(v);
		if (fitness > max) {
			max = fitness;
			best = v;
			solution = x;
		}
		return fitness;
	}

	/**
	 * 直接将原始函数值作为适值返回，可重载，比如求最小化问题可以将函数值加负号作为适值。
	 * 
	 * @param v
	 * @return
	 */
	protected double getFitness(double v) {
		return v;
	}

	/**
	 * @return ConstraintSet
	 */
	public ConstraintSet getConstraintSet() {
		return cSet;
	}

	protected final void setConstraintSet(ConstraintSet cSet) {
		this.cSet = cSet;
	}

	/**
	 * 将空间坐标解码为解域上的向量。默认行为是直接解码，但是重载后解码的值未必是一定的，可能是随机的。
	 * 
	 * @see org.lakeast.pso.skeleton.Domain#decode(org.lakeast.pso.skeleton.Location)
	 */
	public double[] decode(Location location) {
		return decode2(location);
	}

	/**
	 * 直接映射。
	 * 
	 * @param location
	 * @return
	 */
	private double[] decode2(Location location) {
		int len = location.getDimensionsCount();
		double x[] = new double[len];
		for (int i = 0; i < x.length; i++) {
			x[i] = location.getCoordinate(i + 1);
		}
		return x;
	}

	/**
	 * @return the best
	 */
	public double[] getSolution() {
		return solution;
	}
}
