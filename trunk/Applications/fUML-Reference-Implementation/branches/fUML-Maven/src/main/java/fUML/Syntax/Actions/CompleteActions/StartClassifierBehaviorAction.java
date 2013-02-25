
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

package fUML.Syntax.Actions.CompleteActions;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class StartClassifierBehaviorAction extends
		fUML.Syntax.Actions.BasicActions.Action {

	public fUML.Syntax.Actions.BasicActions.InputPin object = null;

	public void setObject(fUML.Syntax.Actions.BasicActions.InputPin object) {
		super.addInput(object);
		this.object = object;

	} // setObject

} // StartClassifierBehaviorAction
