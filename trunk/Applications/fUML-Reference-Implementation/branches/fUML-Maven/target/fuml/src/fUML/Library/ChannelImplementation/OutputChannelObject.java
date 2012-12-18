
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

public abstract class OutputChannelObject extends
		fUML.Library.ChannelImplementation.ChannelObject {

	public abstract void write(fUML.Semantics.Classes.Kernel.Value value);

	public abstract boolean isFull();

	public void execute(
			fUML.Library.LibraryClassImplementation.OperationExecution execution) {
		String name = execution.getOperationName();

		if (name.equals("write")) {
			this.write(execution.getParameterValue("value").values.getValue(0));
		} else if (name.equals("isFull")) {
			BooleanValue isFullValue = new BooleanValue();
			isFullValue.value = this.isFull();
			execution.setParameterValue("result", isFullValue);
		} else {
			super.execute(execution);
		}
	} // execute

} // OutputChannelObject
