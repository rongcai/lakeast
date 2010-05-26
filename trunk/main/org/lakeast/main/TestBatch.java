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

package org.lakeast.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lakeast.common.Constraint;
import org.lakeast.common.ToStringBuffer;

public class TestBatch {
	private static final Log log = LogFactory.getLog(TestBatch.class);

	private final ArrayList<Testable> container = new ArrayList<Testable>();

	private final int numberOfIterations;
	private final int testCount;
	private final Constraint exitCondition;
	private final ArrayList<Double> optima = new ArrayList<Double>();
	private final boolean isCounted;

	public TestBatch(int testCount, int numberOfIterations,
			Constraint exitCondition) {
		this.testCount = testCount;
		this.numberOfIterations = numberOfIterations;
		this.exitCondition = exitCondition;
		isCounted = !(exitCondition.getMaximum() == exitCondition.getMinimum());
	}

	public void addTest(Testable target) {
		container.add(target);
		optima.add(1.0);
	}

	public void addTest(Testable target, double optimum) {
		container.add(target);
		optima.add(optimum);
	}

	public void run() throws TestException {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat();
			log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			log.info("START!^^^^^^^^^^^^"
					+ formatter.format(calendar.getTime()));
			log.debug("*******************DEBUG**********************");
			int size = container.size();
			int[] successCount = new int[size];
			int[] neededTotalGenerations = new int[size];
			double[] totalElapsedTime = new double[size];
			double[] squareSumOfElapsedTime = new double[size];
			double[] totalBestValues = new double[size];
			double[][] values = new double[size][testCount];
			double[] max = new double[size];
			double[] min = new double[size];
			for (int i = 0; i < size; i++) {
				max[i] = Double.MIN_VALUE;
				min[i] = Double.MAX_VALUE;
			}
			int[][] x = new int[size][numberOfIterations / Testable.PRECISION];// 成功次数
			double[][] y = new double[size][numberOfIterations
					/ Testable.PRECISION];// 函数值比值
			if (isCounted) {
				for (int i = 0; i < size; i++) {
					y[i] = null;// a must
				}
			}
			for (int i = 0; i < testCount; i++) {
				for (int j = 0; j < size; j++) {
					Testable target = container.get(j);
					target.reset();
					if (log.isDebugEnabled()) {
						log.debug("The " + (i + 1) + "th run of "
								+ target.getSimpleDescription() + " start.");
					}
					long start = System.currentTimeMillis();
					boolean isSolved = target.iterate(numberOfIterations,
							exitCondition, y[j]);
					long end = System.currentTimeMillis();
					if (log.isDebugEnabled()) {
						log.debug("The " + (i + 1) + "th run of "
								+ target.getSimpleDescription() + " complete.");
					}
					if (isSolved) {
						successCount[j]++;
						neededTotalGenerations[j] += target.getGeneration();
						x[j][target.getGeneration() / Testable.PRECISION]++;
					}
					totalElapsedTime[j] += (end - start) / 1000.0;
					squareSumOfElapsedTime[j] += totalElapsedTime[j]
							* totalElapsedTime[j];
					totalBestValues[j] += target.getBestValue();
					values[j][i] = target.getBestValue();
					if (target.getBestValue() > max[j]) {
						max[j] = target.getBestValue();
					}
					if (target.getBestValue() < min[j]) {
						min[j] = target.getBestValue();
					}
				}
			}
			log.debug("*******************DEBUG**********************");
			log.info("*******************INFO***********************");
			log.info("[TestCount: " + testCount + "]\t[NumberOfIterations: "
					+ numberOfIterations + "]");
			for (int i = 0; i < size; i++) {
				Testable target = container.get(i);
				log.info(target + ":");
				log.info("[SuccessRate: " + 100 * successCount[i]
						/ (double) testCount
						+ "%]\t[AverageNeededGenerations: "
						+ neededTotalGenerations[i] / (double) successCount[i]
						+ "]");
				double average = totalElapsedTime[i] / testCount;
				log.info("[AverageElapsedTime: "
						+ average
						+ " seconds]\t[StandardDeviationOfElapsedTime: "
						+ Math.sqrt((squareSumOfElapsedTime[i] - testCount
								* average * average)
								/ (testCount - 1)) + "]");
				log.info("[AverageBestValue: " + totalBestValues[i] / testCount
						+ "]");
				log.info("[LastBestValue: " + target.getBestValue() + "]");
				log.info("["
						+ ToStringBuffer.list(target.getSolution(),
								"LastSolution: (", ", ", ") ") + "]");
			}
			log.info("*******************INFO***********************");
			log.info("OVER!^^^^^^^^^^^^^" + formatter.format(new Date()));
			/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
			WritableWorkbook wb = Workbook.createWorkbook(new File(
					Testable.DATAPATH, formatter.format(calendar.getTime())
							.replace(":", "-")
							+ "-" + calendar.get(Calendar.SECOND) + ".xls"));
			WritableSheet ws = wb.createSheet("TestResult", 0);
			ws.addCell(new Label(0, 1, "Size"));
			ws.addCell(new Label(0, 2, "DimensionsCount"));
			ws.addCell(new Label(0, 3, "TestCount"));
			ws.addCell(new Label(0, 4, "SuccessRate(%)"));
			ws.addCell(new Label(0, 5, "AverageNeededGenerations"));
			ws.addCell(new Label(0, 6, "AverageElapsedTime(s)"));
			ws.addCell(new Label(0, 7, "StandardDeviationOfElapsedTime"));
			ws.addCell(new Label(0, 8, "AverageBestValue"));
			ws.addCell(new Label(0, 9, "Values"));
			ws.addCell(new Label(0, 10, "MaxValue"));
			ws.addCell(new Label(0, 11, "MinValue"));
			ws.addCell(new Label(0, 12, "LastSolution"));
			int offset = 13;
			for (int j = 0; j < numberOfIterations / Testable.PRECISION; j++) {
				ws.addCell(new Number(0, j + offset, (j + 1)
						* Testable.PRECISION));
			}
			for (int i = 0; i < size; i++) {
				Testable target = container.get(i);
				ws.addCell(new Label(i + 1, 0, target.toString()));
				ws.addCell(new Number(i + 1, 1, target.size()));
				ws.addCell(new Number(i + 1, 2, target.getDimensionsCount()));
				ws.addCell(new Number(i + 1, 3, testCount));
				ws.addCell(new Number(i + 1, 4, 100 * successCount[i]
						/ (double) testCount));
				ws.addCell(new Number(i + 1, 5, neededTotalGenerations[i]
						/ (double) successCount[i]));
				double average = totalElapsedTime[i] / testCount;
				ws.addCell(new Number(i + 1, 6, average));
				ws.addCell(new Number(i + 1, 7, Math
						.sqrt((squareSumOfElapsedTime[i] - testCount * average
								* average)
								/ (testCount - 1))));
				ws
						.addCell(new Number(i + 1, 8, totalBestValues[i]
								/ testCount));
				ws.addCell(new Label(i + 1, 9, ToStringBuffer.list(values[i],
						"", ",", "").toString()));
				ws.addCell(new Number(i + 1, 10, max[i]));
				ws.addCell(new Number(i + 1, 11, min[i]));
				ws.addCell(new Label(i + 1, 12, ToStringBuffer.list(
						target.getSolution(), "", ",", "").toString()));
				if (isCounted) {
					for (int j = 0; j < numberOfIterations / Testable.PRECISION; j++) {
						ws.addCell(new Number(i + 1, j + offset, x[i][j]));
						if (j < numberOfIterations / Testable.PRECISION - 1) {
							x[i][j + 1] += x[i][j];
						}
					}
				} else {
					for (int j = 0; j < numberOfIterations / Testable.PRECISION; j++) {
						ws.addCell(new Number(i + 1, j + offset, y[i][j]
								/ testCount / optima.get(i)));
					}
				}
			}
			wb.write();
			wb.close();
			/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */
		} catch (Exception ex) {
			throw new TestException(ex);
		}
	}
}
