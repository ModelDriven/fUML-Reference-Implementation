package org.modeldriven.fuml.library.common;

import org.modeldriven.fuml.library.Library;

import fUML.Semantics.Classes.Kernel.DataValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Property;

public class Status {

	String context;
	int code;
	String description;
	
	static DataType statusType = null;
	
	static public DataType getStatusType() {
		if (statusType == null) {
			statusType = (DataType)Library.getInstance().lookup("Common-Status");
		}
		return statusType;
	}
	
	static public void setStatusType(DataType type) {
		statusType = type;
	}
	
	public Status(String context, int code, String description) {
		this.setStatus(context, code, description);
	}
	
	public Status(String context) {
		this.setStatus(context, 0, "Normal");
	}
	
	public boolean isNormal() {
		return this.code == 0;
	}
	
	public void setStatus(String context, int code, String description) {
		this.context = context;
		this.code = code;
		this.description = description;
	}
	
	public Value getValue() {
		DataValue value = new DataValue();
		DataType statusType = getStatusType();
		value.type = statusType;
		for (Property attribute: statusType.ownedAttribute) {
			Value attributeValue = null;
			if (attribute.name.equals("context")) {
				attributeValue = new StringValue();
				((StringValue)attributeValue).value = this.context;
			} else if (attribute.name.equals("code")) {
				attributeValue = new IntegerValue();
				((IntegerValue)attributeValue).value = this.code;
			} else if (attribute.name.equals("description")) {
				attributeValue = new StringValue();
				((StringValue)attributeValue).value = this.description;
			}
			ValueList values = new ValueList();
			values.add(attributeValue);
			value.setFeatureValue(attribute, values, 0);
		}
		return value;
	}
	
}
