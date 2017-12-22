package org.modeldriven.fuml.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.FumlObject;
import org.modeldriven.fuml.assembly.ElementAssemblerEvent;
import org.modeldriven.fuml.assembly.ElementAssemblerEventListener;
import org.modeldriven.fuml.assembly.ElementAssemblerResultsEvent;
import org.modeldriven.fuml.repository.Repository;
import org.modeldriven.fuml.repository.RepositoryArtifact;
import org.modeldriven.fuml.repository.ext.Stereotype;
import org.modeldriven.fuml.xmi.stream.StreamReader;
import org.modeldriven.fuml.xmi.validation.ErrorSeverity;
import org.modeldriven.fuml.xmi.validation.ValidationErrorEvent;
import org.modeldriven.fuml.xmi.validation.ValidationEvent;
import org.modeldriven.fuml.xmi.validation.ValidationEventListener;

import fuml.syntax.activities.Activity;
import fuml.syntax.classification.Property;
import fuml.syntax.commonbehavior.OpaqueBehavior;
import fuml.syntax.commonstructure.Element;
import fuml.syntax.commonstructure.NamedElement;
import fuml.syntax.packages.Package;
import fuml.syntax.simpleclassifiers.DataType;
import fuml.syntax.simpleclassifiers.Enumeration;
import fuml.syntax.simpleclassifiers.EnumerationLiteral;
import fuml.syntax.simpleclassifiers.PrimitiveType;
import fuml.syntax.structuredclassifiers.Class_;

