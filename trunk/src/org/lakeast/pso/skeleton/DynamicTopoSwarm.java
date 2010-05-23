package org.lakeast.pso.skeleton;

import org.lakeast.common.InitializeException;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public abstract class DynamicTopoSwarm extends AbstractSwarm {

	public DynamicTopoSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, IFactorGenerator generator)
			throws InitializeException {
		super(particleType, swarmSize, domain, generator);

	}

	public boolean isStaticTopology() {
		return false;
	}

}
