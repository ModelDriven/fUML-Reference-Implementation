/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * All modifications copyright 2009-2011 Data Access Technologies, Inc. 
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
import org.modeldriven.fuml.library.libraryclass.OperationExecution;

import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.Value;

public abstract class InputChannelObject extends ChannelObject {

    public abstract boolean hasMore();
    public abstract Value read(Status errorStatus);
    public abstract Value peek(Status errorStatus);

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();
        
        Status status = new Status(this.locus, "InputChannel");

        if (name.equals("hasMore")) {
            BooleanValue hasMoreValue = new BooleanValue();
            hasMoreValue.value = this.hasMore();
            hasMoreValue.type = this.locus.factory.getBuiltInType("Boolean");
            execution.setReturnParameterValue(hasMoreValue);
        } else if (name.equals("read")) {
        	Value value = this.read(status);
        	if (value != null) {
        		execution.setParameterValue("value", value);
        	}
            this.updateStatus(execution, status);
        } else if (name.equals("peek")) {
        	Value value = this.peek(status);
        	if (value != null) {
        		execution.setParameterValue("value", value);
        	}
            this.updateStatus(execution, status);
        } else {
            super.execute(execution);
        }
    }

} // InputChannelObject
