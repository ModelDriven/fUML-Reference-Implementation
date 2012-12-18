/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * All modifications copyright 2009-2011 Data Access Technologies, Inc. 
 * (Model Driven Solutions). Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.assembly;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.FumlObject;
import org.modeldriven.fuml.common.lang.JavaKeyWords;
import org.modeldriven.fuml.common.reflect.ReflectionUtils;
import org.modeldriven.fuml.common.uuid.UUIDGenerator;
import org.modeldriven.fuml.config.FumlConfiguration;
import org.modeldriven.fuml.config.ImportAdapter;
import org.modeldriven.fuml.config.ImportAdapterType;
import org.modeldriven.fuml.config.NamespaceDomain;
import org.modeldriven.fuml.config.ReferenceMappingType;
import org.modeldriven.fuml.config.ValidationExemption;
import org.modeldriven.fuml.config.ValidationExemptionType;
import org.modeldriven.fuml.environment.Environment;
import org.modeldriven.fuml.library.Library;
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.Property;
import org.modeldriven.fuml.xmi.ModelSupport;
import org.modeldriven.fuml.xmi.XmiExternalReferenceElement;
import org.modeldriven.fuml.xmi.XmiIdentity;
import org.modeldriven.fuml.xmi.XmiMappedReference;
import org.modeldriven.fuml.xmi.XmiNode;
import org.modeldriven.fuml.xmi.XmiReference;
import org.modeldriven.fuml.xmi.XmiReferenceAttribute;
import org.modeldriven.fuml.xmi.stream.StreamNode;
import org.modeldriven.fuml.xmi.validation.ErrorCode;
import org.modeldriven.fuml.xmi.validation.ErrorSeverity;
import org.modeldriven.fuml.xmi.validation.ValidationError;
import org.modeldriven.fuml.xmi.validation.ValidationException;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Syntax.Classes.Kernel.Comment;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Generalization;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.Classes.Kernel.Type;

public class ElementAssembler extends AssemblerNode implements XmiIdentity, Assembler {

    private static Log log = LogFactory.getLog(ElementAssembler.class);
    private Repository metadata = Repository.INSTANCE;
    private List<XmiReference> references;
    private Map<String, ElementAssembler> assemblerMap;
    private ModelSupport modelSupport;
    private boolean assembleExternalReferences = true;

    private ElementAssembler parentAssembler; // FIXME: re-factor ASAP!!

    // Generalizations deferred until element linking is complete, keyed to the
    // specific classifier.
    private List<Generalization> deferredGeneralizations;

    /** XMI source */
    private XmiNode source;
    private XmiNode parent;
    private Attribute xmiId;
    private Attribute xmiHref;
    
    /** namespace source */
    private String xmiNamespace;

    /** UML meta-class */
    private Class_ prototype;

    /** fUML class target(s) */
    private Element target;
    private Comment targetComment;
    
    @SuppressWarnings("unused")
    private ElementAssembler() {
        // nope
    }

    public ElementAssembler(XmiNode source, XmiNode parent, Class_ prototype,
            Map<String, ElementAssembler> assemblerMap) {
        super(source);
        this.source = source;
        this.parent = parent;
        this.prototype = prototype;
        this.assemblerMap = assemblerMap;
        modelSupport = new ModelSupport();
        StreamNode eventNode = (StreamNode) this.source;

        QName idName = new QName(eventNode.getContext().getXmiNamespace().getNamespaceURI(), "id");
        this.xmiId = eventNode.getAttribute(idName);
        QName hrefName = new QName("href");
        this.xmiHref = eventNode.getStartElementEvent().asStartElement().getAttributeByName(hrefName);
        
        
        this.xmiNamespace = eventNode.getNamespaceURI();
        if (this.xmiNamespace == null)
        	throw new AssemblyException("could not find namespace for node '"
        			+ eventNode.getLocalName() + "'");
    }

    public ElementAssembler getParentAssembler() {
        return parentAssembler;
    }

    public void setParentAssembler(ElementAssembler parentAssembler) {
        this.parentAssembler = parentAssembler;
    }
    
    public List<Generalization> getDeferredGeneralizations() {
    	return this.deferredGeneralizations;
    }
    
    public void addDeferredGeneralization(Generalization generalization) {
    	// Record deferred generalizations only in root assemblers.
    	ElementAssembler parent = this.getParentAssembler();
    	if (parent == null) {
    		if (this.deferredGeneralizations == null) {
    			this.deferredGeneralizations = new ArrayList<Generalization>();
    		}
    		this.deferredGeneralizations.add(generalization);
    	} else {
    		parent.addDeferredGeneralization(generalization);
    	}
    }

