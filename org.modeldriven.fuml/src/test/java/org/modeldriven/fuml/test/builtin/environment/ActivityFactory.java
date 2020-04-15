/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2017 Data Access Technologies, Inc.
 * Copyright 2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.test.builtin.environment;

import org.modeldriven.fuml.test.builtin.library.IntegerFunctions;
import org.modeldriven.fuml.test.builtin.library.PrimitiveTypes;
import org.modeldriven.fuml.test.builtin.library.StandardIOClasses;

import fuml.Debug;
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.syntax.actions.AcceptCallAction;
import fuml.syntax.actions.AcceptEventAction;
import fuml.syntax.actions.AddStructuralFeatureValueAction;
import fuml.syntax.actions.CallBehaviorAction;
import fuml.syntax.actions.CallOperationAction;
import fuml.syntax.actions.Clause;
import fuml.syntax.actions.ConditionalNode;
import fuml.syntax.actions.CreateLinkAction;
import fuml.syntax.actions.CreateObjectAction;
import fuml.syntax.actions.DestroyLinkAction;
import fuml.syntax.actions.DestroyObjectAction;
import fuml.syntax.actions.ExpansionKind;
import fuml.syntax.actions.ExpansionNode;
import fuml.syntax.actions.ExpansionRegion;
import fuml.syntax.actions.InputPin;
import fuml.syntax.actions.LinkEndCreationData;
import fuml.syntax.actions.LinkEndData;
import fuml.syntax.actions.LinkEndDestructionData;
import fuml.syntax.actions.LoopNode;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.ReadExtentAction;
import fuml.syntax.actions.ReadIsClassifiedObjectAction;
import fuml.syntax.actions.ReadLinkAction;
import fuml.syntax.actions.ReadSelfAction;
import fuml.syntax.actions.ReadStructuralFeatureAction;
import fuml.syntax.actions.RemoveStructuralFeatureValueAction;
import fuml.syntax.actions.ReplyAction;
import fuml.syntax.actions.SendSignalAction;
import fuml.syntax.actions.StartObjectBehaviorAction;
import fuml.syntax.actions.StructuredActivityNode;
import fuml.syntax.actions.TestIdentityAction;
import fuml.syntax.actions.UnmarshallAction;
import fuml.syntax.actions.ValueSpecificationAction;
import fuml.syntax.activities.Activity;
import fuml.syntax.activities.ActivityEdge;
import fuml.syntax.activities.ActivityFinalNode;
import fuml.syntax.activities.ActivityNode;
import fuml.syntax.activities.ActivityParameterNode;
import fuml.syntax.activities.CentralBufferNode;
import fuml.syntax.activities.ControlFlow;
import fuml.syntax.activities.DataStoreNode;
import fuml.syntax.activities.DecisionNode;
import fuml.syntax.activities.FlowFinalNode;
import fuml.syntax.activities.ForkNode;
import fuml.syntax.activities.InitialNode;
import fuml.syntax.activities.JoinNode;
import fuml.syntax.activities.MergeNode;
import fuml.syntax.activities.ObjectFlow;
import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.Operation;
import fuml.syntax.classification.Parameter;
import fuml.syntax.classification.ParameterDirectionKind;
import fuml.syntax.classification.ParameterList;
import fuml.syntax.classification.Property;
import fuml.syntax.classification.StructuralFeature;
import fuml.syntax.commonbehavior.Behavior;
import fuml.syntax.commonbehavior.CallEvent;
import fuml.syntax.commonbehavior.SignalEvent;
import fuml.syntax.commonbehavior.Trigger;
import fuml.syntax.commonstructure.Element;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.simpleclassifiers.Reception;
import fuml.syntax.simpleclassifiers.Signal;
import fuml.syntax.structuredclassifiers.Association;
import fuml.syntax.structuredclassifiers.Class_;
import fuml.syntax.values.LiteralBoolean;
import fuml.syntax.values.LiteralInteger;
import fuml.syntax.values.LiteralString;
import fuml.syntax.values.LiteralUnlimitedNatural;
import fuml.syntax.values.ValueSpecification;

public class ActivityFactory {

	public TestEnvironment environment = null;

	public ActivityFactory(org.modeldriven.fuml.test.builtin.environment.TestEnvironment environment) {
		this.environment = environment;

	} // ActivityFactory

	protected void addEdge(
			fuml.syntax.activities.Activity activity,
			fuml.syntax.activities.ActivityEdge edge,
			fuml.syntax.activities.ActivityNode source,
			fuml.syntax.activities.ActivityNode target,
			fuml.syntax.values.ValueSpecification guard) {
		edge.setSource(source);
		edge.setTarget(target);
		edge.setGuard(guard);

		activity.addEdge(edge);

	} // addEdge

