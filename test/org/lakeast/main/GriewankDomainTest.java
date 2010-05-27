package org.lakeast.main;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.pso.domain.GriewankDomain;
import org.lakeast.pso.skeleton.ConstrictFactorGenerator;
import org.lakeast.pso.skeleton.Domain;
import org.lakeast.pso.skeleton.ExponentFactorGenerator;
import org.lakeast.pso.skeleton.Factor;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.NeighborhoodBestParticle;
import org.lakeast.pso.skeleton.RingTopoSwarm;

public class GriewankDomainTest implements Task{

	@Test
	public void testIt() throws InitializeException{
		go(null);
	}

	public void go(String[] args) throws InitializeException {
		int numberOfIterations = 3000;
		int testCount = 20;

		int popSize = 53;// 素数较好
		int neighborhoodSize = 6;

		Constraint exitCondition = new Constraint(0, 0);

		TestBatch batch = new TestBatch(testCount, numberOfIterations,
				exitCondition);

		Constraint constraint = new Constraint(0.3, 1.0);
		Factor initFactor = new Factor(1, 2, 2);

		IFactorGenerator generator1 = new ExponentFactorGenerator(
				numberOfIterations, constraint, 1.0, initFactor);
		IFactorGenerator generator2 = new ExponentFactorGenerator(
				numberOfIterations, constraint, 1.2, initFactor);
		IFactorGenerator generator3 = new ConstrictFactorGenerator(
				numberOfIterations, constraint, 1.0);
		IFactorGenerator generator4 = new ConstrictFactorGenerator(
				numberOfIterations, constraint, 1.2);
		final int Y = 4;

		Testable[] targets = new Testable[Y];

		Domain domain = new GriewankDomain(5);

		targets[0] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain, neighborhoodSize, generator1);
		targets[1] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain, neighborhoodSize, generator2);
		targets[2] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain, neighborhoodSize, generator3);
		targets[3] = new RingTopoSwarm(NeighborhoodBestParticle.class, popSize,
				domain, neighborhoodSize, generator4);

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
