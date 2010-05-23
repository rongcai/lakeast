package org.lakeast.pso.domain;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;

/**
 * @author WANG Zhen
 * 
 */
public class TestDomain extends AbstractDomain {
	public TestDomain() {
		// 保证最优值不在边界附近
		// 速度很大的时候通常都不是最优值,想出处理速度很大下时的群调整机制
		// 如何防止陷入局部最优??单单依靠缩小w可以使得对局部进行更细致开拓,但是很难防止
		// 陷入局部最优
		// 提高算法稳定性
		// 争取在数学上解决它
		// 与收敛的速度比起来,达优率会更重要一点.
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
		return "TestDomain(" + getConstraintSet() + ")";
	}

	public double getValueOfFunction(double[] x) {
		return -(100 * Math.pow(x[0] * x[0] - x[1], 2) + Math.pow(1 - x[0], 2));
	}
}
