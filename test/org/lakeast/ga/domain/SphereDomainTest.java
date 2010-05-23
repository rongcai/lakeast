package org.lakeast.ga.domain;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.ga.chromosome.RealChromosome;
import org.lakeast.ga.gen.AdapativeFactorGenerator;
import org.lakeast.ga.skeleton.ConstantFactorGenerator;
import org.lakeast.ga.skeleton.Domain;
import org.lakeast.ga.skeleton.Factor;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.main.TestBatch;
import org.lakeast.main.TestException;
import org.lakeast.main.Testable;

public class SphereDomainTest {

	@Test
	public void testIt() throws InitializeException {
		int numberOfIterations = 3000;
		int testCount = 10;

		int popSize = 53;// 素数较好

		Constraint exitCondition = new Constraint(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY);

		TestBatch batch = new TestBatch(testCount, numberOfIterations,
				exitCondition);

		final int Y = 2;

		Testable[] targets = new Testable[Y];

		Factor initFactor = new Factor(0.75, 0.01);

		int dimension = 150;

		Domain domain = new SphereDomain(dimension);

		targets[0] = new Population(RealChromosome.class, popSize,
				new ConstantFactorGenerator(initFactor), domain);
		targets[1] = new Population(RealChromosome.class, popSize,
				new AdapativeFactorGenerator(initFactor, 0.15, 0.15), domain);

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
