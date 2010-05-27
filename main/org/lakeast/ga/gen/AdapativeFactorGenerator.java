/*
 * Copyright Eric Wang
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.lakeast.ga.gen;

import java.util.Set;

import org.lakeast.common.Functions;
import org.lakeast.common.Range;
import org.lakeast.ga.skeleton.AbstractChromosome;
import org.lakeast.ga.skeleton.AbstractDomain;
import org.lakeast.ga.skeleton.DynamicFactorGenerator;
import org.lakeast.ga.skeleton.Factor;
import org.lakeast.ga.skeleton.Population;

public class AdapativeFactorGenerator extends DynamicFactorGenerator {
	private double lastPc;
	private double lastPm;
	private final double Kc;
	private final double Km;

	// Kc=Km=0.15较为合适
	public AdapativeFactorGenerator(Factor initFactor, double Kc, double Km) {
		super(initFactor);
		this.Kc = Kc;
		this.Km = Km;
		lastPc = initFactor.getProbabilityOfCrossover();
		lastPm = initFactor.getProbabilityOfMutation();
	}

	private final static byte[][] y = new byte[2][2];
	private final static byte[][] z = new byte[2][2];
	static {
		y[0][0] = 1;
		y[1][0] = -1;
		y[1][1] = 1;
		y[0][1] = -1;
		z[0][0] = 1;
		z[1][0] = -1;
		z[1][1] = -1;
		z[0][1] = 1;
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
		int size = p.getPopSize();
		double[][] vector = new double[size][];
		for (int i = 0; i < size; i++) {
			vector[i] = AbstractDomain.decode(p.chromosomes[i]);
		}
		Set<Object>[] sets = Functions.KMeans(vector, 4, p.chromosomes);
		int cMax = 0;
		int cMin = 0;
		for (int i = 0; i < sets.length; i++) {
			if (sets[i].size() > sets[cMax].size()) {
				cMax = i;
			}
			if (sets[i].size() < sets[cMin].size()) {
				cMin = i;
			}
		}
		int bestId = 0;
		int worstId = 0;
		AbstractChromosome best = p.getGlobalBest();
		AbstractChromosome worst = p.getGlobalWorst();
		for (int i = 0; i < sets.length; i++) {
			if (sets[i].contains(best)) {
				bestId = i;
				break;
			}
		}
		for (int i = 0; i < sets.length; i++) {
			if (sets[i].contains(worst)) {
				worstId = i;
				break;
			}
		}
		int delta = sets[cMax].size() - sets[cMin].size();
		Functions.assertTrue(delta != 0);// 群体个数不能是[2-4]的倍数，否则有可能出错。
		double gb = (sets[bestId].size() - sets[cMin].size()) / delta;
		double gw = (sets[worstId].size() - sets[cMin].size()) / delta;
		double u0b = 1 - gb;// best对小群的属度
		double u1b = gb;// best对大群的属度
		double u0w = 1 - gw;// worst对小群的属度
		double u1w = gw;// worst对大群的属度
		double[][] m = new double[2][2];
		double sum = 0.0;
		sum += m[0][0] = u0b * u0w;
		sum += m[0][1] = u0b * u1w;
		sum += m[1][0] = u1b * u0w;
		sum += m[1][1] = u1b * u1w;
		double sum2 = 0.0, sum3 = 0.0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				sum2 += m[i][j] * y[i][j];
				sum3 += m[i][j] * z[i][j];
			}
		}
		double deltaPc = sum2 / sum;
		double deltaPm = sum3 / sum;
		double t = Kc * lastPc;
		if (Math.abs(deltaPc) > t) {
			deltaPc = Math.signum(deltaPc) * t;
		}
		t = Km * lastPm;
		if (Math.abs(deltaPm) > t) {
			deltaPm = Math.signum(deltaPm) * t;
		}
		lastPc += deltaPc;
		lastPm += deltaPm;
		lastPc = Range.constrict(lastPc, 0.6, 0.95);
		lastPm = Range.constrict(lastPm, 0.005, 0.05);
		if (p.getGeneration() % 10 == 0) {
			System.out.printf("%f,%f\n", lastPc, lastPm);
			System.out.flush();
		}
		return new Factor(lastPc, lastPm);
	}
}
