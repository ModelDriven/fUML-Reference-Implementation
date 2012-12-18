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

package org.modeldriven.fuml.xmi.validation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * 
 * @author Scott Cinnamond
 */
public class ValidationProperties {

	public static final String PROPERTY_NAME_VALIDATION_PROPERTIES = "foo";
	public static final String PROPERTY_NAME_DEFAULT_VALIDATION_PROPERTIES = "validation.properties";
		
    private static ValidationProperties instance;
    private Properties props = new Properties();
    private String fileName = "";

    @SuppressWarnings("unchecked")
	private ValidationProperties()
    {      	
        fileName = System.getProperty(PROPERTY_NAME_VALIDATION_PROPERTIES,
        		PROPERTY_NAME_DEFAULT_VALIDATION_PROPERTIES);
         
        try {
        	FileInputStream fis = new FileInputStream(fileName);        	
        	props.load(fis);            
        }
        catch (IOException  e) {
        	try {
        	    InputStream stream = ValidationProperties.class.getResourceAsStream(fileName);
        	    if (stream == null)
        	    	throw new IllegalArgumentException("No properties file found for name, '"
                    		+ fileName + "'");
        	    props.load(stream);
        	}
        	catch (IOException  e2) {
                throw new IllegalArgumentException("No properties file found for name, '"
            		+ fileName + "'");
        	}
        }
        
        // add/override with system properties
    	//props.putAll(System.getProperties());

    	//trim values...in case spaces were inadvertently added in properties file.
    	Iterator keys = props.keySet().iterator();
		while(keys.hasNext()) {
			String key = (String)keys.next();
			props.setProperty(key, props.getProperty(key).trim());
		}   	
    }

    @SuppressWarnings("unchecked")
	public String dumpProperties(String separator)
    {
        StringBuffer buf = new StringBuffer();

        Iterator itr = props.keySet().iterator();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            String value = props.getProperty(key);
            buf.append(separator + key + "='" + value + "'");
        }

        return buf.toString();
    }

    public static ValidationProperties instance()
    {
        if (instance == null)
            initInstance(); // double-checked locking pattern 
        return instance;     
    }

    private static synchronized void initInstance()
    {
        if (instance == null)
            instance = new ValidationProperties();
    }

    public Properties getProperties()
    {
		return props;                          
    } 

    public String getProperty(String name)
    {
		return props.getProperty(name);                          
    } 

    public String getProperty(String name, String defaultValue)
    {
		return props.getProperty(name, defaultValue);                          
    } 
    
    public String getPropertiesFileName()
    {
		return fileName;                          
    } 

	
}
