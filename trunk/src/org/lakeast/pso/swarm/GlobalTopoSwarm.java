/**
 * 
 */
package org.lakeast.pso.swarm;

import java.util.HashMap;

import org.lakeast.common.InitializeException;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.Domain;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.StaticTopoSwarm;

/**
 * 
 * @author WANG Zhen
 * 
 */

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
