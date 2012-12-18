package org.modeldriven.fuml.test.library;



import java.io.File;

import junit.framework.Test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.Fuml;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.environment.ExecutionEnvironment;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

/**
 * 
 */
public class FunctionExecutionTestCase extends FUMLTest {
    private static Log log = LogFactory.getLog(FunctionExecutionTestCase.class);
    
    private static Environment environment; // JUnit creates a new test class for every test! 
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(FunctionExecutionTestCase.class);
    }
    
    public void setUp() throws Exception {
        // called for every test
        if (environment == null) {
            String filename = "./test/mdxml/fUML-Tests.mdxml";
            File file = new File(filename);
            assertTrue("file '" + filename + "' does not exist", file.exists());
            Fuml.loadIncrementally(file, filename);
            environment = Environment.getInstance();
            log.info("done");
        }
    }
      
    public void testTestIntegerFunctions() throws Exception {
        log.info("testTestIntegerFunctions");
        execute("TestIntegerFunctions");
        log.info("done");
    }
   
    public void testTestIntegerComparisonFunctions() throws Exception {
        log.info("testTestIntegerComparisonFunctions");
        execute("TestIntegerComparisonFunctions");
        log.info("done");
    }
            
    public void testTestRealFunctions() throws Exception {
        log.info("testTestRealFunctions");
        execute("TestRealFunctions");
        log.info("done");
    }
   
    public void testTestRealComparisonFunctions() throws Exception {
        log.info("testTestRealComparisonFunctions");
        execute("TestRealComparisonFunctions");
        log.info("done");
    }
            
    public void testTestBooleanFunctions() throws Exception {
        log.info("testTestBooleanFunctions");
        execute("TestBooleanFunctions");
        log.info("done");
    }
     
    public void testTestStringFunctions() throws Exception {
        log.info("testTestStringFunctions");
        execute("TestStringFunctions");
        log.info("done");
    }    
        
    public void testTestUnlimitedNaturalFunctions() throws Exception {
        log.info("testTestUnlimitedNaturalFunctions");
        execute("TestUnlimitedNaturalFunctions");
        log.info("done");
    }

    public void testTestListFunctions() throws Exception {
        log.info("testTestListFunctions");
        execute("TestListFunctions");
        log.info("done");
    }
    
    public void testHelloWorld() throws Exception {
        log.info("testHelloWorld");
        execute("HelloWorld");
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