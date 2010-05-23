/**
 * 
 */
package org.lakeast.pso.domain;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;

/**
 * @author WANG Zhen
 * 
 */
public class TestDomain2 extends AbstractDomain {
	private final int n;

	public TestDomain2(int n) {
		Constraint dc = new Constraint(0, 100, BoundaryType.STICK);
		Constraint vc = new Constraint(-100, 100, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(n * (n - 1) / 2, dc, vc);
		setConstraintSet(cSet);
		this.n = n;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "TestEDomain(" + getConstraintSet() + ")";
	}

	/**
	 * 
	 */
	public double getValueOfFunction(double[] fx) {
		double temp = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int seq = (2 * n - i - 1) * i / 2 + j - i - 1;
				temp += 1 / Math.pow(fx[seq], 12) - 1 / Math.pow(fx[seq], 6);
			}
		}
		return -4 * temp;
	}
}
