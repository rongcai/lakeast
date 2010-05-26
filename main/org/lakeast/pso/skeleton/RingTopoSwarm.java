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
import org.lakeast.common.Range;

public class RingTopoSwarm extends StaticTopoSwarm {
	public RingTopoSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, int neighborhoodSize,
			IFactorGenerator generator) throws InitializeException {
		super(particleType, swarmSize, domain, neighborhoodSize, generator);
	}

	public String getTopology() {
		return RING;
	}

	@Override
	protected HashMap<Integer, AbstractParticle[]> prepareNeighborhoodMap() {
		HashMap<Integer, AbstractParticle[]> map = new HashMap<Integer, AbstractParticle[]>();
		int swarmSize = getSwarmSize();
		int neighborhoodSize = getNeighborhoodSize();
		for (int i = 0; i < swarmSize; i++) {
			AbstractParticle[] neighborhoods = new AbstractParticle[neighborhoodSize];
			for (int j = 0, nid = i - neighborhoodSize / 2; j < neighborhoodSize; j++) {
				neighborhoods[j] = particles[Range
						.wrap(nid++, 0, swarmSize - 1)];
			}
			map.put(particles[i].getID(), neighborhoods);
		}
		return map;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RingTopoSwarm(ParticleType: " + getParticleTypeName()
				+ ", SwarmSize: " + getSwarmSize() + ", Domain: "
				+ getDomainDescription() + ", NeighborhoodSize: "
				+ getNeighborhoodSize() + ", Generator: "
				+ getFactorGeneratorDescription() + ")";
	}

	public String getSimpleDescription() {

		return this.toString();
	}
}
