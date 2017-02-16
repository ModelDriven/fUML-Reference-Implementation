package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class LoopNodeTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LoopNodeTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LoopNodeTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testLoopNode() throws Exception {
        log.info("testLoopNode");
        ParameterValueList output = this.testSuite.testLoopNode();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 6, output.size());
        assertEqualValues("LoopNodeTester_0.i", output.get(0), 0);
        assertEqualValues("LoopNodeTester_0.n", output.get(1), 1);
        assertEqualValues("LoopNodeTester_1.i", output.get(2), 0);
        assertEqualValues("LoopNodeTester_1.n", output.get(3), 1);
        assertEqualValues("LoopNodeTester_2.i", output.get(4), 0);
        assertEqualValues("LoopNodeTester_2.n", output.get(5), 2);
    }
    
}