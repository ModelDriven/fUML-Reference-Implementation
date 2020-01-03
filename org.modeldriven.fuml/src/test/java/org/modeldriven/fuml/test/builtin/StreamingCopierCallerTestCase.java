package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.syntax.structuredclassifiers.Class_;
import junit.framework.Test;

/**
 * 
 */
public class StreamingCopierCallerTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(StreamingCopierCallerTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(StreamingCopierCallerTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testStreamingCopierCaller() throws Exception {
    	Logger.getRootLogger().setLevel(Level.DEBUG);
        log.info("testStreamingCopierCaller");        
        ParameterValueList output = this.testSuite.testStreamingCopierCaller();       
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());        
        assertEqualValues("StreamingCopierCaller.output", output.get(0), 0, 0);
        
       	Class_ streamingCopier = (Class_)environment.getElement("StreamingCopier");
    	ExtensionalValueList extent = environment.locus.getExtent(streamingCopier);
    	assertEquals("StreamingCopier.extent.isEmpty", true, extent.isEmpty());
    }
    
}