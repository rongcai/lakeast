/**
 * 
 */
package org.lakeast.ga.domain;

import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.ga.skeleton.AbstractDomain;
import org.lakeast.ga.skeleton.ConstraintSet;

/**
 * @author WANG Zhen
 * 
 *         May 21, 2007
 */
public class GriewankDomain extends AbstractDomain {
	public GriewankDomain() {
		Constraint dc = new Constraint(-1, 1);
		ConstraintSet cSet = new ConstraintSet(5, dc);
		cSet.setBits(32);
		setConstraintSet(cSet);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GriewankDomain(" + getConstraintSet() + ")";
	}

	public double getValueOfFunction(double[] x) {
		return Functions.Griewank(x);
	}

	@Override
	protected double getFitness(double value) {
		return 1 / (value + 1);// values大于-1
	}
}
