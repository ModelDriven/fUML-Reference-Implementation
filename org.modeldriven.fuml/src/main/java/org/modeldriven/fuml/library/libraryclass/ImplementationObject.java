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


package org.modeldriven.fuml.library.libraryclass;


/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>fUML::Library::LibraryClassImplementation::ImplementationObject</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ImplementationObject#execute <em>execute</em>}</li>
 * <li>{@link ImplementationObject#dispatch <em>dispatch</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */

public abstract class ImplementationObject extends fUML.Semantics.Classes.Kernel.Object_ {

    // Attributes

    // Operations of the class
    /**
     * operation execute <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    public abstract void execute(
    		org.modeldriven.fuml.library.libraryclass.OperationExecution execution);

    /**
     * operation dispatch <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */

    public fUML.Semantics.CommonBehaviors.BasicBehaviors.Execution dispatch(
            fUML.Syntax.Classes.Kernel.Operation operation) {
        OperationExecution execution = new OperationExecution();
        this.locus.add(execution);
        execution.set(this, operation);
        return execution;

    }

} // ImplementationObject
