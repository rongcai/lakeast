package org.lakeast.main;

import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.common.InitializeException;
import org.lakeast.common.Randoms;
import org.lakeast.ga.skeleton.BinaryChromosome;
import org.lakeast.ga.skeleton.ConstantFactorGenerator;
import org.lakeast.ga.skeleton.Population;
import org.lakeast.model.ca.QuotedPriceMatrix;
import org.lakeast.pso.skeleton.ConstrictFactorGenerator;
import org.lakeast.pso.skeleton.ExponentFactorGenerator;
import org.lakeast.pso.skeleton.IFactorGenerator;
import org.lakeast.pso.skeleton.NeighborhoodBestParticle;
import org.lakeast.pso.skeleton.RingTopoSwarm;

public class CAXORValueTaskTest implements Task{

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
		int[][] agents = new int[X][];

		for (int i = 0; i < X; i++) {
			agents[i] = new int[matrices[i].getBidsAmount()];
			for (int j = 0; j < matrices[i].getBidsAmount(); j++) {
				agents[i][j] = Randoms.intInRange(0, matrices[i]
						.getBidsAmount() / 2 - 1);
			}
		}

		for (int i = 0; i < X; i++) {
			targets[i][0] = new Population(BinaryChromosome.class, popSize,
					new ConstantFactorGenerator(initFactor1),
					new org.lakeast.ga.domain.CAXORDomain(matrices[i],
							probability, agents[i]));
			targets[i][1] = new RingTopoSwarm(NeighborhoodBestParticle.class,
					popSize, new org.lakeast.pso.domain.CAXORDomain(
							matrices[i], probability, agents[i]),
					neighborhoodSize, generator);
			targets[i][2] = new RingTopoSwarm(NeighborhoodBestParticle.class,
					popSize, new org.lakeast.pso.domain.CAXORDomainOfK(
							matrices[i], probability, agents[i]),
					neighborhoodSize, generator2);
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
