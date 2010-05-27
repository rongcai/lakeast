package org.lakeast.main;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.ga.chromosome.SequenceIntegerChromosome;
import org.lakeast.ga.gen.AdapativeFactorGenerator;
import org.lakeast.ga.skeleton.ConstantFactorGenerator;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.pso.gen.ExponentFactorGenerator;
import org.lakeast.pso.particle.NeighborhoodBestParticle;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.swarm.RingTopoSwarm;

public class TSPValueTaskTest implements Task {

	@Test
	public void testIt() throws InitializeException{
		go(null);
	}

	public void go(String[] args) throws InitializeException {
		int numberOfIterations = 2000;
		int testCount = 10;

		int popSize = 53;// 素数较好
		int neighborhoodSize = 6;

		Constraint exitCondition = new Constraint(0, 0);

		TestBatch batch = new TestBatch(testCount, numberOfIterations,
				exitCondition);

		final int Y = 3;

		Testable[] targets = new Testable[Y];

		org.lakeast.ga.skeleton.Factor initFactor1 = new org.lakeast.ga.skeleton.Factor(
				0.75, 0.01);
		org.lakeast.pso.skeleton.Factor initFactor2 = new org.lakeast.pso.skeleton.Factor(
				1, 2, 2);

		int dimension = 100;

		Constraint constraint = new Constraint(0.3, 1.4);
		IFactorGenerator generator = new ExponentFactorGenerator(
				numberOfIterations, constraint, 3.0, initFactor2);

		org.lakeast.ga.skeleton.Domain domain1 = new org.lakeast.ga.domain.TSPDomain(
				dimension);
		org.lakeast.pso.skeleton.Domain domain2 = new org.lakeast.pso.domain.TSPDomain(
				dimension);

		targets[0] = new Population(SequenceIntegerChromosome.class, popSize,
				new ConstantFactorGenerator(initFactor1), domain1);
		targets[1] = new Population(SequenceIntegerChromosome.class, popSize,
				new AdapativeFactorGenerator(initFactor1, 0.15, 0.15), domain1);
		targets[2] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain2, neighborhoodSize, generator);

		for (int j = 0; j < Y; j++) {
			batch.addTest(targets[j]);
		}

		try {
			batch.run();
		} catch (TestException e) {
			e.printStackTrace();
		}
	}
}