    public void assembleElementClass() {
        try {
        	
            String packageName = metadata.getJavaPackageNameForClass(this.prototype);
            String instancecClassName = this.prototype.getDelegate().name;
            // TODO: We have a keyword map, but unclear whether upper-case 'Class' should be mapped. Definately 'class' is.
            if ("Class".equals(instancecClassName))
                instancecClassName = instancecClassName + "_"; 
            
            String qualifiedName = packageName + "." + instancecClassName;

            FumlObject object = null;
            ImportAdapter importAdapter = FumlConfiguration.getInstance().findImportAdapter(
                    instancecClassName);
            if (importAdapter == null
                    || importAdapter.getType().ordinal() != ImportAdapterType.ASSEMBLY.ordinal()) {
                object = (FumlObject)ReflectionUtils.instanceForName(qualifiedName);
            } else {
                AssemblyAdapter adapter = (AssemblyAdapter) ReflectionUtils.instanceForName(importAdapter
                        .getAdapterClassName());
                object = adapter.assembleElement((StreamNode) this.source);
            }
            
            if (this.xmiId != null)
                object.setXmiId(this.xmiId.getValue());
            else
            	object.setXmiId(UUIDGenerator.instance().getIdString36()); // synthesize one
            if (this.xmiHref != null)
                object.setHref(this.xmiHref.getValue());
            if (this.xmiId == null && this.xmiHref == null)
            	log.warn("found (" + object.getClass().getName() + ") element with no xmi:id or href");
            object.setXmiNamespace(this.xmiNamespace);

            if (object instanceof Element) {
                if (object instanceof PrimitiveType) {
                    if (object.getHref() == null)
                        throw new AssemblyException("expected 'href' attribute for primitive type");
                    // FIXME; gotta be a better way! URI resolver??
                    // Note: Path map variables allow for portability of URIs. The actual location 
                    // indicated by a URI depends on the run-time binding of the path variable. Thus, different 
                    // environments can work with the same resource URIs even though the 
                    // resources are stored in different physical locations.
                    
                    int idx = object.getHref().lastIndexOf("#");
                    String suffix = object.getHref().substring(idx+1);

                    if (suffix.equals("Integer"))
                        this.target = Environment.getInstance().getInteger();
                    else if (suffix.equals("String"))
                        this.target = Environment.getInstance().getString();
                    else if (suffix.equals("Boolean"))
                        this.target = Environment.getInstance().getBoolean();
                    else if (suffix.equals("Real"))
                        this.target = Environment.getInstance().getReal();
                    else if (suffix.equals("UnlimitedNatural"))
                        this.target = Environment.getInstance().getUnlimitedNatural();
                    else
                        throw new AssemblyException("unknown type, " + object.getHref());
                    
                    //this.target = (Element) object; 
                    if (this.target == null)
                    	throw new AssemblyException("could not determine target object for prototype, "
                    		+ this.prototype.getXmiNamespace() + "#" 
                    		+ this.prototype.getName());
                    
                } else {
                    this.target = (Element) object;
                    if (this.target == null)
                    	throw new AssemblyException("could not determine target object for prototype, "
                    		+ this.prototype.getXmiNamespace() + "#" 
                    		+ this.prototype.getName());
                    if (log.isDebugEnabled())
                        log.debug("constructing class " + this.target.getClass().getName());
                }
            } else if (object instanceof Comment) {
                this.targetComment = (Comment) object;
                if (this.targetComment == null)
            	    throw new AssemblyException("could not determine target object for prototype, "
                		+ this.prototype.getXmiNamespace() + "#" 
                		+ this.prototype.getName());
            } else
                throw new AssemblyException("unknown instance, " + object.getClass().getName());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InvocationTargetException e) {
            log.error(e.getCause().getMessage(), e.getCause());
            throw new AssemblyException(e.getCause());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InstantiationException e) {
            if (e.getCause() != null)
                log.error(e.getCause().getMessage(), e.getCause());
            else
                log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        }
    }

