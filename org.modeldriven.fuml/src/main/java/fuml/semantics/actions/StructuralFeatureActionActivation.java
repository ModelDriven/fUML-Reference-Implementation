
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

import fuml.semantics.structuredclassifiers.ExtensionalValue;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.semantics.structuredclassifiers.Link;
import fuml.semantics.structuredclassifiers.LinkList;
import fuml.syntax.classification.Property;
import fuml.syntax.structuredclassifiers.Association;

public abstract class StructuralFeatureActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public fuml.syntax.structuredclassifiers.Association getAssociation(
			fuml.syntax.classification.StructuralFeature feature) {
		// If the structural feature for the action of this activation is an
		// association end,
		// then get the associated association.

		Association association = null;
		if (feature instanceof Property) {
			association = ((Property) feature).association;
		}

		return association;
	} // getAssociation

	public fuml.semantics.structuredclassifiers.LinkList getMatchingLinks(
			fuml.syntax.structuredclassifiers.Association association,
			fuml.syntax.classification.StructuralFeature end,
			fuml.semantics.values.Value oppositeValue) {
		// Get the links of the given binary association whose end opposite
		// to the given end has the given value
		
		return this.getMatchingLinksForEndValue(association, end, oppositeValue, null);
	}

	
	public fuml.semantics.structuredclassifiers.LinkList getMatchingLinksForEndValue(
			fuml.syntax.structuredclassifiers.Association association,
			fuml.syntax.classification.StructuralFeature end,
			fuml.semantics.values.Value oppositeValue,
			fuml.semantics.values.Value endValue) {
		// Get the links of the given binary association whose end opposite
		// to the given end has the given opposite value and, optionally, that
		// has a given end value for the given end.

		Property oppositeEnd = this.getOppositeEnd(association, end);

		ExtensionalValueList extent = this.getExecutionLocus().getExtent(
				association);

		LinkList links = new LinkList();
		for (int i = 0; i < extent.size(); i++) {
			ExtensionalValue link = extent.getValue(i);
			if (link.getFeatureValue(oppositeEnd).values.getValue(0).equals(oppositeValue)) {
				boolean matches = true;
				if (endValue != null) {
					matches = link.getFeatureValue(end).values.getValue(0).equals(endValue);
				}
				if (matches) {
					if (!end.multiplicityElement.isOrdered | links.size() == 0) {
						links.addValue((Link) link);
					} else {
						int n = link.getFeatureValue(end).position;
						boolean continueSearching = true;
						int j = 0;
						while (continueSearching & j < links.size()) {
							j = j + 1;
							continueSearching = links.getValue(j - 1).getFeatureValue(end).position < n;
						}
						if (continueSearching) {
							links.addValue((Link) link);
						} else {
							links.addValue(j - 1, (Link) link);
						}
					}
				}
			}
		}

		return links;
	} // getMatchingLinks

	public fuml.syntax.classification.Property getOppositeEnd(
			fuml.syntax.structuredclassifiers.Association association,
			fuml.syntax.classification.StructuralFeature end) {
		// Get the end of a binary association opposite to the given end.

		Property oppositeEnd = association.memberEnd.getValue(0);
		if (oppositeEnd == end) {
			oppositeEnd = association.memberEnd.getValue(1);
		}

		return oppositeEnd;
	} // getOppositeEnd

} // StructuralFeatureActionActivation
