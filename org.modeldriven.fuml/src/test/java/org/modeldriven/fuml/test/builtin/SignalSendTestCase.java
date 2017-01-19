package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.Activities.IntermediateActivities.ActivityExecution;
import fUML.Semantics.Classes.Kernel.ExtensionalValue;
import fUML.Semantics.Classes.Kernel.ExtensionalValueList;
import fUML.Semantics.Classes.Kernel.FeatureValueList;
import junit.framework.Test;

/**
 * 
 */
public class SignalSendTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(SignalSendTestCase.class);
    

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(SignalSendTestCase.class);
    }
    
    public void setUp() throws Exception {
    	this.clearExtents();
    }

    public void testSignalSend() throws Exception {
        log.info("testSignalSend");
        this.testSuite.testSignalSend();        
        log.info("done");
        
        ExtensionalValueList extent = this.findExtent("TestSignalAccepter");
        
        assertEquals("extent.size()", 1, extent.size());
        ExtensionalValue accepterExecution = extent.get(0);
        assertTrue("accepterExecution instanceof ActivityExecution", 
        		accepterExecution instanceof ActivityExecution);
        FeatureValueList featureValues = accepterExecution.getFeatureValues();
        assertEquals("featureValues.size()", 1, featureValues.size());
        assertEquals("featureValues[0].values.size()", 1, featureValues.get(0).values.size());
    }
    
}