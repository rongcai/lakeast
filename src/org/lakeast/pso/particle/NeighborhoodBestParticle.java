package org.lakeast.pso.particle;

import org.lakeast.common.Constraint;
import org.lakeast.common.Randoms;
import org.lakeast.common.Range;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.ConstraintSet;
import org.lakeast.pso.skeleton.Location;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public class NeighborhoodBestParticle extends AbstractParticle {
	/**
	 * @param name
	 * @param constraintSet
	 */
	public NeighborhoodBestParticle(int particleID, ConstraintSet constraintSet) {
		super(particleID, constraintSet);
	}

	private Location getNeighborhoodBestLocation(
			AbstractParticle[] neighborhoods) {
		double max = neighborhoods[0].getBestLocationFitness();
		int nBestId = 0;
		for (int i = 1; i < neighborhoods.length; i++) {
			if (neighborhoods[i].getBestLocationFitness() > max) {
				max = neighborhoods[i].getBestLocationFitness();
				nBestId = i;
			}
		}
		return neighborhoods[nBestId].getBestLocation();
	}

	// complexity:
	// o(max{neighborhoods.length, dimensionCount}) ~= o(dimensionCount)
	@Override
	protected void calculateNextVelocities(double inertiaWeight,
			double iWeight, double sWeight, AbstractParticle[] neighborhoods,
			Location globalBest) {
		Location nBestLocation = getNeighborhoodBestLocation(neighborhoods);
		for (int dimension = 1; dimension <= dimensionsCount; dimension++) {
			double iFactor = iWeight * Randoms.doubleInRange(0.0, 1.0);
			double sFactor = sWeight * Randoms.doubleInRange(0.0, 1.0);
			double currentCoordinate = current.getCoordinate(dimension);
			double pDelta = best.getCoordinate(dimension) - currentCoordinate;
			double nDelta = nBestLocation.getCoordinate(dimension)
					- currentCoordinate;
			double delta = (iFactor * pDelta) + (sFactor * nDelta);
			double currentVelocity = current.getVelocity(dimension);
			delta = inertiaWeight * currentVelocity + delta;
			Constraint c = constraintSet.getDimensionConstraint(dimension);
			double velocityMin = c.getMinimum();
			double velocityMax = c.getMaximum();
			current.setVelocity(dimension, Range.constrict(delta, velocityMin,
					velocityMax));
		}
	}
}
