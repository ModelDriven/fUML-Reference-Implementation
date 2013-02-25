
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

public class LinkEndDestructionData extends
		fUML.Syntax.Actions.IntermediateActions.LinkEndData {

	public boolean isDestroyDuplicates = false;
	public fUML.Syntax.Actions.BasicActions.InputPin destroyAt = null;

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates) {
		this.isDestroyDuplicates = isDestroyDuplicates;
	} // setIsDestroyDuplicates

	public void setDestroyAt(fUML.Syntax.Actions.BasicActions.InputPin destroyAt) {
		this.destroyAt = destroyAt;
	} // setDestroyAt

} // LinkEndDestructionData
