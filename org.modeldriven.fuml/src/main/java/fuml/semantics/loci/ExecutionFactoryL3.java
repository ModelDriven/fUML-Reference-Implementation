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

import fuml.semantics.actions.AcceptCallActionActivation;
import fuml.semantics.actions.AcceptEventActionActivation;
import fuml.semantics.actions.ConditionalNodeActivation;
import fuml.semantics.actions.ExpansionNodeActivation;
import fuml.semantics.actions.ExpansionRegionActivation;
import fuml.semantics.actions.LoopNodeActivation;
import fuml.semantics.actions.ReadExtentActionActivation;
import fuml.semantics.actions.ReadIsClassifiedObjectActionActivation;
import fuml.semantics.actions.ReclassifyObjectActionActivation;
import fuml.semantics.actions.ReduceActionActivation;
import fuml.semantics.actions.ReplyActionActivation;
import fuml.semantics.actions.StartClassifierBehaviorActionActivation;
import fuml.semantics.actions.StartObjectBehaviorActionActivation;
import fuml.semantics.actions.StructuredActivityNodeActivation;
import fuml.semantics.activities.DataStoreNodeActivation;
import fuml.syntax.actions.AcceptCallAction;
import fuml.syntax.actions.AcceptEventAction;
import fuml.syntax.actions.ConditionalNode;
import fuml.syntax.actions.ExpansionNode;
import fuml.syntax.actions.ExpansionRegion;
import fuml.syntax.actions.LoopNode;
import fuml.syntax.actions.ReadExtentAction;
import fuml.syntax.actions.ReadIsClassifiedObjectAction;
import fuml.syntax.actions.ReclassifyObjectAction;
import fuml.syntax.actions.ReduceAction;
import fuml.syntax.actions.ReplyAction;
import fuml.syntax.actions.StartClassifierBehaviorAction;
import fuml.syntax.actions.StartObjectBehaviorAction;
import fuml.syntax.actions.StructuredActivityNode;
import fuml.syntax.activities.DataStoreNode;

public class ExecutionFactoryL3 extends
		fuml.semantics.loci.ExecutionFactoryL2 {

	public fuml.semantics.loci.SemanticVisitor instantiateVisitor(
			fuml.syntax.commonstructure.Element element) {
		// Instantiate a visitor object for the given element (at Conformance
		// Level 3)

		SemanticVisitor visitor = null;
		
		if (element instanceof DataStoreNode) {
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

		else {
			visitor = super.instantiateVisitor(element);
		}

		return visitor;

	} // instantiateVisitor

} // ExecutionFactoryL3
