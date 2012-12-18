
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

package fUML.Semantics.Actions.IntermediateActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;

import fUML.Semantics.*;
import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;
import fUML.Semantics.Activities.IntermediateActivities.*;
import fUML.Semantics.Actions.BasicActions.*;
import fUML.Semantics.Loci.*;

public class ClearAssociationActionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

	public void doAction() {
		// Get the extent, at the current execution locus, of the given
		// association.
		// Read the object input pin. Destroy all links in which the object
		// participates.

		ClearAssociationAction action = (ClearAssociationAction) (this.node);

		ExtensionalValueList extent = this.getExecutionLocus().getExtent(
				action.association);
		Value objectValue = this.takeTokens(action.object).getValue(0);

		for (int i = 0; i < extent.size(); i++) {
			Link link = (Link) (extent.getValue(i));
			if (this.valueParticipatesInLink(objectValue, link)) {
				link.destroy();
			}
		}
	} // doAction

} // ClearAssociationActionActivation
