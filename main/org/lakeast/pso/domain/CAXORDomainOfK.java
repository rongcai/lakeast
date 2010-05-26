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

package org.lakeast.pso.domain;

import java.util.Arrays;

import org.lakeast.common.Functions;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.pso.skeleton.Location;

/**
 * 基于K-DPSO求解基于XOR标集合的WDP问题。
 *
 * @author Eric Wang
 *
 */
public class CAXORDomainOfK extends BasicCADomain {
	public int[] agents;

	public CAXORDomainOfK(QuotedPriceMatrix matrix, double probability,
			int[] agents) {
		super(matrix, probability);
		this.agents = agents;
	}

	/**
	 * @see org.lakeast.pso.skeleton.AbstractDomain#calculateFitness(org.lakeast.pso.skeleton.Location)
	 */
	@Override
	public double calculateFitness(Location location) {
		repair(location);
		return super.calculateFitness(location);
	}

	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getVelocity(dimension));
	}

	private void repair(Location location) {
		int len = location.getDimensionsCount();
		Integer[] ids = new Integer[len];
		int sid = 0;
		for (int i = 0; i < len; i++) {
			if (isOne(location, i + 1)) {
				ids[sid++] = i;
			}
			location.setCoordinate(i + 1, 0.0);
		}
		if (needSort)
			Arrays.sort(ids, 0, sid, comparator);
		if (sid > 0) {
			int[] t = new int[len];
			int k = 0;
			t[k++] = ids[0];
			for (int i = 1; i < sid; i++) {
				boolean legal = true;
				for (int j = 0; j < k; j++) {
					if (!matrix.isCompatible(ids[i], t[j])
							|| agents[ids[i]] == agents[t[j]]) {
						legal = false;
						break;
					}
				}
				if (legal)
					t[k++] = ids[i];
			}
			for (int i = 0; i < k; i++) {
				location.setCoordinate(t[i] + 1, 1.0);
			}
		}
	}

	@Override
	public String toString() {
		return "CAXORDomainOfK(" + getConstraintSet() + ")";
	}
}
