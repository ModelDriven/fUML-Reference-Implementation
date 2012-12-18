
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

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Actions.CompleteActions.*;

public class TestSuite extends fUML.Test.Test {

	private fUML.Test.ActivityFactory activityFactory = null;
	private fUML.Test.ClassifierFactory classifierFactory = null;
	private fUML.Test.ExecutorTest executorTest = null;
	private fUML.Test.VariableUtility variableUtility = null;

	public TestSuite(fUML.Test.InitTestEnvironment init) {
		this.environment = init.environment;
		this.activityFactory = init.activityFactory;
		this.classifierFactory = init.classifierFactory;
		this.executorTest = init.executorTest;
		this.variableUtility = init.variableUtility;
	} // TestSuite

	public void testSimpleActivites() {
		Debug.println("");
		Debug.println("[testSimpleActivites] Setting up...");

		this.activityFactory.createCopier();
		this.activityFactory.createCaller("Copier");
		this.activityFactory.createSimpleDecision(0);
		this.activityFactory.createSimpleDecision(1);
		this.activityFactory.createForkJoin();
		this.activityFactory.createDecisionJoin();
		this.activityFactory.createForkMerge();
		this.activityFactory.createForkMergeData();
		this.activityFactory.createFlowFinal();
		this.activityFactory.createSelfReader();

		Debug.println("[testSimpleActivities] Testing...");

		this.executorTest.testExecute("Copier");
		this.executorTest.testExecute("CopierCaller");
		this.executorTest.testExecute("SimpleDecision0");
		this.executorTest.testExecute("SimpleDecision1");
		this.executorTest.testExecute("ForkJoin");
		this.executorTest.testExecute("DecisionJoin");
		this.executorTest.testExecute("ForkMerge");
		this.executorTest.testExecute("ForkMergeData");
		this.executorTest.testExecute("FlowFinal");
		this.executorTest.testExecute("SelfReader");

		Debug.println("[testSimpleActivities] Done!");
	} // testSimpleActivites

	public void testHelloWorld() {
		Debug.println("");
		Debug.println("[testHelloWorld] Setting up...");
		this.activityFactory.createHelloWorld2();

		Debug.println("[testHelloWorld] Testing...");
		this.executorTest.testExecute("HelloWorld2");

		Debug.println("[testHelloWorld] Done!");

	} // testHelloWorld

	public void testPolymorphicOperationCall(String superclassMethodName,
			String subclassMethodName) {
		Debug.println("");
		Debug.println("[testPolymorphicOperationCall] Setting up...");

		if (activityFactory.getActivity(superclassMethodName) == null)
			return;
		if (activityFactory.getActivity(subclassMethodName) == null)
			return;

		String superclassName = "Super_" + superclassMethodName;
		String subclassName = "Sub_" + subclassMethodName;

		if (this.environment.getElement(superclassName) != null) {
			Debug.println("[testPolymorphicOperationCall] Replacing class "
					+ superclassName + ".");
			this.environment.removeElement(superclassName);
		}

		if (this.environment.getElement(subclassName) != null) {
			Debug.println("[testPolymorphicOperationCall] Replacing class "
					+ subclassName + ".");
			this.environment.removeElement(subclassName);
		}

		this.classifierFactory.createClass(superclassName);
		this.classifierFactory.addOperation(superclassName, "", "test",
				superclassMethodName);

		this.classifierFactory.createClass(subclassName);
		this.classifierFactory.addOperation(subclassName, superclassName,
				"test", subclassMethodName);

		this.activityFactory.createPolymorphicOperationCaller(subclassName,
				superclassName, "test");

		Debug.println("[testPolymorphicOperationCall] Testing...");

		this.executorTest.testExecute(subclassName + superclassName
				+ "testCaller");

		Debug.println("[testPolymorphicOperationCall] Done!");

	} // testPolymorphicOperationCall

