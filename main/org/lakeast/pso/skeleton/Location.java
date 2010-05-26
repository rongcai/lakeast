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

import java.io.Serializable;

import org.lakeast.common.ToStringBuffer;

public class Location implements Cloneable, Serializable {

	private static final long serialVersionUID = -1454304139904924986L;

	private static final String CRLF = "\r\n";

	double fitness;

	double[] coordinates;

	double[] velocities;

	private final int dimensionsCount;

	/**
	 * Creates a new instance of Location
	 *
	 * @param dimensionsCount
	 *            Dimensions count
	 * @param coordinates
	 *            Locations along each dimension of the solution space.
	 * @param velocities
	 *            The +/- movement along dimension leading to this coordinate.
	 *            There is a 1-to-1 mapping between velocities and coordinates.
	 */
	public Location(int dimensionsCount) {
		if (dimensionsCount < 1)
			throw new IllegalArgumentException(
					"Dimensions count must be greater than zero.");
		this.dimensionsCount = dimensionsCount;
		this.coordinates = new double[dimensionsCount];
		this.velocities = new double[dimensionsCount];
	}

	/**
	 * Returns the fitness value.
	 *
	 * @return the fitness value.
	 * @throws FitnessNotSetException
	 *             if fitness has not been set yet.
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * perform a "deep copy" operation.
	 *
	 * @return a deep copy
	 */
	public Location clone() {
		Location location = new Location(dimensionsCount);
		for (int i = 0; i < dimensionsCount; i++) {
			location.coordinates[i] = this.coordinates[i];
			location.velocities[i] = this.velocities[i];
		}
		location.fitness = this.fitness;
		return location;
	}

	/**
	 * Sets the fitness value for the location coordinates.
	 *
	 * @param newFitness
	 *            The new fitness value.
	 */
	protected void setFitness(double newFitness) {
		this.fitness = newFitness;
	}

	/**
	 * Overrides toString and returns a formatted listing of the fitness value
	 * and (via <code>Move&#46toString</code>) the coordinates and velocities
	 * for this location.
	 *
	 * @return formatted fitness, coordinates and velocities for this location.
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Fitness:     ");
		s.append(fitness);
		s.append(CRLF);
		ToStringBuffer.list(coordinates, "Coordinates: (", ", ", ") ", s);
		ToStringBuffer.list(velocities, "Velocities:  (", ", ", ") ", s);
		return s.toString();
	}

	public double getCoordinate(int dimension) {
		return coordinates[dimension - 1];
	}

	public void setCoordinate(int dimension, double value) {
		coordinates[dimension - 1] = value;
	}

	public double getVelocity(int dimension) {
		return velocities[dimension - 1];
	}

	public void setVelocity(int dimension, double value) {
		velocities[dimension - 1] = value;
	}

	public int getDimensionsCount() {
		return dimensionsCount;
	}
}
