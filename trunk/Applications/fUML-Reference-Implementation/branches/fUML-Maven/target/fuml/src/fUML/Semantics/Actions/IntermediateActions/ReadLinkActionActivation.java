
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

public class ReadLinkActionActivation extends
		fUML.Semantics.Actions.IntermediateActions.LinkActionActivation {

	public void doAction() {
		// Get the extent, at the current execution locus, of the association to
		// which the action applies.
		// For all links that match the link end data, place the value of the
		// remaining "open" end on the result pin.

		ReadLinkAction action = (ReadLinkAction) (this.node);
		LinkEndDataList endDataList = action.endData;
		LinkEndData openEnd = null;

		int i = 1;
		while ((openEnd == null) & i <= endDataList.size()) {
			if (endDataList.getValue(i - 1).value == null) {
				openEnd = endDataList.getValue(i - 1);
			}
			i = i + 1;
		}

		ExtensionalValueList extent = this.getExecutionLocus().getExtent(
				this.getAssociation());

		FeatureValueList featureValues = new FeatureValueList();
		for (int j = 0; j < extent.size(); j++) {
			ExtensionalValue value = extent.getValue(j);
			Link link = (Link) value;
			if (this.linkMatchesEndData(link, endDataList)) {
				FeatureValue featureValue = link.getFeatureValue(openEnd.end);
				if (!openEnd.end.multiplicityElement.isOrdered
						| featureValues.size() == 0) {
					featureValues.addValue(featureValue);
				} else {
					int n = featureValue.position;
					boolean continueSearching = true;
					int k = 0;
					while (continueSearching & k < featureValues.size()) {
						k = k + 1;
						continueSearching = featureValues.getValue(k - 1).position < n;
					}
					if (continueSearching) {
						featureValues.addValue(featureValue);
					} else {
						featureValues.addValue(k - 1, featureValue);
					}
				}
			}
		}

		for (int j = 0; j < featureValues.size(); j++) {
			FeatureValue featureValue = featureValues.getValue(j);
			this.putToken(action.result, featureValue.values.getValue(0));
		}

		// Now that matching is done, ensure that all tokens on end data input
		// pins
		// are consumed.
		for (int k = 0; k < endDataList.size(); k++) {
			LinkEndData endData = endDataList.getValue(k);
			if (endData.value != null) {
				this.takeTokens(endData.value);
			}
		}

	} // doAction

} // ReadLinkActionActivation
