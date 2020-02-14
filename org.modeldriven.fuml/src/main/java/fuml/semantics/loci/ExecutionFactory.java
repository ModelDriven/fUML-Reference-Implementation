
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications 
 * copyright 2009-2018 Data Access Technologies, Inc.
 * copyright 2019 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fuml.semantics.loci;

import fuml.Debug;
import fuml.semantics.actions.AcceptCallActionActivation;
import fuml.semantics.actions.AcceptEventActionActivation;
import fuml.semantics.actions.AddStructuralFeatureValueActionActivation;
import fuml.semantics.actions.CallBehaviorActionActivation;
import fuml.semantics.actions.CallOperationActionActivation;
import fuml.semantics.actions.ClearAssociationActionActivation;
import fuml.semantics.actions.ClearStructuralFeatureActionActivation;
import fuml.semantics.actions.ConditionalNodeActivation;
import fuml.semantics.actions.CreateLinkActionActivation;
import fuml.semantics.actions.CreateObjectActionActivation;
import fuml.semantics.actions.DestroyLinkActionActivation;
import fuml.semantics.actions.DestroyObjectActionActivation;
import fuml.semantics.actions.ExpansionNodeActivation;
import fuml.semantics.actions.ExpansionRegionActivation;
import fuml.semantics.actions.InputPinActivation;
import fuml.semantics.actions.LoopNodeActivation;
import fuml.semantics.actions.OutputPinActivation;
import fuml.semantics.actions.RaiseExceptionActionActivation;
import fuml.semantics.actions.ReadExtentActionActivation;
import fuml.semantics.actions.ReadIsClassifiedObjectActionActivation;
import fuml.semantics.actions.ReadLinkActionActivation;
import fuml.semantics.actions.ReadSelfActionActivation;
import fuml.semantics.actions.ReadStructuralFeatureActionActivation;
import fuml.semantics.actions.ReclassifyObjectActionActivation;
import fuml.semantics.actions.ReduceActionActivation;
import fuml.semantics.actions.RemoveStructuralFeatureValueActionActivation;
import fuml.semantics.actions.ReplyActionActivation;
import fuml.semantics.actions.SendSignalActionActivation;
import fuml.semantics.actions.StartClassifierBehaviorActionActivation;
import fuml.semantics.actions.StartObjectBehaviorActionActivation;
import fuml.semantics.actions.StructuredActivityNodeActivation;
import fuml.semantics.actions.TestIdentityActionActivation;
import fuml.semantics.actions.UnmarshallActionActivation;
import fuml.semantics.actions.ValueSpecificationActionActivation;
import fuml.semantics.activities.ActivityExecution;
import fuml.semantics.activities.ActivityFinalNodeActivation;
import fuml.semantics.activities.ActivityParameterNodeActivation;
import fuml.semantics.activities.CentralBufferNodeActivation;
import fuml.semantics.activities.DataStoreNodeActivation;
import fuml.semantics.activities.DecisionNodeActivation;
import fuml.semantics.activities.FlowFinalNodeActivation;
import fuml.semantics.activities.ForkNodeActivation;
import fuml.semantics.activities.InitialNodeActivation;
import fuml.semantics.activities.JoinNodeActivation;
import fuml.semantics.activities.MergeNodeActivation;
import fuml.semantics.classification.InstanceValueEvaluation;
import fuml.semantics.commonbehavior.CallEventBehavior;
import fuml.semantics.commonbehavior.CallEventExecution;
import fuml.semantics.commonbehavior.Execution;
import fuml.semantics.commonbehavior.OpaqueBehaviorExecution;
import fuml.semantics.values.Evaluation;
import fuml.semantics.values.LiteralBooleanEvaluation;
import fuml.semantics.values.LiteralIntegerEvaluation;
import fuml.semantics.values.LiteralNullEvaluation;
import fuml.semantics.values.LiteralRealEvaluation;
import fuml.semantics.values.LiteralStringEvaluation;
import fuml.semantics.values.LiteralUnlimitedNaturalEvaluation;
import fuml.syntax.actions.AcceptCallAction;
import fuml.syntax.actions.AcceptEventAction;
import fuml.syntax.actions.AddStructuralFeatureValueAction;
import fuml.syntax.actions.CallBehaviorAction;
import fuml.syntax.actions.CallOperationAction;
import fuml.syntax.actions.ClearAssociationAction;
import fuml.syntax.actions.ClearStructuralFeatureAction;
import fuml.syntax.actions.ConditionalNode;
import fuml.syntax.actions.CreateLinkAction;
import fuml.syntax.actions.CreateObjectAction;
import fuml.syntax.actions.DestroyLinkAction;
import fuml.syntax.actions.DestroyObjectAction;
import fuml.syntax.actions.ExpansionNode;
import fuml.syntax.actions.ExpansionRegion;
import fuml.syntax.actions.InputPin;
import fuml.syntax.actions.LoopNode;
import fuml.syntax.actions.OutputPin;
import fuml.syntax.actions.RaiseExceptionAction;
import fuml.syntax.actions.ReadExtentAction;
import fuml.syntax.actions.ReadIsClassifiedObjectAction;
import fuml.syntax.actions.ReadLinkAction;
import fuml.syntax.actions.ReadSelfAction;
import fuml.syntax.actions.ReadStructuralFeatureAction;
import fuml.syntax.actions.ReclassifyObjectAction;
import fuml.syntax.actions.ReduceAction;
import fuml.syntax.actions.RemoveStructuralFeatureValueAction;
import fuml.syntax.actions.ReplyAction;
import fuml.syntax.actions.SendSignalAction;
import fuml.syntax.actions.StartClassifierBehaviorAction;
import fuml.syntax.actions.StartObjectBehaviorAction;
import fuml.syntax.actions.StructuredActivityNode;
import fuml.syntax.actions.TestIdentityAction;
import fuml.syntax.actions.UnmarshallAction;
import fuml.syntax.actions.ValueSpecificationAction;
import fuml.syntax.activities.Activity;
import fuml.syntax.activities.ActivityFinalNode;
import fuml.syntax.activities.ActivityParameterNode;
import fuml.syntax.activities.CentralBufferNode;
import fuml.syntax.activities.DataStoreNode;
import fuml.syntax.activities.DecisionNode;
import fuml.syntax.activities.FlowFinalNode;
import fuml.syntax.activities.ForkNode;
import fuml.syntax.activities.InitialNode;
import fuml.syntax.activities.JoinNode;
import fuml.syntax.activities.MergeNode;
import fuml.syntax.classification.InstanceValue;
import fuml.syntax.commonbehavior.OpaqueBehavior;
import fuml.syntax.simpleclassifiers.PrimitiveType;
import fuml.syntax.values.LiteralBoolean;
import fuml.syntax.values.LiteralInteger;
import fuml.syntax.values.LiteralNull;
import fuml.syntax.values.LiteralReal;
import fuml.syntax.values.LiteralString;
import fuml.syntax.values.LiteralUnlimitedNatural;

