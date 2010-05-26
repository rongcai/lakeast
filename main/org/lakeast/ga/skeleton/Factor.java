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

public class Factor {
	/*
	 * pc=0.8 pm=0.005
	 */
	private final double probabilityOfCrossover;
	private final double probabilityOfMutation;

	/**
	 * @return the probabilityOfCrossover
	 */
	public double getProbabilityOfCrossover() {
		return probabilityOfCrossover;
	}

	/**
	 * @return the probabilityOfMutation
	 */
	public double getProbabilityOfMutation() {
		return probabilityOfMutation;
	}

	public Factor(double probabilityOfCrossover, double probabilityOfMutation) {
		super();
		this.probabilityOfCrossover = probabilityOfCrossover;
		this.probabilityOfMutation = probabilityOfMutation;
	}
}
