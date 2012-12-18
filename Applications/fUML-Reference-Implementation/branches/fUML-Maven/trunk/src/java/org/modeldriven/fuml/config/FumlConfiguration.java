/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License 
 * version 3.0 (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */
package org.modeldriven.fuml.config;

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
import org.modeldriven.fuml.FumlException;
import org.modeldriven.fuml.FumlSystemProperty;
import org.modeldriven.fuml.bind.DefaultValidationEventHandler;
import org.xml.sax.SAXException;

import org.modeldriven.fuml.repository.Classifier;
import org.modeldriven.fuml.repository.Property;

public class FumlConfiguration {

    private static Log log = LogFactory.getLog(FumlConfiguration.class);
    private static FumlConfiguration instance = null;
    private static String defaultConfigFileName = "DefaultFumlConfig.xml";  
    private Map<String, BehaviorExecutionMapping> executions = 
        new HashMap<String, BehaviorExecutionMapping>();
    /** maps import exemptions to XML element local names */
    private Map<String, ImportExemption> importExemptions = new HashMap<String, ImportExemption>();
    private Map<String, ImportAdapter> importAdapters = new HashMap<String, ImportAdapter>();
    
    /** maps validation exemptions to classifier names */
    private Map<String, List<ValidationExemption>> validationExemptions = new HashMap<String, List<ValidationExemption>>();
    
    private String activeConfigFileName;
    
    private Configuration config;
        
    private FumlConfiguration()
    {
        log.info("initializing...");
        try {
            FumlConfigDataBinding configBinding = new FumlConfigDataBinding(
	        		new DefaultValidationEventHandler());
	        
            activeConfigFileName = System.getProperty(FumlSystemProperty.CONFIG.getProperty(), 
            		defaultConfigFileName);
                        
            log.info("loading configuration, " + activeConfigFileName);
            config = unmarshalConfig(activeConfigFileName, configBinding);
	        
            if (config.getMappingConfiguration().getBehaviorExecutionMapping() != null)
                for (BehaviorExecutionMapping mapping : config.getMappingConfiguration().getBehaviorExecutionMapping())
                {
                    executions.put(mapping.getClassName(), mapping);
                    Class.forName(mapping.getExecutionClassName()); // execution classes must exist on the classpath
                }

            if (config.getImportConfiguration().getExemption() != null)
                for (ImportExemption e : config.getImportConfiguration().getExemption())
                	importExemptions.put(e.getLocalName(), e);
        
            if (config.getImportConfiguration().getAdapter() != null)  
                for (ImportAdapter adapter : config.getImportConfiguration().getAdapter())
                    importAdapters.put(adapter.getClassName(), adapter);
            
            if (config.getValidationConfiguration().getExemption() != null)
                for (ValidationExemption exemption : config.getValidationConfiguration().getExemption())
                {
                	List<ValidationExemption> list = validationExemptions.get(exemption.getClassifierName());
                    if (list == null) {
                    	list = new ArrayList<ValidationExemption>();
                    	validationExemptions.put(exemption.getClassifierName(), list);
                    }                    
                    list.add(exemption);
                }
            
        }
        catch (SAXException e) {
            throw new FumlException(e);
        }
        catch (JAXBException e) {
            throw new FumlException(e);
        }
        catch (ClassNotFoundException e) {
            throw new FumlException(e);
        }
    }
    
    @SuppressWarnings("unchecked")
    private Configuration unmarshalConfig(String configFileName, FumlConfigDataBinding binding)
    {
    	try {
	        InputStream stream = FumlConfiguration.class.getClassLoader().getResourceAsStream(configFileName);
	        if (stream == null)
                stream = FumlConfiguration.class.getResourceAsStream(configFileName);
	        if (stream == null)
	            throw new FumlException("cannot find resource '" + configFileName + "' on current classpath");
	        
	        JAXBElement root = (JAXBElement)binding.validate(stream);
	        
	        Configuration result = (Configuration)root.getValue();
            return result;
    	}
        catch (UnmarshalException e) {
            throw new FumlException(e);
        }
        catch (JAXBException e) {
            throw new FumlException(e);
        }
    }
    
    public static FumlConfiguration getInstance()
        throws FumlException
    {
        if (instance == null)
            initializeInstance();
        return instance;
    }
    
    private static synchronized void initializeInstance()
        throws FumlException
    {
        if (instance == null)
            instance = new FumlConfiguration();
    }

    public Configuration getConfig() {
        return config;
    } 
    
    public String[] getSupportedNamespaceURIsForDomain(NamespaceDomain domain)
    {
        List<String> list = new ArrayList<String>();
    
        Iterator<SupportedNamespace> namespaces = 
            getConfig().getImportConfiguration().getSupportedNamespace().iterator();  
        while (namespaces.hasNext())
        {
            SupportedNamespace namespace = namespaces.next();
            if (namespace.getDomain().value().equals(domain.value()))
                list.add(namespace.getUri());
        }
        
        String[] results = new String[list.size()];
        list.toArray(results);
        return results;
    }

    public NamespaceDomain getNamespaceDomain(String namespaceUri)
    {
    	NamespaceDomain result = findNamespaceDomain(namespaceUri);
    	if (result == null)
    		throw new FumlException("no namespace domain found for URI '" 
                    + namespaceUri + "' - please add this URI to the set of supported namespaces for the appropriate domain"); 
    	return result;
    }
    
    public NamespaceDomain findNamespaceDomain(String namespaceUri)
    {
        Iterator<SupportedNamespace> namespaces = 
            getConfig().getImportConfiguration().getSupportedNamespace().iterator();  
        while (namespaces.hasNext())
        {
            SupportedNamespace namespace = namespaces.next();
            if (namespace.getUri().equals(namespaceUri))
                return namespace.getDomain();
        }
        return null;
    }
    
    public boolean hasReferenceMapping(Classifier classifier, Property property)
    {
        Iterator<ReferenceMapping> mappings = 
            getConfig().getMappingConfiguration().getReferenceMapping().iterator();  
        while (mappings.hasNext())
        {
            ReferenceMapping mapping = mappings.next();
            if (mapping.getClassName().equals(classifier.getName()) &&
                    mapping.getPropertyName().equals(property.getName()))
                return true;
        }
        return false;
    }
    
    public ReferenceMappingType getReferenceMappingType(Classifier classifier, Property property)
    {
        Iterator<ReferenceMapping> mappings = 
            getConfig().getMappingConfiguration().getReferenceMapping().iterator();  
        while (mappings.hasNext())
        {
            ReferenceMapping mapping = mappings.next();
            if (mapping.getClassName().equals(classifier.getName()) &&
                    mapping.getPropertyName().equals(property.getName()))
                return mapping.getType();
        }
        throw new FumlException("no mapping found for, " 
                + classifier.getName() + "." + property.getName());
    }
    
    public String findExecutionClassName(String name)
    {
        BehaviorExecutionMapping mapping = this.executions.get(name);
        if (mapping != null)
            return mapping.getExecutionClassName();
        return null;
    }

    public String getActiveConfigFileName() {
        return activeConfigFileName;
    }
    
    /**
     * Return the import exception for the goven element local name, if exists
     * @param localName the element local name
     * @return the import exception or null if not exists
     */
    public ImportExemption findImportExemptionByElement(String localName) {
        return this.importExemptions.get(localName);
    }
    
    public ImportAdapter findImportAdapter(String name) {
        return this.importAdapters.get(name);
    }
 
    /**
     * Returns the list of validation exemptions for the given classifier name.
     * @param classifierName the unqualified classifier name
     * @return the validation exemptions or 
     */
    public List<ValidationExemption> findValidationExemptionByClassifierName(String classifierName) {
        return this.validationExemptions.get(classifierName);
    }
    
    public ValidationExemption findValidationExemptionByProperty(ValidationExemptionType type, 
			Classifier classifier, String propertyName, String namespaceURI, NamespaceDomain domain) {
        ValidationExemption result = null;
        if (domain != null) {
	    	List<ValidationExemption> exemptions = findValidationExemptionByClassifierName(classifier.getName());
	    	if (exemptions != null)
	    		for (ValidationExemption exemption : exemptions)
	    			if (exemption.getPropertyName().equals(propertyName) &&
	    				exemption.getDomain().ordinal() == domain.ordinal() &&
	    				exemption.getType().ordinal() == type.ordinal()) {
	    				result = exemption;
	    				break;
	    			}
        }
        else
            if (log.isDebugEnabled())
        	    log.debug("could not lookup validation exemption for namespace URI '"
        	    		+ "namespaceURI");
    	return result;
	}

    public ValidationExemption findValidationExemptionByReference(ValidationExemptionType type, 
			Classifier classifier, String reference, String namespaceURI, NamespaceDomain domain) {
        ValidationExemption result = null;
        if (domain != null) {
	    	List<ValidationExemption> exemptions = findValidationExemptionByClassifierName(classifier.getName());
	    	if (exemptions != null)
	    		for (ValidationExemption exemption : exemptions)
	    			if (exemption.getHref().equals(reference) &&
	    				exemption.getDomain().ordinal() == domain.ordinal() &&
	    				exemption.getType().ordinal() == type.ordinal()) {
	    				result = exemption;
	    				break;
	    			}
        }
        else
            if (log.isDebugEnabled())
        	    log.debug("could not lookup validation exemption for namespace URI '"
        	    		+ "namespaceURI");
    	return result;
	}	
    
}
