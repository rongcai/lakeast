package org.lakeast.pso.skeleton;

import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.common.Randoms;
import org.lakeast.common.Range;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public abstract class AbstractParticle {
	protected Location best;

	protected final Location current;

	protected final ConstraintSet constraintSet;

	protected final int dimensionsCount;

	private final int particleID;

	/** Creates a new instance of Particle */
	public AbstractParticle(int particleID, ConstraintSet constraintSet) {
		this.particleID = particleID;
		this.constraintSet = constraintSet;
		this.dimensionsCount = constraintSet.getDimensionsCount();
		current = new Location(dimensionsCount);
		best = new Location(dimensionsCount);
	}

	/** Reset */
	public void reset(boolean zeroVelocity) {
		Constraint c;
		for (int d = 1; d <= dimensionsCount; d++) {
			c = constraintSet.getDimensionConstraint(d);
			current.setCoordinate(d, Randoms.doubleInRange(c.getMinimum(), c
					.getMaximum()));
			if (!zeroVelocity) {
				c = constraintSet.getVelocityConstraint(d);
				current.setVelocity(d, Randoms.doubleInRange(c.getMinimum(), c
						.getMaximum()));
			}
		}
	}

	public Location getCurrentLocation() {
		return current;
	}

	public void setCurrentLocationAsBest() {
		for (int i = 0; i < dimensionsCount; i++) {
			best.coordinates[i] = current.coordinates[i];
			best.velocities[i] = current.velocities[i];
		}
		best.fitness = current.fitness;
	}

	public Location getBestLocation() {
		return best;
	}

	public double getCurrentLocationFitness() {
		return current.getFitness();
	}

	public double getBestLocationFitness() {
		return best.getFitness();
	}

	public int getID() {
		return particleID;
	}

	public Constraint getDimensionConstraint(int dimension) {
		return constraintSet.getDimensionConstraint(dimension);
	}

	public Constraint getVelocityConstraint(int dimension) {
		return constraintSet.getVelocityConstraint(dimension);
	}

	public void setCurrentLocationFitness(double newFitness) {
		current.setFitness(newFitness);
	}

	public int getDimensionsCount() {
		return dimensionsCount;
	}

	/**
	 * 
	 * @see org.lakeast.pso.skeleton.IMoveAlgorithm#calculateNextLocation(double,
	 *      double, double, org.lakeast.pso.skeleton.AbstractParticle[],
	 *      org.lakeast.pso.skelecton.ImmutableLocation)
	 */
	public void calculateNextLocation(double inertiaWeight, double iWeight,
			double sWeight, AbstractParticle[] neighborhoods,
			Location globalBest) {
		double nextCoordinate;
		double dimensionMin;
		double dimensionMax;
		calculateNextVelocities(inertiaWeight, iWeight, sWeight, neighborhoods,
				globalBest);
		for (int d = 1; d <= dimensionsCount; d++) {
			nextCoordinate = current.getCoordinate(d) + current.getVelocity(d);
			Constraint c = constraintSet.getDimensionConstraint(d);
			dimensionMin = c.getMinimum();
			dimensionMax = c.getMaximum();
			switch (c.getBoundaryType()) {
			case BOUNCE:
				nextCoordinate = Range.bounce(nextCoordinate, dimensionMin,
						dimensionMax);
				break;
			case WRAP:
				nextCoordinate = Range.wrap(nextCoordinate, dimensionMin,
						dimensionMax);
				break;
			case STICK:
				nextCoordinate = Range.stick(nextCoordinate, dimensionMin,
						dimensionMax);
				break;
			default:
				Functions.assertTrue(false);
				break;
			}
			current.setCoordinate(d, nextCoordinate);
		}
	}

	protected abstract void calculateNextVelocities(double inertiaWeight,
			double iWeight, double sWeight, AbstractParticle[] neighborhoods,
			Location globalBest);
}