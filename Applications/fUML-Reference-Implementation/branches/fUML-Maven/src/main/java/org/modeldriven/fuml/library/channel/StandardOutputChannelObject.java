/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * All modifications copyright 2008-2011 Data Access Technologies, Inc. 
 * Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.channel;

import org.modeldriven.fuml.library.common.Status;

import fUML.Semantics.Classes.Kernel.Value;

public class StandardOutputChannelObject extends TextOutputChannelObject {

	private boolean opened = true; // S.C. 11/20/2008 - set default to 'true'

	@Override
	public String getName() {
		return "StandardOutput";
	}

	@Override
	public void open(Status errorStatus) {
		this.opened = true;
	}

	@Override
	public void close(Status errorStatus) {
		this.opened = false;
	}

	@Override
	public boolean isOpen() {
		return this.opened;
	}
	
	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public void write(Value value, Status errorStatus) {
		this.writeString(value.toString(), errorStatus);
	}

	@Override
	public void writeString(String value, Status errorStatus) {
		if (this.isOpen()) {
			System.out.print(value);
			// Debug.println("[event] >>>>>>>> " + value);
		} else {
			errorStatus.setStatus("StandardOutputChannel", -1, "Not open");
		}
	}

	@Override
	public void writeNewLine(Status errorStatus) {
		if (this.isOpen()) {
			System.out.println();
		} else {
			errorStatus.setStatus("StandardOutputChannel", -1, "Not open");
		}
	}

	@Override
	public Value new_() {
		return (Value)new StandardOutputChannelObject();
	}

} // StandardOutputChannelObject