	public void testSuperCall(String superclassMethodName,
			String subclassMethodName) {
		Debug.println("");
		Debug.println("[testSuperCall] Setting up...");

		if (activityFactory.getActivity(superclassMethodName) == null)
			return;
		if (activityFactory.getActivity(subclassMethodName) == null)
			return;

		String superclassName = "Super_" + superclassMethodName;
		String subclassName = "Sub_" + subclassMethodName;

		if (this.environment.getElement(superclassName) != null) {
			Debug.println("[testSuperCall] Replacing class " + superclassName
					+ ".");
			this.environment.removeElement(superclassName);
		}

		if (this.environment.getElement(subclassName) != null) {
			Debug.println("[testSuperCall] Replacing class " + subclassName
					+ ".");
			this.environment.removeElement(subclassName);
		}

		// Debug.println("[testSuperCall] Creating class " + superclassName +
		// "...");

		this.classifierFactory.createClass(superclassName);
		this.classifierFactory.addOperation(superclassName, "", "test",
				superclassMethodName);
		this.activityFactory.createSelfCaller(superclassName, "test");
		// Activity callTestMethod =
		// (Activity)(this.environment.getElement(superclassName+"testSelfCaller"));
		this.classifierFactory.addOperation(superclassName, "", "callTest",
				superclassName + "testSelfCaller");
		// Debug.println("[testSuperCall] " + superclassName + "::" +
		// "callTest method = " + callTestMethod.name + ", context = " +
		// callTestMethod.context);

		// Debug.println("[testSuperCall] Creating class " + subclassName +
		// "...");

		this.classifierFactory.createClass(subclassName);
		this.classifierFactory.addOperation(subclassName, superclassName,
				"test", subclassMethodName);
		this.activityFactory.createMethodCaller(superclassName, "callTest");
		this.classifierFactory.addOperation(subclassName, superclassName,
				"callTest", superclassName + "callTestMethodCaller");

		// Debug.println("[testSuperCall] Adding generalization...");
		this.classifierFactory.addGeneralization(subclassName, superclassName);

		// Debug.println("[testSuperCall] Creating operation caller activity...");
		this.activityFactory.createOperationCaller(subclassName, "callTest");

		Debug.println("[testSuperCall] Testing...");

		this.executorTest.testExecute(subclassName + "callTestCaller");

		Debug.println("[testSuperCall] Done!");

	} // testSuperCall

	public void testSignalSend() {
		Debug.println("[testSignalSend] Setting up...");

		classifierFactory.createSignal("TestSignal");
		activityFactory.createSender("TestSignal");

		Debug.println("[testSignalSend] Testing...");

		executorTest.testExecute("TestSignalSender");

		Debug.println("[testSignalSend] Done!");
	} // testSignalSend

	public void testStructuredNode() {
		Debug.println("");
		Debug.println("[testStructuredNode] Setting up...");
		this.activityFactory.createStructuredNodeTester("ForkMergeInput");

		Debug.println("[testStructuredNode] Testing...");
		this.executorTest.testExecute("StructuredForkMergeInput");

		Debug.println("[testStructuredNode] Done!");
	} // testStructuredNode

	public void testConditionalNode() {
		Debug.println("");
		Debug.println("[testConditionalNode] Setting up...");
		this.activityFactory.createConditionalNodeTester(0);
		this.activityFactory.createConditionalNodeTester(1);
		this.activityFactory.createConditionalNodeTester(2);

		Debug.println("[testConditionalNode] Testing...");
		this.executorTest.testExecute("ConditionalNodeTester_0");
		this.executorTest.testExecute("ConditionalNodeTester_1");
		this.executorTest.testExecute("ConditionalNodeTester_2");

		Debug.println("[testConditionalNode] Done!");
	} // testConditionalNode

	public void testLoopNode() {
		Debug.println("");
		Debug.println("[testLoopNode] Setting up...");
		this.activityFactory.createLoopNodeTester(0);
		this.activityFactory.createLoopNodeTester(1);
		this.activityFactory.createLoopNodeTester(2);

		Debug.println("[testLoopNode] Testing...");
		this.executorTest.testExecute("LoopNodeTester_0");
		this.executorTest.testExecute("LoopNodeTester_1");
		this.executorTest.testExecute("LoopNodeTester_2");

		Debug.println("[testLoopNode] Done!");
	} // testLoopNode

	public void testExpansionRegion() {
		Debug.println("");
		Debug.println("[testExpansionRegion] Setting up...");
		this.activityFactory.createExpansionRegionTester(0);
		this.activityFactory.createExpansionRegionTester(1);
		this.activityFactory.createExpansionRegionTester(2);

		Debug.println("[testExpansionRegion] Testing...");
		this.executorTest.testExecute("ExpansionRegionTester_0");
		this.executorTest.testExecute("ExpansionRegionTester_1");
		this.executorTest.testExecute("ExpansionRegionTester_2");

		Debug.println("[testExpansionRegion] Done!");
	} // testExpansionRegion

	public void testLinkCreator() {
		Debug.println("[testLinkCreator] Setting up...");

		classifierFactory.createClass("A");
		classifierFactory.createClass("B");

		this.environment.removeElement("AB");

		classifierFactory.createAssociation("AB");
		classifierFactory.addEnd("AB", "a", "A", false);
		classifierFactory.addEnd("AB", "b", "B", false);

		activityFactory.createLinkCreator("AB");

		Debug.println("[testLinkCreator] Testing...");

		executorTest.testExecute("ABLinkCreator");
		this.environment.printExtent("AB");

		Debug.println("[testLinkCreator] Done!");
	} // testLinkCreator

