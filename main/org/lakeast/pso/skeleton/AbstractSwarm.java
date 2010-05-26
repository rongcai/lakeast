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

package org.lakeast.pso.skeleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.common.Range;
import org.lakeast.main.Testable;

public abstract class AbstractSwarm implements INeighborhoodTopology, Testable {
	/*
	 * @see org.lakeast.test.Testable#size()
	 */
	public int size() {
		return this.getSwarmSize();
	}

	private static final Log log = LogFactory.getLog(AbstractSwarm.class);

	private final int swarmSize;

	private final Domain domain;

	private Location globalBest;

	public final AbstractParticle[] particles;// 粒子不会消亡，只会更换位置，所以可以final

	private int generation;

	private final IFactorGenerator generator;

	private final String particleTypeName;

	/**
	 * @return the particleTypeName
	 */
	public String getParticleTypeName() {
		return particleTypeName;
	}

	/**
	 * Creates a new instance of Swarm
	 *
	 * @param particleType
	 * @param swarmSize
	 * @param domain
	 * @param generator
	 * @throws InitializeException
	 */
	public AbstractSwarm(Class<? extends AbstractParticle> particleType,
			int swarmSize, Domain domain, IFactorGenerator generator)
			throws InitializeException {
		if (swarmSize < 1) {
			throw new IllegalArgumentException(
					"The swarmsize must be larger than zero.");
		}
		particleTypeName = particleType.getSimpleName();
		particles = new AbstractParticle[swarmSize];
		for (int i = 0; i < swarmSize; i++) {
			try {
				this.particles[i] = (AbstractParticle) particleType
						.getConstructor(int.class, ConstraintSet.class)
						.newInstance(i, domain.getConstraintSet());
			} catch (Exception ex) {
				throw new InitializeException(ex);
			}
		}
		this.swarmSize = swarmSize;
		this.domain = domain;
		this.generator = generator;
		reset();
	}

	/**
	 * Sets up the particles for the beginning of a swarm (run) by updating the
	 * current location, setting the current location's fitness and, as this is
	 * the first location, making it the best location (so far) -- this method
	 * <b>must be invoked</b> before the move org.lakeast is used for the first
	 * time.
	 */
	private void setInitialFitnessValues() {
		int p;
		double fitness;
		double max = Double.NEGATIVE_INFINITY;
		int best = 0;
		for (p = 0; p < swarmSize; p++) {
			fitness = domain
					.calculateFitness(particles[p].getCurrentLocation());
			particles[p].setCurrentLocationFitness(fitness);
			if (fitness > max) {
				max = fitness;
				best = p;
			}
			particles[p].setCurrentLocationAsBest();
		}
		globalBest = particles[best].getBestLocation();
		generation = 1;
	}

	/**
	 * Iterates a specified number of times through all particles in the Swarm,
	 * getting their next location, making the next locations current and
	 * evaluating the fitness of the new current location; returns <b>true</b>
	 * if any particle's fitness is within the defined exit conditions,
	 * otherwise <b>false</b>.
	 *
	 * @param numberOfIterations
	 *            how many times to move particles.
	 * @param exitCondition
	 *            a range (Constraint) indicating an acceptable fitness value
	 *            after which the iterations can stop. <b>Note</b> that the
	 *            iterate method will <i>not</i> return until it has completed
	 *            the current iteration for <i>all</i> particles. if not all
	 *            particles have been defined yet.
	 * @return <b>true</b> if any particle's fitness is within the defined exit
	 *         conditions, otherwise <b>false</b>
	 */
	// complexity:
	// o(numberOfIterations * swarmSize * max{o(calculateNextVelocities()),
	// o(getNeighborhoods())})
	public final boolean iterate(int numberOfIterations,
			Constraint exitCondition, double[] y) {
		// TODO 对二维变量引入图形化接口，进行实时显示。
		double fitness, bestFitness;
		double exitConditionMin = exitCondition.getMinimum();
		double exitConditionMax = exitCondition.getMaximum();
		int p; // particle
		for (int i = 0; i < numberOfIterations; i++, generation++) {
			Factor factor = generator.generateFactor(this);
			// calculate the "next" location
			for (p = 0; p < swarmSize; p++) {
				particles[p].calculateNextLocation(factor.getInertiaWeight(),
						factor.getIndividualityWeight(), factor
								.getSocialityWeight(),
						getNeighborhoods(particles[p]), globalBest);
				bestFitness = particles[p].getBestLocationFitness();
				fitness = domain.calculateFitness(particles[p]
						.getCurrentLocation());
				particles[p].setCurrentLocationFitness(fitness);
				if (fitness >= bestFitness) {
					particles[p].setCurrentLocationAsBest();
					if (fitness > getGlobalBestFitness()) {
						globalBest = particles[p].getBestLocation();
						if (log.isDebugEnabled()) {
							log.debug("At generation " + generation
									+ " particle " + particles[p].getID()
									+ " now has global best fitness => ");
							log.debug(particles[p].getCurrentLocation()
									.toString());
						}
					}
				}
			}
			if (y != null && (i + 1) % PRECISION == 0) {
				y[(i + 1) / PRECISION - 1] += this.getBestValue();
			}
			// return true if the global best fitness is within the defined exit
			// conditions
			if (Range.inRange(getBestValue(), exitConditionMin,
					exitConditionMax))
				return true;
		} // end of: for (int i = 0; i < numberOfIterations; i++) ...

		return false;
	}

	public int getGeneration() {
		return generation;
	}

	public double getGlobalBestFitness() {
		return globalBest.getFitness();
	}

	public Location getGlobalBest() {
		return globalBest;
	}

	public int getSwarmSize() {
		return swarmSize;
	}

	public void reset() {
		domain.reset();
		for (AbstractParticle particle : particles) {
			particle.reset(false);
		}
		setInitialFitnessValues();
	}

	/**
	 * @see org.lakeast.main.Testable#getSolution()
	 */
	public double[] getSolution() {
		return domain.getSolution();
	}

	public String getDomainDescription() {
		return domain.toString();
	}

	public String getFactorGeneratorDescription() {
		return generator.toString();
	}

	public double getBestValue() {
		return domain.getBestValue();
	}

	/*
	 * @see org.lakeast.test.Testable#getDimensionsCount()
	 */
	public int getDimensionsCount() {
		return domain.getConstraintSet().getDimensionsCount();
	}

	/*
	 * @see org.lakeast.test.Testable#iterate(int,
	 * org.lakeast.common.Constraint)
	 */
	public boolean iterate(int numberOfIterations, Constraint exitCondition) {
		return iterate(numberOfIterations, exitCondition, null);
	}
}
