
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
import fUML.Syntax.Activities.ExtraStructuredActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Actions.CompleteActions.*;

import fUML.Semantics.Classes.Kernel.*;

public class ActivityFactory extends fUML.Test.Test {

	public ActivityFactory(fUML.Test.TestEnvironment environment) {
		this.environment = environment;

	} // ActivityFactory

	protected void addEdge(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity,
			fUML.Syntax.Activities.IntermediateActivities.ActivityEdge edge,
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode source,
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode target,
			fUML.Syntax.Classes.Kernel.ValueSpecification guard) {
		edge.setSource(source);
		edge.setTarget(target);
		edge.setGuard(guard);

		activity.addEdge(edge);

	} // addEdge

	protected void addNode(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity,
			fUML.Syntax.Activities.IntermediateActivities.ActivityNode node) {
		activity.addNode(node);

	} // addNode

	protected fUML.Syntax.Classes.Kernel.Parameter addParameter(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity,
			String name,
			fUML.Syntax.Classes.Kernel.ParameterDirectionKind direction,
			fUML.Syntax.Classes.Kernel.Type type) {
		Parameter parameter = new Parameter();
		parameter.setName(name);
		parameter.setType(type);
		parameter.setDirection(direction);
		parameter.setLower(1);
		parameter.setUpper(1);

		activity.addOwnedParameter(parameter);

		return parameter;
	} // addParameter

	protected fUML.Syntax.Classes.Kernel.Property addProperty(
			fUML.Syntax.Activities.IntermediateActivities.Activity activity,
			String name, fUML.Syntax.Classes.Kernel.Type type, int lower,
			int upper) {
		Property property = new Property();
		property.setName(name);
		property.setType(type);
		property.setLower(lower);
		property.setUpper(upper);

		activity.addOwnedAttribute(property);

		return property;

	} // addProperty

	protected fUML.Syntax.Actions.BasicActions.InputPin addInputPin(
			fUML.Syntax.Actions.BasicActions.Action action, String name,
			int lower, int upper) {
		// Debug.println("[addInputPin] name = " + name);

		InputPin inputPin = new InputPin();
		this.setPin(inputPin, name, lower, upper);
		action.input.addValue(inputPin);
		return inputPin;
	} // addInputPin

	protected fUML.Syntax.Actions.BasicActions.OutputPin addOutputPin(
			fUML.Syntax.Actions.BasicActions.Action action, String name,
			int lower, int upper) {
		// Debug.println("[addOutputPin] name = " + name);

		OutputPin outputPin = new OutputPin();
		this.setPin(outputPin, name, lower, upper);
		action.output.addValue(outputPin);
		return outputPin;
	} // addOutputPin

	protected fUML.Syntax.Actions.BasicActions.InputPin makeInputPin(
			String name, int lower, int upper) {
		// Debug.println("[makeInputPin] name = " + name);

		InputPin inputPin = new InputPin();
		this.setPin(inputPin, name, lower, upper);

		return inputPin;
	} // makeInputPin

	protected fUML.Syntax.Actions.BasicActions.OutputPin makeOutputPin(
			String name, int lower, int upper) {
		// Debug.println("[makeOutputPin] name = " + name);

		OutputPin outputPin = new OutputPin();
		this.setPin(outputPin, name, lower, upper);

		return outputPin;

	} // makeOutputPin

	protected void setPin(fUML.Syntax.Actions.BasicActions.Pin pin,
			String name, int lower, int upper) {
		// Debug.println("[setPin] name = " + name);

		pin.setName(name);
		pin.setLower(lower);
		pin.setUpper(upper);

		// this.addNode(activity, pin);
	} // setPin

	protected void setMultiplicity(
			fUML.Syntax.Classes.Kernel.MultiplicityElement element, int lower,
			int upper) {
		element.setLower(lower);
		element.setUpper(upper);
	} // setMultiplicity

	protected fUML.Syntax.Classes.Kernel.Operation getOperation(
			fUML.Syntax.Classes.Kernel.Class_ class_, String operationName) {
		for (int i = 0; i < class_.member.size(); i++) {
			NamedElement member = class_.member.getValue(i);
			if (member.name.equals(operationName)) {
				if (!(member instanceof Operation)) {
					return null;
				}
				return (Operation) member;
			}
		}

		return null;
	} // getOperation

	protected fUML.Syntax.Classes.Kernel.Property getProperty(
			fUML.Syntax.Classes.Kernel.Classifier classifier,
			String propertyName) {
		for (int i = 0; i < classifier.member.size(); i++) {
			NamedElement member = classifier.member.getValue(i);
			if (member.name.equals(propertyName)) {
				if (!(member instanceof Property)) {
					return null;
				}
				return (Property) member;
			}
		}

		return null;
	} // getProperty

	protected fUML.Syntax.Classes.Kernel.LiteralInteger createLiteralInteger(
			String name, int value) {
		LiteralInteger literal = (LiteralInteger) (this.environment
				.makeValue((Classifier) (this.environment.primitiveTypes.Integer))
				.specify());
		literal.setName(name);
		literal.setValue(value);

		return literal;

	} // createLiteralInteger

	protected fUML.Syntax.Classes.Kernel.LiteralString createLiteralString(
			String name, String value) {
		LiteralString literal = (LiteralString) (this.environment
				.makeValue((Classifier) (this.environment.primitiveTypes.String))
				.specify());
		literal.setName(name);
		literal.setValue(value);

		return literal;

	} // createLiteralString

	protected fUML.Syntax.Classes.Kernel.LiteralBoolean createLiteralBoolean(
			String name, boolean value) {
		LiteralBoolean literal = (LiteralBoolean) (this.environment
				.makeValue((Classifier) (this.environment.primitiveTypes.Boolean))
				.specify());
		literal.setName(name);
		literal.setValue(value);

		return literal;

	} // createLiteralBoolean

	protected fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural createLiteralUnlimitedNatural(
			String name, int value) {
		LiteralUnlimitedNatural literal = (LiteralUnlimitedNatural) (this.environment
				.makeValue((Classifier) (this.environment.primitiveTypes.UnlimitedNatural))
				.specify());
		literal.setName(name);
		literal.value.naturalValue = value;

		return literal;
	} // createLiteralUnlimitedNatural

	protected fUML.Syntax.Activities.IntermediateActivities.Activity createInstanceGetter(
			fUML.Syntax.Classes.Kernel.Classifier classifier,
			fUML.Syntax.Classes.Kernel.Operation operation,
			fUML.Semantics.Classes.Kernel.Value value) {
		Activity testActivity = new Activity();
		testActivity.setName("Test(" + operation.name + ")");

		Parameter testInput = this.addParameter(testActivity, "testInput",
				ParameterDirectionKind.in, classifier);
		Parameter testOutput = this.addParameter(testActivity, "testOutput",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Boolean);

		ActivityParameterNode testInputNode = new ActivityParameterNode();
		testInputNode.setName("TestInputNode");
		testInputNode.setParameter(testInput);
		testInputNode.setType(testInput.type);
		this.addNode(testActivity, testInputNode);

		CallOperationAction callOperationAction = new CallOperationAction();
		callOperationAction.setName("Call(" + operation.name + ")");
		callOperationAction.setOperation(operation);
		callOperationAction.setTarget(this.makeInputPin(
				callOperationAction.name + ".object", 1, 1));
		callOperationAction.addResult(this.makeOutputPin(
				callOperationAction.name + ".result", 1, 1));
		this.addNode(testActivity, callOperationAction);

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("Value(" + value + ")");
		valueAction.setValue(value.specify());
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(testActivity, valueAction);

		TestIdentityAction testIdentityAction = new TestIdentityAction();
		testIdentityAction
				.setName("Test(" + operation.name + "=" + value + ")");
		testIdentityAction.setFirst(this.makeInputPin("Test.first", 1, 1));
		testIdentityAction.setSecond(this.makeInputPin("Test.second", 1, 1));
		testIdentityAction.setResult(this.makeOutputPin("Test.result", 1, 1));
		this.addNode(testActivity, testIdentityAction);

		ActivityParameterNode testOutputNode = new ActivityParameterNode();
		testOutputNode.setName("TestOutputNode");
		testOutputNode.setParameter(testOutput);
		testOutputNode.setType(testOutput.type);
		this.addNode(testActivity, testOutputNode);

		this.addEdge(testActivity, new ObjectFlow(), testInputNode,
				callOperationAction.target, null);
		this.addEdge(testActivity, new ObjectFlow(), callOperationAction.result
				.getValue(0), testIdentityAction.first, null);
		this.addEdge(testActivity, new ObjectFlow(), valueAction.result,
				testIdentityAction.second, null);
		this.addEdge(testActivity, new ObjectFlow(), testIdentityAction.result,
				testOutputNode, null);

		Activity instanceGetterActivity = new Activity();
		instanceGetterActivity.setName(classifier.name + "InstanceGetter");

		Parameter instanceOutput = this.addParameter(instanceGetterActivity,
				"instance", ParameterDirectionKind.out, classifier);

		ReadExtentAction readExtentAction = new ReadExtentAction();
		readExtentAction.setName("ReadExtent(" + classifier.name + ")");
		readExtentAction.setClassifier(classifier);
		readExtentAction.setResult(this.makeOutputPin(readExtentAction.name
				+ ".result", 0, -1));
		this.addNode(instanceGetterActivity, readExtentAction);

		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setName("Decision(" + operation.name + "=" + value + ")");
		decisionNode.setDecisionInput(testActivity);
		this.addNode(instanceGetterActivity, decisionNode);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(instanceOutput);
		outputNode.setType(instanceOutput.type);
		this.addNode(instanceGetterActivity, outputNode);

		this.addEdge(instanceGetterActivity, new ObjectFlow(),
				readExtentAction.result, decisionNode, null);
		this.addEdge(instanceGetterActivity, new ObjectFlow(), decisionNode,
				outputNode, this.createLiteralBoolean("true", true));

		return instanceGetterActivity;
	} // createInstanceGetter

	protected fUML.Syntax.Activities.IntermediateActivities.Activity getCopier() {
		return this.getActivity("Copier");
	} // getCopier

	public fUML.Syntax.Activities.IntermediateActivities.Activity getActivity(
			String name) {
		NamedElement activity = this.environment.getElement(name);

		if ((activity == null) || !(activity instanceof Activity)) {
			if (activity == null) {
				Debug
						.println("[getActivity] Creating a " + name
								+ " activity.");
			} else {
				Debug.println("[getActivity] Replacing the existing " + name
						+ " element with an activity.");
				this.environment.removeElement(activity);
				activity = null;
			}

			try {
				this.getClass().getMethod("create" + name, new Class[0])
						.invoke(this, new Object[0]);
				activity = this.environment.getElement(name);
			} catch (NoSuchMethodException e) {
				Debug.println("[getActivity] create" + name + "() not found.");
			} catch (Exception e) {
				Debug.println("[getActivity] Exeception: " + e);
			}
		}

		return (Activity) activity;
	} // getActivity

	public void createCopier() {
		Activity activity = new Activity();
		activity.setName("Copier");

		Parameter inputParameter = this.addParameter(activity, "input",
				ParameterDirectionKind.in,
				this.environment.primitiveTypes.Integer);
		Parameter outputParameter = this.addParameter(activity, "output",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer);

		// Debug.println("[createCopier] Creating input node...");

		ActivityParameterNode inputNode = new ActivityParameterNode();
		inputNode.setName("inputNode");
		inputNode.setParameter(inputParameter);
		this.addNode(activity, inputNode);

		// Debug.println("[createCopier] Creating output node...");

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("outputNode");
		outputNode.setParameter(outputParameter);
		this.addNode(activity, outputNode);

		// Debug.println("[createCopier] Creating edge...");

		this.addEdge(activity, new ObjectFlow(), inputNode, outputNode, null);

		this.environment.addElement(activity);

	} // createCopier

	public void createCaller(String calledActivityName) {
		Element element = this.getActivity(calledActivityName);

		Activity calledActivity = (Activity) element;
		ParameterList parameters = calledActivity.ownedParameter;

		Activity callerActivity = new Activity();
		callerActivity.setName(calledActivityName + "Caller");

		CallBehaviorAction callAction = new CallBehaviorAction();
		callAction.setName("Call(" + calledActivityName + ")");
		callAction.setBehavior(calledActivity);
		this.addNode(callerActivity, callAction);

		for (int i = 0; i < parameters.size(); i++) {
			Parameter parameter = parameters.getValue(i);

			if (parameter.direction.equals(ParameterDirectionKind.in)
					|| parameter.direction.equals(ParameterDirectionKind.inout)) {
				ValueSpecification valueSpecification = this.environment
						.makeValue((Classifier) (parameter.type)).specify();
				ValueSpecificationAction valueAction = new ValueSpecificationAction();
				valueAction.setName("Value(" + parameter.name + ":"
						+ parameter.type.name + ")");
				valueAction.setValue(valueSpecification);
				valueAction.setResult(this.makeOutputPin(valueAction.name
						+ ".result", 1, 1));
				this.addNode(callerActivity, valueAction);

				InputPin inputPin = this.makeInputPin(callAction.name
						+ ".argument[" + parameter.name + "]", 1, 1);
				callAction.addArgument(inputPin);

				this.addEdge(callerActivity, new ObjectFlow(),
						valueAction.result, inputPin, null);
			} else {
				ActivityParameterNode parameterNode = new ActivityParameterNode();
				parameterNode.setName("Parameter(" + parameter.name + ")");
				parameterNode.setParameter(this.addParameter(callerActivity,
						parameter.name, ParameterDirectionKind.out,
						parameter.type));
				this.addNode(callerActivity, parameterNode);

				OutputPin outputPin = this.makeOutputPin(callAction.name
						+ ".result[" + parameter.name + "]", 1, 1);
				callAction.addResult(outputPin);

				this.addEdge(callerActivity, new ObjectFlow(), outputPin,
						parameterNode, null);
			}
		}

		this.environment.addElement(callerActivity);

	} // createCaller

