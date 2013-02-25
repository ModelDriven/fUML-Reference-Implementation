
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

public abstract class LinkActionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

	public boolean linkMatchesEndData(fUML.Semantics.Classes.Kernel.Link link,
			fUML.Syntax.Actions.IntermediateActions.LinkEndDataList endDataList) {
		// Test whether the given link matches the given end data.

		boolean matches = true;
		int i = 1;
		while (matches & i <= endDataList.size()) {
			matches = this.endMatchesEndData(link, endDataList.getValue(i - 1));
			i = i + 1;
		}

		return matches;
	} // linkMatchesEndData

	public boolean endMatchesEndData(fUML.Semantics.Classes.Kernel.Link link,
			fUML.Syntax.Actions.IntermediateActions.LinkEndData endData) {
		// Test whether the appropriate end of the given link matches the given
		// end data.

		boolean matches = false;
		if (endData.value == null) {
			matches = true;
		} else {
			Property end = endData.end;
			FeatureValue linkFeatureValue = link.getFeatureValue(end);
			Value endValue = this.getTokens(endData.value).getValue(0);
			if (endData instanceof LinkEndDestructionData) {
				if (!((LinkEndDestructionData) endData).isDestroyDuplicates
						& !end.multiplicityElement.isUnique
						& end.multiplicityElement.isOrdered) {
					int destroyAt = ((UnlimitedNaturalValue) (this
							.getTokens(((LinkEndDestructionData) endData).destroyAt)
							.getValue(0))).value.naturalValue;
					matches = linkFeatureValue.values.getValue(0).equals(
							endValue)
							&& linkFeatureValue.position == destroyAt;
				} else {
					matches = linkFeatureValue.values.getValue(0).equals(
							endValue);
				}
			} else {
				matches = linkFeatureValue.values.getValue(0).equals(endValue);
			}
		}

		return matches;
	} // endMatchesEndData

	public fUML.Syntax.Classes.Kernel.Association getAssociation() {
		// Get the association for the link action of this activation.

		return (Association) (((LinkAction) (this.node)).endData.getValue(0).end.association);
	} // getAssociation

} // LinkActionActivation