    // TODO: move this to library.Library setting up the locus
    // as elements are registered. Current;y can't do this as opposite
    // properties are not being set. I.e. a packagedElement does not
    // know it's package (parent). We are relying on the assembler
    // hierarchy to do this (below), which is unnecessary/hacky. The metadata
    // will
    // need to be enhanced to find opposite properties using associations.
    // 
    public void registerElement() {
        try {
            if (!(this.target instanceof Type))
                return;

            Type targetType = (Type) this.target;
            
            ElementAssembler assembler = this;

            // FIXME: generalize this hack using associations in metamodel. For incremental
            // assembly where we remove chunks of XMI from the parent (packagedElement), this
            // scheme breaks because it relies on a continuous graph. 
            String qualifiedPackageName = "";
            fUML.Syntax.Classes.Kernel.Package parentPackage = null;
            if (assembler.getParentAssembler() != null && 
                assembler.getParentAssembler().getTarget() instanceof fUML.Syntax.Classes.Kernel.Package) {
            	parentPackage = (fUML.Syntax.Classes.Kernel.Package)assembler.getParentAssembler().getTarget();
            }

            for (int i = 0; (assembler = assembler.getParentAssembler()) != null; i++) {
                if (assembler.getTarget() instanceof fUML.Syntax.Classes.Kernel.Package) {
                	fUML.Syntax.Classes.Kernel.Package pckg = (fUML.Syntax.Classes.Kernel.Package) assembler.getTarget();
                    String name = pckg.name;
                    if (i > 0)
                        qualifiedPackageName = name + "." + qualifiedPackageName;
                    else
                        qualifiedPackageName = name;
                } else
                    break;
            }

            // FIXME: generalize this hack using associations in metamodel
            String libraryObjectClassName = null;
            if (qualifiedPackageName != null && qualifiedPackageName.trim().length() > 0)
            	libraryObjectClassName = qualifiedPackageName + "." + targetType.name;
            else
            	libraryObjectClassName = targetType.name;
            
            targetType.qualifiedName = libraryObjectClassName;
            
            if (parentPackage != null)
            	targetType.package_ = parentPackage;
            
            if (!(targetType instanceof fUML.Syntax.Classes.Kernel.Class_))
            	return;
            fUML.Syntax.Classes.Kernel.Class_ targetClass = (fUML.Syntax.Classes.Kernel.Class_)targetType;

            String implObjectClassName = FumlConfiguration.getInstance().findExecutionClassName(
                    libraryObjectClassName);
            if (implObjectClassName == null)
                return; // not mapped - we're not interested

            Classifier implClassifier = metadata.getClassifierByQualifiedName(implObjectClassName);
            if (implClassifier == null) {
            	if (log.isDebugEnabled())
                    log.debug("(expected) no classifier found for mapped library class '"
                        + implObjectClassName + "' for library class, " 
                        + libraryObjectClassName);
            }
            log.info("mapped " + targetType.name + " to " + implObjectClassName);
            
            Object object = ReflectionUtils.instanceForName(implObjectClassName);

            if (object instanceof ImplementationObject) {
                ImplementationObject execution = (ImplementationObject) object;
                execution.types.add(targetClass);
                log.info("adding to locus: " + execution.getClass().getName());
                Environment.getInstance().locus.add(execution);
            } else if (object instanceof OpaqueBehaviorExecution) {
                OpaqueBehaviorExecution execution = (OpaqueBehaviorExecution) object;
                execution.types.add(targetClass);
                Environment.getInstance().locus.factory.addPrimitiveBehaviorPrototype(execution);
            } else
                log.warn("unknown instance, " + object.getClass().getName());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InvocationTargetException e) {
            log.error(e.getCause().getMessage(), e.getCause());
            throw new AssemblyException(e.getCause());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InstantiationException e) {
            if (e.getCause() != null)
                log.error(e.getCause().getMessage(), e.getCause());
            else
                log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        }
    }

