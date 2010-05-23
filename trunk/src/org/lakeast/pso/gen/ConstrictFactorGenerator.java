package org.lakeast.pso.gen;

import org.lakeast.common.Constraint;
import org.lakeast.pso.skeleton.AbstractSwarm;
import org.lakeast.pso.skeleton.DynamicFatorGenerator;
import org.lakeast.pso.skeleton.Factor;

/**
 * @author WANG Zhen
 * @date 2007-5-26
 */
public class ConstrictFactorGenerator extends DynamicFatorGenerator {
	private double exponent;

	/**
	 * @return the exponent
	 */
	public double getExponent() {
		return exponent;
	}

	// constraint应属于[0,1]区间，这是公式c1+c2=(w+1)^2成立的条件
	public ConstrictFactorGenerator(int limit, Constraint constraint,
			double exponent) {
		super(limit, constraint, null);
		this.exponent = exponent;
	}

	public Factor generateFactor(AbstractSwarm swarm) {
		int generation = swarm.getGeneration();
		if (generation < 1)
			throw new IllegalArgumentException(
					"the generation must be not less than one.");
		int limit = getLimit();
		if (generation > limit)
			generation = limit;
		double multiplier = Math.pow((limit - generation) / (double) limit,
				exponent);
		double upper = getConstraint().getMaximum();
		double lower = getConstraint().getMinimum();
		double inertiaWeight = lower + (upper - lower) * multiplier;
		double iWeight = 0.5 * Math.pow(inertiaWeight, 2.0) + inertiaWeight
				+ 0.5;
		double sWeight = iWeight;
		return new Factor(inertiaWeight, iWeight, sWeight);
	}

	public String toString() {
		return "ConstrictFactorGenerator(Limit: " + getLimit()
				+ ", Constraint: " + getConstraint() + ", Exponent: "
				+ getExponent() + ")";
	}
}