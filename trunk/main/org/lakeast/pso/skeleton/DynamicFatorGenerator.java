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

package org.lakeast.pso.skeleton;

import org.lakeast.common.Constraint;

public abstract class DynamicFatorGenerator extends AbstractFactorGenerator {
	private final int limit;

	private final Constraint constraint;

	public Constraint getConstraint() {
		return constraint;
	}

	public DynamicFatorGenerator(int limit, Constraint constraint,
			Factor initialFactor) {
		super(initialFactor);
		if (limit < 2)
			throw new IllegalArgumentException(
					"the upper limit of generation must be greater than one.");
		this.limit = limit;
		this.constraint = constraint;
	}

	public int getLimit() {
		return limit;
	}
}
