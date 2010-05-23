package org.lakeast.main;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.ga.skeleton.BinaryChromosome;
import org.lakeast.ga.skeleton.ConstantFactorGenerator;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.pso.skeleton.ConstrictFactorGenerator;
import org.lakeast.pso.skeleton.ExponentFactorGenerator;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.NeighborhoodBestParticle;
import org.lakeast.pso.skeleton.RingTopoSwarm;

public class CAL3ValueTaskTest {
	public static void main(String[] args) {
		try {
			new CAL3ValueTaskTest().go(args);
		} catch (InitializeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testIt() {
		main(null);
	}

	public void go(String[] args) throws InitializeException {
		int numberOfIterations = 2000;
		int testCount = 20;

		int popSize = 53;// 素数较好
		int neighborhoodSize = 6;

		Constraint exitCondition = new Constraint(0, 0);

		TestBatch batch = new TestBatch(testCount, numberOfIterations,
				exitCondition);

		final int X = 3;
		final int Y = 3;

		QuotedPriceMatrix matrices[] = new QuotedPriceMatrix[X];

		String path = new String("C:\\");

		matrices[0] = new QuotedPriceMatrix(path + "L3_500.txt");
		matrices[1] = new QuotedPriceMatrix(path + "L3_1000.txt");
		matrices[2] = new QuotedPriceMatrix(path + "L3_1500.txt");

		org.lakeast.ga.skeleton.Factor initFactor1 = new org.lakeast.ga.skeleton.Factor(
				0.75, 0.01);
		org.lakeast.pso.skeleton.Factor initFactor2 = new org.lakeast.pso.skeleton.Factor(
				1, 2, 2);
		Constraint constraint = new Constraint(0.3, 1.0);

		IFactorGenerator generator = new ConstrictFactorGenerator(
				numberOfIterations, constraint, 2.0);

		IFactorGenerator generator2 = new ExponentFactorGenerator(
				numberOfIterations, constraint, 2.0, initFactor2);

		double probability = 0.9;

		Testable[][] targets = new Testable[X][Y];

		for (int i = 0; i < X; i++) {
			targets[i][0] = new Population(
					BinaryChromosome.class,
					popSize,
					new ConstantFactorGenerator(initFactor1),
					new org.lakeast.ga.domain.CADomain(matrices[i], probability));
			targets[i][1] = new RingTopoSwarm(NeighborhoodBestParticle.class,
					popSize, new org.lakeast.pso.domain.CADomain(matrices[i],
							probability), neighborhoodSize, generator);
			targets[i][2] = new RingTopoSwarm(NeighborhoodBestParticle.class,
					popSize, new org.lakeast.pso.domain.CADomainOfK(
							matrices[i], probability), neighborhoodSize,
					generator2);
		}

		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				batch.addTest(targets[i][j]);
			}
		}

		try {
			batch.run();
		} catch (TestException e) {
			e.printStackTrace();
		}
	}
}
