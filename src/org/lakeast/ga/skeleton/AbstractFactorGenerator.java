/**
 * 
 */
package org.lakeast.ga.skeleton;

/**
 * @author WANG Zhen
 * 
 */
public abstract class AbstractFactorGenerator implements IFactorGenerator {
	public AbstractFactorGenerator(Factor initFactor) {
		this.initFactor = initFactor;
	}

	private Factor initFactor;

	/**
	 * @return the initFactor
	 */
	public Factor getInitFactor() {
		return initFactor;
	}

}
