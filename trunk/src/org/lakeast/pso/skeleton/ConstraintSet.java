package org.lakeast.pso.skeleton;

import org.lakeast.common.Constraint;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public class ConstraintSet {

	private final Constraint[] dimensionConstraints;

	private final Constraint[] velocityConstraints;

	private final int dimensionsCount;

	private final boolean hasCommonConstraint;

	/**
	 * Creates a new instance of Constraints from array of constraints
	 * 
	 * @param dimensionsCount
	 *            dimension count
	 * @param dimensionConstraints
	 *            An array of constraints limiting each dimemsion in the
	 *            solution space.
	 * @param velocityConstraint
	 *            The limits (+/-) on how far a particle can move along a
	 *            dimension at one time. @ if the dimension count less than zero
	 *            or the argments are not consistent.
	 */
	public ConstraintSet(int dimensionsCount,
			Constraint[] dimensionConstraints, Constraint[] velocityConstraints) {
		if (dimensionsCount < 1)
			throw new IllegalArgumentException(
					"Dimensions count must be greater than zero.");
		if (dimensionsCount != dimensionConstraints.length
				|| dimensionsCount != velocityConstraints.length)
			throw new IllegalArgumentException(
					"The arguments are inconsistent.");
		this.dimensionsCount = dimensionsCount;
		this.dimensionConstraints = dimensionConstraints;
		this.velocityConstraints = velocityConstraints;
		this.hasCommonConstraint = false;
	}

	/**
	 * Creates a new instance of Constraints using a single constraint
	 * 
	 * @param dimensionsCount
	 *            The number of dimensions in the solution space.
	 * @param dimensionConstraint
	 *            The minimum/maximum boudaries for all dimensions.
	 * @param velocityConstraint
	 *            The limits (+/-) on how far a particle can move along a
	 *            dimension at one time. @ if the dimensions count is < 1.
	 */
	public ConstraintSet(int dimensionsCount, Constraint dimensionConstraint,
			Constraint velocityConstraint) {
		if (dimensionsCount < 1)
			throw new IllegalArgumentException(
					"Number of dimensions must be greater than 0.");
		this.dimensionsCount = dimensionsCount;
		dimensionConstraints = new Constraint[1];
		velocityConstraints = new Constraint[1];
		dimensionConstraints[0] = dimensionConstraint;
		velocityConstraints[0] = velocityConstraint;
		hasCommonConstraint = true;
	}

	/**
	 * Returns the number of dimensions in the solution space.
	 * 
	 * @return the number of dimensions
	 */
	public int getDimensionsCount() {
		return dimensionsCount;
	}

	protected void setDimensionConstraint(int dimension, Constraint value) {
		if (hasCommonConstraint) {
			throw new IllegalStateException("It has common constraint.");
		}
		dimensionConstraints[dimension - 1] = value;

	}

	protected void setDimensionConstraint(Constraint value) {
		if (!hasCommonConstraint) {
			throw new IllegalStateException("It has not common constraint.");
		}
		dimensionConstraints[0] = value;
	}

	/**
	 * Returns a reference to the dimension constraint for a specified
	 * dimension.
	 * 
	 * @return a reference to the dimension constraint for a specified dimension
	 */
	public Constraint getDimensionConstraint(int dimension) {
		if (hasCommonConstraint)
			return dimensionConstraints[0];
		return dimensionConstraints[dimension - 1];
	}

	protected void setVelocityConstraint(int dimension, Constraint value) {
		if (hasCommonConstraint) {
			throw new IllegalStateException("It has common constraint.");
		}
		velocityConstraints[dimension - 1] = value;
	}

	protected void setVelocityConstraint(Constraint value) {
		if (!hasCommonConstraint) {
			throw new IllegalStateException("It has not common constraint.");
		}
		velocityConstraints[0] = value;
	}

	/**
	 * Returns a reference to the velocity constraint for a specified dimension.
	 * 
	 * @return a reference to the velocity constraint for a specified dimension
	 */
	public Constraint getVelocityConstraint(int dimension) {
		if (hasCommonConstraint)
			return velocityConstraints[0];
		return velocityConstraints[dimension - 1];
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(DimensionsCount: " + dimensionsCount + ")";
	}
}