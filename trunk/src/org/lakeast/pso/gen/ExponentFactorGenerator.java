package org.lakeast.pso.gen;

import org.lakeast.common.Constraint;
import org.lakeast.pso.skeleton.AbstractSwarm;
import org.lakeast.pso.skeleton.DynamicFatorGenerator;
import org.lakeast.pso.skeleton.Factor;

/**
 * @author WANG Zhen
 * @date 2007-5-26
 */
public class ExponentFactorGenerator extends DynamicFatorGenerator {
	private double exponent;

	/**
	 * @return the exponent
	 */
	public double getExponent() {
		return exponent;
	}

	public ExponentFactorGenerator(int limit, Constraint constraint,
			double exponent, Factor initialFactor) {
		super(limit, constraint, initialFactor);
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
		Factor initialFactor = getInitialFactor();
		double multiplier = Math.pow((limit - generation) / (double) limit,
				exponent);
		double upper = getConstraint().getMaximum();
		double lower = getConstraint().getMinimum();
		double inertiaWeight = lower + (upper - lower) * multiplier;
		double iWeight = initialFactor.getIndividualityWeight();
		double sWeight = initialFactor.getSocialityWeight();
		return new Factor(inertiaWeight, iWeight, sWeight);
	}

	public String toString() {
		return "ExponentFactorGenerator(InitialFactor: " + getInitialFactor()
				+ ", Limit: " + getLimit() + ", Constraint: " + getConstraint()
				+ ", Exponent: " + getExponent() + ")";
	}
}
