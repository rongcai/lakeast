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

package org.lakeast.pso.gen;

import org.lakeast.common.Constraint;
import org.lakeast.pso.skeleton.AbstractSwarm;
import org.lakeast.pso.skeleton.DynamicFatorGenerator;
import org.lakeast.pso.skeleton.Factor;

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
