package org.lakeast.pso.particle;

import org.lakeast.common.Randoms;
import org.lakeast.common.Range;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.ConstraintSet;
import org.lakeast.pso.skeleton.Location;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
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
