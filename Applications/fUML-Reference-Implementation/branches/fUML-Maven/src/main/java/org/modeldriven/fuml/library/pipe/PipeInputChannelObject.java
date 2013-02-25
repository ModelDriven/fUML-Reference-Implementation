/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */


package org.modeldriven.fuml.library.pipe;

import org.modeldriven.fuml.library.common.Status;

import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;

public class PipeInputChannelObject extends org.modeldriven.fuml.library.channel.InputChannelObject {

    private ValueList values = new fUML.Semantics.Classes.Kernel.ValueList();
    private boolean opened = false;
    private String name = new String();

    public boolean hasMore() {
        return this.isOpen() && !(this.values.size() == 0);

    }

    public Value read(Status errorStatus) {
        if (this.hasMore()) {
            Value value = this.values.getValue(0);
            this.values.remove(0);
            return value;
        } else {
            return null;
        }
    }

	@Override
	public Value peek(Status errorStatus) {
        if (this.hasMore()) {
            Value value = this.values.getValue(0);
            return value;
        } else {
            return null;
        }
	}

    public void receive(Value value) {
        this.values.addValue(value);

    }

    public void open(Status errorStatus) {
        opened = true;
    }

    public void close(Status errorStatus) {
        opened = false;
    }

    public boolean isOpen() {
        return opened;

    }

    public String getName() {
        return name;
    }

    public PipeInputChannelObject(String name) {
        this.name = name;
        this.opened = true;
    }

} // PipeInputChannelObject
