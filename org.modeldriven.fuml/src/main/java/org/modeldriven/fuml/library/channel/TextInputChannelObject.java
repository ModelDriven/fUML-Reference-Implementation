/*
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.library.channel;

import org.modeldriven.fuml.library.common.Status;
import org.modeldriven.fuml.library.libraryclass.OperationExecution;

import UMLPrimitiveTypes.UnlimitedNatural;
import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.IntegerValue;
import fuml.semantics.simpleclassifiers.PrimitiveValue;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;

public abstract class TextInputChannelObject extends InputChannelObject {

	public abstract String readCharacter(Status errorStatus);
	public abstract String peekCharacter(Status errorStatus);
	public abstract String readLine(Status errorStatus);
	public abstract Integer readInteger(Status errorStatus);
	public abstract Float readReal(Status errorStatus);
	public abstract Boolean readBoolean(Status errorStatus);
	public abstract UnlimitedNatural readUnlimitedNatural(Status errorStatus);

    public void execute(OperationExecution execution) {
        String name = execution.getOperationName();

        Status status = new Status(this.locus, "TextInputChannel");
        PrimitiveValue resultValue = null;
        
        if (name.equals("readCharacter")) {
            String result = this.readCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.type = this.locus.factory.getBuiltInType("String");
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("peekCharacter")) {
            String result = this.peekCharacter(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.type = this.locus.factory.getBuiltInType("String");
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readLine")) {
            String result = this.readLine(status);
            if (result != null) {
	            resultValue = new StringValue();
	            resultValue.type = this.locus.factory.getBuiltInType("String");
	            ((StringValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readInteger")) {
            Integer result = this.readInteger(status);
            if (result != null) {
	            resultValue = new IntegerValue();
	            resultValue.type = this.locus.factory.getBuiltInType("Integer");
	            ((IntegerValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readReal")) {
            Float result = this.readReal(status);
            if (result != null) {
	            resultValue = new RealValue();
	            resultValue.type = this.locus.factory.getBuiltInType("Real");
	            ((RealValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readBoolean")) {
            Boolean result = this.readBoolean(status);
            if (result != null) {
	            resultValue = new BooleanValue();
	            resultValue.type = this.locus.factory.getBuiltInType("Boolean");
	            ((BooleanValue)resultValue).value = result;
            }
            this.updateStatus(execution, status);
        } else if (name.equals("readUnlimitedNatural")) {
            UnlimitedNatural result = this.readUnlimitedNatural(status);
            if (result != null) {
	            resultValue = new UnlimitedNaturalValue();
	            resultValue.type = this.locus.factory.getBuiltInType("UnlimitedNatural");
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