    // TODO: find the "opposite" property using metadata and value
    // that as well. Metadata will need to be enhanced to use associations.
    public void associateElement(ElementAssembler other) {
        try {
            Property property = other.getPrototype().findProperty(this.getSource().getLocalName());
            if (property == null)
                return; // we validate this elsewhere

            // If the target property is "generalization", then defer associating it with the source.
            // This is necessary to ensure that the general classifier is fully assembled before
            // it is added as a generalization of the specific classifier. Otherwise, the
            // inherited members of the specific classifier will not be set properly.
            if ("generalization".equals(this.source.getLocalName())) {
            	this.addDeferredGeneralization((Generalization)this.getTargetObject());
            	return;
            }
            
            if (!property.isSingular()) {
                if (log.isDebugEnabled())
                    log.debug("linking collection property: " + other.getPrototype().getName() + "."
                            + this.getSource().getLocalName() + " with: "
                            + this.getPrototype().getName());
                try {
                    String methodName = "add"
                            + this.getSource().getLocalName().substring(0, 1).toUpperCase()
                            + this.getSource().getLocalName().substring(1);
                    Method adder = ReflectionUtils.getMethod(other.getTargetClass(), methodName, this
                            .getTargetClass());
                    Object[] args = { this.getTargetObject() };
                    adder.invoke(other.getTarget(), args);
                } catch (NoSuchMethodException e) {
                    // try to get and add to the list field if exists
                    try {
                        Field field = other.getTargetClass().getField(
                                this.getSource().getLocalName());
                        Object list = field.get(other.getTargetObject());
                        Method adder = ReflectionUtils.getMethod(list.getClass(), "add", this.getTargetClass());
                        Object[] args = { this.getTargetObject() };
                        adder.invoke(list, args);
                    } catch (NoSuchFieldException e2) {
                        log.warn("no 'add' or 'List.add' method found for property, "
                                        + other.getPrototype().getName() + "."
                                        + this.getSource().getLocalName());
                    }
                }
            } else {
                if (log.isDebugEnabled())
                    log.debug("linking singular property: " + other.getPrototype().getName() + "."
                            + this.getSource().getLocalName() + " with: "
                            + this.getPrototype().getName());
                try {
                    String methodName = "set"
                            + this.getSource().getLocalName().substring(0, 1).toUpperCase()
                            + this.getSource().getLocalName().substring(1);
                    Method setter = ReflectionUtils.getMethod(other.getTargetClass(), methodName, this
                            .getTargetClass());
                    Object[] args = { this.getTargetObject() };
                    setter.invoke(other.getTarget(), args);
                } catch (NoSuchMethodException e) {
                    // try to get and add to the list field if exists
                    try {
                        Field field = other.getTargetClass().getField(
                                this.getSource().getLocalName());
                        field.set(other.getTargetObject(), this.getTargetObject());
                    } catch (NoSuchFieldException e2) {
                        log.warn("no 'set' method or public field found for property, "
                                        + other.getPrototype().getName() + "."
                                        + this.getSource().getLocalName());
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InvocationTargetException e) {
            log.error(e.getCause().getMessage(), e.getCause());
            throw new AssemblyException(e.getCause());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        }
    }

    public void associateDeferredGeneralizations() {
		List<Generalization> deferredGeneralizations = this.getDeferredGeneralizations();
		if (deferredGeneralizations != null) {
			while (!deferredGeneralizations.isEmpty()) {
				int i = 0;
				while (i < deferredGeneralizations.size()) {
					Generalization deferredGeneralization = deferredGeneralizations.get(i);
					// Only associate a deferred generalization with its specific classifier
					// if there are no generalizations still deferred for the general
					// classifier.
					for (Generalization generalization: deferredGeneralizations) {
						if (generalization.specific == deferredGeneralization.general) {
							i++;
							continue;
						}
					}
					deferredGeneralization.specific.addGeneralization(deferredGeneralization);
					deferredGeneralizations.remove(deferredGeneralization);
				}
			}
		}
	}
    
	public void assembleFeatures() {
        try {
            NamespaceDomain domain = null; // only lookup as needed	            	
            StreamNode eventNode = (StreamNode) source;

            Location loc = eventNode.getLocation();
            if (log.isDebugEnabled())
                log.debug("element line/column: " + loc.getLineNumber() + ":"
                        + loc.getColumnNumber());

            // look at XML attributes
            Iterator<Attribute> attributes = eventNode.getAttributes();
            while (attributes != null && attributes.hasNext()) {
                Attribute xmlAttrib = attributes.next();

                QName name = xmlAttrib.getName();
                String prefix = name.getPrefix();
                if (prefix != null && prefix.length() > 0)
                    continue; // not applicable to current
                              // element/association-end.
                if ("href".equals(name.getLocalPart()))
                    continue; // FIXME: find/write URI resolver

                Property property = this.prototype.findProperty(name.getLocalPart());
                if (property == null) {
                	if (domain == null)
                		domain = FumlConfiguration.getInstance().findNamespaceDomain(source.getNamespaceURI());	
	            	ValidationExemption exemption = FumlConfiguration.getInstance().findValidationExemptionByProperty(ValidationExemptionType.UNDEFINED_PROPERTY,
	            			this.prototype, name.getLocalPart(), source.getNamespaceURI(), domain);
	            	if (exemption == null) {
	                    throw new ValidationException(new ValidationError(eventNode, name
	                            .getLocalPart(), ErrorCode.UNDEFINED_PROPERTY, ErrorSeverity.FATAL));
	            	}
	            	else {
	    				if (log.isDebugEnabled())
	    					log.debug("undefined property exemption found within domain '" 
	    						+ exemption.getDomain().toString() + "' for property '"
	    						+ this.prototype.getName() + "." + name.getLocalPart() + "' - ignoring error");
	            		continue; // ignore attrib
	            	}                
                }    
                Classifier type = property.getType();

                if (this.modelSupport.isReferenceAttribute(property)) {
                    XmiReferenceAttribute reference = new XmiReferenceAttribute(source, xmlAttrib, this.getPrototype());
                    this.addReference(reference);
                    continue;
                }
                
                String value = xmlAttrib.getValue();
                if (value == null || value.length() == 0) 
                {
                    String defaultValue = property.findPropertyDefault();
                    if (defaultValue != null) {
                        value = defaultValue;
                        if (log.isDebugEnabled())
                            log.debug("using default '" + String.valueOf(value)
                                    + "' for enumeration feature <" + type.getName() + "> "
                                    + this.getPrototype().getName() + "." + property.getName());
                    }
                }

                this.assembleNonReferenceFeature(property, value, type);
            }

            // look at model properties not found in above attribute set
            List<Property> properties = this.prototype.getNamedProperties();
            for (Property property : properties) {
                QName name = new QName(property.getName());
                String value = eventNode.getAttributeValue(name);
                if (value != null && value.trim().length() > 0)
                    continue; // handled above

                String defaultValue = property.findPropertyDefault();
                if (defaultValue != null) {
                    Classifier type = property.getType();
                    if (log.isDebugEnabled())
                        log.debug("using default: '" + String.valueOf(defaultValue)
                                + "' for enumeration feature <" + type.getName() + "> "
                                + this.getPrototype().getName() + "." + property.getName());
                    this.assembleNonReferenceFeature(property, defaultValue, type);
                    continue;
                }

                if (!property.isRequired())
                    continue; // don't bother digging further for a value

                if (eventNode.findChildByName(property.getName()) != null)
                    continue; // it has such a child, let

                if (this.modelSupport.isReferenceAttribute(property)
                        && FumlConfiguration.getInstance().hasReferenceMapping(this.prototype,
                        		property)) {
                    ReferenceMappingType mappingType = FumlConfiguration.getInstance()
                            .getReferenceMappingType(this.prototype, property);
                    if (mappingType == ReferenceMappingType.PARENT) {
                        if (parent != null && parent.getXmiId() != null
                                && parent.getXmiId().length() > 0) {
                            XmiMappedReference reference = new XmiMappedReference(source,
                            		property.getName(), new String[] { parent.getXmiId() },
                            		this.prototype);
                            this.addReference(reference);
                            continue;
                        } else
                            log.warn("no parent XMI id found, ignoring mapping for, "
                                    + this.prototype.getName() + "." + property.getName());
                    } else
                        log.warn("unrecognized mapping type, " + mappingType.value()
                                + " ignoring mapping for, " + this.prototype.getName() + "."
                                + property.getName());
                }

                if (!property.isDerived()) {
                	if (domain == null)
                		domain = FumlConfiguration.getInstance().findNamespaceDomain(source.getNamespaceURI());	
	            	ValidationExemption exemption = FumlConfiguration.getInstance().findValidationExemptionByProperty(ValidationExemptionType.REQUIRED_PROPERTY,
	            			this.prototype, name.getLocalPart(), source.getNamespaceURI(), domain);
	            	if (exemption == null) {
	                    if (log.isDebugEnabled())
	                        log.debug("throwing " + ErrorCode.PROPERTY_REQUIRED.toString()
	                                + " error for " + this.prototype.getName() + "." + property.getName());
	                    throw new ValidationException(new ValidationError(eventNode,
	                    		property.getName(), ErrorCode.PROPERTY_REQUIRED, ErrorSeverity.FATAL));
	            	}
	            	else {
	    				if (log.isDebugEnabled())
	    					log.debug("required property exemption found within domain '" 
	    						+ exemption.getDomain().toString() + "' for property '"
	    						+ this.prototype.getName() + "." + name.getLocalPart() + "' - ignoring error");
	            		continue; // ignore property
	            	}                
                }
            }
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InvocationTargetException e) {
            log.error(e.getCause().getMessage(), e.getCause());
            throw new AssemblyException(e.getCause());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        }
    }

    public void assembleReferenceFeatures() {
        try {
            if (this.references == null)
                return;
            Iterator<XmiReference> iter = this.references.iterator();
            while (iter.hasNext()) {
                XmiReference reference = iter.next();
                this.assembleReferenceFeature(reference);
            }
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InvocationTargetException e) {
            log.error(e.getCause().getMessage(), e.getCause());
            throw new AssemblyException(e.getCause());
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (InstantiationException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        } catch (NoSuchFieldException e) {
            log.error(e.getMessage(), e);
            throw new AssemblyException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    private void assembleNonReferenceFeature(Property property, String stringValue, Classifier type)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {
        if (type.getDelegate() instanceof Enumeration) {
            if (log.isDebugEnabled())
                log.debug("assembling enum feature <" + type.getName() + "> " + this.getPrototype().getName()
                        + "." + property.getName() + " = " + stringValue);
            Object value = toEnumerationValue(stringValue, type);
            assembleEnumerationFeature(property, value, type);
        } else if (type.getDelegate() instanceof DataType) {
        	Class javaType = toPrimitiveJavaClass((DataType) type.getDelegate());
            Object value = toPrimitiveJavaValue(stringValue, (DataType) type.getDelegate(), javaType);

            if (log.isDebugEnabled())
                log.debug("assembling primitive feature <" + javaType.getName() + "> "
                        + this.getPrototype().getName() + "." + property.getName() + " = " + stringValue);

            if (property.isSingular())
                assembleSingularPrimitiveFeature(property, value, javaType);
            else
                assembleCollectionPrimitiveFeature(property, value, javaType);
        } else if (type.getDelegate() instanceof fUML.Syntax.Classes.Kernel.Class_) {
            if (UnlimitedNatural.class.getSimpleName().equals(type.getName())) {
                UnlimitedNatural value = new UnlimitedNatural();
                if ("*".equals(stringValue))
                    value.naturalValue = -1;
                else
                    try {
                        value.naturalValue = Integer.parseInt(stringValue);
                    } catch (NumberFormatException e) {
                        //log.error("could not parse string value '" + stringValue + "'", e);
                        throw new AssemblyException(e);
                    }
                if (log.isDebugEnabled())
                    log.debug("assembling primitive feature <"
                            + UnlimitedNatural.class.getSimpleName() + "> "
                            + this.getPrototype().getName() + "." + property.getName() + " = " + stringValue);
                if (property.isSingular())
                    assembleSingularPrimitiveFeature(property, value, UnlimitedNatural.class);
                else
                    assembleCollectionPrimitiveFeature(property, value, UnlimitedNatural.class);
            } else
                throw new AssemblyException("unexpected Class_, " + type.getName());
        } else
            throw new AssemblyException("unexpected instance, " + type.getClass().getName());
    }

    @SuppressWarnings("rawtypes")
    private void assembleReferenceFeature(XmiReference reference) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException, NoSuchFieldException {
        StreamNode eventNode = (StreamNode) this.getSource();

        NamespaceDomain domain = null; // only lookup as needed
        Property property = this.prototype.getProperty(reference.getLocalName());
        Classifier type = property.getType();

        if (type.getDelegate() instanceof Enumeration || type.getDelegate() instanceof DataType)
            return; // handled these in pre-assembly

        String instancecClassName = type.getName();
        // TODO: We have a keyword map, but unclear whether upper-case 'Class' should be mapped. Definately 'class' is.
        if ("Class".equals(instancecClassName))
            instancecClassName = instancecClassName + "_"; 

        String packageName = metadata.getJavaPackageNameForClass(type);
        String qualifiedName = packageName + "." + instancecClassName;
        Class fClass_ = Class.forName(qualifiedName);
        if (!Element.class.isAssignableFrom(fClass_)) {
            log.warn("ignoring non-element feature <" + type.getName() + "> " + this.getPrototype().getName()
                    + "." + property.getName());
            return;
        }

        if (property.isSingular()) {
            if (log.isDebugEnabled())
                log.debug("assembling singular reference feature <" + type.getName() + "> "
                        + this.getPrototype().getName() + "." + property.getName());

            if (reference.getReferenceCount() != 1)
                log.warn("expected single reference, not "
                        + String.valueOf(reference.getReferenceCount()));

            String id = reference.getXmiIds().next(); // expect only one for
                                                      // singular prop

            if (reference instanceof XmiExternalReferenceElement) {
                if (assembleExternalReferences) {
                	// FIXME: resolve these references inside/outside of lib(s)
                    if (id == null || !id.startsWith("pathmap:")) 
                    {
                        Element referent = Library.getInstance().lookup(id);
                        if (referent == null) {                        	
                        	if (domain == null)
                        		domain = FumlConfiguration.getInstance().findNamespaceDomain(eventNode.getNamespaceURI());	            	

                        	ValidationExemption exemption = FumlConfiguration.getInstance().findValidationExemptionByReference(ValidationExemptionType.EXTERNAL_REFERENCE,
                        			reference.getClassifier(), id, eventNode.getNamespaceURI(), domain);
                        	if (exemption == null) {
                                throw new ValidationException(new ValidationError(reference, id,
                                        ErrorCode.INVALID_EXTERNAL_REFERENCE, ErrorSeverity.FATAL));
                        	}
                        	// otherwise don't attempt to assemble
                        }
                        else
                            this.assembleSingularReferenceFeature(referent, property, type);
                    }
                }
            } else {
                Element target = null;

                ElementAssembler referencedAssembler = this.assemblerMap.get(id);
                if (referencedAssembler != null)
                    target = referencedAssembler.getTarget();
                else
                    target = Environment.getInstance().findElementById(id);

                if (target != null)
                    this.assembleSingularReferenceFeature(target, property, type);
                else
                    throw new ValidationException(new ValidationError(reference, id,
                            ErrorCode.INVALID_REFERENCE, ErrorSeverity.FATAL));
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("assembling collection reference feature <" + type.getName() + "> "
                        + this.getPrototype().getName() + "." + property.getName());

            Iterator<String> iter = reference.getXmiIds();
            while (iter.hasNext()) {
                String id = iter.next();
                if (reference instanceof XmiExternalReferenceElement) {
                    if (assembleExternalReferences) {
                    	// FIXME: resolve these references inside/outside of lib(s)
                        if (id == null || !id.startsWith("pathmap:")) 
                        {
                            Element referent = Library.getInstance().lookup(id);
                            if (referent == null) {
                            	if (domain == null)
                            		domain = FumlConfiguration.getInstance().findNamespaceDomain(eventNode.getNamespaceURI());	            	

                            	ValidationExemption exemption = FumlConfiguration.getInstance().findValidationExemptionByReference(ValidationExemptionType.EXTERNAL_REFERENCE,
                            			reference.getClassifier(), id, eventNode.getNamespaceURI(), domain);
                            	if (exemption == null) {
                                    throw new ValidationException(new ValidationError(reference, id,
                                            ErrorCode.INVALID_EXTERNAL_REFERENCE, ErrorSeverity.FATAL));
                            	}
                            	// otherwise don't attempt to assemble
                            }
                            else
                                this.assembleCollectionReferenceFeature(referent, property, type);
                        }
                    }
                } else {
                	FumlObject target = null;
                	
                    ElementAssembler referencedAssembler = this.assemblerMap.get(id);
                    if (referencedAssembler != null)
                        target = referencedAssembler.getTargetObject();
                    else
                        target = Environment.getInstance().findElementById(id);

                    if (target != null)
                        this.assembleCollectionReferenceFeature(target, property, type);
                    else
                        throw new ValidationException(new ValidationError(reference, id,
                                ErrorCode.INVALID_REFERENCE, ErrorSeverity.FATAL));
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void assembleEnumerationFeature(Property property, Object value, Classifier type)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InstantiationException {
        try {
            Class[] types = { value.getClass() };
            String methodName = "set" + property.getName().substring(0, 1).toUpperCase()
                    + property.getName().substring(1);
            Method setter = this.getTarget().getClass().getMethod(methodName, types);
            Object[] args = { value };
            setter.invoke(this.getTarget(), args);
        } catch (NoSuchMethodException e) {
            try {
                Field field = this.getTargetClass().getField(property.getName());
                field.set(this.getTargetObject(), value);
            } catch (NoSuchFieldException e2) {
                String msg = "no fUML (" + this.getTargetObject().getClass().getName()
                        + ") setter method or public field found for enumeration feature " + "<"
                        + type.getName() + "> " + this.getPrototype().getName() + "." + property.getName();
                log.warn(msg);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void assembleSingularPrimitiveFeature(Property property, Object value, Class javaType)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {
        String methodName = "set" + property.getName().substring(0, 1).toUpperCase()
                + property.getName().substring(1);
        Object[] args = { value };
        try {
            Method setter = this.getTargetClass().getMethod(methodName, new Class[] { javaType });
            setter.invoke(this.getTargetObject(), args);
        } catch (NoSuchMethodException e) {
            try {
                Object targetObject = this.getTargetObject();
                Class targetClass = targetObject.getClass();
                Field field = targetClass.getField(property.getName());
                field.set(targetObject, value);
            } catch (NoSuchFieldException e2) {
                String msg = "no fUML (" + this.getTargetObject().getClass().getName()
                        + ") setter method named '"+methodName+"' or public field found for primitive feature " + "<"
                        + javaType.getName() + "> " + this.getPrototype().getName() + "."
                        + property.getName();
                log.warn(msg);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void assembleCollectionPrimitiveFeature(Property property, Object value, Class javaType)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {
        String methodName = "add" + property.getName().substring(0, 1).toUpperCase()
                + property.getName().substring(1);
        try {
            Object[] args = { value };
            Method adder = this.getTargetClass().getMethod(methodName, new Class[] { javaType });
            adder.invoke(this.getTargetObject(), args);
        } catch (NoSuchMethodException e) {
            try {
                Field field = this.getTargetClass().getField(property.getName());
                Object list = field.get(this.getTargetObject());
                Method adder = ReflectionUtils.getMethod(list.getClass(), "add", value.getClass());
                Object[] args = { value };
                adder.invoke(list, args);
            } catch (NoSuchMethodException e2) {
                String msg = "no fUML (" + this.getTargetObject().getClass().getName()
                        + ") add method or public field found for primitive collection property "
                        + "<" + javaType.getName() + "> " + this.getPrototype().getName() + "."
                        + property.getName();
                log.warn(msg);
            } catch (NoSuchFieldException e2) {
                String msg = "no fUML (" + this.getTargetObject().getClass().getName()
                        + ") add method or public field found for primitive collection property "
                        + "<" + javaType.getName() + "> " + this.getPrototype().getName() + "."
                        + property.getName();
                log.warn(msg);
            }
        }
    }

    private void assembleSingularReferenceFeature(FumlObject target, Property property, Classifier type)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        try {
            String methodName = "set" + property.getName().substring(0, 1).toUpperCase()
                    + property.getName().substring(1);
            Method setter = ReflectionUtils.getMethod(this.getTarget().getClass(), methodName, target.getClass());
            Object[] args = { target };
            setter.invoke(this.getTarget(), args);
        } catch (NoSuchMethodException e) {
            try {
                Field field = this.getTargetClass().getField(property.getName());
                field.set(this.getTargetObject(), target);
            } catch (NoSuchFieldException e2) {
                String msg = "no fUML (" + this.getTargetObject().getClass().getName()
                        + ") setter method or public field found for singular property " + "<"
                        + type.getName() + "> " + this.getPrototype().getName() + "." + property.getName();
                log.warn(msg);
            }
        }
    }

    private void assembleCollectionReferenceFeature(FumlObject target, Property property,
            Classifier type) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {
        try {
            String methodName = "add" + property.getName().substring(0, 1).toUpperCase()
                    + property.getName().substring(1);
            Method adder = ReflectionUtils.getMethod(this.getTargetObject().getClass(), methodName, target
                    .getClass());
            Object[] args = { target };
            adder.invoke(this.getTarget(), args);
        } catch (NoSuchMethodException e) {
            try {
                Field field = this.getTargetClass().getField(property.getName());
                Object list = field.get(this.getTargetObject());
                Method adder = ReflectionUtils.findMethod(list.getClass(), "add", target.getClass());
                Object[] args = { target };
                adder.invoke(list, args);
            } catch (NoSuchMethodException e2) {
                String msg = "no fUML (" + this.getTargetObject().getClass().getName()
                        + ") add method or public field found for collection property " + "<"
                        + type.getName() + "> " + this.getPrototype().getName() + "." + property.getName();
                log.warn(msg);
            }
        }
    }
    
    /**
     * Returns the Java class associated with the given primitive type. 
     * 
     * Note: A primitive type defines 
     * a predefined data type, without any relevant substructure (i.e., it has no defined parts in 
     * the UML/MOF Infrastructure). So a Property who's datatype is a PrimitiveType cannot be treated as
     * a reference Property, e.g. reference to UnlimitedNatural, because the internal structure of UnlimitedNatural
     * is not defined.    
     * 
     * @param dataType the dataType to convert.
     * @return the Java Class
     */
    @SuppressWarnings("rawtypes")
	private Class toPrimitiveJavaClass(DataType dataType) {
        if (PrimitiveType.class.isAssignableFrom(dataType.getClass())) {
            if (dataType.name != null && dataType.name.trim().length() > 0) {
                if (String.class.getSimpleName().equals(dataType.name))
                    return java.lang.String.class;
                else if (Integer.class.getSimpleName().equals(dataType.name))
                    return int.class;
                else if (Boolean.class.getSimpleName().equals(dataType.name))
                    return boolean.class;
                else if (UnlimitedNatural.class.getSimpleName().equals(dataType.name))
                    return int.class; 
                else if ("Real".equals(dataType.name))
                    return float.class;
                else
                    throw new AssemblyException("unknown dataType ("
                            + dataType.getClass().getName() + ") name: '" + dataType.name + "'");
            } else if (dataType.getHref() != null) {
                if (dataType.getHref().endsWith(String.class.getSimpleName()))
                    return java.lang.String.class;
                else if (dataType.getHref().endsWith(Integer.class.getSimpleName()))
                    return int.class;
                else if (dataType.getHref().endsWith(Boolean.class.getSimpleName()))
                    return boolean.class;
                else if (dataType.getHref().endsWith(UnlimitedNatural.class.getSimpleName()))
                    return int.class;
                else if (dataType.getHref().endsWith("Real"))
                    return int.class;
                else
                    throw new AssemblyException("unknown dataType ("
                            + dataType.getClass().getName() + ") href: '" + dataType.getHref() + "'");
            } else
                throw new AssemblyException("expected name or href for primitive type, "
                        + dataType.getClass().getName());
        } else {
            throw new AssemblyException("expected primitive type not ("
                    + dataType.getClass().getName() + ") name: '" + dataType.name + "'");
        }
    }

    /**
     * Returns the Java value associated with the given String value in the context of the given
     * primitive type and Java type. 
     * 
     * Note: A primitive type defines 
     * a predefined data type, without any relevant substructure (i.e., it has no defined parts in 
     * the UML/MOF Infrastructure). So a Property who's datatype is a PrimitiveType cannot be treated as
     * a reference Property, e.g. reference to UnlimitedNatural, because the internal structure of UnlimitedNatural
     * is not defined.    
     * 
     * @param value the value to convert.
     * @param dataType the data type under which to evaluate the the String value.
     * @param javaType the Java type under which to evaluate the the String value.
     * @return the value
     */
    @SuppressWarnings("rawtypes")
    private Object toPrimitiveJavaValue(String value, DataType dataType, Class javaType) {
        if (javaType.equals(java.lang.String.class))
            return value;
        else if (javaType.equals(java.lang.Integer.class))
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                if (value == null || value.length() == 0)
                    return new Integer(0);
                else if (UnlimitedNatural.class.getSimpleName().equals(dataType.name) && "*".equals(value))
                	return new Integer(-1);	
                else
                    throw e;
            }
        else if (javaType.equals(int.class))
            try {
                return Integer.valueOf(value).intValue();
            } catch (NumberFormatException e) {
                if (value == null || value.length() == 0)
                    return 0;
                else if (UnlimitedNatural.class.getSimpleName().equals(dataType.name) && "*".equals(value))
                	return -1;	
                else
                    throw e;
            }
        else if (javaType.equals(java.lang.Boolean.class))
            return Boolean.valueOf(value);
        else if (javaType.equals(boolean.class))
            return (boolean) Boolean.valueOf(value).booleanValue();
        else if (javaType.equals(float.class)) {
        	return (float)Float.valueOf(value).floatValue();
        }
        else
            return value;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Object toEnumerationValue(String value, Classifier type) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        String pkg = metadata.getJavaPackageNameForClass(type);
        String qualifiedName = pkg + "." + type.getName();
        Class enumClass = Class.forName(qualifiedName);
        if (!enumClass.isEnum())
            throw new AssemblyException("expected class as enum, " + enumClass.getName());

        Method valueOf = enumClass.getMethod("valueOf", new Class[] { String.class });

        if (JavaKeyWords.getInstance().isKeyWord(value))
            value = value + "_";
        Object enumValue = valueOf.invoke(enumClass, value);
        return enumValue;
    }
    
    public String getXmiId() {
        return this.getTargetObject().getXmiId();
    }

    public Element getTarget() {
        return target;
    }

    public FumlObject getTargetObject() {
        if (target != null)
            return target;
        else
            return targetComment;
    }

    @SuppressWarnings("rawtypes")
	public Class getTargetClass() {
        if (target != null)
            return target.getClass();
        else
            return targetComment.getClass();
    }

    public Comment getTargetComment() {
        return targetComment;
    }

    public Class_ getPrototype() {
        return prototype;
    }

    public XmiNode getSource() {
        return source;
    }

    public XmiNode getParent() {
        return parent;
    }

    public void addReference(XmiReference ref) {
        if (references == null)
            references = new ArrayList<XmiReference>();
        references.add(ref);
    }

    public boolean isAssembleExternalReferences() {
        return assembleExternalReferences;
    }

    public void setAssembleExternalReferences(boolean assembleExternalReferences) {
        this.assembleExternalReferences = assembleExternalReferences;
    }

}
