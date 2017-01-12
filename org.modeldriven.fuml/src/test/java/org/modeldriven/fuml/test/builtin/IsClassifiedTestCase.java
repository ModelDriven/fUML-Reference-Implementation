package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class IsClassifiedTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(IsClassifiedTestCase.class);
    

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(IsClassifiedTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testIsClassified() throws Exception {
        log.info("testIsClassified");
        ParameterValueList output = initTestEnv.testSuite.testIsClassified();       
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());
        assertEquals("TestIsSignal.output: values.size()", 1, output.get(0).values.size());
        assertTrue("TestIsSignal.output: value instanceof BooleanValue", 
        		output.get(0).values.get(0) instanceof BooleanValue);
        assertTrue("TestIsSignal.output: value", 
        		((BooleanValue)output.get(0).values.get(0)).value);
    }
    
}