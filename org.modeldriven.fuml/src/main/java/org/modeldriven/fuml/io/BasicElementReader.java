package org.modeldriven.fuml.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.FumlException;
import org.modeldriven.fuml.assembly.ElementAssemblerEventListener;
import org.modeldriven.fuml.assembly.ElementGraphAssembler;
import org.modeldriven.fuml.xmi.stream.StreamNodeEvent;
import org.modeldriven.fuml.xmi.stream.StreamNodeListener;
import org.modeldriven.fuml.xmi.validation.ErrorSeverity;
import org.modeldriven.fuml.xmi.validation.ValidationErrorCollector;
import org.modeldriven.fuml.xmi.validation.ValidationEventListener;

public class BasicElementReader extends ElementReader 
    implements StreamNodeListener
{	
    private static Log log = LogFactory.getLog(BasicElementReader.class);
    
    private static String STREAM_ELEMENT_NAME_XMI = "XMI";
    private static String STREAM_ELEMENT_NAME_MODEL = "Model";
   
    public BasicElementReader() {    	
    }
    
    public String[] getElementNames()
    {
        return new String[] {
        		STREAM_ELEMENT_NAME_XMI,	
        	    STREAM_ELEMENT_NAME_MODEL
        };
    }

    public void nodeCreated(StreamNodeEvent event) {
    	// not interesting
    }

    public void nodeCompleted(StreamNodeEvent event) {

        if (event.getParent() != null)
            if (event.getParent().getLocalName().equals(STREAM_ELEMENT_NAME_XMI))
                return; // ignore other than top level model elements

        //if (!event.getSource().getContext().getUmlNamespace().getNamespaceURI().equals(
        //        event.getSource().getNamespaceURI()))
        //    throw new IOException("root node is not in UML namespace");

        if (log.isDebugEnabled())
            log.debug("validating: "
                + event.getSource().getXmiType() + "("
                + event.getSource().getXmiId() + ")");

        ValidationErrorCollector errorCollector = new ValidationErrorCollector(
                event.getSource());
        if (this.validationEventListeners != null)
        	for (ValidationEventListener listener : this.validationEventListeners)
        		errorCollector.addEventListener(listener);
        errorCollector.validate();

        int count = errorCollector.getErrorCount();
        if (count == 0) // valid
        {
            ElementGraphAssembler assembler =
                new ElementGraphAssembler(event.getSource());
            if (this.elementAssemblerEventListeners != null)
            	for (ElementAssemblerEventListener listener : this.elementAssemblerEventListeners)
            		assembler.addEventListener(listener);
            assembler.start();
            assembler.clear();

        }
        else // has errors
        {
            if (errorCollector.getErrorCount(ErrorSeverity.FATAL) > 0)
                throw new FumlException("fatal validation errors encountered");
        }
        
        if (this.elementReaderEventListeners != null)
        	for (ElementReaderEventListener listener : this.elementReaderEventListeners)
        		listener.streamCompleted(new ElementReaderEvent(this));
        
    }
    
}
