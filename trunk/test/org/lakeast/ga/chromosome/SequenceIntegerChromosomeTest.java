package org.lakeast.ga.chromosome;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lakeast.common.Functions;
import org.lakeast.ga.skeleton.AbstractDomain;
import org.lakeast.ga.skeleton.ConstraintSet;

public class SequenceIntegerChromosomeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testDoublePointsCrossover() {
		ConstraintSet cSet = new ConstraintSet(10);
		SequenceIntegerChromosome a = new SequenceIntegerChromosome(cSet);
		SequenceIntegerChromosome b = new SequenceIntegerChromosome(cSet);
		for (int i = 0; i < 1000; i++) {
			a.initialize();
			b.initialize();
			System.out.println(a);
			System.out.println(b);
			a.singlePointCrossover(b);
			System.out.println(a);
			System.out.println(b);
			Functions.assertTrue(Functions.isUnrepeated(AbstractDomain
					.decode(a)));
			Functions.assertTrue(Functions.isUnrepeated(AbstractDomain
					.decode(b)));
		}
	}

}
