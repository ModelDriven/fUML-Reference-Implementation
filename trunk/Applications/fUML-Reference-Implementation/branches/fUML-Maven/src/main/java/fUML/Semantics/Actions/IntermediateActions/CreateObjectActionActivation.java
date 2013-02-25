
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

public class CreateObjectActionActivation extends
		fUML.Semantics.Actions.BasicActions.ActionActivation {

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
