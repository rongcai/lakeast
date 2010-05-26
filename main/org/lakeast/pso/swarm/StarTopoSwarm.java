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

package org.lakeast.pso.swarm;

import java.util.HashMap;

import org.lakeast.common.InitializeException;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.Domain;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.StaticTopoSwarm;

public class StarTopoSwarm extends StaticTopoSwarm {
	public StarTopoSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, IFactorGenerator generator)
			throws InitializeException {
		super(particleType, swarmSize, domain, 2, generator);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lakeast.pso.INeighborhoodTopology#getTopology()
	 */
	public String getTopology() {

		return STAR;
	}

	@Override
	protected HashMap<Integer, AbstractParticle[]> prepareNeighborhoodMap() {

		HashMap<Integer, AbstractParticle[]> map = new HashMap<Integer, AbstractParticle[]>();
		for (int i = 1; i < getSwarmSize(); i++) {
			AbstractParticle[] neighborhoods = new AbstractParticle[2];
			neighborhoods[0] = particles[0];
			neighborhoods[1] = particles[i];
			map.put(particles[i].getID(), neighborhoods);
		}
		map.put(particles[0].getID(), particles.clone());
		return map;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "StarTopoSwarm(ParticleType: " + getParticleTypeName()
				+ ", SwarmSize: " + getSwarmSize() + ", Domain: "
				+ getDomainDescription() + ", Generator: "
				+ getFactorGeneratorDescription() + ")";
	}

	public String getSimpleDescription() {

		return this.toString();
	}
}
