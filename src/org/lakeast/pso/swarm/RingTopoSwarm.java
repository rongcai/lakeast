package org.lakeast.pso.swarm;

import java.util.HashMap;

import org.lakeast.common.InitializeException;
import org.lakeast.common.Range;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.Domain;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.StaticTopoSwarm;

/**
 * @author WANG Zhen
 * 
 */
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
