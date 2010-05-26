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

public abstract class AbstractChromosome implements Cloneable {
	protected ConstraintSet cSet;

	protected double fitness;

	private EncodingType type;

	public AbstractChromosome(ConstraintSet cSet, EncodingType type) {
		this.cSet = cSet;
		this.type = type;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public abstract void initialize();

	/**
	 * Single point crossover.
	 *
	 * @param another
	 */
	public abstract void singlePointCrossover(AbstractChromosome another);

	/**
	 * Double points crossover.
	 *
	 * @param another
	 */
	public abstract void doublePointsCrossover(AbstractChromosome another);

	/**
	 * Uniform crossover.
	 *
	 * @param another
	 */
	public abstract void uniformCrossover(AbstractChromosome another, long MASK);

	/**
	 * mutate.
	 *
	 * @param pm
	 */
	public abstract void mutate(double pm);

	public abstract AbstractChromosome clone();

	public int getDimensionsCount() {
		return cSet.getDimensionsCount();
	}

	public ConstraintSet getConstraintSet() {
		return cSet;
	}

	/**
	 * 实现自解码后返回的值，而非相应基因位的值。
	 *
	 * @param dimension
	 * @return
	 */
	public abstract double getValueOfDimension(int dimension);

	// public abstract void setValueOfDimension(int dimension, double value);

	public EncodingType getType() {
		return type;
	}

	public abstract void setValueOfDimension(int dimension, double value);
}