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
public class BananaDomain extends AbstractDomain {
	public BananaDomain() {
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
		return "BananaDomain(" + getConstraintSet() + ")";
	}

	public double getValueOfFunction(double[] x) {
		return Functions.Banana(x);
	}

	@Override
	protected double getFitness(double value) {
		return 1 / value;
	}
}
