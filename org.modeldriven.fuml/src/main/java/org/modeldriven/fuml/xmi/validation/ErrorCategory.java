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

/**
 * Represents various error groupings or categorizations across both XMI
 * document level compliance and UML model compliance.
 * 
 * @author Scott Cinnamond
 */
public enum ErrorCategory {

    MODEL_COMPLIANCE_WELL_FORMED("MODEL_COMPLIANCE_WELL_FORMED"), // catch-all
                                                                  // for
                                                                  // model-compliance
    MODEL_COMPLIANCE_MULTIPLICITY("MODEL_COMPLIANCE_MULTIPLICITY"), MODEL_COMPLIANCE_NAME_CORRELATION(
            "MODEL_COMPLIANCE_NAME_CORRELATION"), MODEL_COMPLIANCE_TYPE_CORRELATION(
            "MODEL_COMPLIANCE_TYPE_CORRELATION"), MODEL_COMPLIANCE_STEREOTYPE_CORRELATION(
            "MODEL_COMPLIANCE_STEREOTYPE_CORRELATION"), DOCUMENT_COMPLIANCE_WELL_FORMED(
            "DOCUMENT_COMPLIANCE_WELL_FORMED"), DOCUMENT_COMPLIANCE_ID_CORRELATION(
            "DOCUMENT_COMPLIANCE_ID_CORRELATION"), DOCUMENT_COMPLIANCE_URI_CORRELATION(
            "DOCUMENT_COMPLIANCE_URI_CORRELATION"), DOCUMENT_COMPLIANCE_NAMESPACE_CORRELATION(
            "DOCUMENT_COMPLIANCE_NAMESPACE_CORRELATION"); // catch-all for
                                                          // document-compliance

    private final String value;

    ErrorCategory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorCategory fromValue(String v) {
        for (ErrorCategory c : ErrorCategory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
