package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTestSetup;

import fuml.semantics.commonbehavior.ParameterValueList;
import fuml.semantics.simpleclassifiers.FeatureValue;
import fuml.semantics.structuredclassifiers.ExtensionalValue;
import fuml.semantics.structuredclassifiers.ExtensionalValueList;
import fuml.semantics.values.Value;
import junit.framework.Test;

/**
 * 
 */
public class LinkReaderTestCase extends BuiltInTest {
    private static Log log = LogFactory.getLog(LinkReaderTestCase.class);
    
    public static Test suite() {
        return FUMLTestSetup.newTestSetup(LinkReaderTestCase.class);
    }
    
    public void setUp() throws Exception {
    	this.clearExtents();
    }

    public void runTest(int n) throws Exception {
        log.info("testLinkReader with " + n + " association-owned end(s)");
        ParameterValueList output = this.testSuite.testLinkReader(n);
        log.info("done");
        
        assertNotNull(output);
        assertEquals("output.size()", 2, output.size());
        assertEquals("output[0].values.size()", 2, output.get(0).values.size());
        assertEquals("output[1].values.size()", 2, output.get(1).values.size());
        
        Value linkOutput1 = output.get(0).values.get(0);
        Value linkOutput2 = output.get(0).values.get(1);
        
        Value featureOutput1 = output.get(1).values.get(0);
        Value featureOutput2 = output.get(1).values.get(1);
        
        ExtensionalValueList extent = this.findExtent("AB");
                
        assertEquals("extent.size()", 2, extent.size());
        
        ExtensionalValue link1 = extent.get(0);
        assertEquals("link1.featureValues.size()", 2, link1.getFeatureValues().size());
        FeatureValue featureValue1a = link1.getFeatureValues().get(0);
        assertEquals("featureValue1a.values.size()", 1, featureValue1a.values.size());
        FeatureValue featureValue1b = link1.getFeatureValues().get(1);
        assertEquals("featureValue1b.values.size()", 1, featureValue1b.values.size());
        
        ExtensionalValue link2 = extent.get(1);
        assertEquals("link2.featureValues.size()", 2, link2.getFeatureValues().size());
        FeatureValue featureValue2a = link2.getFeatureValues().get(0);
        assertEquals("featureValue2a.values.size()", 1, featureValue2a.values.size());
        FeatureValue featureValue2b = link2.getFeatureValues().get(1);
        assertEquals("featureValue2b.values.size()", 1, featureValue2b.values.size());
        
        Value value1b = featureValue1b.values.get(0);
        Value value2b = featureValue2b.values.get(0);
        assertSame("value1b (end)", linkOutput2, value1b);
        assertSame("value1b (feature)", featureOutput2, value1b);
        assertSame("value2b (end)", linkOutput1, value2b);
        assertSame("value2b (feature)", featureOutput1, value2b);
    }
    
    public void testLinkReaderWithNoOwnedEnds() throws Exception {
    	this.runTest(0);
    }
    
    public void testLinkReaderWithOneOwnedEnd() throws Exception {
    	this.runTest(1);
    }
    
    public void testLinkReaderWithTwoOwnedEnds() throws Exception {
    	this.runTest(2);
    }
    
}