/*
 * Copyright 2008 Lockheed Martin Corporation, except as stated in the file 
 * entitled Licensing-Information. 
 * 
 * Modifications:
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 * Copyright 2023 Model Driven Solutions, Inc.
 * 
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated 
 * in the file entitled Licensing-Information. 
 *
 * Contributors:
 *   DAT/MDS - initial API and implementation
 *   MDS - changed from javax.xml to jakarta.xml
 *
 */
package org.modeldriven.fuml.bind;

import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultValidationEventHandler implements BindingValidationEventHandler {
	
    private static Log log = LogFactory.getLog(DefaultValidationEventHandler.class);
    private int errorCount;
    private boolean cumulative = true;
	
	public int getErrorCount() {
		return errorCount;
	}

	public DefaultValidationEventHandler() {}	
	public DefaultValidationEventHandler(boolean cumulative) {
		this.cumulative = cumulative;
	}	
	
    public boolean handleEvent(ValidationEvent ve) {
        boolean result = this.cumulative;
        this.errorCount++;        
        ValidationEventLocator vel = ve.getLocator();
        
        String message = "Line:Col:Offset[" + vel.getLineNumber() + ":" + vel.getColumnNumber() + ":" 
            + String.valueOf(vel.getOffset())
            + "] - " + ve.getMessage();
        
        switch (ve.getSeverity()) {
        case ValidationEvent.WARNING:
            log.warn(message);
            break;
        case ValidationEvent.ERROR:
            log.error(message);
            break;
        case ValidationEvent.FATAL_ERROR:
            log.fatal(message);
            break;
        default:
            log.error(message);
        }
        return result;
    }
    
    public void reset()
    {
    	this.errorCount = 0;
    }

}
