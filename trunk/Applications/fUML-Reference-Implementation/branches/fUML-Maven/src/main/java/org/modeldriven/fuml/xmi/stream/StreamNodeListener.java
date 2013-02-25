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

/**
 * An event dispatch interface used to notify interested clients of stream 
 * node allocation related events occurring during processing of 
 * an XML stream.
 * 
 * @author Scott Cinnamond
 */
public interface StreamNodeListener extends StreamEventListener {

    /**
     * A listener operation called when a node of interest and all "child" nodes 
     * have been completely allocated.
     * @param event the node event
     */
    public void nodeCreated(StreamNodeEvent event);
    
    /**
     * A listener operation called when a node of interest has 
     * been encounterd within the stream and allocated in memory.
     * @param event the node event
     */
    public void nodeCompleted(StreamNodeEvent event);
}
