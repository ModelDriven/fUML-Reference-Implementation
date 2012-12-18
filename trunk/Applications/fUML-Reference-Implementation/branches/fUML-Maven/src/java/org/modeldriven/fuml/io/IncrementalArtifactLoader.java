package org.modeldriven.fuml.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.xmi.stream.StreamReader;
import org.modeldriven.fuml.xmi.validation.ErrorCode;
import org.modeldriven.fuml.xmi.validation.ErrorSeverity;
import org.modeldriven.fuml.xmi.validation.ValidationErrorEvent;

public class IncrementalArtifactLoader extends ArtifactLoader
{
	
    private static Log log = LogFactory.getLog(IncrementalArtifactLoader.class);
    
    public IncrementalArtifactLoader() {
    	
    	super();
    }
    
    public void read(FileArtifact artifact) {
    	
    	this.artifact = artifact;

    	StreamReader reader = new StreamReader();

        IncrementalElementReader packagedElementReader = new IncrementalElementReader();
        reader.addStreamNodeListener(packagedElementReader);
    	packagedElementReader.addValidationEventListener(this);
    	packagedElementReader.addElementAssemblerEventListener(this);    	
        
        BasicElementReader modelElementReader = new BasicElementReader();
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

    	StreamReader reader = new StreamReader();

        IncrementalElementReader packagedElementReader = new IncrementalElementReader();
        reader.addStreamNodeListener(packagedElementReader);
    	packagedElementReader.addValidationEventListener(this);
    	packagedElementReader.addElementAssemblerEventListener(this);    	
        
        BasicElementReader modelElementReader = new BasicElementReader();
        reader.addStreamNodeListener(modelElementReader);
        modelElementReader.addValidationEventListener(this);
        modelElementReader.addElementAssemblerEventListener(this);    	        
        modelElementReader.addElementReaderEventListener(this);
        
		reader.read(artifact.getInputStream());
    }  
    
    @Override
	public void validationError(ValidationErrorEvent event) {
		
		if (event.getError().getCode().ordinal() == ErrorCode.INVALID_REFERENCE.ordinal())
			return; // we ignore these above
        if (event.getError().getSeverity().ordinal() == ErrorSeverity.FATAL.ordinal())
            log.error("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
        else if (event.getError().getSeverity().ordinal() == ErrorSeverity.WARN.ordinal())
            log.warn("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
        else if (event.getError().getSeverity().ordinal() == ErrorSeverity.INFO.ordinal())
            log.info("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
        else
            log.error("(" + this.artifact.getURN() + ") validation error " + event.getError().getText());
	}

}
