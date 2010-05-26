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

package org.lakeast.ga.skeleton;

import org.lakeast.common.Functions;

/**
 * 与问题域有关的计算与参数抽象类。
 *
 * @author Eric Wang
 *
 */
public abstract class AbstractDomain implements Domain {
	private ConstraintSet cSet;
	private double[] solution;
	private double best;
	private double max = Double.NEGATIVE_INFINITY;

	public void reset() {
		max = Double.NEGATIVE_INFINITY;
	}

	/**
	 * @return the max
	 */
	public double getBestValue() {
		return best;
	}

	protected final void setConstraintSet(ConstraintSet cSet) {
		this.cSet = cSet;
	}

	/**
	 * 需要先在类的构造方法中调用setConstraintSet()予以设定。
	 *
	 * @see org.lakeast.ga.skecleton.Domain#getConstraintSet()
	 */
	public ConstraintSet getConstraintSet() {
		return cSet;
	}

	/**
	 * 解码基因型为表现型。
	 *
	 * @param chromosome
	 * @return 返回的表现型数组
	 */
	public static double[] decode(AbstractChromosome chromosome) {
		double[] x = new double[chromosome.getDimensionsCount()];
		for (int i = 0; i < chromosome.getDimensionsCount(); i++) {
			x[i] = chromosome.getValueOfDimension(i + 1);
		}
		return x;
	}

	/**
	 * 默认适值计算模板，重载后要仍然应该调用该方法来进行适值计算。
	 */
	public void calculateFitness(Population p) {
		calculateFitness2(p);
	}

	/**
	 * 代理方法，实现默认适值计算。
	 *
	 * @param chromosome
	 * @return
	 */
	private void calculateFitness2(Population p) {
		int len = p.chromosomes.length;
		double sum = 0.0;
		double[][] l = new double[len][];
		double v[] = new double[len];
		double f[] = new double[len];
		for (int i = 0; i < len; i++) {
			double[] x = decode(p.chromosomes[i]);
			l[i] = x;
			v[i] = getValueOfFunction(x);
			f[i] = getFitness(v[i]);
			Functions.assertTrue(f[i] >= 0.0);
			sum += f[i];
		}
		double average = sum / len;
		double standardDeviation = 0.0;// TODO 暂时不计算
		for (int i = 0; i < len; i++) {
			double fitness = stretchFitness(f[i], average, standardDeviation);
			p.chromosomes[i].setFitness(fitness);
			if (fitness > p.chromosomes[p.bestId].fitness) {
				p.bestId = i;
				if (fitness > max) {
					max = fitness;
					best = v[i];
					solution = l[i];
				}
			}
			if (fitness < p.chromosomes[p.worstId].fitness) {
				p.worstId = i;
			}
		}
	}

	/**
	 * 返回根据原始函数值变换后但未被拉伸的适应值。默认实现直接返回原始函数值。
	 *
	 * @param standardDeviation
	 * @param average
	 * @param chromosome
	 * @return
	 */
	protected double getFitness(double v) {
		return v;
	}

	/**
	 * 返回运用标准拉伸算法产生的适值，可重载使用或者实现其他的拉伸方法。
	 *
	 * @param value
	 *            该染色体适值
	 * @param average
	 *            种群平均适值
	 * @param standardDeviation
	 *            种群适值方差
	 * @return
	 */
	protected double stretchFitness(double fitness, double average,
			double standardDeviation) {
		return fitness;
		// standardStretch2(fitness, average, standardDeviation);
	}

	private static final double K = 1;// <=1
	private static final double K2 = 1;// <=average

	/**
	 * 标准适值拉伸。
	 *
	 * @param value
	 * @param average
	 * @param standardDeviation
	 * @return
	 */
	protected static double standardStretch(double fitness, double average,
			double standardDeviation) {
		// TODO 这个方法会使得适值过度聚集
		Functions.assertTrue(fitness >= 0.0 && average > 0.0);
		double delta = fitness - average;
		double coef = K * average;
		double deltaf;
		if (delta > 1000) {
			deltaf = coef;
		} else {
			deltaf = coef * (Math.exp(delta) - 1) / (Math.exp(delta) + 1);
		}
		Functions.assertTrue(Math.abs(deltaf) <= coef + 0.0001);
		// make sure that the absolute value of deltaf must be less than
		// average.
		double ret = deltaf + average;
		return ret;
	}

	protected static double standardStretch2(double fitness, double average,
			double standardDeviation) {
		// TODO 这个方法会使得适值过度聚集
		Functions.assertTrue(fitness >= 0.0 && average > 0.0);
		double delta = fitness - average;
		double deltaf = K2 * Math.tanh(delta);
		double ret = deltaf + average;
		Functions.assertTrue(ret >= 0.0);
		return ret;
	}

	protected static double standardStretch3(double fitness, double average,
			double standardDeviation) {
		// TODO 什么时候保持delta不变的基准选取十分重要。以及考虑ret<0时截断的影响。
		Functions.assertTrue(fitness >= 0.0 && average > 0.0);
		double delta = fitness - average;
		double deltaf;
		if (delta >= 0) {
			deltaf = Math.pow(delta, 0.1);
		} else {
			deltaf = -Math.pow(-delta, 0.1);
		}
		double ret = deltaf + average;
		if (ret < 0)
			ret = 0.0;
		Functions.assertTrue(ret >= 0.0);
		return ret;
	}

	/**
	 * @return the best
	 */
	public double[] getSolution() {
		return solution;
	}
}
