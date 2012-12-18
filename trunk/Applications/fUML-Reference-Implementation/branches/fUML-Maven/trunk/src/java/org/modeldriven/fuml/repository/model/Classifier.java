package org.modeldriven.fuml.repository.model;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Package;
import org.modeldriven.fuml.repository.RepositoryArtifact;

import fUML.Syntax.Classes.Kernel.DataType;



public class Classifier extends NamedElement 
    implements org.modeldriven.fuml.repository.Classifier {

	private fUML.Syntax.Classes.Kernel.Classifier _classifier;
	private org.modeldriven.fuml.repository.Package _package;
	    
    public Classifier(fUML.Syntax.Classes.Kernel.Classifier _classifier,
    		RepositoryArtifact artifact) {
    	super(_classifier, artifact);
    	this._classifier = _classifier;
    }
 
	public fUML.Syntax.Classes.Kernel.Classifier getDelegate() {
		return this._classifier;
	}
	
    public Package getPackage() {
    	if (this._package == null) {
    		this._package = 
    			new org.modeldriven.fuml.repository.model.Package(this._classifier.package_, 
    					this.artifact);	
    	}
    	return _package;
    }
    
    //public fUML.Syntax.Classes.Kernel.ClassifierList getGeneral() {
	//    return this._classifier.general;
    //}
    
    private List<org.modeldriven.fuml.repository.Classifier> general = new ArrayList<org.modeldriven.fuml.repository.Classifier>();
    public List<org.modeldriven.fuml.repository.Classifier> getGeneral() {
    	if (general.size() == 0 && this._classifier.general != null && this._classifier.general.size() > 0)
    	    for (int i = 0; i < this._classifier.general.size(); i++)
    	    	this.general.add(new Classifier(this._classifier.general.get(i), this.artifact));
    	return general;
    }
    
    private List<org.modeldriven.fuml.repository.Classifier> generalization = new ArrayList<org.modeldriven.fuml.repository.Classifier>();
    public List<org.modeldriven.fuml.repository.Classifier> getGeneralization() {
    	if (generalization.size() == 0 && this._classifier.generalization != null && this._classifier.generalization.size() > 0)
    	    for (int i = 0; i < this._classifier.generalization.size(); i++)
    	    	this.generalization.add(new Classifier(this._classifier.generalization.get(i).general, 
    	    			this.artifact));
    	return generalization;
    }   
    
    public boolean isAbstract() {
    	return this._classifier.isAbstract;
    }

	public boolean isDataType() {
		return this._classifier instanceof DataType;
	}
    
    
	
} // Classifier
