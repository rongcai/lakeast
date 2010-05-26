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

package org.lakeast.main;

import org.lakeast.common.Constraint;

public interface Testable {

	public static final int PRECISION = 50;

	public static final String DATAPATH = "E:\\data\\";

	public boolean iterate(int numberOfIterations, Constraint exitCondition);

	public void reset();

	public int size();

	public int getDimensionsCount();

	public String getSimpleDescription();

	public int getGeneration();

	/**
	 * 返回最优解的适应值。基于同一个群体上的最优解。
	 *
	 * @return 最优解的适应值。
	 */
	public double getGlobalBestFitness();

	/**
	 * 返回最优个体。基于同一个群体上的最优解。
	 *
	 * @return 最优个体。
	 */
	public Object getGlobalBest();

	/**
	 * 返回最优的解向量。基于同一个Domain上的最优解。
	 *
	 * @return 最优解的解向量。
	 */
	public double[] getSolution();

	/**
	 * 返回最优解的函数值。基于同一个Domain上的最优解。
	 *
	 * @return 最优解的函数值。
	 */
	public double getBestValue();

	public boolean iterate(int numberOfIterations, Constraint exitCondition,
			double[] y);
}
