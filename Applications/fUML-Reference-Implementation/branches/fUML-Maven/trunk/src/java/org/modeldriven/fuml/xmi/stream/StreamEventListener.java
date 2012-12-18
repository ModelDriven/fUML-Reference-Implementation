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

import java.util.EventListener;

/**
 * A base listener interface for this package. Makes the assumption that all 
 * events for the stream package are triggered by XML elements within the stream, and not
 * attributes, character entities or other lower level STAX stream events. 
 *  
 * @author Scott Cinnamond
 */
public interface StreamEventListener extends EventListener {
    /**
     * Returns the element (local) names of interest for this listener. 
     * @return the element (local) names of interest for this listener.
     */
    public String[] getElementNames();
}