public class ArtifactLoader 
    implements ValidationEventListener, ElementAssemblerEventListener, 
        ElementReaderEventListener 
{
	
    private static Log log = LogFactory.getLog(ArtifactLoader.class);
    
    protected List<Class_> classList = new ArrayList<Class_>();
    protected RepositoryArtifact artifact;
    protected BasicElementReader modelElementReader;
	protected boolean validateExternalReferences = true;
	protected boolean assembleExternalReferences = true;
    
	public ArtifactLoader() {
		this.modelElementReader = new BasicElementReader();
	}
	
	public ArtifactLoader(BasicElementReader modelElementReader) {
		this.modelElementReader = modelElementReader;
	}
        
    public boolean isValidateExternalReferences() {
		return this.modelElementReader.isValidateExternalReferences();
	}

	public void setValidateExternalReferences(boolean validateExternalReferences) {
		this.modelElementReader.setValidateExternalReferences(validateExternalReferences);
	}

	public boolean isAssembleExternalReferences() {
		return this.modelElementReader.isAssembleExternalReferences();
	}

	public void setAssembleExternalReferences(boolean assembleExternalReferences) {
		this.modelElementReader.setAssembleExternalReferences(assembleExternalReferences);
	}
    public void read(FileArtifact artifact) {
    	    	
    	log.debug("reading " + artifact.getURN());
    	this.artifact = artifact;
    	
        StreamReader reader = new StreamReader();

        reader.addStreamNodeListener(modelElementReader);
        modelElementReader.addValidationEventListener(this);
        modelElementReader.addElementAssemblerEventListener(this);    	        
        modelElementReader.addElementReaderEventListener(this);
        
		try {
			reader.read(new FileInputStream(artifact.getFile()));
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		}
    }

    public void read(ResourceArtifact artifact) {
    	
    	this.artifact = artifact;
    	log.debug("reading " + artifact.getURN());

    	StreamReader reader = new StreamReader();

        reader.addStreamNodeListener(modelElementReader);
        modelElementReader.addValidationEventListener(this);
        modelElementReader.addElementAssemblerEventListener(this);    	        
        modelElementReader.addElementReaderEventListener(this);
        
		reader.read(artifact.getInputStream());
    }
    
	public void validationCompleted(ValidationEvent event) {
        if (log.isDebugEnabled())
            log.debug("validation completed");
	}

	public void validationError(ValidationErrorEvent event) {
		
        if (event.getError().getSeverity().ordinal() == ErrorSeverity.FATAL.ordinal())
            log.error("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
        else if (event.getError().getSeverity().ordinal() == ErrorSeverity.WARN.ordinal())
            log.warn("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
        else if (event.getError().getSeverity().ordinal() == ErrorSeverity.INFO.ordinal())
            log.info("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
        else
            log.error("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
	}

	public void validationStarted(ValidationEvent event) {
        if (log.isDebugEnabled())
            log.debug("validation started");
	}

	public void elementAssembled(ElementAssemblerEvent event) {
		try {
            if (log.isDebugEnabled())
                log.debug("element assembled: ("
                    	+ event.getSource().getClass().getName() 
                    	+ ") "+ event.getSource().getXmiId());
		}
		catch (java.lang.NullPointerException e) {
			if (event == null)
				log.error("found null event");
			if (event.getSource() == null)
				log.error("found null event source");
			log.error(e);
		}
	}

	public void elementGraphAssembled(ElementAssemblerResultsEvent event) {
        if (log.isDebugEnabled())
            log.debug("element graph assembled");

        Iterator<String> ids = event.getSource().getResultsXmiIds().iterator();
        while (ids.hasNext())
        {
            String id = ids.next();
            FumlObject fumlObject = event.getSource().lookupResult(id);
            if (fumlObject == null) {
            	log.warn("could not lookup element from id '"
            			+ id + "'");
            	continue;
            }
            
            if (fumlObject instanceof NamedElement)
                if (log.isDebugEnabled())
                    log.debug("loading: ("
                    	+ fumlObject.getClass().getName() + ") "	
                    	+ ((NamedElement)fumlObject).qualifiedName
                        + " (" + id + ")");
            else
                if (log.isDebugEnabled())
                    log.debug("loading: ("
                    	+ fumlObject.getClass().getName() + ") "
                    	+ "(" + id + ")");
            
            if (fumlObject instanceof Activity) {
                NamedElement namedElement = (NamedElement)fumlObject;
            	Repository.INSTANCE.getMapping().mapElementByName(namedElement, this.artifact);
        	    Repository.INSTANCE.getMapping().mapElementById(namedElement, this.artifact);
            }
            else if (fumlObject instanceof Class_ && !(fumlObject instanceof OpaqueBehavior)) {
            	Class_ clss = (Class_)fumlObject;
            	classList.add(clss);
            	if (!(clss instanceof Stereotype)) {
                	if (clss.package_ != null) {
                	    Repository.INSTANCE.getMapping().mapClass(clss, 
                	    		getQualifiedPackageName(clss.package_), artifact);
                	}
                	else
                		Repository.INSTANCE.getMapping().mapClass(clss, 
                    			null, artifact);
            	}
            	else {
            		Stereotype stereotype = (Stereotype)clss;
                	if (stereotype.package_ != null)
                	    Repository.INSTANCE.getMapping().mapStereotype(stereotype, 
                	    		getQualifiedPackageName(stereotype.package_), artifact);
                	else
                		Repository.INSTANCE.getMapping().mapStereotype(stereotype, 
                    			null, artifact);
            	}            		
            }  
            else if (fumlObject instanceof Enumeration) {
            	Enumeration enumeration = (Enumeration)fumlObject;        			
               	if (enumeration.package_ != null)
            	    Repository.INSTANCE.getMapping().mapEnumerationExternal(enumeration, 
            	    		getQualifiedPackageName(enumeration.package_), artifact);
            	else
            		Repository.INSTANCE.getMapping().mapEnumerationExternal(enumeration, 
                			null, artifact);
            }
            else if (fumlObject instanceof DataType) {
        		if (!(fumlObject instanceof PrimitiveType)) {
        			// datatypes
        			DataType datatype = (DataType)fumlObject;        			
                   	if (datatype.package_ != null)
	            	    Repository.INSTANCE.getMapping().mapDataType(datatype, 
	            	    		getQualifiedPackageName(datatype.package_), artifact);
	            	else
	            		Repository.INSTANCE.getMapping().mapDataType(datatype, 
	                			null, artifact);
        		}
            }
            else if (fumlObject instanceof Property)
            {
            	Property property = (Property)fumlObject;
        	    Repository.INSTANCE.getMapping().mapProperty(property.class_,
        	    		property, this.artifact);
            }
            else if (fumlObject instanceof EnumerationLiteral)
            {
            	EnumerationLiteral literal = (EnumerationLiteral)fumlObject;
              	Repository.INSTANCE.getMapping().mapEnumerationLiteral(literal, 
                			null, artifact);
            }
            else if (fumlObject instanceof Package)
            {
            	Package pkg = (Package)fumlObject;
              	Repository.INSTANCE.getMapping().mapPackage(pkg, 
            			null, artifact);
            	//Repository.INSTANCE.getMapping().mapElementByName(namedElement, this.artifact);
        	    //Repository.INSTANCE.getMapping().mapElementById(namedElement, this.artifact);
            }
            else if (fumlObject instanceof NamedElement)
            {
                NamedElement namedElement = (NamedElement)fumlObject;
            	Repository.INSTANCE.getMapping().mapElementByName(namedElement, this.artifact);
        	    Repository.INSTANCE.getMapping().mapElementById(namedElement, this.artifact);
            }
            else if (fumlObject instanceof Element) {
                Element element = (Element)fumlObject;
        	    Repository.INSTANCE.getMapping().mapElementById(element, this.artifact);
            }
            else // can be a Comment which is not an Element
            	if (log.isDebugEnabled())
            		log.debug("ignoring class, " 
            				+ fumlObject.getClass().getName());
        }       
	}

	public void elementStubAssembled(ElementAssemblerResultsEvent event) {
        if (log.isDebugEnabled())
            log.debug("element stub assembled");
        Iterator<String> ids = event.getSource().getResultsXmiIds().iterator();
        while (ids.hasNext())
        {
            String id = ids.next();
            FumlObject fumlObject = event.getSource().lookupResult(id);
            if (fumlObject instanceof NamedElement)
                if (log.isDebugEnabled())
                    log.debug("loading: ("
                    	+ fumlObject.getClass().getName() + ") "	
                    	+ ((NamedElement)fumlObject).qualifiedName
                        + " (" + id + ")");
            else
                if (log.isDebugEnabled())
                    log.debug("loading: ("
                    	+ fumlObject.getClass().getName() + ") "
                    	+ "(" + id + ")");

            if (fumlObject instanceof NamedElement)
            {
                NamedElement namedElement = (NamedElement)fumlObject;
            	Repository.INSTANCE.getMapping().mapElementByName(namedElement, this.artifact);
            	Repository.INSTANCE.getMapping().mapElementById(namedElement, this.artifact);
                log.warn("imported invalid "
                        + namedElement.getClass() + " ("
                        + namedElement.getXmiId() + ") '"
                        + namedElement.name
                        + "' as non-executable \"stub\" element");
            }
            else if (fumlObject instanceof Element) {
            	Element element = (NamedElement)fumlObject;
            	Repository.INSTANCE.getMapping().mapElementById(element, this.artifact);
                log.warn("imported invalid "
                        + fumlObject.getClass() + " ("
                        + fumlObject.getXmiId()
                        + " as non-executable \"stub\" element");
            }	
            else // can be a Comment which is not an Element
            	if (log.isDebugEnabled())
            		log.debug("ignoring class, " 
            				+ fumlObject.getClass().getName());
        }
	}

	public void streamCompleted(ElementReaderEvent event) {
		
    	for (Class_ c: classList) {  
    		org.modeldriven.fuml.repository.Class_ clss = null;
    		if (!(c instanceof Stereotype)) {
    		    clss = new org.modeldriven.fuml.repository.model.Class_(c, 
    		    		this.artifact);
    		}
    		else
    		{
    		    clss = new org.modeldriven.fuml.repository.model.Stereotype((Stereotype)c, 
    		    		this.artifact);
    		}	
    		Repository.INSTANCE.loadClass(clss);
    	}
    		
	}
	
	private String getQualifiedPackageName(fuml.syntax.packages.Package pkg) {
		List<fuml.syntax.packages.Package> list = new ArrayList<fuml.syntax.packages.Package>();
		fuml.syntax.packages.Package p = pkg;
		while (p != null) {
			list.add(p);
			p = p.nestingPackage;
		}
		
		StringBuilder buf = new StringBuilder();
		for (int i = list.size()-1; i >= 0; i--) {
			if (i < list.size()-1)
				buf.append(".");
			buf.append(list.get(i).name);
		}
		
		return buf.toString();
	}

}
