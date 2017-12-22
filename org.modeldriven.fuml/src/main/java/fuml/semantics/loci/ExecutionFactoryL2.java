
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

package fuml.semantics.loci;

import fuml.semantics.actions.AddStructuralFeatureValueActionActivation;
import fuml.semantics.actions.CallBehaviorActionActivation;
import fuml.semantics.actions.CallOperationActionActivation;
import fuml.semantics.actions.ClearAssociationActionActivation;
import fuml.semantics.actions.ClearStructuralFeatureActionActivation;
import fuml.semantics.actions.CreateLinkActionActivation;
import fuml.semantics.actions.CreateObjectActionActivation;
import fuml.semantics.actions.DestroyLinkActionActivation;
import fuml.semantics.actions.DestroyObjectActionActivation;
import fuml.semantics.actions.InputPinActivation;
import fuml.semantics.actions.OutputPinActivation;
import fuml.semantics.actions.ReadLinkActionActivation;
import fuml.semantics.actions.ReadSelfActionActivation;
import fuml.semantics.actions.ReadStructuralFeatureActionActivation;
import fuml.semantics.actions.RemoveStructuralFeatureValueActionActivation;
import fuml.semantics.actions.SendSignalActionActivation;
import fuml.semantics.actions.TestIdentityActionActivation;
import fuml.semantics.actions.ValueSpecificationActionActivation;
import fuml.semantics.activities.ActivityExecution;
import fuml.semantics.activities.ActivityFinalNodeActivation;
import fuml.semantics.activities.ActivityParameterNodeActivation;
import fuml.semantics.activities.CentralBufferNodeActivation;
import fuml.semantics.activities.DecisionNodeActivation;
import fuml.semantics.activities.FlowFinalNodeActivation;
import fuml.semantics.activities.ForkNodeActivation;
import fuml.semantics.activities.InitialNodeActivation;
import fuml.semantics.activities.JoinNodeActivation;
import fuml.semantics.activities.MergeNodeActivation;
import fuml.syntax.actions.AddStructuralFeatureValueAction;
import fuml.syntax.actions.CallBehaviorAction;
import fuml.syntax.actions.CallOperationAction;
import fuml.syntax.actions.ClearAssociationAction;
import fuml.syntax.actions.ClearStructuralFeatureAction;
import fuml.syntax.actions.CreateLinkAction;
import fuml.syntax.actions.CreateObjectAction;
import fuml.syntax.actions.DestroyLinkAction;
import fuml.syntax.actions.DestroyObjectAction;
import fuml.syntax.actions.InputPin;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.ReadLinkAction;
import fuml.syntax.actions.ReadSelfAction;
import fuml.syntax.actions.ReadStructuralFeatureAction;
import fuml.syntax.actions.RemoveStructuralFeatureValueAction;
import fuml.syntax.actions.SendSignalAction;
import fuml.syntax.actions.TestIdentityAction;
import fuml.syntax.actions.ValueSpecificationAction;
import fuml.syntax.activities.Activity;
import fuml.syntax.activities.ActivityFinalNode;
import fuml.syntax.activities.ActivityParameterNode;
import fuml.syntax.activities.CentralBufferNode;
import fuml.syntax.activities.DecisionNode;
import fuml.syntax.activities.FlowFinalNode;
import fuml.syntax.activities.ForkNode;
import fuml.syntax.activities.InitialNode;
import fuml.syntax.activities.JoinNode;
import fuml.syntax.activities.MergeNode;

public class ExecutionFactoryL2 extends
		fuml.semantics.loci.ExecutionFactoryL1 {

	public fuml.semantics.loci.SemanticVisitor instantiateVisitor(
			fuml.syntax.commonstructure.Element element) {
		// Instantiate a visitor object for the given element (at Conformance
		// Level 2)

		SemanticVisitor visitor = null;

		if (element instanceof Activity) {
			visitor = new ActivityExecution();
		}

		else if (element instanceof ActivityParameterNode) {
			visitor = new ActivityParameterNodeActivation();
		}
		
		else if (element instanceof CentralBufferNode) {
			visitor = new CentralBufferNodeActivation();
		}

		else if (element instanceof InitialNode) {
			visitor = new InitialNodeActivation();
		}

		else if (element instanceof ActivityFinalNode) {
			visitor = new ActivityFinalNodeActivation();
		}

		else if (element instanceof FlowFinalNode) {
			visitor = new FlowFinalNodeActivation();
		}

		else if (element instanceof JoinNode) {
			visitor = new JoinNodeActivation();
		}

		else if (element instanceof MergeNode) {
			visitor = new MergeNodeActivation();
		}

		else if (element instanceof ForkNode) {
			visitor = new ForkNodeActivation();
		}

		else if (element instanceof DecisionNode) {
			visitor = new DecisionNodeActivation();
		}

		else if (element instanceof InputPin) {
			visitor = new InputPinActivation();
		}

		else if (element instanceof OutputPin) {
			visitor = new OutputPinActivation();
		}

		else if (element instanceof CallBehaviorAction) {
			visitor = new CallBehaviorActionActivation();
		}

		else if (element instanceof CallOperationAction) {
			visitor = new CallOperationActionActivation();
		}

		else if (element instanceof SendSignalAction) {
			visitor = new SendSignalActionActivation();
		}

		else if (element instanceof ReadSelfAction) {
			visitor = new ReadSelfActionActivation();
		}

		else if (element instanceof TestIdentityAction) {
			visitor = new TestIdentityActionActivation();
		}

		else if (element instanceof ValueSpecificationAction) {
			visitor = new ValueSpecificationActionActivation();
		}

		else if (element instanceof CreateObjectAction) {
			visitor = new CreateObjectActionActivation();
		}

		else if (element instanceof DestroyObjectAction) {
			visitor = new DestroyObjectActionActivation();
		}

		else if (element instanceof ReadStructuralFeatureAction) {
			visitor = new ReadStructuralFeatureActionActivation();
		}

		else if (element instanceof ClearStructuralFeatureAction) {
			visitor = new ClearStructuralFeatureActionActivation();
		}

		else if (element instanceof AddStructuralFeatureValueAction) {
			visitor = new AddStructuralFeatureValueActionActivation();
		}

		else if (element instanceof RemoveStructuralFeatureValueAction) {
			visitor = new RemoveStructuralFeatureValueActionActivation();
		}

		else if (element instanceof ReadLinkAction) {
			visitor = new ReadLinkActionActivation();
		}

		else if (element instanceof ClearAssociationAction) {
			visitor = new ClearAssociationActionActivation();
		}

		else if (element instanceof CreateLinkAction) {
			visitor = new CreateLinkActionActivation();
		}

		else if (element instanceof DestroyLinkAction) {
			visitor = new DestroyLinkActionActivation();
		}

		else {
			visitor = super.instantiateVisitor(element);
		}

		return visitor;

	} // instantiateVisitor

} // ExecutionFactoryL2
