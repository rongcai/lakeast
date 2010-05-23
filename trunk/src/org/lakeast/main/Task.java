package org.lakeast.main;

import org.lakeast.common.InitializeException;
import org.lakeast.common.MyLicense;

public interface Task extends MyLicense {
	public void go(String[] args) throws InitializeException;
}
