/**
 * 
 */
package org.lakeast.pso.domain;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;

/**
 * @author WANG Zhen
 * 
 *         May 21, 2007
 */
public class ShafferF6Domain extends AbstractDomain {
	public ShafferF6Domain() {
		Constraint dc = new Constraint(-100, 100, BoundaryType.STICK);
		Constraint vc = new Constraint(-100, 100, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(2, dc, vc);
		setConstraintSet(cSet);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShafferF6Domain(" + getConstraintSet() + ")";
	}

	@Override
	protected double getFitness(double v) {
		return -v;
	}

	public double getValueOfFunction(double[] x) {
		return Functions.ShafferF6(x);
	}
}
