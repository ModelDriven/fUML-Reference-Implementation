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
 * Interface generalizing XMI elements across both stream-based XMI import 
 * and assembly into the target FUML java model. An element may have character 
 * data as well as element and attribute data. 
 * 
 * @author Scott Cinnamond
 */
public interface XmiElement {

	public String getNamespaceURI();
	public String getPrefix();
	public String getLocalName();
    public String getData();
    public String getXmiType();
    public boolean hasXmiType();
    public String getXmiId();
}
