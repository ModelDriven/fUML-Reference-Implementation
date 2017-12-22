
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

import fuml.semantics.structuredclassifiers.Reference;
import fuml.syntax.actions.CreateObjectAction;
import fuml.syntax.structuredclassifiers.Class_;

public class CreateObjectActionActivation extends
		fuml.semantics.actions.ActionActivation {

	public void doAction() {
		// Create an object with the given classifier (which must be a class) as
		// its type, at the same locus as the action activation.
		// Place a reference to the object on the result pin of the action.

		CreateObjectAction action = (CreateObjectAction) (this.node);

		Reference reference = new Reference();
		reference.referent = this.getExecutionLocus().instantiate(
				(Class_) (action.classifier));

		this.putToken(action.result, reference);

	} // doAction

} // CreateObjectActionActivation
