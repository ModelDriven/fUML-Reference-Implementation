package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class ExpansionRegionTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(ExpansionRegionTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(ExpansionRegionTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testExpansionRegion() throws Exception {
        log.info("testExpansionRegion");
        ParameterValueList output = initTestEnv.testSuite.testExpansionRegion();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 6, output.size());
        assertIntegerValues("ExpansionRegionTester_0.list", output.get(0));
        assertIntegerValues("ExpansionRegionTester_0.list*10", output.get(1));
        assertIntegerValues("ExpansionRegionTester_1.list", output.get(2), 1);
        assertIntegerValues("ExpansionRegionTester_1.list*10", output.get(3), 10);
        assertIntegerValues("ExpansionRegionTester_2.list", output.get(4), 1, 2);
        assertIntegerValues("ExpansionRegionTester_2.list*10", output.get(5), 10, 20);
    }
    
}