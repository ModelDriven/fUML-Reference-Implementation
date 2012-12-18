package org.modeldriven.fuml.test.validation;

import java.io.File;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.FumlException;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

/**
 * 
 */
public class IncrementalValidationTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(IncrementalValidationTestCase.class);

    private static Environment environment; // JUnit creates a new test class
                                            // for every test!

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(IncrementalValidationTestCase.class);
    }

    public void setUp() throws Exception {
        if (environment == null) {
            String filename = "./test/mdxml/fUML-Tests-Incremental-Validation.mdxml";
            File file = new File(filename);
            assertTrue("file '" + filename + "' does not exist", file.exists());
            Fuml.loadIncrementally(file, filename);
            environment = Environment.getInstance();
            log.info("done");
        }
    }

    public void testCopierCaller() throws Exception {
        try {
            execute("CopierCaller");
            assertTrue("expected thrown exception", false);
        } catch (FumlException e) {
            log.info(e.getMessage());
            log.info("done");
        }
    }

    private void execute(String activityName) {
        Behavior behavior = environment.findBehavior(activityName);
        if (behavior == null)
            throw new RuntimeException("behavior does not exist, " + activityName);
        log.info("executing behavior: " + behavior.name);
        ExecutionEnvironment execution = new ExecutionEnvironment(environment);
        execution.execute(behavior);
    }
}