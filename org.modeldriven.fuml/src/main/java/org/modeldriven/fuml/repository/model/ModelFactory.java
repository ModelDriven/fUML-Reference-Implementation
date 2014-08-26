package org.modeldriven.fuml.repository.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.RepositoryArtifact;
import org.modeldriven.fuml.repository.RepositoryMapping;
import org.modeldriven.fuml.repository.RepositorylException;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Syntax.Classes.Kernel.Association;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;
import fUML.Syntax.Classes.Kernel.Generalization;
import fUML.Syntax.Classes.Kernel.InstanceSpecification;
import fUML.Syntax.Classes.Kernel.InstanceValue;
import fUML.Syntax.Classes.Kernel.LiteralBoolean;
import fUML.Syntax.Classes.Kernel.LiteralInteger;
import fUML.Syntax.Classes.Kernel.LiteralSpecification;
import fUML.Syntax.Classes.Kernel.LiteralString;
import fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural;
import fUML.Syntax.Classes.Kernel.Package;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.Classes.Kernel.ValueSpecification;

public class ModelFactory 
{
    private static Log log = LogFactory.getLog(ModelFactory.class);
    protected RepositoryMapping mapping;
    protected Repository model;

    @SuppressWarnings("unused")
	private ModelFactory() {}
    
    public ModelFactory(RepositoryMapping mapping, Repository model) {
    	this.mapping = mapping;
    	this.model = model;
    }
    
    public Package createPackage(String name, String qualifiedName, String id, RepositoryArtifact artifact) {
    	return createPackage(name, qualifiedName, id, null, artifact);
    }    
    
    public Package createPackage(String name, String qualifiedName, String id, Package parent, RepositoryArtifact artifact) {
    	Package p = new Package();
        p.name = name;
        p.qualifiedName = qualifiedName;
        p.setHref(artifact.getURN() + "#" + p.qualifiedName);
        p.setXmiId(id);
        if (parent != null)
            parent.addPackagedElement(p);
        
        if (parent != null) {
            parent.nestedPackage.add(p);
            p.nestingPackage = parent;
        }
        
        return p;
    }
            
    public Class_ createClass(String name, String id, Package pkg) {
        Class_ c = new Class_();
        c.name = name;
        c.qualifiedName = pkg.qualifiedName + "." + c.name;
        c.setXmiId(id);
        
        pkg.addPackagedElement(c);        
        c.package_ = pkg;
        
        return c;
    }

    public Enumeration createEnumeration(String name, String id) {
        Enumeration e = new Enumeration();
        e.name = name;
        e.setXmiId(id);
        return e;
    }
    
    public PrimitiveType createPrimitiveType(String name, String id) {
    	PrimitiveType t = new PrimitiveType();
        t.name = name;
        t.setXmiId(id);
        return t;        
    }

    public Association createAssociation(String name, String id, Property[] members) {
        Association a = new Association();
        a.name = name;
        a.setXmiId(id);

        for (int i = 0; i < members.length; i++) {
            a.memberEnd.add(members[i]);
        }

        return a;
    }

    public Association createAssociation(String name, String id) {
    	Association a = new Association();
        a.name = name;
        a.setXmiId(id);    	
    	return a;
    }
    
    public Property[] createAssociationEnds(Association assoc, String ends) {
    	List<Property> props = new ArrayList<Property>();
    	StringTokenizer st = new StringTokenizer(ends);
    	while (st.hasMoreTokens()) {
    		String token = st.nextToken();
    		org.modeldriven.fuml.repository.Property prop = (org.modeldriven.fuml.repository.Property)model.getElementById(token);
    		if (prop.getAssociation() != null && 
    			!prop.getAssociation().getXmiId().equals(assoc.getXmiId()))
    			log.warn("found existing association ("
    					+ prop.getAssociation().getXmiId() + ") on property ("
    					+ prop.getXmiId() + ") - overwriting with association ("
    					+ assoc.getXmiId() + ")");
    		prop.setAssociation(assoc);
    		props.add(prop.getDelegate());
    		
    		if (!assoc.memberEnd.contains(prop))
    			assoc.memberEnd.add(prop.getDelegate());
    	}
    	Property[] result = new Property[props.size()];
    	props.toArray(result);
    	    	
    	return result;
    }
        
    public Generalization createGeneralization(Class_ c, String general) {
        Generalization g = new Generalization();
        org.modeldriven.fuml.repository.Classifier classifier = (org.modeldriven.fuml.repository.Classifier) model.getElementById(general);
        g.general = classifier.getDelegate();
        c.generalization.add(g);
        return g;
    }

