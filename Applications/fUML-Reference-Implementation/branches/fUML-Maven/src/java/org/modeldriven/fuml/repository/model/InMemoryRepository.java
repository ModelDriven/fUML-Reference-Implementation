/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.repository.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.bind.DefaultValidationEventHandler;
import org.modeldriven.fuml.common.reflect.ReflectionUtils;
import org.modeldriven.fuml.config.ExtensionPackage;
import org.modeldriven.fuml.config.FumlConfiguration;
import org.modeldriven.fuml.environment.Environment;
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
import org.modeldriven.fuml.repository.RepositorylException;
import org.modeldriven.fuml.repository.Extension;
import org.modeldriven.fuml.repository.Stereotype;
import org.modeldriven.fuml.repository.merge.PackageGraphNode;
import org.modeldriven.fuml.repository.merge.PackageGraphVisitor;
import org.modeldriven.fuml.xmi.InvalidReferenceException;
import org.modeldriven.fuml.xmi.XmiException;
import org.xml.sax.SAXException;

import fUML.Syntax.Classes.Kernel.Generalization;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.PackageableElement;

public class InMemoryRepository extends InMemoryMapping 
    implements org.modeldriven.fuml.repository.Repository 
{
    private static Log log = LogFactory.getLog(InMemoryRepository.class);
    private static InMemoryRepository instance = null;
    private static String configFileName = "RepositoryConfig.xml";

    private RepositoryConfig config;

    //private Map<String, Map<String, Property>> classNameToAttributeMap = new HashMap<String, Map<String, Property>>();
    //protected Map<String, Map<String, Operation>> classNameToOperationMap = new HashMap<String, Map<String, Operation>>();
    
    private Map<String, IgnoredPackage> ignoredPackageNameMap = new HashMap<String, IgnoredPackage>();
    private Map<String, IgnoredClass> ignoredClassNameMap = new HashMap<String, IgnoredClass>();


    private InMemoryRepository() {
         
        log.info("initializing...");
        try {
            RepositoryConfigDataBinding configBinding = new RepositoryConfigDataBinding(
                    new DefaultValidationEventHandler());
            config = unmarshalConfig(configFileName, configBinding);
            
        } catch (SAXException e) {
            throw new RepositorylException(e);
        } catch (JAXBException e) {
            throw new RepositorylException(e);
        }

        // The fUML execution environment requires a single instance
        // of these primitive types to be used for execution purposes.
        // Consequently they are "assembled" into M1 models. As a repository, 
        // we need to map these at bootstrap time as repository interface
        // instances for later lookup.
        org.modeldriven.fuml.repository.model.Classifier primitiveTypeClassifier = 
        	new org.modeldriven.fuml.repository.model.Classifier(
        	    Environment.getInstance().getInteger(), null);
        if (primitiveTypeClassifier.getXmiId() == null || primitiveTypeClassifier.getXmiId().length() == 0)
        	throw new IllegalStateException("expected XMI ID for fUML primitive type");
        elementIdToElementMap.put(primitiveTypeClassifier.getXmiId(), primitiveTypeClassifier);
        classifierNameToClassifierMap.put("Integer", primitiveTypeClassifier);

        primitiveTypeClassifier = 
        	new org.modeldriven.fuml.repository.model.Classifier(
        	    Environment.getInstance().getString(), null);
        if (primitiveTypeClassifier.getXmiId() == null || primitiveTypeClassifier.getXmiId().length() == 0)
        	throw new IllegalStateException("expected XMI ID for fUML primitive type");
        elementIdToElementMap.put(primitiveTypeClassifier.getXmiId(), primitiveTypeClassifier);
        classifierNameToClassifierMap.put("String", primitiveTypeClassifier);

        primitiveTypeClassifier = 
        	new org.modeldriven.fuml.repository.model.Classifier(
        	    Environment.getInstance().getBoolean(), null);
        if (primitiveTypeClassifier.getXmiId() == null || primitiveTypeClassifier.getXmiId().length() == 0)
        	throw new IllegalStateException("expected XMI ID for fUML primitive type");
        elementIdToElementMap.put(primitiveTypeClassifier.getXmiId(), primitiveTypeClassifier);
        classifierNameToClassifierMap.put("Boolean", primitiveTypeClassifier);

        primitiveTypeClassifier = 
            	new org.modeldriven.fuml.repository.model.Classifier(
            	    Environment.getInstance().getReal(), null);
            if (primitiveTypeClassifier.getXmiId() == null || primitiveTypeClassifier.getXmiId().length() == 0)
            	throw new IllegalStateException("expected XMI ID for fUML primitive type");
            elementIdToElementMap.put(primitiveTypeClassifier.getXmiId(), primitiveTypeClassifier);
            classifierNameToClassifierMap.put("Real", primitiveTypeClassifier);

        primitiveTypeClassifier = 
        	new org.modeldriven.fuml.repository.model.Classifier(
        	    Environment.getInstance().getUnlimitedNatural(), null);
        if (primitiveTypeClassifier.getXmiId() == null || primitiveTypeClassifier.getXmiId().length() == 0)
        	throw new IllegalStateException("expected XMI ID for fUML primitive type");
        elementIdToElementMap.put(primitiveTypeClassifier.getXmiId(), primitiveTypeClassifier);
        classifierNameToClassifierMap.put("UnlimitedNatural", primitiveTypeClassifier);
        
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
    
    public static InMemoryRepository getInstance() throws RepositorylException {
        if (instance == null)
            initializeInstance();
        return instance;
    }

    private static synchronized void initializeInstance() throws RepositorylException {
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

    public void loadClass(Class_ clss) {
    	construct(clss, clss.getName());
    }
    
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
        
        // map final unqualifed class (post merge) to the default namespace, ...
        // FIXME: do we map by every "supported" namespace
        // FIXME: do we map by the final package name post merge? This currently
        // causes errors below. 
        String namespaceQualifiedName = this.config.getDefaultUMLNamespaceURI() + "#" + className;
        this.qualifiedClassifierNameToClassifierMap.put(namespaceQualifiedName, clss);

        String artifactQualifiedName = clss.getArtifact().getNamespaceURI() + "#" + className;
        this.qualifiedClassifierNameToClassifierMap.put(artifactQualifiedName, clss);
        
        //String packageQualifiedName = implClass.getDelegate().package_.name + "." + className;
        //this.qualifiedClassifierNameToClassifierMap.put(packageQualifiedName, clss);
    }

    private void bootstrap() {

    	Map<Artifact, ModelAssembler> factoryMap = new HashMap<Artifact, ModelAssembler>();
        Iterator<Artifact> artifacts = config.getArtifact().iterator();
        while (artifacts.hasNext()) {
        	Artifact artifact = artifacts.next();
            Object[] args = { artifact, this, this };
            Class[] types = { Artifact.class, RepositoryMapping.class, org.modeldriven.fuml.repository.Repository.class };
            try {
            	ModelAssembler factory = (ModelAssembler)ReflectionUtils.instanceForName(
                		artifact.getFactoryClassName(), 
                        args, types);               
            	factoryMap.put(artifact, factory);
            }
            catch (Throwable e) {
            	log.error(e.getMessage(), e);
            	throw new RepositorylException(e);
            }
        }
    	
        log.info("performing package merge...");
        merge();
        
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

    private void merge() {
        List<PackageGraphNode> roots = findMergeRoots();

        Iterator<PackageGraphNode> rootIter = roots.iterator();
        while (rootIter.hasNext())
        {
        	PackageGraphNode root = rootIter.next();
        	
        	Package rootPackage = (Package)this.getElementById(root.getId());
        	if (log.isDebugEnabled())
        	    log.debug("merging package root:  " + rootPackage.getHref());
        	PackageGraphVisitor visitor = new PackageGraphVisitor() {

				public void visit(PackageGraphNode target, PackageGraphNode source) {
					if (source != null) // it's not a root
					{	
				        if (log.isDebugEnabled())
				            log.debug("visit: " + target.getPackage().getQualifiedName() + "<-" + source.getPackage().getQualifiedName());
					    mergePackage(source.getPackage().getDelegate(), target.getPackage().getDelegate());
					}
				}       		
        	};
        	root.accept(visitor);
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
    	throw new RepositorylException("could not get package, "
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
    		if (element instanceof fUML.Syntax.Classes.Kernel.Class_)
    		{
    			fUML.Syntax.Classes.Kernel.Class_ c = (fUML.Syntax.Classes.Kernel.Class_)element;
    			Class_ clss = new org.modeldriven.fuml.repository.model.Class_(c, p.getArtifact());
    			classifierNameToClassifierMap.put(c.name, clss);
    			classifierNameToPackageNameMap.put(c.name, p.getQualifiedName());
    		}
    		else if (element instanceof fUML.Syntax.Classes.Kernel.Classifier)
    		{
    			fUML.Syntax.Classes.Kernel.Classifier c = (fUML.Syntax.Classes.Kernel.Classifier)element;
    			Classifier classifier = new org.modeldriven.fuml.repository.model.Classifier(c, p.getArtifact());
    			classifierNameToClassifierMap.put(c.name, classifier);
    			classifierNameToPackageNameMap.put(c.name, p.getQualifiedName());
    		}
    		else if (element instanceof fUML.Syntax.Classes.Kernel.Package) {
    			Package pkg = new org.modeldriven.fuml.repository.model.Package((fUML.Syntax.Classes.Kernel.Package)element, p.getArtifact());
    			registerPackage(pkg); 			
    		}
		}
    }    
    
    private List<PackageGraphNode> findMergeRoots()
    {
    	Map<String, PackageGraphNode> sources = new HashMap<String, PackageGraphNode>();
    	Iterator<String> merges = packageIdToPackageMergeMap.keySet().iterator();	
    	while (merges.hasNext())
    	{   			
    		PackageGraphNode target = packageIdToPackageMergeMap.get(merges.next());
    		Package targetPackage = (Package)this.getElementById(target.getId());
    		target.setPackage(targetPackage); // HACKY but we must build the merge graph during mapping, and all package targets don't yet exist
    		if (target.getNodes() == null)
    			continue;
    		Iterator<PackageGraphNode> iter = target.getNodes().iterator();
    		while (iter.hasNext())
    		{	
    			PackageGraphNode source = iter.next();
        		Package sourcePackage = (Package)this.getElementById(source.getId());
        		source.setPackage(sourcePackage);
    			sources.put(source.getId(), source);
    		}    		
    	}
    	
    	List<PackageGraphNode> roots = new ArrayList<PackageGraphNode>();
    	merges = packageIdToPackageMergeMap.keySet().iterator();	
    	while (merges.hasNext())
    	{   			
    		PackageGraphNode target = packageIdToPackageMergeMap.get(merges.next());
    		if (sources.get(target.getId()) != null) // some source points to it, not a root
    			continue;    		
    		roots.add(target);
    	} 
    	
	    if (log.isDebugEnabled())
		    log.debug("found " + String.valueOf(roots.size()) + " merge-root and " 
		    		+ String.valueOf(sources.size()) + " source packages");
	    return roots;	
    }   
    
    @SuppressWarnings("unchecked")
    private RepositoryConfig unmarshalConfig(String configFileName, RepositoryConfigDataBinding binding) {
        try {
            InputStream stream = Element.class.getResourceAsStream(configFileName);
            if (stream == null)
            	stream = Element.class.getClassLoader().getResourceAsStream(configFileName);
            if (stream == null)
                throw new RepositorylException("cannot find resource '" + configFileName + "'");
            JAXBElement root = (JAXBElement) binding.validate(stream);

            RepositoryConfig result = (RepositoryConfig) root.getValue();
            return result;
        } catch (UnmarshalException e) {
            throw new RepositorylException(e);
        } catch (JAXBException e) {
            throw new RepositorylException(e);
        }
    }

    
    protected void collectAttributes(Class_ clss, List<Property> attributes) {
        fUML.Syntax.Classes.Kernel.Property[] props = new fUML.Syntax.Classes.Kernel.Property[clss.getDelegate().ownedAttribute.size()];
        clss.getDelegate().ownedAttribute.toArray(props);
        for (int i = 0; i < props.length; i++)
        {
        	fUML.Syntax.Classes.Kernel.Property fumlProperty = props[i];        	
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
            throw new RepositorylException("no classifier found for name, '" + name + "'");
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

    public String findJavaPackageNamePackageForClass(Classifier classifier) {
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
        return result;
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

}
