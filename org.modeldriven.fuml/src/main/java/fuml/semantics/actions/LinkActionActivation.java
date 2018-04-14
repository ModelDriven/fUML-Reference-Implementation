
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

package fuml.semantics.actions;

import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;
import fuml.semantics.values.Value;
import fuml.syntax.actions.LinkAction;
import fuml.syntax.actions.LinkEndDestructionData;
import fuml.syntax.classification.Property;
import fuml.syntax.structuredclassifiers.Association;

public abstract class LinkActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public boolean linkMatchesEndData(fuml.semantics.structuredclassifiers.Link link,
			fuml.syntax.actions.LinkEndDataList endDataList) {
		// Test whether the given link matches the given end data.

		boolean matches = true;
		int i = 1;
		while (matches & i <= endDataList.size()) {
			matches = this.endMatchesEndData(link, endDataList.getValue(i - 1));
			i = i + 1;
		}

		return matches;
	} // linkMatchesEndData

	public boolean endMatchesEndData(fuml.semantics.structuredclassifiers.Link link,
			fuml.syntax.actions.LinkEndData endData) {
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

	public fuml.syntax.structuredclassifiers.Association getAssociation() {
		// Get the association for the link action of this activation.

		return (Association) (((LinkAction) (this.node)).endData.getValue(0).end.association);
	} // getAssociation

} // LinkActionActivation
