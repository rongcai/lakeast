/**
 * 
 */
package org.lakeast.ga.skeleton;

/**
 * @author WANG Zhen
 * 
 */
public class Factor {
	/*
	 * pc=0.8 pm=0.005
	 */
	private final double probabilityOfCrossover;
	private final double probabilityOfMutation;

	/**
	 * @return the probabilityOfCrossover
	 */
	public double getProbabilityOfCrossover() {
		return probabilityOfCrossover;
	}

	/**
	 * @return the probabilityOfMutation
	 */
	public double getProbabilityOfMutation() {
		return probabilityOfMutation;
	}

	public Factor(double probabilityOfCrossover, double probabilityOfMutation) {
		super();
		this.probabilityOfCrossover = probabilityOfCrossover;
		this.probabilityOfMutation = probabilityOfMutation;
	}
}
