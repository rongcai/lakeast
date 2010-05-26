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

package org.lakeast.pso.particle;

import org.lakeast.common.Randoms;
import org.lakeast.common.Range;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.ConstraintSet;
import org.lakeast.pso.skeleton.Location;

public class GlobalBestParticle extends AbstractParticle {

	/**
	 * @param name
	 * @param constraintSet
	 */
	public GlobalBestParticle(int particleID, ConstraintSet constraintSet) {
		super(particleID, constraintSet);
	}

	// complexity:
	// o(dimensionCount)
	@Override
	protected void calculateNextVelocities(double inertiaWeight,
			double iWeight, double sWeight, AbstractParticle[] neighborhoods,
			Location globalBest) {
		int dimensionCount = this.getDimensionsCount();
		for (int dimension = 1; dimension <= dimensionCount; dimension++) {
			double iFactor = iWeight * Randoms.doubleInRange(0.0, 1.0);
			double sFactor = sWeight * Randoms.doubleInRange(0.0, 1.0);
			double currentCoordinate = getCurrentLocation().getCoordinate(
					dimension);
			double personalBest = getBestLocation().getCoordinate(dimension);
			double pDelta = personalBest - currentCoordinate;
			double nDelta = globalBest.getCoordinate(dimension)
					- currentCoordinate;
			double delta = (iFactor * pDelta) + (sFactor * nDelta);
			double currentVelocity = getCurrentLocation()
					.getVelocity(dimension);
			delta = inertiaWeight * currentVelocity + delta;
			double velocityMin = getDimensionConstraint(dimension).getMinimum();
			double velocityMax = getDimensionConstraint(dimension).getMaximum();
			getCurrentLocation().setVelocity(dimension,
					Range.constrict(delta, velocityMin, velocityMax));
		}
	}
}
