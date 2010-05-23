package org.lakeast.ga.domain;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.ga.chromosome.SequenceIntegerChromosome;
import org.lakeast.ga.gen.AdapativeFactorGenerator;
import org.lakeast.ga.skeleton.ConstantFactorGenerator;
import org.lakeast.ga.skeleton.Domain;
import org.lakeast.ga.skeleton.Factor;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.main.TestBatch;
import org.lakeast.main.TestException;
import org.lakeast.main.Testable;

public class TSPDomainTest {

	@Test
	public void testIt() throws InitializeException {
		int numberOfIterations = 2000;
		int testCount = 10;

		int popSize = 53;// 素数较好

		Constraint exitCondition = new Constraint(0, 0);

		TestBatch batch = new TestBatch(testCount, numberOfIterations,
				exitCondition);

		final int Y = 2;

		Testable[] targets = new Testable[Y];

		Factor initFactor = new Factor(0.75, 0.01);

		int dimension = 100;

		Domain domain = new TSPDomain(dimension);

		targets[0] = new Population(SequenceIntegerChromosome.class, popSize,
				new ConstantFactorGenerator(initFactor), domain);
		targets[1] = new Population(SequenceIntegerChromosome.class, popSize,
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
