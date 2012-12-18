package org.modeldriven.fuml.test.library;



import java.io.File;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.environment.InvalidExecutionTargetException;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

/**
 * 
 */
public class ExternalReferenceTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(ExternalReferenceTestCase.class);
    
    private static Environment environment; // JUnit creates a new test class for every test! 
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(ExternalReferenceTestCase.class);
    }
    
    public void setUp() throws Exception {
    	
    	if (environment == null) {
	    	String filename = "./test/mdxml/fUML-Tests.mdxml";
	        File file = new File(filename);
	        assertTrue("file '" + filename + "' does not exist", file.exists());
	        Fuml.loadIncrementally(file, "test/mdxml/fUML-Tests.mdxml");
	        environment = Environment.getInstance();
    	}   	
    }

    public void testLUnlimitedNaturalFunctions() throws Exception {
        try {
        	// this behavior currently contains invalid external references - FIXME: Ed S
            execute("TestUnlimitedNaturalFunctions");
        }
        catch (InvalidExecutionTargetException e) {
        	log.info(e); 
            assertTrue("unexpected " + InvalidExecutionTargetException.class.getSimpleName(), 
            		false);
        }
        log.info("done");
    }  
    
    public void testIntegerFunctions() throws Exception {
        environment = Environment.getInstance();
        execute("TestIntegerFunctions");
        log.info("done");
    }    
    
    private void execute(String activityName)
    {
        Behavior behavior = environment.findBehavior(activityName);
        if (behavior == null)
            throw new RuntimeException("invalid behavior, " + activityName);
        log.info("executing behavior: " + behavior.name);
        ExecutionEnvironment execution = new ExecutionEnvironment(environment);
        execution.execute(behavior);
    }
}