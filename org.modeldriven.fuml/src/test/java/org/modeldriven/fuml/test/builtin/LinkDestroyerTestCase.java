package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.classification.Value;
import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.structuredclassifiers.ExtensionalValue;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import junit.framework.Test;

/**
 * 
 */
public class LinkDestroyerTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkDestroyerTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkDestroyerTestCase.class);
    }
    
    public void setUp() throws Exception {
    	this.clearExtents();
    }

    public void testLinkDestroyer() throws Exception {
        log.info("testLinkDestroyer");
        ParameterValueList output = this.testSuite.testLinkDestroyer();
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
        FeatureValue featureValueA = link.getFeatureValues().get(0);
        assertEquals("featureValueA.values.size()", 1, featureValueA.values.size());
        Value valueA = featureValueA.values.get(0);
        assertSame("valueA", output1, valueA);
        FeatureValue featureValueB = link.getFeatureValues().get(1);
        assertEquals("featureValueB.values.size()", 1, featureValueB.values.size());
        Value valueB = featureValueB.values.get(0);
        assertNotSame("valueB", output2, valueB);       
    }
    
}