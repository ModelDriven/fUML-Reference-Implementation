/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009-2012 Data Access Technologies, Inc.
 * Copyright 2020 Model Driven Solutions, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.test.builtin.environment;

import fuml.Debug;
import fuml.syntax.classification.AggregationKind;
import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.Generalization;
import fuml.syntax.classification.Operation;
import fuml.syntax.classification.Property;
import fuml.syntax.commonbehavior.Behavior;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.simpleclassifiers.DataType;
import fuml.syntax.simpleclassifiers.Enumeration;
import fuml.syntax.simpleclassifiers.EnumerationLiteral;
import fuml.syntax.simpleclassifiers.Signal;
import fuml.syntax.structuredclassifiers.Association;
import fuml.syntax.structuredclassifiers.Class_;

public class ClassifierFactory {

	public TestEnvironment environment = null;

	public ClassifierFactory(org.modeldriven.fuml.test.builtin.environment.TestEnvironment environment) {
		this.environment = environment;
	} // ClassifierFactory

	public Enumeration createEnumerationType(String typeName, int numberOfLiterals) {
		Enumeration type = new Enumeration();

		type.setName(typeName);

		for (int i = 0; i < numberOfLiterals; i++) {
			EnumerationLiteral literal = new EnumerationLiteral();

			literal.setName(typeName + "_" + String.valueOf(i));
			type.addOwnedLiteral(literal);
		}

		environment.addElement(type);
		
		return type;
	} // createEnumerationType

	public DataType createDataType(String name) {
		DataType dataType = new DataType();
		dataType.setName(name);
		environment.addElement(dataType);
		return dataType;
	} // createDataType

	public Class_ createClass(String name) {
		Class_ class_ = new Class_();
		class_.setName(name);
		environment.addElement(class_);
		return class_;
	} // createClass

	public Signal createSignal(String name) {
		Signal signal = new Signal();
		signal.setName(name);
		this.environment.addElement(signal);
		return signal;
	} // createSignal

	public Association createAssociation(String name) {
		Association association = new Association();
		association.setName(name);
		environment.addElement(association);
		return association;
	} // createAssociation
	
	public Property addAttribute(String classifierName, String attributeName,
			String attributeTypeName, boolean isComposite, boolean isUnique, int lower, int upper) {
		Property attribute = addAttribute(classifierName, attributeName, attributeTypeName, isComposite);
		if (attribute != null) {
			attribute.setIsUnique(isUnique);
			attribute.setLower(lower);
			attribute.setUpper(upper);
		}
		return attribute;
	}
	
	public Property addAttribute(String classifierName, String attributeName,
			String attributeTypeName, boolean isComposite) {
		Classifier type = environment.getType(classifierName);

		if (type == null) {
			Debug.println("[addAttribute] " + classifierName
					+ " not found or not a classifier.");
			return null;
		}

		Classifier attributeType = environment.getType(attributeTypeName);

		if (attributeType == null) {
			Debug.println("[addAttribute] " + attributeTypeName
					+ " not found or not a classifier.");
			return null;
		}

		Property attribute = new Property();
		attribute.setName(attributeName);
		attribute.setType(attributeType);
		attribute.setIsOrdered(false);
		attribute.setIsUnique(true);
		attribute.setLower(1);
		attribute.setUpper(1);

		if (isComposite) {
			attribute.setAggregation(AggregationKind.composite);
		} else {
			attribute.setAggregation(AggregationKind.none);
		}

		if (type instanceof DataType) {
			// Debug.println("[addAttribute] " + type.name +
			// " is a data type.");
			((DataType) type).addOwnedAttribute(attribute);
		} else if (type instanceof Class_) {
			// Debug.println("[addAttribute] " + type.name + " is a class.");
			((Class_) type).addOwnedAttribute(attribute);
		} else if (type instanceof Signal) {
			((Signal) type).addOwnedAttribute(attribute);
		}
		
		return attribute;
	} // addAttribute

