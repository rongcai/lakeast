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

package org.lakeast.pso.domain;

import org.lakeast.common.BoundaryType;
import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.pso.skeleton.AbstractDomain;
import org.lakeast.pso.skeleton.ConstraintSet;
import org.lakeast.pso.skeleton.Location;

/**
 * 将实数离散化编码然后采用NDPSO方法求解Sphere函数， 由于各个维度之间高度依赖但是又缺乏交流，效果不好。
 *
 * @author Eric Wang
 */
public class NDPSOSphereDomain extends AbstractDomain {

	private int bits = 32;
	private double max = 1.0;
	private double min = -1.0;

	public NDPSOSphereDomain(int dimension) {
		Constraint dc = new Constraint(-200, 200, BoundaryType.STICK);
		Constraint vc = new Constraint(-200, 200, BoundaryType.STICK);
		ConstraintSet cSet = new ConstraintSet(dimension * bits, dc, vc);
		setConstraintSet(cSet);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NDPSOSphereDomain(" + getConstraintSet() + ")";
	}

	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getCoordinate(dimension));
	}

	@Override
	public double[] decode(Location location) {
		int len = location.getDimensionsCount();
		double[] x = new double[len];
		for (int i = 0; i < len; i++) {
			if (isOne(location, i + 1)) {
				x[i] = 1.0;
			}
		}
		return x;
	}

	@Override
	protected double getFitness(double v) {
		return -v;
	}

	public double getValueOfFunction(double[] x) {
		int[] v = new int[x.length];
		for (int i = 0; i < x.length; i++) {
			v[i] = (int) x[i];
		}
		int dimensions = this.getConstraintSet().getDimensionsCount() / bits;
		double[] values = new double[dimensions];
		double precision = (max - min) / (((long) 1 << bits) - 1);
		for (int i = 0; i < dimensions; i++) {
			long sum = 0;
			for (int j = 0; j < bits; j++) {
				sum += (long) v[i * bits + j] << j;
			}
			values[i] = min + sum * precision;
		}
		return Functions.Sphere(values);
	}
}
