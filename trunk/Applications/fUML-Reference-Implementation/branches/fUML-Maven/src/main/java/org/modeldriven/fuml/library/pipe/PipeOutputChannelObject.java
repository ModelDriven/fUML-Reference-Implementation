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

import org.modeldriven.fuml.library.channel.OutputChannelObject;
import org.modeldriven.fuml.library.common.Status;

import fUML.Semantics.Classes.Kernel.Value;


public class PipeOutputChannelObject extends OutputChannelObject {

    private PipeInputChannelObject otherEnd = null;
    private boolean opened = false;
    private String name = new String();

    public void write(Value value, Status errorStatus) {
        if (this.isOpen()) {
            this.otherEnd.receive(value);
        }
    }

    public boolean isFull() {
        return false;

    }

    public PipeOutputChannelObject(String name, PipeInputChannelObject otherEnd) {
		this.name = name;
		this.otherEnd = otherEnd;
		this.opened = true;
    }

    public String getName() {
        return name;
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

} // PipeOutputChannelObject
