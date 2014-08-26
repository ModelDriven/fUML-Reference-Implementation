/*
 * Initial version copyright 2008 Lockheed Martin Corporation, except
 * as stated in the file entitled Licensing-Information.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc.
 *
 * All modifications copyright 2009 Data Access Technologies, Inc. Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php), except as stated
 * in the file entitled Licensing-Information.
 *
 * Contributors:
 *   MDS - initial API and implementation
 *
 */

package org.modeldriven.fuml.xmi.validation;


/**
 * Represents the set of possible errors that may be incurred during XMI validation. Each
 * enumeration literal is linked by default to it's ErrorCategory as a
 * convenience for clients.
 *
 * @author Scott Cinnamond
 */
public enum ErrorCode {

	INVALID_REFERENCE("INVALID_REFERENCE", 			ErrorCategory.DOCUMENT_COMPLIANCE_ID_CORRELATION),
	INVALID_EXTERNAL_REFERENCE("INVALID_EXTERNAL_REFERENCE", ErrorCategory.DOCUMENT_COMPLIANCE_ID_CORRELATION),
	DUPLICATE_REFERENCE("DUPLICATE_REFERENCE", 		ErrorCategory.DOCUMENT_COMPLIANCE_ID_CORRELATION),
	UNDRESOLVED_URI("UNDRESOLVED_URI", 				ErrorCategory.DOCUMENT_COMPLIANCE_URI_CORRELATION),
	INVALID_NAMESPACE("INVALID_NAMESPACE", 			ErrorCategory.DOCUMENT_COMPLIANCE_NAMESPACE_CORRELATION),
	NAMESPACE_REQUIRED("NAMESPACE_REQUIRED", 		ErrorCategory.DOCUMENT_COMPLIANCE_NAMESPACE_CORRELATION),
	MALFORMED_DOCUMENT("MALFORMED_DOCUMENT", 		ErrorCategory.DOCUMENT_COMPLIANCE_WELL_FORMED),
	MALFORMED_ELEMENT("MALFORMED_ELEMENT", 			ErrorCategory.DOCUMENT_COMPLIANCE_WELL_FORMED),

	PROPERTY_REQUIRED("PROPERTY_REQUIRED", 			ErrorCategory.MODEL_COMPLIANCE_MULTIPLICITY),
	PROPERTY_RANGE_ERROR("PROPERTY_RANGE_ERROR", 	ErrorCategory.MODEL_COMPLIANCE_MULTIPLICITY),
	UNDEFINED_PROPERTY("UNDEFINED_PROPERTY", 		ErrorCategory.MODEL_COMPLIANCE_NAME_CORRELATION),
	UNDEFINED_CLASS("UNDEFINED_CLASS", 				ErrorCategory.MODEL_COMPLIANCE_NAME_CORRELATION),
	INVALID_TYPE("INVALID_TYPE", 					ErrorCategory.MODEL_COMPLIANCE_TYPE_CORRELATION),
	ABSTRACT_CLASS_INSTANTIATION("ABSTRACT_CLASS_INSTANTIATION", ErrorCategory.MODEL_COMPLIANCE_TYPE_CORRELATION),
	INVALID_STEREOTYPE_APPLICATION("INVALID_STEREOTYPE_APPLICATION", ErrorCategory.MODEL_COMPLIANCE_STEREOTYPE_CORRELATION),
	DERIVED_PROPERTY_INSTANTIATION("DERIVED_PROPERTY_INSTANTIATION", ErrorCategory.MODEL_COMPLIANCE_WELL_FORMED);

    private final String value;
    private final ErrorCategory category;

    public ErrorCategory getCategory() {
		return category;
	}

	private ErrorCode(String v, ErrorCategory category) {
        this.value = v;
        this.category = category;
    }

    public String value() {
        return value;
    }

    public static ErrorCode fromValue(String v) {
        for (ErrorCode c: ErrorCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
