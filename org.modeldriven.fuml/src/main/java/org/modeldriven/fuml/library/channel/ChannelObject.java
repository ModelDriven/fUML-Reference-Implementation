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
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;
import org.modeldriven.fuml.library.libraryclass.OperationExecution;

import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.StringValue;

public abstract class ChannelObject extends ImplementationObject {
	
	protected Status status = new Status("ChannelObject");

    public abstract String getName();
    public abstract void open(Status errorStatus);
    public abstract void close(Status errorStatus);
    public abstract boolean isOpen();
    
    public Status getStatus() {
    	return this.status;
    }
    
    public void updateStatus(OperationExecution execution, Status status) {
        if (!status.isNormal()) {
        	execution.setParameterValue("errorStatus", status.getValue());
        }
        this.status = status;
    }

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();
        
        Status status = new Status("ChannelObject");

        if (name.equals("getName")) {
            StringValue nameValue = new StringValue();
            nameValue.value = this.getName();
            execution.setReturnParameterValue(nameValue);
        } else if (name.equals("open")) {
            this.open(status);
            this.updateStatus(execution, status);
        } else if (name.equals("close")) {
            this.close(status);
            this.updateStatus(execution, status);
        } else if (name.equals("isOpen")) {
            BooleanValue isOpenValue = new BooleanValue();
            isOpenValue.value = this.isOpen();
            execution.setReturnParameterValue(isOpenValue);
        } else if (name.equals("getStatus")) {
        	Status result = this.getStatus();        	
            execution.setReturnParameterValue(result.getValue());
        }        
    }

} // ChannelObject
