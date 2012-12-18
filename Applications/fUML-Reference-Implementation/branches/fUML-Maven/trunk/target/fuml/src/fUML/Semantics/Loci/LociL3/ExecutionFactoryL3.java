
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

package fUML.Semantics.Loci.LociL3;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Activities.ExtraStructuredActivities.*;
import fUML.Syntax.Actions.CompleteActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Activities.CompleteStructuredActivities.*;
import fUML.Semantics.Activities.ExtraStructuredActivities.*;
import fUML.Semantics.Actions.CompleteActions.*;
import fUML.Semantics.Loci.LociL1.*;
import fUML.Semantics.Loci.LociL2.*;

public class ExecutionFactoryL3 extends
		fUML.Semantics.Loci.LociL2.ExecutionFactoryL2 {

	public fUML.Semantics.Loci.LociL1.SemanticVisitor instantiateVisitor(
			fUML.Syntax.Classes.Kernel.Element element) {
		// Instantiate a visitor object for the given element (at Conformance
		// Level 3)

		SemanticVisitor visitor = null;

		if (element instanceof ConditionalNode) {
			visitor = new ConditionalNodeActivation();
		}

		else if (element instanceof LoopNode) {
			visitor = new LoopNodeActivation();
		}

		else if (element instanceof ExpansionRegion) {
			visitor = new ExpansionRegionActivation();
		}

		// Note: Since ConditionalNode, LoopNode and ExpansionRegion are
		// subclasses of
		// StructuredActivityNode, element must be tested against the three
		// subclasses before
		// the superclass
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

		else if (element instanceof AcceptEventAction) {
			visitor = new AcceptEventActionActivation();
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
