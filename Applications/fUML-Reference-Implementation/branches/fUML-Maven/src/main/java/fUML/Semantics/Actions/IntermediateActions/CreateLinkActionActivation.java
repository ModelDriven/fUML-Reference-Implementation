
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

public class CreateLinkActionActivation extends
		fUML.Semantics.Actions.IntermediateActions.WriteLinkActionActivation {

	public void doAction() {
		// If the association has any unique ends, then destroy an existing link
		// that matches all ends of the link being created.
		// Get the extent at the current execution locus of the association for
		// which a link is being created.
		// Destroy all links that have a value for any end for which
		// isReplaceAll is true.
		// Create a new link for the association, at the current locus, with the
		// given end data values,
		// inserted at the given insertAt position (for ordered ends).

		CreateLinkAction action = (CreateLinkAction) (this.node);
		LinkEndCreationDataList endDataList = action.endData;

		Association linkAssociation = this.getAssociation();
		ExtensionalValueList extent = this.getExecutionLocus().getExtent(
				linkAssociation);

		boolean unique = false;
		for (int i = 0; i < endDataList.size(); i++) {
			if (endDataList.getValue(i).end.multiplicityElement.isUnique) {
				unique = true;
			}
		}

		for (int i = 0; i < extent.size(); i++) {
			ExtensionalValue value = extent.getValue(i);
			Link link = (Link) value;

			boolean match = true;
			boolean destroy = false;
			int j = 1;
			while (j <= endDataList.size()) {
				LinkEndCreationData endData = endDataList.getValue(j - 1);
				if (this.endMatchesEndData(link, endData)) {
					if (endData.isReplaceAll) {
						destroy = true;
					}
				} else {
					match = false;
				}
				j = j + 1;
			}
			if (destroy | unique & match) {
				link.destroy();
			}
		}

		Link newLink = new Link();
		newLink.type = linkAssociation;

		for (int i = 0; i < endDataList.size(); i++) {
			LinkEndCreationData endData = endDataList.getValue(i);

			int insertAt = 0;
			if (endData.insertAt != null) {
				insertAt = ((UnlimitedNaturalValue) (this
						.takeTokens(endData.insertAt).getValue(0))).value.naturalValue;
			}

			newLink.setFeatureValue(endData.end,
					this.takeTokens(endData.value), insertAt);
		}

		newLink.addTo(this.getExecutionLocus());
	} // doAction

} // CreateLinkActionActivation
