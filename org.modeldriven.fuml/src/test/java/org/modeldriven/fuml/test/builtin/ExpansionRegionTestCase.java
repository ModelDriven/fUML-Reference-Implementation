package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
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
        ParameterValueList output = this.testSuite.testExpansionRegion();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 6, output.size());
        assertEqualValues("ExpansionRegionTester_0.list", output.get(0));
        assertEqualValues("ExpansionRegionTester_0.list*10", output.get(1));
        assertEqualValues("ExpansionRegionTester_1.list", output.get(2), 1);
        assertEqualValues("ExpansionRegionTester_1.list*10", output.get(3), 10);
        assertEqualValues("ExpansionRegionTester_2.list", output.get(4), 1, 2);
        assertEqualValues("ExpansionRegionTester_2.list*10", output.get(5), 10, 20);
    }
    
}