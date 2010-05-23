package org.lakeast.pso.domain;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.main.TestBatch;
import org.lakeast.main.TestException;
import org.lakeast.main.Testable;
import org.lakeast.pso.gen.ConstrictFactorGenerator;
import org.lakeast.pso.gen.ExponentFactorGenerator;
import org.lakeast.pso.particle.NeighborhoodBestParticle;
import org.lakeast.pso.skeleton.Domain;
import org.lakeast.pso.skeleton.Factor;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.swarm.RingTopoSwarm;

public class SphereDomainTest {
	public static void main(String[] args) {
		try {
			new SphereDomainTest().testIt();
		} catch (InitializeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testIt() throws InitializeException {
		int numberOfIterations = 3000;
		int testCount = 10;

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

		int dimension = 100;

		Testable[] targets = new Testable[Y];

		Domain domain = new SphereDomain(dimension);

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
