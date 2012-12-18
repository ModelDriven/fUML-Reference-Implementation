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

package org.modeldriven.fuml.xmi.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javanet.staxutils.events.EventAllocator;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventAllocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.xmi.XmiConstants;
import org.modeldriven.fuml.xmi.XmiException;
import org.modeldriven.fuml.xmi.XmiReader;


/**
 * Stream based XmiReader implementation utilizing the StAX parser. For more information 
 * on the StAX parser, see the package-level documentation. Only start-element, 
 * end-element and character(s) events are processed, other events being ignored. 
 * Certain start-element events are also ignored based on namespace-sensitive configuration 
 * information. Events of interest are allocated from the stream into immutable nodes 
 * and arranged into a hierarchy for ease of processing by interested clients. Clients 
 * are notified using a standard event-listener pattern. NOTE: event queues are not 
 * currently used to asynchronously publish events, as clients are intended to 
 * completely process relevant portions of the stream freeing or "clipping" any and 
 * all possible allocated nodes from the hierarchy for garbage collection.  
 *  
 * @author Scott Cinnamond
 */
public class StreamReader implements XmiReader {
    
    private static Log log = LogFactory.getLog(StreamReader.class);
    private Stack<StreamNode> nodes = new Stack<StreamNode>();
    private Map<String, List<StreamNodeListener>> streamNodeListenerMap; 
	
    private static XMLEventAllocator allocator = null;
	
	public StreamReader () {
    }		
	
