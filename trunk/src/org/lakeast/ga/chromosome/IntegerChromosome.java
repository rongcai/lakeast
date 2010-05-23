package org.lakeast.ga.chromosome;

import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.common.Randoms;
import org.lakeast.common.ToStringBuffer;
import org.lakeast.ga.skeleton.AbstractChromosome;
import org.lakeast.ga.skeleton.ConstraintSet;
import org.lakeast.ga.skeleton.EncodingType;

public class IntegerChromosome extends AbstractChromosome {
	protected final int[] genes;

	public IntegerChromosome(ConstraintSet cSet) {
		super(cSet, EncodingType.INTEGER);
		genes = new int[cSet.getDimensionsCount()];
	}

	@Override
	public AbstractChromosome clone() {
		IntegerChromosome ret = new IntegerChromosome(getConstraintSet());
		for (int i = 0; i < this.genes.length; i++) {
			ret.genes[i] = this.genes[i];
		}
		ret.fitness = this.fitness;
		return ret;
	}

	@Override
	public void initialize() {
		for (int i = 0; i < genes.length; i++) {
			Constraint c = this.getConstraintSet().getConstraint(i + 1);
			genes[i] = Randoms.intInRange((int) c.getMinimum(), (int) c
					.getMaximum());
		}
	}

	@Override
	public void singlePointCrossover(AbstractChromosome another) {
		IntegerChromosome bca = (IntegerChromosome) another;
		/**
		 * x >= 1 && x <= len - 2
		 */
		int x = Randoms.intInRange(1, genes.length - 2);
		// System.out.println(x);
		for (int i = 0; i <= x; i++) {
			Functions.swap(this.genes, bca.genes, i);
		}
	}

	@Override
	public void mutate(double pm) {
		for (int i = 0; i < genes.length; i++) {
			if (Math.random() < pm) {
				Constraint c = this.getConstraintSet().getConstraint(i + 1);
				genes[i] = Randoms.intInRange((int) c.getMinimum(), (int) c
						.getMaximum());
			}
		}
	}

	@Override
	public double getValueOfDimension(int dimension) {
		return genes[dimension - 1];
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		ToStringBuffer.list(genes, "Genes: [", ", ", "]", s);
		return "IntegerChromosome(Fitness: " + this.getFitness() + ", "
				+ s.toString() + ")";
	}

	@Override
	public void doublePointsCrossover(AbstractChromosome another) {
		IntegerChromosome bca = (IntegerChromosome) another;
		int x = Randoms.intInRange(0, genes.length - 1);
		int y = Randoms.intInRange(0, genes.length - 1);
		if (x > y) {
			x ^= y;
			y ^= x;
			x ^= y;
		}
		for (int i = x; i <= y; i++) {
			Functions.swap(this.genes, bca.genes, i);
		}
	}

	@Override
	public void uniformCrossover(AbstractChromosome another, long MASK) {
		IntegerChromosome bca = (IntegerChromosome) another;
		for (int i = 0; i < genes.length; i++) {
			if ((Functions.getMaskBit(MASK, i + 1)) == 1) {
				Functions.swap(this.genes, bca.genes, i);
			}
		}
	}

	@Override
	public void setValueOfDimension(int dimension, double value) {
		this.genes[dimension - 1] = (int) value;
	}
}