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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.config.FumlConfiguration;
import org.modeldriven.fuml.config.ImportExemption;
import org.modeldriven.fuml.config.ImportExemptionType;
import org.modeldriven.fuml.config.NamespaceDomain;
import org.modeldriven.fuml.xmi.XmiConstants;
import org.modeldriven.fuml.xmi.XmiNode;
import org.modeldriven.fuml.xmi.XmiNodeVisitor;
import org.modeldriven.fuml.xmi.XmiNodeVisitorStatus;

/**
 * Encapsulates a set of related XML (stream) events. Stream events are objects 
 * allocated by the StAX parser from the current stream and include element and 
 * attribute as well as namespace and location information. The primary event 
 * encapsulated is always a start-element event, but one of more 'characters' 
 * events may be added if character data for the element is encountered in the 
 * stream. 
 * @author Scott Cinnamond
 */
public class StreamNode implements XmiNode {

    private static Log log = LogFactory.getLog(StreamNode.class);
    private XMLEvent startElementEvent;
    private List<XMLEvent> characterEvents;
    private StreamContext context;
    private Location location;
	private List<XmiNode> nodes; // TODO: do we really want interfaces in this list?
	private XmiNode parent; // TODO: same issue
    
    @SuppressWarnings("unused")
	private StreamNode() {}
    
    public StreamNode(XMLEvent startElement, XmiNode parent, StreamContext context) {
        this.startElementEvent = startElement;
        this.parent = parent;
        this.context = context;
        // The Sun StAX impl "loses" element location info, seemingly 
        // immediately after it's read into an event. We just copy it
        // off here. Tried the default event allocator in both
        // the Sun RI and the associated utilities. 
        this.location = new StreamLocation(startElement.getLocation());
        if (log.isDebugEnabled())
        	log.debug("created '" + this.getLocation() + "' node (" + 
        			this.getXmiId() + ")");
    }
    
    public StreamNode(XMLEvent startElement, StreamContext context) {
        this(startElement, null, context);
    }
    
    public Location getLocation() {
		return location;
	}

	public String getLocalName()
    {
        return startElementEvent.asStartElement().getName().getLocalPart();
    }

	public String getNamespaceURI()
	{
	    QName name = startElementEvent.asStartElement().getName();
	    String uri = name.getNamespaceURI();
	    if (uri == null || uri.trim().length() == 0)
	        return this.context.getDefaultNamespace().getNamespaceURI();
	    else
	        return name.getNamespaceURI();
	}
	
	public String getPrefix() {
	    QName name = startElementEvent.asStartElement().getName();
	    String uri = name.getNamespaceURI();
	    if (uri == null || uri.trim().length() == 0)
	        return this.context.getDefaultNamespace().getPrefix();
	    else
	        return name.getPrefix();
	}

	public String getData()
    {
        if (this.characterEvents != null)
        {
            StringBuffer buf = new StringBuffer();
            Iterator<XMLEvent> events = this.characterEvents.iterator();
            while (events.hasNext())
                buf.append(events.next().asCharacters().getData());
        	return buf.toString();
        }
        return null;
    }
	
    public String getXmiType()
    {
        QName typeAttrib = new QName(context.getXmiNamespace().getNamespaceURI(), 
        		XmiConstants.ATTRIBUTE_XMI_TYPE);
        Attribute attrib = startElementEvent.asStartElement().getAttributeByName(typeAttrib);
        if (attrib != null)
        {    
            String value = attrib.getValue();
            return value.substring(context.getUmlNamespace().getPrefix().length() + 1); // prefix + ':'
        }
        
        return null;
    }
 
    public boolean hasAttribute(QName name)
    {
        Attribute attrib = startElementEvent.asStartElement().getAttributeByName(name);
        return (attrib != null);
    }
    
    public boolean hasAttributes()
    {
        if (startElementEvent != null)
        {    
            Iterator<Attribute> attributes = startElementEvent.asStartElement().getAttributes();
            return attributes.hasNext();
        }
        return false;       
    }    

    public Iterator<Attribute> getAttributes()
    {
        if (startElementEvent != null)
        {    
            Iterator<Attribute> attributes = startElementEvent.asStartElement().getAttributes();
            return attributes;
        }
        return null;       
    }    

    public Attribute getAttribute(QName name)
    {
        if (startElementEvent != null)
        {    
            Attribute attrib = startElementEvent.asStartElement().getAttributeByName(name);
            return attrib;
        }
        return null;
    }

    public Attribute getAttribute(String name)
    {
        if (startElementEvent != null)
        {    
            Attribute attrib = startElementEvent.asStartElement().getAttributeByName(
                    new QName(name));
            return attrib;
        }
        return null;
    }
    
    public String getAttributeValue(QName name)
    {
        if (startElementEvent != null)
        {    
            Attribute attrib = startElementEvent.asStartElement().getAttributeByName(name);
            if (attrib != null)
                return attrib.getValue();
        }
        return null;
    }

    public String getAttributeValue(String name)
    {
        if (startElementEvent != null)
        {    
            Attribute attrib = startElementEvent.asStartElement().getAttributeByName(
                new QName(name));
            if (attrib != null)
                return attrib.getValue();
        }
        return null;
    }
    
