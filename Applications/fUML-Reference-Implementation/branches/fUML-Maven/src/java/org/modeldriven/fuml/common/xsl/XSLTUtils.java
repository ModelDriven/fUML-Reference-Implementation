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
package org.modeldriven.fuml.common.xsl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class XSLTUtils
{       
    public XSLTUtils()
    {
    } 

    public void transform(File dest, File src, URL styleSheet)
        throws IOException, TransformerConfigurationException, TransformerException
    {
        transform(dest, src, styleSheet, null);
    }

    public void transform(File dest, File src, URL styleSheet, Properties params)
        throws IOException, TransformerConfigurationException, TransformerException
    {
        String srcXML = getContent(src);
        FileInputStream is = new FileInputStream(src);
        OutputStreamWriter result = new OutputStreamWriter(new FileOutputStream(dest));
        transform(
        	new StreamSource(is),
        	result,
        	styleSheet, params);       
    }

    public void transform(InputStream source, OutputStream dest, URL styleSheet)
        throws IOException, TransformerConfigurationException, TransformerException
    {
        OutputStreamWriter result = new OutputStreamWriter(dest);
        transform(
        	new StreamSource(source), 
        	result,
        	styleSheet, null);
    }
    
    public String transform(String xml, URL styleSheet)
        throws IOException, TransformerConfigurationException, TransformerException
    {
    	StringWriter result = new StringWriter();
    	transform(
    		new StreamSource(new StringReader(xml)),
    		result,
    		styleSheet, null);
    	return result.toString();
    }
    
    private void transform(StreamSource source, Writer dest, URL styleSheet, Properties params)
        throws TransformerConfigurationException, TransformerException
    {
        int indent_amount = 0;
        String encoding = "utf-8";

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer =             
                factory.newTransformer(new StreamSource(styleSheet.toExternalForm()));
                    
        if (indent_amount > 0)
        {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http\u003a//xml.apache.org/xslt}indent-amount", 
                                        Integer.toString(indent_amount));
        }
        if (params != null)
        {
            Iterator iter = params.keySet().iterator();           
            while (iter.hasNext())                                
            {                                                     
                String key = (String)iter.next();                 
                transformer.setParameter(key, params.get(key));   
            }                                                     
        }
                    
        if (encoding != null && !"".equals(encoding.trim()))
            transformer.setOutputProperty(OutputKeys.ENCODING, encoding);                        
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(source, new StreamResult(dest));
    }   

    private void writeContent(File dest, String content)
        throws IOException
    {
        FileOutputStream fos = new FileOutputStream(dest);
        BufferedOutputStream os = new BufferedOutputStream(fos);
        os.write(content.getBytes());
        os.flush();
        fos.close();
    }

    private String getContent(File source)
        throws IOException
    {
        long size = source.length();
        byte[] buf = new byte[4000];
        ByteArrayOutputStream os = new ByteArrayOutputStream((int)size); // bad!
        FileInputStream is = new FileInputStream(source);  
        try {
            int len = -1;                                                
            while ((len = is.read(buf)) != -1)                           
                os.write(buf, 0, len);     
        }
        finally {
            is.close();
            os.flush();
        }
        return new String(os.toByteArray());            
    }
    
    private String getContent(InputStream source) throws IOException {
        byte[] buf = new byte[4000];
        ByteArrayOutputStream os = new ByteArrayOutputStream(4000);
        try {
            int len = -1;
            while ((len = source.read(buf)) != -1)
                os.write(buf, 0, len);
        }
        finally  {
            source.close();
            os.flush();
        }
        return new String(os.toByteArray());            
    }
    
    public static void main(String[] args)
    {
        try {
            XSLTUtils ut = new XSLTUtils();
            if (args.length == 3)
            {
                ut.transform(new File(args[0]), new File(args[1]), (new File(args[2])).toURL()); 
            }
            else if (args.length == 4)
            {
                Properties params = new Properties();
                StringTokenizer st = new StringTokenizer(args[3], " =");
                while (st.hasMoreTokens())
                    params.put(st.nextToken(), st.nextToken());
                ut.transform(new File(args[0]), new File(args[1]), (new File(args[2])).toURL(), params);
            }
            else
                throw new IllegalArgumentException("expected either 3 or 4 args"); 
           
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}