/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * Modifications:
 * Copyright 2009-2017 Data Access Technologies, Inc.
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 */
package org.modeldriven.fuml.repository.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.FumlSystemProperty;
import org.modeldriven.fuml.bind.DefaultValidationEventHandler;
import org.modeldriven.fuml.common.reflect.ReflectionUtils;
import org.modeldriven.fuml.config.ExtensionPackage;
import org.modeldriven.fuml.config.FumlConfiguration;
import org.modeldriven.fuml.repository.config.Artifact;
import org.modeldriven.fuml.repository.config.IgnoredClass;
import org.modeldriven.fuml.repository.config.IgnoredPackage;
import org.modeldriven.fuml.repository.config.RepositoryConfig;
import org.modeldriven.fuml.repository.config.RegisteredPackage;
import org.modeldriven.fuml.repository.Class_;
import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Element;
import org.modeldriven.fuml.repository.Package;
import org.modeldriven.fuml.repository.Property;
import org.modeldriven.fuml.repository.RepositoryConfigDataBinding;
import org.modeldriven.fuml.repository.RepositoryMapping;
import org.modeldriven.fuml.repository.RepositoryException;
import org.modeldriven.fuml.repository.Extension;
import org.modeldriven.fuml.repository.Stereotype;
import org.modeldriven.fuml.xmi.InvalidReferenceException;
import org.modeldriven.fuml.xmi.XmiException;
import org.xml.sax.SAXException;

import fuml.syntax.classification.Operation;
import fuml.syntax.commonstructure.PackageableElement;

