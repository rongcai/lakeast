package org.lakeast.common;

/**
 * �����ṩ�йر���Լ��Χ����Ϣ�Լ�������
 */
public class Constraint {

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + minimum + "," + maximum + ")";
	}

	private final double minimum;

	private final double maximum;

	private final BoundaryType boundaryType;

	/** Creates a new instance of Constraint */
	public Constraint(double minimum, double maximum, BoundaryType boundaryType) {
		if (!(boundaryType == BoundaryType.NONE
				|| boundaryType == BoundaryType.WRAP
				|| boundaryType == BoundaryType.BOUNCE || boundaryType == BoundaryType.STICK))
			throw new IllegalArgumentException("Invalid boundary type.");
		if (boundaryType == BoundaryType.NONE) {
			if (minimum > maximum)
				throw new IllegalArgumentException(
						"Maximum must be greater than minimum for specified boundary type.");
		} else if (minimum >= maximum)
			throw new IllegalArgumentException(
					"Maximum must be greater than minimum for specified boundary type.");
		this.minimum = minimum;
		this.maximum = maximum;
		this.boundaryType = boundaryType;
	}

	/** Creates a new instance of Constraint with no boundary type */
	public Constraint(double minimum, double maximum) {
		this(minimum, maximum, BoundaryType.NONE);
	}

	public double getMinimum() {
		return this.minimum;
	}

	public double getMaximum() {
		return this.maximum;
	}

	public BoundaryType getBoundaryType() {
		return this.boundaryType;
	}

}
