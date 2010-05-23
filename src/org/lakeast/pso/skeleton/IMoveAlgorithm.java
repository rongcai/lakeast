package org.lakeast.pso.skeleton;

import org.lakeast.common.MyLicense;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public interface IMoveAlgorithm extends MyLicense {
	public void calculateNextLocation(double inertiaWeight, double iWeight,
			double sWeight, AbstractParticle[] neighborhoods,
			Location globalBest);
}
