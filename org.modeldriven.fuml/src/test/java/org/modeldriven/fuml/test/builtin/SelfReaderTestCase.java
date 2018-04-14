package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.activities.ActivityExecution;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import junit.framework.Test;

public class SelfReaderTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(SelfReaderTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(SelfReaderTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testSelfReader() throws Exception {
        log.info("testSelfReader");        
        ParameterValueList output = this.testSuite.testSelfReader();        
        log.info("done");
        
        assertNotNull(output);
        assertTrue("output.size()", 
        		output.size() == 1 && output.get(0).values.size() == 1);
        Value value = output.get(0).values.get(0);
        assertTrue("value.referent instanceof ActivityExecution", 
        		value instanceof Reference && 
        		((Reference)value).referent instanceof ActivityExecution);
    }
    
}