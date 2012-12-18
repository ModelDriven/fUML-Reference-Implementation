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

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

public interface DataBinding {

    public String marshal(Object root) 
        throws JAXBException;

    public Object unmarshal(String xml) 
        throws JAXBException;

    public Object unmarshal(InputStream stream) 
        throws JAXBException;

    public Object validate(String xml) 
        throws JAXBException;

    public Object validate(InputStream xml)
        throws JAXBException, UnmarshalException;
    
    public BindingValidationEventHandler getValidationEventHandler()
    throws JAXBException;
}
