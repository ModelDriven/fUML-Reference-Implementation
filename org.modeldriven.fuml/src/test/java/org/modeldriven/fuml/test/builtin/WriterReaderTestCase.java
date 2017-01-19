package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.Classes.Kernel.ExtensionalValue;
import fUML.Semantics.Classes.Kernel.ExtensionalValueList;
import fUML.Semantics.Classes.Kernel.FeatureValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

public class WriterReaderTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(WriterReaderTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(WriterReaderTestCase.class);
    }
    
    public void setUp() throws Exception {
    	this.clearExtents();
   }

    public void testWriterReader() throws Exception {
        log.info("testWriterReader");
        ParameterValueList output = this.testSuite.testWriterReader();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 1, output.size());
        assertIntegerValues("TestClass_x_WriterReader.x", output.get(0), 0);
        
        int x = ((IntegerValue)output.get(0).values.get(0)).value;
        
        ExtensionalValueList extent = this.findExtent("TestClass");
        
        assertEquals("extent.size()", 1, extent.size());
        
        ExtensionalValue object = extent.get(0);
        assertEquals("object.featureValues.size()", 1, object.getFeatureValues().size());
        FeatureValue featureValue = object.getFeatureValues().get(0);
        assertIntegerValues("object.x", featureValue.values, x);
    }
    
}