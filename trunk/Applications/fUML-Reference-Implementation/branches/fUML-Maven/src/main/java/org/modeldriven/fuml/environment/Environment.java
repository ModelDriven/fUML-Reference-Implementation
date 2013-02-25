/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file
 * entitled Licensing-Information.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.environment;



import java.util.UUID;

import org.modeldriven.fuml.repository.Repository;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Test.TestEnvironment;

public class Environment extends TestEnvironment {
    private static Environment instance = null;

    private Environment() {

		this.locus.setFactory(new ExecutionFactory());  // Uses local subclass for ExecutionFactory

		this.locus.factory
				.setStrategy(new fUML.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy());
		this.locus.factory
				.setStrategy(new fUML.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy());
		this.locus.factory.setStrategy(new fUML.Semantics.Loci.LociL1.FirstChoiceStrategy());
	
        // The fUML execution environment requires a single instance
        // of these primitive types to be used for execution purposes.
		// Give these types a "synthetic" XMI id such that they CAN be mapped
		// by XMI id by various repository implementations.
		this.primitiveTypes.Boolean.setXmiId(UUID.randomUUID().toString());
		this.primitiveTypes.String.setXmiId(UUID.randomUUID().toString());
		this.primitiveTypes.Integer.setXmiId(UUID.randomUUID().toString());
		this.primitiveTypes.UnlimitedNatural.setXmiId(UUID.randomUUID().toString());
		this.primitiveTypes.Real.setXmiId(UUID.randomUUID().toString());
    }

    public static Environment getInstance()
    {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance()
    {
        if (instance == null)
            instance = new Environment();
    }

    public Behavior findBehavior(String name)
    {
    	org.modeldriven.fuml.repository.Element elem = Repository.INSTANCE.findElementByName(name);
    	if (elem != null) {
    		if (elem.getDelegate() instanceof Behavior)
    		    return (Behavior)elem.getDelegate();
    		else
    			throw new EnvironmentException("Element '" + name + "' is not a Behavior, it is a '"
    					+ elem.getDelegate().getClass().getSimpleName() + "'");
    	}
    	else
    		return null;
    }

    public Element findElementById(String id)
    {
    	org.modeldriven.fuml.repository.Element elem = Repository.INSTANCE.findElementById(id);
        if (elem != null)
        	return elem.getDelegate();
        else
    	    return null;
    }

    public int getBehaviorCount()
    {
    	return Repository.INSTANCE.getElementCount(Behavior.class);
    }

    public String[] getBehaviorNames()
    {
    	return Repository.INSTANCE.getElementNames(Behavior.class);
    }
    
    public PrimitiveType getBoolean() {
    	return this.primitiveTypes.Boolean;
    }
    
    public PrimitiveType getString() {
    	return this.primitiveTypes.String;
    }
    
    public PrimitiveType getInteger() {
    	return this.primitiveTypes.Integer;
    }
    
    public PrimitiveType getUnlimitedNatural() {
    	return this.primitiveTypes.UnlimitedNatural;
    }
    
    public PrimitiveType getReal() {
    	return this.primitiveTypes.Real;
    }

}
