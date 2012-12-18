
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

public class CreateLinkAction extends
		fUML.Syntax.Actions.IntermediateActions.WriteLinkAction {

	public fUML.Syntax.Actions.IntermediateActions.LinkEndCreationDataList endData = new fUML.Syntax.Actions.IntermediateActions.LinkEndCreationDataList();

	public void addEndData(
			fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData endData) {
		super.addEndData(endData);
		this.endData.addValue(endData);
	} // addEndData

} // CreateLinkAction
