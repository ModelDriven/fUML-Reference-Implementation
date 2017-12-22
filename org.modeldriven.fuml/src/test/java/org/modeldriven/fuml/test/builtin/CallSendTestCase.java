package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.activities.ActivityExecution;
import fuml.semantics.simpleclassifiers.FeatureValueList;
import fuml.semantics.structuredclassifiers.ExtensionalValue;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import junit.framework.Test;

public class CallSendTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(CallSendTestCase.class);
    

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(CallSendTestCase.class);
    }
    
    public void setUp() throws Exception {
    	this.clearExtents();;
    }

    public void testCallSend() throws Exception {
        log.info("testCallSend");
        this.testSuite.testCallSend();        
        log.info("done");
        
        ExtensionalValueList extent = this.findExtent("TestCallSender");
        
        assertEquals("extent.size()", 1, extent.size());
        ExtensionalValue accepterExecution = extent.get(0);
        assertTrue("accepterExecution instanceof ActivityExecution", 
        		accepterExecution instanceof ActivityExecution);
        FeatureValueList featureValues = accepterExecution.getFeatureValues();
        assertEquals("featureValues.size()", 1, featureValues.size());
        assertEquals("featureValues[0].values.size()", 1, featureValues.get(0).values.size());
    }
    
}