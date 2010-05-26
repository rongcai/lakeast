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

package org.lakeast.ga.domain;

import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.ga.skeleton.AbstractDomain;
import org.lakeast.ga.skeleton.ConstraintSet;

/**
 * @author Eric Wang
 *
 *         May 21, 2007
 */
public class GriewankDomain extends AbstractDomain {
	public GriewankDomain() {
		Constraint dc = new Constraint(-1, 1);
		ConstraintSet cSet = new ConstraintSet(5, dc);
		cSet.setBits(32);
		setConstraintSet(cSet);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GriewankDomain(" + getConstraintSet() + ")";
	}

	public double getValueOfFunction(double[] x) {
		return Functions.Griewank(x);
	}

	@Override
	protected double getFitness(double value) {
		return 1 / (value + 1);// values大于-1
	}
}
