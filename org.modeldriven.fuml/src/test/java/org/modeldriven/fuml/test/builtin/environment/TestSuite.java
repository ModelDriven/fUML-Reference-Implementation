/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.test.builtin.environment;

import fuml.Debug;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.syntax.structuredclassifiers.Association;

public class TestSuite {

	public TestEnvironment environment = null;

	private ActivityFactory activityFactory = null;
	private ClassifierFactory classifierFactory = null;
	private ExecutorTest executorTest = null;
	
	public TestSuite(TestEnvironment environment) {
		this.environment = environment;
		this.activityFactory = environment.activityFactory;
		this.classifierFactory = environment.classifierFactory;
		this.executorTest = environment.executorTest;
	} // TestSuite

	public ParameterValueList testSimpleActivities() {
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

		Debug.println("[testSimpleActivities] Testing...");

		ParameterValueList output = new ParameterValueList();
				
		output.addAll(this.executorTest.testExecute("Copier"));		
		output.addAll(this.executorTest.testExecute("CopierCaller"));		
		output.addAll(this.executorTest.testExecute("SimpleDecision0"));		
		output.addAll(this.executorTest.testExecute("SimpleDecision1"));		
		output.addAll(this.executorTest.testExecute("ForkJoin"));		
		output.addAll(this.executorTest.testExecute("DecisionJoin"));		
		output.addAll(this.executorTest.testExecute("ForkMerge"));		
		output.addAll(this.executorTest.testExecute("ForkMergeData"));		
		output.addAll(this.executorTest.testExecute("FlowFinal"));		

		Debug.println("[testSimpleActivities] Done!");
		
		return output;
	} // testSimpleActivites
	
	public ParameterValueList testSelfReader() {
		Debug.println("");
		Debug.println("[testSelfReader] Setting up...");
		
		this.activityFactory.createSelfReader();

		Debug.println("[testSelfReader] Testing...");
		
		ParameterValueList output = this.executorTest.testExecute("SelfReader");
		
		Debug.println("[testSelfReader] Done!");
		
		return output;
	}

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
		this.environment.printExtent("TestSignalAccepter");

