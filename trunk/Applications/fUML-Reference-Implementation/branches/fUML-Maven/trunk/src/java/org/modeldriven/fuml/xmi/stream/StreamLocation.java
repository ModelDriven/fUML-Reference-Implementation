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

import javax.xml.stream.Location;

/**
 * Stream location data necessary only because Sun's StAX implementation "loses" 
 * location data immediately after it is allocated. This class us used to 
 * copy location data and associate it with the concerned start-element.  
 * 
 * @author Scott Cinnamond
 */
public class StreamLocation implements Location {

	private int characterOffset = -1;
	private int columnNumber = -1;
	private int lineNumber = -1;
	private String publicId;
	private String systemId;

	@SuppressWarnings("unused")
	private StreamLocation() {}
	
	public StreamLocation(Location location) {
		this.characterOffset = location.getCharacterOffset();
		this.columnNumber = location.getColumnNumber();
		this.lineNumber = location.getLineNumber();
		this.publicId = location.getPublicId();
		this.systemId = location.getSystemId();
	}
	
	/**
	 * Return the byte or character offset into the input source this location
	 * is pointing to.
	 */
	public int getCharacterOffset() {
		return characterOffset;
	}

	/**
	 * Return the column number where the current event ends, returns -1 if none
	 * is available.
	 */
	public int getColumnNumber() {
		return columnNumber;
	}

	/**
	 * Return the line number where the current event ends, returns -1 if none
	 * is available.
	 */
	public int getLineNumber() {
		return lineNumber;
	}



	public String getPublicId() {
		// TODO Auto-generated method stub
		return publicId;
	}

	public String getSystemId() {
		// TODO Auto-generated method stub
		return systemId;
	}

}