public class ExecutionFactory extends org.modeldriven.fuml.FumlObject {

	public fuml.semantics.loci.Locus locus = null;
	public fuml.semantics.commonbehavior.OpaqueBehaviorExecutionList primitiveBehaviorPrototypes = new fuml.semantics.commonbehavior.OpaqueBehaviorExecutionList();
	public fuml.syntax.simpleclassifiers.PrimitiveTypeList builtInTypes = new fuml.syntax.simpleclassifiers.PrimitiveTypeList();
	public fuml.semantics.loci.SemanticStrategyList strategies = new fuml.semantics.loci.SemanticStrategyList();

	public fuml.semantics.commonbehavior.Execution createExecution(
			fuml.syntax.commonbehavior.Behavior behavior,
			fuml.semantics.structuredclassifiers.Object_ context) {
		// Create an execution object for a given behavior.
		// The execution will take place at the locus of the factory in the
		// given context.
		// If the context is empty, the execution is assumed to provide its own
		// context.

		Execution execution;

		if (behavior instanceof OpaqueBehavior) {
			execution = this
					.instantiateOpaqueBehaviorExecution((OpaqueBehavior) behavior);
		} else {
			execution = (Execution) this.instantiateVisitor(behavior);
			execution.types.addValue(behavior);
			execution.createFeatureValues();
		}

		this.locus.add(execution);

		if (context == null) {
			execution.context = execution;
		} else {
			execution.context = context;
		}

		return execution;
	} // createExecution

