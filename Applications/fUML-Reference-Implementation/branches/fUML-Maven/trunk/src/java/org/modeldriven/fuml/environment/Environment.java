/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file
 * entitled Licensing-Information.
 *
 * All modifications copyright 2009-2011 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.environment;



import org.modeldriven.fuml.common.uuid.UUIDGenerator;
import org.modeldriven.fuml.repository.Repository;

import UMLPrimitiveTypes.UnlimitedNatural;

import fUML.Debug;
import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.DataValue;
import fUML.Semantics.Classes.Kernel.EnumerationValue;
import fUML.Semantics.Classes.Kernel.FeatureValueList;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.PrimitiveValue;
import fUML.Semantics.Classes.Kernel.RealValue;
import fUML.Semantics.Classes.Kernel.Reference;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.StructuredValue;
import fUML.Semantics.Classes.Kernel.UnlimitedNaturalValue;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.Communications.SignalInstance;
import fUML.Semantics.Loci.LociL1.Executor;
import fUML.Semantics.Loci.LociL1.Locus;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.Classes.Kernel.StructuralFeature;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

public class Environment {
    private static Environment instance = null;

	public Locus locus = null;
	
	private PrimitiveType Boolean = null;
	private PrimitiveType String = null;
	private PrimitiveType Integer = null;
	private PrimitiveType Real = null;
	private PrimitiveType UnlimitedNatural = null;
 
	private Environment() {

		this.locus = new Locus();
		this.locus.setFactory(new ExecutionFactory());  // Uses local subclass for ExecutionFactory
		this.locus.setExecutor(new Executor());

		this.locus.factory
				.setStrategy(new fUML.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy());
		this.locus.factory
				.setStrategy(new fUML.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy());
		this.locus.factory
				.setStrategy(new fUML.Semantics.Loci.LociL1.FirstChoiceStrategy());
	
		this.Boolean = this.createBuiltInType("Boolean");
		this.String = this.createBuiltInType("String");
		this.Integer = this.createBuiltInType("Integer");
		this.Real = this.createBuiltInType("Real");
		this.UnlimitedNatural = this.createBuiltInType("UnlimitedNatural");

		// The fUML execution environment requires a single instance
        // of these primitive types to be used for execution purposes.
		// Give these types a "synthetic" XMI id such that they CAN be mapped
		// by XMI id by various repository implementations.
		this.Boolean.setXmiId(UUIDGenerator.instance().getIdString36());
		this.String.setXmiId(UUIDGenerator.instance().getIdString36());
		this.Integer.setXmiId(UUIDGenerator.instance().getIdString36());
		this.Real.setXmiId(UUIDGenerator.instance().getIdString36());
		this.UnlimitedNatural.setXmiId(UUIDGenerator.instance().getIdString36());
    }

	private PrimitiveType createBuiltInType(String name) {
		PrimitiveType type = new PrimitiveType();
		type.name = name;
		this.locus.factory.addBuiltInType(type);
		return type;
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
    	return this.Boolean;
    }
    
    public PrimitiveType getString() {
    	return this.String;
    }
    
    public PrimitiveType getInteger() {
    	return this.Integer;
    }
    
    public PrimitiveType getReal() {
    	return this.Real;
    }
    
    public PrimitiveType getUnlimitedNatural() {
    	return this.UnlimitedNatural;
    }

	public PrimitiveValue makePrimitiveValue(Classifier classifier) {
		PrimitiveType type = (PrimitiveType) classifier;
		PrimitiveValue primitiveValue = null;

		if (type == this.Boolean) {
			primitiveValue = new BooleanValue();
		} else if (type == this.Integer) {
			primitiveValue = new IntegerValue();
		} else if (type == this.Real) {
			primitiveValue = new RealValue();
		} else if (type == this.String) {
			primitiveValue = new StringValue();
		} else if (type == this.UnlimitedNatural) {
			primitiveValue = new UnlimitedNaturalValue();
			((UnlimitedNaturalValue) primitiveValue).value = new UnlimitedNatural();
		}

		if (primitiveValue != null) {
			primitiveValue.type = type;
		} else {
			Debug.println("[makePrimitiveValue] " + type.name
					+ " not understood.");
		}

		return primitiveValue;
	}

	public EnumerationValue makeEnumerationValue(Classifier classifier) {
		Enumeration type = (Enumeration) classifier;
		EnumerationValue enumerationValue = new EnumerationValue();

		enumerationValue.type = type;
		enumerationValue.literal = type.ownedLiteral.getValue(0);

		return enumerationValue;
	}

	public fUML.Semantics.Classes.Kernel.StructuredValue makeStructuredValue(
			fUML.Syntax.Classes.Kernel.Classifier classifier) {
		StructuredValue structuredValue = null;

		if (classifier instanceof DataType) {
			structuredValue = new DataValue();
			((DataValue) structuredValue).type = (DataType) classifier;
			structuredValue.createFeatureValues();
		} else if (classifier instanceof Class_) {
			structuredValue = new Reference();
			((Reference) structuredValue).referent = this.locus
					.instantiate((Class_) classifier);
		} else if (classifier instanceof Signal) {
			structuredValue = new SignalInstance();
			((SignalInstance) structuredValue).type = (Signal) classifier;
			structuredValue.createFeatureValues();
		}

		FeatureValueList featureValues = structuredValue.getFeatureValues();

		for (int i = 0; i < featureValues.size(); i++) {
			StructuralFeature feature = featureValues.getValue(i).feature;
			ValueList valueList = new ValueList();
			valueList.addValue(this
					.makeValue((Classifier) (feature.typedElement.type)));
			structuredValue.setFeatureValue(feature, valueList, 0);
		}

		return structuredValue;

	}

	public Value makeValue(Classifier type) {

		if (type == null) {
			return this.makePrimitiveValue(this.String);
		} else if (type instanceof PrimitiveType) {
			return this.makePrimitiveValue(type);
		} else if (type instanceof Enumeration) {
			return this.makeEnumerationValue(type);
		} else {
			return this.makeStructuredValue(type);
		}
	} // makeValue

}
