package org.lakeast.pso.skeleton;

/**
 * @author WANG Zhen
 * @date 2007-5-20
 */
public interface INeighborhoodTopology {
	/**
	 * The neighborhood topology is a ring.
	 */
	public static final String RING = "Ring";

	/**
	 * The neighborhood topology is a star.
	 */
	public static final String STAR = "Star";

	/**
	 * The neighborhood topology is global (all particles are neighbors).
	 */
	public static final String GLOBAL = "Global";

	public AbstractParticle[] getNeighborhoods(AbstractParticle particle);

	/**
	 * Returns a String (static final field) identifying the neighborhood
	 * topology type.
	 * 
	 * @return a String field identifying the neighborhood topology type.
	 */
	public String getTopology();

	public boolean isStaticTopology();
}
