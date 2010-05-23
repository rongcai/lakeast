package org.lakeast.pso.skeleton;

/**
 * @author WANG Zhen
 * @date 2007-5-26
 */
public abstract class AbstractFactorGenerator implements IFactorGenerator {

	private final Factor initialFactor;

	public AbstractFactorGenerator(Factor initialFactor) {
		this.initialFactor = initialFactor;
	}

	public Factor getInitialFactor() {
		return initialFactor;
	}
}
