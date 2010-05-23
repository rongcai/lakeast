package org.lakeast.pso.skeleton;

/**
 * @author WANG Zhen
 * @date 2007-5-26
 */
public class ConstantFactorGenerator extends AbstractFactorGenerator {

	public ConstantFactorGenerator(Factor initialFactor) {
		super(initialFactor);
	}

	public String toString() {
		return "ConstantFactorGenerator(InitialFactor: " + getInitialFactor()
				+ ")";
	}

	public Factor generateFactor(AbstractSwarm swarm) {
		return this.getInitialFactor();
	}
}