    public Property createProperty(Class_ c, String name, String id, 
            String typeName, String redefinedProperty, 
            boolean readOnly, boolean derived, boolean derivedUnion) {
        Property p = createProperty(name, id, typeName, 
                redefinedProperty, readOnly, derived,
                derivedUnion);
        p.class_ = c;
        c.ownedAttribute.add(p);
        return p;
    }

    public Property createProperty(Association assoc, String name, String id, 
            String typeName, String subsettedProperty, String redefinedProperty, 
            boolean readOnly, boolean derived, boolean derivedUnion) {
        Property p = createProperty(name, id, typeName, 
                redefinedProperty, readOnly, derived,
                derivedUnion);
        p.association = assoc;        
        assoc.ownedEnd.add(p);
        p.owningAssociation = assoc;
                
        return p;
    }
    
    public Property createProperty(String name, String id, 
            String typeName, String redefinedProperty, 
            boolean readOnly, boolean derived, boolean derivedUnion) {
        Property p = new Property();
        p.setName(name);
        p.setXmiId(id);
        p.isDerived = derived;
        p.setIsReadOnly(readOnly);
        p.isDerivedUnion = derivedUnion;

        if ((typeName == null || typeName.length() == 0) 
                && (redefinedProperty == null || redefinedProperty.length() == 0))
            throw new RepositorylException("no type or redefinedProperty found for property '" 
                    + name + "' (" + id
                    + ")");
        if (typeName != null && typeName.length() > 0)
        {   
        	//FIXME: flat namespace lookup
        	org.modeldriven.fuml.repository.Classifier typeClassifier =  (org.modeldriven.fuml.repository.Classifier)model.findElementById(typeName);
            // Well TypedElement.type is not frigging required. Another ridiculous
            // UML problem, like NamedElement.name. I guess we don't throw here, 
            // just log an error. 
            if (typeClassifier == null) {
                log.error("could not find type '" + typeName + "' for property '" 
                        + name + "' (" + id + ")");  
            }
            else
                p.typedElement.type = typeClassifier.getDelegate();
        } 
        else
        {
        	org.modeldriven.fuml.repository.Property redefinedProp = (org.modeldriven.fuml.repository.Property)model.getElementById(redefinedProperty);
            if (redefinedProp == null)
                throw new RepositorylException("could not find redefinedProperty '" + redefinedProperty + "' for property '" 
                    + name + "' (" + id
                    + ")");
            
            Property redefined = redefinedProp.getDelegate();
            p.typedElement.type = redefined.typedElement.type;
        }

        return p;
    }

    public LiteralInteger createLowerValue(Property p, boolean hasLowerValue, String value)
    {
    	LiteralInteger lowerValue = new LiteralInteger();
        p.setLowerValue(lowerValue);
        if ("1".equals(value)) {
            lowerValue.value = 1;
        }
        else if (value != null && value.length() > 0)
        {
            lowerValue.value = 1;
        }
        else if (hasLowerValue) {
            // it's defined, but no value attrib, give it the default
            // value for LiteralInteger (0)
            lowerValue.value = 0;
        }
        else {
            lowerValue.value = 1;
         }
        return lowerValue;
    }
    
    /**
     * multiplicity examples
     * ---------------------------------------
     * 1       - Default if omitted
     * *       - zero or more
     * 1..*    - 1 or more
     * 0..1    - zero or 1
     * 2..5    - At least 2 and up to 5
     * 2,5     - 2 or 5
     * n       - Unknown at compile time
     * ---------------------------------------
     * @param p
     * @param hasUpperValue
     * @param value
     */
    public LiteralSpecification createUpperValue(Property p, boolean hasUpperValue, String value)
    {
    	LiteralSpecification result = null;
        if ("*".equals(value)) {
            LiteralUnlimitedNatural upperValue = new LiteralUnlimitedNatural();
            UnlimitedNatural unlimitedNatural = new UnlimitedNatural();
            upperValue.value = unlimitedNatural;
            p.setUpperValue(upperValue);
            result = upperValue;
        }
        else if (value != null && value.length() > 0)
        {
            int intValue = Integer.parseInt(value);
            LiteralInteger upperValue = new LiteralInteger();
            upperValue.value = intValue;
            p.setUpperValue(upperValue);
            result = upperValue;
        }
        else if (hasUpperValue) {
            LiteralInteger upperValue = new LiteralInteger();
            upperValue.value = 1;
            p.setUpperValue(upperValue);
            result = upperValue;
        }
        else
        {
            LiteralInteger upperValue = new LiteralInteger();
            upperValue.value = 1;
            p.setUpperValue(upperValue);
            result = upperValue;
        }
        return result;
    }
    
