package org.modeldriven.fuml.repository.model;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.assembly.AssemblyException;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.io.ArtifactLoader;
import org.modeldriven.fuml.repository.Element;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.RepositoryArtifact;
import org.modeldriven.fuml.repository.RepositorylException;
import org.modeldriven.fuml.xmi.InvalidReferenceException;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Syntax.Classes.Kernel.Association;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;
import fUML.Syntax.Classes.Kernel.InstanceValue;
import fUML.Syntax.Classes.Kernel.LiteralBoolean;
import fUML.Syntax.Classes.Kernel.LiteralInteger;
import fUML.Syntax.Classes.Kernel.LiteralNull;
import fUML.Syntax.Classes.Kernel.LiteralString;
import fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class Property extends NamedElement 
    implements org.modeldriven.fuml.repository.Property{


    private static Log log = LogFactory.getLog(Property.class);
	private fUML.Syntax.Classes.Kernel.Property property;
	    
    public Property(fUML.Syntax.Classes.Kernel.Property property,
    		RepositoryArtifact artifact) {
    	super(property, artifact);
    	this.property = property;
    }
	
    public Class_ getClass_() {
    	return (Class_)Repository.INSTANCE.getElementById(this.property.class_.getXmiId());
    }
	
	public ValueSpecification getDefaultValue() {
		return this.property.defaultValue;
	}

	public void setDefaultValue(ValueSpecification defaultValue) {
		this.property.defaultValue = defaultValue;
	}

	public org.modeldriven.fuml.repository.Property getOpposite() {
		if (this.property.opposite != null)
		{	
			org.modeldriven.fuml.repository.Property result = (org.modeldriven.fuml.repository.Property)Repository.INSTANCE.getElementById(this.property.opposite.getXmiId());
		    return result;
		}
		
		if (property.owningAssociation == null) {
			fUML.Syntax.Classes.Kernel.Property otherEnd = getOtherEnd(this);

			if (otherEnd != null && otherEnd.owningAssociation == null) {
				this.property.opposite = otherEnd;
				org.modeldriven.fuml.repository.Property result = (org.modeldriven.fuml.repository.Property)Repository.INSTANCE.getElementById(this.property.opposite.getXmiId());
			    return result;
			}
		}
		return null;
	}
	
	public fUML.Syntax.Classes.Kernel.Property getDelegate() {
		return this.property;
	}

    public Classifier getType() {
    	return this.findType(false);
    }
	
    public Classifier findType() {
    	return this.findType(true);
    }   
    
    private Classifier findType(boolean supressErrors) {
        Classifier result = null;
        
        String typeXmiId = this.property.typedElement.type.getXmiId();
        if (typeXmiId != null) {
        	Element elementResult = Repository.INSTANCE.getElementById(typeXmiId);
        	try {
        	    result = (Classifier)elementResult;
        	}
        	catch (ClassCastException e) {
        		throw new RuntimeException(e);
        	}
        	//if (Classifier.class.isAssignableFrom(Element.class))
        	//    result = Classifier.class.cast(elementResult);
            //else
            //	throw new IllegalStateException(Classifier.class.getName() 
            //		+ " cannot be assigned from " + Element.class.getName());
            if (result == null && !supressErrors)
                throw new InvalidReferenceException(typeXmiId);
        }
        if (result == null && !supressErrors)
            throw new RepositorylException("no type found for property, " + this.property.name);
        return result;
    }
    
    public boolean isDataType() {
    	return getType().isDataType();
    }
    
    public Association getAssociation() {
    	return this.property.association;
    }
    
    public void setAssociation(Association assoc) {
    	this.property.association = assoc;
    }
    
    public boolean isDerived() {
    	return this.property.isDerived;
    }
    
    public boolean isRequired() {
        return this.getLowerValue() > 0;
    }

    public boolean isSingular() {
        return "1".equals(this.getUpperValue());
    }
    
    public String getPropertyDefault() {
        return getPropertyDefaultValue(false);
    }

    public String findPropertyDefault() {
        return getPropertyDefaultValue(true);
    }

    public boolean hasPropertyDefaultValue() {
        String value = getPropertyDefaultValue(true);
        return value != null && value.trim().length() > 0;
    }

    private String getPropertyDefaultValue(boolean supressErrors) {
        ValueSpecification valueSpec = findDefaultValueSpecification();
        if (valueSpec != null)
            return getValue(valueSpec);
        else
            return null;
    }

    private ValueSpecification findDefaultValueSpecification() {
        return property.defaultValue;
    }
    
    public String getUpperValue() {
        return getValue(this.property.multiplicityElement.upperValue);
    }

    public int getLowerValue() {
    	String value = getValue(this.property.multiplicityElement.lowerValue);
         if (value == null || "null".equalsIgnoreCase(value))
            value = "0";
        return Integer.valueOf(value);
    }

    private String getValue(ValueSpecification valueSpec) {
        if (LiteralString.class.isAssignableFrom(valueSpec.getClass())) {
            return ((LiteralString)valueSpec).value;
        }
        else if (LiteralInteger.class.isAssignableFrom(valueSpec.getClass())) {
            return String.valueOf(((LiteralInteger)valueSpec).value); // use specifically typed value in subclass
        }
        else if (LiteralBoolean.class.isAssignableFrom(valueSpec.getClass())) {
            return String.valueOf(((LiteralBoolean)valueSpec).value); // use specifically typed value in subclass
        }
        else if (LiteralNull.class.isAssignableFrom(valueSpec.getClass())) {
            return null;
        }
        else if (LiteralUnlimitedNatural.class.isAssignableFrom(valueSpec.getClass())) {
            return String.valueOf(((LiteralUnlimitedNatural)valueSpec).value.naturalValue);
        } else if (InstanceValue.class.isAssignableFrom(valueSpec.getClass())) {
        	InstanceValue instanceValue = (InstanceValue)valueSpec;
        	if (EnumerationLiteral.class.isAssignableFrom(instanceValue.instance.getClass()))
        	{
        		EnumerationLiteral enumerationLiteral = (EnumerationLiteral)instanceValue.instance;
        		return enumerationLiteral.name;
        	}
        	else
                throw new RepositorylException("unknown instance type, " 
                		+ instanceValue.instance.getClass().getName());
        } else {
            // return ((OpaqueExpression)valueSpec).getBody();
            throw new IllegalArgumentException("expected literal or instance value");
        }
    }

    private void setValue(ValueSpecification valueSpec, String value) {
        if (LiteralString.class.isAssignableFrom(valueSpec.getClass()))
        	((LiteralString)valueSpec).value = value;
        else if (LiteralInteger.class.isAssignableFrom(valueSpec.getClass()))
        	((LiteralInteger)valueSpec).value = Integer.parseInt(value);
        else if (LiteralBoolean.class.isAssignableFrom(valueSpec.getClass()))
            ((LiteralBoolean)valueSpec).value = Boolean.parseBoolean(value);
        //else if (LiteralNull.class.isAssignableFrom(valueSpec.getClass()))        	 	
        else if (LiteralUnlimitedNatural.class.isAssignableFrom(valueSpec.getClass())) {
        	UnlimitedNatural un = new UnlimitedNatural();
        	un.naturalValue = Integer.parseInt(value);
        	((LiteralUnlimitedNatural)valueSpec).value = un;
        } else if (InstanceValue.class.isAssignableFrom(valueSpec.getClass())) {
            valueSpec.setName(value);
        } else {
            // ((OpaqueExpression)valueSpec).setBody(value);
            throw new IllegalArgumentException("expected literal or instance value");
        }
    }
    
	private static fUML.Syntax.Classes.Kernel.Property getOtherEnd(org.modeldriven.fuml.repository.Property property) {
		Association association = property.getAssociation();

		if (association != null) {
			List<?> memberEnds = association.memberEnd;

			if (memberEnds.size() == 2) {
				int index = memberEnds.indexOf(property.getDelegate());

				if (index != -1) {
					return (fUML.Syntax.Classes.Kernel.Property) memberEnds.get(Math.abs(index - 1));
				}
			}
			else if (log.isDebugEnabled())
				log.debug("expected exactly 2 (not " 
						+ memberEnds.size() + ") member-end elements for association ("
						+ association.getXmiId() + ") linking property " 
						+ property.getClass_().getQualifiedName() + "." + property.getName()
						+ " - ignoring any association owned-end property as not applicable as property opposite");
			// Note where one end (Property) of an association is linked to a read-only class, tools (MagicDraw)
			// will necessarily create an association owned-end as it cannot modify the target read-only class. This
			// can be the case when imported read-only modules are used.
		}

		return null;
	}
	
} // Property
