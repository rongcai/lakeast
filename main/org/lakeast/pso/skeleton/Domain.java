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

package org.lakeast.pso.skeleton;

public interface Domain {
	public void reset();

	public ConstraintSet getConstraintSet();

	/**
	 * 计算适应值。
	 *
	 * @param location
	 * @return
	 */
	public double calculateFitness(Location location);

	public double[] getSolution();

	public double getBestValue();

	/**
	 * 直接返回目标函数值。
	 *
	 * @param x
	 * @return
	 */
	public double getValueOfFunction(double[] x);
}