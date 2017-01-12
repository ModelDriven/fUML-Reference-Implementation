
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.test.builtin.environment;

public class InitTestEnvironment extends org.modeldriven.fuml.FumlObject {

	public org.modeldriven.fuml.test.builtin.environment.TestEnvironment environment = null;
	public org.modeldriven.fuml.test.builtin.environment.ExecutorTest executorTest = null;
	public org.modeldriven.fuml.test.builtin.environment.ActivityFactory activityFactory = null;
	public org.modeldriven.fuml.test.builtin.environment.VariableUtility variableUtility = null;
	public org.modeldriven.fuml.test.builtin.environment.ClassifierFactory classifierFactory = null;
	public org.modeldriven.fuml.test.builtin.environment.TestSuite testSuite = null;

	public InitTestEnvironment() {
		// Debug.println("[InitTestEnvironment] Start...");

		environment = new TestEnvironment();
		executorTest = new ExecutorTest(environment);
		activityFactory = new ActivityFactory(environment);
		variableUtility = new VariableUtility(environment);
		classifierFactory = new ClassifierFactory(environment);

		testSuite = new TestSuite(this);

		// Debug.println("[InitTestEnvironment] Done...");

	} // InitTestEnvironment

} // InitTestEnvironment
