
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

import fUML.Syntax.Classes.Kernel.*;

import fUML.Semantics.Classes.Kernel.*;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.*;

public class VariableUtility extends fUML.Test.Test {

	public VariableUtility(fUML.Test.TestEnvironment environment) {
		this.environment = environment;
	} // VariableUtility

	public void setValue(String variable, boolean value) {
		BooleanValue newValue = new BooleanValue();
		newValue.type = environment.primitiveTypes.Boolean;
		newValue.value = value;
		environment.setVariable(variable, newValue);
	} // setValue

	public void setValue(String variable, int value) {
		IntegerValue newValue = new IntegerValue();
		newValue.type = environment.primitiveTypes.Integer;
		newValue.value = value;
		environment.setVariable(variable, newValue);
	} // setValue

	public void setValue(String variable, String value) {
		StringValue newValue = new StringValue();
		newValue.type = environment.primitiveTypes.String;
		newValue.value = value;
		environment.setVariable(variable, newValue);
	} // setValue

	public void clearValue(String variable) {
		environment.setVariable(variable, null);

	} // clearValue

	public void copyValue(String target, String source) {
		Variable sourceVariable = environment.getVariable(source);

		if (sourceVariable == null) {
			Debug.println("[copyValue] Variable " + source + " not found.");
			return;
		}

		environment.setVariable(target, sourceVariable.value.copy());

	} // copyValue

	public void setEnumerationValue(String variable, String typeName,
			String literalName) {
		NamedElement element = environment.getElement(typeName);

		if (element == null) {
			Debug.println("[createEnumerationValue] " + typeName
					+ " not found.");
		} else if (!(element instanceof Enumeration)) {
			Debug.println("[createEnumerationValue] " + typeName
					+ " is not an enumeration.");
		} else {
			Enumeration enumeration = (Enumeration) element;

			for (int i = 0; i < enumeration.ownedLiteral.size(); i++) {
				if (enumeration.ownedLiteral.getValue(i).name
						.equals(literalName)) {
					EnumerationValue newValue = new EnumerationValue();
					newValue.type = enumeration;
					newValue.literal = enumeration.ownedLiteral.getValue(i);
					environment.setVariable(variable, newValue);
					return;
				}
			}

			Debug.println("[createEnumerationValue] " + literalName
					+ " is not an owned literal of " + typeName + ".");
		}
	} // setEnumerationValue

	public void setDefaultValue(String variable, String typeName) {
		Classifier type = environment.getType(typeName);

		if (type == null) {
			Debug.println("[setDefaultValue] " + typeName
					+ " not found or not a classifier.");
			return;
		}

		Value value = environment.makeValue(type);
		environment.setVariable(variable, value);

		Debug.println(value.toString());
	} // setDefaultValue

	public void printVariables() {
		Debug.println("");
		Debug.println(this.environment.variables.size() + " variable(s)");
		Debug.println("----------");

		for (int i = 0; i < this.environment.variables.size(); i++) {
			Variable variable = this.environment.variables.getValue(i);
			this.printVariable(variable.name);
			// Value value = variable.value;

			// if (value == null) {
			// Debug.println(variable.name + ": value = null");
			// }
			// else {
			// ClassifierList types = value.getTypes();
			// StringBuffer typeNames = new StringBuffer();
			//
			// for (int j=0; j < types.size(); j++) {
			// typeNames.append(" ").append(types.getValue(j).name);
			// }

			// Debug.println(variable.name + ": value = " + value + ", types ("
			// + types.size() + ") =" + typeNames);
			// }
		}

		Debug.println("");
	} // printVariables

	public void printVariable(String variableName) {
		Variable variable = this.environment.getVariable(variableName);

		if (variable == null) {
			Debug
					.println("[printVariable] " + variableName
							+ "does not exist.");
			return;
		}

		Debug.println(variableName + " = " + variable.value);
	} // printVariable

	public void copyToAttribute(String target, String attributeName,
			String source) {
		Variable sourceVariable = this.environment.getVariable(source);

		if (sourceVariable == null) {
			Debug.println("[copyToAttribute] " + source + " does not exist.");
			return;
		}

		this.setAttributeValue(target, attributeName, sourceVariable.value
				.copy());

	} // copyToAttribute

	public void setAttributeValue(String target, String attributeName,
			fUML.Semantics.Classes.Kernel.Value sourceValue) {
		Variable targetVariable = this.environment.getVariable(target);

		if (targetVariable == null) {
			Debug.println("[setAttribute] " + target + " does not exist.");
			return;
		}

		Value value = targetVariable.value;

		if (!(value instanceof StructuredValue)) {
			Debug.println("[setAttribute] " + target
					+ "does not hold a structured value.");
			return;
		}

		StructuredValue targetValue = (StructuredValue) value;
		ClassifierList types = targetValue.getTypes();

		Property attribute = null;

		for (int i = 0; i < types.size(); i++) {
			PropertyList attributes = types.getValue(i).attribute;
			for (int j = 0; j < attributes.size(); j++) {
				if (attributes.getValue(j).name.equals(attributeName)) {
					attribute = attributes.getValue(j);
					break;
				}
			}
		}

		if (attribute == null) {
			Debug.println("[setAttribute] " + attributeName + " not found.");
			return;
		}

		ValueList values = new ValueList();
		values.addValue(sourceValue);
		targetValue.setFeatureValue(attribute, values, 0);
	} // setAttributeValue

	public void setAttributeValue(String target, String attributeName, int value) {
		IntegerValue integerValue = new IntegerValue();
		integerValue.value = value;

		this.setAttributeValue(target, attributeName, integerValue);

	} // setAttributeValue

	public void setAttributeValue(String target, String attributeName,
			boolean value) {
		BooleanValue booleanValue = new BooleanValue();
		booleanValue.value = value;

		this.setAttributeValue(target, attributeName, booleanValue);
	} // setAttributeValue

	public void setAttributeValue(String target, String attributeName,
			String value) {
		StringValue stringValue = new StringValue();
		stringValue.value = value;

		this.setAttributeValue(target, attributeName, stringValue);
	} // setAttributeValue

	public void run(String variableName) {
		Variable variable = this.environment.getVariable(variableName);

		if (variable == null
				|| variable.value == null
				|| !(variable.value instanceof Reference)
				|| !(((Reference) (variable.value)).referent instanceof Execution)) {
			Debug.println("[run] " + variableName
					+ " not found or does not have an execution as its value.");
			return;
		}

		Execution execution = (Execution) (((Reference) (variable.value)).referent);

		Debug.println("[run] Executing " + variableName + "...");

		// try {
		execution.execute();

		ParameterValueList outputParameterValues = execution
				.getOutputParameterValues();

		for (int i = 0; i < outputParameterValues.size(); i++) {
			ParameterValue outputParameterValue = outputParameterValues
					.getValue(i);
			Debug.println("[run] Output parameter "
					+ outputParameterValue.parameter.name + " has "
					+ outputParameterValue.values.size() + " value(s):");
			for (int j = 0; j < outputParameterValue.values.size(); j++) {
				Debug.println("[run] value [" + j + "] = "
						+ outputParameterValue.values.getValue(j));
			}
		}
		// }
		// catch (Throwable e) {
		// Debug.println("[run] Execution terminated due to " +
		// e.getClass().getName() + "...");
		// StackTraceElement [] stackTrace = e.getStackTrace();
		// if (stackTrace.length > 0) Debug.println(stackTrace[0] + ".");
		// }

		Debug.println("");

	} // run

} // VariableUtility
