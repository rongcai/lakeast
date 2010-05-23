package org.lakeast.pso.skeleton;

import java.util.HashMap;

import org.lakeast.common.InitializeException;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
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
