package org.modeldriven.fuml.test.library;

import java.io.File;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.library.Library;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

/**
 * 
 */
public class LibraryTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(LibraryTestCase.class);

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LibraryTestCase.class);
    }

    public void setUp() throws Exception {
    }

    public void testLoadLibraries() throws Exception {
        Library.getInstance();
        log.info("done");
    }


}