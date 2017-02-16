package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class FireAgainTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(FireAgainTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(FireAgainTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testFireAgain() throws Exception {
        log.info("testFireAgain");
        ParameterValueList output = this.testSuite.testFireAgain();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());
        assertEqualValues("FireAgainTester.output", output.get(0), 1, 2);
    }
    
}