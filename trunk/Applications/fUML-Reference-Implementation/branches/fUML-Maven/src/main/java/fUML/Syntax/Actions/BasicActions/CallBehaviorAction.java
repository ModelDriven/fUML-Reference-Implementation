
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

package fUML.Syntax.Actions.BasicActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class CallBehaviorAction extends
		fUML.Syntax.Actions.BasicActions.CallAction {

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior behavior = null;

	public void setBehavior(
			fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior behavior) {
		this.behavior = behavior;
	} // setBehavior

} // CallBehaviorAction
