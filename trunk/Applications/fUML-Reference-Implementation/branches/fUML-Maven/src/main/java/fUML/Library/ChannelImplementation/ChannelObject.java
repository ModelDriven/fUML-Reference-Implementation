
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

public abstract class ChannelObject extends
		fUML.Library.LibraryClassImplementation.ImplementationObject {

	public abstract String getName();

	public abstract void open();

	public abstract void close();

	public abstract boolean isOpen();

	public void execute(
			fUML.Library.LibraryClassImplementation.OperationExecution execution) {
		String name = execution.getOperationName();

		if (name.equals("getName")) {
			StringValue nameValue = new StringValue();
			nameValue.value = this.getName();
			execution.setParameterValue("result", nameValue);
		} else if (name.equals("open")) {
			this.open();
		} else if (name.equals("close")) {
			this.close();
		} else if (name.equals("isOpen")) {
			BooleanValue isOpenValue = new BooleanValue();
			isOpenValue.value = this.isOpen();
			execution.setParameterValue("result", isOpenValue);
		}
	} // execute

} // ChannelObject
