package org.lakeast.main;

import java.util.ArrayList;

import org.lakeast.common.InitializeException;

public class TaskScheduler {
	private final ArrayList<Task> container = new ArrayList<Task>();

	public void addTask(Task task) {
		container.add(task);
	}

	public void go(String[] args) throws InitializeException {
		for (int i = 0; i < container.size(); i++) {
			container.get(i).go(args);
		}
	}
}
