package org.lakeast.pso.skeleton;

import org.lakeast.common.Constraint;

/**
 * @author WANG Zhen
 * @date 2007-5-26
 */
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
