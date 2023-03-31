/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 * Copyright 2023 Model Driven Solutions, Inc.
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   DAT/MDS - initial API and implementation
 *   MDS - changed from javax.xml to jakarta.xml
 *
 */
package org.modeldriven.fuml.bind;

import java.io.InputStream;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;

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
