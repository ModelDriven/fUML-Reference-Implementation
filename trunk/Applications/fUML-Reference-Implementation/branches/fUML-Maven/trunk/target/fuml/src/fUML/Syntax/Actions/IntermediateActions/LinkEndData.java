
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

public class LinkEndData extends fUML.Syntax.Classes.Kernel.Element {

	public fUML.Syntax.Actions.BasicActions.InputPin value = null;
	public fUML.Syntax.Classes.Kernel.Property end = null;

	public void setEnd(fUML.Syntax.Classes.Kernel.Property end) {
		this.end = end;
	} // setEnd

	public void setValue(fUML.Syntax.Actions.BasicActions.InputPin value) {
		this.value = value;
	} // setValue

} // LinkEndData
