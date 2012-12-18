
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

package fUML.Semantics.Activities.ExtraStructuredActivities;

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

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.CommonBehaviors.Communications.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Activities.CompleteStructuredActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class ExpansionNodeActivation extends
		fUML.Semantics.Activities.IntermediateActivities.ObjectNodeActivation {

	public void fire(
			fUML.Semantics.Activities.IntermediateActivities.TokenList incomingTokens) {
		// Take tokens from all incoming edges.

		Debug.println("[fire] Expansion node " + this.node.name + "...");

		this.addTokens(incomingTokens);

	} // fire

	public void receiveOffer() {
		// Forward the offer on to the expansion region.

		this.getExpansionRegionActivation().receiveOffer();
	} // receiveOffer

	public boolean isReady() {
		// An expansion node is always fired by its expansion region.

		return false;
	} // isReady

	public fUML.Semantics.Activities.ExtraStructuredActivities.ExpansionRegionActivation getExpansionRegionActivation() {
		// Return the expansion region activation corresponding to this
		// expansion node, in the context of the activity node activation group
		// this expansion node activation is in.

		ExpansionNode node = (ExpansionNode) (this.node);

		ExpansionRegion region = node.regionAsInput;
		if (region == null) {
			region = node.regionAsOutput;
		}

		return (ExpansionRegionActivation) (this.group
				.getNodeActivation(region));

	} // getExpansionRegionActivation

} // ExpansionNodeActivation
