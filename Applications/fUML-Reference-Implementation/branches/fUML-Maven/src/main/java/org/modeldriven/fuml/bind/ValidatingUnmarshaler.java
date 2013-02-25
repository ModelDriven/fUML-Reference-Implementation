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
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class ValidatingUnmarshaler extends DataBindingSupport {

    private static Log log = LogFactory.getLog(ValidatingUnmarshaler.class);
    private Unmarshaller unmarshaler;
    private Resolver resolver;
    private Handler handler;

    private ValidatingUnmarshaler() {
        super(null);
    }

    public ValidatingUnmarshaler(URL url, JAXBContext context,
            BindingValidationEventHandler validationEventHandler) throws JAXBException,
            SAXException {
        super(context);
        if (url == null)
            throw new IllegalArgumentException("non-null argument expected, url");
        this.unmarshaler = createUnmarshaler(url, context);
        this.unmarshaler.setEventHandler(validationEventHandler);
    }

    public ValidatingUnmarshaler(InputStream stream, JAXBContext context,
            BindingValidationEventHandler validationEventHandler) throws JAXBException,
            SAXException {
        super(context);
        if (stream == null)
            throw new IllegalArgumentException("non-null argument expected, stream");
        // this.resolver = new Resolver();
        // this.handler = new Handler();
        this.unmarshaler = createUnmarshaler(stream, context, this.handler, this.resolver);
        this.unmarshaler = createUnmarshaler(stream, context);
        this.unmarshaler.setEventHandler(validationEventHandler);
    }

    public ValidatingUnmarshaler(URL url, JAXBContext context, Handler handler, Resolver resolver)
            throws JAXBException, SAXException {
        super(context);
        if (url == null)
            throw new IllegalArgumentException("non-null argument expected, url");
        this.resolver = resolver;
        this.handler = handler;
        this.unmarshaler = createUnmarshaler(url, context, this.handler, this.resolver);
    }

    public ValidatingUnmarshaler(InputStream stream, JAXBContext context, Handler handler,
            Resolver resolver) throws JAXBException, SAXException {
        super(context);
        if (stream == null)
            throw new IllegalArgumentException("non-null argument expected, stream");
        this.resolver = resolver;
        this.handler = handler;
        this.unmarshaler = createUnmarshaler(stream, context, this.handler, this.resolver);
    }

    /**
     * Creates an unmarshaler using the given factories and URL. Loads only the
     * given (subclass) schema as this is the "root" schema and it should
     * include any other schema resources it needs, and so on. Note all included
     * schemas MUST be found at the same class level as the root schema.
     * 
     * @param url
     *            the Schema URL
     * @param context
     *            the SAXB context
     * @param handler
     *            the SAX handler
     * @param resolver
     *            the SAX resolver
     * @return the unmarshaler
     * @throws JAXBException
     * @throws SAXException
     */
    private Unmarshaller createUnmarshaler(URL url, JAXBContext context, Handler handler,
            Resolver resolver) throws JAXBException, SAXException {
        Unmarshaller u = context.createUnmarshaller();
        // adds a custom object factory
        // u.setProperty("com.sun.xml.bind.ObjectFactory",new
        // ObjectFactoryEx());
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schemaGrammar = schemaFactory.newSchema(url);
        u.setSchema(schemaGrammar);
        Validator schemaValidator = schemaGrammar.newValidator();
        schemaValidator.setResourceResolver(resolver);
        schemaValidator.setErrorHandler(handler);

        return u;
    }

    private Unmarshaller createUnmarshaler(URL url, JAXBContext context) throws JAXBException,
            SAXException {
        Unmarshaller u = context.createUnmarshaller();
        // adds a custom object factory
        // u.setProperty("com.sun.xml.bind.ObjectFactory",new
        // ObjectFactoryEx());
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schemaGrammar = schemaFactory.newSchema(url);
        u.setSchema(schemaGrammar);

        return u;
    }

    /**
     * Creates an unmarshaler using the given factories and sream. Loads only
     * the given (subclass) schema as this is the "root" schema and it should
     * include any other schema resources it needs, and so on. Note all included
     * schemas MUST be found at the same class level as the root schema.
     * 
     * @param stream
     *            the Schema stream
     * @param context
     *            the SAXB context
     * @param handler
     *            the SAX handler
     * @param resolver
     *            the SAX resolver
     * @return the unmarshaler
     * @throws JAXBException
     * @throws SAXException
     */
    private Unmarshaller createUnmarshaler(InputStream stream, JAXBContext context,
            Handler handler, Resolver resolver) throws JAXBException, SAXException {
        Unmarshaller u = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schemaGrammar = schemaFactory.newSchema(new StreamSource(stream));
        u.setSchema(schemaGrammar);
        Validator schemaValidator = schemaGrammar.newValidator();
        schemaValidator.setResourceResolver(resolver);
        schemaValidator.setErrorHandler(handler);

        return u;
    }

    private Unmarshaller createUnmarshaler(InputStream stream, JAXBContext context)
            throws JAXBException, SAXException {
        Unmarshaller u = context.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        Schema schemaGrammar = schemaFactory.newSchema(new StreamSource(stream));
        u.setSchema(schemaGrammar);

        return u;
    }

    public Object validate(String xml) throws JAXBException, UnmarshalException {
        return unmarshaler.unmarshal(new StringReader(xml));
    }

    public Object validate(InputStream xml) throws JAXBException, UnmarshalException {
        return unmarshaler.unmarshal(xml);
    }

    public BindingValidationEventHandler getValidationEventHandler() throws JAXBException {
        return (BindingValidationEventHandler) this.unmarshaler.getEventHandler();
    }

    protected static class Handler extends DefaultHandler {

        public void error(SAXParseException sAXParseException) throws SAXException {
            log.error(sAXParseException.getMessage(), sAXParseException);
        }

        public void fatalError(SAXParseException sAXParseException) throws SAXException {
            log.error(sAXParseException.getMessage(), sAXParseException);
        }

        public void warning(org.xml.sax.SAXParseException sAXParseException)
                throws org.xml.sax.SAXException {
            log.warn(sAXParseException.getMessage(), sAXParseException);
        }
    }

    protected static class Resolver implements LSResourceResolver {
        public org.w3c.dom.ls.LSInput resolveResource(String str, String str1, String str2,
                String str3, String str4) {
            log.info("Resolving : " + str + ":" + str1 + ":" + str2 + ":" + str3 + ":" + str4);
            return null;

        }
    }

}
