
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

package fUML.Library.PipeImplementation;

import fUML.Debug;
import UMLPrimitiveTypes.*;

public class PipeOutputChannelObject extends
		fUML.Library.ChannelImplementation.OutputChannelObject {

	private fUML.Library.PipeImplementation.PipeInputChannelObject otherEnd = null;
	private boolean opened = false;
	private String name = "";

	public void write(fUML.Semantics.Classes.Kernel.Value value) {
		if (this.isOpen()) {
			this.otherEnd.receive(value);
		}
	} // write

	public boolean isFull() {
		return false;
	} // isFull

	public PipeOutputChannelObject(String name,
			fUML.Library.PipeImplementation.PipeInputChannelObject otherEnd) {
		this.name = name;
		this.otherEnd = otherEnd;
		this.opened = true;
	} // PipeOutputChannelObject

	public String getName() {
		return name;
	} // getName

	public void open() {
		opened = true;
	} // open

	public void close() {
		opened = false;
	} // close

	public boolean isOpen() {
		return opened;
	} // isOpen

} // PipeOutputChannelObject
