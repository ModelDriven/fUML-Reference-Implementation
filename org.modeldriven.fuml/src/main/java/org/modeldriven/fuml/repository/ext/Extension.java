package org.modeldriven.fuml.repository.ext;

import fuml.syntax.commonstructure.Element;
import fuml.syntax.commonstructure.ElementList;
import fuml.syntax.structuredclassifiers.Class_;



public class Extension extends fuml.syntax.structuredclassifiers.Association {

	private ElementList relatedElement = new ElementList();
	private Class_ metaclass;
	private boolean isRequired = false;
	
	public ElementList getRelatedElement() {
		return relatedElement;
	}
	public void addRelatedElement(Element element) {
		relatedElement.add(element);
	}
	public Class_ getMetaclass() {
		return metaclass;
	}
	public void setMetaclass(Class_ metaclass) {
		this.metaclass = metaclass;
	}
	public boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	
} // Extension
