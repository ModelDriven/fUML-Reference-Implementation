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

package org.modeldriven.fuml.xmi;

/**
 * Package level constants.
 * 
 * @author Scott Cinnamond
 */
public class XmiConstants {

    public static final String NAMESPACE_PREFIX = "xmlns";

    public static final String ELEMENT_XMI_ROOT = "Model";
    public static final String ELEMENT_XMI_EXTENSION = "Extension";

    public static final String ATTRIBUTE_XMI_TYPE = "type";
    public static final String ATTRIBUTE_XMI_ID = "id";
    public static final String ATTRIBUTE_XMI_HREF = "href"; // used for external
                                                            // references
    public static final String ATTRIBUTE_XMI_IDREF = "idref"; // used for
                                                              // internal
                                                              // references

}
