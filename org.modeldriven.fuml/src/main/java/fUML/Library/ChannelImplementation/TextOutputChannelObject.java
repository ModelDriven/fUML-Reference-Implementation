
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
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;

public abstract class TextOutputChannelObject extends
		fUML.Library.ChannelImplementation.OutputChannelObject {

	public abstract void writeString(String value);

	public abstract void writeNewLine();

	public void writeLine(String value) {
		this.writeString(value);
		this.writeNewLine();
	} // writeLine

	public void writeInteger(int value) {
		this.writeString(Integer.toString(value));
	} // writeInteger

	public void writeBoolean(boolean value) {
		this.writeString(Boolean.toString(value));
	} // writeBoolean

	public void writeUnlimitedNatural(UnlimitedNatural value) {
		int naturalValue = value.naturalValue;

		if (naturalValue < 0) {
			this.writeString("*");
		} else {
			this.writeString(Integer.toString(naturalValue));
		}
	} // writeUnlimitedNatural

	public void execute(
			fUML.Library.LibraryClassImplementation.OperationExecution execution) {
		String name = execution.getOperationName();
		// Debug.println("[execute] operation = " + name);

		ParameterValue parameterValue = execution.getParameterValue("value");
		// if ((parameterValue != null) && (parameterValue.values.size() > 0)) {
		// Debug.println("[execute] argument = " +
		// parameterValue.values.getValue(0));
		// }

		if (name.equals("writeNewLine")) {
			this.writeNewLine();
		} else if (name.equals("writeString")) {
			this
					.writeString(((StringValue) (parameterValue.values
							.getValue(0))).value);
		} else if (name.equals("writeLine")) {
			this
					.writeLine(((StringValue) (parameterValue.values
							.getValue(0))).value);
		} else if (name.equals("writeInteger")) {
			this.writeInteger(((IntegerValue) (parameterValue.values
					.getValue(0))).value);
		} else if (name.equals("writeBoolean")) {
			this.writeBoolean(((BooleanValue) (parameterValue.values
					.getValue(0))).value);
		} else if (name.equals("writeUnlimitedNatural")) {
			this
					.writeUnlimitedNatural(((UnlimitedNaturalValue) (parameterValue.values
							.getValue(0))).value);
		} else {
			super.execute(execution);
		}
	} // execute

} // TextOutputChannelObject