	public void testLinkDestroyer() {
		Debug.println("[testLinkDestroyer] Setting up...");

		classifierFactory.createClass("A");
		classifierFactory.createClass("B");

		this.environment.removeElement("AB");

		classifierFactory.createAssociation("AB");
		classifierFactory.addEnd("AB", "a", "A", false);
		classifierFactory.addEnd("AB", "b", "B", false);

		activityFactory.createLinkDestroyer("AB");

		Debug.println("[testLinkDestroyer] Testing...");

		executorTest.testExecute("ABLinkDestroyer");
		this.environment.printExtent("AB");

		Debug.println("[testLinkDestroyer] Done!");
	} // testLinkDestroyer

	public void testLinkReader() {
		Debug.println("[testLinkReader] Setting up...");

		classifierFactory.createClass("A");
		classifierFactory.createClass("B");

		this.environment.removeElement("AB");

		classifierFactory.createAssociation("AB");
		classifierFactory.addEnd("AB", "a", "A", false);
		classifierFactory.addEnd("AB", "b", "B", false);
		((Association) this.environment.getElement("AB")).memberEnd.getValue(1).multiplicityElement
				.setIsOrdered(true);

		activityFactory.createLinkReader("AB");

		Debug.println("[testLinkReader] Testing...");

		executorTest.testExecute("ABLinkReader");
		this.environment.printExtent("AB");

		Debug.println("[testLinkReader] Done!");
	} // testLinkReader

	public void testLinkWriter() {
		Debug.println("[testLinkWriter] Setting up...");

		classifierFactory.createClass("A");
		classifierFactory.createClass("B");

		this.environment.removeElement("AB");

		classifierFactory.createAssociation("AB");
		classifierFactory.addEnd("AB", "a", "A", false);
		classifierFactory.addEnd("AB", "b", "B", false);
		((Association) this.environment.getElement("AB")).memberEnd.getValue(1).multiplicityElement
				.setIsOrdered(true);

		activityFactory.createLinkWriter("AB");

		Debug.println("[testLinkWriter] Testing...");

		executorTest.testExecute("ABLinkWriter");
		this.environment.printExtent("AB");

		Debug.println("[testLinkWriter] Done!");
	} // testLinkWriter

	public void testLinkRemover() {
		Debug.println("[testLinkRemover] Setting up (unordered end)...");

		classifierFactory.createClass("A");
		classifierFactory.createClass("B");

		this.environment.removeElement("AB");

		classifierFactory.createAssociation("AB");
		classifierFactory.addEnd("AB", "a", "A", false);
		classifierFactory.addEnd("AB", "b", "B", false);

		activityFactory.createLinkRemover("AB");

		Debug.println("[testLinkRemover] Testing (unordered end)...");

		executorTest.testExecute("ABLinkRemover");
		this.environment.printExtent("AB");

		Debug.println("[testLinkRemover] Setting up (ordered end)...");

		this.environment.locus.extensionalValues.clear();
		((Association) this.environment.getElement("AB")).memberEnd.getValue(1).multiplicityElement
				.setIsOrdered(true);
		((Association) this.environment.getElement("AB")).memberEnd.getValue(1).multiplicityElement
				.setIsUnique(false);

		this.environment.removeElement("ABLinkWriter");
		this.environment.removeElement("ABLinkRemover");
		activityFactory.createLinkRemover("AB");

		Debug.println("[testLinkRemover] Testing (ordered end)...");

		executorTest.testExecute("ABLinkRemover");
		this.environment.printExtent("AB");

		Debug.println("[testLinkRemover] Done!");
	} // testLinkRemover

	public void testWriterReader() {
		Debug.println("[testWriterReader] Setting up...");

		this.environment.removeElement("TestClass");
		classifierFactory.createClass("TestClass");
		classifierFactory.addAttribute("TestClass", "x", "Integer", false);

		activityFactory.createWriterReader("TestClass", "x");

		Debug.println("[testWriterReader] Testing...");

		executorTest.testExecute("TestClass_x_WriterReader");
		this.environment.printExtent("TestClass");

		Debug.println("[testWriterReader] Done!");
	} // testWriterReader

	public void testIsClassified() {
		Debug.println("[testIsClassified] Setting up...");

		classifierFactory.createSignal("Signal");
		activityFactory.createIsClassifiedTester("Signal");
		// activityFactory.createAttributedCaller("TestIsSignal");

		// variableUtility.setDefaultValue("caller",
		// "AttributedTestIsSignalCaller");
		// variableUtility.setAttributeValue("caller", "input",
		// environment.makeValue(environment.getType("Signal")));

		Debug.println("[testIsClassified] Testing...");

		executorTest.testExecute("TestIsSignal");
		// variableUtility.run("caller");

		Debug.println("[testIsClassified] Done!");
	} // testIsClassified

	public void testFireAgain() {
		Debug.println("[testFireAgain] Setting up...");

		activityFactory.createFireAgainTester();

		Debug.println("[testFireAgain] Testing...");

		executorTest.testExecute("FireAgainTester");

		Debug.println("[testFireAgain] Done!");

	} // testFireAgain

} // TestSuite
