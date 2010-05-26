/*
 * Copyright Eric Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.lakeast.ga.chromosome;

import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.common.Randoms;
import org.lakeast.common.ToStringBuffer;
import org.lakeast.ga.skeleton.AbstractChromosome;
import org.lakeast.ga.skeleton.ConstraintSet;
import org.lakeast.ga.skeleton.EncodingType;

public class RealChromosome extends AbstractChromosome {
	private final double[] genes;

	public RealChromosome(ConstraintSet cSet) {
		super(cSet, EncodingType.REAL);
		genes = new double[cSet.getDimensionsCount()];
	}

	@Override
	public AbstractChromosome clone() {
		RealChromosome ret = new RealChromosome(getConstraintSet());
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
			genes[i] = Randoms.doubleInRange(c.getMinimum(), c.getMaximum());
		}
	}

	@Override
	public void singlePointCrossover(AbstractChromosome another) {
		RealChromosome bca = (RealChromosome) another;
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
				genes[i] = Randoms
						.doubleInRange(c.getMinimum(), c.getMaximum());
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
		return "RealChromosome(Fitness: " + this.getFitness() + ", "
				+ s.toString() + ")";
	}

	@Override
	public void doublePointsCrossover(AbstractChromosome another) {
		RealChromosome bca = (RealChromosome) another;
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
		RealChromosome bca = (RealChromosome) another;
		for (int i = 0; i < genes.length; i++) {
			if ((Functions.getMaskBit(MASK, i + 1)) == 1) {
				Functions.swap(this.genes, bca.genes, i);
			}
		}
	}

	@Override
	public void setValueOfDimension(int dimension, double value) {
		this.genes[dimension - 1] = value;

	}
}
