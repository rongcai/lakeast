/**
 * 
 */
package org.lakeast.ga.skeleton;

/**
 * @author WANG Zhen
 * 
 */
public class ConstantFactorGenerator extends AbstractFactorGenerator {

	public ConstantFactorGenerator(Factor initFactor) {
		super(initFactor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public Factor generateFactor(Population p) {
		return this.getInitFactor();
	}

}
