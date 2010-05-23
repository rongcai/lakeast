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
 * @author WANG Zhen
 * 
 */
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
