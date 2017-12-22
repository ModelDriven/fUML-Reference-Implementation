package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
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
        ParameterValueList output = this.testSuite.testConditionalNode();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 3, output.size());
        assertEqualValues("ConditionalNodeTester_1.output", output.get(0), 0);
        assertEqualValues("ConditionalNodeTester_2.output", output.get(1), 1);
        assertEqualValues("ConditionalNodeTester_3.output", output.get(2), 2);
    }
    
}