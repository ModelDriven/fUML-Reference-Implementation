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
 * Encapsulates the node(s) of concern and other state for this event. 
 * 
 * @author Scott Cinnamond
 */
public class StreamNodeEvent {

    private StreamNode source;
    private StreamNode parent;
    
    @SuppressWarnings("unused")
    private StreamNodeEvent() {}

    public StreamNodeEvent(StreamNode source, StreamNode parent) {
        super();
        this.source = source;
        this.parent = parent;
    }

    public StreamNode getSource() {
        return source;
    }

    public StreamNode getParent() {
        return parent;
    }

    

}
