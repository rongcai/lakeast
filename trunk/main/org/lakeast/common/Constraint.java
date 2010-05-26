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

package org.lakeast.common;

public class Constraint {

	@Override
	public String toString() {
		return "(" + minimum + "," + maximum + ")";
	}

	private final double minimum;

	private final double maximum;

	private final BoundaryType boundaryType;

	/** Creates a new instance of Constraint */
	public Constraint(double minimum, double maximum, BoundaryType boundaryType) {
		if (!(boundaryType == BoundaryType.NONE
				|| boundaryType == BoundaryType.WRAP
				|| boundaryType == BoundaryType.BOUNCE || boundaryType == BoundaryType.STICK))
			throw new IllegalArgumentException("Invalid boundary type.");
		if (boundaryType == BoundaryType.NONE) {
			if (minimum > maximum)
				throw new IllegalArgumentException(
						"Maximum must be greater than minimum for specified boundary type.");
		} else if (minimum >= maximum)
			throw new IllegalArgumentException(
					"Maximum must be greater than minimum for specified boundary type.");
		this.minimum = minimum;
		this.maximum = maximum;
		this.boundaryType = boundaryType;
	}

	/** Creates a new instance of Constraint with no boundary type */
	public Constraint(double minimum, double maximum) {
		this(minimum, maximum, BoundaryType.NONE);
	}

	public double getMinimum() {
		return this.minimum;
	}

	public double getMaximum() {
		return this.maximum;
	}

	public BoundaryType getBoundaryType() {
		return this.boundaryType;
	}

}
