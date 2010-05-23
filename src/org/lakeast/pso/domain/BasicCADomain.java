/**
 * 
 */
package org.lakeast.pso.domain;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.model.ca.RandomizedAveragePriceComparator;
import org.lakeast.model.ca.RandomizedPriceComparator;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;

/**
 * WDP问题公用模块，实现根据报价矩阵来求得当前竞胜标的总报价。
 * 
 * @author WANG Zhen
 * 
 */
public class BasicCADomain extends AbstractDomain {

	protected QuotedPriceMatrix matrix;

	protected final RandomizedPriceComparator comparator;

	protected final boolean needSort;

	public BasicCADomain(QuotedPriceMatrix matrix, double probability) {
		this.matrix = matrix;
		Constraint dc = new Constraint(-200, 200, BoundaryType.STICK);
		Constraint vc = new Constraint(-200, 200, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(matrix.getBidsAmount(), dc, vc);
		setConstraintSet(cSet);
		this.comparator = new RandomizedAveragePriceComparator(matrix,
				probability);
		this.needSort = true;
	}

	public BasicCADomain(QuotedPriceMatrix matrix) {
		this.matrix = matrix;
		Constraint dc = new Constraint(-200, 200, BoundaryType.STICK);
		Constraint vc = new Constraint(-200, 200, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(matrix.getBidsAmount(), dc, vc);
		setConstraintSet(cSet);
		this.comparator = null;
		this.needSort = false;
	}

	/**
	 * @see org.lakeast.ga.skeleton.AbstractDomain#getValueOfFunction(double[])
	 */
	public double getValueOfFunction(double[] x) {
		return Functions.CA(x, matrix);// 调用CA计算。
	}
}