    public boolean hasXmiType()
    {
    	String xmiType = this.getXmiType();
    	return xmiType != null && xmiType.length() > 0;
    }

    public String getXmiId()
    {
        QName typeAttrib = new QName(context.getXmiNamespace().getNamespaceURI(), 
        		XmiConstants.ATTRIBUTE_XMI_ID);
        Attribute attrib = startElementEvent.asStartElement().getAttributeByName(typeAttrib);
        if (attrib != null)
        {    
            String value = attrib.getValue();
            return value;
        }
        
        return null;
    }
    
    public int getLineNumber() {
        return this.location.getLineNumber();	
    }
    
    public int getColumnNumber() {
        return this.location.getColumnNumber();	
    }
    
    public void add(StreamNode node)
    {
        if (nodes == null)
            nodes = new ArrayList<XmiNode>();
        nodes.add(node);
        if (log.isDebugEnabled())
        	log.debug("added '" + node.getLocalName() + "' (" + node.getXmiId() + ") child node to "
        		+ this.getLocation() + "' parent node (" + this.getXmiId() + ")");
    }
    
    public XmiNode getParent() {
        return parent;
    }

    public void setParent(XmiNode parent) {
        this.parent = parent;
    }
    
    public List<XmiNode> getNodes() {
        return nodes;
    }
    
    public boolean hasNodes() {
        return nodes != null;
    }

    public XmiNode findChildByName(String name)
    {
        if (this.nodes != null)
        {   
            Iterator<XmiNode> iter = this.nodes.iterator();
            while (iter.hasNext())
            {
                XmiNode child = iter.next();
                if (child.getLocalName().equals(name))
                {
                    return child;
                }
            }
        }
        return null;
    }
    
    public XmiNode findChildById(String id)
    {
        if (this.nodes != null)
        {   
            Iterator<XmiNode> iter = this.nodes.iterator();
            while (iter.hasNext())
            {
                XmiNode child = iter.next();
                if (child.getXmiId().equals(id))
                {
                    return child;
                }
            }
        }
        return null;
    }
    
    public boolean removeChild(XmiNode child)
    {
    	boolean removed = this.nodes.remove(child);
        if (log.isDebugEnabled())
        	log.debug("removed '" + child.getLocalName() + "' (" + child.getXmiId() + ") child node to "
        		+ this.getLocation() + "' parent node (" + this.getXmiId() + ")");
        return removed;
    }
    
    public XMLEvent getStartElementEvent() {
        return startElementEvent;
    }
    
    public void accept(XmiNodeVisitor visitor)
    {
        if (log.isDebugEnabled())
            log.debug(this.getClass().getSimpleName());
        accept(visitor, this, null, null, this, new HashMap<XmiNode, XmiNode>(), 
                new XmiNodeVisitorStatus(), 0);
    }
        
    private void accept(XmiNodeVisitor visitor, XmiNode target, 
            XmiNode source, String sourceKey, 
            XmiNode root, 
            Map<XmiNode, XmiNode> visited, XmiNodeVisitorStatus status, int level)
    {
        if (log.isDebugEnabled())
            log.debug("accept: " + target.getLocalName());
        
        if (visited.get(target) == null)
            visited.put(target, target);
        else
        {   
            if (log.isDebugEnabled())
                log.debug("ignoring, " + target.getClass().getName());
            return;
        }    
        
        visitor.visit(target, source, sourceKey, status, level);
        if (status.getStatus() == XmiNodeVisitorStatus.STATUS_ABORT)
            return;
        
        List<XmiNode> nodes = target.getNodes();
        if (nodes != null)
            for (int i = 0; i < nodes.size(); i++)
            {
                XmiNode child = nodes.get(i);
                if (((StreamNode)child).isIgnored())
                {
                    if (log.isDebugEnabled())
                        log.debug("ignoring XMI 'Extension' node");
                    continue; // bag these and their children for now
                }
                if (log.isDebugEnabled())
                    log.debug("processing node: " + child.getLocalName());
                accept(visitor, child, target, 
                        child.getLocalName(), root, visited, status, level++);                
            }       
    }
    
	public StreamContext getContext() {
		return context;
	}

	public void addCharactersEvent(XMLEvent charactersEvent) {
	    if (this.characterEvents == null)
	        this.characterEvents = new ArrayList<XMLEvent>();
		this.characterEvents.add(charactersEvent);
	}
    
	public boolean hasCharacters()
	{
		return this.characterEvents != null && characterEvents.size() > 0;
	}
	
	public boolean isIgnored()
	{
	    if (startElementEvent == null)
	        return false; // can't ignore character nodes
	    
        String uri = startElementEvent.asStartElement().getName().getNamespaceURI();
        if (uri == null || uri.length() == 0)
        {
            uri = this.context.getDefaultNamespace().getNamespaceURI();
        }
        
        ImportExemption importExemption = FumlConfiguration.getInstance().findImportExemptionByElement(this.getLocalName());
        
        if (importExemption != null) { 
        	
            NamespaceDomain domain = FumlConfiguration.getInstance().getNamespaceDomain(uri);
        	if (importExemption.getType().ordinal() == ImportExemptionType.ELEMENT.ordinal() &&
        		importExemption.getDomain().ordinal() == domain.ordinal())
        		
            return true;
        }
	    return false;
	}

    
}