	@SuppressWarnings("unchecked")
    public Collection read(InputStream stream)
	{	    
        List<Object> results = new ArrayList<Object>();
        InputStream source = stream;
        StreamContext context = null;
    	
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			
						
			factory.setXMLResolver(new XMLResolver() {

				@Override
				public Object resolveEntity(String publicID,
	                     String systemID,
	                     String baseURI,
	                     String namespace) throws XMLStreamException {
					// TODO Auto-generated method stub
					return null;
				}
				
			});
			
			/*
			XStream xstream = new XStream(new StaxDriver() {
				protected XMLStreamReader createParser(Reader xml) throws
				XMLStreamException {
				return getInputFactory().createXMLStreamReader(xml);
				}
				protected XMLStreamReader createParser(InputStream xml) throws
				XMLStreamException {
				return getInputFactory().createXMLStreamReader(xml);
				}
				});
			*/
            
            //factory.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,Boolean.FALSE);
            //factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,Boolean.TRUE);
            //set the IS_COALESCING property to true , if application desires to
            //get whole text data as one event.            
            //factory.setProperty(XMLInputFactory.IS_COALESCING , Boolean.TRUE);

            factory.setEventAllocator(new EventAllocator());
            
            allocator = factory.getEventAllocator();
            XMLStreamReader streamReader = factory.createXMLStreamReader(stream);
            
            
            int eventType = streamReader.getEventType();
            StreamNode node = null;
            StreamNode parent = null;
            int level = 0;
            int ignoredNodeLevel = -1;
            
            while(streamReader.hasNext()){
                eventType = streamReader.next();
                //if (log.isDebugEnabled())
                //    log.debug(this.getEventTypeString(eventType));
                                
                switch (eventType){
                case XMLEvent.START_ELEMENT:
                    level++;
                    if (ignoredNodeLevel >= 0)
                        break;
                    
                    XMLEvent event = allocateXMLEvent(streamReader);
                    if (level == 1)
                    {
                        if (context != null)
                            throw new XmiException("existing context unexpected");
                        context = new StreamContext(event);
                    }
                    
                    // debugging
                    if (event.toString().contains("plasma:PlasmaType")) {
                    	int foo = 1;
                    	foo++;
                    }
                        
                    node = new StreamNode(event, context);
                    if (node.isIgnored())
                    {    
                        if (log.isDebugEnabled())
                        {    
                            Location loc = event.getLocation();               
                            String msg = "start ignoring elements - level:  " 
                                + String.valueOf(level) 
                                + " - line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "] - ";
                            log.debug(msg);
                        }
                        ignoredNodeLevel = level;
                        break;
                    }
                    
                    logEventInfo(event); 
                    parent = null;
                    if (nodes.size() > 0)
                    {    
                        parent = nodes.peek();                      
                        parent.add(node);
                        node.setParent(parent);
                        if (isRootNode(node, context))
                            results.add(node);
                    }
                    else
                    {   
                        if (isRootNode(node, context))
                            results.add(node);
                    }
                    
                    nodes.push(node);
                    fireStreamNodeCreated(node, parent);
                    break;
                case XMLEvent.END_ELEMENT:
                    if (ignoredNodeLevel >= 0)
                    {
                        if (ignoredNodeLevel == level)
                        {    
                            if (log.isDebugEnabled())
                            {    
                                event = allocateXMLEvent(streamReader);
                                Location loc = event.getLocation();               
                                String msg = "end ignoring elements - level:  " 
                                    + String.valueOf(level) 
                                    + " - line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "] - ";
                                log.debug(msg);
                            }
                            ignoredNodeLevel = -1;
                        }
                        level--;
                        break;
                    }
                    
                    level--;
                    node = nodes.pop();  
                    parent = null;
                    if (nodes.size() > 0)
                        parent = nodes.peek();
                    fireStreamNodeCompleted(node, parent);
                    break;
                case XMLEvent.CHARACTERS:
                    if (ignoredNodeLevel >= 0)
                        break;
                    node = nodes.peek(); 
                    event = allocateXMLEvent(streamReader);
                    String data = event.asCharacters().getData();
                    if (data != null)
                    {
                        data = data.trim();
                        if (data.length() > 0)
                        {    
                            if (log.isDebugEnabled())
                                log.debug("CHARACTERS: '" + data + "'");
                            if (data.length() > 0)
                            {
                                node = nodes.peek();
                                node.addCharactersEvent(event);
                            }
                        }
                    }
                	break;
                default:
                	if (log.isDebugEnabled())
                	{	
                        event = allocateXMLEvent(streamReader);
                        logEventInfo(event);               
                	}
                	break;
                }
            }
            if (results.size() > 1)
                throw new XmiException("found multiple root nodes (" + results.size() + ")");
        }catch(XMLStreamException e){
            throw new XmiException(e);
        }
		finally {
		    try {
		        source.close();
		    }
		    catch (IOException e) {		        
		    }
		}
				
		return results;		
	}
	
	// FIXME: need event queue
	private void fireStreamNodeCreated(StreamNode node, StreamNode parent)
	{
        if (streamNodeListenerMap != null)
        {    
            List<StreamNodeListener> list = streamNodeListenerMap.get(node.getLocalName());
            if (list != null) {
            	for (StreamNodeListener listener : list)
                    listener.nodeCreated(
                        new StreamNodeEvent(node, parent));
            }
        }	    
	}

    // FIXME: need event queue
    private void fireStreamNodeCompleted(StreamNode node, StreamNode parent)
    {
        if (streamNodeListenerMap != null)
        {    
            List<StreamNodeListener> list = streamNodeListenerMap.get(node.getLocalName());
            if (list != null) {
            	for (StreamNodeListener listener : list)
            		listener.nodeCompleted(
                    		new StreamNodeEvent(node, parent));
            }
        }       
    }
	
	private boolean isRootNode(StreamNode node, StreamContext context)
	{
        QName name = node.getStartElementEvent().asStartElement().getName();
        if (name.getNamespaceURI().equals(context.getUmlNamespace().getNamespaceURI()))                            
            if (XmiConstants.ELEMENT_XMI_ROOT.equalsIgnoreCase(name.getLocalPart()))
                return true;
        return false;
	}
	
	private void logEventInfo(XMLEvent event)
	{
	    if (log.isDebugEnabled())
	    {    
            Location loc = event.getLocation();               
            String msg = getEventTypeString(event.getEventType());
            msg += " line:col[" + loc.getLineNumber() + ":" + loc.getColumnNumber() + "] - ";
            msg += event.toString();
            log.debug(msg);	   
	    }
	}
	
    /** Get the immutable XMLEvent from given XMLStreamReader using XMLEventAllocator */
    private static XMLEvent allocateXMLEvent(XMLStreamReader reader) throws XMLStreamException{
        return allocator.allocate(reader);
    }  
    
    /**
     * Returns the String representation of the given integer constant.
     *
     * @param eventType Type of event.
     * @return String representation of the event
     */    
    public final static String getEventTypeString(int eventType) {
        switch (eventType){
            case XMLEvent.START_ELEMENT:
                return "START_ELEMENT";
            case XMLEvent.END_ELEMENT:
                return "END_ELEMENT";
            case XMLEvent.PROCESSING_INSTRUCTION:
                return "PROCESSING_INSTRUCTION";
            case XMLEvent.CHARACTERS:
                return "CHARACTERS";
            case XMLEvent.COMMENT:
                return "COMMENT";
            case XMLEvent.START_DOCUMENT:
                return "START_DOCUMENT";
            case XMLEvent.END_DOCUMENT:
                return "END_DOCUMENT";
            case XMLEvent.ENTITY_REFERENCE:
                return "ENTITY_REFERENCE";
            case XMLEvent.ATTRIBUTE:
                return "ATTRIBUTE";
            case XMLEvent.DTD:
                return "DTD";
            case XMLEvent.CDATA:
                return "CDATA";
            case XMLEvent.SPACE:
                return "SPACE";
        }
        return "UNKNOWN_EVENT_TYPE , " + eventType;
    }  
    
    public void addStreamNodeListener(StreamNodeListener listener) {
        if (streamNodeListenerMap == null)
            streamNodeListenerMap = new HashMap<String, List<StreamNodeListener>>();
        String[] names = listener.getElementNames();
        for (int i = 0; i < names.length; i++)  
        {    
            List<StreamNodeListener> list = streamNodeListenerMap.get(names[i]);
            if (list == null)
            {    
                list = new ArrayList<StreamNodeListener>();
                    streamNodeListenerMap.put(names[i], list);
            }    
            list.add(listener);
        }
    }
}
