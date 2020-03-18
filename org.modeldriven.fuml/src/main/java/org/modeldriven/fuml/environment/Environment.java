/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * Modifications:
 * Copyright 2009-2013 Data Access Technologies, Inc.
 * Copyright 2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS
 *
 */
package org.modeldriven.fuml.environment;

import org.modeldriven.fuml.repository.Repository;

import UMLPrimitiveTypes.UnlimitedNatural;
import fuml.Debug;
import fuml.semantics.loci.Executor;
import fuml.semantics.loci.Locus;
import fuml.semantics.simpleclassifiers.BooleanValue;
import fuml.semantics.simpleclassifiers.DataValue;
import fuml.semantics.simpleclassifiers.EnumerationValue;
import fuml.semantics.simpleclassifiers.FeatureValueList;
import fuml.semantics.simpleclassifiers.IntegerValue;
import fuml.semantics.simpleclassifiers.PrimitiveValue;
import fuml.semantics.simpleclassifiers.RealValue;
import fuml.semantics.simpleclassifiers.SignalInstance;
import fuml.semantics.simpleclassifiers.StringValue;
import fuml.semantics.simpleclassifiers.StructuredValue;
import fuml.semantics.simpleclassifiers.UnlimitedNaturalValue;
import fuml.semantics.structuredclassifiers.Reference;
import fuml.semantics.values.Value;
import fuml.semantics.values.ValueList;
import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.Property;
import fuml.syntax.classification.StructuralFeature;
import fuml.syntax.commonbehavior.Behavior;
import fuml.syntax.commonstructure.Element;
import fuml.syntax.simpleclassifiers.DataType;
import fuml.syntax.simpleclassifiers.Enumeration;
import fuml.syntax.simpleclassifiers.PrimitiveType;
import fuml.syntax.simpleclassifiers.Signal;
import fuml.syntax.structuredclassifiers.Class_;

public class Environment {
    private static Environment instance = null;

	public Locus locus = null;
	
	private PrimitiveType Boolean = null;
	private PrimitiveType String = null;
	private PrimitiveType Integer = null;
	private PrimitiveType Real = null;
	private PrimitiveType UnlimitedNatural = null;
 
	private Environment(ExecutionFactory factory) {

		this.locus = new Locus();
		this.locus.setFactory(factory);
		this.locus.setExecutor(new Executor());

		this.locus.factory
				.setStrategy(new fuml.semantics.structuredclassifiers.RedefinitionBasedDispatchStrategy());
		this.locus.factory
				.setStrategy(new fuml.semantics.commonbehavior.FIFOGetNextEventStrategy());
		this.locus.factory
				.setStrategy(new fuml.semantics.loci.FirstChoiceStrategy());
		
		this.Boolean = this.addBuiltInType("Boolean");
		this.String = this.addBuiltInType("String");
		this.Integer = this.addBuiltInType("Integer");
		this.Real = this.addBuiltInType("Real");
		this.UnlimitedNatural = this.addBuiltInType("UnlimitedNatural");

	}

	private PrimitiveType addBuiltInType(String name) {
		PrimitiveType type = (PrimitiveType)Repository.INSTANCE.
				getClassifierByName(name).getDelegate();
		this.locus.factory.addBuiltInType(type);
		return type;
	}
	
	public static Environment getInstance() {
		return getInstance(new ExecutionFactory()); // Uses local subclass for ExecutionFactory
	}
	
    public static Environment getInstance(ExecutionFactory factory)
    {
        if (instance == null)
            initializeInstance(factory);
        return instance;
    }

    private static synchronized void initializeInstance(ExecutionFactory factory)
    {
        if (instance == null)
            instance = new Environment(factory);
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

	public fuml.semantics.simpleclassifiers.StructuredValue makeStructuredValue(
			fuml.syntax.classification.Classifier classifier) {
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
			if (feature instanceof Property && ((Property)feature).association == null) {
				ValueList valueList = new ValueList();
				valueList.addValue(this.makeValue((Classifier) (feature.typedElement.type)));
				structuredValue.setFeatureValue(feature, valueList, 0);
			}
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