	public fuml.semantics.values.Evaluation createEvaluation(
			fuml.syntax.values.ValueSpecification specification) {
		// Create an evaluation object for a given value specification.
		// The evaluation will take place at the locus of the factory.

		Evaluation evaluation = (Evaluation) this
				.instantiateVisitor(specification);

		evaluation.specification = specification;
		evaluation.locus = this.locus;

		return evaluation;

	} // createEvaluation

	public fuml.semantics.loci.SemanticVisitor instantiateVisitor(
			fuml.syntax.commonstructure.Element element) {
		// Instantiate a visitor object for the given element.
		
		SemanticVisitor visitor = null;
		
		// Formerly Level L1
		
		if (element instanceof LiteralBoolean) {
			visitor = new LiteralBooleanEvaluation();
		}
		
		else if (element instanceof LiteralString) {
			visitor = new LiteralStringEvaluation();
		}
		
		else if (element instanceof LiteralNull) {
			visitor = new LiteralNullEvaluation();
		}
		
		else if (element instanceof InstanceValue) {
			visitor = new InstanceValueEvaluation();
		}
		
		else if (element instanceof LiteralUnlimitedNatural) {
			visitor = new LiteralUnlimitedNaturalEvaluation();
		}
		
		else if (element instanceof LiteralInteger) {
			visitor = new LiteralIntegerEvaluation();
		}
		
		else if (element instanceof LiteralReal) {
			visitor = new LiteralRealEvaluation();
		}
		
		else if (element instanceof CallEventBehavior) {
			visitor = new CallEventExecution();
			
		// Formerly Level L2
		
		} else if (element instanceof Activity) {
			visitor = new ActivityExecution();
		}
		
		else if (element instanceof ActivityParameterNode) {
			visitor = new ActivityParameterNodeActivation();
		}
		
		else if (element instanceof CentralBufferNode &
				!(element instanceof DataStoreNode)) {
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
		
		// Formerly Level L3
		
		else if (element instanceof DataStoreNode) {
			visitor = new DataStoreNodeActivation();
		} 
		
		else if (element instanceof ConditionalNode) {
			visitor = new ConditionalNodeActivation();
		}
		
		else if (element instanceof LoopNode) {
			visitor = new LoopNodeActivation();
		}
		
		else if (element instanceof ExpansionRegion) {
			visitor = new ExpansionRegionActivation();
		}
		
		// Note: Since ConditionalNode, LoopNode and ExpansionRegion are
		// subclasses of StructuredActivityNode, element must be tested 
		// against the three subclasses before the superclass.
		else if (element instanceof StructuredActivityNode) {
			visitor = new StructuredActivityNodeActivation();
		}
		
		else if (element instanceof ExpansionNode) {
			visitor = new ExpansionNodeActivation();
		}
		
		else if (element instanceof ReadExtentAction) {
			visitor = new ReadExtentActionActivation();
		}
		
		else if (element instanceof ReadIsClassifiedObjectAction) {
			visitor = new ReadIsClassifiedObjectActionActivation();
		}
		
		else if (element instanceof ReclassifyObjectAction) {
			visitor = new ReclassifyObjectActionActivation();
		}
		
		else if (element instanceof StartObjectBehaviorAction) {
			visitor = new StartObjectBehaviorActionActivation();
		}
		
		else if (element instanceof StartClassifierBehaviorAction) {
			visitor = new StartClassifierBehaviorActionActivation();
		}
		
		// Note: Since AcceptCallAction is a subclass of AcceptEventAction,
		// element must be tested against AcceptCallAction before
		// AcceptEventAction.
		else if (element instanceof AcceptCallAction) {
			visitor = new AcceptCallActionActivation();
		}
		
		else if (element instanceof AcceptEventAction) {
			visitor = new AcceptEventActionActivation();
		}
		
		else if (element instanceof ReplyAction) {
			visitor = new ReplyActionActivation();
		}
		
		else if (element instanceof ReduceAction) {
			visitor = new ReduceActionActivation();
		}
		
		else if(element instanceof RaiseExceptionAction) {
			visitor = new RaiseExceptionActionActivation();
		}
		
		else if (element instanceof UnmarshallAction) {
			visitor = new UnmarshallActionActivation();
		}
		
		return visitor;
	}

	public fuml.semantics.commonbehavior.OpaqueBehaviorExecution instantiateOpaqueBehaviorExecution(
			fuml.syntax.commonbehavior.OpaqueBehavior behavior) {
		// Return a copy of the prototype for the primitive behavior execution
		// of the given opaque behavior.

		OpaqueBehaviorExecution execution = null;
		int i = 1;
		while (execution == null & i <= this.primitiveBehaviorPrototypes.size()) {
			// Debug.println("[instantiateOpaqueExecution] Checking " +
			// this.primitiveBehaviorPrototypes.getValue(i).objectId() + "...");
			OpaqueBehaviorExecution prototype = this.primitiveBehaviorPrototypes
					.getValue(i - 1);
			if (prototype.getBehavior() == behavior) {
				execution = (OpaqueBehaviorExecution) (prototype.copy());
			}
			i = i + 1;
		}

		Debug.println(execution == null, 
				"[instantiateOpaqueExecution] No prototype execution found for " + behavior.name + ".");

		return execution;
	} // instantiateOpaqueBehaviorExecution

	public void addPrimitiveBehaviorPrototype(
			fuml.semantics.commonbehavior.OpaqueBehaviorExecution execution) {
		// Add an opaque behavior execution to use as a prototype for
		// instantiating the corresponding primitive opaque behavior.
		// Precondition: No primitive behavior prototype for the type of the
		// given execution should already exist.

		this.primitiveBehaviorPrototypes.addValue(execution);
	} // addPrimitiveBehaviorPrototype

	public void addBuiltInType(fuml.syntax.simpleclassifiers.PrimitiveType type) {
		// Add the given primitive type as a built-in type.
		// Precondition: No built-in type with the same name should already
		// exist.

		this.builtInTypes.addValue(type);
	} // addBuiltInType

	public fuml.syntax.simpleclassifiers.PrimitiveType getBuiltInType(String name) {
		// Return the built-in type with the given name.

		PrimitiveType type = null;
		int i = 1;
		while (type == null & i <= this.builtInTypes.size()) {
			PrimitiveType primitiveType = this.builtInTypes.getValue(i - 1);
			if (primitiveType.name.equals(name)) {
				type = primitiveType;
			}
			i = i + 1;
		}

		return type;
	} // getBuiltInType

	public void setStrategy(fuml.semantics.loci.SemanticStrategy strategy) {
		// Set the strategy for a semantic variation point. Any existing
		// strategy for the same SVP is replaced.

		int i = this.getStrategyIndex(strategy.getName());

		if (i <= this.strategies.size()) {
			this.strategies.removeValue(i - 1);
		}

		this.strategies.addValue(strategy);
	} // setStrategy

	public fuml.semantics.loci.SemanticStrategy getStrategy(String name) {
		// Get the strategy with the given name.

		int i = this.getStrategyIndex(name);

		SemanticStrategy strategy = null;
		if (i <= this.strategies.size()) {
			strategy = this.strategies.getValue(i - 1);
		}

		return strategy;
	} // getStrategy

	public int getStrategyIndex(String name) {
		// Get the index of the strategy with the given name.
		// If there is no such strategy, return the size of the strategies list.

		SemanticStrategyList strategies = this.strategies;

		int i = 1;
		boolean unmatched = true;
		while (unmatched & (i <= strategies.size())) {
			if (strategies.getValue(i - 1).getName().equals(name)) {
				unmatched = false;
			} else {
				i = i + 1;
			}
		}

		return i;

	} // getStrategyIndex

} // ExecutionFactory
