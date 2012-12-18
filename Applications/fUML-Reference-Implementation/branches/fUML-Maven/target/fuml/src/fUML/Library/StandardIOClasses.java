
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

package fUML.Library;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.Classes.Kernel.*;

public class StandardIOClasses extends fUML.Library.Classes {

	public fUML.Syntax.Classes.Kernel.Class_ Channel = null;
	public fUML.Syntax.Classes.Kernel.Class_ OutputChannel = null;
	public fUML.Syntax.Classes.Kernel.Class_ TextOutputChannel = null;
	public fUML.Syntax.Classes.Kernel.Class_ StandardOutputChannel = null;
	public fUML.Syntax.Classes.Kernel.Class_ InputChannel = null;

	public StandardIOClasses(fUML.Library.PrimitiveTypes primitiveTypes) {
		this.createChannelClass(primitiveTypes);
		this.createOutputChannelClass(primitiveTypes);
		this.createTextOutputChannelClass(primitiveTypes);
		this.createStandardOutputChannelClass(primitiveTypes);
		this.createInputChannelClass(primitiveTypes);

	} // StandardIOClasses

	public void createChannelClass(fUML.Library.PrimitiveTypes primitiveTypes) {
		// MexSystem.println("[createChannelClass] Start...");

		this.Channel = this.createClass("Channel", true);

		Operation getNameOperation = this.addOperation(this.Channel, "getName",
				null);
		this.addParameter(getNameOperation, "result",
				ParameterDirectionKind.return_, primitiveTypes.String);

		this.addOperation(this.Channel, "open", null);
		this.addOperation(this.Channel, "close", null);

		Operation isOpenOperation = this.addOperation(this.Channel, "isOpen",
				null);
		this.addParameter(isOpenOperation, "result",
				ParameterDirectionKind.return_, primitiveTypes.Boolean);

	} // createChannelClass

	public void createOutputChannelClass(
			fUML.Library.PrimitiveTypes primitiveTypes) {
		// MexSystem.println("[createOutputChannelClass] Start...");

		this.OutputChannel = this.createClass("OutputChannel", true);
		this.addGeneralization(this.OutputChannel, this.Channel);

		Operation writeOperation = this.addOperation(this.OutputChannel,
				"write", null);
		this.addParameter(writeOperation, "value", ParameterDirectionKind.in,
				null);

		Operation isFullOperation = this.addOperation(this.OutputChannel,
				"isFull", null);
		this.addParameter(isFullOperation, "result",
				ParameterDirectionKind.return_, primitiveTypes.Boolean);
	} // createOutputChannelClass

	public void createTextOutputChannelClass(
			fUML.Library.PrimitiveTypes primitiveTypes) {
		// MexSystem.println("[createTextOutputChannelClass] Start...");

		this.TextOutputChannel = this.createClass("TextOutputChannel", true);
		this.addGeneralization(this.TextOutputChannel, this.OutputChannel);

		Operation writeStringOperation = this.addOperation(
				this.TextOutputChannel, "writeString", null);
		this.addParameter(writeStringOperation, "value",
				ParameterDirectionKind.in, primitiveTypes.String);

		this.addOperation(this.TextOutputChannel, "writeNewLine", null);

		Operation writeLineOperation = this.addOperation(
				this.TextOutputChannel, "writeLine", null);
		this.addParameter(writeLineOperation, "value",
				ParameterDirectionKind.in, primitiveTypes.String);

		Operation writeIntegerOperation = this.addOperation(
				this.TextOutputChannel, "writeInteger", null);
		this.addParameter(writeIntegerOperation, "value",
				ParameterDirectionKind.in, primitiveTypes.Integer);

		Operation writeBooleanOperation = this.addOperation(
				this.TextOutputChannel, "writeBoolean", null);
		this.addParameter(writeBooleanOperation, "value",
				ParameterDirectionKind.in, primitiveTypes.Boolean);

		Operation writeUnlimitedNaturalOperation = this.addOperation(
				this.TextOutputChannel, "writeUnlimitedNatural", null);
		this.addParameter(writeUnlimitedNaturalOperation, "value",
				ParameterDirectionKind.in, primitiveTypes.UnlimitedNatural);

	} // createTextOutputChannelClass

	public void createStandardOutputChannelClass(
			fUML.Library.PrimitiveTypes primitiveTypes) {
		// MexSystem.println("[createStandardOutputChannelClass] Start...");

		this.StandardOutputChannel = this.createClass("StandardOutputChannel",
				false);
		this.addGeneralization(this.StandardOutputChannel,
				this.TextOutputChannel);
	} // createStandardOutputChannelClass

	public void createInputChannelClass(
			fUML.Library.PrimitiveTypes primitiveTypes) {
		this.InputChannel = this.createClass("InputChannel", true);
		this.addGeneralization(this.InputChannel, this.Channel);

		Operation readOperation = this.addOperation(this.InputChannel, "read",
				null);
		this.addParameter(readOperation, "value", ParameterDirectionKind.out,
				null);

		Operation hasMoreOperation = this.addOperation(this.InputChannel,
				"hasMore", null);
		this.addParameter(hasMoreOperation, "result",
				ParameterDirectionKind.return_, primitiveTypes.Boolean);
	} // createInputChannelClass

} // StandardIOClasses
