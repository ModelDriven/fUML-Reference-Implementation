/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except  
 * as stated in the file entitled Licensing-Information. 
 * 
 * All modifications copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 */

package org.modeldriven.fuml.test.builtin.environment;

import fUML.Debug;

import UMLPrimitiveTypes.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.Communications.*;

import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.Communications.*;
import fUML.Semantics.Loci.LociL1.Executor;
import fUML.Semantics.Loci.LociL1.FirstChoiceStrategy;
import fUML.Semantics.Loci.LociL1.Locus;

import org.modeldriven.fuml.environment.ExecutionFactory;
import org.modeldriven.fuml.library.channel.StandardOutputChannelObject;
import org.modeldriven.fuml.library.common.Status;
import org.modeldriven.fuml.test.builtin.library.IntegerFunctions;
import org.modeldriven.fuml.test.builtin.library.PrimitiveTypes;
import org.modeldriven.fuml.test.builtin.library.StandardIOClasses;
import org.modeldriven.fuml.test.builtin.library.SystemIO;

public class TestEnvironment {

	public ActivityFactory activityFactory = new ActivityFactory(this);
	public ClassifierFactory classifierFactory = new ClassifierFactory(this);
	public ExecutorTest executorTest = new ExecutorTest(this);
	
	public NamedElementList elements = new NamedElementList();
	public Locus locus = null;

	public TestEnvironment() {
		try {

			this.locus = new Locus();
			this.locus.setFactory(new ExecutionFactory());
			this.locus.setExecutor(new Executor());

			this.locus.factory.setStrategy(new RedefinitionBasedDispatchStrategy());
			this.locus.factory.setStrategy(new FIFOGetNextEventStrategy());
			this.locus.factory.setStrategy(new FirstChoiceStrategy());

			this.addElement(PrimitiveTypes.Boolean);
			this.addElement(PrimitiveTypes.Integer);
			this.addElement(PrimitiveTypes.String);
			this.addElement(PrimitiveTypes.UnlimitedNatural);

			IntegerFunctions.addFunctions(this.locus.factory);
			this.addElement(IntegerFunctions.integerPlus);
			this.addElement(IntegerFunctions.integerMinus);
			this.addElement(IntegerFunctions.integerTimes);
			this.addElement(IntegerFunctions.integerDivide);
			this.addElement(IntegerFunctions.integerNegate);
			this.addElement(IntegerFunctions.integerGreater);

			this.addElement(SystemIO.WriteLine);

			this.addElement(StandardIOClasses.Channel);
			this.addElement(StandardIOClasses.OutputChannel);
			this.addElement(StandardIOClasses.TextOutputChannel);
			this.addElement(StandardIOClasses.StandardOutputChannel);

			StandardOutputChannelObject standardOutputChannel = new StandardOutputChannelObject();
			standardOutputChannel.types
					.addValue(StandardIOClasses.StandardOutputChannel);
			standardOutputChannel.open(new Status(this.locus, ""));
			this.locus.add(standardOutputChannel);
			
		} catch (Throwable e) {
			Debug.println("[TestEnvironment] Terminated due to "
					+ e.getClass().getName() + "...");
			StackTraceElement[] stackTrace = e.getStackTrace();
			if (stackTrace.length > 0)
				Debug.println(stackTrace[0] + ".");
		}

	} // TestEnvironment

	public PrimitiveValue makePrimitiveValue(Classifier classifier) {
		PrimitiveType type = (PrimitiveType) classifier;
		PrimitiveValue primitiveValue = null;

		// Debug.println("[makePrimitiveValue] type = " + type.name);

		if (type == PrimitiveTypes.Boolean) {
			primitiveValue = new BooleanValue();
		} else if (type == PrimitiveTypes.Integer) {
			primitiveValue = new IntegerValue();
		} else if (type == PrimitiveTypes.String) {
			primitiveValue = new StringValue();
		} else if (type == PrimitiveTypes.UnlimitedNatural) {
			primitiveValue = new UnlimitedNaturalValue();
			((UnlimitedNaturalValue) primitiveValue).value = new UnlimitedNatural();
		}

		if (primitiveValue != null) {
			primitiveValue.type = type;
		} else {
			Debug.println("[makePrimitiveValue] " + type.name
					+ " not understood.");
		}

		// Debug.println("[makePrimitiveValue] value = " + primitiveValue);

		return primitiveValue;
	} // makePrimitiveValue

