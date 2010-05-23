package org.lakeast.pso.swarm;

import java.util.HashMap;

import org.lakeast.common.InitializeException;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.Domain;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.StaticTopoSwarm;

/**
 * @author WANG Zhen
 * @date 2007-7-12
 */
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
