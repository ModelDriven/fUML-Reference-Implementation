
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

package fUML.Syntax.Actions.IntermediateActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class RemoveStructuralFeatureValueAction extends
		fUML.Syntax.Actions.IntermediateActions.WriteStructuralFeatureAction {

	public boolean isRemoveDuplicates = false;
	public fUML.Syntax.Actions.BasicActions.InputPin removeAt = null;

	public void setIsRemoveDuplicates(boolean isRemoveDuplicates) {
		this.isRemoveDuplicates = isRemoveDuplicates;
	} // setIsRemoveDuplicates

	public void setRemoveAt(fUML.Syntax.Actions.BasicActions.InputPin removeAt) {
		if (removeAt != null) {
			super.addInput(removeAt);
		}

		this.removeAt = removeAt;
	} // setRemoveAt

} // RemoveStructuralFeatureValueAction
