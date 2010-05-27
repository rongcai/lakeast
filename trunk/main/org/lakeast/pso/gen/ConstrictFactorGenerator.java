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

/**
 * DCF-PSO的实现类，可以根据迭代次数同步更新w,c1,c2。
 *
 * @author Eric Wang
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