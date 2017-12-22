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
package org.modeldriven.fuml.assembly;

import fuml.syntax.classification.Classifier;
import fuml.syntax.structuredclassifiers.Class_;

@Deprecated
public class AssemblerResultsProfile {

    private Class_[] resultsClasses;

    @SuppressWarnings("unused")
    private AssemblerResultsProfile() {
    }

    public AssemblerResultsProfile(Class_[] resultsClasses) {
        this.resultsClasses = resultsClasses;
    }

    public boolean isResultClass(Classifier c) {
        for (int i = 0; i < resultsClasses.length; i++)
            if (resultsClasses[i].name.equals(c.name))
                return true;
        return false;
    }

}
