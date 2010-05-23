package org.lakeast.ga.chromosome;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lakeast.common.Constraint;
import org.lakeast.ga.skeleton.ConstraintSet;

public class BinaryChromosomeTest {
	ConstraintSet cSet = new ConstraintSet(10, new Constraint(-1, 1));
	BinaryChromosome a;
	BinaryChromosome b;

	@Before
	public void setUp() throws Exception {
		cSet.setBits(63);
		a = new BinaryChromosome(cSet);
		b = new BinaryChromosome(cSet);
	}

	@After
	public void tearDown() throws Exception {
	}

	// @Test
	// public final void testInitialize() {
	// a.initialize();
	// System.out.println(a);
	// }
	//
	// @Test
	// public final void testSinglePointCrossover() {
	// a.initialize();
	// b.initialize();
	// System.out.println(a);
	// System.out.println(b);
	// a.singlePointCrossover(b);
	// System.out.println(a);
	// System.out.println(b);
	// }
	@Test
	public final void testuniformCrossover() {
		a.initialize();
		b.initialize();
		// System.out.println(a);
		// System.out.println(b);
	}
	// @Test
	// public final void testMutate() {
	// a.initialize();
	// System.out.println(a);
	// a.mutate(1);
	// System.out.println(a);
	// }

	// @Test
	// public final void testClone() {
	// a.initialize();
	// a.setFitness(100);
	// System.out.println(a);
	// System.out.println(a.clone());
	//		
	// }

	// @Test
	// public final void testGetValueOfDimension() {
	// a.initialize();
	// System.out.println(a);
	// System.out.println(a.getValueOfDimension(10));
	// }

}
