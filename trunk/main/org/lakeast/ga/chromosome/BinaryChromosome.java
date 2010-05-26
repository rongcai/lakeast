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

/**
 * 采用二进制编码，求解离散或者连续问题均可以使用。效果一般比实数编码好。
 *
 * @author Eric Wang
 *
 */
public class BinaryChromosome extends AbstractChromosome {
	protected final byte[] genes;
	private final double[] values;
	private boolean updated;

	public BinaryChromosome(ConstraintSet cSet) {
		super(cSet, EncodingType.BINARY);
		genes = new byte[cSet.getDimensionsCount() * cSet.getBits()];
		values = new double[cSet.getDimensionsCount()];
	}

	protected BinaryChromosome(ConstraintSet set, int len) {
		super(set, EncodingType.BINARY);
		genes = new byte[len];
		values = new double[cSet.getDimensionsCount()];
	}

	@Override
	public AbstractChromosome clone() {
		BinaryChromosome ret = new BinaryChromosome(getConstraintSet());
		for (int i = 0; i < this.genes.length; i++) {
			ret.genes[i] = this.genes[i];
		}
		ret.fitness = this.fitness;
		for (int i = 0; i < this.getDimensionsCount(); i++) {
			ret.values[i] = this.values[i];
		}
		return ret;
	}

	@Override
	public void initialize() {
		for (int i = 0; i < genes.length; i++) {
			genes[i] = (byte) (Math.random() > 0.5 ? 1 : 0);
		}
		flushValues();
	}

	@Override
	public void singlePointCrossover(AbstractChromosome another) {
		BinaryChromosome bca = (BinaryChromosome) another;
		/**
		 * x >= 1 && x <= len - 2
		 */
		int x = Randoms.intInRange(1, genes.length - 2);
		// System.out.println(x);
		for (int i = 0; i <= x; i++) {
			Functions.swap(this.genes, bca.genes, i);
		}
		flushValues();
	}

	@Override
	public void mutate(double pm) {
		for (int i = 0; i < genes.length; i++) {
			if (Math.random() < pm) {
				genes[i] ^= 1;
			}
		}
		flushValues();
	}

	private void flushValues() {
		updated = true;
	}

	/**
	 * 采用将[min,max]平均分割的方法生成实数值。bits最大63,否则将出现溢出错误。
	 */
	private void flushValues2() {
		int bits = cSet.getBits();
		if (bits == 1)
			return;
		Constraint c = cSet.getConstraint(1);
		double precision = (c.getMaximum() - c.getMinimum())
				/ (((long) 1 << bits) - 1);
		long sum = 0;
		for (int j = 0; j < bits; j++) {
			sum += (long) genes[j] << j;
		}
		values[0] = c.getMinimum() + sum * precision;
		for (int i = 1; i < cSet.getDimensionsCount(); i++) {
			if (!cSet.hasCommonConstraint()) {
				c = cSet.getConstraint(i + 1);
				precision = (c.getMaximum() - c.getMinimum())
						/ (((long) 1 << bits) - 1);
			}
			sum = 0;
			for (int j = 0; j < bits; j++) {
				sum += (long) genes[i * bits + j] << j;
			}
			values[i] = c.getMinimum() + sum * precision;
		}
	}

	@Override
	public double getValueOfDimension(int dimension) {
		if (cSet.getBits() == 1)
			return genes[dimension - 1];
		if (updated) {
			flushValues2();
			updated = false;
		}
		return values[dimension - 1];
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		ToStringBuffer.list(genes, "Genes: [", ", ", "]", s);
		return "BinaryChromosome(Fitness: " + this.getFitness() + ", "
				+ s.toString() + ")";
	}

	@Override
	public void doublePointsCrossover(AbstractChromosome another) {
		BinaryChromosome bca = (BinaryChromosome) another;
		int x = Randoms.intInRange(0, genes.length - 1);
		int y = Randoms.intInRange(0, genes.length - 1);
		if (x > y) {
			x = x ^ y;
			y = x ^ y;
			x = x ^ y;
		}
		for (int i = x; i <= y; i++) {
			Functions.swap(this.genes, bca.genes, i);
		}
		flushValues();
	}

	@Override
	public void uniformCrossover(AbstractChromosome another, long MASK) {
		BinaryChromosome bca = (BinaryChromosome) another;
		for (int i = 0; i < genes.length; i++) {
			if ((Functions.getMaskBit(MASK, i + 1)) == 1) {
				Functions.swap(this.genes, bca.genes, i);
			}
		}
		flushValues();
	}

	@Override
	public void setValueOfDimension(int dimension, double value) {
		if (cSet.getBits() != 1)
			throw new IllegalStateException();
		this.genes[dimension - 1] = (byte) (value >= 1.0 ? 1 : 0);
	}
}
