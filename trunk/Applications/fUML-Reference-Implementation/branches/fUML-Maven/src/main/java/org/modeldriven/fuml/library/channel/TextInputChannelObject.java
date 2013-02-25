/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.library.channel;

import org.modeldriven.fuml.library.common.Status;
import org.modeldriven.fuml.library.libraryclass.OperationExecution;

import UMLPrimitiveTypes.UnlimitedNatural;

import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.PrimitiveValue;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;

public abstract class TextInputChannelObject extends InputChannelObject {

	public abstract String readCharacter(Status errorStatus);
	public abstract String peekCharacter(Status errorStatus);
	public abstract String readLine(Status errorStatus);
	public abstract Integer readInteger(Status errorStatus);
	public abstract Boolean readBoolean(Status errorStatus);
	public abstract UnlimitedNatural readUnlimitedNatural(Status errorStatus);

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();

        Status status = new Status("TextInputChannel");
        PrimitiveValue resultValue = null;
        
        if (name.equals("readCharacter")) {
            String result = this.readCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("peekCharacter")) {
            String result = this.peekCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readLine")) {
            String result = this.readLine(status);
            if (result != null) {
	            resultValue = new StringValue();
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readInteger")) {
            Integer result = this.readInteger(status);
            if (result != null) {
	            resultValue = new IntegerValue();
	            ((IntegerValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readBoolean")) {
            Boolean result = this.readBoolean(status);
            if (result != null) {
	            resultValue = new BooleanValue();
	            ((BooleanValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readUnlimitedNatural")) {
            UnlimitedNatural result = this.readUnlimitedNatural(status);
            if (result != null) {
	            resultValue = new UnlimitedNaturalValue();
	            ((UnlimitedNaturalValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else {
            super.execute(execution);
        }
        
        if (resultValue != null) {
        	execution.setReturnParameterValue(resultValue);
        }
    }

} // TextOutputChannelObject
