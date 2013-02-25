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

public abstract class OutputChannelObject extends ChannelObject {

    public abstract void write(fUML.Semantics.Classes.Kernel.Value value, Status errorStatus);
    public abstract boolean isFull();

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();

        if (name.equals("write")) {
        	Status status = new Status("OutputChannelObject");
            this.write(execution.getParameterValue("value").values.getValue(0), status);
            this.updateStatus(execution, status);
        } else if (name.equals("isFull")) {
            BooleanValue isFullValue = new BooleanValue();
            isFullValue.value = this.isFull();
            execution.setReturnParameterValue(isFullValue);
        } else {
            super.execute(execution);
        }

    }

} // OutputChannelObject
