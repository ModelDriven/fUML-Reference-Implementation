package org.modeldriven.fuml.repository.model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.common.reflect.ReflectionUtils;
import org.modeldriven.fuml.config.FumlConfiguration;
import org.modeldriven.fuml.config.NamespaceDomain;
import org.modeldriven.fuml.repository.RepositoryArtifact;
import org.modeldriven.fuml.repository.RepositoryMapping;
import org.modeldriven.fuml.repository.RepositorylException;
import org.modeldriven.fuml.repository.ext.Extension;
import org.modeldriven.fuml.repository.ext.Stereotype;
import org.modeldriven.fuml.repository.merge.PackageGraphNode;

import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Syntax.Classes.Kernel.Association;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;
import fUML.Syntax.Classes.Kernel.Generalization;
import fUML.Syntax.Classes.Kernel.InstanceValue;
import fUML.Syntax.Classes.Kernel.LiteralBoolean;
import fUML.Syntax.Classes.Kernel.LiteralInteger;
import fUML.Syntax.Classes.Kernel.LiteralNull;
import fUML.Syntax.Classes.Kernel.LiteralString;
import fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Package;
import fUML.Syntax.Classes.Kernel.PackageableElement;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.Classes.Kernel.ValueSpecification;


public class InMemoryMapping implements RepositoryMapping 
{
    private Log log = LogFactory.getLog(InMemoryMapping.class);

    // generic maps
    protected Map<String, org.modeldriven.fuml.repository.Element> elementIdToElementMap = new HashMap<String, org.modeldriven.fuml.repository.Element>();
    protected Map<String, org.modeldriven.fuml.repository.NamedElement> elementNameToElementMap = new HashMap<String, org.modeldriven.fuml.repository.NamedElement>();
    protected Map<String, org.modeldriven.fuml.repository.NamedElement> qualifiedElementNameToElementMap = new HashMap<String, org.modeldriven.fuml.repository.NamedElement>();
    
    // unqualified classifier specific maps
    protected Map<String, org.modeldriven.fuml.repository.Classifier> classifierNameToClassifierMap = new HashMap<String, org.modeldriven.fuml.repository.Classifier>();
    protected Map<String, String> classifierNameToPackageNameMap = new HashMap<String, String>();

    // qualified classifier specific maps
    protected Map<String, org.modeldriven.fuml.repository.Classifier> qualifiedClassifierNameToClassifierMap = new HashMap<String, org.modeldriven.fuml.repository.Classifier>();    
    protected Map<String, String> qualifiedClassifierNameToPackageNameMap = new HashMap<String, String>();

    // package specific maps
    protected Map<String, org.modeldriven.fuml.repository.Package> qualifiedPackageNameToPackageMap = new HashMap<String, org.modeldriven.fuml.repository.Package>();
    
    // package merge maps
    protected Map<String, PackageGraphNode> packageIdToPackageMergeMap = new HashMap<String, PackageGraphNode>();
    protected Map<String, List<org.modeldriven.fuml.repository.Package>> artifactURIToPackagesMap = new HashMap<String, List<org.modeldriven.fuml.repository.Package>>();

    // Stereotype and extension maps
    protected Map<String, List<org.modeldriven.fuml.repository.Extension>> elementToExtensionListMap = new HashMap<String, List<org.modeldriven.fuml.repository.Extension>>();
    protected Map<String, List<org.modeldriven.fuml.repository.Stereotype>> elementToStereotypeListMap = new HashMap<String, List<org.modeldriven.fuml.repository.Stereotype>>();
    protected Map<Class<?>, List<org.modeldriven.fuml.repository.Stereotype>> classToStereotypeListMap = new HashMap<Class<?>, List<org.modeldriven.fuml.repository.Stereotype>>();
      
    
    
    protected InMemoryMapping() {}
    
	public org.modeldriven.fuml.repository.Element getElementById(String id) 
	{
		org.modeldriven.fuml.repository.Element result = elementIdToElementMap.get(id);
		if (result != null)
			return result;
		else
			throw new RepositorylException("could not get element from XMI ID, '" 
					+ id + "'");		
	}
	
	public org.modeldriven.fuml.repository.Element findElementById(String id) 
	{
		return elementIdToElementMap.get(id);
	}

	public org.modeldriven.fuml.repository.Element getElementByName(String name) 
	{
		org.modeldriven.fuml.repository.Element result = elementNameToElementMap.get(name);
		if (result != null)
			return result;
		else
			throw new RepositorylException("could not get element from name, '" 
					+ name + "'");		
	}
	
	public org.modeldriven.fuml.repository.Element findElementByName(String name) 
	{
		return elementNameToElementMap.get(name);
	}

	public org.modeldriven.fuml.repository.Element getElementByQualifiedName(String qualifiedName) 
	{
		org.modeldriven.fuml.repository.Element result = qualifiedElementNameToElementMap.get(qualifiedName);
		if (result != null)
			return result;
		else
			throw new RepositorylException("could not get element from qualified name, '" 
					+ qualifiedName + "'");		
	}
	