		Debug.println("[testSignalSend] Done!");
	} // testSignalSend

	public void testCallSend() {
		Debug.println("[testCallSend] Setting up...");
		
		classifierFactory.createSignal("ContinueSignal");
		activityFactory.createCallSender("Test", "ContinueSignal");

		Debug.println("[testCallSend] Testing...");

		executorTest.testExecute("DoTestCallSender");
		this.environment.printExtent("TestCallSender");

		Debug.println("[testCallSend] Done!");
	} // testCallSend

	public ParameterValueList testStructuredNode() {
		Debug.println("");
		Debug.println("[testStructuredNode] Setting up...");
		this.activityFactory.createStructuredNodeTester("ForkMergeInput");

		Debug.println("[testStructuredNode] Testing...");
		ParameterValueList output = this.executorTest.testExecute("StructuredForkMergeInput");

		Debug.println("[testStructuredNode] Done!");
		
		return output;
	} // testStructuredNode

	public ParameterValueList testConditionalNode() {
		Debug.println("");
		Debug.println("[testConditionalNode] Setting up...");
		this.activityFactory.createConditionalNodeTester(0);
		this.activityFactory.createConditionalNodeTester(1);
		this.activityFactory.createConditionalNodeTester(2);

		Debug.println("[testConditionalNode] Testing...");
		ParameterValueList output = new ParameterValueList();
		output.addAll(this.executorTest.testExecute("ConditionalNodeTester_0"));
		output.addAll(this.executorTest.testExecute("ConditionalNodeTester_1"));
		output.addAll(this.executorTest.testExecute("ConditionalNodeTester_2"));

		Debug.println("[testConditionalNode] Done!");
		
		return output;
	} // testConditionalNode

	public ParameterValueList testLoopNode() {
		Debug.println("");
		Debug.println("[testLoopNode] Setting up...");
		this.activityFactory.createLoopNodeTester(0);
		this.activityFactory.createLoopNodeTester(1);
		this.activityFactory.createLoopNodeTester(2);

		Debug.println("[testLoopNode] Testing...");
		ParameterValueList output = new ParameterValueList();
		output.addAll(this.executorTest.testExecute("LoopNodeTester_0"));
		output.addAll(this.executorTest.testExecute("LoopNodeTester_1"));
		output.addAll(this.executorTest.testExecute("LoopNodeTester_2"));

		Debug.println("[testLoopNode] Done!");
		
		return output;
	} // testLoopNode

	public ParameterValueList testExpansionRegion() {
		Debug.println("");
		Debug.println("[testExpansionRegion] Setting up...");
		this.activityFactory.createExpansionRegionTester(0);
		this.activityFactory.createExpansionRegionTester(1);
		this.activityFactory.createExpansionRegionTester(2);

		Debug.println("[testExpansionRegion] Testing...");
		ParameterValueList output = new ParameterValueList();
		output.addAll(this.executorTest.testExecute("ExpansionRegionTester_0"));
		output.addAll(this.executorTest.testExecute("ExpansionRegionTester_1"));
		output.addAll(this.executorTest.testExecute("ExpansionRegionTester_2"));

		Debug.println("[testExpansionRegion] Done!");
		
		return output;
	} // testExpansionRegion

	public ParameterValueList testLinkCreator() {
		Debug.println("[testLinkCreator] Setting up...");

		classifierFactory.createClass("A");
		classifierFactory.createClass("B");

		this.environment.removeElement("AB");

		classifierFactory.createAssociation("AB");
		classifierFactory.addEnd("AB", "a", "A", false);
		classifierFactory.addEnd("AB", "b", "B", false);

		activityFactory.createLinkCreator("AB");

		Debug.println("[testLinkCreator] Testing...");

		ParameterValueList output = executorTest.testExecute("ABLinkCreator");
		this.environment.printExtent("AB");

		Debug.println("[testLinkCreator] Done!");
		
		return output;
	} // testLinkCreator

	public ParameterValueList testLinkDestroyer() {
		Debug.println("[testLinkDestroyer] Setting up...");

		classifierFactory.createClass("A");
		classifierFactory.createClass("B");

		this.environment.removeElement("AB");

		classifierFactory.createAssociation("AB");
		classifierFactory.addEnd("AB", "a", "A", false);
		classifierFactory.addEnd("AB", "b", "B", false);

		activityFactory.createLinkDestroyer("AB");

		Debug.println("[testLinkDestroyer] Testing...");

		ParameterValueList output = executorTest.testExecute("ABLinkDestroyer");
		this.environment.printExtent("AB");

		Debug.println("[testLinkDestroyer] Done!");
		
		return output;
	} // testLinkDestroyer

	public ParameterValueList testLinkReader() {
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

		ParameterValueList output = executorTest.testExecute("ABLinkReader");
		this.environment.printExtent("AB");

		Debug.println("[testLinkReader] Done!");
		
		return output;
	} // testLinkReader

	public ParameterValueList testLinkWriter() {
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

		ParameterValueList output = executorTest.testExecute("ABLinkWriter");
		this.environment.printExtent("AB");

		Debug.println("[testLinkWriter] Done!");
		
		return output;
	} // testLinkWriter

	public ParameterValueList testLinkRemover() {
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

		ParameterValueList output = executorTest.testExecute("ABLinkRemover");
		this.environment.printExtent("AB");

		Debug.println("[testLinkRemover] Done!");
		
		return output;
	} // testLinkRemover

	public ParameterValueList testWriterReader() {
		Debug.println("[testWriterReader] Setting up...");

		this.environment.removeElement("TestClass");
		classifierFactory.createClass("TestClass");
		classifierFactory.addAttribute("TestClass", "x", "Integer", false);

		activityFactory.createWriterReader("TestClass", "x");

		Debug.println("[testWriterReader] Testing...");

		ParameterValueList output = executorTest.testExecute("TestClass_x_WriterReader");
		this.environment.printExtent("TestClass");

		Debug.println("[testWriterReader] Done!");
		
		return output;
	} // testWriterReader

	public ParameterValueList testIsClassified() {
		Debug.println("[testIsClassified] Setting up...");

		classifierFactory.createSignal("Signal");
		activityFactory.createIsClassifiedTester("Signal");

		Debug.println("[testIsClassified] Testing...");

		ParameterValueList output = executorTest.testExecute("TestIsSignal");

		Debug.println("[testIsClassified] Done!");
		
		return output;
	} // testIsClassified

	public ParameterValueList testFireAgain() {
		Debug.println("[testFireAgain] Setting up...");

		activityFactory.createFireAgainTester();

		Debug.println("[testFireAgain] Testing...");

		ParameterValueList output = executorTest.testExecute("FireAgainTester");

		Debug.println("[testFireAgain] Done!");
		
		return output;
	} // testFireAgain
	
	public ParameterValueList testCentralBuffer() {
		Debug.println("[testCentralBuffer] Setting up...");

		activityFactory.createCentralBuffer();

		Debug.println("[testCentralBuffer] Testing...");

		ParameterValueList output = executorTest.testExecute("CentralBuffer");

		Debug.println("[testCentralBuffer] Done!");
		
		return output;
	}

	public ParameterValueList testDataStore() {
		Debug.println("[testDataStore] Setting up...");

		activityFactory.createDataStore();

		Debug.println("[testDataStore] Testing...");

		ParameterValueList output = executorTest.testExecute("DataStore");

		Debug.println("[testDataStore] Done!");
		
		return output;
	}

	public ParameterValueList testUnmarshaller() {
		Debug.println("[testUnmarshaller] Setting up...");

		this.environment.removeElement("TestClass");
		classifierFactory.createClass("TestClass");
		classifierFactory.addAttribute("TestClass", "x", "Integer", false);
		classifierFactory.addAttribute("TestClass", "y", "Boolean", false, false, 0, 2);
		classifierFactory.addAttribute("TestClass", "z", "String", false, true, 0, 0);

		activityFactory.createUnmarshaller("TestClass");

		Debug.println("[testUnmarshaller] Testing...");

		ParameterValueList output = executorTest.testExecute("TestClass_Unmarshaller");

		Debug.println("[testUnmarshaller] Done!");
		
		return output;
	}
	
	public ParameterValueList testStreamingCopierCaller() {
		Debug.println("");
		Debug.println("[testStreamingCaller] Setting up...");

		this.activityFactory.createStreamingCopier();
		this.activityFactory.createCaller("StreamingCopier", false, 2);

		Debug.println("[testStreamingCaller] Testing...");

		ParameterValueList output = this.executorTest.testExecute("StreamingCopierCaller");		

		Debug.println("[testStreamingCaller] Done!");
		
		return output;
		
	}

} // TestSuite
