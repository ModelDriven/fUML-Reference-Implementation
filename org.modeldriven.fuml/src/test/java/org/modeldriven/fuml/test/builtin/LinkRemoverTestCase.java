package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fUML.Semantics.Classes.Kernel.ExtensionalValue;
import fUML.Semantics.Classes.Kernel.ExtensionalValueList;
import fUML.Semantics.Classes.Kernel.FeatureValue;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import junit.framework.Test;

/**
 * 
 */
public class LinkRemoverTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkRemoverTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkRemoverTestCase.class);
    }
    
    public void setUp() throws Exception {
    	this.clearExtents();
    }

    public void testLinkRemover() throws Exception {
        log.info("testLinkRemover");
        ParameterValueList output = this.testSuite.testLinkRemover();
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 2, output.size());
        assertEquals("output[0].values.size()", 1, output.get(0).values.size());
        assertEquals("output[1].values.size()", 1, output.get(1).values.size());
        
        Value output1 = output.get(0).values.get(0);
        Value output2 = output.get(1).values.get(0);
        
        ExtensionalValueList extent = this.findExtent("AB");
                
        assertEquals("extent.size()", 1, extent.size());
        
        ExtensionalValue link = extent.get(0);
        assertEquals("link.featureValues.size()", 2, link.getFeatureValues().size());
        FeatureValue featureValueA = link.getFeatureValues().get(1);
        assertEquals("featureValueA.values.size()", 1, featureValueA.values.size());
        Value valueA = featureValueA.values.get(0);
        assertSame("valueA", output1, valueA);
        FeatureValue featureValueB = link.getFeatureValues().get(0);
        assertEquals("featureValueB.values.size()", 1, featureValueB.values.size());
        assertEquals("featureValueB.position", 1, featureValueB.position);
        Value valueB = featureValueB.values.get(0);
        assertSame("valueB", output2, valueB);
    }
    
}