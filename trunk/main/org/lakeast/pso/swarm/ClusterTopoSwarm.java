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

public class ClusterTopoSwarm extends StaticTopoSwarm {

	public ClusterTopoSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, int neighborhoodSize,
			IFactorGenerator generator) throws InitializeException {
		super(particleType, swarmSize, domain, neighborhoodSize, generator);

	}

	/*
	 * @see
	 * org.lakeast.pso.skelecton.INeighborhoodTopology#getNeighborhoods(org.
	 * lakeast .pso.skelecton.AbstractParticle)
	 */
	public AbstractParticle[] getNeighborhoods(AbstractParticle particle) {

		return null;
	}

	/*
	 * @see org.lakeast.pso.skelecton.INeighborhoodTopology#getTopology()
	 */
	public String getTopology() {

		return "Cluster";
	}

	@Override
	protected HashMap<Integer, AbstractParticle[]> prepareNeighborhoodMap() {

		return null;
	}

	public String getSimpleDescription() {

		return this.toString();
	}

}
