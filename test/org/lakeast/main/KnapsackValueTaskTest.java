package org.lakeast.main;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.common.Randoms;
import org.lakeast.ga.chromosome.BinaryChromosome;
import org.lakeast.ga.skeleton.ConstantFactorGenerator;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.pso.gen.ExponentFactorGenerator;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.NeighborhoodBestParticle;
import org.lakeast.pso.skeleton.RingTopoSwarm;

public class KnapsackValueTaskTest implements Task{

	@Test
	public void testIt() throws InitializeException{
		go(null);
	}

	public void go(String[] args) throws InitializeException {
		int numberOfIterations = 2000;
		int testCount = 20;

		int popSize = 53;// 素数较好
		int neighborhoodSize = 6;

		Constraint exitCondition = new Constraint(0, 0);

		TestBatch batch = new TestBatch(testCount, numberOfIterations,
				exitCondition);

		int dimension = 50;

		double[] prices = new double[dimension];
		double[] weights = new double[dimension];

		for (int i = 0; i < dimension; i++) {
			prices[i] = Randoms.doubleInRange(10, 100);
			weights[i] = Randoms.doubleInRange(2, 8);
		}

		double capacity = 100;

		final int Y = 12;

		Testable[] targets = new Testable[Y];

		org.lakeast.ga.skeleton.Factor initFactor1 = new org.lakeast.ga.skeleton.Factor(
				0.75, 0.01);
		org.lakeast.pso.skeleton.Factor initFactor2 = new org.lakeast.pso.skeleton.Factor(
				1, 2, 2);

		Constraint constraint = new Constraint(0.3, 1.0);

		IFactorGenerator generator = new ExponentFactorGenerator(
				numberOfIterations, constraint, 1.0, initFactor2);

		org.lakeast.ga.skeleton.Domain domain1 = new org.lakeast.ga.domain.KnapsackDomain(
				prices, weights, capacity, 0.6);
		org.lakeast.pso.skeleton.Domain domain2 = new org.lakeast.pso.domain.KnapsackDomain(
				prices, weights, capacity, 0.6);
		org.lakeast.pso.skeleton.Domain domain3 = new org.lakeast.pso.domain.KnapsackDomainOfK(
				prices, weights, capacity, 0.6);
		org.lakeast.ga.skeleton.Domain domain4 = new org.lakeast.ga.domain.KnapsackDomain(
				prices, weights, capacity, 0.7);
		org.lakeast.pso.skeleton.Domain domain5 = new org.lakeast.pso.domain.KnapsackDomain(
				prices, weights, capacity, 0.7);
		org.lakeast.pso.skeleton.Domain domain6 = new org.lakeast.pso.domain.KnapsackDomainOfK(
				prices, weights, capacity, 0.7);
		org.lakeast.ga.skeleton.Domain domain7 = new org.lakeast.ga.domain.KnapsackDomain(
				prices, weights, capacity, 0.8);
		org.lakeast.pso.skeleton.Domain domain8 = new org.lakeast.pso.domain.KnapsackDomain(
				prices, weights, capacity, 0.8);
		org.lakeast.pso.skeleton.Domain domain9 = new org.lakeast.pso.domain.KnapsackDomainOfK(
				prices, weights, capacity, 0.8);
		org.lakeast.ga.skeleton.Domain domain10 = new org.lakeast.ga.domain.KnapsackDomain(
				prices, weights, capacity, 0.9);
		org.lakeast.pso.skeleton.Domain domain11 = new org.lakeast.pso.domain.KnapsackDomain(
				prices, weights, capacity, 0.9);
		org.lakeast.pso.skeleton.Domain domain12 = new org.lakeast.pso.domain.KnapsackDomainOfK(
				prices, weights, capacity, 0.9);

		targets[0] = new Population(BinaryChromosome.class, popSize,
				new ConstantFactorGenerator(initFactor1), domain1);

		targets[1] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain2, neighborhoodSize, generator);

		targets[2] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain3, neighborhoodSize, generator);

		targets[3] = new Population(BinaryChromosome.class, popSize,
				new ConstantFactorGenerator(initFactor1), domain4);

		targets[4] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain5, neighborhoodSize, generator);

		targets[5] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain6, neighborhoodSize, generator);

		targets[6] = new Population(BinaryChromosome.class, popSize,
				new ConstantFactorGenerator(initFactor1), domain7);

		targets[7] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain8, neighborhoodSize, generator);

		targets[8] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain9, neighborhoodSize, generator);

		targets[9] = new Population(BinaryChromosome.class, popSize,
				new ConstantFactorGenerator(initFactor1), domain10);

		targets[10] = new RingTopoSwarm(NeighborhoodBestParticle.class,
				popSize, domain11, neighborhoodSize, generator);

		targets[11] = new RingTopoSwarm(NeighborhoodBestParticle.class,
				popSize, domain12, neighborhoodSize, generator);

		for (int i = 0; i < Y; i++) {
			batch.addTest(targets[i]);
		}

		try {
			batch.run();
		} catch (TestException e) {
			e.printStackTrace();
		}
	}
}
