
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
import fUML.Semantics.Loci.LociL1.*;

public class DestroyLinkActionActivation extends
		fUML.Semantics.Actions.IntermediateActions.WriteLinkActionActivation {

	public void doAction() {
		// Get the extent, at the current execution locus, of the association
		// for which links are being destroyed.
		// Destroy all links that match the given link end destruction data.
		// For unique ends, or non-unique ends for which isDestroyDuplicates is
		// true, match links with a matching value for that end.
		// For non-unique, ordered ends for which isDestroyDuplicates is false,
		// match links with an end value at the given destroyAt position. [Must
		// a value be given, too, in this case?]
		// For non-unique, non-ordered ends for which isDestroyDuplicates is
		// false, pick one matching link (if any) non-deterministically. [The
		// semantics of this case is not clear from the current spec.]

		Debug.println("[doAction] DestroyLinkAction...");

		DestroyLinkAction action = (DestroyLinkAction) (this.node);
		LinkEndDestructionDataList destructionDataList = action.endData;

		Debug.println("[doAction] end data size = "
				+ destructionDataList.size());

		boolean destroyOnlyOne = false;
		int j = 1;
		while (!destroyOnlyOne & j <= destructionDataList.size()) {
			LinkEndDestructionData endData = destructionDataList
					.getValue(j - 1);
			destroyOnlyOne = !endData.end.multiplicityElement.isUnique
					& !endData.end.multiplicityElement.isOrdered
					& !endData.isDestroyDuplicates;
			j = j + 1;
		}

		LinkEndDataList endDataList = new LinkEndDataList();
		for (int i = 0; i < destructionDataList.size(); i++) {
			LinkEndDestructionData endData = destructionDataList.getValue(i);
			Debug.println("[doAction] Matching end = " + endData.end.name);
			endDataList.addValue(endData);
		}

		ExtensionalValueList extent = this.getExecutionLocus().getExtent(
				this.getAssociation());
		ExtensionalValueList matchingLinks = new ExtensionalValueList();

		for (int i = 0; i < extent.size(); i++) {
			ExtensionalValue value = extent.getValue(i);
			Link link = (Link) value;
			if (this.linkMatchesEndData(link, endDataList)) {
				matchingLinks.addValue(link);
			}
		}

		// Now that matching is done, ensure that all tokens on end data input
		// pins
		// are consumed.
		for (int i = 0; i < destructionDataList.size(); i++) {
			LinkEndDestructionData endData = destructionDataList.getValue(i);
			Property end = endData.end;
			if (!endData.isDestroyDuplicates
					& !end.multiplicityElement.isUnique
					& end.multiplicityElement.isOrdered) {
				this.takeTokens(endData.destroyAt);
			}
			Debug.println("[doAction] Consuming tokens for end " + end.name);
			this.takeTokens(endData.value);
		}

		if (destroyOnlyOne) {
			// *** If there is more than one matching link,
			// non-deterministically choose one. ***
			if (matchingLinks.size() > 0) {
				int i = ((ChoiceStrategy) this.getExecutionLocus().factory
						.getStrategy("choice")).choose(matchingLinks.size());
				matchingLinks.getValue(i - 1).destroy();
			}
		} else {
			for (int i = 0; i < matchingLinks.size(); i++) {
				ExtensionalValue matchingLink = matchingLinks.getValue(i);
				matchingLink.destroy();
			}
		}
	} // doAction

} // DestroyLinkActionActivation
