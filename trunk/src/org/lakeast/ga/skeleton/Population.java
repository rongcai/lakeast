package org.lakeast.ga.skeleton;

import org.lakeast.common.Constraint;
import org.lakeast.common.Functions;
import org.lakeast.common.InitializeException;
import org.lakeast.common.Range;
import org.lakeast.main.Testable;

public class Population implements Testable {

	// private static final Log log = LogFactory.getLog(Population.class);

	/**
	 * @author WANG Zhen
	 * 
	 */
	public interface RepairCallback {
		public void repair(AbstractChromosome chromosome);
	}

	private final int popSize;

	private final Domain domain;

	private int generation;

	private final IFactorGenerator generator;

	private final String chromosomeType;

	public AbstractChromosome[] chromosomes;

	private final AbstractChromosome[] pool;

	int bestId;

	private AbstractChromosome historyBest;

	int worstId;

	public String getChromosomeTypeName() {
		return chromosomeType;
	}

	public Population(Class<? extends AbstractChromosome> chromosomeType,
			int popSize, IFactorGenerator generator, Domain domain)
			throws InitializeException {
		this.chromosomeType = chromosomeType.getSimpleName();
		this.popSize = popSize;
		this.generator = generator;
		this.domain = domain;
		this.chromosomes = new AbstractChromosome[popSize];
		this.pool = new AbstractChromosome[popSize];
		for (int i = 0; i < popSize; i++) {
			try {
				chromosomes[i] = (AbstractChromosome) chromosomeType
						.getConstructor(ConstraintSet.class).newInstance(
								domain.getConstraintSet());
			} catch (Exception ex) {
				throw new InitializeException(ex);
			}
		}
		reset();
	}

	public void reset() {
		domain.reset();
		for (int i = 0; i < chromosomes.length; i++) {
			chromosomes[i].initialize();
		}
		bestId = 0;
		worstId = 0;
		evaluateFitness();
		historyBest = chromosomes[bestId].clone();
		generation = 1;
	}

	private void evaluateFitness() {
		domain.calculateFitness(this);// 直接由domain代理
	}

	public boolean iterate(int numberOfIterations, Constraint exitCondition,
			double[] y) {
		// TODO 对二维变量引入图形化接口，进行实时显示。
		for (int i = 0; i < numberOfIterations; i++, generation++) {
			Factor factor = generator.generateFactor(this);
			selectAndCrossover(factor.getProbabilityOfCrossover());
			mutate(factor.getProbabilityOfMutation());
			evaluateFitness();
			elitist();
			if (y != null && (i + 1) % PRECISION == 0) {
				y[(i + 1) / PRECISION - 1] += this.getBestValue();
			}
			if (Range.inRange(getBestValue(), exitCondition.getMinimum(),
					exitCondition.getMaximum())) {
				return true;
			}
		}
		return false;
	}

	private void mutate(double pm) {
		for (int i = 0; i < chromosomes.length; i++) {
			chromosomes[i].mutate(pm);
		}
	}

	public void repair(RepairCallback c) {
		for (int i = 0; i < this.chromosomes.length; i++) {
			c.repair(this.chromosomes[i]);
		}
	}

	private void elitist() {
		if (chromosomes[bestId].getFitness() < historyBest.getFitness()) {
			chromosomes[worstId] = historyBest.clone();
			int id = 0;
			for (int i = 1; i < popSize; i++) {
				if (chromosomes[i].getFitness() < chromosomes[id].getFitness()) {
					id = i;
				}
			}
			// 更新
			bestId = worstId;
			worstId = id;
		} else {
			historyBest = chromosomes[bestId].clone();
		}
	}

	private static final int MAXCOUNT = 8;// 防止重复过多，退不出循环

	/**
	 * 轮盘赌选择并根据交叉概率随机与不同个体交叉产生新种群。
	 * 
	 * @param pc
	 */
	private void selectAndCrossover(double pc) {
		int len = chromosomes.length;
		double sum = 0.0;
		for (int i = 0; i < len; i++) {
			sum += chromosomes[i].getFitness();
		}
		Functions.assertTrue(sum > 0.0 && !Double.isInfinite(sum)
				&& !Double.isNaN(sum));
		double[] proportion = new double[len];
		proportion[0] = chromosomes[0].getFitness() / sum;
		for (int i = 1; i < len; i++) {
			proportion[i] = proportion[i - 1] + chromosomes[i].getFitness()
					/ sum;
		}
		int previous = 0; // 上一个被选择用来交叉的个体序号
		int counter1 = 0; // 被交叉个体的总个数
		int i = 0; // 当前已产生或者已经选择用来交叉的个体数目
		int k = 0; // 当前被pool中已被填充到的序号位
		boolean needed = false;
		int counter2 = 0;// 循环重复计数器
		while (i < len) {
			double rnd = Math.random();
			int selectedId = 0;
			for (int j = 0; j < len; j++) {
				if (rnd < proportion[j]) {
					selectedId = j;
					break;
				}
			}
			if (needed || Math.random() < pc) {
				if (counter1 % 2 == 1) {// 已有两个供交叉个体
					if (selectedId != previous || counter2 >= MAXCOUNT) {
						AbstractChromosome a = chromosomes[previous].clone();
						AbstractChromosome b = chromosomes[selectedId].clone();
						a.singlePointCrossover(b);
						pool[k++] = a;
						pool[k++] = b;
						needed = false;
						counter2 = 0;
					} else {// 产生的个体重复，继续轮盘赌
						needed = true;
						counter2++;
						continue;
					}
				} else {// 设置新交叉个体的id为previous值，以便比较接下来产生的个体是否相同
					previous = selectedId;
				}
				counter1++;
			} else {// 不交叉直接复制
				pool[k++] = chromosomes[selectedId].clone();
			}
			i++;
		}
		/*
		 * 如果由于达到种群数目而使最后一个待交叉的未得到交叉，直接复制它到种群。
		 */
		if (k < len) {
			pool[k++] = chromosomes[previous].clone();
		}
		Functions.assertTrue(k == len);
		Functions.assertTrue(!needed);
		chromosomes = pool.clone();
	}

	public AbstractChromosome getGlobalBest() {
		return historyBest;
	}

	public int getPopSize() {
		return popSize;
	}

	public double getGlobalBestFitness() {
		return getGlobalBest().getFitness();
	}

	public String getSimpleDescription() {
		return this.getClass().getSimpleName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Population(ChromosomeType: " + this.getChromosomeTypeName()
				+ ", PopSize: " + this.getPopSize() + ", Domain: "
				+ getDomainDescription() + ", Generator: "
				+ getFactorGeneratorDescription() + ")";
	}

	public String getDomainDescription() {
		return domain.toString();
	}

	public String getFactorGeneratorDescription() {
		return generator.toString();
	}

	public int getGeneration() {
		return this.generation;
	}

	/**
	 * @see org.lakeast.main.Testable#getSolution()
	 */
	public double[] getSolution() {
		return domain.getSolution();
	}

	public double getBestValue() {
		return domain.getBestValue();
	}

	/**
	 * O(popSize)
	 * 
	 * @return
	 */
	public AbstractChromosome getGlobalWorst() {
		return chromosomes[worstId];
	}

	public int getDimensionsCount() {
		return domain.getConstraintSet().getDimensionsCount();
	}

	public double getGlobalWorstFitness() {
		return getGlobalWorst().getFitness();
	}

	public int size() {
		return this.getPopSize();
	}

	public boolean iterate(int numberOfIterations, Constraint exitCondition) {
		return iterate(numberOfIterations, exitCondition, null);
	}
}
