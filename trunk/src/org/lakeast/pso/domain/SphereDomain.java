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
public class SphereDomain extends AbstractDomain {
	public SphereDomain(int dimension) {
		Constraint dc = new Constraint(-1, 1, BoundaryType.STICK);
		Constraint vc = new Constraint(-1, 1, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(dimension, dc, vc);
		setConstraintSet(cSet);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SphereDomain(" + getConstraintSet() + ")";
	}

	@Override
	protected double getFitness(double v) {
		return -v;
	}

	public double getValueOfFunction(double[] x) {
		return Functions.Sphere(x);
	}
}
