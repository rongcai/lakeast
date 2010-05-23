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
public class ClusterAnalysisParticle extends AbstractParticle {
	/**
	 * @param name
	 * @param constraintSet
	 */
	public ClusterAnalysisParticle(int particleID, ConstraintSet constraintSet) {
		super(particleID, constraintSet);
	}

	private double[] getNeighborhoodCenterCoordinates(
			AbstractParticle[] neighborhoods) {
		int dimensionsCount = getDimensionsCount();
		double[] coordinates = new double[dimensionsCount];
		for (int j = 1; j <= dimensionsCount; j++) {
			for (int i = 0; i < neighborhoods.length; i++) {
				coordinates[j - 1] += neighborhoods[i].getBestLocation()
						.getCoordinate(j);
			}
			coordinates[j - 1] /= neighborhoods.length;
		}
		return coordinates;
	}

	// complexity:
	// o(neighborhoods.length * dimensionCount)
	@Override
	protected void calculateNextVelocities(double inertiaWeight,
			double iWeight, double sWeight, AbstractParticle[] neighborhoods,
			Location globalBest) {
		int dimensionCount = this.getDimensionsCount();
		double[] nCenterCoordinates = getNeighborhoodCenterCoordinates(neighborhoods);
		for (int dimension = 1; dimension <= dimensionCount; dimension++) {
			double iFactor = iWeight * Randoms.doubleInRange(0.0, 1.0);
			double sFactor = sWeight * Randoms.doubleInRange(0.0, 1.0);
			double currentCoordinate = getCurrentLocation().getCoordinate(
					dimension);
			double pDelta = nCenterCoordinates[dimension - 1]
					- currentCoordinate;
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
