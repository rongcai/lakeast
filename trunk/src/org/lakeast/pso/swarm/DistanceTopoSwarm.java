/**
 * 
 */
package org.lakeast.pso.swarm;

import java.util.ArrayList;

import org.lakeast.common.InitializeException;
import org.lakeast.pso.skeleton.AbstractParticle;
import org.lakeast.pso.skeleton.Domain;
import org.lakeast.pso.skeleton.DynamicTopoSwarm;
import org.lakeast.pso.skeleton.IFactorGenerator;

/**
 * @author WANG Zhen
 * 
 */
public class DistanceTopoSwarm extends DynamicTopoSwarm {
	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "DistanceTopoSwarm(ParticleType: " + getParticleTypeName()
				+ ", SwarmSize: " + getSwarmSize() + ", Domain: "
				+ getDomainDescription() + ", Threshold: " + threshold
				+ ", Generator: " + getFactorGeneratorDescription() + ")";
	}

	public DistanceTopoSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, double threshold,
			IFactorGenerator generator) throws InitializeException {
		super(particleType, swarmSize, domain, generator);

		this.threshold = threshold;
	}

	private double threshold;

	private AbstractParticle[] getNearestParticles(AbstractParticle particle) {
		ArrayList<AbstractParticle> list = new ArrayList<AbstractParticle>();
		int swarmSize = getSwarmSize();
		for (int i = 0; particle.getID() != particles[i].getID()
				&& i < swarmSize; i++) {
			if (getDistanceBetween(particle, particles[i]) <= getThreshold()) {
				list.add(particles[i]);
			}
		}
		list.add(particle);
		AbstractParticle[] neighborhoods = new AbstractParticle[list.size()];
		return list.toArray(neighborhoods);
	}

	private static final int upper = 3000;

	private double getThreshold() {

		int g = getGeneration();
		if (g > upper) {
			g = upper;
		}
		// return threshold;
		return (0.6 + 0.3 * g / (double) upper) * threshold;
	}

	private double getDistanceBetween(AbstractParticle particle,
			AbstractParticle particle2) {

		int dimensionsCount = particle.getDimensionsCount();
		double distance = 0.0;
		double delta = 0.0;
		for (int i = 1; i <= dimensionsCount; i++) {
			delta = particle.getCurrentLocation().getCoordinate(i)
					- particle2.getCurrentLocation().getCoordinate(i);
			distance += Math.pow(delta, 2.0);
		}
		distance = Math.sqrt(distance);
		return distance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lakeast.pso.INeighborhoodTopology#getNeighborhoods(int)
	 */
	// complexity:
	// o(swarmSize * dimensionsCount)
	public AbstractParticle[] getNeighborhoods(AbstractParticle particle) {

		return getNearestParticles(particle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lakeast.pso.INeighborhoodTopology#getTopology()
	 */
	public String getTopology() {

		return "Distance";
	}

	public String getSimpleDescription() {

		return this.toString();
	}
}
