package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class ConditionalNodeTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(ConditionalNodeTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(ConditionalNodeTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testConditionalNode() throws Exception {
        log.info("testConditionalNode");
        ParameterValueList output = initTestEnv.testSuite.testConditionalNode();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 3, output.size());
        assertIntegerValues("ConditionalNodeTester_1.output", output.get(0), 0);
        assertIntegerValues("ConditionalNodeTester_2.output", output.get(1), 1);
        assertIntegerValues("ConditionalNodeTester_3.output", output.get(2), 2);
    }
    
}