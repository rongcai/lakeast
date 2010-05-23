package org.lakeast.main;

import org.junit.Test;
import org.lakeast.common.InitializeException;

public abstract class TaskTest implements Task {
	@Test
	public void testIt() {
		TaskScheduler scheduler = new TaskScheduler();
		scheduler.addTask(this);
		try {
			scheduler.go(null);
		} catch (InitializeException e) {
			e.printStackTrace();
		}
	}
}
