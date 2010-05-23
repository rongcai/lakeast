/**
 * 
 */
package org.lakeast.ga.domain;

import java.util.Arrays;

import org.lakeast.common.Functions;
import org.lakeast.ga.skeleton.AbstractChromosome;
import org.lakeast.ga.skeleton.AbstractDomain;
import org.lakeast.ga.skeleton.ConstraintSet;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.ga.skeleton.Population.RepairCallback;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.model.ca.RandomizedAveragePriceComparator;
import org.lakeast.model.ca.RandomizedPriceComparator;

/**
 * @author WANG Zhen
 * 
 */
public class CADomain extends AbstractDomain {
	private QuotedPriceMatrix matrix;
	private final boolean needSort;
	private final RandomizedPriceComparator comparator;

	public CADomain(QuotedPriceMatrix matrix, double probability) {
		this.matrix = matrix;
		ConstraintSet cSet = new ConstraintSet(matrix.getBidsAmount());
		setConstraintSet(cSet);
		this.comparator = new RandomizedAveragePriceComparator(matrix,
				probability);
		this.needSort = true;
	}

	public CADomain(QuotedPriceMatrix matrix) {
		this.matrix = matrix;
		ConstraintSet cSet = new ConstraintSet(matrix.getBidsAmount());
		setConstraintSet(cSet);
		this.comparator = null;
		this.needSort = false;
	}

	@Override
	public void calculateFitness(Population p) {
		p.repair(new RepairCallback() {

			public void repair(AbstractChromosome chromosome) {
				int len = chromosome.getDimensionsCount();
				Integer[] ids = new Integer[len];
				int sid = 0;
				for (int i = 0; i < chromosome.getDimensionsCount(); i++) {
					if (chromosome.getValueOfDimension(i + 1) == 1.0) {
						ids[sid++] = i;
					}
					chromosome.setValueOfDimension(i + 1, 0.0);
				}
				if (needSort) {
					Arrays.sort(ids, 0, sid, comparator);
				}
				if (sid > 0) {
					int[] t = new int[len];
					int k = 0;
					t[k++] = ids[0];
					for (int i = 1; i < sid; i++) {
						boolean legal = true;
						for (int j = 0; j < k; j++) {
							if (!matrix.isCompatible(ids[i], t[j])) {
								legal = false;
								break;
							}
						}
						if (legal)
							t[k++] = ids[i];
					}
					for (int i = 0; i < k; i++) {
						chromosome.setValueOfDimension(t[i] + 1, 1.0);
					}
				}
			}

		});
		super.calculateFitness(p);
	}

	public double getValueOfFunction(double[] x) {
		return Functions.CA(x, matrix);
	}

	@Override
	public String toString() {
		return "CADomain(ConstraintSet: " + getConstraintSet()
				+ ", Comparator: " + comparator + ")";
	}
}
