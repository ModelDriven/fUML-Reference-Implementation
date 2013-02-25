
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

public class PipeInputChannelObject extends
		fUML.Library.ChannelImplementation.InputChannelObject {

	private fUML.Semantics.Classes.Kernel.ValueList values = new fUML.Semantics.Classes.Kernel.ValueList();
	private boolean opened = false;
	private String name = "";

	public boolean hasMore() {
		return this.isOpen() && !(this.values.size() == 0);
	} // hasMore

	public fUML.Semantics.Classes.Kernel.Value read() {
		if (this.hasMore()) {
			fUML.Semantics.Classes.Kernel.Value value = this.values.getValue(0);
			this.values.remove(0);
			return value;
		} else {
			return null;
		}
	} // read

	public void receive(fUML.Semantics.Classes.Kernel.Value value) {
		this.values.addValue(value);

	} // receive

	public void open() {
		opened = true;
	} // open

	public void close() {
		opened = false;
	} // close

	public boolean isOpen() {
		return opened;
	} // isOpen

	public String getName() {
		return name;
	} // getName

	public PipeInputChannelObject(String name) {
		this.name = name;
		this.opened = true;
	} // PipeInputChannelObject

} // PipeInputChannelObject
