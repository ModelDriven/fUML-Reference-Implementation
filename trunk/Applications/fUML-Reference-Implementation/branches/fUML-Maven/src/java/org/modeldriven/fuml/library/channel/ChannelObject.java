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
	
	// NOTE: Initial status is not set here because this.locus will not be
	// set when the object is first created.
	protected Status status = null;

    public abstract String getName();
    public abstract void open(Status errorStatus);
    public abstract void close(Status errorStatus);
    public abstract boolean isOpen();
    
    public Status getStatus() {
    	if (this.status == null) {
    		this.status = new Status(this.locus, "ChannelObject");
    	}
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
        
        Status status = new Status(this.locus, "ChannelObject");

        if (name.equals("getName")) {
            StringValue nameValue = new StringValue();
            nameValue.value = this.getName();
            nameValue.type = this.locus.factory.getBuiltInType("String");
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
            isOpenValue.type = this.locus.factory.getBuiltInType("Boolean");
            execution.setReturnParameterValue(isOpenValue);
        } else if (name.equals("getStatus")) {
        	Status result = this.getStatus();        	
            execution.setReturnParameterValue(result.getValue());
        }        
    }

} // ChannelObject