	public void createSimpleDecision(int testValue) {
		Activity copierActivity = this.getCopier();

		Activity simpleDecisionActivity = new Activity();
		simpleDecisionActivity.setName("SimpleDecision" + testValue);

		Parameter parameter0 = this.addParameter(simpleDecisionActivity,
				"output_0", ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer);
		Parameter parameter1 = this.addParameter(simpleDecisionActivity,
				"output_1", ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer);

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("ValueAction_" + testValue);
		valueAction.setValue(this.createLiteralInteger("", testValue));
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(simpleDecisionActivity, valueAction);

		// UnlimitedNatural unlimitedNatural1 = new UnlimitedNatural(1);

		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setName("DecisionNode");
		// decisionNode.setDecisionInput(copierActivity);
		this.addNode(simpleDecisionActivity, decisionNode);

		ActivityParameterNode parameterNode0 = new ActivityParameterNode();
		parameterNode0.setName("ParameterNode_0");
		parameterNode0.setParameter(parameter0);
		this.addNode(simpleDecisionActivity, parameterNode0);

		ActivityParameterNode parameterNode1 = new ActivityParameterNode();
		parameterNode1.setName("ParameterNode_1");
		parameterNode1.setParameter(parameter1);
		this.addNode(simpleDecisionActivity, parameterNode1);

		LiteralInteger value0 = this.createLiteralInteger("guard_0", 0);
		LiteralInteger value1 = this.createLiteralInteger("guard_1", 1);

		this.addEdge(simpleDecisionActivity, new ObjectFlow(),
				valueAction.result, decisionNode, null);
		this.addEdge(simpleDecisionActivity, new ObjectFlow(), decisionNode,
				parameterNode0, value0);
		this.addEdge(simpleDecisionActivity, new ObjectFlow(), decisionNode,
				parameterNode1, value1);

		this.environment.addElement(simpleDecisionActivity);
	} // createSimpleDecision

	public void createForkJoin() {
		Activity forkJoinActivity = new Activity();
		forkJoinActivity.setName("ForkJoin");

		InitialNode initialNode = new InitialNode();
		initialNode.setName("InitialNode");
		this.addNode(forkJoinActivity, initialNode);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		this.addNode(forkJoinActivity, forkNode);

		JoinNode joinNode = new JoinNode();
		joinNode.setName("JoinNode");
		this.addNode(forkJoinActivity, joinNode);

		ActivityFinalNode finalNode = new ActivityFinalNode();
		finalNode.setName("FinalNode");
		this.addNode(forkJoinActivity, finalNode);

		this.addEdge(forkJoinActivity, new ControlFlow(), initialNode,
				forkNode, null);
		this.addEdge(forkJoinActivity, new ControlFlow(), forkNode, joinNode,
				null);
		this.addEdge(forkJoinActivity, new ControlFlow(), forkNode, joinNode,
				null);
		this.addEdge(forkJoinActivity, new ControlFlow(), joinNode, finalNode,
				null);

		this.environment.addElement(forkJoinActivity);
	} // createForkJoin

	public void createDecisionJoin() {
		Activity copierActivity = this.getCopier();

		Activity decisionJoinActivity = new Activity();
		decisionJoinActivity.setName("DecisionJoin");

		ValueSpecificationAction valueAction0 = new ValueSpecificationAction();
		valueAction0.setName("ValueAction_0");
		valueAction0.setValue(this.createLiteralInteger("", 0));
		valueAction0.setResult(this.makeOutputPin(
				valueAction0.name + ".result", 1, 1));
		this.addNode(decisionJoinActivity, valueAction0);

		ValueSpecificationAction valueAction1 = new ValueSpecificationAction();
		valueAction1.setName("ValueAction_1");
		valueAction1.setValue(this.createLiteralInteger("", 1));
		valueAction1.setResult(this.makeOutputPin(
				valueAction1.name + ".result", 1, 1));
		this.addNode(decisionJoinActivity, valueAction1);

		CallBehaviorAction callActionA = new CallBehaviorAction();
		callActionA.setName("Action_A");
		callActionA.setBehavior(copierActivity);
		callActionA.addArgument(this.makeInputPin(callActionA.name
				+ ".argument", 1, 1));
		callActionA.addResult(this.makeOutputPin(callActionA.name + ".result",
				1, 1));
		this.addNode(decisionJoinActivity, callActionA);

		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setName("DecisionNode");
		decisionNode.setDecisionInput(null);
		this.addNode(decisionJoinActivity, decisionNode);

		JoinNode joinNode = new JoinNode();
		joinNode.setName("JoinNode");
		this.addNode(decisionJoinActivity, joinNode);

		CallBehaviorAction callActionB = new CallBehaviorAction();
		callActionB.setName("Action_B");
		callActionB.setBehavior(copierActivity);
		callActionB.addArgument(this.makeInputPin(callActionB.name
				+ ".argument", 2, 2));
		callActionB.addResult(this.makeOutputPin(callActionB.name + ".result",
				2, 2));
		this.addNode(decisionJoinActivity, callActionB);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(decisionJoinActivity,
				"output", ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		this.addNode(decisionJoinActivity, outputNode);

		this.addEdge(decisionJoinActivity, new ControlFlow(), valueAction0,
				valueAction1, null);
		this.addEdge(decisionJoinActivity, new ObjectFlow(),
				valueAction0.result, callActionA.argument.getValue(0), null);
		this.addEdge(decisionJoinActivity, new ObjectFlow(),
				valueAction1.result, callActionA.argument.getValue(0), null);
		this.addEdge(decisionJoinActivity, new ObjectFlow(), callActionA.result
				.getValue(0), decisionNode, null);
		this.addEdge(decisionJoinActivity, new ObjectFlow(), decisionNode,
				joinNode, this.createLiteralInteger("guard_0", 0));
		this.addEdge(decisionJoinActivity, new ObjectFlow(), decisionNode,
				joinNode, this.createLiteralInteger("guard_1", 1));
		this.addEdge(decisionJoinActivity, new ObjectFlow(), joinNode,
				callActionB.argument.getValue(0), null);
		this.addEdge(decisionJoinActivity, new ObjectFlow(), callActionB.result
				.getValue(0), outputNode, null);

		this.environment.addElement(decisionJoinActivity);

	} // createDecisionJoin

	public void createForkMerge() {
		Activity forkMergeActivity = new Activity();
		forkMergeActivity.name = "ForkMerge";

		InitialNode initialNode = new InitialNode();
		initialNode.setName("InitialNode");
		this.addNode(forkMergeActivity, initialNode);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		this.addNode(forkMergeActivity, forkNode);

		MergeNode mergeNode = new MergeNode();
		mergeNode.setName("MergeNode");
		this.addNode(forkMergeActivity, mergeNode);

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("ValueAction_0");
		valueAction.setValue(this.createLiteralInteger("", 0));
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(forkMergeActivity, valueAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(forkMergeActivity, "output",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		this.addNode(forkMergeActivity, outputNode);

		this.addEdge(forkMergeActivity, new ControlFlow(), initialNode,
				forkNode, null);
		this.addEdge(forkMergeActivity, new ControlFlow(), forkNode, mergeNode,
				null);
		this.addEdge(forkMergeActivity, new ControlFlow(), forkNode, mergeNode,
				null);
		this.addEdge(forkMergeActivity, new ControlFlow(), mergeNode,
				valueAction, null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), valueAction.result,
				outputNode, null);

		this.environment.addElement(forkMergeActivity);

	} // createForkMerge

	public void createForkMergeData() {
		Activity forkMergeActivity = new Activity();
		forkMergeActivity.name = "ForkMergeData";

		ValueSpecificationAction actionA = new ValueSpecificationAction();
		actionA.setName("Action_A");
		actionA.setValue(this.createLiteralInteger("", 0));
		actionA.setResult(this.makeOutputPin(actionA.name + ".result", 1, 1));
		this.addNode(forkMergeActivity, actionA);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		this.addNode(forkMergeActivity, forkNode);

		MergeNode mergeNode = new MergeNode();
		mergeNode.setName("MergeNode");
		this.addNode(forkMergeActivity, mergeNode);

		CallBehaviorAction actionB = new CallBehaviorAction();
		actionB.setName("Action_B");
		actionB.setBehavior(this.getCopier());
		actionB
				.addArgument(this
						.makeInputPin(actionB.name + ".argument", 1, 1));
		actionB.addResult(this.makeOutputPin(actionB.name + ".result", 1, 1));
		this.addNode(forkMergeActivity, actionB);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(forkMergeActivity, "output",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		this.addNode(forkMergeActivity, outputNode);

		this.addEdge(forkMergeActivity, new ObjectFlow(), actionA.result,
				forkNode, null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), forkNode, mergeNode,
				null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), forkNode, mergeNode,
				null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), mergeNode,
				actionB.argument.getValue(0), null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), actionB.result
				.getValue(0), outputNode, null);

