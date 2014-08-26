package org.modeldriven.fuml;

public enum FumlSystemProperty {
    
	CONFIG("fuml.configuration");
    
    private String property;
    private FumlSystemProperty(String property) {
    	this.property = property;
    }
    
    /**
     * Returns the Java system property name.    
     * @return the Java system property name
     */
	public String getProperty() {
		return this.property;
	}
    
}
