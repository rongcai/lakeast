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

import java.util.Arrays;

import org.lakeast.common.Functions;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.pso.skeleton.Location;

/**
 * 基于带邻域的HSRO算子求解WDP问题
 *
 * @author Eric Wang
 *
 */
public class CADomainOfL extends BasicCADomain {

	public CADomainOfL(QuotedPriceMatrix matrix, double probability) {
		super(matrix, probability);
	}

	public CADomainOfL(QuotedPriceMatrix matrix) {
		super(matrix);
	}

	/**
	 *
	 * @param location
	 * @param dimension
	 * @return
	 */
	private boolean isOne(Location location, int dimension) {
		return Math.random() < Functions.Sigmoid(location
				.getCoordinate(dimension));
	}

	/**
	 * 采用位置向量生成离散值并局部查找，不替代当前粒子，而是作为当前粒子坐标的解码解向量。
	 *
	 * @see org.lakeast.pso.skeleton.AbstractDomain#decode(org.lakeast.pso.skeleton.Location)
	 */
	@Override
	public double[] decode(Location location) {
		int len = location.getDimensionsCount();
		double[] x = new double[len];
		Integer[] ids = new Integer[len];
		int sid = 0;
		for (int i = 0; i < len; i++) {
			if (isOne(location, i + 1)) {
				ids[sid++] = i;
			}
		}
		if (needSort)
			Arrays.sort(ids, 0, sid, comparator);
		if (sid > 0) {
			double max = 0.0;
			int k = 0;// 有效标的数目
			/*
			 * 存储有效的标的id号。
			 */
			int[] t = new int[len];
			int[] opt = new int[len];
			int opt_len = 0;
			for (int p = 0; p < Math.min(sid, 10); p++) {
				k = 0;
				t[k++] = ids[p];
				for (int i = 0; i < sid; i++) {
					if (i == p)
						continue;// unnecessary compatibility check
					boolean legal = true;
					for (int j = 0; j < k; j++) {
						if (!matrix.isCompatible(ids[i], t[j])) {
							legal = false;
							break;
						}
					}
					if (legal) {
						t[k++] = ids[i];
					}
				}// checked
				double[] v = new double[len];
				for (int i = 0; i < k; i++) {
					v[t[i]] = 1.0;
				}
				double f = getFitness(getValueOfFunction(v));
				if (f > max) {
					max = f;
					System.arraycopy(t, 0, opt, 0, k);
					opt_len = k;
				}
			}
			for (int i = 0; i < opt_len; i++) {
				x[opt[i]] = 1.0;
			}
		}
		return x;
	}

	@Override
	public String toString() {
		return "CADomainOfL(" + getConstraintSet() + ")";
	}
}