	public EnumerationValue makeEnumerationValue(Classifier classifier) {
		Enumeration type = (Enumeration) classifier;
		EnumerationValue enumerationValue = new EnumerationValue();

		enumerationValue.type = type;
		enumerationValue.literal = type.ownedLiteral.getValue(0);

		return enumerationValue;
	} // makeEnumerationValue

	public StructuredValue makeStructuredValue(Classifier classifier) {
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

		// Debug.println("[makeStructuredValue] " + featureValues.size() +
		// " structural features.");
		for (int i = 0; i < featureValues.size(); i++) {
			StructuralFeature feature = featureValues.getValue(i).feature;
			// Debug.println("[makeStructuredValue] feature = " + feature.name +
			// ", type = " + feature.typedElement.type.name);
			ValueList valueList = new ValueList();
			valueList.addValue(this
					.makeValue((Classifier) (feature.typedElement.type)));
			structuredValue.setFeatureValue(feature, valueList, 0);
		}

		return structuredValue;

	} // makeStructuredValue

	public Value makeValue(Classifier type) {
		// if (type == null) {
		// Debug.println("[makeValue} type is null");
		// } else {
		// Debug.println("[makeValue] type = " + type.name);
		// }

		if (type == null) {
			return this.makePrimitiveValue(PrimitiveTypes.String);
		} else if (type instanceof PrimitiveType) {
			return this.makePrimitiveValue(type);
		} else if (type instanceof Enumeration) {
			return this.makeEnumerationValue(type);
		} else {
			return this.makeStructuredValue(type);
		}
	} // makeValue

	public void addElement(fUML.Syntax.Classes.Kernel.NamedElement element) {
		if (this.getElement(element.name) == null) {
			this.elements.addValue(element);
		} else {
			Debug.println("[addElement] There is already an element named "
					+ element.name + ".");
		}
	} // addElement

	public NamedElement getElement(String name) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.getValue(i).name.equals(name))
				return elements.getValue(i);
		}
		return null;
	} // getElement

	public void removeElement(NamedElement element) {
		for (int i = 0; i < this.elements.size(); i++) {
			if (this.elements.getValue(i) == element) {
				this.elements.remove(i);
				return;
			}
		}
	} // removeElement

	public Classifier getType(String typeName) {
		NamedElement element = this.getElement(typeName);

		if ((element == null) || !(element instanceof Classifier)) {
			return null;
		} else {
			return (Classifier) element;
		}
	} // getType

	public void printElements() {
		Debug.println("");
		Debug.println(elements.size() + " element(s)");
		Debug.println("---------");

		for (int i = 0; i < elements.size(); i++) {
			Debug.println(elements.getValue(i).name + ": "
					+ elements.getValue(i).getClass().getName());
		}
	} // printElements

	public void printExtent(String classifierName) {
		Element element = this.getElement(classifierName);

		if (element == null) {
			Debug.println("[printExtent] " + classifierName + " not found.");
			return;
		}

		if (!(element instanceof Classifier)) {
			Debug.println("[printExtent] " + classifierName
					+ " is not a classifier.");
			return;
		}

		ExtensionalValueList extent = this.locus
				.getExtent((Classifier) element);

		Debug.println("");
		Debug.println(classifierName + " has " + extent.size()
				+ " instance(s):");

		for (int i = 0; i < extent.size(); i++) {
			Debug.println("[" + i + "] " + extent.getValue(i));
		}
	} // printExtent

	public void removeElement(String elementName) {
		NamedElement element = this.getElement(elementName);

		if (element != null) {
			this.removeElement(element);
		} else {
			Debug.println("[removeElement] " + elementName 
					+ " does not exist.");
		}
	} // removeElement

} // TestEnvironment
