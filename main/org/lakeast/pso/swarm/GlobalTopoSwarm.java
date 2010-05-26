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

public class GlobalTopoSwarm extends StaticTopoSwarm {
	public GlobalTopoSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, IFactorGenerator generator)
			throws InitializeException {
		super(particleType, swarmSize, domain, swarmSize, generator);

	}

	@Override
	protected HashMap<Integer, AbstractParticle[]> prepareNeighborhoodMap() {
		HashMap<Integer, AbstractParticle[]> map = new HashMap<Integer, AbstractParticle[]>();
		for (int i = 0; i < getSwarmSize(); i++) {
			map.put(particles[i].getID(), particles);
		}
		return map;
	}

	public String getTopology() {
		return GLOBAL;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GlobalTopoSwarm(ParticleType: " + getParticleTypeName()
				+ ", SwarmSize: " + getSwarmSize() + ", Domain: "
				+ getDomainDescription() + ", Generator: "
				+ getFactorGeneratorDescription() + ")";
	}

	public String getSimpleDescription() {

		return this.toString();
	}
}