		this.environment.addElement(forkMergeActivity);
	} // createForkMergeData

	public void createExtentReader(String className) {
		Element element = environment.getElement(className);

		if (element == null) {
			Debug.println("[createExtentReader] " + className + " not found.");
			return;
		}

		if (!(element instanceof Class_)) {
			Debug.println("[createExtentReader] " + className
					+ " is not a class.");
			return;
		}

		Class_ theClass = (Class_) element;

		Activity extentReaderActivity = new Activity();
		extentReaderActivity.setName(className + "ExtentReader");

		ReadExtentAction readExtentAction = new ReadExtentAction();
		readExtentAction.setName("ReadExtent(" + theClass.name + ")");
		readExtentAction.setClassifier(theClass);
		readExtentAction.setResult(this.makeOutputPin(readExtentAction.name
				+ ".result", 0, -1));
		this.addNode(extentReaderActivity, readExtentAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(extentReaderActivity,
				"extent", ParameterDirectionKind.out, theClass));
		this.addNode(extentReaderActivity, outputNode);

		this.addEdge(extentReaderActivity, new ObjectFlow(),
				readExtentAction.result, outputNode, null);

		this.environment.addElement(extentReaderActivity);
	} // createExtentReader

	public void createSelfReader() {
		Activity selfReaderActivity = new Activity();
		selfReaderActivity.setName("SelfReader");

		ReadSelfAction readSelfAction = new ReadSelfAction();
		readSelfAction.setName("ReadSelf");
		readSelfAction.setResult(this.addOutputPin(readSelfAction,
				readSelfAction.name + ".result", 1, 1));
		this.addNode(selfReaderActivity, readSelfAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(selfReaderActivity, "self",
				ParameterDirectionKind.out, selfReaderActivity));
		this.addNode(selfReaderActivity, outputNode);

		this.addEdge(selfReaderActivity, new ObjectFlow(),
				readSelfAction.result, outputNode, null);

		this.environment.addElement(selfReaderActivity);
	} // createSelfReader

	public void createIdentityTester(String nameSuffix, String variableName1,
			String variableName2) {
		Variable variable1 = this.environment.getVariable(variableName1);
		Variable variable2 = this.environment.getVariable(variableName2);

		if (variable1 == null) {
			Debug.println("[createIdentityTester] " + variableName1
					+ " does not exist.");
			return;
		}

		if (variable2 == null) {
			Debug.println("[createIdentityTester] " + variableName2
					+ " does not exist.");
			return;
		}

		Value value1 = variable1.value;
		Value value2 = variable2.value;

		Activity identityTesterActivity = new Activity();
		identityTesterActivity.setName("IdentityTester_" + nameSuffix);

		ValueSpecificationAction valueAction1 = new ValueSpecificationAction();
		valueAction1.setName("ValueAction_1");
		valueAction1.setValue(value1.specify());
		valueAction1.setResult(this.makeOutputPin(
				valueAction1.name + ".result", 1, 1));
		this.addNode(identityTesterActivity, valueAction1);

		ValueSpecificationAction valueAction2 = new ValueSpecificationAction();
		valueAction2.setName("ValueAction_2");
		valueAction2.setValue(value2.specify());
		valueAction2.setResult(this.addOutputPin(valueAction2,
				valueAction2.name + ".result", 1, 1));
		this.addNode(identityTesterActivity, valueAction2);

		TestIdentityAction testIdentityAction = new TestIdentityAction();
		testIdentityAction.setName("TestIdentityAction");
		testIdentityAction.setFirst(this.makeInputPin(testIdentityAction.name
				+ ".first", 1, 1));
		testIdentityAction.setSecond(this.makeInputPin(testIdentityAction.name
				+ ".second", 1, 1));
		testIdentityAction.setResult(this.makeOutputPin(testIdentityAction.name
				+ ".result", 1, 1));
		this.addNode(identityTesterActivity, testIdentityAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("outputNode");
		outputNode.setParameter(this.addParameter(identityTesterActivity,
				"result", ParameterDirectionKind.out,
				this.environment.primitiveTypes.Boolean));
		this.addNode(identityTesterActivity, outputNode);

		this.addEdge(identityTesterActivity, new ObjectFlow(),
				valueAction1.result, testIdentityAction.first, null);
		this.addEdge(identityTesterActivity, new ObjectFlow(),
				valueAction2.result, testIdentityAction.second, null);
		this.addEdge(identityTesterActivity, new ObjectFlow(),
				testIdentityAction.result, outputNode, null);

		this.environment.addElement(identityTesterActivity);
	} // createIdentityTester

	public void createObjectCreater(String className) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[createObjectCreater] " + className
					+ " does not exist or is not a class.");
			return;
		}

		Class_ type = (Class_) element;

		Activity objectCreatorActivity = new Activity();
		objectCreatorActivity.setName(className + "ObjectCreator");

		CreateObjectAction createObjectAction = new CreateObjectAction();
		createObjectAction.setName("Create(" + className + ")");
		createObjectAction.setClassifier(type);
		createObjectAction.setResult(this.makeOutputPin(createObjectAction.name
				+ ".result", 1, 1));
		this.addNode(objectCreatorActivity, createObjectAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(objectCreatorActivity,
				"result", ParameterDirectionKind.out, type));
		this.addNode(objectCreatorActivity, outputNode);

		this.addEdge(objectCreatorActivity, new ObjectFlow(),
				createObjectAction.result, outputNode, null);

		this.environment.addElement(objectCreatorActivity);
	} // createObjectCreater

	public void createObjectDestroyer(String className, boolean isDestroyLinks,
			boolean isDestroyOwnedObjects) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[createObjectDestroyer] " + className
					+ " does not exist or is not a class.");
			return;
		}

		Class_ type = (Class_) element;

		Activity objectDestroyerActivity = new Activity();
		objectDestroyerActivity.setName(className + "ObjectDestroyer");

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("ValueAction");
		valueAction.setValue(this.environment.makeValue(type).specify());
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(objectDestroyerActivity, valueAction);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		this.addNode(objectDestroyerActivity, forkNode);

		DestroyObjectAction destroyObjectAction = new DestroyObjectAction();
		destroyObjectAction.setName("Destroy(" + className + ")");
		destroyObjectAction.setIsDestroyLinks(isDestroyLinks);
		destroyObjectAction.setIsDestroyOwnedObjects(isDestroyOwnedObjects);
		destroyObjectAction.setTarget(this.makeInputPin(
				destroyObjectAction.name + ".target", 1, 1));
		this.addNode(objectDestroyerActivity, destroyObjectAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(objectDestroyerActivity,
				"object", ParameterDirectionKind.out, type));
		this.addNode(objectDestroyerActivity, outputNode);

		this.addEdge(objectDestroyerActivity, new ObjectFlow(),
				valueAction.result, forkNode, null);
		this.addEdge(objectDestroyerActivity, new ObjectFlow(), forkNode,
				destroyObjectAction.target, null);
		this.addEdge(objectDestroyerActivity, new ObjectFlow(), forkNode,
				outputNode, null);

		this.environment.addElement(objectDestroyerActivity);
	} // createObjectDestroyer

	public void createWriterReader(String classifierName, String attributeName) {
		NamedElement element = this.environment.getElement(classifierName);

		if (element == null || !(element instanceof Classifier)) {
			Debug.println("[createWriterReader] " + classifierName
					+ " does not exist or is not a classifier.");
			return;
		}

		Classifier classifier = (Classifier) element;

		StructuralFeature attribute = null;

		// Debug.println("[createWriterReader] classifier.member.size = " +
		// classifier.member.size());

		for (int i = 0; i < classifier.member.size(); i++) {

			if (classifier.member.getValue(i).name.equals(attributeName)) {
				if (classifier.member.getValue(i) instanceof StructuralFeature) {
					attribute = (StructuralFeature) (classifier.member
							.getValue(i));
					break;
				} else {
					Debug.println("[createWriterReader] " + classifierName
							+ "::" + attributeName
							+ " is not a structural feature.");
					return;
				}
			}
		}

		if (attribute == null) {
			Debug.println("[createWriterReader] " + classifierName + "::"
					+ attributeName + " not found.");
			return;
		}

		// Debug.println("[createWriterReader] " + classifierName + "::" +
		// attributeName + " found.");

		Activity writerReaderActivity = new Activity();
		writerReaderActivity.setName(classifierName + "_" + attributeName
				+ "_WriterReader");

		// Debug.println("[createWriterReader] Creating value specification...");

		ValueSpecification valueSpecification = environment.makeValue(
				(Classifier) (attribute.typedElement.type)).specify();
		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName(attribute.typedElement.type.name + "Value");
		valueAction.setValue(valueSpecification);
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(writerReaderActivity, valueAction);

		// Debug.println("[createWriterReader] Creating object action...");

		CreateObjectAction createAction = new CreateObjectAction();
		createAction.setName("Create(" + classifierName + ")");
		createAction.setClassifier(classifier);
		createAction.setResult(this.makeOutputPin(
				createAction.name + ".result", 1, 1));
		this.addNode(writerReaderActivity, createAction);

		// ForkNode forkNode = new ForkNode();
		// forkNode.name = "ObjectFork";
		// this.addNode(writerReaderActivity, forkNode);

		// Debug.println("[createWriterReader] Creating add structural feature action...");

		AddStructuralFeatureValueAction writeAction = new AddStructuralFeatureValueAction();
		writeAction.setName("Write(" + attribute.name + ")");
		writeAction.setStructuralFeature(attribute);
		writeAction.setIsReplaceAll(true);
		writeAction.setObject(this.makeInputPin(writeAction.name + ".object",
				1, 1));
		writeAction.setValue(this.makeInputPin(writeAction.name + ".value", 1,
				-1));
		writeAction.setResult(this.makeOutputPin(writeAction.name + ".result",
				1, 1));
		this.addNode(writerReaderActivity, writeAction);

		// Debug.println("[createWriterReader] Creating read structural feature action...");

		ReadStructuralFeatureAction readAction = new ReadStructuralFeatureAction();
		readAction.setName("Read(" + attribute.name + ")");
		readAction.setStructuralFeature(attribute);
		readAction.setObject(this.makeInputPin(readAction.name + ".object", 1,
				1));
		readAction.setResult(this.makeOutputPin(readAction.name + ".result", 0,
				-1));
		this.addNode(writerReaderActivity, readAction);

		// Debug.println("[createWriterReader] Creating output node...");

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(writerReaderActivity,
				attribute.name, ParameterDirectionKind.out,
				attribute.typedElement.type));
		this.addNode(writerReaderActivity, outputNode);

		// Debug.println("[createWriterReader] Creating edges...");

		// this.addEdge(writerReaderActivity, new ObjectFlow(),
		// createAction.result, forkNode, null);
		// this.addEdge(writerReaderActivity, new ObjectFlow(), forkNode,
		// writeAction.object, null);
		this.addEdge(writerReaderActivity, new ObjectFlow(),
				createAction.result, writeAction.object, null);
		this.addEdge(writerReaderActivity, new ObjectFlow(),
				valueAction.result, writeAction.value, null);
		this.addEdge(writerReaderActivity, new ControlFlow(), writeAction,
				readAction, null);
		// this.addEdge(writerReaderActivity, new ObjectFlow(), forkNode,
		// readAction.object, null);
		this.addEdge(writerReaderActivity, new ObjectFlow(),
				writeAction.result, readAction.object, null);
		this.addEdge(writerReaderActivity, new ObjectFlow(), readAction.result,
				outputNode, null);

		this.environment.addElement(writerReaderActivity);

	} // createWriterReader

	public void createAttributeWriter(String classifierName,
			String attributeName, boolean isReplaceAll) {
		NamedElement element = this.environment.getElement(classifierName);

		if (element == null || !(element instanceof Classifier)) {
			Debug.println("[createAttributeWriter] " + classifierName
					+ " does not exist or is not a classifier.");
			return;
		}

		Classifier classifier = (Classifier) element;

		StructuralFeature attribute = null;

		for (int i = 0; i < classifier.member.size(); i++) {
			if (classifier.member.getValue(i).name.equals(attributeName)) {
				if (classifier.member.getValue(i) instanceof StructuralFeature) {
					attribute = (StructuralFeature) (classifier.member
							.getValue(i));
					break;
				} else {
					Debug.println("[createAttrbuteWriter] " + classifierName
							+ "::" + attributeName
							+ " is not a structural feature.");
					return;
				}
			}
		}

		if (attribute == null) {
			Debug.println("[createAttributeWriter] " + classifierName + "::"
					+ attributeName + " not found.");
			return;
		}

		Activity attributeWriterActivity = new Activity();

		if (isReplaceAll) {
			attributeWriterActivity.setName(classifierName + "_"
					+ attributeName + "_all_AttributeWriter");
		} else {
			attributeWriterActivity.setName(classifierName + "_"
					+ attributeName + "_AttributeWriter");
		}

		ActivityParameterNode objectNode = new ActivityParameterNode();
		objectNode.setName("Parameter(object)");
		objectNode.setParameter(this.addParameter(attributeWriterActivity,
				"object", ParameterDirectionKind.in, classifier));
		this.addNode(attributeWriterActivity, objectNode);

		ActivityParameterNode valueNode = new ActivityParameterNode();
		valueNode.setName("Parameter(value)");
		valueNode
				.setParameter(this.addParameter(attributeWriterActivity,
						"value", ParameterDirectionKind.in,
						attribute.typedElement.type));
		this.addNode(attributeWriterActivity, valueNode);

		ActivityParameterNode insertAtNode = null;
		if (!isReplaceAll) {
			insertAtNode = new ActivityParameterNode();
			insertAtNode.setName("Parameter(insertAt)");
			insertAtNode.setParameter(this.addParameter(
					attributeWriterActivity, "insertAt",
					ParameterDirectionKind.in,
					this.environment.primitiveTypes.UnlimitedNatural));
			this.addNode(attributeWriterActivity, insertAtNode);
		}

		AddStructuralFeatureValueAction writeAction = new AddStructuralFeatureValueAction();
		writeAction.setName("Write(" + attribute.name + ")");
		writeAction.setStructuralFeature(attribute);
		writeAction.setIsReplaceAll(isReplaceAll);
		writeAction.setObject(this.makeInputPin(writeAction.name + ".object",
				1, 1));
		writeAction.setValue(this.makeInputPin(writeAction.name + ".value", 1,
				-1));
		if (!isReplaceAll) {
			writeAction.setInsertAt(this.makeInputPin(
					writeAction + ".insertAt", 1, 1));
		}
		writeAction.setResult(this.addOutputPin(writeAction, writeAction.name
				+ ".result", 1, 1));
		this.addNode(attributeWriterActivity, writeAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("Parameter(output)");
		outputNode.setParameter(this.addParameter(attributeWriterActivity,
				"output", ParameterDirectionKind.out, classifier));
		this.addNode(attributeWriterActivity, outputNode);

		this.addEdge(attributeWriterActivity, new ObjectFlow(), objectNode,
				writeAction.object, null);
		this.addEdge(attributeWriterActivity, new ObjectFlow(), valueNode,
				writeAction.value, null);
		if (!isReplaceAll) {
			this.addEdge(attributeWriterActivity, new ObjectFlow(),
					insertAtNode, writeAction.insertAt, null);
		}
		this.addEdge(attributeWriterActivity, new ObjectFlow(),
				writeAction.result, outputNode, null);

		this.environment.addElement(attributeWriterActivity);

	} // createAttributeWriter

	public void createAttributedCaller(String calledBehaviorName) {
		Element element = environment.getElement(calledBehaviorName);

		if (element == null) {
			Debug.println("[createAttributedCaller] " + calledBehaviorName
					+ " not found.");
			return;
		}

		if (!(element instanceof Behavior)) {
			Debug.println("[createAttributedCaller] " + calledBehaviorName
					+ " is not an behavior.");
			return;
		}

		Behavior calledBehavior = (Behavior) element;
		ParameterList parameters = calledBehavior.ownedParameter;

		Activity callerActivity = new Activity();
		callerActivity.setName("Attributed" + calledBehaviorName + "Caller");

		ReadSelfAction readSelfAction = new ReadSelfAction();
		readSelfAction.setName("Read(self)");
		readSelfAction.setResult(this.makeOutputPin(readSelfAction.name
				+ ".result", 1, 1));
		this.addNode(callerActivity, readSelfAction);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkSelf");
		this.addNode(callerActivity, forkNode);

		this.addEdge(callerActivity, new ObjectFlow(), readSelfAction.result,
				forkNode, null);

		CallBehaviorAction callAction = new CallBehaviorAction();
		callAction.setName("Call(" + calledBehaviorName + ")");
		callAction.setBehavior(calledBehavior);
		this.addNode(callerActivity, callAction);

		for (int i = 0; i < parameters.size(); i++) {
			Parameter parameter = parameters.getValue(i);

			if (parameter.direction.equals(ParameterDirectionKind.in)
					|| parameter.direction.equals(ParameterDirectionKind.inout)) {

				ReadStructuralFeatureAction readAction = new ReadStructuralFeatureAction();
				readAction.setName("Read(" + parameter.name + ")");
				readAction.setStructuralFeature(this.addProperty(
						callerActivity, parameter.name, parameter.type, 0, -1));
				readAction.setObject(this.makeInputPin(readAction.name
						+ ".object", 1, 1));
				readAction.setResult(this.makeOutputPin(readAction.name
						+ ".result", 0, -1));
				this.addNode(callerActivity, readAction);

				this.addEdge(callerActivity, new ObjectFlow(), forkNode,
						readAction.object, null);

				InputPin inputPin = this.makeInputPin(callAction.name
						+ ".argument[" + parameter.name + "]", 1, 1);
				callAction.addArgument(inputPin);

				this.addEdge(callerActivity, new ObjectFlow(),
						readAction.result, inputPin, null);
			} else {
				ActivityParameterNode parameterNode = new ActivityParameterNode();
				parameterNode.setName("Parameter(" + parameter.name + ")");
				parameterNode.setParameter(this.addParameter(callerActivity,
						parameter.name, ParameterDirectionKind.out,
						parameter.type));
				this.addNode(callerActivity, parameterNode);

				OutputPin outputPin = this.makeOutputPin(callAction.name
						+ ".result[" + parameter.name + "]", 1, 1);
				callAction.addResult(outputPin);

				this.addEdge(callerActivity, new ObjectFlow(), outputPin,
						parameterNode, null);
			}
		}

		this.environment.addElement(callerActivity);
	} // createAttributedCaller

	public void createHelloWorld1() {
		Class_ standardOutputChannelClass = this.environment.standardIO.StandardOutputChannel;

		Activity helloWorldActivity = new Activity();
		helloWorldActivity.setName("HelloWorld1");

		ReadExtentAction readExtentAction = new ReadExtentAction();
		readExtentAction.setName("ReadExtent(StandardOutputChannel)");
		readExtentAction.setClassifier(standardOutputChannelClass);
		readExtentAction.setResult(this.makeOutputPin(readExtentAction.name
				+ ".result", 0, -1));
		this.addNode(helloWorldActivity, readExtentAction);

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("ValueAction(\"Hello World\")");
		valueAction.setValue(this.createLiteralString("", "Hello World!"));
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(helloWorldActivity, valueAction);

		CallOperationAction callAction = new CallOperationAction();
		callAction.setName("Call(StandardOutputChannel::writeLine)");
		callAction.setTarget(this.makeInputPin(callAction.name + ".target", 1,
				1));
		callAction.addArgument(this.makeInputPin(callAction.name + ".argument",
				1, 1));
		callAction.setOperation(this.getOperation(standardOutputChannelClass,
				"writeLine"));

		if (callAction.operation == null) {
			Debug
					.println("[createHelloWorld1] Operation StandardOutputChannelClass::writeLine not found.");
			return;
		}

		this.addNode(helloWorldActivity, callAction);

		this.addEdge(helloWorldActivity, new ObjectFlow(),
				readExtentAction.result, callAction.target, null);
		this.addEdge(helloWorldActivity, new ObjectFlow(), valueAction.result,
				callAction.argument.getValue(0), null);

		this.environment.addElement(helloWorldActivity);

	} // createHelloWorld1

	public void createHelloWorld2() {
		Activity helloWorldActivity = new Activity();
		helloWorldActivity.setName("HelloWorld2");

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("ValueAction(\"Hello World\")");
		valueAction.setValue(this.createLiteralString("", "Hello World!"));
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(helloWorldActivity, valueAction);

		CallBehaviorAction callAction = new CallBehaviorAction();
		callAction.setName("Call(WriteLine)");
		callAction.setBehavior(this.getActivity("WriteLine"));
		callAction.addArgument(this.makeInputPin(callAction.name + ".argument",
				1, 1));
		this.addNode(helloWorldActivity, callAction);

		if (callAction.behavior == null) {
			return;
		}

		this.addEdge(helloWorldActivity, new ObjectFlow(), valueAction.result,
				callAction.argument.getValue(0), null);

		this.environment.addElement(helloWorldActivity);
	} // createHelloWorld2

	public void createChannelReader(String channelName) {
		Class_ inputChannelClass = this.environment.standardIO.InputChannel;
		Operation getNameOperation = this.getOperation(inputChannelClass,
				"getName");

		if (getNameOperation == null) {
			Debug
					.println("[createChannelReader] Operation InputChannel::getName not found.");
			return;
		}

		StringValue channelNameValue = new StringValue();
		channelNameValue.value = channelName;

		Activity channelReaderActivity = new Activity();
		channelReaderActivity.setName(channelName + "ChannelReader");

		Parameter output = this.addParameter(channelReaderActivity, "value",
				ParameterDirectionKind.out, null);

		CallBehaviorAction channelGetterAction = new CallBehaviorAction();
		channelGetterAction.setName("Call(InputChannelInstanceGetter)");
		channelGetterAction.setBehavior(this.createInstanceGetter(
				inputChannelClass, getNameOperation, channelNameValue));
		channelGetterAction.addResult(this.makeOutputPin(
				channelGetterAction.name + ".result", 1, 1));
		this.addNode(channelReaderActivity, channelGetterAction);

		CallOperationAction callOperationAction = new CallOperationAction();
		callOperationAction.setName("Call(read)");
		callOperationAction.setOperation(this.getOperation(inputChannelClass,
				"read"));
		callOperationAction.setTarget(this.makeInputPin(
				callOperationAction.name + ".target", 1, 1));
		callOperationAction.addResult(this.makeOutputPin(
				callOperationAction.name + ".result[value]", 0, 1));
		this.addNode(channelReaderActivity, callOperationAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode(" + output.name + ")");
		outputNode.setParameter(output);
		outputNode.setType(output.type);
		this.addNode(channelReaderActivity, outputNode);

		this.addEdge(channelReaderActivity, new ObjectFlow(),
				channelGetterAction.result.getValue(0),
				callOperationAction.target, null);
		this.addEdge(channelReaderActivity, new ObjectFlow(),
				callOperationAction.result.getValue(0), outputNode, null);

		this.environment.addElement(channelReaderActivity);
	} // createChannelReader

	public void createChannelWriter(String channelName) {
		Class_ outputChannelClass = this.environment.standardIO.OutputChannel;
		Operation getNameOperation = this.getOperation(outputChannelClass,
				"getName");

		if (getNameOperation == null) {
			Debug
					.println("[createChannelWriter] Operation OutputChannel::getName not found.");
			return;
		}

		StringValue channelNameValue = new StringValue();
		channelNameValue.value = channelName;

		Activity channelWriterActivity = new Activity();
		channelWriterActivity.setName(channelName + "ChannelWriter");

		Parameter input = this.addParameter(channelWriterActivity, "value",
				ParameterDirectionKind.in,
				this.environment.primitiveTypes.String);

		ActivityParameterNode inputNode = new ActivityParameterNode();
		inputNode.setName("InputNode(" + input.name + ")");
		inputNode.setParameter(input);
		inputNode.setType(input.type);
		this.addNode(channelWriterActivity, inputNode);

		CallBehaviorAction channelGetterAction = new CallBehaviorAction();
		channelGetterAction.setName("Call(OutputChannelInstanceGetter)");
		channelGetterAction.setBehavior(this.createInstanceGetter(
				outputChannelClass, getNameOperation, channelNameValue));
		channelGetterAction.addResult(this.makeOutputPin(
				channelGetterAction.name + ".result", 1, 1));
		this.addNode(channelWriterActivity, channelGetterAction);

		CallOperationAction callOperationAction = new CallOperationAction();
		callOperationAction.setName("Call(write)");
		callOperationAction.setOperation(this.getOperation(outputChannelClass,
				"write"));
		callOperationAction.setTarget(this.makeInputPin(
				callOperationAction.name + ".target", 1, 1));
		callOperationAction.addArgument(this.makeInputPin(
				callOperationAction.name + ".argument[value]", 0, 1));
		this.addNode(channelWriterActivity, callOperationAction);

		this.addEdge(channelWriterActivity, new ObjectFlow(), inputNode,
				callOperationAction.argument.getValue(0), null);
		this.addEdge(channelWriterActivity, new ObjectFlow(),
				channelGetterAction.result.getValue(0),
				callOperationAction.target, null);

		this.environment.addElement(channelWriterActivity);
		this.createAttributedCaller(channelWriterActivity.name);
	} // createChannelWriter

	public void createWriteLine() {
		Class_ standardOutputChannelClass = this.environment.standardIO.StandardOutputChannel;

		Activity writeLineActivity = new Activity();
		writeLineActivity.setName("WriteLine");

		ReadExtentAction readExtentAction = new ReadExtentAction();
		readExtentAction.setName("ReadExtent(StandardOutputChannel)");
		readExtentAction.setClassifier(standardOutputChannelClass);
		readExtentAction.setResult(this.makeOutputPin(readExtentAction.name
				+ ".result", 0, -1));
		this.addNode(writeLineActivity, readExtentAction);

		ActivityParameterNode inputNode = new ActivityParameterNode();
		inputNode.setName("input(value)");
		inputNode.setParameter(this.addParameter(writeLineActivity, "value",
				ParameterDirectionKind.in, null));
		this.addNode(writeLineActivity, inputNode);

		CallOperationAction callAction = new CallOperationAction();
		callAction.setName("Call(StandardOutputChannel::writeLine)");
		callAction.setTarget(this.makeInputPin(callAction.name + ".target", 1,
				1));
		callAction.addArgument(this.makeInputPin(callAction.name + ".argument",
				1, 1));
		callAction.setOperation(this.getOperation(standardOutputChannelClass,
				"writeLine"));

		if (callAction.operation == null) {
			Debug
					.println("[createWriteLine] Operation StandardOutputChannelClass::writeLine not found.");
			return;
		}

		this.addNode(writeLineActivity, callAction);

		this.addEdge(writeLineActivity, new ObjectFlow(),
				readExtentAction.result, callAction.target, null);
		this.addEdge(writeLineActivity, new ObjectFlow(), inputNode,
				callAction.argument.getValue(0), null);

		this.environment.addElement(writeLineActivity);

	} // createWriteLine

	public void createOperationCaller(String className, String operationName) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[createOperationCaller] " + className
					+ " does not exist or is not a class.");
			return;
		}

		Class_ type = (Class_) element;
		Operation operation = this.getOperation(type, operationName);

		if (operation == null) {
			Debug.println("[createOperationCaller] " + operationName
					+ " is not an operation of " + className + ".");
			return;
		}

		Activity operationCallerActivity = new Activity();
		operationCallerActivity.setName(className + operationName + "Caller");

		CreateObjectAction createObjectAction = new CreateObjectAction();
		createObjectAction.setName("Create(" + className + ")");
		createObjectAction.setClassifier(type);
		createObjectAction.setResult(this.makeOutputPin(createObjectAction.name
				+ ".result", 1, 1));
		this.addNode(operationCallerActivity, createObjectAction);

		CallOperationAction callOperationAction = new CallOperationAction();
		callOperationAction.setName("Call(" + operationName + ")");
		callOperationAction.setOperation(operation);
		callOperationAction.setTarget(this.makeInputPin(
				callOperationAction.name + ".target", 1, 1));
		this.addNode(operationCallerActivity, callOperationAction);

		this.addEdge(operationCallerActivity, new ObjectFlow(),
				createObjectAction.result, callOperationAction.target, null);

		this.environment.addElement(operationCallerActivity);
	} // createOperationCaller

	public void createPolymorphicOperationCaller(String subclassName,
			String superclassName, String operationName) {
		NamedElement element = this.environment.getElement(subclassName);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[createPolymorphicOperationCaller] " + subclassName
					+ " does not exist or is not a class.");
			return;
		}

		Class_ subclass = (Class_) element;

		element = this.environment.getElement(superclassName);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[createPolymorphicOperationCaller] "
					+ superclassName + " does not exist or is not a class.");
			return;
		}

		Class_ superclass = (Class_) element;
		Operation operation = this.getOperation(superclass, operationName);

		if (operation == null) {
			Debug.println("[createPolymorphicOperationCaller] " + operationName
					+ " is not an operation of " + superclassName + ".");
			return;
		}

		Activity operationCallerActivity = new Activity();
		operationCallerActivity.setName(subclassName + superclassName
				+ operationName + "Caller");

		CreateObjectAction createObjectAction = new CreateObjectAction();
		createObjectAction.setName("Create(" + subclassName + ")");
		createObjectAction.setClassifier(subclass);
		createObjectAction.setResult(this.makeOutputPin(createObjectAction.name
				+ ".result", 1, 1));
		this.addNode(operationCallerActivity, createObjectAction);

		CallOperationAction callOperationAction = new CallOperationAction();
		callOperationAction.setName("Call(" + operationName + ")");
		callOperationAction.setOperation(operation);
		callOperationAction.setTarget(this.makeInputPin(
				callOperationAction.name + ".target", 1, 1));
		this.addNode(operationCallerActivity, callOperationAction);

		this.addEdge(operationCallerActivity, new ObjectFlow(),
				createObjectAction.result, callOperationAction.target, null);

		this.environment.addElement(operationCallerActivity);
	} // createPolymorphicOperationCaller

	public void createSelfCaller(String className, String operationName) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[createSelfCaller] " + className
					+ " does not exist or is not a class.");
			return;
		}

		Class_ type = (Class_) element;
		Operation operation = this.getOperation(type, operationName);

		if (operation == null) {
			Debug.println("[createSelfCaller] " + operationName
					+ " is not an operation of " + className + ".");
			return;
		}

		Activity selfCallerActivity = new Activity();
		selfCallerActivity.setName(className + operationName + "SelfCaller");

		ReadSelfAction readSelfAction = new ReadSelfAction();
		readSelfAction.setName("ReadSelf");
		readSelfAction.setResult(this.makeOutputPin(readSelfAction.name
				+ ".result", 1, 1));
		this.addNode(selfCallerActivity, readSelfAction);

		CallOperationAction callOperationAction = new CallOperationAction();
		callOperationAction.setName("Call(" + operationName + ")");
		callOperationAction.setOperation(operation);
		callOperationAction.setTarget(this.makeInputPin(
				callOperationAction.name + ".target", 1, 1));
		this.addNode(selfCallerActivity, callOperationAction);

		this.addEdge(selfCallerActivity, new ObjectFlow(),
				readSelfAction.result, callOperationAction.target, null);

		this.environment.addElement(selfCallerActivity);
	} // createSelfCaller

	public void createMethodCaller(String className, String operationName) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[createMethodCaller] " + className
					+ " does not exist or is not a class.");
			return;
		}

		Class_ type = (Class_) element;
		Operation operation = this.getOperation(type, operationName);

		if (operation == null) {
			Debug.println("[createMethodCaller] " + operationName
					+ " is not an operation of " + className + ".");
			return;
		}

		if (operation.method.size() == 0) {
			Debug.println("[createMethodCaller] " + operationName
					+ " has no method.");
			return;
		}

		Behavior method = (Behavior) (operation.method.get(0));

		Activity superCallerActivity = new Activity();
		superCallerActivity.setName(className + operationName + "MethodCaller");

		CallBehaviorAction callBehaviorAction = new CallBehaviorAction();
		callBehaviorAction.setName("Call(" + method.name + ")");
		callBehaviorAction.setBehavior(method);
		this.addNode(superCallerActivity, callBehaviorAction);

		this.environment.addElement(superCallerActivity);
	} // createMethodCaller

	public void createAccepter(String signalName) {
		NamedElement element = this.environment.getElement(signalName);

		if (element == null || !(element instanceof Signal)) {
			Debug.println("[createAccepter] " + signalName
					+ " does not exist or is not a signal.");
			return;
		}

		Signal signal = (Signal) element;

		Activity accepterActivity = new Activity();
		accepterActivity.setName(signal.name + "Accepter");
		accepterActivity.setIsActive(true);

		Reception reception = new Reception();
		reception.setSignal(signal);
		accepterActivity.addOwnedReception(reception);

		SignalEvent signalEvent = new SignalEvent();
		signalEvent.setSignal(signal);

		Trigger trigger = new Trigger();
		trigger.setEvent(signalEvent);

		AcceptEventAction acceptEventAction = new AcceptEventAction();
		acceptEventAction.setName("Accept(" + signal.name + ")");
		acceptEventAction.addTrigger(trigger);
		acceptEventAction.setIsUnmarshall(false);
		acceptEventAction.addResult(this.makeOutputPin(acceptEventAction.name
				+ ".result", 1, 1));
		this.addNode(accepterActivity, acceptEventAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("Output(signal)");
		outputNode.setParameter(this.addParameter(accepterActivity, "signal",
				ParameterDirectionKind.out, signal));
		this.addNode(accepterActivity, outputNode);

		this.addEdge(accepterActivity, new ObjectFlow(),
				acceptEventAction.result.getValue(0), outputNode, null);

		this.environment.addElement(accepterActivity);
	} // createAccepter

	public void createSender(String signalName) {
		NamedElement element = this.environment.getElement(signalName);

		if (element == null || !(element instanceof Signal)) {
			Debug.println("[createSignaler] " + signalName
					+ " does not exist or is not a signal.");
			return;
		}

		Signal signal = (Signal) element;

		this.createAccepter(signalName);
		element = this.environment.getElement(signalName + "Accepter");

		if (!(element instanceof Activity)) {
			Debug.println("[createSignaler] " + signalName
					+ "Accepter is not an activity.");
			return;
		}

		Activity accepterActivity = (Activity) element;

		Activity senderActivity = new Activity();
		senderActivity.setName(signalName + "Sender");

		CreateObjectAction createObjectAction = new CreateObjectAction();
		createObjectAction.setName("Create(" + signalName + "Accepter)");
		createObjectAction.setClassifier(accepterActivity);
		createObjectAction.setResult(this.makeOutputPin(createObjectAction.name
				+ ".result", 1, 1));
		this.addNode(senderActivity, createObjectAction);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("Fork(accepterActivity)");
		this.addNode(senderActivity, forkNode);

		StartObjectBehaviorAction startAction = new StartObjectBehaviorAction();
		startAction.setName("Start");
		startAction.setObject(this.makeInputPin(startAction.name + ".object",
				1, 1));
		this.addNode(senderActivity, startAction);

		SendSignalAction sendAction = new SendSignalAction();
		sendAction.setName("Send(" + signalName + ")");
		sendAction.setSignal(signal);
		sendAction.setTarget(this.makeInputPin(sendAction.name + ".target", 1,
				1));
		this.addNode(senderActivity, sendAction);

		DestroyObjectAction destroyObjectAction = new DestroyObjectAction();
		destroyObjectAction.setName("Destroy(" + signalName + "Accepter)");
		destroyObjectAction.setTarget(this.makeInputPin(
				destroyObjectAction.name + ".target", 1, 1));
		this.addNode(senderActivity, destroyObjectAction);

		this.addEdge(senderActivity, new ObjectFlow(),
				createObjectAction.result, forkNode, null);
		this.addEdge(senderActivity, new ObjectFlow(), forkNode,
				startAction.object, null);
		this.addEdge(senderActivity, new ObjectFlow(), forkNode,
				sendAction.target, null);
		this.addEdge(senderActivity, new ObjectFlow(), forkNode,
				destroyObjectAction.target, null);

		this.addEdge(senderActivity, new ControlFlow(), startAction,
				sendAction, null);
		this.addEdge(senderActivity, new ControlFlow(), sendAction,
				destroyObjectAction, null);

		this.environment.addElement(senderActivity);
	} // createSender

	public void createStructuredNodeTester(String activityName) {
		// Note that the named activity is actually modified in place.
		Activity activity = this.getActivity(activityName);

		if (activity != null) {

			StructuredActivityNode structuredNode = new StructuredActivityNode();
			structuredNode.setName("StructuredNode");

			int i = 0;
			while (i < activity.node.size()) {
				ActivityNode node = activity.node.getValue(i);

				if (node instanceof ActivityParameterNode) {
					i++;
				} else {
					activity.node.removeValue(i);
					node.activity = null;
					structuredNode.addNode(node);
				}
			}

			i = 0;
			while (i < activity.edge.size()) {
				ActivityEdge edge = activity.edge.getValue(i);

				if (edge.source instanceof ActivityParameterNode
						&& !(edge.target instanceof ActivityParameterNode)) {
					Parameter parameter = ((ActivityParameterNode) edge.source).parameter;

					InputPin inputPin = makeInputPin(structuredNode.name + "."
							+ parameter.name,
							parameter.multiplicityElement.lower,
							parameter.multiplicityElement.upper.naturalValue);
					structuredNode.addStructuredNodeInput(inputPin);

					ActivityNode target = edge.target;
					for (int j = 0; j < target.incoming.size(); j++) {
						if (target.incoming.getValue(j) == edge) {
							target.incoming.removeValue(j);
							break;
						}
					}

					ObjectFlow objectFlow = new ObjectFlow();
					objectFlow.setSource(inputPin);
					objectFlow.setTarget(target);
					objectFlow.setGuard(null);
					structuredNode.addEdge(objectFlow);

					edge.setTarget(inputPin);
					i++;

				} else if (edge.target instanceof ActivityParameterNode
						&& !(edge.source instanceof ActivityParameterNode)) {
					Parameter parameter = ((ActivityParameterNode) edge.target).parameter;

					OutputPin outputPin = makeOutputPin(structuredNode.name
							+ "." + parameter.name,
							parameter.multiplicityElement.lower,
							parameter.multiplicityElement.upper.naturalValue);
					structuredNode.addStructuredNodeOutput(outputPin);

					ActivityNode source = edge.source;
					for (int j = 0; j < source.outgoing.size(); j++) {
						if (source.outgoing.getValue(j) == edge) {
							source.outgoing.removeValue(j);
							break;
						}
					}

					ObjectFlow objectFlow = new ObjectFlow();
					objectFlow.setSource(source);
					objectFlow.setTarget(outputPin);
					objectFlow.setGuard(null);
					structuredNode.addEdge(objectFlow);

					edge.setSource(outputPin);
					i++;

				} else {
					activity.edge.remove(i);
					edge.activity = null;
					structuredNode.addEdge(edge);
				}
			}

			activity.setName("Structured" + activity.name);
			activity.addNode(structuredNode);
		}
	} // createStructuredNodeTester

	public void createForkMergeInput() {
		Activity forkMergeActivity = new Activity();
		forkMergeActivity.name = "ForkMergeInput";

		ActivityParameterNode inputNode = new ActivityParameterNode();
		inputNode.setName("InputNode");
		inputNode.setParameter(this.addParameter(forkMergeActivity, "input",
				ParameterDirectionKind.in,
				this.environment.primitiveTypes.Integer));
		this.addNode(forkMergeActivity, inputNode);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		this.addNode(forkMergeActivity, forkNode);

		MergeNode mergeNode = new MergeNode();
		mergeNode.setName("MergeNode");
		this.addNode(forkMergeActivity, mergeNode);

		CallBehaviorAction action = new CallBehaviorAction();
		action.setName("Action");
		action.setBehavior(this.getCopier());
		action.addArgument(this.makeInputPin(action.name + ".argument", 1, 1));
		action.addResult(this.makeOutputPin(action.name + ".result", 1, 1));
		this.addNode(forkMergeActivity, action);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(forkMergeActivity, "output",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		this.setMultiplicity(outputNode.parameter.multiplicityElement, 1, -1);
		this.addNode(forkMergeActivity, outputNode);

		this.addEdge(forkMergeActivity, new ObjectFlow(), inputNode, forkNode,
				null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), forkNode, mergeNode,
				null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), forkNode, mergeNode,
				null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), mergeNode,
				action.argument.getValue(0), null);
		this.addEdge(forkMergeActivity, new ObjectFlow(), action.result
				.getValue(0), outputNode, null);

		this.environment.addElement(forkMergeActivity);
	} // createForkMergeInput

	public void createConditionalNodeTester(int value) {
		Activity copierActivity = this.getCopier();

		if (copierActivity == null) {
			return;
		}

		Activity conditionalNodeActivity = new Activity();
		conditionalNodeActivity.setName("ConditionalNodeTester_" + value);

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("Value(" + value + ")");
		valueAction.setValue(this.createLiteralInteger("", value));
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		conditionalNodeActivity.addNode(valueAction);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		conditionalNodeActivity.addNode(forkNode);

		this.addEdge(conditionalNodeActivity, new ObjectFlow(),
				valueAction.result, forkNode, null);

		ConditionalNode conditionalNode = new ConditionalNode();
		conditionalNode.setName("ConditionalNode");
		conditionalNode.addResult(this.makeOutputPin(conditionalNode.name
				+ ".result", 1, 1));
		conditionalNodeActivity.addNode(conditionalNode);

		Clause clause;
		TestIdentityAction testIdentityAction;
		ObjectFlow flow;
		ForkNode otherForkNode;
		CallBehaviorAction callAction;

		for (int i = 0; i <= 2; i++) {
			clause = new Clause();

			callAction = new CallBehaviorAction();
			callAction.setName("CallAction_" + i);
			callAction.setBehavior(copierActivity);
			callAction.addArgument(this.makeInputPin(callAction.name
					+ ".argument", 1, 1));
			callAction.addResult(this.makeOutputPin(
					callAction.name + ".result", 1, 1));
			conditionalNode.addNode(callAction);
			clause.addBody(callAction);
			clause.addBodyOutput(callAction.result.getValue(0));

			valueAction = new ValueSpecificationAction();

			if (i < 2) {
				otherForkNode = new ForkNode();
				otherForkNode.setName("ForkNode_" + i);
				conditionalNode.addNode(otherForkNode);

				this.addEdge(conditionalNodeActivity, new ObjectFlow(),
						forkNode, otherForkNode, null);

				valueAction.setName("TestValue(" + i + ")");
				valueAction.setValue(this.createLiteralInteger("", i));
				valueAction.setResult(this.makeOutputPin(valueAction.name
						+ ".result", 1, 1));
				conditionalNode.addNode(valueAction);
				clause.addTest(valueAction);

				testIdentityAction = new TestIdentityAction();
				testIdentityAction.setName("Test(value=" + i + ")");
				testIdentityAction.setFirst(this.makeInputPin(
						testIdentityAction.name + ".first", 1, 1));
				testIdentityAction.setSecond(this.makeInputPin(
						testIdentityAction.name + ".second", 1, 1));
				testIdentityAction.setResult(this.makeOutputPin(
						testIdentityAction.name + ".result", 1, 1));
				conditionalNode.addNode(testIdentityAction);
				clause.addTest(testIdentityAction);
				clause.setDecider(testIdentityAction.result);

				flow = new ObjectFlow();
				flow.setSource(otherForkNode);
				flow.setTarget(testIdentityAction.first);
				conditionalNode.addEdge(flow);

				flow = new ObjectFlow();
				flow.setSource(valueAction.result);
				flow.setTarget(testIdentityAction.second);
				conditionalNode.addEdge(flow);

				flow = new ObjectFlow();
				flow.setSource(otherForkNode);
				flow.setTarget(callAction.argument.getValue(0));
				conditionalNode.addEdge(flow);

			} else {
				for (int j = 0; j < conditionalNode.clause.size(); j++) {
					clause.addPredecessorClause(conditionalNode.clause
							.getValue(j));
				}

				valueAction.setName("Value(true)");
				valueAction.setValue(this.createLiteralBoolean("", true));
				valueAction.setResult(this.makeOutputPin(valueAction.name
						+ ".result", 1, 1));
				conditionalNode.addNode(valueAction);
				clause.addTest(valueAction);
				clause.setDecider(valueAction.result);

				this.addEdge(conditionalNodeActivity, new ObjectFlow(),
						forkNode, callAction.argument.getValue(0), null);
			}

			conditionalNode.addClause(clause);
		}

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("OutputNode");
		outputNode.setParameter(this.addParameter(conditionalNodeActivity,
				"output", ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		conditionalNodeActivity.addNode(outputNode);

		this.addEdge(conditionalNodeActivity, new ObjectFlow(),
				conditionalNode.result.getValue(0), outputNode, null);

		this.environment.addElement(conditionalNodeActivity);
	} // createConditionalNodeTester

	public void createLoopNodeTester(int value) {
		Activity loopNodeActivity = new Activity();
		loopNodeActivity.setName("LoopNodeTester_" + value);

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("InitValue(" + value + ")");
		valueAction.setValue(this.createLiteralInteger("", value));
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		loopNodeActivity.addNode(valueAction);

		ValueSpecificationAction valueAction1 = new ValueSpecificationAction();
		valueAction1.setName("InitValue(1)");
		valueAction1.setValue(this.createLiteralInteger("", 1));
		valueAction1.setResult(this.makeOutputPin(
				valueAction1.name + ".result", 1, 1));
		loopNodeActivity.addNode(valueAction1);

		LoopNode loopNode = new LoopNode();
		loopNode.setName("LoopNode");
		loopNode.addLoopVariableInput(this.makeInputPin(loopNode.name
				+ ".loopVariableInput(i)", 1, 1));
		loopNode.addLoopVariableInput(this.makeInputPin(loopNode.name
				+ ".loopVariableInput(n)", 1, 1));
		loopNode.addLoopVariable(this.makeOutputPin(loopNode.name
				+ ".loopVariable(i)", 1, 1));
		loopNode.addLoopVariable(this.makeOutputPin(loopNode.name
				+ ".loopVariable(n)", 1, 1));
		loopNode.addResult(this.makeOutputPin(loopNode.name + ".result(i)", 1,
				1));
		loopNode.addResult(this.makeOutputPin(loopNode.name + ".result(n)", 1,
				1));
		loopNode.setIsTestedFirst(true);
		loopNodeActivity.addNode(loopNode);

		ActivityParameterNode outputNode0 = new ActivityParameterNode();
		outputNode0.setName("Output(i)");
		outputNode0.setParameter(this.addParameter(loopNodeActivity, "i",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		loopNodeActivity.addNode(outputNode0);

		ActivityParameterNode outputNode1 = new ActivityParameterNode();
		outputNode1.setName("Output(n)");
		outputNode1.setParameter(this.addParameter(loopNodeActivity, "n",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		loopNodeActivity.addNode(outputNode1);

		this.addEdge(loopNodeActivity, new ObjectFlow(), valueAction.result,
				loopNode.loopVariableInput.getValue(0), null);
		this.addEdge(loopNodeActivity, new ObjectFlow(), valueAction1.result,
				loopNode.loopVariableInput.getValue(1), null);
		this.addEdge(loopNodeActivity, new ObjectFlow(), loopNode.result
				.getValue(0), outputNode0, null);
		this.addEdge(loopNodeActivity, new ObjectFlow(), loopNode.result
				.getValue(1), outputNode1, null);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		loopNode.addNode(forkNode);

		ValueSpecificationAction valueAction0 = new ValueSpecificationAction();
		valueAction0.setName("Value(0)");
		valueAction0.setValue(this.createLiteralInteger("", 0));
		valueAction0.setResult(this.makeOutputPin(
				valueAction0.name + ".result", 1, 1));
		loopNode.addNode(valueAction0);
		loopNode.addTest(valueAction0);

		CallBehaviorAction greaterCall = new CallBehaviorAction();
		greaterCall.setName("Call(Greater)");
		greaterCall
				.setBehavior(this.environment.integerFunctions.integerGreater);
		greaterCall.addArgument(this.makeInputPin(greaterCall.name
				+ ".argument(first)", 1, 1));
		greaterCall.addArgument(this.makeInputPin(greaterCall.name
				+ ".argument(second)", 1, 1));
		greaterCall.addResult(this.makeOutputPin(greaterCall.name + ".result",
				1, 1));
		loopNode.addNode(greaterCall);
		loopNode.addTest(greaterCall);
		loopNode.setDecider(greaterCall.result.getValue(0));

		ObjectFlow flow = new ObjectFlow();
		flow.setSource(loopNode.loopVariable.getValue(0));
		flow.setTarget(forkNode);
		loopNode.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(forkNode);
		flow.setTarget(greaterCall.argument.getValue(0));
		loopNode.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(valueAction0.result);
		flow.setTarget(greaterCall.argument.getValue(1));
		loopNode.addEdge(flow);

		valueAction1 = new ValueSpecificationAction();
		valueAction1.setName("Value(1)");
		valueAction1.setValue(this.createLiteralInteger("", 1));
		valueAction1.setResult(this.makeOutputPin(
				valueAction1.name + ".result", 1, 1));
		loopNode.addNode(valueAction1);
		loopNode.addBodyPart(valueAction1);

		CallBehaviorAction minusCall = new CallBehaviorAction();
		minusCall.setName("Call(Minus)");
		minusCall.setBehavior(this.environment.integerFunctions.integerMinus);
		minusCall.addArgument(this.makeInputPin(minusCall.name
				+ ".argument(first)", 1, 1));
		minusCall.addArgument(this.makeInputPin(minusCall.name
				+ ".argument(second)", 1, 1));
		minusCall.addResult(this
				.makeOutputPin(minusCall.name + ".result", 1, 1));
		loopNode.addNode(minusCall);
		loopNode.addBodyPart(minusCall);
		loopNode.addBodyOutput(minusCall.result.getValue(0));

		CallBehaviorAction timesCall = new CallBehaviorAction();
		timesCall.setName("Call(Times)");
		timesCall.setBehavior(this.environment.integerFunctions.integerTimes);
		timesCall.addArgument(this.makeInputPin(timesCall.name
				+ ".argument(first)", 1, 1));
		timesCall.addArgument(this.makeInputPin(timesCall.name
				+ ".argument(second)", 1, 1));
		timesCall.addResult(this
				.makeOutputPin(timesCall.name + ".result", 1, 1));
		loopNode.addNode(timesCall);
		loopNode.addBodyPart(timesCall);
		loopNode.addBodyOutput(timesCall.result.getValue(0));

		flow = new ObjectFlow();
		flow.setSource(forkNode);
		flow.setTarget(minusCall.argument.getValue(0));
		loopNode.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(valueAction1.result);
		flow.setTarget(minusCall.argument.getValue(1));
		loopNode.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(loopNode.loopVariable.getValue(1));
		flow.setTarget(timesCall.argument.getValue(0));
		loopNode.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(forkNode);
		flow.setTarget(timesCall.argument.getValue(1));
		loopNode.addEdge(flow);

		this.environment.addElement(loopNodeActivity);
	} // createLoopNodeTester

	public void createExpansionRegionTester(int n) {
		Activity expansionRegionActivity = new Activity();
		expansionRegionActivity.setName("ExpansionRegionTester_" + n);

		ValueSpecificationAction[] valueAction = new ValueSpecificationAction[n];
		ActivityNode node = new InitialNode();
		node.setName("InitialNode");
		expansionRegionActivity.addNode(node);

		for (int i = 0; i < n; i++) {
			valueAction[i] = new ValueSpecificationAction();
			valueAction[i].setName("Value(" + (i + 1) + ")");
			valueAction[i].setValue(this.createLiteralInteger("", i + 1));
			valueAction[i].setResult(this.makeOutputPin(valueAction[i].name
					+ ".result", 1, 1));
			expansionRegionActivity.addNode(valueAction[i]);

			this.addEdge(expansionRegionActivity, new ControlFlow(), node,
					valueAction[i], null);
			node = valueAction[i];
		}

		ExpansionRegion expansionRegion = new ExpansionRegion();
		expansionRegion.setName("ExpansionRegion");
		expansionRegion.mode = ExpansionKind.iterative;
		expansionRegionActivity.addNode(expansionRegion);

		this.addEdge(expansionRegionActivity, new ControlFlow(), node,
				expansionRegion, null);

		ExpansionNode expansionNode = new ExpansionNode();
		expansionNode.setName(expansionRegion.name + ".input(list)");
		expansionRegion.addInputElement(expansionNode);
		expansionRegionActivity.addNode(expansionNode);

		expansionNode = new ExpansionNode();
		expansionNode.setName(expansionRegion.name + ".output(list)");
		expansionRegion.addOutputElement(expansionNode);
		expansionRegionActivity.addNode(expansionNode);

		expansionNode = new ExpansionNode();
		expansionNode.setName(expansionRegion.name + ".output(list*10)");
		expansionRegion.addOutputElement(expansionNode);
		expansionRegionActivity.addNode(expansionNode);

		ActivityParameterNode outputNode0 = new ActivityParameterNode();
		outputNode0.setName("Output(list)");
		outputNode0.setParameter(this.addParameter(expansionRegionActivity,
				"list", ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		expansionRegionActivity.addNode(outputNode0);

		ActivityParameterNode outputNode1 = new ActivityParameterNode();
		outputNode1.setName("Output(list*10)");
		outputNode1.setParameter(this.addParameter(expansionRegionActivity,
				"list*10", ParameterDirectionKind.out,
				this.environment.primitiveTypes.Integer));
		expansionRegionActivity.addNode(outputNode1);

		for (int i = 0; i < n; i++) {
			this.addEdge(expansionRegionActivity, new ObjectFlow(),
					valueAction[i].result, expansionRegion.inputElement
							.getValue(0), null);
		}

		this.addEdge(expansionRegionActivity, new ObjectFlow(),
				expansionRegion.outputElement.getValue(0), outputNode0, null);
		this.addEdge(expansionRegionActivity, new ObjectFlow(),
				expansionRegion.outputElement.getValue(1), outputNode1, null);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		expansionRegion.addNode(forkNode);

		ValueSpecificationAction valueAction10 = new ValueSpecificationAction();
		valueAction10.setName("Value(10)");
		valueAction10.setValue(this.createLiteralInteger("", 10));
		valueAction10.setResult(this.makeOutputPin(valueAction10.name
				+ ".result", 1, 1));
		expansionRegion.addNode(valueAction10);

		CallBehaviorAction timesCall = new CallBehaviorAction();
		timesCall.setName("Call(Times)");
		timesCall.setBehavior(this.environment.integerFunctions.integerTimes);
		timesCall.addArgument(this
				.makeInputPin(timesCall.name + ".first", 1, 1));
		timesCall.addArgument(this.makeInputPin(timesCall.name + ".second", 1,
				1));
		timesCall.addResult(this
				.makeOutputPin(timesCall.name + ".result", 1, 1));
		expansionRegion.addNode(timesCall);

		ObjectFlow flow = new ObjectFlow();
		flow.setSource(expansionRegion.inputElement.getValue(0));
		flow.setTarget(forkNode);
		expansionRegion.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(forkNode);
		flow.setTarget(expansionRegion.outputElement.getValue(0));
		expansionRegion.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(forkNode);
		flow.setTarget(timesCall.argument.getValue(0));
		expansionRegion.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(valueAction10.result);
		flow.setTarget(timesCall.argument.getValue(1));
		expansionRegion.addEdge(flow);

		flow = new ObjectFlow();
		flow.setSource(timesCall.result.getValue(0));
		flow.setTarget(expansionRegion.outputElement.getValue(1));
		expansionRegion.addEdge(flow);

		this.environment.addElement(expansionRegionActivity);

	} // createExpansionRegionTester

	public void createLinkCreator(String associationName) {
		NamedElement element = this.environment.getElement(associationName);

		if (element == null || !(element instanceof Association)) {
			Debug.println("[createLinkCreator] " + associationName
					+ " does not exist or is not an association.");
			return;
		}

		Association association = (Association) element;
		Property endA = association.memberEnd.getValue(0);
		Property endB = association.memberEnd.getValue(1);
		Class_ classA = (Class_) (endA.typedElement.type);
		Class_ classB = (Class_) (endB.typedElement.type);

		Activity linkCreatorActivity = new Activity();
		linkCreatorActivity.setName(associationName + "LinkCreator");

		InitialNode initialNode = new InitialNode();
		initialNode.setName("Init");
		this.addNode(linkCreatorActivity, initialNode);

		CreateObjectAction createActionA = new CreateObjectAction();
		createActionA.setName("Create A");
		createActionA.classifier = classA;
		createActionA.result = this.addOutputPin(createActionA,
				createActionA.name + ".a", 1, 1);
		this.addNode(linkCreatorActivity, createActionA);

		CreateObjectAction createActionB1 = new CreateObjectAction();
		createActionB1.setName("Create B1");
		createActionB1.classifier = classB;
		createActionB1.result = this.addOutputPin(createActionB1,
				createActionB1.name + ".b", 1, 1);
		this.addNode(linkCreatorActivity, createActionB1);

		CreateObjectAction createActionB2 = new CreateObjectAction();
		createActionB2.setName("Create B2");
		createActionB2.classifier = classB;
		createActionB2.result = this.addOutputPin(createActionB2,
				createActionB2.name + ".b", 1, 1);
		this.addNode(linkCreatorActivity, createActionB2);

		ForkNode forkNodeA = new ForkNode();
		forkNodeA.setName("ForkA");
		this.addNode(linkCreatorActivity, forkNodeA);

		ForkNode forkNodeB = new ForkNode();
		forkNodeB.setName("ForkB");
		this.addNode(linkCreatorActivity, forkNodeB);

		ActivityParameterNode outputNodeA = new ActivityParameterNode();
		outputNodeA.setName("Parameter(outputA)");
		outputNodeA.parameter = this.addParameter(linkCreatorActivity,
				"outputA", ParameterDirectionKind.out, classA);
		this.addNode(linkCreatorActivity, outputNodeA);

		ActivityParameterNode outputNodeB = new ActivityParameterNode();
		outputNodeB.setName("Parameter(outputB)");
		outputNodeB.parameter = this.addParameter(linkCreatorActivity,
				"outputB", ParameterDirectionKind.out, classB);
		this.addNode(linkCreatorActivity, outputNodeB);

		CreateLinkAction linkAction1 = new CreateLinkAction();
		linkAction1.setName("Create Link 1");
		this.addNode(linkCreatorActivity, linkAction1);

		LinkEndCreationData endData1a = new LinkEndCreationData();
		endData1a.setIsReplaceAll(false);
		endData1a.setEnd(endA);
		endData1a.value = this.addInputPin(linkAction1,
				linkAction1.name + ".a", 1, 1);

		LinkEndCreationData endData1b = new LinkEndCreationData();
		endData1b.setIsReplaceAll(false);
		endData1b.setEnd(endB);
		endData1b.value = this.addInputPin(linkAction1,
				linkAction1.name + ".b", 1, 1);

		linkAction1.addEndData(endData1a);
		linkAction1.addEndData(endData1b);

		CreateLinkAction linkAction2 = new CreateLinkAction();
		linkAction2.setName("Create Link 2");
		this.addNode(linkCreatorActivity, linkAction2);

		LinkEndCreationData endData2a = new LinkEndCreationData();
		endData2a.setIsReplaceAll(false);
		endData2a.setEnd(endA);
		endData2a.value = this.addInputPin(linkAction2,
				linkAction2.name + ".a", 1, 1);

		LinkEndCreationData endData2b = new LinkEndCreationData();
		endData2b.setIsReplaceAll(false);
		endData2b.setEnd(endB);
		endData2b.value = this.addInputPin(linkAction2,
				linkAction2.name + ".b", 1, 1);

		linkAction2.addEndData(endData2a);
		linkAction2.addEndData(endData2b);

		this.addEdge(linkCreatorActivity, new ControlFlow(), initialNode,
				createActionA, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(),
				createActionA.result, forkNodeA, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(), forkNodeA,
				outputNodeA, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(), forkNodeA,
				endData1a.value, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(), forkNodeA,
				endData2a.value, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(),
				createActionB1.result, forkNodeB, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(), forkNodeB,
				outputNodeB, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(), forkNodeB,
				endData1b.value, null);
		this.addEdge(linkCreatorActivity, new ObjectFlow(),
				createActionB2.result, endData2b.value, null);

		if (endB.multiplicityElement.isOrdered) {
			ValueSpecificationAction valueAction = new ValueSpecificationAction();
			valueAction.setName("Value(1)");
			valueAction.setValue(this.createLiteralUnlimitedNatural("", 1));
			valueAction.setResult(this.makeOutputPin(valueAction.name
					+ ".result", 1, 1));
			linkCreatorActivity.addNode(valueAction);

			ForkNode forkNode1 = new ForkNode();
			forkNode1.setName("Fork(1)");
			linkCreatorActivity.addNode(forkNode1);

			InputPin insertAt1 = this.makeInputPin(linkAction1.name
					+ ".insertAt", 1, 1);
			linkAction1.addInputValue(insertAt1);
			endData1b.setInsertAt(insertAt1);

			InputPin insertAt2 = this.makeInputPin(linkAction2.name
					+ ".insertAt", 1, 1);
			linkAction2.addInputValue(insertAt2);
			endData2b.setInsertAt(insertAt2);

			this.addEdge(linkCreatorActivity, new ObjectFlow(),
					valueAction.result, forkNode1, null);
			this.addEdge(linkCreatorActivity, new ObjectFlow(), forkNode1,
					insertAt1, null);
			this.addEdge(linkCreatorActivity, new ObjectFlow(), forkNode1,
					insertAt2, null);
		}

		this.environment.addElement(linkCreatorActivity);
	} // createLinkCreator

	public void createLinkDestroyer(String associationName) {
		NamedElement element = this.environment.getElement(associationName);

		if (element == null || !(element instanceof Association)) {
			Debug.println("[createLinkDestroyer] " + associationName
					+ " not found or is not an asssociation.");
			return;
		}

		Association association = (Association) element;
		Property endA = association.memberEnd.getValue(0);
		Property endB = association.memberEnd.getValue(1);

		Activity linkDestroyerActivity = new Activity();
		linkDestroyerActivity.setName(associationName + "LinkDestroyer");

		String linkCreatorName = associationName + "LinkCreator";
		NamedElement linkCreatorActivity = this.environment
				.getElement(linkCreatorName);

		if ((linkCreatorActivity == null)
				|| !(linkCreatorActivity instanceof Activity)) {
			if (linkCreatorActivity == null) {
				Debug.println("[createLinkDestroyer] Creating a "
						+ linkCreatorName + " activity.");
			} else {
				Debug.println("[createLinkDestroyer] Replacing the existing "
						+ linkCreatorName + " element with an activity.");
				this.environment.removeElement(linkCreatorActivity);
			}

			this.createLinkCreator(associationName);
			linkCreatorActivity = this.environment.getElement(linkCreatorName);

			if (linkCreatorActivity == null) {
				return;
			}
		}

		CallBehaviorAction callAction = new CallBehaviorAction();
		callAction.setName("Call(" + linkCreatorName + ")");
		callAction.setBehavior((Activity) linkCreatorActivity);
		callAction.addResult(this.makeOutputPin(callAction.name + ".result(a)",
				1, 1));
		callAction.addResult(this.makeOutputPin(callAction.name + ".result(b)",
				1, 1));
		linkDestroyerActivity.addNode(callAction);

		ForkNode forkNodeA = new ForkNode();
		forkNodeA.setName("ForkA");
		linkDestroyerActivity.addNode(forkNodeA);

		ForkNode forkNodeB = new ForkNode();
		forkNodeA.setName("ForkB");
		linkDestroyerActivity.addNode(forkNodeB);

		DestroyLinkAction destroyLinkAction = new DestroyLinkAction();
		destroyLinkAction.setName("DestroyLink(" + associationName + ")");
		destroyLinkAction.addInputValue(this.makeInputPin(
				destroyLinkAction.name + ".a", 1, 1));
		destroyLinkAction.addInputValue(this.makeInputPin(
				destroyLinkAction.name + ".b", 1, 1));
		linkDestroyerActivity.addNode(destroyLinkAction);

		LinkEndData endDataA = new LinkEndDestructionData();
		endDataA.setEnd(endA);
		endDataA.setValue(destroyLinkAction.inputValue.getValue(0));
		destroyLinkAction.addEndData(endDataA);

		LinkEndData endDataB = new LinkEndDestructionData();
		endDataB.setEnd(endB);
		endDataB.setValue(destroyLinkAction.inputValue.getValue(1));
		destroyLinkAction.addEndData(endDataB);

		ActivityParameterNode outputNodeA = new ActivityParameterNode();
		outputNodeA.setName("Parameter(outputA)");
		outputNodeA.setParameter(this.addParameter(linkDestroyerActivity,
				"outputA", ParameterDirectionKind.out, endA.typedElement.type));
		linkDestroyerActivity.addNode(outputNodeA);

		ActivityParameterNode outputNodeB = new ActivityParameterNode();
		outputNodeB.setName("Parameter(outputB)");
		outputNodeB.setParameter(this.addParameter(linkDestroyerActivity,
				"outputB", ParameterDirectionKind.out, endB.typedElement.type));
		linkDestroyerActivity.addNode(outputNodeB);

		this.addEdge(linkDestroyerActivity, new ObjectFlow(), callAction.result
				.getValue(0), forkNodeA, null);
		this.addEdge(linkDestroyerActivity, new ObjectFlow(), callAction.result
				.getValue(1), forkNodeB, null);
		this.addEdge(linkDestroyerActivity, new ObjectFlow(), forkNodeA,
				endDataA.value, null);
		this.addEdge(linkDestroyerActivity, new ObjectFlow(), forkNodeB,
				endDataB.value, null);
		this.addEdge(linkDestroyerActivity, new ObjectFlow(), forkNodeA,
				outputNodeA, null);
		this.addEdge(linkDestroyerActivity, new ObjectFlow(), forkNodeB,
				outputNodeB, null);

		this.environment.addElement(linkDestroyerActivity);
	} // createLinkDestroyer

	public void createLinkReader(String associationName) {
		NamedElement element = this.environment.getElement(associationName);

		if (element == null || !(element instanceof Association)) {
			Debug.println("[createLinkReader] " + associationName
					+ " not found or is not an asssociation.");
			return;
		}

		Association association = (Association) element;
		Property endA = association.memberEnd.getValue(0);
		Property endB = association.memberEnd.getValue(1);

		Activity linkReaderActivity = new Activity();
		linkReaderActivity.setName(associationName + "LinkReader");

		String linkCreatorName = associationName + "LinkCreator";
		NamedElement linkCreatorActivity = this.environment
				.getElement(linkCreatorName);

		if ((linkCreatorActivity == null)
				|| !(linkCreatorActivity instanceof Activity)) {
			if (linkCreatorActivity == null) {
				Debug.println("[createLinkReader] Creating a "
						+ linkCreatorName + " activity.");
			} else {
				Debug.println("[createLinkReader] Replacing the existing "
						+ linkCreatorName + " element with an activity.");
				this.environment.removeElement(linkCreatorActivity);
			}

			this.createLinkCreator(associationName);
			linkCreatorActivity = this.environment.getElement(linkCreatorName);

			if (linkCreatorActivity == null) {
				return;
			}
		}

		CallBehaviorAction callAction = new CallBehaviorAction();
		callAction.setName("Call(" + linkCreatorName + ")");
		callAction.setBehavior((Activity) linkCreatorActivity);
		callAction.addResult(this.makeOutputPin(callAction.name + ".result(a)",
				1, 1));
		callAction.addResult(this.makeOutputPin(callAction.name + ".result(b)",
				1, 1));
		linkReaderActivity.addNode(callAction);

		ForkNode fork = new ForkNode();
		fork.setName("ForkNode");
		linkReaderActivity.addNode(fork);

		ReadLinkAction readLinkAction = new ReadLinkAction();
		readLinkAction.setName("ReadLink(" + associationName + "::" + endB.name
				+ ")");
		readLinkAction.addInputValue(this.makeInputPin(readLinkAction.name
				+ ".input", 1, 1));
		readLinkAction.setResult(this.makeOutputPin(readLinkAction.name
				+ ".result", 0, -1));
		linkReaderActivity.addNode(readLinkAction);

		LinkEndData linkEndData = new LinkEndData();
		linkEndData.setEnd(endA);
		linkEndData.setValue(readLinkAction.inputValue.getValue(0));
		readLinkAction.addEndData(linkEndData);

		linkEndData = new LinkEndData();
		linkEndData.setEnd(endB);
		readLinkAction.addEndData(linkEndData);

		ReadStructuralFeatureAction readFeatureAction = new ReadStructuralFeatureAction();
		readFeatureAction.setName("ReadFeature(" + endB.name + ")");
		readFeatureAction.setStructuralFeature(endB);
		readFeatureAction.setObject(this.makeInputPin(readFeatureAction.name
				+ ".object", 1, 1));
		readFeatureAction.setResult(this.makeOutputPin(readFeatureAction.name
				+ ".result", 0, -1));
		linkReaderActivity.addNode(readFeatureAction);

		ActivityParameterNode linkOutput = new ActivityParameterNode();
		linkOutput.setName("LinkOutput");
		linkOutput.setParameter(this.addParameter(linkReaderActivity,
				"linkOutput", ParameterDirectionKind.out,
				endB.typedElement.type));
		linkReaderActivity.addNode(linkOutput);

		ActivityParameterNode featureOutput = new ActivityParameterNode();
		featureOutput.setName("FeatureOutput");
		featureOutput.setParameter(this.addParameter(linkReaderActivity,
				"featureOutput", ParameterDirectionKind.out,
				endB.typedElement.type));
		linkReaderActivity.addNode(featureOutput);

		this.addEdge(linkReaderActivity, new ObjectFlow(), callAction.result
				.getValue(0), fork, null);
		this.addEdge(linkReaderActivity, new ObjectFlow(), fork,
				readLinkAction.inputValue.getValue(0), null);
		this.addEdge(linkReaderActivity, new ObjectFlow(),
				readLinkAction.result, linkOutput, null);
		this.addEdge(linkReaderActivity, new ObjectFlow(), fork,
				readFeatureAction.object, null);
		this.addEdge(linkReaderActivity, new ObjectFlow(),
				readFeatureAction.result, featureOutput, null);

		this.environment.addElement(linkReaderActivity);
	} // createLinkReader

	public void createLinkWriter(String associationName) {
		NamedElement element = this.environment.getElement(associationName);

		if (element == null || !(element instanceof Association)) {
			Debug.println("[createLinkWriter] " + associationName
					+ " does not exist or is not an association.");
			return;
		}

		Association association = (Association) element;
		Property endA = association.memberEnd.getValue(0);
		Property endB = association.memberEnd.getValue(1);
		Class_ classA = (Class_) (endA.typedElement.type);
		Class_ classB = (Class_) (endB.typedElement.type);

		Activity linkWriterActivity = new Activity();
		linkWriterActivity.setName(associationName + "LinkWriter");

		InitialNode initialNode = new InitialNode();
		initialNode.setName("Init");
		linkWriterActivity.addNode(initialNode);

		CreateObjectAction createActionA = new CreateObjectAction();
		createActionA.setName("Create A");
		createActionA.setClassifier(classA);
		createActionA.setResult(this.makeOutputPin(createActionA.name + ".a",
				1, 1));
		linkWriterActivity.addNode(createActionA);

		CreateObjectAction createActionB1 = new CreateObjectAction();
		createActionB1.setName("Create B1");
		createActionB1.setClassifier(classB);
		createActionB1.setResult(this.makeOutputPin(createActionB1.name + ".b",
				1, 1));
		linkWriterActivity.addNode(createActionB1);

		CreateObjectAction createActionB2 = new CreateObjectAction();
		createActionB2.setName("Create B2");
		createActionB2.setClassifier(classB);
		createActionB2.setResult(this.makeOutputPin(createActionB2.name + ".b",
				1, 1));
		linkWriterActivity.addNode(createActionB2);

		ForkNode forkNodeA = new ForkNode();
		forkNodeA.setName("ForkA");
		linkWriterActivity.addNode(forkNodeA);

		ForkNode forkNodeB = new ForkNode();
		forkNodeB.setName("ForkB");
		linkWriterActivity.addNode(forkNodeB);

		ActivityParameterNode outputNodeA = new ActivityParameterNode();
		outputNodeA.setName("Parameter(outputA)");
		outputNodeA.setParameter(this.addParameter(linkWriterActivity,
				"outputA", ParameterDirectionKind.out, classA));
		linkWriterActivity.addNode(outputNodeA);

		ActivityParameterNode outputNodeB = new ActivityParameterNode();
		outputNodeB.setName("Parameter(outputB)");
		outputNodeB.setParameter(this.addParameter(linkWriterActivity,
				"outputB", ParameterDirectionKind.out, classB));
		linkWriterActivity.addNode(outputNodeB);

		AddStructuralFeatureValueAction writeAction1 = new AddStructuralFeatureValueAction();
		writeAction1.setName("Write 1");
		writeAction1.setStructuralFeature(endB);
		writeAction1.setObject(this.makeInputPin(writeAction1.name + ".object",
				1, 1));
		writeAction1.setValue(this.makeInputPin(writeAction1.name + ".value",
				1, 1));
		writeAction1.setResult(this.makeOutputPin(
				writeAction1.name + ".result", 1, 1));
		linkWriterActivity.addNode(writeAction1);

		AddStructuralFeatureValueAction writeAction2 = new AddStructuralFeatureValueAction();
		writeAction2.setName("Write 2");
		writeAction2.setStructuralFeature(endB);
		writeAction2.setObject(this.makeInputPin(writeAction2.name + ".object",
				1, 1));
		writeAction2.setValue(this.makeInputPin(writeAction2.name + ".value",
				1, 1));
		linkWriterActivity.addNode(writeAction2);

		this.addEdge(linkWriterActivity, new ObjectFlow(),
				createActionA.result, forkNodeA, null);
		this.addEdge(linkWriterActivity, new ObjectFlow(),
				createActionB1.result, forkNodeB, null);
		this.addEdge(linkWriterActivity, new ObjectFlow(),
				createActionB2.result, writeAction2.value, null);
		this.addEdge(linkWriterActivity, new ObjectFlow(), forkNodeA,
				outputNodeA, null);
		this.addEdge(linkWriterActivity, new ObjectFlow(), forkNodeA,
				writeAction1.object, null);
		this.addEdge(linkWriterActivity, new ObjectFlow(), forkNodeA,
				writeAction2.object, null);
		this.addEdge(linkWriterActivity, new ObjectFlow(), forkNodeB,
				outputNodeB, null);
		this.addEdge(linkWriterActivity, new ObjectFlow(), forkNodeB,
				writeAction1.value, null);
		this.addEdge(linkWriterActivity, new ControlFlow(), writeAction1,
				writeAction2, null);

		if (endB.multiplicityElement.isOrdered) {
			ValueSpecificationAction valueAction = new ValueSpecificationAction();
			valueAction.setName("Value(1)");
			valueAction.setValue(this.createLiteralUnlimitedNatural("", 1));
			valueAction.setResult(this.makeOutputPin(valueAction.name
					+ ".result", 1, 1));
			linkWriterActivity.addNode(valueAction);

			ForkNode forkNode1 = new ForkNode();
			forkNode1.setName("Fork(1)");
			linkWriterActivity.addNode(forkNode1);

			writeAction1.setInsertAt(this.makeInputPin(writeAction1.name
					+ ".insertAt", 1, 1));
			writeAction2.setInsertAt(this.makeInputPin(writeAction2.name
					+ ".insertAt", 1, 1));

			this.addEdge(linkWriterActivity, new ObjectFlow(),
					valueAction.result, forkNode1, null);
			this.addEdge(linkWriterActivity, new ObjectFlow(), forkNode1,
					writeAction1.insertAt, null);
			this.addEdge(linkWriterActivity, new ObjectFlow(), forkNode1,
					writeAction2.insertAt, null);
		}

		this.environment.addElement(linkWriterActivity);
	} // createLinkWriter

	public void createLinkRemover(String associationName) {
		NamedElement element = this.environment.getElement(associationName);

		if (element == null || !(element instanceof Association)) {
			Debug.println("[createLinkRemover] " + associationName
					+ " not found or is not an asssociation.");
			return;
		}

		Association association = (Association) element;
		Property endA = association.memberEnd.getValue(0);
		Property endB = association.memberEnd.getValue(1);

		Activity linkRemoverActivity = new Activity();
		linkRemoverActivity.setName(associationName + "LinkRemover");

		String linkWriterName = associationName + "LinkWriter";
		NamedElement linkWriterActivity = this.environment
				.getElement(linkWriterName);

		if ((linkWriterActivity == null)
				|| !(linkWriterActivity instanceof Activity)) {
			if (linkWriterActivity == null) {
				Debug.println("[createLinkRemover] Creating a "
						+ linkWriterName + " activity.");
			} else {
				Debug.println("[createLinkRemover] Replacing the existing "
						+ linkWriterName + " element with an activity.");
				this.environment.removeElement(linkWriterActivity);
			}

			this.createLinkWriter(associationName);
			linkWriterActivity = this.environment.getElement(linkWriterName);

			if (linkWriterActivity == null) {
				return;
			}
		}

		CallBehaviorAction callAction = new CallBehaviorAction();
		callAction.setName("Call(" + linkWriterName + ")");
		callAction.setBehavior((Activity) linkWriterActivity);
		callAction.addResult(this.makeOutputPin(callAction.name + ".result(a)",
				1, 1));
		callAction.addResult(this.makeOutputPin(callAction.name + ".result(b)",
				1, 1));
		linkRemoverActivity.addNode(callAction);

		ForkNode forkNodeA = new ForkNode();
		forkNodeA.setName("ForkA");
		linkRemoverActivity.addNode(forkNodeA);

		ForkNode forkNodeB = new ForkNode();
		forkNodeA.setName("ForkB");
		linkRemoverActivity.addNode(forkNodeB);

		RemoveStructuralFeatureValueAction removeAction = new RemoveStructuralFeatureValueAction();
		removeAction.setName("Remove(" + endB.name + ")");
		removeAction.setStructuralFeature(endB);
		removeAction.setObject(this.makeInputPin(removeAction.name + ".object",
				1, 1));
		linkRemoverActivity.addNode(removeAction);

		ActivityParameterNode outputNodeA = new ActivityParameterNode();
		outputNodeA.setName("Parameter(outputA)");
		outputNodeA.setParameter(this.addParameter(linkRemoverActivity,
				"outputA", ParameterDirectionKind.out, endA.typedElement.type));
		linkRemoverActivity.addNode(outputNodeA);

		ActivityParameterNode outputNodeB = new ActivityParameterNode();
		outputNodeB.setName("Parameter(outputB)");
		outputNodeB.setParameter(this.addParameter(linkRemoverActivity,
				"outputB", ParameterDirectionKind.out, endB.typedElement.type));
		linkRemoverActivity.addNode(outputNodeB);

		this.addEdge(linkRemoverActivity, new ObjectFlow(), callAction.result
				.getValue(0), forkNodeA, null);
		this.addEdge(linkRemoverActivity, new ObjectFlow(), callAction.result
				.getValue(1), forkNodeB, null);
		this.addEdge(linkRemoverActivity, new ObjectFlow(), forkNodeA,
				removeAction.object, null);
		this.addEdge(linkRemoverActivity, new ObjectFlow(), forkNodeA,
				outputNodeA, null);
		this.addEdge(linkRemoverActivity, new ObjectFlow(), forkNodeB,
				outputNodeB, null);

		if (!endB.multiplicityElement.isOrdered
				|| endB.multiplicityElement.isUnique) {
			removeAction.setValue(this.makeInputPin(removeAction.name
					+ ".value", 1, 1));
			this.addEdge(linkRemoverActivity, new ObjectFlow(), forkNodeB,
					removeAction.value, null);
		} else {
			ValueSpecificationAction valueAction = new ValueSpecificationAction();
			valueAction.setName("Value(1)");
			valueAction.setValue(this.createLiteralUnlimitedNatural("", 1));
			valueAction.setResult(this.makeOutputPin(valueAction.name
					+ ".result", 1, 1));
			linkRemoverActivity.addNode(valueAction);

			removeAction.setRemoveAt(this.makeInputPin(removeAction.name
					+ ".removeAt", 1, 1));
			this.addEdge(linkRemoverActivity, new ObjectFlow(),
					valueAction.result, removeAction.removeAt, null);
		}

		this.environment.addElement(linkRemoverActivity);
	} // createLinkRemover

	public void createIsClassifiedTester(String classifierName) {
		NamedElement element = this.environment.getElement(classifierName);

		if (element == null || !(element instanceof Classifier)) {
			Debug.println("[createIsClassifiedTester] " + classifierName
					+ " does not exist or is not a classifier.");
			return;
		}

		Classifier type = (Classifier) element;

		Activity activity = new Activity();
		activity.setName("TestIs" + classifierName);

		ActivityParameterNode inputNode = new ActivityParameterNode();
		inputNode.setName("Parameter(input)");
		inputNode.setParameter(this.addParameter(activity, "input",
				ParameterDirectionKind.in, type));
		activity.addNode(inputNode);

		ReadIsClassifiedObjectAction testAction = new ReadIsClassifiedObjectAction();
		testAction.setName("InstanceOf(" + classifierName + ")");
		testAction.setClassifier(type);
		testAction.setObject(this.makeInputPin(testAction.name + ".object", 1,
				1));
		testAction.setResult(this.makeOutputPin(testAction.name + ".result", 1,
				1));
		activity.addNode(testAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("Parameter(output)");
		outputNode.setParameter(this.addParameter(activity, "output",
				ParameterDirectionKind.out,
				this.environment.primitiveTypes.Boolean));
		activity.addNode(outputNode);

		this.addEdge(activity, new ObjectFlow(), inputNode, testAction.object,
				null);
		this.addEdge(activity, new ObjectFlow(), testAction.result, outputNode,
				null);

		this.environment.addElement(activity);

	} // createIsClassifiedTester

	public void createFireAgainTester() {
		Activity activity = new Activity();
		activity.setName("FireAgainTester");

		ValueSpecificationAction valueAction1 = new ValueSpecificationAction();
		valueAction1.setName("Value(1)");
		valueAction1.setValue(this.createLiteralInteger("", 1));
		valueAction1.setResult(this.makeOutputPin(
				valueAction1.name + ".result", 1, 1));
		activity.addNode(valueAction1);

		ValueSpecificationAction valueAction2 = new ValueSpecificationAction();
		valueAction2.setName("Value(2)");
		valueAction2.setValue(this.createLiteralInteger("", 2));
		valueAction2.setResult(this.makeOutputPin(
				valueAction2.name + ".result", 1, 1));
		activity.addNode(valueAction2);

		StructuredActivityNode structuredNode = new StructuredActivityNode();
		structuredNode.setName("StructuredNode");
		activity.addNode(structuredNode);

		CallBehaviorAction callAction = new CallBehaviorAction();
		callAction.setName("Call");
		callAction.setBehavior(this.getActivity("Copier"));
		callAction.addArgument(this.makeInputPin(callAction.name + ".argument",
				0, 1));
		callAction.addResult(this.makeOutputPin(callAction.name + ".result", 0,
				1));
		structuredNode.addNode(callAction);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("Parameter(output)");
		outputNode.setParameter(this.addParameter(activity, "output",
				ParameterDirectionKind.out, null));
		activity.addNode(outputNode);

		this.addEdge(activity, new ControlFlow(), valueAction1, valueAction2,
				null);
		this.addEdge(activity, new ControlFlow(), valueAction2, structuredNode,
				null);
		this.addEdge(activity, new ObjectFlow(), valueAction1.result,
				callAction.argument.getValue(0), null);
		this.addEdge(activity, new ObjectFlow(), valueAction2.result,
				callAction.argument.getValue(0), null);
		this.addEdge(activity, new ObjectFlow(), callAction.result.getValue(0),
				outputNode, null);

		this.environment.addElement(activity);
	} // createFireAgainTester

	public void createFlowFinal() {
		Activity flowFinalActivity = new Activity();
		flowFinalActivity.setName("FlowFinal");

		ValueSpecificationAction actionA = new ValueSpecificationAction();
		actionA.setName("Action_A");
		actionA.setValue(this.createLiteralInteger("", 0));
		actionA.setResult(this.makeOutputPin(actionA.name + ".result", 1, 1));
		this.addNode(flowFinalActivity, actionA);

		ValueSpecificationAction actionB = new ValueSpecificationAction();
		actionB.setName("Action_B");
		actionB.setValue(this.createLiteralInteger("", 1));
		actionB.setResult(this.makeOutputPin(actionB.name + ".result", 1, 1));
		this.addNode(flowFinalActivity, actionB);

		FlowFinalNode finalNode = new FlowFinalNode();
		finalNode.setName("FlowFinalNode");
		this.addNode(flowFinalActivity, finalNode);

		this.addEdge(flowFinalActivity, new ObjectFlow(), actionA.result,
				finalNode, null);
		this.addEdge(flowFinalActivity, new ObjectFlow(), actionB.result,
				finalNode, null);
		this.addEdge(flowFinalActivity, new ControlFlow(), actionA, actionB,
				null);
		this.addEdge(flowFinalActivity, new ControlFlow(), actionB, finalNode,
				null);

		this.environment.addElement(flowFinalActivity);
	} // createFlowFinal

} // ActivityFactory
