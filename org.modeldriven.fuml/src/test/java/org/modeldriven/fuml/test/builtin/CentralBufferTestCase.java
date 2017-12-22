package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
import junit.framework.Test;

public class CentralBufferTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(CentralBufferTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(CentralBufferTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testCentralBuffer() throws Exception {
        log.info("testCentralBuffer");
        ParameterValueList output = this.testSuite.testCentralBuffer();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());
        assertEqualValues("testCentralBuffer.output", output.get(0), 0, 0);
    }
    
}