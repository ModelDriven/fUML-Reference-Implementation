/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.commonbehavior;

import java.util.ArrayList;

import fuml.Debug;

public class ExecutionQueue {
	
	private ArrayList<Execution> queue = new ArrayList<Execution>();

	private void run() {
		while (this.runNext());
	}
	
    private boolean runNext() {
		if (this.queue.size() == 0) {
			return false;
		} else {
			Execution execution = this.queue.get(0);
			this.queue.remove(0);
			if (execution.context.getTypes().size() > 0) {
				Debug.println("[runNext] execution = " + execution);
				execution.execute();
			}
			return true;
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
	
	public static boolean step() {
		return executionQueue.runNext();
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
