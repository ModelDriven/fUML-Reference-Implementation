
/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2012 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package fUML.Test;

import fUML.Debug;
import UMLPrimitiveTypes.*;

import fUML.Syntax.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Actions.CompleteActions.*;

public class ClassifierFactory extends fUML.Test.Test {

	public ClassifierFactory(fUML.Test.TestEnvironment environment) {
		this.environment = environment;
	} // ClassifierFactory

	public void createEnumerationType(String typeName, int numberOfLiterals) {
		Enumeration type = new Enumeration();

		type.setName(typeName);

		for (int i = 0; i < numberOfLiterals; i++) {
			EnumerationLiteral literal = new EnumerationLiteral();

			literal.setName(typeName + "_" + String.valueOf(i));
			type.addOwnedLiteral(literal);
		}

		environment.addElement(type);
	} // createEnumerationType

	public void createDataType(String name) {
		DataType dataType = new DataType();
		dataType.setName(name);
		environment.addElement(dataType);
	} // createDataType

	public void createClass(String name) {
		Class_ class_ = new Class_();
		class_.setName(name);
		environment.addElement(class_);
	} // createClass

	public void createSignal(String name) {
		Signal signal = new Signal();
		signal.setName(name);
		this.environment.addElement(signal);
	} // createSignal

	public void createAssociation(String name) {
		Association association = new Association();
		association.setName(name);
		environment.addElement(association);
	} // createAssociation

	public void addAttribute(String classifierName, String attributeName,
			String attributeTypeName, boolean isComposite) {
		Classifier type = environment.getType(classifierName);

		if (type == null) {
			Debug.println("[addAttribute] " + classifierName
					+ " not found or not a classifier.");
			return;
		}

		Classifier attributeType = environment.getType(attributeTypeName);

		if (attributeType == null) {
			Debug.println("[addAttribute] " + attributeTypeName
					+ " not found or not a classifier.");
			return;
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
	} // addAttribute

	public void addEnd(String associationName, String endName,
			String endTypeName, boolean isComposite) {
		Classifier type = environment.getType(associationName);

		if (type == null || !(type instanceof Association)) {
			Debug.println("[addEnd] " + associationName
					+ " not found or not an association.");
			return;
		}

		Association association = (Association) type;

		Classifier endType = environment.getType(endTypeName);

		if (endType == null) {
			Debug.println("[addEnd] " + endTypeName
					+ " not found or not a classifier.");
			return;
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

	} // addEnd

	public void addClassifierBehavior(String className, String behaviorName) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[addClassifierBehavior] " + className
					+ " not found or not a class.");
			return;
		}

		Class_ classifier = (Class_) element;

		element = this.environment.getElement(behaviorName);

		if (element == null || !(element instanceof Behavior)) {
			Debug.println("[addClassifierBehavior] " + behaviorName
					+ " not found or not a behavior.");
			return;
		}

		Behavior behavior = (Behavior) element;
		this.environment.removeElement(element);

		classifier.addOwnedBehavior(behavior);
		classifier.setClassifierBehavior(behavior);

	} // addClassifierBehavior

	public void addOperation(String className, String baseClassName,
			String operationName, String methodName) {
		NamedElement element = this.environment.getElement(className);

		if (element == null || !(element instanceof Class_)) {
			Debug.println("[addOperation] " + className
					+ " not found or not a class.");
			return;
		}

		Class_ classifier = (Class_) element;

		Operation operation = new Operation();
		operation.setName(operationName);

		if (!baseClassName.equals("")) {
			element = this.environment.getElement(baseClassName);

			if (element == null || !(element instanceof Class_)) {
				Debug.println("[addOperation] " + baseClassName
						+ " not found or not a class.");
				return;
			}

			Class_ baseClass = (Class_) element;
			Operation redefinedOperation = this.getOperation(baseClass,
					operationName);

			if (redefinedOperation == null) {
				Debug.println("[addOperation] " + operationName
						+ " is not an operation of " + baseClassName + ".");
				return;
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
				return;
			}

			Behavior behavior = (Behavior) element;
			this.environment.removeElement(element);
			classifier.addOwnedBehavior(behavior);

			operation.addMethod(behavior);
		}

		classifier.addOwnedOperation(operation);

	} // addOperation

	public void addGeneralization(String subtypeName, String supertypeName) {
		Classifier subtype = this.environment.getType(subtypeName);

		if (subtype == null) {
			Debug.println("[addGeneralization] " + subtypeName
					+ " not found or not a classifier.");
			return;
		}

		Classifier supertype = this.environment.getType(supertypeName);

		if (supertype == null) {
			Debug.println("[addGeneralization] " + supertypeName
					+ " not found or not a classifier.");
			return;
		}

		Generalization generalization = new Generalization();
		generalization.setGeneral(supertype);
		subtype.addGeneralization(generalization);
	} // addGeneralization

	protected fUML.Syntax.Classes.Kernel.Operation getOperation(
			fUML.Syntax.Classes.Kernel.Class_ class_, String operationName) {
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
