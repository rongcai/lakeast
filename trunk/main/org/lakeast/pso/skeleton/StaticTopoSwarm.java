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

import java.util.HashMap;

import org.lakeast.common.InitializeException;

public abstract class StaticTopoSwarm extends AbstractSwarm {

	private final int neighborhoodSize;

	/**
	 * @return the neighborhoodSize
	 */
	public int getNeighborhoodSize() {
		return neighborhoodSize;
	}

	public StaticTopoSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, int neighborhoodSize,
			IFactorGenerator generator) throws InitializeException {
		super(particleType, swarmSize, domain, generator);
		if (neighborhoodSize < 1 || neighborhoodSize > swarmSize) {
			throw new IllegalArgumentException("Neighborhood size is invalid.");
		}
		this.neighborhoodSize = neighborhoodSize;
		neighborhoodMap = prepareNeighborhoodMap();
	}

	private final HashMap<Integer, AbstractParticle[]> neighborhoodMap;

	protected abstract HashMap<Integer, AbstractParticle[]> prepareNeighborhoodMap();

	// complexity:
	// o(1)
	public AbstractParticle[] getNeighborhoods(AbstractParticle particle) {
		return neighborhoodMap.get(particle.getID());
	}

	public boolean isStaticTopology() {
		return true;
	}

}
