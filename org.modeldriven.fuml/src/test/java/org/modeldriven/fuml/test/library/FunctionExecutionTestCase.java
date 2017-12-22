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

import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.syntax.commonbehavior.Behavior;

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
            String filename = "./target/test-classes/mdxml/fUML-Tests.mdxml";
            File file = new File(filename);
            assertTrue("file '" + filename + "' does not exist", file.exists());
            Fuml.loadIncrementally(file, filename);
            environment = Environment.getInstance();
            log.info("loaded " + filename);
        }
    }
      
    public void testTestIntegerFunctions() throws Exception {
        log.info("testTestIntegerFunctions");
        ParameterValueList output = execute("TestIntegerFunctions");
        assertEqualValues("TestTestIntegerFunctions.NegResult", output, "NegResult", -3);
        assertEqualValues("TestTestIntegerFunctions.PlusResult", output, "PlusResult", 5);
        assertEqualValues("TestTestIntegerFunctions.MinusResult", output, "MinusResult", 1);
        assertEqualValues("TestTestIntegerFunctions.TimesResult", output, "TimesResult", 6);
        assertEqualValues("TestTestIntegerFunctions.DivResult", output, "DivResult", 1);
        assertEqualValues("TestTestIntegerFunctions.ModResult", output, "ModResult", 1);
        assertEqualValues("TestTestIntegerFunctions.MaxResult", output, "MaxResult", 3);
        assertEqualValues("TestTestIntegerFunctions.MinResult", output, "MinResult", 2);
        assertEqualValues("TestTestIntegerFunctions.AbsResult", output, "AbsResult", 2);
        log.info("done");
    }
   
    public void testTestIntegerComparisonFunctions() throws Exception {
        log.info("testTestIntegerComparisonFunctions");
        ParameterValueList output = execute("TestIntegerComparisonFunctions");
        assertEqualValues("TestIntegerComparisonFunctions.LTResult", output, "LTResult", false);
        assertEqualValues("TestIntegerComparisonFunctions.LEResult", output, "LEResult", false);
        assertEqualValues("TestIntegerComparisonFunctions.GTResult", output, "GTResult", true);
        assertEqualValues("TestIntegerComparisonFunctions.GEResult", output, "GEResult", true);
        log.info("done");
    }
            
    public void testTestRealFunctions() throws Exception {
        log.info("testTestRealFunctions");
        ParameterValueList output = execute("TestRealFunctions");
        assertEqualValues("TestRealFunctions.NegResult", output, "NegResult", -3.1f);
        assertEqualValues("TestRealFunctions.PlusResult", output, "PlusResult", 3.6f);
        assertEqualValues("TestRealFunctions.MinusResult", output, "MinusResult", 2.6f);
        assertEqualValues("TestRealFunctions.InvResult", output, "InvResult", 2.0f);
        assertEqualValues("TestRealFunctions.TimesResult", output, "TimesResult", 1.55f);
        assertEqualValues("TestRealFunctions.DivideResult", output, "DivideResult", 6.2f);
        assertEqualValues("TestRealFunctions.MaxResult", output, "MaxResult", 3.1f);
        assertEqualValues("TestRealFunctions.MinResult", output, "MinResult", 0.5f);
        assertEqualValues("TestRealFunctions.AbsResult", output, "AbsResult", 2.3f);
        assertEqualValues("TestRealFunctions.FloorResult", output, "FloorResult", -3);
        assertEqualValues("TestRealFunctions.RoundResult", output, "RoundResult", -2);
        assertEqualValues("TestRealFunctions.ToIntegerResult", output, "ToIntegerResult", -2);
        log.info("done");
    }
   
    public void testTestRealComparisonFunctions() throws Exception {
        log.info("testTestRealComparisonFunctions");
        ParameterValueList output = execute("TestRealComparisonFunctions");
        assertEqualValues("TestRealComparisonFunctions.LTResult", output, "LTResult", false);
        assertEqualValues("TestRealComparisonFunctions.LEResult", output, "LEResult", false);
        assertEqualValues("TestRealComparisonFunctions.GTResult", output, "GTResult", true);
        assertEqualValues("TestRealComparisonFunctions.GEResult", output, "GEResult", true);
        log.info("done");
    }
            
    public void testTestBooleanFunctions() throws Exception {
        log.info("testTestBooleanFunctions");
        ParameterValueList output = execute("TestBooleanFunctions");
        assertEqualValues("TestBooleanFunctions.NotResult", output, "NotResult", false, true);
        assertEqualValues("TestBooleanFunctions.AndResult", output, "AndResult", true, false, false, false);
        assertEqualValues("TestBooleanFunctions.OrResult", output, "OrResult", true, true, true, false);
        assertEqualValues("TestBooleanFunctions.ImpliesResult", output, "ImpliesResult", true, false, true, true);
        assertEqualValues("TestBooleanFunctions.XorResult", output, "XorResult", false, true, true, false);
        log.info("done");
    }
     
    public void testTestStringFunctions() throws Exception {
        log.info("testTestStringFunctions");
        ParameterValueList output = execute("TestStringFunctions");
        assertEqualValues("TestStringFunctions.SizeResult", output, "SizeResult", 9);
        assertEqualValues("TestStringFunctions.Substring1Result", output, "Substring1Result", "12345");
        assertEqualValues("TestStringFunctions.Substring2Result", output, "Substring2Result", "6789");
        assertEqualValues("TestStringFunctions.ConcatResult", output, "ConcatResult", "123456789");
        log.info("done");
    }    
        
    public void testTestUnlimitedNaturalFunctions() throws Exception {
        log.info("testTestUnlimitedNaturalFunctions");
        ParameterValueList output = execute("TestUnlimitedNaturalFunctions");
        assertEqualValues("TestUnlimitedNaturalFunctions.LTResult", output, "LTResult", false);
        assertEqualValues("TestUnlimitedNaturalFunctions.LEResult", output, "LEResult", false);
        assertEqualValues("TestUnlimitedNaturalFunctions.GTResult", output, "GTResult", true);
        assertEqualValues("TestUnlimitedNaturalFunctions.GEResult", output, "GEResult", true);
        assertEqualValues("TestUnlimitedNaturalFunctions.LTResult*", output, "LTResult*", true);
        assertEqualValues("TestUnlimitedNaturalFunctions.LEResult*", output, "LEResult*", true);
        assertEqualValues("TestUnlimitedNaturalFunctions.GTResult*", output, "GTResult*", false);
        assertEqualValues("TestUnlimitedNaturalFunctions.GEResult*", output, "GEResult*", false);
        assertEqualValues("TestUnlimitedNaturalFunctions.MaxResult", output, "MaxResult", -1);
        assertEqualValues("TestUnlimitedNaturalFunctions.MinResult", output, "MinResult", 2);
        log.info("done");
    }

    public void testTestListFunctions() throws Exception {
        log.info("testTestListFunctions");
        ParameterValueList output = execute("TestListFunctions");
        assertEqualValues("TestListFunctions.size", output, "size", 3);
        assertEqualValues("TestListFunctions.element", output, "element", 2);
        assertEqualValues("TestListFunctions.concat", output, "concat", 1, 2, 3, 1, 2, 3);
        log.info("done");
    }
    
    public void testHelloWorld() throws Exception {
        log.info("testHelloWorld");
        execute("HelloWorld");
        log.info("done");
    }
    
    private ParameterValueList execute(String activityName)
    {
        Behavior behavior = environment.findBehavior(activityName);
        if (behavior == null)
            throw new RuntimeException("invalid behavior, " + activityName);
        log.info("executing behavior: " + behavior.name);
        ExecutionEnvironment execution = new ExecutionEnvironment(environment);
        return execution.execute(behavior);
    }
    
}