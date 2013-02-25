package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class LinkWriterTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkWriterTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkWriterTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testLinkWriter() throws Exception {
        log.info("testLinkWriter");
        initTestEnv.testSuite.testLinkWriter();
        log.info("done");
    }
    
}