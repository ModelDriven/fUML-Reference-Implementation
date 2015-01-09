package org.modeldriven.fuml.library.common;

import org.modeldriven.fuml.library.Library;

import fUML.Semantics.Classes.Kernel.DataValue;
import fUML.Semantics.Classes.Kernel.IntegerValue;
import fUML.Semantics.Classes.Kernel.PrimitiveValue;
import fUML.Semantics.Classes.Kernel.StringValue;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.Loci.LociL1.Locus;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.Classes.Kernel.Property;

public class Status {
	
	private PrimitiveType stringType = null;
	private PrimitiveType integerType = null;

	private String context;
	private int code;
	private String description;
	
	static private DataType statusType = null;
	
	static public DataType getStatusType() {
		if (statusType == null) {
			statusType = (DataType)Library.getInstance().lookup("Common-Status");
		}
		return statusType;
	}
	
	static public void setStatusType(DataType type) {
		statusType = type;
	}
	
	public Status(Locus locus, String context, int code, String description) {
		this.setPrimitiveTypes(locus);
		this.setStatus(context, code, description);
	}
	
	public Status(Locus locus, String context) {
		this.setPrimitiveTypes(locus);
		this.setStatus(context, 0, "Normal");
	}
	
	private void setPrimitiveTypes(Locus locus) {
		this.stringType = locus.factory.getBuiltInType("String");
		this.integerType = locus.factory.getBuiltInType("Integer");
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
			PrimitiveValue attributeValue = null;
			if (attribute.name.equals("context")) {
				attributeValue = new StringValue();
				attributeValue.type = this.stringType;
				((StringValue)attributeValue).value = this.context;
			} else if (attribute.name.equals("code")) {
				attributeValue = new IntegerValue();
				attributeValue.type = this.integerType;
				((IntegerValue)attributeValue).value = this.code;
			} else if (attribute.name.equals("description")) {
				attributeValue = new StringValue();
				attributeValue.type = this.stringType;
				((StringValue)attributeValue).value = this.description;
			}
			ValueList values = new ValueList();
			values.add(attributeValue);
			value.setFeatureValue(attribute, values, 0);
		}
		return value;
	}
	
}
