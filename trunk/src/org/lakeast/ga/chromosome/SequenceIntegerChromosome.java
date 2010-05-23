/**
 * 
 */
package org.lakeast.ga.chromosome;

import org.lakeast.common.Functions;
import org.lakeast.common.Randoms;
import org.lakeast.common.ToStringBuffer;
import org.lakeast.ga.skeleton.AbstractChromosome;
import org.lakeast.ga.skeleton.ConstraintSet;

/**
 * @author WANG Zhen
 * 
 */
public class SequenceIntegerChromosome extends IntegerChromosome {
	/*
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#setValueOfDimension
	 * (int, double)
	 */
	@Override
	public void setValueOfDimension(int dimension, double value) {
		throw new RuntimeException();
	}

	/**
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#initialize()
	 */
	@Override
	public void initialize() {
		int[] x = Randoms.sequence(genes.length);
		for (int i = 0; i < genes.length; i++) {
			genes[i] = x[i];
		}
	}

	/*
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#clone()
	 */
	@Override
	public AbstractChromosome clone() {
		SequenceIntegerChromosome ret = new SequenceIntegerChromosome(
				getConstraintSet());
		for (int i = 0; i < this.genes.length; i++) {
			ret.genes[i] = this.genes[i];
		}
		ret.fitness = this.fitness;
		return ret;
	}

	/**
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#mutate(double)
	 */
	@Override
	public void mutate(double pm) {
		if (Math.random() < pm) {
			Functions.swap(genes, Randoms.intInRange(0, genes.length - 1),
					Randoms.intInRange(0, genes.length - 1));
		}
	}

	/*
	 * 交换的平均长度为genes.length/3。 P{D<=d} = 1-(1-d)^2
	 * 
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#doublePointsCrossover
	 * (org.lakeast.ga.skelecton.AbstractChromosome)
	 */
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
		Functions.crossover(this.genes, bca.genes, x, y);
	}

	/*
	 * 交换的平均长度为genes.length/2。 平均长度大比较好，使得无效交换减少。
	 * 
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#singlePointCrossover
	 * (org.lakeast.ga.skelecton.AbstractChromosome)
	 */
	@Override
	public void singlePointCrossover(AbstractChromosome another) {
		IntegerChromosome bca = (IntegerChromosome) another;
		Functions.crossover(this.genes, bca.genes, 0, Randoms.intInRange(1,
				genes.length - 2));
	}

	/*
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#toString()
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		ToStringBuffer.list(genes, "Genes: [", ", ", "]", s);
		return "SequenceBinaryChromosome(Fitness: " + this.getFitness() + ", "
				+ s.toString() + ")";
	}

	/*
	 * @see org.lakeast.ga.chromosome.IntegerChromosome#uniformCrossover(
	 * org.lakeast.ga.skelecton.AbstractChromosome, long)
	 */
	@Override
	public void uniformCrossover(AbstractChromosome another, long MASK) {
		IntegerChromosome bca = (IntegerChromosome) another;
		int[] a = new int[genes.length];
		int[] b = new int[genes.length];
		int[] t = new int[genes.length];
		int k = 0;
		for (int i = 0; i < genes.length; i++) {
			if (Functions.getMaskBit(MASK, i + 1) == 1) {
				a[k++] = this.genes[i];
				b[k++] = bca.genes[i];
				t[k++] = i;
			}
		}
		Functions.crossover(a, b, 0, k - 1);
		for (int i = 0; i < k; i++) {
			this.genes[t[i]] = a[i];
			bca.genes[t[i]] = b[i];
		}
	}

	public SequenceIntegerChromosome(ConstraintSet cSet) {
		super(cSet);
	}

}