    public ValueSpecification createDefault(Property prop, Object value, String instance, String id, 
            String xmiType, String typeId) {
        
        ValueSpecification valueSpec = null;
        
        String typeName = xmiType.substring(4);
        
        Classifier type = null;
        
        if (typeId != null && typeId.length() > 0)
        {
        	org.modeldriven.fuml.repository.Classifier typeClassifier =  (org.modeldriven.fuml.repository.Classifier)model.getElementById(typeId);
            type = typeClassifier.getDelegate();
        }    
            
        if (LiteralString.class.getSimpleName().equals(typeName))
        {
            LiteralString literalString = new LiteralString();
            if (type != null)
                literalString.type = type;
            else
                literalString.type = model.getClassifierByName("String").getDelegate();
            literalString.value = String.valueOf(value);
            
            valueSpec = literalString;
        }
        else if (LiteralBoolean.class.getSimpleName().equals(typeName))
        {
            LiteralBoolean literalBoolean = new LiteralBoolean();
            if (type != null)
                literalBoolean.type = type;
            else
                literalBoolean.type = model.getClassifierByName("Boolean").getDelegate();
            if (value != null) {
                literalBoolean.value = Boolean.valueOf(String.valueOf(value)).booleanValue();
            }    
            else
            {    
                literalBoolean.value = false;
            }    
            valueSpec = literalBoolean;
        }
        else if (LiteralInteger.class.getSimpleName().equals(typeName))
        {
            LiteralInteger literalInteger = new LiteralInteger();                
            if (type != null)
                literalInteger.type = type;
            else
                literalInteger.type = model.getClassifierByName("Integer").getDelegate();
            if (value != null && String.valueOf(value).length() > 0) {            	
                literalInteger.value = Integer.valueOf(String.valueOf(value)).intValue();
            }    
            else
            {    
                literalInteger.value = 0;
            }    
            valueSpec = literalInteger;
        }
        else if (LiteralUnlimitedNatural.class.getSimpleName().equals(typeName))
        {
        	LiteralUnlimitedNatural literalUnlimitedNatural = new LiteralUnlimitedNatural();                
            if (type != null)
                literalUnlimitedNatural.type = type;
            else
                literalUnlimitedNatural.type = model.getClassifierByName("UnlimitedNatural").getDelegate();
            if (value instanceof UnlimitedNatural) {            	
                literalUnlimitedNatural.value = (UnlimitedNatural)value;
            }    
            else
            {    
                literalUnlimitedNatural.value = new UnlimitedNatural();
            }    
            valueSpec = literalUnlimitedNatural;
        }
        else if ("OpaqueExpression".equals(typeName))
        {
        	if (value == null)
        		throw new RepositorylException("expected default value - cannot create OpaqueExpression default for property '"
        				+ prop.class_.name + "." + prop.name + "'");
        	if (value instanceof UnlimitedNatural)
        	{
        		LiteralUnlimitedNatural literalUnlimitedNatural = new LiteralUnlimitedNatural();
        		literalUnlimitedNatural.value = (UnlimitedNatural)value;
        		valueSpec = literalUnlimitedNatural;
        	}
        	else
    		    throw new RepositorylException("expected UnlimitedNatural value from OpaqueExpression default for property '"
    				+ prop.class_.name + "." + prop.name + "'");
        }
        else if (InstanceValue.class.getSimpleName().equals(typeName))
        {
            InstanceValue instanceValue = new InstanceValue();
            if (type == null)
                throw new RepositorylException("can't derive type for InstanceValue");
            instanceValue.type = type;
            valueSpec = instanceValue;
            if (instance == null || instance.length() == 0)
                throw new RepositorylException("required InstanceValue.instance ");
            instanceValue.instance = (InstanceSpecification)model.getElementById(instance).getDelegate();
            if (instanceValue.instance == null)
                log.warn("could not lookup reference for instance by id, '"
                        + instance + "'");
        }            
        else
            throw new RepositorylException("unknown type, '" + typeName + "'");
        
        prop.defaultValue = valueSpec;
        return valueSpec;
    }
    
    public EnumerationLiteral createEnumerationLiteral(Enumeration enumeration, 
            String name, String id) {
        EnumerationLiteral literal = new EnumerationLiteral();
        literal.name = name;
        literal.setXmiId(id);
        enumeration.ownedLiteral.add(literal);
        return literal;
    }

}
