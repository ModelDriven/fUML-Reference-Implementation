package org.modeldriven.fuml.io;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.assembly.ElementAssemblerEventListener;
import org.modeldriven.fuml.assembly.ElementGraphAssembler;
import org.modeldriven.fuml.assembly.ElementStubAssembler;
import org.modeldriven.fuml.xmi.XmiChildFinder;
import org.modeldriven.fuml.xmi.stream.StreamNodeEvent;
import org.modeldriven.fuml.xmi.stream.StreamNodeListener;
import org.modeldriven.fuml.xmi.validation.ErrorCode;
import org.modeldriven.fuml.xmi.validation.ValidationError;
import org.modeldriven.fuml.xmi.validation.ValidationErrorCollector;
import org.modeldriven.fuml.xmi.validation.ValidationEventListener;

public class IncrementalElementReader extends ElementReader 
    implements StreamNodeListener 
{	
    private static Log log = LogFactory.getLog(IncrementalElementReader.class);
    
    private static String STREAM_ELEMENT_NAME_PACKAGED_ELEMENT = "packagedElement";
    
    public IncrementalElementReader() {
    }
    
    public String[] getElementNames()
    {
        return new String[] {
        		STREAM_ELEMENT_NAME_PACKAGED_ELEMENT
        };
    }

    public void nodeCreated(StreamNodeEvent event) {
        // not interested
    }

    public void nodeCompleted(StreamNodeEvent event) {

    	if (log.isDebugEnabled())
    		log.debug("nodeCompleted '" + event.getSource().getLocalName()
    				+ "'");
        XmiChildFinder childFinder = new XmiChildFinder(
                event.getSource().getLocalName());
        event.getSource().accept(childFinder);
        if (childFinder.getResult() != null) {
        	if (log.isDebugEnabled())
        		log.debug("ignoring element '" + event.getSource().getLocalName()
        				+ "' as non-atomic packaged element");
            return; // has a descendant with our local name. We want "atomic" packaged elements
        }    
        // FIXME: make finder namespace aware

        if (!event.getSource().getContext().getUmlNamespace().getNamespaceURI().equals(
                event.getSource().getNamespaceURI()))
            return; // not in UML namespace

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

            freeNode(event); 
        }
        else
        {
            // For packagedElement, allow internal (and external?) invalid reference errors, as these are to
            // be resolved in a later traversal over the data. For errors other than these
            // we create a stub element such that we can still reference it.
            int internalCount = errorCollector.getErrorCount(ErrorCode.INVALID_REFERENCE);
            if (count > internalCount) // invalid
            {
                if (log.isDebugEnabled())
                    log.debug("found invalid node: "
                        + event.getSource().getXmiType() + " ("
                        + event.getSource().getXmiId() + ")");

                ElementStubAssembler stubAssembler =
                    new ElementStubAssembler(event.getSource());
                if (this.elementAssemblerEventListeners != null)
                	for (ElementAssemblerEventListener listener : this.elementAssemblerEventListeners)
                		stubAssembler.addEventListener(listener);
                stubAssembler.start();
                
                for (ValidationError error : errorCollector.getErrors())
                	stubAssembler.addErrorText(error.getText());

                freeNode(event); 
            }
            else // unresolved references
                if (log.isDebugEnabled())
                    log.debug("found node with unresolved references: "
                        + event.getSource().getXmiType() + " ("
                        + event.getSource().getXmiId() + ") - ignoring during incremental loading");
        }
    }
    
    /**
     * Free (clip) the given XMI sub-graph from the stream, 
	 * so to recoup it's memory and keep processing
     * @param event - the event containing the XMI node to remove
     */
    private void freeNode(StreamNodeEvent event) {
        if (event.getParent() != null)
            if (!event.getParent().removeChild(event.getSource()))
                throw new IOException("could not remove assembled node: "
                    + event.getSource().getXmiType() + " ("
                    + event.getSource().getXmiId() + ")");
    	
    }
}
