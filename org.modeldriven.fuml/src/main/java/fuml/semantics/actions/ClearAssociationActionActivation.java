
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

import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.semantics.structuredclassifiers.Link;
import fuml.semantics.values.Value;
import fuml.syntax.actions.ClearAssociationAction;

public class ClearAssociationActionActivation extends
		fuml.semantics.actions.ActionActivation {

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
