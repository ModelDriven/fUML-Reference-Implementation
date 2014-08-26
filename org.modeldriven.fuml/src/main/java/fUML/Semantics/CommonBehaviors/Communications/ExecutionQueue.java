/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications copyright 2009-2012 Data Access Technologies, Inc.
 * Modifications copyright 2013 Ivar Jacobson International SA.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Semantics.CommonBehaviors.Communications;

import java.util.ArrayList;

import fUML.Debug;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution;

public class ExecutionQueue {
	
	private ArrayList<Execution> queue = new ArrayList<Execution>();

	private void run() {
		while (queue.size() > 0) {
			Execution execution = queue.get(0);
			queue.remove(0);
			if (execution.context.getTypes().size() > 0) {
				Debug.println("[run] execution = " + execution);
				execution.execute();
			}
		}
	}
	
	private void add(Execution execution) {
		queue.add(execution);
	}
	
	private static ExecutionQueue executionQueue;
	
	public static boolean notStarted() {
		return executionQueue == null;
	}
	
	public static void start(Execution execution) {
		// Debug.println("[start] execution = " + execution);
		executionQueue = new ExecutionQueue();
		executionQueue.add(execution);
		executionQueue.run();
		executionQueue = null;
	}
	
	public static void enqueue(Execution execution) {
		if (notStarted()) {
			start(execution);
		} else {
			Debug.println("[enqueue] execution = " + execution);
			executionQueue.add(execution);
		}
	}
}