public class InMemoryRepository extends InMemoryMapping 
    implements org.modeldriven.fuml.repository.Repository 
{
	public static final String ROOT_FUML_SYNTAX_PACKAGE = "fuml.syntax";
	
    private static Log log = LogFactory.getLog(InMemoryRepository.class);
    private static InMemoryRepository instance = null;
    private static String defaultConfigFileName = "RepositoryConfig.xml";  
    private static final List<Classifier> EMPTY_CLASSIFIER_LIST = new ArrayList<Classifier>();

    private RepositoryConfig config;

    //private Map<String, Map<String, Property>> classNameToAttributeMap = new HashMap<String, Map<String, Property>>();
    //protected Map<String, Map<String, Operation>> classNameToOperationMap = new HashMap<String, Map<String, Operation>>();
    
    private Map<String, IgnoredPackage> ignoredPackageNameMap = new HashMap<String, IgnoredPackage>();
    private Map<String, IgnoredClass> ignoredClassNameMap = new HashMap<String, IgnoredClass>();
    private String activeConfigFileName;

    private InMemoryRepository() {
         
        log.info("initializing...");
        try {
            RepositoryConfigDataBinding configBinding = new RepositoryConfigDataBinding(
                    new DefaultValidationEventHandler());
            activeConfigFileName = System.getProperty(FumlSystemProperty.REPOSITORY.getProperty(), 
            		defaultConfigFileName);
            
            config = unmarshalConfig(activeConfigFileName, configBinding);
            
        } catch (SAXException e) {
            throw new RepositoryException(e);
        } catch (JAXBException e) {
            throw new RepositoryException(e);
        }

        Iterator<IgnoredPackage> packages = config.getIgnoredPackage().iterator();
        while (packages.hasNext()) {
            IgnoredPackage pkg = packages.next();
            ignoredPackageNameMap.put(pkg.getName(), pkg);
        }

        Iterator<IgnoredClass> classes = config.getIgnoredClass().iterator();
        while (classes.hasNext()) {
            IgnoredClass c = classes.next();
            ignoredClassNameMap.put(c.getName(), c);
        }

        this.bootstrap();
        this.construct();

    }
    
    public static InMemoryRepository getInstance() throws RepositoryException {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance() throws RepositoryException {
        if (instance == null)
            instance = new InMemoryRepository();
    }

    private void construct() {
        log.info("cacheing classifier attributes and operations...");
        Iterator<String> classes = classifierNameToClassifierMap.keySet().iterator();
        while (classes.hasNext()) {
            String className = classes.next();
            Classifier classifier = classifierNameToClassifierMap.get(className);
            
            if (classifier instanceof Class_)
                construct((Class_)classifier, className);
        }
    }

    /**
     * Collects attributes and operations for the given class and
     * maps it by default UML namespace URI and artifact namespace URI 
     * qualified names.  Because of the mapping to default UML namespace, 
     * This method is only for use on initialization
     * when M2 level FUML/UML artifacts are being loaded.
     * @param clss the class
     * @param className the class name
     */
    private void construct(Class_ clss, String className) {
        List<Property> attributes = new ArrayList<Property>();
        collectAttributes(clss, attributes);
        //classNameToAttributeMap.put(className, attributes);

        List<Operation> operations = new ArrayList<Operation>();
        collectOperations(clss, operations);
        //classNameToOperationMap.put(className, operations);   	

        org.modeldriven.fuml.repository.model.Class_ implClass = (org.modeldriven.fuml.repository.model.Class_)clss;
        implClass.setAttributes(attributes);
        implClass.setOperations(operations);
        
        // map final unqualified class to the default namespace, ...
        // FIXME: do we map by every "supported" namespace
        String namespaceQualifiedName = this.config.getDefaultUMLNamespaceURI() + "#" + className;
        this.qualifiedClassifierNameToClassifierMap.put(namespaceQualifiedName, clss);

        String artifactQualifiedName = clss.getArtifact().getNamespaceURI() + "#" + className;
        this.qualifiedClassifierNameToClassifierMap.put(artifactQualifiedName, clss);
        
        //String packageQualifiedName = implClass.getDelegate().package_.name + "." + className;
        //this.qualifiedClassifierNameToClassifierMap.put(packageQualifiedName, clss);
    }    
        
    /**
     * Collects attributes and operations for the given class and
     * maps it by artifact(file) namespace URI qualified
     * names. 
     * @param clss the class
     * @param className the class name
     */
    public void loadClass(Class_ clss) {
        List<Property> attributes = new ArrayList<Property>();
        collectAttributes(clss, attributes);

        List<Operation> operations = new ArrayList<Operation>();
        collectOperations(clss, operations);

        org.modeldriven.fuml.repository.model.Class_ implClass = (org.modeldriven.fuml.repository.model.Class_)clss;
        implClass.setAttributes(attributes);
        implClass.setOperations(operations);
        
        String artifactQualifiedName = clss.getArtifact().getNamespaceURI() + "#" + clss.getName();
        this.qualifiedClassifierNameToClassifierMap.put(artifactQualifiedName, clss);
    }    

    private void bootstrap() {

    	Map<Artifact, ModelAssembler> factoryMap = new HashMap<Artifact, ModelAssembler>();
        Iterator<Artifact> artifacts = config.getArtifact().iterator();
        while (artifacts.hasNext()) {
        	Artifact artifact = artifacts.next();
            Object[] args = { artifact, this, this };
            Class<?>[] types = { Artifact.class, RepositoryMapping.class, org.modeldriven.fuml.repository.Repository.class };
            try {
            	ModelAssembler factory = (ModelAssembler)ReflectionUtils.instanceForName(
                		artifact.getFactoryClassName(), 
                        args, types);               
            	factoryMap.put(artifact, factory);
            }
            catch (Throwable e) {
            	log.error(e.getMessage(), e);
            	throw new RepositoryException(e);
            }
        }
    	
        artifacts = config.getArtifact().iterator();
        while (artifacts.hasNext()) {
        	Artifact artifact = artifacts.next();
        	ModelAssembler factory = factoryMap.get(artifact); 
            Iterator<RegisteredPackage> packages = artifact.getRegisteredPackage().iterator();
            while (packages.hasNext()) {
            	RegisteredPackage pkg = packages.next();
            	registerPackage((org.modeldriven.fuml.repository.RepositoryArtifact)factory, pkg.getName());
            }           
        }    
    }

    private Package getPackage(org.modeldriven.fuml.repository.RepositoryArtifact artifact, String qualifiedName) {
    	
        List<Package> artifactPackages = artifactURIToPackagesMap.get(artifact.getURN());
   	    Iterator<Package> iter = artifactPackages.iterator();	
    	while (iter.hasNext())
    	{   			
    		Package p = iter.next();
    		if (qualifiedName.equals(p.getQualifiedName()))
    			return p;
    	}
    	throw new RepositoryException("could not get package, "
    			+ qualifiedName);
    }
    
    private void registerPackage(org.modeldriven.fuml.repository.RepositoryArtifact aftifact, String qualifiedName) {
    	Package p = getPackage(aftifact, qualifiedName);
    	registerPackage(p);
    }
   
    private void registerPackage(Package p)
    {
		Iterator<PackageableElement> elementIter = p.getPackagedElement().iterator();
		while (elementIter.hasNext()) {
    		PackageableElement element = elementIter.next();
    		if (element instanceof fuml.syntax.structuredclassifiers.Class_)
    		{
    			fuml.syntax.structuredclassifiers.Class_ c = (fuml.syntax.structuredclassifiers.Class_)element;
    			Class_ clss = new org.modeldriven.fuml.repository.model.Class_(c, p.getArtifact());
    			classifierNameToClassifierMap.put(c.name, clss);
    			classifierNameToPackageNameMap.put(c.name, p.getQualifiedName());
    		}
    		else if (element instanceof fuml.syntax.classification.Classifier)
    		{
    			fuml.syntax.classification.Classifier c = (fuml.syntax.classification.Classifier)element;
    			Classifier classifier = new org.modeldriven.fuml.repository.model.Classifier(c, p.getArtifact());
    			classifierNameToClassifierMap.put(c.name, classifier);
    			classifierNameToPackageNameMap.put(c.name, p.getQualifiedName());
    		}
    		else if (element instanceof fuml.syntax.packages.Package) {
    			Package pkg = new org.modeldriven.fuml.repository.model.Package((fuml.syntax.packages.Package)element, p.getArtifact());
    			registerPackage(pkg); 			
    		}
		}
    }    
    
    @SuppressWarnings("rawtypes")
    private RepositoryConfig unmarshalConfig(String configFileName, RepositoryConfigDataBinding binding) {
        try {
            InputStream stream = Element.class.getResourceAsStream(configFileName);
            if (stream == null)
            	stream = Element.class.getClassLoader().getResourceAsStream(configFileName);
            if (stream == null)
                throw new RepositoryException("cannot find resource '" + configFileName + "'");
            JAXBElement root = (JAXBElement) binding.validate(stream);

            RepositoryConfig result = (RepositoryConfig) root.getValue();
            return result;
        } catch (UnmarshalException e) {
            throw new RepositoryException(e);
        } catch (JAXBException e) {
            throw new RepositoryException(e);
        }
    }

    
    protected void collectAttributes(Class_ clss, List<Property> attributes) {
        fuml.syntax.classification.Property[] props = new fuml.syntax.classification.Property[clss.getDelegate().ownedAttribute.size()];
        clss.getDelegate().ownedAttribute.toArray(props);
        for (int i = 0; i < props.length; i++)
        {
        	fuml.syntax.classification.Property fumlProperty = props[i];        	
    		Property property = new org.modeldriven.fuml.repository.model.Property(fumlProperty, clss.getArtifact());
    		
            attributes.add(property);
        }      	
        
        for (Classifier generalization : clss.getGeneralization()) {
            String superclassXmiId = generalization.getXmiId();
            Element element = elementIdToElementMap.get(superclassXmiId);
            if (element == null) {
                throw new InvalidReferenceException(superclassXmiId);
                // log.warn("invalid reference: " + superclassXmiId);
                // continue;
            }
            Class_ umlSuperClass = (Class_) element;
            collectAttributes(umlSuperClass, attributes);
        }

    }

    protected void collectOperations(Class_ clss, List<Operation> operations) {
        Iterator<Operation> iter = clss.getDelegate().ownedOperation.iterator();
        while (iter.hasNext()) {
            Operation oper = iter.next();
            operations.add(oper);
        }

        for (Classifier generalization : clss.getGeneralization()) {
            String superclassXmiId = generalization.getXmiId();
            Element element = elementIdToElementMap.get(superclassXmiId);
            if (element == null) {
                throw new InvalidReferenceException(superclassXmiId);
                // log.warn("invalid reference: " + superclassXmiId);
                // continue;
            }
            Class_ umlSuperClass = (Class_) element;
            collectOperations(umlSuperClass, operations);
        }

    }

    public RepositoryMapping getMapping() {
    	return this;
    }
    
    public String getDefaultUMLNamespaceURI() {
    	return this.config.getDefaultUMLNamespaceURI();
    }
    
    public Classifier getClassifier(String name) {
        return getClassifier(name, false);
    }

    public Classifier findClassifier(String name) {
        return getClassifier(name, true);
    }

    private Classifier getClassifier(String name, boolean supressErrors) {
        Classifier result = null;
        if (name.indexOf(".") == -1 && name.indexOf("#") == -1) {
            result = this.classifierNameToClassifierMap.get(name);
        }    
        else
            result = this.qualifiedClassifierNameToClassifierMap.get(name);

        if (result == null && !supressErrors)
            throw new RepositoryException("no classifier found for name, '" + name + "'");
        return result;
    }
    
    public Classifier[] getAllClassifiers() {
    	Iterator<String> iter = this.classifierNameToClassifierMap.keySet().iterator();
    	List<Classifier> list = new ArrayList<Classifier>();
    	while (iter.hasNext())
    		list.add(this.classifierNameToClassifierMap.get(iter.next()));
    	Classifier[] result = new Classifier[list.size()];
    	list.toArray(result);
    	return result;
    }

    public List<Extension> getExtensions(Element element) {
    	List<Extension> result = this.elementToExtensionListMap.get(element.getXmiId());
    	if (result != null)
    		return result;
    	else
    		return new ArrayList<Extension>();
    }

    public List<Stereotype> getStereotypes(Element element) {
    	List<Stereotype> result = this.elementToStereotypeListMap.get(element.getXmiId());
    	if (result != null)
    		return result;
    	else
    		return new ArrayList<Stereotype>();
    }
    
    public List<Stereotype> getStereotypes(Class<?> clss) {
    	List<Stereotype> result = this.classToStereotypeListMap.get(clss);
    	if (result != null)
    		return result;
    	else
    		return new ArrayList<Stereotype>();
    }
    
    public List<Stereotype> getAllStereotypes() {
    	List<Stereotype> result = new ArrayList<Stereotype>();
    	for (List<Stereotype> sublist : this.elementToStereotypeListMap.values()) {
    		result.addAll(sublist);
    	}
    	return result;
    }
    
    public String getJavaPackageNameForClass(Classifier classifier) {
        return getJavaPackageNameForClass(classifier, false);
    }

    public String findJavaPackageNameForClass(Classifier classifier) {
        return getJavaPackageNameForClass(classifier, true);
    }

    private String getJavaPackageNameForClass(Classifier classifier, boolean supressErrors) {
        
    	String classifierName = classifier.getName();
    	
    	String result = null;
        if (classifierName.indexOf(".") == -1) {
        	
        	for (ExtensionPackage extPkg : FumlConfiguration.getInstance().getConfig().getImportConfiguration().getExtensionPackage()) {
	        	String qualifiedClassName = extPkg.getName() + "." + classifierName;
	        	try {
	        		Class.forName(qualifiedClassName);
	        		result = extPkg.getName();
	        		break;
				} catch (ClassNotFoundException e) {
				}
	        }
        	
        	if (result == null) {
        		result = this.classifierNameToPackageNameMap.get(classifierName);
        	}
        }
        else
            result = this.qualifiedClassifierNameToPackageNameMap.get(classifierName);
        
        if (result == null && !supressErrors)
            throw new XmiException("no package found for class '" + classifierName + "'");
        return result.replace("UML", ROOT_FUML_SYNTAX_PACKAGE).toLowerCase();
    }

    public boolean isIgnoredClassifier(String classifierName) {
        return this.ignoredClassNameMap.get(classifierName) != null;
    }

    public boolean isIgnoredClassifier(Classifier classifier) {
        String packageName = getJavaPackageNameForClass(classifier);
        if (isIgnoredPackage(packageName))
            return true;
        else
            return this.ignoredClassNameMap.get(classifier.getName()) != null;
    }

    public boolean isIgnoredPackage(String packageName) {
        return this.ignoredPackageNameMap.get(packageName) != null;
    }

    
    public static void main(String[] args) {
    	InMemoryRepository.getInstance();
    }
	public List<Classifier> getSpecializations(Classifier classifier) {
    	List<Classifier> result = this.classifierIdToSpecializationClassifierMap.get(
    		classifier.getXmiId());
    	if (result != null)
    		return result;
    	else
    		return EMPTY_CLASSIFIER_LIST;
    }

}
