package org.lakeast.pso.skeleton;

import java.io.Serializable;

/**
 * @author WANG Zhen
 * @date 2007-5-26
 */
public class Factor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7864369340357206450L;

	private final double inertiaWeight;

	private final double iWeight;

	private final double sWeight;

	/**
	 * @param inertiaWeight
	 * @param iWeight
	 * @param sWeight
	 */
	public Factor(double inertiaWeight, double iWeight, double sWeight) {
		this.inertiaWeight = inertiaWeight;
		this.iWeight = iWeight;
		this.sWeight = sWeight;
	}

	/**
	 * @return the inertiaWeight
	 */
	public double getInertiaWeight() {
		return inertiaWeight;
	}

	/**
	 * @return the iWeight
	 */
	public double getIndividualityWeight() {
		return iWeight;
	}

	/**
	 * @return the sWeight
	 */
	public double getSocialityWeight() {
		return sWeight;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(inertiaWeight: " + inertiaWeight + ", iWeight: " + iWeight
				+ ", sWeight: " + sWeight + ")";
	}
}
