
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

package fUML.Test;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class InitTestEnvironment extends org.modeldriven.fuml.FumlObject {

	public fUML.Test.TestEnvironment environment = null;
	public fUML.Test.ExecutorTest executorTest = null;
	public fUML.Test.ActivityFactory activityFactory = null;
	public fUML.Test.VariableUtility variableUtility = null;
	public fUML.Test.ClassifierFactory classifierFactory = null;
	public fUML.Test.TestSuite testSuite = null;

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
