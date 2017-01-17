package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class CallSendTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(CallSendTestCase.class);
    

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(CallSendTestCase.class);
    }
    
    public void setUp() throws Exception {
    	initTestEnv.environment.locus.extensionalValues.clear();
    }

    public void testCallSend() throws Exception {
        log.info("testCallSend");
        ParameterValueList output = initTestEnv.testSuite.testCallSend();        
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("output", output.get(0), 0);
    }
    
}