package org.modeldriven.fuml.test.builtin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.test.FUMLTest;
import org.modeldriven.fuml.test.builtin.environment.TestEnvironment;
import org.modeldriven.fuml.test.builtin.environment.TestSuite;

import fUML.Semantics.Classes.Kernel.ExtensionalValueList;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class BuiltInTest extends FUMLTest {

	protected Log log = LogFactory.getLog(getClass().getName());
	protected TestEnvironment environment;
	protected TestSuite testSuite;

    public BuiltInTest() {
    	this.environment = new TestEnvironment();
    	this.testSuite = new TestSuite(this.environment);
    }
    
    public BuiltInTest(String name) {
        super(name);
    	this.environment = new TestEnvironment();
    	this.testSuite = new TestSuite(this.environment);
    }
    
    public void clearExtents() {
    	this.environment.locus.extensionalValues.clear();
    }
    
    public ExtensionalValueList findExtent(String classifierName) {
    	Element element = this.findElement(classifierName);
        return element == null? null: 
        	this.environment.locus.getExtent((Classifier)element);    	
    }

    public Element findElement(String classifierName) {
    	
        Element element = this.environment.getElement(classifierName);

        if (!(element instanceof Classifier)) {
        	log.warn(classifierName + " is not a classifier.");
            return null;
        }
        
        return element;    	
    }
    
}
