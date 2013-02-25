package org.modeldriven.fuml.test.builtin;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Test.InitTestEnvironment;

import junit.framework.Test;

/**
 * 
 */
public class WriterReaderTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(WriterReaderTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(WriterReaderTestCase.class);
    }
    
    public void setUp() throws Exception {
    }

    public void testWriterReader() throws Exception {
        log.info("testWriterReader");
        initTestEnv.testSuite.testWriterReader();
        log.info("done");
    }
    
}