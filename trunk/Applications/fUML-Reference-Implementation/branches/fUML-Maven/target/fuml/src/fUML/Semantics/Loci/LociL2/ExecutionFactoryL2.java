
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

package fUML.Semantics.Loci.LociL2;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Actions.IntermediateActions.*;
import fUML.Semantics.Loci.LociL1.*;

public class ExecutionFactoryL2 extends
		fUML.Semantics.Loci.LociL1.ExecutionFactoryL1 {

	public fUML.Semantics.Loci.LociL1.SemanticVisitor instantiateVisitor(
			fUML.Syntax.Classes.Kernel.Element element) {
		// Instantiate a visitor object for the given element (at Conformance
		// Level 2)

		SemanticVisitor visitor = null;

		if (element instanceof Activity) {
			visitor = new ActivityExecution();
		}

		else if (element instanceof ActivityParameterNode) {
			visitor = new ActivityParameterNodeActivation();
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
