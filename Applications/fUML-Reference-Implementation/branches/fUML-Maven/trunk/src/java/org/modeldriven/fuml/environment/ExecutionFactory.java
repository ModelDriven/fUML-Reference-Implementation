
/*
 * Copyright 2009 Data Access Technologies, Inc., except
 * as stated in the file entitled Licensing-Information.
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.environment;

public class ExecutionFactory extends fUML.Semantics.Loci.LociL3.ExecutionFactoryL3 {

    public fUML.Semantics.Loci.LociL1.SemanticVisitor instantiateVisitor(
            fUML.Syntax.Classes.Kernel.Element element) {
        // Instantiate a visitor object for the given element.

        fUML.Semantics.Loci.LociL1.SemanticVisitor visitor = null;

        if (element instanceof fUML.Syntax.Activities.IntermediateActivities.Activity) {
			visitor = new ActivityExecution();  // Uses local subclass for ActivityExecution
		} else {
			visitor = super.instantiateVisitor(element);
		}

        return visitor;
    } // instantiateVisitor

} // ExecutionFactory
