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

package fUML.Semantics.Loci.LociL3;

import fUML.Syntax.Activities.CompleteActivities.DataStoreNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Activities.ExtraStructuredActivities.*;
import fUML.Syntax.Actions.CompleteActions.*;
import fUML.Semantics.Activities.CompleteActivities.DataStoreNodeActivation;
import fUML.Semantics.Activities.CompleteStructuredActivities.*;
import fUML.Semantics.Activities.ExtraStructuredActivities.*;
import fUML.Semantics.Actions.CompleteActions.*;
import fUML.Semantics.Loci.LociL1.*;

public class ExecutionFactoryL3 extends
		fUML.Semantics.Loci.LociL2.ExecutionFactoryL2 {

	public fUML.Semantics.Loci.LociL1.SemanticVisitor instantiateVisitor(
			fUML.Syntax.Classes.Kernel.Element element) {
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
