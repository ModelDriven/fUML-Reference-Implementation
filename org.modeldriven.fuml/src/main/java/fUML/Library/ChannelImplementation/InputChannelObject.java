
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

package fUML.Library.ChannelImplementation;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Semantics.Classes.Kernel.*;

public abstract class InputChannelObject extends
		fUML.Library.ChannelImplementation.ChannelObject {

	public abstract boolean hasMore();

	public abstract fUML.Semantics.Classes.Kernel.Value read();

	public void execute(
			fUML.Library.LibraryClassImplementation.OperationExecution execution) {
		String name = execution.getOperationName();

		if (name.equals("hasMore")) {
			BooleanValue hasMoreValue = new BooleanValue();
			hasMoreValue.value = this.hasMore();
			execution.setParameterValue("result", hasMoreValue);
		} else if (name.equals("read")) {
			execution.setParameterValue("value", this.read());
		} else {
			super.execute(execution);
		}
	} // execute

} // InputChannelObject
