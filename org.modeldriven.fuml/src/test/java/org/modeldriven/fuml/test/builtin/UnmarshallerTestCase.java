package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
import junit.framework.Test;

public class UnmarshallerTestCase extends BuiltInTest {

    private static Log log = LogFactory.getLog(UnmarshallerTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(UnmarshallerTestCase.class);
    }
    
    public void setUp() throws Exception {
    }
    
    public void testUnmarshaller() throws Exception {
        log.info("testUnmarshaller");
        ParameterValueList output = this.testSuite.testUnmarshaller();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 3, output.size());
        assertEqualValues("TestClass_Unmarshaller.x", output.get(0), 0);
        assertEqualValues("TestClass_Unmarshaller.y", output.get(1), false, false);
        assertEqualValues("TestClass_Unmarshaller.z", output.get(2));
    }
    
}
