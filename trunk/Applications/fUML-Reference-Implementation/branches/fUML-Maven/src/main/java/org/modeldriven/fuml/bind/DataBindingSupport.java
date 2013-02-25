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
package org.modeldriven.fuml.bind;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class DataBindingSupport {

	protected JAXBContext context;

	private DataBindingSupport() {}
	
	public DataBindingSupport(JAXBContext context)
	{
		this.context = context;
	}
	
    public String marshal(Object root) 
        throws JAXBException
	{	    
	    StringWriter writer = new StringWriter();
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    m.marshal(root, writer);
	    return writer.toString();
	}    

    public Object unmarshal(String xml) 
        throws JAXBException
	{
	    StringWriter writer = new StringWriter();
	    Unmarshaller u = context.createUnmarshaller();
	    return u.unmarshal(new StringReader(xml));
	}    

    public Object unmarshal(InputStream stream) 
        throws JAXBException
	{
	    StringWriter writer = new StringWriter();
	    Unmarshaller u = context.createUnmarshaller();
	    return u.unmarshal(stream);
	}    
    
}