	protected void addNode(
			fuml.syntax.activities.Activity activity,
			fuml.syntax.activities.ActivityNode node) {
		activity.addNode(node);

	} // addNode

	protected fuml.syntax.classification.Parameter addParameter(
			fuml.syntax.activities.Activity activity,
			String name,
			fuml.syntax.classification.ParameterDirectionKind direction,
			fuml.syntax.commonstructure.Type type) {
		Parameter parameter = new Parameter();
		parameter.setName(name);
		parameter.setType(type);
		parameter.setDirection(direction);
		parameter.setLower(1);
		parameter.setUpper(1);

		activity.addOwnedParameter(parameter);

		return parameter;
	} // addParameter

	protected fuml.syntax.classification.Property addProperty(
			fuml.syntax.activities.Activity activity,
			String name, fuml.syntax.commonstructure.Type type, int lower,
			int upper) {
		Property property = new Property();
		property.setName(name);
		property.setType(type);
		property.setLower(lower);
		property.setUpper(upper);

		activity.addOwnedAttribute(property);

		return property;

	} // addProperty

	protected fuml.syntax.actions.InputPin addInputPin(
			fuml.syntax.actions.Action action, String name,
			int lower, int upper) {
		// Debug.println("[addInputPin] name = " + name);

		InputPin inputPin = new InputPin();
		this.setPin(inputPin, name, lower, upper);
		action.input.addValue(inputPin);
		return inputPin;
	} // addInputPin

	protected fuml.syntax.actions.OutputPin addOutputPin(
			fuml.syntax.actions.Action action, String name,
			int lower, int upper) {
		// Debug.println("[addOutputPin] name = " + name);

		OutputPin outputPin = new OutputPin();
		this.setPin(outputPin, name, lower, upper);
		action.output.addValue(outputPin);
		return outputPin;
	} // addOutputPin

	protected fuml.syntax.actions.InputPin makeInputPin(
			String name, int lower, int upper) {
		// Debug.println("[makeInputPin] name = " + name);

		InputPin inputPin = new InputPin();
		this.setPin(inputPin, name, lower, upper);

		return inputPin;
	} // makeInputPin

	protected fuml.syntax.actions.OutputPin makeOutputPin(
			String name, int lower, int upper) {
		// Debug.println("[makeOutputPin] name = " + name);

		OutputPin outputPin = new OutputPin();
		this.setPin(outputPin, name, lower, upper);

		return outputPin;

	} // makeOutputPin

	protected void setPin(fuml.syntax.actions.Pin pin,
			String name, int lower, int upper) {
		// Debug.println("[setPin] name = " + name);

		pin.setName(name);
		pin.setLower(lower);
		pin.setUpper(upper);

		// this.addNode(activity, pin);
	} // setPin

	protected void setMultiplicity(
			fuml.syntax.commonstructure.MultiplicityElement element, int lower,
			int upper) {
		element.setLower(lower);
		element.setUpper(upper);
	} // setMultiplicity

	protected fuml.syntax.classification.Operation getOperation(
			fuml.syntax.structuredclassifiers.Class_ class_, String operationName) {
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

	protected fuml.syntax.classification.Property getProperty(
			fuml.syntax.classification.Classifier classifier,
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

	protected fuml.syntax.values.LiteralInteger createLiteralInteger(
			String name, int value) {
		LiteralInteger literal = (LiteralInteger) (this.environment
				.makeValue((Classifier) (PrimitiveTypes.Integer))
				.specify());
		literal.setName(name);
		literal.setValue(value);

		return literal;

	} // createLiteralInteger

	protected fuml.syntax.values.LiteralString createLiteralString(
			String name, String value) {
		LiteralString literal = (LiteralString) (this.environment
				.makeValue((Classifier) (PrimitiveTypes.String))
				.specify());
		literal.setName(name);
		literal.setValue(value);

		return literal;

	} // createLiteralString

	protected fuml.syntax.values.LiteralBoolean createLiteralBoolean(
			String name, boolean value) {
		LiteralBoolean literal = (LiteralBoolean) (this.environment
				.makeValue((Classifier) (PrimitiveTypes.Boolean))
				.specify());
		literal.setName(name);
		literal.setValue(value);

		return literal;

	} // createLiteralBoolean

	protected fuml.syntax.values.LiteralUnlimitedNatural createLiteralUnlimitedNatural(
			String name, int value) {
		LiteralUnlimitedNatural literal = (LiteralUnlimitedNatural) (this.environment
				.makeValue((Classifier) (PrimitiveTypes.UnlimitedNatural))
				.specify());
		literal.setName(name);
		literal.value.naturalValue = value;

		return literal;
	} // createLiteralUnlimitedNatural

	protected fuml.syntax.activities.Activity createInstanceGetter(
			fuml.syntax.classification.Classifier classifier,
			fuml.syntax.classification.Operation operation,
			fuml.semantics.values.Value value) {
		Activity testActivity = new Activity();
		testActivity.setName("Test(" + operation.name + ")");

		Parameter testInput = this.addParameter(testActivity, "testInput",
				ParameterDirectionKind.in, classifier);
		Parameter testOutput = this.addParameter(testActivity, "testOutput",
				ParameterDirectionKind.out,
				PrimitiveTypes.Boolean);

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

	protected fuml.syntax.activities.Activity getCopier() {
		return this.getActivity("Copier");
	} // getCopier

	public fuml.syntax.activities.Activity getActivity(
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
				PrimitiveTypes.Integer);
		Parameter outputParameter = this.addParameter(activity, "output",
				ParameterDirectionKind.out,
				PrimitiveTypes.Integer);

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
		Activity simpleDecisionActivity = new Activity();
		simpleDecisionActivity.setName("SimpleDecision" + testValue);

		Parameter parameter0 = this.addParameter(simpleDecisionActivity,
				"output_0", ParameterDirectionKind.out,
				PrimitiveTypes.Integer);
		Parameter parameter1 = this.addParameter(simpleDecisionActivity,
				"output_1", ParameterDirectionKind.out,
				PrimitiveTypes.Integer);

		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("ValueAction_" + testValue);
		valueAction.setValue(this.createLiteralInteger("", testValue));
		valueAction.setResult(this.makeOutputPin(valueAction.name + ".result",
				1, 1));
		this.addNode(simpleDecisionActivity, valueAction);

		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setName("DecisionNode");
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
				PrimitiveTypes.Integer));
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
				PrimitiveTypes.Integer));
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
				PrimitiveTypes.Integer));
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

