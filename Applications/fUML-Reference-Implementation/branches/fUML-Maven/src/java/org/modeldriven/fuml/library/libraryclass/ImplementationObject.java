/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc. 
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.library.libraryclass;

public abstract class ImplementationObject extends fUML.Semantics.Classes.Kernel.Object_ {

    public abstract void execute(OperationExecution execution);

    @Override
    public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution dispatch(
            fUML.Syntax.Classes.Kernel.Operation operation) {
        OperationExecution execution = new OperationExecution();
        this.locus.add(execution);
        execution.set(this, operation);
        return execution;
    }

} // ImplementationObject
