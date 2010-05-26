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
