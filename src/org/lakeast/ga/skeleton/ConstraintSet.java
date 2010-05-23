package org.lakeast.ga.skeleton;

import org.lakeast.common.Constraint;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public class ConstraintSet {

	private final Constraint[] constraints;

	private final int dimensionsCount;

	private int bits = 1;

	private final boolean hasCommonConstraint;

	public ConstraintSet(int dimensionsCount) {
		if (dimensionsCount < 1)
			throw new IllegalArgumentException(
					"Number of dimensions must be greater than 0.");
		this.dimensionsCount = dimensionsCount;
		constraints = new Constraint[1];
		constraints[0] = null;
		this.hasCommonConstraint = true;
	}

	/**
	 * @return the hasCommonConstraint
	 */
	public boolean hasCommonConstraint() {
		return hasCommonConstraint;
	}

	public ConstraintSet(int dimensionsCount, Constraint[] constraints) {
		if (dimensionsCount < 1)
			throw new IllegalArgumentException(
					"Dimensions count must be greater than zero.");
		this.dimensionsCount = dimensionsCount;
		this.constraints = constraints;
		this.hasCommonConstraint = false;

	}

	public ConstraintSet(int dimensionsCount, Constraint constraint) {
		if (dimensionsCount < 1)
			throw new IllegalArgumentException(
					"Number of dimensions must be greater than 0.");
		this.dimensionsCount = dimensionsCount;
		constraints = new Constraint[1];
		constraints[0] = constraint;
		this.hasCommonConstraint = true;
	}

	public int getDimensionsCount() {
		return dimensionsCount;
	}

	public Constraint getConstraint(int dimensionsCount) {
		if (dimensionsCount < 1 || dimensionsCount > dimensionsCount)
			throw new IllegalArgumentException(
					"dimensionsCount invalid - valid range: [1.."
							+ (dimensionsCount) + "].");
		if (hasCommonConstraint)
			return constraints[0];
		return constraints[dimensionsCount - 1];
	}

	/**
	 * @return the bits
	 */
	public int getBits() {
		return bits;
	}

	private int limit = 0;

	/**
	 * 每个实例只允许调用该方法一次。bits必须大于等于1且小于等于32。
	 * 
	 * @param bits
	 *            the bits to set
	 */
	public void setBits(int bits) {
		if (bits < 1)
			throw new IllegalArgumentException();
		if (limit > 0)
			throw new RuntimeException();
		limit++;
		this.bits = bits;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(DimensionsCount: " + dimensionsCount + ")";
	}
}