	public org.modeldriven.fuml.repository.Element findElementByQualifiedName(String qualifiedName) 
	{
		return qualifiedElementNameToElementMap.get(qualifiedName);
	}
	
	public int getElementCount(Class<? extends Element> clss) {
		
        int result = 0;
        Iterator<String> keys = elementNameToElementMap.keySet().iterator();
        while (keys.hasNext())
        {
        	org.modeldriven.fuml.repository.Element element = elementNameToElementMap.get(keys.next());
            if (clss.isAssignableFrom(element.getDelegate().getClass()))
                result++;
        }
        return result;
	}
	
	public String[] getElementNames(Class<? extends Element> clss) {
        List<String> list = new ArrayList<String>();
        Iterator<String> keys = elementNameToElementMap.keySet().iterator();
        while (keys.hasNext())
        {
        	org.modeldriven.fuml.repository.Element element = elementNameToElementMap.get(keys.next());
        	if (clss.isAssignableFrom(element.getDelegate().getClass()))
                list.add(((NamedElement)element.getDelegate()).name);
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;		
	}
	
	public org.modeldriven.fuml.repository.Classifier getClassifierByName(String name) {
		return classifierNameToClassifierMap.get(name);
	}
	
	public org.modeldriven.fuml.repository.Classifier getClassifierByQualifiedName(String qualifiedName) {
		return qualifiedClassifierNameToClassifierMap.get(qualifiedName);
	}
 
	public org.modeldriven.fuml.repository.Package getPackageByQualifiedName(String qualifiedName) {
		org.modeldriven.fuml.repository.Package result = qualifiedPackageNameToPackageMap.get(qualifiedName);
		if (result != null)
			return result;
		else
			throw new RepositorylException("could not get package from qualified name, '" 
					+ qualifiedName + "'");		
	}

	public void mapElementById(Element element, RepositoryArtifact artifact) {
		org.modeldriven.fuml.repository.Element elem = 
			    new org.modeldriven.fuml.repository.model.Element(element, artifact);
		elementIdToElementMap.put(element.getXmiId(), elem);
        String globalId = artifact.getURN() + "#" + element.getXmiId();
        if (elementIdToElementMap.get(globalId) != null)
        	throw new RepositorylException("found existing reference, '"
        			+ globalId + "'");
        elementIdToElementMap.put(globalId, elem);
	}
	
	public void mapElementByName(NamedElement element, RepositoryArtifact artifact) {
		if (element instanceof Class_) {
			org.modeldriven.fuml.repository.Class_ clss = 
				new org.modeldriven.fuml.repository.model.Class_((Class_)element, artifact);
			elementNameToElementMap.put(element.name, clss);				
		    classifierNameToClassifierMap.put(element.name, clss);
			if (element.qualifiedName != null) {
				qualifiedElementNameToElementMap.put(element.qualifiedName, clss);
			    qualifiedClassifierNameToClassifierMap.put(element.qualifiedName, clss);
			}			
		}
		else if (element instanceof Classifier) {
			org.modeldriven.fuml.repository.Classifier classifier = 
				new org.modeldriven.fuml.repository.model.Classifier((Classifier)element, artifact);
			elementNameToElementMap.put(element.name, classifier);				
		    classifierNameToClassifierMap.put(element.name, classifier);
			if (element.qualifiedName != null) {
				qualifiedElementNameToElementMap.put(element.qualifiedName, classifier);
			    qualifiedClassifierNameToClassifierMap.put(element.qualifiedName, classifier);
			}			
		}
		else {
			org.modeldriven.fuml.repository.NamedElement elem = 
				new org.modeldriven.fuml.repository.model.NamedElement(element, artifact);
			elementNameToElementMap.put(element.name, elem);				
			if (element.qualifiedName != null) 
				qualifiedElementNameToElementMap.put(element.qualifiedName, elem);
		}
	}
	
    public void mapPackage(Package p, String currentPackageName, RepositoryArtifact artifact) {
    	String qualifiedName = null;
        if (currentPackageName != null)
        	qualifiedName = currentPackageName + "." + p.name;
        //else
        //	qualifiedName = p.name;   	
    	
        if (log.isDebugEnabled())
            log.debug("mapping package, " + artifact.getURN() + "#" + p.name);


		org.modeldriven.fuml.repository.Package pkg = new org.modeldriven.fuml.repository.model.Package(p, artifact);
        
		if (qualifiedName != null) {
            if (qualifiedPackageNameToPackageMap.get(qualifiedName) != null)
        	    throw new RepositorylException("found existing package, '"
        			+ qualifiedName + ".");
            qualifiedPackageNameToPackageMap.put(qualifiedName, pkg);
		}
		
        List<org.modeldriven.fuml.repository.Package> artifactPackages = artifactURIToPackagesMap.get(artifact.getURN());
        if (artifactPackages == null)
        {
        	artifactPackages = new ArrayList<org.modeldriven.fuml.repository.Package>();
        	artifactURIToPackagesMap.put(artifact.getURN(), artifactPackages);
        }
        artifactPackages.add(pkg);
                
        if (elementIdToElementMap.get(p.getXmiId()) != null)
        	throw new RepositorylException("found existing package reference, '"
        			+ p.getXmiId() + ".");
        elementIdToElementMap.put(p.getXmiId(), pkg);
        String globalId = artifact.getURN() + "#" + p.getXmiId();
        if (elementIdToElementMap.get(globalId) != null)
        	throw new RepositorylException("found existing package reference, '"
        			+ globalId + "'");
        elementIdToElementMap.put(globalId, pkg);
    }

    public void mapPackageMerge(Package p, String sourcePackageXmiId) {
        if (log.isDebugEnabled())
            log.debug("mapping package merge, " + p.qualifiedName + "->" + sourcePackageXmiId);
        
        PackageGraphNode target = packageIdToPackageMergeMap.get(p.getXmiId());
        if (target == null)
        {	
        	target = new PackageGraphNode(p.getXmiId());
        	packageIdToPackageMergeMap.put(p.getXmiId(), target);
        }

        PackageGraphNode source = packageIdToPackageMergeMap.get(sourcePackageXmiId);
        if (source == null) {
        	source = new PackageGraphNode(sourcePackageXmiId);
        	packageIdToPackageMergeMap.put(sourcePackageXmiId, source);
        }
        
        if (target.contains(source))
	        	throw new RepositorylException("found existing merge node (" + sourcePackageXmiId 
	        			+ ") in target, " + p.qualifiedName + " ("
	        			+ p.getXmiId() + ")");
        target.addNode(source);
    }
    
    public void mapClass(Class_ clss, String currentPackageName, RepositoryArtifact artifact) {

		org.modeldriven.fuml.repository.Class_ repositoryClass = 
			new org.modeldriven.fuml.repository.model.Class_(clss, artifact);
		mapClassifier(clss, repositoryClass, currentPackageName,
				artifact);
    }

    public void mapClassifier(Classifier classifier, String currentPackageName, RepositoryArtifact artifact) {

		org.modeldriven.fuml.repository.Classifier repositoryClassifier = 
			new org.modeldriven.fuml.repository.model.Classifier(classifier, artifact);
		mapClassifier(classifier, repositoryClassifier, currentPackageName,
				artifact);
    }
    
    private void mapClassifier(Classifier classifier, 
    		org.modeldriven.fuml.repository.Classifier repositoryClassifier,
    		String currentPackageName, RepositoryArtifact artifact) {
    	
    	String packageQualifiedName = null;
    	if (currentPackageName != null)
    		packageQualifiedName = currentPackageName + "." + classifier.name;
    	else
    		packageQualifiedName = classifier.name; // is a qualified name as it may not have a package 
    	
        if (log.isDebugEnabled())
            log.debug("mapping class, " + artifact.getURN() + "#" + packageQualifiedName);
        
        if (qualifiedClassifierNameToClassifierMap.get(packageQualifiedName) != null)
        	throw new RepositorylException("found existing classifier by package qualified name, '"
        			+ packageQualifiedName + "' while mapping artifact, "
        			+ artifact.getURN());
        
        qualifiedClassifierNameToClassifierMap.put(packageQualifiedName, repositoryClassifier);
        qualifiedClassifierNameToPackageNameMap.put(packageQualifiedName,
                currentPackageName);
        qualifiedElementNameToElementMap.put(packageQualifiedName, repositoryClassifier);
                      
        if (artifact.getNamespaceURI() != null) {
	        String artifactNamespaceQualifiedName = artifact.getNamespaceURI()
	            + "#" + classifier.name;
	        if (qualifiedClassifierNameToClassifierMap.get(artifactNamespaceQualifiedName) != null)
	        	if (log.isDebugEnabled())
	        	    log.debug("found existing classifier by artifact qualified name, '"
	        			+ artifactNamespaceQualifiedName + "' while mapping artifact, "
	        			+ artifact.getURN());
	        qualifiedClassifierNameToClassifierMap.put(artifactNamespaceQualifiedName, repositoryClassifier);
        }
        else
    	    log.warn("missing artifact URI - could not map classifier '"
    			+ classifier.name + "' as externally referencable by artifact URI");
                
        if (elementIdToElementMap.get(classifier.getXmiId()) != null)
        	throw new RepositorylException("found existing classifier reference, '"
        			+ classifier.getXmiId() + "' while mapping artifact, "
        			+ artifact.getURN());
        elementIdToElementMap.put(classifier.getXmiId(), repositoryClassifier);
        String globalId = artifact.getURN() + "#" + classifier.getXmiId();
        if (elementIdToElementMap.get(globalId) != null)
        	throw new RepositorylException("found existing classifier reference, '"
        			+ globalId + "' while mapping artifact, "
        			+ artifact.getURN());
        elementIdToElementMap.put(globalId, repositoryClassifier);
        
        // so all classifiers can be externally referenced (i.e. external XMI references)using a "generic"
        // namespace assigned currently in the model artifact config entry
        if (artifact.getNamespaceURI() != null) {
	        String artifactNamespaceQualifiedName = artifact.getNamespaceURI()
	            + "#" + classifier.name;
	        if (elementIdToElementMap.get(artifactNamespaceQualifiedName) != null)
	        	if (log.isDebugEnabled())
	        	    log.debug("found existing classifier, '"
	        			+ artifactNamespaceQualifiedName + "' while mapping artifact, "
	        			+ artifact.getURN());
	        elementIdToElementMap.put(artifactNamespaceQualifiedName, repositoryClassifier);
        }
        else
        	log.warn("missing artifact URI - could not map element '"
        			+ classifier.name + "' as externally referencable by artifact URI");

    }
    
    public void mapStereotype(Stereotype stereotype, String currentPackageName, RepositoryArtifact artifact) {
    	String packageQualifiedName = null;
    	if (currentPackageName != null)
    		packageQualifiedName = currentPackageName + "." + stereotype.name;
    	else
    		packageQualifiedName = stereotype.name; // is a qualified name as it may not have a package 
    	
        if (log.isDebugEnabled())
            log.debug("mapping stereotype, " + artifact.getURN() + "#" + packageQualifiedName);
            
        // try and find the classifier for this stereotype to determine
        // how to map it
        if (stereotype.getXmiNamespace() != null) {
        	// use our actual Java class instance name to find our classifier if exists
	        String xmiNamespaceQualifiedClassifierName = stereotype.getXmiNamespace()
	            + "#" + stereotype.getClass().getSimpleName();
	        
	        org.modeldriven.fuml.repository.Stereotype stereotypeClassifier = (org.modeldriven.fuml.repository.Stereotype)qualifiedClassifierNameToClassifierMap.get(xmiNamespaceQualifiedClassifierName);
	        if (stereotypeClassifier != null) {
	        	boolean foundExtension = false;
	        	for (fUML.Syntax.Classes.Kernel.Property prop : stereotypeClassifier.getDelegate().ownedAttribute)
	        	{
	        		if (prop.association == null)
	        			continue;
	        		org.modeldriven.fuml.repository.Element assocElement = null; 
	        		if ((assocElement = elementIdToElementMap.get(prop.association.getXmiId())) == null)
	                	throw new RepositorylException("could not find association reference, '"
	                			+ prop.association.getXmiId() + "' while mapping artifact, "
	                			+ artifact.getURN());
	        		if (!(assocElement.getDelegate() instanceof Extension))
	        			continue;
	        		
	        		foundExtension = true;
	        		Extension extension = (Extension)assocElement.getDelegate();
	        		String targetClassAttributeName = prop.name;
	        		
	        		try {
	        			
	        			Element targetElement = null;
						try {
							targetElement = (Element)ReflectionUtils.invokePublicGetterOrField(
									stereotype, targetClassAttributeName);
						} catch (InvocationTargetException e) {
						}
	        			
						//Field targetClassField = stereotype.getClass().getField(targetClassAttributeName);
						
	        			//Element targetElement = (Element)targetClassField.get(stereotype);
				        if (targetElement == null)
				        	throw new RepositorylException("no target element found linked to Stereotype instance, '"
				        			+ stereotype.getXmiId() + "' by field '" 
				        			+ targetClassAttributeName
				        			+ "' while mapping artifact, "
				        			+ artifact.getURN());
				        
				        List<org.modeldriven.fuml.repository.Extension> list = elementToExtensionListMap.get(targetElement.getXmiId());
				        if (list == null) {
				        	list = new ArrayList<org.modeldriven.fuml.repository.Extension>();
				        	elementToExtensionListMap.put(targetElement.getXmiId(), list);
				        }
				        list.add(new org.modeldriven.fuml.repository.model.Extension(extension, artifact));	
				        
				        List<org.modeldriven.fuml.repository.Stereotype> elementStereotypeList = elementToStereotypeListMap.get(targetElement.getXmiId());
				        if (elementStereotypeList == null) {
				        	elementStereotypeList = new ArrayList<org.modeldriven.fuml.repository.Stereotype>();
				        	elementToStereotypeListMap.put(targetElement.getXmiId(), elementStereotypeList);
				        }
				        org.modeldriven.fuml.repository.model.Stereotype repoStereotype = new org.modeldriven.fuml.repository.model.Stereotype(stereotype, artifact);
				        elementStereotypeList.add(repoStereotype);	
				        
				        List<org.modeldriven.fuml.repository.Stereotype> classStereotypeList = classToStereotypeListMap.get(repoStereotype.getDelegate().getClass());
				        if (classStereotypeList == null) {
				        	classStereotypeList = new ArrayList<org.modeldriven.fuml.repository.Stereotype>();
				        	classToStereotypeListMap.put(repoStereotype.getDelegate().getClass(), classStereotypeList);
				        }
				        classStereotypeList.add(repoStereotype);
				        
	        		} catch (SecurityException e) {
						throw new RepositorylException(e);
					} catch (IllegalArgumentException e) {
						throw new RepositorylException(e);
					} catch (IllegalAccessException e) {
						throw new RepositorylException(e);
					}
	        		
	        	}
	        	if (!foundExtension)
	        		throw new RepositorylException("could not find extension property for Stereotype, '"
                			+ stereotypeClassifier.getXmiId() + "' while mapping artifact, "
                			+ artifact.getURN());
	        }
	        else {
	            if (qualifiedClassifierNameToClassifierMap.get(packageQualifiedName) != null)
	            	throw new RepositorylException("found existing classifier, '"
	            			+ packageQualifiedName + "' while mapping artifact, "
	            			+ artifact.getURN());
	            
	            stereotypeClassifier = new org.modeldriven.fuml.repository.model.Stereotype(stereotype, artifact);
	            qualifiedClassifierNameToClassifierMap.put(packageQualifiedName, stereotypeClassifier);
	            qualifiedClassifierNameToPackageNameMap.put(packageQualifiedName,
	                    currentPackageName);
	            qualifiedElementNameToElementMap.put(packageQualifiedName, stereotypeClassifier);
	                          
	            if (artifact.getNamespaceURI() != null) {
	    	        String artifactNamespaceQualifiedName = artifact.getNamespaceURI()
	    	            + "#" + stereotype.name;
	    	        if (qualifiedClassifierNameToClassifierMap.get(artifactNamespaceQualifiedName) != null)
	    	        	if (log.isDebugEnabled())
	    	        	    log.debug("found existing classifier, '"
	    	        			+ artifactNamespaceQualifiedName + "' while mapping artifact, "
	    	        			+ artifact.getURN());
	    	        qualifiedClassifierNameToClassifierMap.put(artifactNamespaceQualifiedName, stereotypeClassifier);
	            }
	            else
	        	    log.warn("missing artifact URI - could not map classifier '"
	        			+ stereotype.name + "' as externally referencable by artifact URI");
	        }
        }
               
        if (elementIdToElementMap.get(stereotype.getXmiId()) != null)
        	throw new RepositorylException("found existing classifier reference, '"
        			+ stereotype.getXmiId() + "' while mapping artifact, "
        			+ artifact.getURN());
        
        org.modeldriven.fuml.repository.Stereotype strtpe = new org.modeldriven.fuml.repository.model.Stereotype(stereotype, artifact);
        elementIdToElementMap.put(stereotype.getXmiId(), strtpe);
        String globalId = artifact.getURN() + "#" + stereotype.getXmiId();
        if (elementIdToElementMap.get(globalId) != null)
        	throw new RepositorylException("found existing classifier reference, '"
        			+ globalId + "' while mapping artifact, "
        			+ artifact.getURN());
        elementIdToElementMap.put(globalId, strtpe);
        
        // so all classifiers can be externally referenced (i.e. external XMI references)using a "generic"
        // namespace assigned currently in the model artifact config entry
        if (artifact.getNamespaceURI() != null) {
	        String artifactNamespaceQualifiedName = artifact.getNamespaceURI()
	            + "#" + stereotype.name;
	        if (elementIdToElementMap.get(artifactNamespaceQualifiedName) != null)
	        	if (log.isDebugEnabled())
	        	    log.debug("found existing classifier, '"
	        			+ artifactNamespaceQualifiedName + "' while mapping artifact, "
	        			+ artifact.getURN());
	        elementIdToElementMap.put(artifactNamespaceQualifiedName, strtpe);
        }
        else
        	log.warn("missing artifact URI - could not map element '"
        			+ stereotype.name + "' as externally referencable by artifact URI");

    }
    
    public void mapProperty(Classifier c, Property p, RepositoryArtifact artifact) {
        if (log.isDebugEnabled())
	    	if (c != null) // FIXME - why after assembly this could happen
	            log.debug("mapping property, " + artifact.getURN() + "#" + c.name + "." + p.name);
	    	else
	            log.debug("mapping property, " + artifact.getURN() + "#" + p.name);
        
    	if (elementIdToElementMap.get(p.getXmiId()) != null)
        	throw new RepositorylException("found existing property reference, '"
        			+ p.getXmiId() + ".");
        
        org.modeldriven.fuml.repository.Property property = new org.modeldriven.fuml.repository.model.Property(p, artifact);
        elementIdToElementMap.put(p.getXmiId(), property);
        String globalId = artifact.getURN() + "#" + p.getXmiId();
        if (elementIdToElementMap.get(globalId) != null)
        	throw new RepositorylException("found existing property reference, '"
        			+ globalId + ".");
        elementIdToElementMap.put(globalId, property);
    }
    
    public void mapPrimitiveType(PrimitiveType t, String currentPackageName, RepositoryArtifact artifact) {
    	
    	if (log.isDebugEnabled())
            log.debug("mapping type, " + artifact.getURN() + "#" + currentPackageName + "." + t.getClass().getSimpleName());
        
        org.modeldriven.fuml.repository.Classifier classifier = new org.modeldriven.fuml.repository.model.Classifier(t, artifact);
        
        classifierNameToClassifierMap.put(t.name, classifier);
        qualifiedClassifierNameToClassifierMap.put(currentPackageName + "." + t.name, classifier);
        classifierNameToPackageNameMap.put(t.name, currentPackageName);
        qualifiedClassifierNameToPackageNameMap.put(currentPackageName + "." + t.name,
                currentPackageName);
        if (elementIdToElementMap.get(t.getXmiId()) != null)
        	throw new RepositorylException("found existing primitive type, '"
        			+ t.getXmiId() + ".");
        elementIdToElementMap.put(t.getXmiId(), classifier);
        String globalId = artifact.getURN() + "#" + t.getXmiId();
        if (elementIdToElementMap.get(globalId) != null)
        	throw new RepositorylException("found existing primitive type, '"
        			+ globalId + ".");
        elementIdToElementMap.put(globalId, classifier);
        
        // FIXME: Path map variables allow for portability of URIs. The actual location 
        // indicated by a URI depends on the run-time binding of the path variable. Thus, different 
        // environments can work with the same resource URIs even though the 
        // resources are stored in different physical locations.
        elementIdToElementMap.put("pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml" 
                + "#" + t.name, classifier);  
        
        String[] uris = FumlConfiguration.getInstance().getSupportedNamespaceURIsForDomain(NamespaceDomain.UML);
        for (String uri : uris)
            elementIdToElementMap.put(uri + "/" + "uml.xml" 
                + "#" + t.name, classifier);        
    }
    
    public void mapDataType(DataType t, String currentPackageName, RepositoryArtifact artifact) {
    	
    	if (log.isDebugEnabled())
            log.debug("mapping datatype, " + artifact.getURN() + "#" + currentPackageName + "." + t.getClass().getSimpleName());
        
        org.modeldriven.fuml.repository.Classifier classifier = new org.modeldriven.fuml.repository.model.Classifier(t, artifact);
        if (t.name != null && currentPackageName != null) {
            qualifiedClassifierNameToClassifierMap.put(currentPackageName + "." + t.name, classifier);
            classifierNameToPackageNameMap.put(t.name, currentPackageName);
            qualifiedClassifierNameToPackageNameMap.put(currentPackageName + "." + t.name,
                currentPackageName);
        }
        
        
        if (elementIdToElementMap.get(t.getXmiId()) != null)
        	throw new RepositorylException("found existing datatype, '"
        			+ t.getXmiId() + ".");
        elementIdToElementMap.put(t.getXmiId(), classifier);
        String globalId = artifact.getURN() + "#" + t.getXmiId();
        if (elementIdToElementMap.get(globalId) != null)
        	throw new RepositorylException("found existing datatype, '"
        			+ globalId + ".");
        elementIdToElementMap.put(globalId, classifier); 
        if (t.name != null) {
            if (artifact.getNamespaceURI() != null) {
    	        String artifactNamespaceQualifiedName = artifact.getNamespaceURI()
    	            + "#" + t.name;
    	        if (qualifiedClassifierNameToClassifierMap.get(artifactNamespaceQualifiedName) != null)
    	        	if (log.isDebugEnabled())
    	        	    log.debug("found existing datatype by artifact qualified name, '"
    	        			+ artifactNamespaceQualifiedName + "' while mapping artifact, "
    	        			+ artifact.getURN());
    	        qualifiedClassifierNameToClassifierMap.put(artifactNamespaceQualifiedName, classifier);
            }
            else
        	    log.warn("missing artifact URI - could not map datatype '"
        			+ t.name + "' as externally referencable by artifact URI");
        }
    }

    public void mapEnumeration(Enumeration e, String currentPackageName, RepositoryArtifact artifact) {
        if (log.isDebugEnabled())
            log.debug("mapping enumeration, " + currentPackageName + "." + e.name);
        org.modeldriven.fuml.repository.Classifier classifier = new org.modeldriven.fuml.repository.model.Enumeration(e, artifact);
        classifierNameToClassifierMap.put(e.name, classifier); // FIXME: why are we doing this flat mapping??
        qualifiedClassifierNameToClassifierMap.put(currentPackageName + "." + e.name, classifier);
        classifierNameToPackageNameMap.put(e.name, currentPackageName);
        qualifiedClassifierNameToPackageNameMap.put(currentPackageName + "." + e.name,
                currentPackageName);
        if (elementIdToElementMap.get(e.getXmiId()) != null)
        	throw new RepositorylException("found existing enumeration, '"
        			+ e.getXmiId() + ".");
        elementIdToElementMap.put(e.getXmiId(), classifier);
    }

    public void mapEnumerationLiteral(EnumerationLiteral literal, String currentPackageName, RepositoryArtifact artifact) {
        if (log.isDebugEnabled())
            log.debug("mapping enumeration literal, " + currentPackageName + "." + literal.name);
        if (elementIdToElementMap.get(literal.getXmiId()) != null)
        	throw new RepositorylException("found existing enumeration literal, '"
        			+ literal.getXmiId() + ".");
        org.modeldriven.fuml.repository.NamedElement namedElement = new org.modeldriven.fuml.repository.model.EnumerationLiteral(literal, artifact);
        elementIdToElementMap.put(literal.getXmiId(), namedElement);
    }   
    
	public void mapAssociation(Association assoc, String currentPackageName, RepositoryArtifact artifact) {
        if (log.isDebugEnabled())
            log.debug("mapping association, " + currentPackageName + "." + assoc.name);
        if (elementIdToElementMap.get(assoc.getXmiId()) != null)
        	throw new RepositorylException("found existing association, '"
        			+ assoc.getXmiId() + ".");
        org.modeldriven.fuml.repository.Classifier classifier = new org.modeldriven.fuml.repository.model.Classifier(assoc, artifact);
        elementIdToElementMap.put(assoc.getXmiId(), classifier);		
	}

    public void mergePackage(Package target, Package source) {
    	if (log.isDebugEnabled())
		    log.debug("merging package " + target.getHref() + " with "
            		+ source.getHref());
    	Iterator<PackageableElement> sourceIter = source.packagedElement.iterator();
    	while (sourceIter.hasNext())
    	{
    		PackageableElement sourceElement = sourceIter.next();
            if (sourceElement instanceof Class_)
            {	
	    		Class_ sourceClass = (Class_)sourceElement;
	    		Class_ targetClass = findClass(target, sourceClass.name);
	    		if (targetClass != null)
	    		{	
	        		if (log.isDebugEnabled())
	                    log.debug("merging class (" + target.qualifiedName + ") " + targetClass.name + " with "
	                    		+ "(" + source.qualifiedName + ") " + sourceClass.name);
	    			mergeClass(targetClass, sourceClass);
	    		}	
	    		else
	    		{	
	        		if (log.isDebugEnabled())
	                    log.debug("adding class (" + source.qualifiedName + ") " + sourceClass.name
	                    		+ " to package " + target.qualifiedName);
	    			target.packagedElement.add(sourceClass);
	    		}
            }
    	}
    }
 
    private Class_ findClass(Package p, String name)
    {
    	Iterator<PackageableElement> targetIter = p.packagedElement.iterator();
    	while (targetIter.hasNext())
    	{
    		PackageableElement element = targetIter.next();
    		if (element instanceof Class_)
    		{	
    		    Class_ c = (Class_)element;
    		    if (name.equals(c.name))
    			    return c;
    		}
    	}
    	return null;
    }

    @SuppressWarnings("unchecked")
	private Property findProperty(Class_ c, String name)
    {    	
	    Iterator<fUML.Syntax.Classes.Kernel.Property> iter = c.ownedAttribute.iterator();
	    while (iter.hasNext()) {
	        Property p = (Property)iter.next();
	        if (p.name.equals(name))
	            return p;
	    }
	    return null;
    }
    
    public void mergeClass(Class_ target, Class_ source) {
        
        mergeProperties(target, source);
        mergeGeneralizations(target, source);
        target.general.clear(); // HACK
        //mergeGenerals(target, source);
                
        target.isAbstract = source.isAbstract;
        target.setIsAbstract(target.isAbstract);
    }
    
    public void mergeProperties(Class_ target, Class_ source) {
        
        // merge existing properties and add new properties 
        Iterator<fUML.Syntax.Classes.Kernel.Property> sourceIter = source.ownedAttribute.iterator();
        while (sourceIter.hasNext()) {
            Property sourceProp = (Property)sourceIter.next();
            Property targetProp = findProperty(target, sourceProp.name);
            if (targetProp != null)
            {
        		if (log.isDebugEnabled())
                    log.debug("merging property " + target.qualifiedName + "." + targetProp.name + " with "
                    	+ source.qualifiedName + "." + sourceProp.name);
            	mergeProperty(targetProp, sourceProp);
            }
            else
            {
        		if (log.isDebugEnabled())
                    log.debug("adding property " + source.qualifiedName + "." + sourceProp.name + " to "
                    	+ target.name);
            	target.ownedAttribute.add(sourceProp);
            }
        }
        
        // remove obsolete properties
/*        
        List<Property> toRemove = new ArrayList<Property>();
        Iterator<Property> targetIter = target.ownedAttribute.iterator();
        while (targetIter.hasNext()) {
            Property targetProp = targetIter.next();
            sourceIter = source.ownedAttribute.iterator();
            boolean found = false;
            while (sourceIter.hasNext()) {
                Property sourceProp = sourceIter.next();
            
                if (!targetProp.name.equals(sourceProp.name))
                    continue;
                found = true;
            }
            if (!found) // target no longer in source
                toRemove.add(targetProp);
        }
        
        Iterator<Property> toRemoveTargetIter = toRemove.iterator();
        while (toRemoveTargetIter.hasNext())
        {
            Property targetProp = toRemoveTargetIter.next();
            if (!target.ownedAttribute.remove(targetProp))
                log.warn("could not remove property " 
                        + target.name + "." + targetProp.name);
        }
*/
    }

    private void mergeGeneralizations(Class_ target, Class_ source) {
        Iterator<Generalization> sourceIter = source.generalization.iterator();
        while (sourceIter.hasNext()) {
            Generalization sourceGeneralization = sourceIter.next();
            
            boolean found = false;
            Iterator<Generalization> targetIter = target.generalization.iterator();
            while (targetIter.hasNext()) {
                Generalization targetGeneralization = targetIter.next();
                if (!targetGeneralization.general.name.equals(sourceGeneralization.general.name))
                    continue;
                targetGeneralization.setXmiId(sourceGeneralization.getXmiId());
                found = true;
                break;
            }
            if (!found)
                target.generalization.add(sourceGeneralization);
        }
    }

    private void mergeGenerals(Class_ target, Class_ source) {
        Iterator<Classifier> sourceIter = source.general.iterator();
        while (sourceIter.hasNext()) {
            Classifier sourceGeneral = sourceIter.next();

            boolean found = false;
            Iterator<Classifier> targetIter = target.general.iterator();
            while (targetIter.hasNext()) {
                Classifier targetGeneral = targetIter.next();
                if (!targetGeneral.name.equals(sourceGeneral.name))
                    continue;
                found = true;
                break;
            }
            if (!found)
                target.general.add(sourceGeneral);
        }
    }
    
    protected void mergeProperty(Property target, Property source) {
        
        // type
        target.typedElement = source.typedElement;
        
        // merge defaults
        ValueSpecification sourceDefault = source.defaultValue;
        if (sourceDefault != null) {
            ValueSpecification targetDefault = target.defaultValue;
            if (targetDefault == null) {
                target.defaultValue = source.defaultValue;
            } else
                mergeValueSpecification(targetDefault, sourceDefault);
        }
        
        // merge upper/lower value (constraints) 
        ValueSpecification sourceLower = source.multiplicityElement.lowerValue;
        if (sourceLower != null)
        {
            ValueSpecification targetLower = target.multiplicityElement.lowerValue;
            if (targetLower == null)
                target.setLowerValue(sourceLower);
            else
                mergeValueSpecification(targetLower, sourceLower);           
        }
        ValueSpecification sourceUpper = source.multiplicityElement.lowerValue;
        if (sourceUpper != null)
        {
            ValueSpecification targetUpper = target.multiplicityElement.lowerValue;
            if (targetUpper == null)
                target.setUpperValue(sourceUpper);
            else
                mergeValueSpecification(targetUpper, sourceUpper);           
        }
    }

    private void mergeValueSpecification(ValueSpecification target, ValueSpecification source) {
        // NOTE; this is scary.
        if (!target.getClass().equals(source.getClass()))
            if (log.isDebugEnabled())
                log.warn("merging unequal value specification classes, "
                        + source.getClass().getSimpleName() + " to "
                        + target.getClass().getSimpleName());
        String sourceValue = getValue(source);
        if (sourceValue != null && sourceValue.trim().length() > 0)
            setValue(target, sourceValue);
    }

    private String getValue(ValueSpecification valueSpec) {
        if (LiteralString.class.isAssignableFrom(valueSpec.getClass()))
            return ((LiteralString)valueSpec).value;		
        else if (LiteralInteger.class.isAssignableFrom(valueSpec.getClass()))
            return String.valueOf(((LiteralInteger)valueSpec).value);		
        else if (LiteralBoolean.class.isAssignableFrom(valueSpec.getClass()))
            return String.valueOf(((LiteralBoolean)valueSpec).value);		
        else if (LiteralNull.class.isAssignableFrom(valueSpec.getClass()))
            return null; //((LiteralNull)valueSpec).;		
        else if (LiteralUnlimitedNatural.class.isAssignableFrom(valueSpec.getClass())) {
        	int naturalValue = ((LiteralUnlimitedNatural)valueSpec).value.naturalValue;
            return naturalValue == -1? "*": String.valueOf(naturalValue);		
        } else if (InstanceValue.class.isAssignableFrom(valueSpec.getClass())) {
            return valueSpec.name;
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
        	un.naturalValue = value.equals("*")? -1: Integer.parseInt(value);
        	((LiteralUnlimitedNatural)valueSpec).value = un;
        } else if (InstanceValue.class.isAssignableFrom(valueSpec.getClass())) {
            valueSpec.setName(value);
        } else {
            // ((OpaqueExpression)valueSpec).setBody(value);
            throw new IllegalArgumentException("expected literal or instance value");
        }
    }
    
    
}
