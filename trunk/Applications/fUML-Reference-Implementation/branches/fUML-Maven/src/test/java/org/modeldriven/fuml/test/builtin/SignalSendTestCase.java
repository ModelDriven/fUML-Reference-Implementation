package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.CommonBehaviors.Communications.Signal;
import fUML.Test.InitTestEnvironment;

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
    }

    public void testSignalSend() throws Exception {
        log.info("testSignalSend");
        initTestEnv.testSuite.testSignalSend();
        
        initTestEnv.environment.printElements();

        // see if there is one
        Element element = findElement("TestSignal");
        assertTrue(element != null);
        assertTrue(element instanceof Signal);
        Signal signal = (Signal)element;
        this.assertTrue("TestSignal".equals(signal.name));

        
        element = findElement("TestSignalAccepter");
        assertTrue(element != null);
        assertTrue(element instanceof Activity);
        Activity accepter = (Activity)element;
         
        
        log.info("done");
    }
    
}