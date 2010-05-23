package org.lakeast.pso.domain;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;

/**
 * @author WANG Zhen
 * @date 2007-5-23
 */
public class BananaDomain extends AbstractDomain {
	public BananaDomain(int dimension) {
		Constraint dc = new Constraint(-100, 100, BoundaryType.STICK);
		Constraint vc = new Constraint(-100, 100, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(dimension, dc, vc);
		setConstraintSet(cSet);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BananaDomain(" + getConstraintSet() + ")";
	}

	@Override
	protected double getFitness(double v) {
		return -v;
	}

	public double getValueOfFunction(double[] x) {
		return Functions.Banana(x);
	}
}
