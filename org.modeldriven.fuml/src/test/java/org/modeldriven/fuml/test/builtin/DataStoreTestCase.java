package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

public class DataStoreTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(DataStoreTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(DataStoreTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testDataStore() throws Exception {
        log.info("testDataStore");
        ParameterValueList output = this.testSuite.testDataStore();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());
        assertEqualValues("testDataStore.output", output.get(0), 1, 2);
    }
    
}