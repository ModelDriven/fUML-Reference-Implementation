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
public class IsClassifiedTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(IsClassifiedTestCase.class);
    

    public static Test suite() {
        return FUMLTestSetup.newTestSetup(IsClassifiedTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testIsClassified() throws Exception {
        log.info("testIsClassified");
        initTestEnv.testSuite.testIsClassified();
        
        log.info("done");
    }
    
}