//	public void createIdentityTester(String nameSuffix, String variableName1,
//			String variableName2) {
//		Variable variable1 = this.environment.getVariable(variableName1);
//		Variable variable2 = this.environment.getVariable(variableName2);
//
//		if (variable1 == null) {
//			Debug.println("[createIdentityTester] " + variableName1
//					+ " does not exist.");
//			return;
//		}
//
//		if (variable2 == null) {
//			Debug.println("[createIdentityTester] " + variableName2
//					+ " does not exist.");
//			return;
//		}
//
//		Value value1 = variable1.value;
//		Value value2 = variable2.value;
//
//		Activity identityTesterActivity = new Activity();
//		identityTesterActivity.setName("IdentityTester_" + nameSuffix);
//
//		ValueSpecificationAction valueAction1 = new ValueSpecificationAction();
//		valueAction1.setName("ValueAction_1");
//		valueAction1.setValue(value1.specify());
//		valueAction1.setResult(this.makeOutputPin(
//				valueAction1.name + ".result", 1, 1));
//		this.addNode(identityTesterActivity, valueAction1);
//
//		ValueSpecificationAction valueAction2 = new ValueSpecificationAction();
//		valueAction2.setName("ValueAction_2");
//		valueAction2.setValue(value2.specify());
//		valueAction2.setResult(this.addOutputPin(valueAction2,
//				valueAction2.name + ".result", 1, 1));
//		this.addNode(identityTesterActivity, valueAction2);
//
//		TestIdentityAction testIdentityAction = new TestIdentityAction();
//		testIdentityAction.setName("TestIdentityAction");
//		testIdentityAction.setFirst(this.makeInputPin(testIdentityAction.name
//				+ ".first", 1, 1));
//		testIdentityAction.setSecond(this.makeInputPin(testIdentityAction.name
//				+ ".second", 1, 1));
//		testIdentityAction.setResult(this.makeOutputPin(testIdentityAction.name
//				+ ".result", 1, 1));
//		this.addNode(identityTesterActivity, testIdentityAction);
//
//		ActivityParameterNode outputNode = new ActivityParameterNode();
//		outputNode.setName("outputNode");
//		outputNode.setParameter(this.addParameter(identityTesterActivity,
//				"result", ParameterDirectionKind.out,
//				PrimitiveTypes.Boolean));
//		this.addNode(identityTesterActivity, outputNode);
//
//		this.addEdge(identityTesterActivity, new ObjectFlow(),
//				valueAction1.result, testIdentityAction.first, null);
//		this.addEdge(identityTesterActivity, new ObjectFlow(),
//				valueAction2.result, testIdentityAction.second, null);
//		this.addEdge(identityTesterActivity, new ObjectFlow(),
//				testIdentityAction.result, outputNode, null);
//
//		this.environment.addElement(identityTesterActivity);
//	} // createIdentityTester

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
					PrimitiveTypes.UnlimitedNatural));
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
		Class_ standardOutputChannelClass = StandardIOClasses.StandardOutputChannel;

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
		callAction.addResult(this.makeOutputPin(callAction.name + ".result",
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
		callAction.addResult(this.makeOutputPin(callAction.name + ".result",
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
		Class_ inputChannelClass = StandardIOClasses.InputChannel;
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
		Class_ outputChannelClass = StandardIOClasses.OutputChannel;
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
				PrimitiveTypes.String);

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
		Class_ standardOutputChannelClass = StandardIOClasses.StandardOutputChannel;

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
		
		Property property = this.addProperty(accepterActivity, "signal", null, 0, 1);

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
		
		ReadSelfAction readSelfAction = new ReadSelfAction();
		readSelfAction.setName("ReadSelf");
		readSelfAction.setResult(this.makeOutputPin(
				readSelfAction.name + ".result", 1, 1));
		this.addNode(accepterActivity, readSelfAction);
		
		AddStructuralFeatureValueAction writeAction = new AddStructuralFeatureValueAction();
		writeAction.setName("Write(signal)");
		writeAction.setStructuralFeature(property);
		writeAction.setObject(this.makeInputPin(
				writeAction.name + ".object", 1, 1));
		writeAction.setValue(this.makeInputPin(
				writeAction.name + ".value", 1, 1));
		writeAction.setResult(this.makeOutputPin(
				writeAction.name + ".result", 1, 1));
		accepterActivity.addNode(writeAction);
		
		InitialNode initialNode = new InitialNode();
		this.addNode(accepterActivity, initialNode);

		this.addEdge(accepterActivity, new ControlFlow(), 
				initialNode, acceptEventAction, null);
		this.addEdge(accepterActivity, new ObjectFlow(),
				acceptEventAction.result.getValue(0), 
				writeAction.value, null);
		this.addEdge(accepterActivity, new ObjectFlow(), 
				readSelfAction.result, writeAction.object, null);

		this.environment.addElement(accepterActivity);
	} // createAccepter

	public void createSender(String signalName) {
		NamedElement element = this.environment.getElement(signalName);

		if (element == null || !(element instanceof Signal)) {
			Debug.println("[createSender] " + signalName
					+ " does not exist or is not a signal.");
			return;
		}

		Signal signal = (Signal) element;

		this.createAccepter(signalName);
		element = this.environment.getElement(signalName + "Accepter");

		if (!(element instanceof Activity)) {
			Debug.println("[createSender] " + signalName
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

		this.addEdge(senderActivity, new ObjectFlow(),
				createObjectAction.result, forkNode, null);
		this.addEdge(senderActivity, new ObjectFlow(), forkNode,
				startAction.object, null);
		this.addEdge(senderActivity, new ObjectFlow(), forkNode,
				sendAction.target, null);

		this.addEdge(senderActivity, new ControlFlow(), startAction,
				sendAction, null);

		this.environment.addElement(senderActivity);
	} // createSender

	public void createCallAccepter(String operationName) {
		Activity accepterActivity = new Activity();
		accepterActivity.setName(operationName + "CallAccepter");
		accepterActivity.setIsActive(true);
		
		Operation operation = new Operation();
		operation.setName(operationName);
		accepterActivity.addOwnedOperation(operation);

		Parameter parameter = new Parameter();
		parameter.setName("input");
		parameter.setType(PrimitiveTypes.Integer);
		parameter.setLower(1);
		parameter.setUpper(1);
		parameter.setDirection(ParameterDirectionKind.in);
		operation.addOwnedParameter(parameter);
		
		parameter = new Parameter();
		parameter.setName("output");
		parameter.setType(PrimitiveTypes.Integer);
		parameter.setLower(1);
		parameter.setUpper(1);
		parameter.setDirection(ParameterDirectionKind.out);
		operation.addOwnedParameter(parameter);

		CallEvent callEvent = new CallEvent();
		callEvent.setOperation(operation);

		Trigger trigger = new Trigger();
		trigger.setEvent(callEvent);

		AcceptCallAction acceptCallAction = new AcceptCallAction();
		acceptCallAction.setName("Accept(" + operationName + ")");
		acceptCallAction.addTrigger(trigger);
		acceptCallAction.setIsUnmarshall(true);
		acceptCallAction.addResult(this.makeOutputPin(
				acceptCallAction.name + ".result", 1, 1));
		acceptCallAction.setReturnInformation(this.makeOutputPin(
				acceptCallAction.name + ".returnInfo", 1, 1));
		this.addNode(accepterActivity, acceptCallAction);
		
		ReplyAction replyAction = new ReplyAction();
		replyAction.setName("Reply(" + operation.name + ")");
		replyAction.setReplyToCall(trigger);
		replyAction.addReplyValue(this.makeInputPin(
				replyAction.name + ".replyValue", 1, 1));
		replyAction.setReturnInformation(this.makeInputPin(
				replyAction.name + ".returnInfo", 1, 1));
		this.addNode(accepterActivity, replyAction);
		
		InitialNode initialNode = new InitialNode();
		this.addNode(accepterActivity, initialNode);

		this.addEdge(accepterActivity, new ControlFlow(), 
				initialNode, acceptCallAction, null);
		this.addEdge(accepterActivity, new ControlFlow(), 
				acceptCallAction, replyAction, null);
		
		this.addEdge(accepterActivity, new ObjectFlow(),
				acceptCallAction.result.getValue(0), 
				replyAction.replyValue.get(0), null);
		this.addEdge(accepterActivity, new ObjectFlow(), 
				acceptCallAction.returnInformation, replyAction.returnInformation, null);

		this.environment.addElement(accepterActivity);
	}

	public void createCallSender(String operationName, String signalName) {
		NamedElement element = this.environment.getElement(signalName);

		if (element == null || !(element instanceof Signal)) {
			Debug.println("[createCallSender] " + signalName
					+ " does not exist or is not a signal.");
			return;
		}

		Signal signal = (Signal) element;
		
		this.createCallAccepter(operationName);
		element = this.environment.getElement(operationName + "CallAccepter");

		if (!(element instanceof Activity)) {
			Debug.println("[createCallSender] " + operationName
					+ "CallAccepter is not an activity.");
			return;
		}

		Activity accepterActivity = (Activity) element;
		
		if (accepterActivity.ownedOperation.size() == 0) {
			Debug.println("[createCallSender] " + operationName
					+ "CallAccepter has no owned operations.");
			return;
		}
		
		Operation operation = accepterActivity.ownedOperation.get(0);

		Activity senderActivity = new Activity();
		senderActivity.setName(operationName + "CallSender");
		senderActivity.setIsActive(true);
		Property property = this.addProperty(senderActivity, "value", null, 0, 1);

		CreateObjectAction createAction = new CreateObjectAction();
		createAction.setName("Create(" + accepterActivity.name + ")");
		createAction.setClassifier(accepterActivity);
		createAction.setResult(this.makeOutputPin(
				createAction.name + ".result", 1, 1));
		this.addNode(senderActivity, createAction);

		ForkNode forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		this.addNode(senderActivity, forkNode);

		StartObjectBehaviorAction startAction = new StartObjectBehaviorAction();
		startAction.setName("Start(" + accepterActivity.name + ")");
		startAction.setObject(this.makeInputPin(
				startAction.name + ".object", 1, 1));
		this.addNode(senderActivity, startAction);

		CallOperationAction callAction = new CallOperationAction();
		callAction.setName("Call(" + operationName + ")");
		callAction.setOperation(operation);
		callAction.setTarget(this.makeInputPin(
				callAction.name + ".target", 1, 1));
		callAction.addArgument(this.makeInputPin(
				callAction.name + ".argument", 1, 1));
		callAction.addResult(this.makeOutputPin(
				callAction.name + ".result", 1, 1));
		this.addNode(senderActivity, callAction);
		
		ValueSpecificationAction valueAction = new ValueSpecificationAction();
		valueAction.setName("Value(0)");
		valueAction.setValue(this.createLiteralInteger("", 0));
		valueAction.setResult(this.makeOutputPin(
				valueAction.name + ".result", 1, 1));
		this.addNode(senderActivity, valueAction);
		
		SignalEvent signalEvent = new SignalEvent();
		signalEvent.signal = signal;
		
		Trigger trigger = new Trigger();
		trigger.event = signalEvent;
		
		AcceptEventAction acceptAction = new AcceptEventAction();
		acceptAction.setName("Accept(" + signal.name + ")");
		acceptAction.addTrigger(trigger);
		this.addNode(senderActivity, acceptAction);
		
		ActivityFinalNode finalNode = new ActivityFinalNode();
		finalNode.setName("FinalNode");
		this.addNode(senderActivity, finalNode);
		
		ReadSelfAction readSelfAction = new ReadSelfAction();
		readSelfAction.setName("ReadSelf");
		readSelfAction.setResult(this.makeOutputPin(
				readSelfAction.name + ".result", 1, 1));
		this.addNode(senderActivity, readSelfAction);
		
		AddStructuralFeatureValueAction writeAction = new AddStructuralFeatureValueAction();
		writeAction.setName("Write(" + property.name + ")");
		writeAction.setStructuralFeature(property);
		writeAction.setObject(this.makeInputPin(
				writeAction.name + ".object", 1, 1));
		writeAction.setValue(this.makeInputPin(
				writeAction.name + ".value", 1, 1));
		this.addNode(senderActivity, writeAction);

		this.addEdge(senderActivity, new ControlFlow(), 
				startAction, callAction, null);
		this.addEdge(senderActivity, new ControlFlow(), 
				acceptAction, finalNode, null);

		this.addEdge(senderActivity, new ObjectFlow(),
				createAction.result, forkNode, null);
		this.addEdge(senderActivity, new ObjectFlow(), 
				forkNode, startAction.object, null);
		this.addEdge(senderActivity, new ObjectFlow(), 
				forkNode, callAction.target, null);
		this.addEdge(senderActivity, new ObjectFlow(), 
				valueAction.result, callAction.argument.get(0), null);
		this.addEdge(senderActivity, new ObjectFlow(), 
				readSelfAction.result, writeAction.object, null);
		this.addEdge(senderActivity, new ObjectFlow(), 
				callAction.result.get(0), writeAction.value, null);

		this.environment.addElement(senderActivity);
		
		Activity driverActivity = new Activity();
		driverActivity.setName("Do" + senderActivity.name);
		
		createAction = new CreateObjectAction();
		createAction.setName("Create(" + senderActivity.name + ")");
		createAction.setClassifier(senderActivity);
		createAction.setResult(this.makeOutputPin(
				createAction.name + ".result", 1, 1));
		this.addNode(driverActivity, createAction);
		
		forkNode = new ForkNode();
		forkNode.setName("ForkNode");
		this.addNode(driverActivity, forkNode);
		
		startAction = new StartObjectBehaviorAction();
		startAction.setName("Start(" + senderActivity.name + ")");
		startAction.setObject(this.makeInputPin(
				startAction.name + ".object", 1, 1));
		this.addNode(driverActivity, startAction);
		
		SendSignalAction sendAction = new SendSignalAction();
		sendAction.signal = signal;
		sendAction.setTarget(this.makeInputPin(
				sendAction.name + ".target", 1, 1));
		this.addNode(driverActivity, sendAction);
		
		this.addEdge(driverActivity, new ControlFlow(), 
				startAction, sendAction, null);
		
		this.addEdge(driverActivity, new ObjectFlow(), 
				createAction.result, forkNode, null);
		this.addEdge(driverActivity, new ObjectFlow(), 
				forkNode, startAction.object, null);
		this.addEdge(driverActivity, new ObjectFlow(), 
				forkNode, sendAction.target, null);
		
		this.environment.addElement(driverActivity);		
	}

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
				PrimitiveTypes.Integer));
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
				PrimitiveTypes.Integer));
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
				PrimitiveTypes.Integer));
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
				PrimitiveTypes.Integer));
		loopNodeActivity.addNode(outputNode0);

		ActivityParameterNode outputNode1 = new ActivityParameterNode();
		outputNode1.setName("Output(n)");
		outputNode1.setParameter(this.addParameter(loopNodeActivity, "n",
				ParameterDirectionKind.out,
				PrimitiveTypes.Integer));
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
				.setBehavior(IntegerFunctions.integerGreater);
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
		minusCall.setBehavior(IntegerFunctions.integerMinus);
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
		timesCall.setBehavior(IntegerFunctions.integerTimes);
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
				PrimitiveTypes.Integer));
		expansionRegionActivity.addNode(outputNode0);

		ActivityParameterNode outputNode1 = new ActivityParameterNode();
		outputNode1.setName("Output(list*10)");
		outputNode1.setParameter(this.addParameter(expansionRegionActivity,
				"list*10", ParameterDirectionKind.out,
				PrimitiveTypes.Integer));
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
		timesCall.setBehavior(IntegerFunctions.integerTimes);
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
				PrimitiveTypes.Boolean));
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
	
	public void createCentralBuffer() {
		Activity activity = new Activity();
		activity.setName("CentralBuffer");
		
		InitialNode initialNode = new InitialNode();
		initialNode.setName("InitialNode");
		activity.addNode(initialNode);
		
		StructuredActivityNode structuredNode = new StructuredActivityNode();
		structuredNode.setName("StructuredActivityNode");
		activity.addNode(structuredNode);
		
		CentralBufferNode bufferNode = new CentralBufferNode();
		bufferNode.setName("CentralBufferNode");
		structuredNode.addNode(bufferNode);
		
		ActivityParameterNode inputNode1 = new ActivityParameterNode();
		inputNode1.setName("Parameter(input1)");
		inputNode1.setParameter(this.addParameter(activity, "input1",
				ParameterDirectionKind.in, PrimitiveTypes.Integer));
		activity.addNode(inputNode1);

		ActivityParameterNode inputNode2 = new ActivityParameterNode();
		inputNode2.setName("Parameter(input2)");
		inputNode2.setParameter(this.addParameter(activity, "input2",
				ParameterDirectionKind.in, PrimitiveTypes.Integer));
		activity.addNode(inputNode2);

		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("Parameter(output)");
		Parameter parameter = this.addParameter(activity, "output",
				ParameterDirectionKind.out, null);
		parameter.setLower(0);
		parameter.setUpper(-1);
		outputNode.setParameter(parameter);
		activity.addNode(outputNode);
		
		this.addEdge(activity, new ControlFlow(), initialNode, structuredNode, null);
		this.addEdge(activity, new ObjectFlow(), inputNode1, bufferNode, null);
		this.addEdge(activity, new ObjectFlow(), inputNode2, bufferNode, null);
		this.addEdge(activity, new ObjectFlow(), bufferNode, outputNode, null);
		
		this.environment.addElement(activity);
	}
	
	public void createDataStore() {
		Activity activity = new Activity();
		activity.setName("DataStore");
		
		MergeNode mergeNode1 = new MergeNode();
		mergeNode1.setName("MergeNode1");
		activity.addNode(mergeNode1);
		
		ValueSpecificationAction initAction1 = new ValueSpecificationAction();
		initAction1.setName("Value(1)-Init-1");
		initAction1.setValue(this.createLiteralInteger("", 1));
		initAction1.setResult(this.makeOutputPin(
				initAction1.name + ".result", 1, 1));
		activity.addNode(initAction1);
		
		ValueSpecificationAction initAction2 = new ValueSpecificationAction();
		initAction2.setName("Value(1)-Init-2");
		initAction2.setValue(this.createLiteralInteger("", 1));
		initAction2.setResult(this.makeOutputPin(
				initAction1.name + ".result", 1, 1));
		activity.addNode(initAction2);
		
		ValueSpecificationAction initAction3 = new ValueSpecificationAction();
		initAction3.setName("Value(2)-Init-3");
		initAction3.setValue(this.createLiteralInteger("", 2));
		initAction3.setResult(this.makeOutputPin(
				initAction1.name + ".result", 1, 1));
		activity.addNode(initAction3);
		
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
		
		ValueSpecificationAction valueAction3 = new ValueSpecificationAction();
		valueAction3.setName("Value(3)");
		valueAction3.setValue(this.createLiteralInteger("", 3));
		valueAction3.setResult(this.makeOutputPin(
				valueAction3.name + ".result", 1, 1));
		activity.addNode(valueAction3);
		
		ForkNode forkNode1 = new ForkNode();
		forkNode1.setName("ForkNode1");
		activity.addNode(forkNode1);
		
		ForkNode forkNode2 = new ForkNode();
		forkNode2.setName("ForkNode2");
		activity.addNode(forkNode2);
		
		DataStoreNode dataStoreNode = new DataStoreNode();
		dataStoreNode.setName("DataStoreNode");
		activity.addNode(dataStoreNode);
		
		MergeNode mergeNode2 = new MergeNode();
		mergeNode2.setName("MergeNode2");
		activity.addNode(mergeNode2);
		
		DecisionNode decisionNode = new DecisionNode();
		decisionNode.setName("DecisionNode");
		decisionNode.setDecisionInputFlow(new ObjectFlow());
		activity.addNode(decisionNode);
		
		CallBehaviorAction callAction1 = new CallBehaviorAction();
		callAction1.setName("Call(Copier)-1");
		callAction1.setBehavior(this.getActivity("Copier"));
		callAction1.addArgument(this.makeInputPin(
				callAction1.name + ".argument", 1, 1));
		callAction1.addResult(this.makeOutputPin(
				callAction1.name + ".result", 1, 1));
		activity.addNode(callAction1);

		CallBehaviorAction callAction2 = new CallBehaviorAction();
		callAction2.setName("Call(Copier)-2");
		callAction2.setBehavior(this.getActivity("Copier"));
		callAction2.addArgument(this.makeInputPin(
				callAction2.name + ".argument", 1, 1));
		callAction2.addResult(this.makeOutputPin(
				callAction2.name + ".result", 1, 1));
		activity.addNode(callAction2);

		ActivityFinalNode finalNode = new ActivityFinalNode();
		finalNode.setName("FinalNode");
		activity.addNode(finalNode);
		
		ActivityParameterNode outputNode = new ActivityParameterNode();
		outputNode.setName("Parameter(output)");
		Parameter parameter = this.addParameter(activity, "output",
				ParameterDirectionKind.out, null);
		parameter.setLower(0);
		parameter.setUpper(-1);
		outputNode.setParameter(parameter);
		activity.addNode(outputNode);
		
		this.addEdge(activity, new ControlFlow(), initAction1, initAction2, null);
		this.addEdge(activity, new ControlFlow(), initAction2, initAction3, null);
		this.addEdge(activity, new ControlFlow(), initAction3, mergeNode1, null);
		this.addEdge(activity, new ControlFlow(), mergeNode1, decisionNode, null);
		this.addEdge(activity, new ControlFlow(), decisionNode, callAction1, this.createLiteralInteger("", 1));
		this.addEdge(activity, new ControlFlow(), decisionNode, callAction2, this.createLiteralInteger("", 2));
		this.addEdge(activity, new ControlFlow(), decisionNode, finalNode, this.createLiteralInteger("", 3));
		this.addEdge(activity, new ControlFlow(), callAction1, valueAction2, null);
		this.addEdge(activity, new ControlFlow(), valueAction2, mergeNode1, null);
		this.addEdge(activity, new ControlFlow(), callAction2, valueAction3, null);
		this.addEdge(activity, new ControlFlow(), valueAction3, mergeNode1, null);

		this.addEdge(activity, new ObjectFlow(), initAction1.result, dataStoreNode, null);
		this.addEdge(activity, new ObjectFlow(), initAction2.result, dataStoreNode, null);
		this.addEdge(activity, new ObjectFlow(), initAction3.result, dataStoreNode, null);
		
		this.addEdge(activity, new ObjectFlow(), valueAction1.result, mergeNode2, null);
		this.addEdge(activity, decisionNode.decisionInputFlow, mergeNode2, decisionNode, null);
		this.addEdge(activity, new ObjectFlow(), valueAction2.result, mergeNode2, null);
		this.addEdge(activity, new ObjectFlow(), dataStoreNode, callAction1.argument.get(0), null);
		this.addEdge(activity, new ObjectFlow(), callAction1.result.get(0), outputNode, null);
		this.addEdge(activity, new ObjectFlow(), dataStoreNode, callAction2.argument.get(0), null);
		this.addEdge(activity, new ObjectFlow(), callAction2.result.get(0), outputNode, null);

		this.environment.addElement(activity);
	}
	
	public void createUnmarshaller(String classifierName) {
		Activity activity = new Activity();
		activity.setName(classifierName + "_Unmarshaller");
		
		NamedElement element = this.environment.getElement(classifierName);

		if (element == null || !(element instanceof Classifier)) {
			Debug.println("[createWriterReader] " + classifierName
					+ " does not exist or is not a classifier.");
			return;
		}

		Classifier classifier = (Classifier) element;
		
		UnmarshallAction unmarshallAction = new UnmarshallAction();
		unmarshallAction.setName("Unmarshall(" + classifierName + ")");
		unmarshallAction.setUnmarshallType(classifier);
		unmarshallAction.setObject(this.makeInputPin(unmarshallAction.name + ".object", 1, 1));
		this.addNode(activity, unmarshallAction);

		CreateObjectAction createAction = new CreateObjectAction();
		createAction.setName("Create(" + classifierName + ")");
		createAction.setClassifier(classifier);
		createAction.setResult(this.makeOutputPin(createAction.name + ".result", 1, 1));
		this.addNode(activity, createAction);
		
		OutputPin object = createAction.result;
		
		for (Property attribute: classifier.attribute) {
			int upper = attribute.multiplicityElement.upper.naturalValue;
			if (upper < 0) {
				upper = 1;
			}
			for (int i = 0; i < upper; i++) {
				ValueSpecification valueSpecification = environment.makeValue(
						(Classifier) (attribute.typedElement.type)).specify();
				ValueSpecificationAction valueAction = new ValueSpecificationAction();
				valueAction.setName(attribute.name + "[" + (i+1) + "]." + attribute.typedElement.type.name + "Value");
				valueAction.setValue(valueSpecification);
				valueAction.setResult(this.makeOutputPin(valueAction.name + ".result", 1, 1));
				this.addNode(activity, valueAction);
				
				AddStructuralFeatureValueAction writeAction = new AddStructuralFeatureValueAction();
				writeAction.setName("Write(" + attribute.name + "[" + (i+1) + "])");
				writeAction.setStructuralFeature(attribute);
				writeAction.setObject(this.makeInputPin(writeAction.name + ".object", 1, 1));
				writeAction.setValue(this.makeInputPin(writeAction.name + ".value", 1, 1));
				writeAction.setResult(this.makeOutputPin(writeAction.name + ".result", 1, 1));
				this.addNode(activity, writeAction);
				
				this.addEdge(activity, new ObjectFlow(), object, writeAction.object, null);
				this.addEdge(activity, new ObjectFlow(), valueAction.result, writeAction.value, null);
				object = writeAction.result;
			}
			
			ActivityParameterNode outputNode = new ActivityParameterNode();
			outputNode.setName("Parameter(" + attribute.name + ")");
			Parameter parameter = this.addParameter(activity, attribute.name, ParameterDirectionKind.out, null);
			parameter.setLower(0);
			parameter.setUpper(-1);
			outputNode.setParameter(parameter);
			this.addNode(activity, outputNode);
			
			OutputPin result = this.makeOutputPin(unmarshallAction.name + ".result(" + attribute.name + ")", 0, -1);
			unmarshallAction.addResult(result);
			
			this.addEdge(activity, new ObjectFlow(), result, outputNode, null);
			
		}
		
		this.addEdge(activity, new ObjectFlow(), object, unmarshallAction.object, null);
		
		this.environment.addElement(activity);
	}

} // ActivityFactory
