
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

public class StandardOutputChannelObject extends
		fUML.Library.ChannelImplementation.TextOutputChannelObject {

	private boolean opened = false;

	public void writeLine(String value) {
		this.writeString(value);
	} // writeLine

	public String getName() {
		return "StandardOutput";
	} // getName

	public void open() {
		this.opened = true;
	} // open

	public void close() {
		this.opened = false;
	} // close

	public boolean isOpen() {
		return opened;
	} // isOpen

	public void write(fUML.Semantics.Classes.Kernel.Value value) {
		this.writeString(value.toString());
	} // write

	public void writeString(String value) {
		if (this.isOpen()) {
			Debug.println("[event] >>>>>>>> " + value);
		}

	} // writeString

	public void writeNewLine() {
		this.writeLine("");
	} // writeNewLine

	public fUML.Semantics.Classes.Kernel.Value new_() {
		return (fUML.Semantics.Classes.Kernel.Value) (new StandardOutputChannelObject());
	} // new_

	public boolean isFull() {
		return false;
	} // isFull

} // StandardOutputChannelObject
