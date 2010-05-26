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

public interface INeighborhoodTopology {
	/**
	 * The neighborhood topology is a ring.
	 */
	public static final String RING = "Ring";

	/**
	 * The neighborhood topology is a star.
	 */
	public static final String STAR = "Star";

	/**
	 * The neighborhood topology is global (all particles are neighbors).
	 */
	public static final String GLOBAL = "Global";

	public AbstractParticle[] getNeighborhoods(AbstractParticle particle);

	/**
	 * Returns a String (static final field) identifying the neighborhood
	 * topology type.
	 *
	 * @return a String field identifying the neighborhood topology type.
	 */
	public String getTopology();

	public boolean isStaticTopology();
}
