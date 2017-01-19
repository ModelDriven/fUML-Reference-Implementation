package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class StructuredNodeTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(StructuredNodeTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(StructuredNodeTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testStructuredNode() throws Exception {
        log.info("testStructuredNode");
        ParameterValueList output = this.testSuite.testStructuredNode();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("StructuredForkMergeInput.output", output.get(0), 0, 0);
    }
    
}