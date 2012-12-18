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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modeldriven.fuml.bind.BindingValidationEventHandler;
import org.modeldriven.fuml.bind.DataBinding;
import org.modeldriven.fuml.bind.ValidatingUnmarshaler;
import org.xml.sax.SAXException;

public class FumlConfigDataBinding implements DataBinding {

    private static Log log = LogFactory.getLog(FumlConfigDataBinding.class);
    public static String FILENAME_SCHEMA_CHAIN_ROOT = "FumlConfig.xsd";

    // just classes in the same package where can find the above respective
    // schema files via Class.getResource*
    public static Class RESOURCE_CLASS = FumlConfigDataBinding.class;

    private ValidatingUnmarshaler validatingUnmarshaler;

    public static Class[] FACTORIES = { org.modeldriven.fuml.config.ObjectFactory.class, };

    private FumlConfigDataBinding() {
    }

    public FumlConfigDataBinding(BindingValidationEventHandler validationEventHandler)
            throws JAXBException, SAXException {
        log.info("loading schema chain...");
        validatingUnmarshaler = new ValidatingUnmarshaler(RESOURCE_CLASS
                .getResource(FILENAME_SCHEMA_CHAIN_ROOT), JAXBContext.newInstance(FACTORIES),
                validationEventHandler);
    }

    public Class[] getObjectFactories() {
        return FACTORIES;
    }

    public String marshal(Object root) throws JAXBException {
        return validatingUnmarshaler.marshal(root);
    }

    public Object unmarshal(String xml) throws JAXBException {
        return validatingUnmarshaler.unmarshal(xml);
    }

    public Object unmarshal(InputStream stream) throws JAXBException {
        return validatingUnmarshaler.unmarshal(stream);
    }

    public Object validate(String xml) throws JAXBException {
        return validatingUnmarshaler.validate(xml);
    }

    public Object validate(InputStream xml) throws JAXBException, UnmarshalException {
        return validatingUnmarshaler.validate(xml);
    }

    public BindingValidationEventHandler getValidationEventHandler() throws JAXBException {
        return this.validatingUnmarshaler.getValidationEventHandler();
    }

}