	public Property addEnd(String associationName, String endName,
			String endTypeName, boolean isComposite) {
		Classifier type = environment.getType(associationName);

		if (type == null || !(type instanceof Association)) {
			Debug.println("[addEnd] " + associationName
					+ " not found or not an association.");
			return null;
		}

		Association association = (Association) type;

		Classifier endType = environment.getType(endTypeName);

		if (endType == null) {
			Debug.println("[addEnd] " + endTypeName
					+ " not found or not a classifier.");
			return null;
		}

		Property end = new Property();
		end.setName(endName);
		end.setType(endType);
		end.setIsOrdered(false);
		end.setIsUnique(true);
		end.setLower(1);
		end.setUpper(1);

		if (isComposite) {
			end.setAggregation(AggregationKind.composite);
		} else {
			end.setAggregation(AggregationKind.none);
		}

		association.addOwnedEnd(end);
		association.addNavigableOwnedEnd(end);
		
		return end;
	} // addEnd

	public Class_ addClassifierBehavior(String className, String behaviorName) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[addClassifierBehavior] " + className
					+ " not found or not a class.");
			return null;
		}

		Class_ classifier = (Class_) element;

		element = this.environment.getElement(behaviorName);

		if (element == null || !(element instanceof Behavior)) {
			Debug.println("[addClassifierBehavior] " + behaviorName
					+ " not found or not a behavior.");
			return null;
		}

		Behavior behavior = (Behavior) element;
		this.environment.removeElement(element);

		classifier.addOwnedBehavior(behavior);
		classifier.setClassifierBehavior(behavior);
		
		return classifier;
	} // addClassifierBehavior

	public Operation addOperation(String className, String baseClassName,
			String operationName, String methodName) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[addOperation] " + className
					+ " not found or not a class.");
			return null;
		}

		Class_ classifier = (Class_) element;

		Operation operation = new Operation();
		operation.setName(operationName);

		if (!baseClassName.equals("")) {
			element = this.environment.getElement(baseClassName);

			if (element == null || !(element instanceof Class_)) {
				Debug.println("[addOperation] " + baseClassName
						+ " not found or not a class.");
				return null;
			}

			Class_ baseClass = (Class_) element;
			Operation redefinedOperation = this.getOperation(baseClass,
					operationName);

			if (redefinedOperation == null) {
				Debug.println("[addOperation] " + operationName
						+ " is not an operation of " + baseClassName + ".");
				return null;
			}

			operation.addRedefinedOperation(redefinedOperation);
		}

		if (methodName.equals("")) {
			operation.setIsAbstract(true);
		} else {
			element = this.environment.getElement(methodName);

			if (element == null || !(element instanceof Behavior)) {
				Debug.println("[addOperation] " + methodName
						+ " not found or not a behavior.");
				return null;
			}

			Behavior behavior = (Behavior) element;
			this.environment.removeElement(element);
			classifier.addOwnedBehavior(behavior);

			operation.addMethod(behavior);
		}

		classifier.addOwnedOperation(operation);
		
		return operation;
	} // addOperation

	public Generalization addGeneralization(String subtypeName, String supertypeName) {
		Classifier subtype = this.environment.getType(subtypeName);

		if (subtype == null) {
			Debug.println("[addGeneralization] " + subtypeName
					+ " not found or not a classifier.");
			return null;
		}

		Classifier supertype = this.environment.getType(supertypeName);

		if (supertype == null) {
			Debug.println("[addGeneralization] " + supertypeName
					+ " not found or not a classifier.");
			return null;
		}

		Generalization generalization = new Generalization();
		generalization.setGeneral(supertype);
		subtype.addGeneralization(generalization);
		
		return generalization;
	} // addGeneralization

	protected Operation getOperation(Class_ class_, String operationName) {
		for (int i = 0; i < class_.member.size(); i++) {
			NamedElement member = class_.member.getValue(i);
			if (member.name.equals(operationName)) {
				if (!(member instanceof Operation)) {
					return null;
				}
				return (Operation) member;
			}
		}

		return null;
	} // getOperation

} // ClassifierFactory
