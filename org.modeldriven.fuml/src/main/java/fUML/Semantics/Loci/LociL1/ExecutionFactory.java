
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

package fUML.Semantics.Loci.LociL1;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;

public abstract class ExecutionFactory extends org.modeldriven.fuml.FumlObject {

	public fUML.Semantics.Loci.LociL1.Locus locus = null;
	public fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecutionList primitiveBehaviorPrototypes = new fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecutionList();
	public fUML.Syntax.Classes.Kernel.PrimitiveTypeList builtInTypes = new fUML.Syntax.Classes.Kernel.PrimitiveTypeList();
	public fUML.Semantics.Loci.LociL1.SemanticStrategyList strategies = new fUML.Semantics.Loci.LociL1.SemanticStrategyList();

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution createExecution(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior behavior,
			fUML.Semantics.Classes.Kernel.Object_ context) {
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

	public fUML.Semantics.Classes.Kernel.Evaluation createEvaluation(
			fUML.Syntax.Classes.Kernel.ValueSpecification specification) {
		// Create an evaluation object for a given value specification.
		// The evaluation will take place at the locus of the factory.

		Evaluation evaluation = (Evaluation) this
				.instantiateVisitor(specification);

		evaluation.specification = specification;
		evaluation.locus = this.locus;

		return evaluation;

	} // createEvaluation

	public abstract fUML.Semantics.Loci.LociL1.SemanticVisitor instantiateVisitor(
			fUML.Syntax.Classes.Kernel.Element element);

	public fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution instantiateOpaqueBehaviorExecution(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior behavior) {
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

		if (execution == null) {
			Debug
					.println("[instantiateOpaqueExecution] No prototype execution found for "
							+ behavior.name + ".");
		}

		return execution;
	} // instantiateOpaqueBehaviorExecution

	public void addPrimitiveBehaviorPrototype(
			fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution execution) {
		// Add an opaque behavior execution to use as a prototype for
		// instantiating the corresponding primitive opaque behavior.
		// Precondition: No primitive behavior prototype for the type of the
		// given execution should already exist.

		this.primitiveBehaviorPrototypes.addValue(execution);
	} // addPrimitiveBehaviorPrototype

	public void addBuiltInType(fUML.Syntax.Classes.Kernel.PrimitiveType type) {
		// Add the given primitive type as a built-in type.
		// Precondition: No built-in type with the same name should already
		// exist.

		this.builtInTypes.addValue(type);
	} // addBuiltInType

	public fUML.Syntax.Classes.Kernel.PrimitiveType getBuiltInType(String name) {
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

	public void setStrategy(fUML.Semantics.Loci.LociL1.SemanticStrategy strategy) {
		// Set the strategy for a semantic variation point. Any existing
		// strategy for the same SVP is replaced.

		int i = this.getStrategyIndex(strategy.getName());

		if (i <= this.strategies.size()) {
			this.strategies.removeValue(i - 1);
		}

		this.strategies.addValue(strategy);
	} // setStrategy

	public fUML.Semantics.Loci.LociL1.SemanticStrategy getStrategy(String name) {
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
