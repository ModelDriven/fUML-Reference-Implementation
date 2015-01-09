package org.modeldriven.fuml.repository;

import java.util.List;



public interface Repository {

	public Repository INSTANCE = org.modeldriven.fuml.repository.model.InMemoryRepository.getInstance();
	
	public String getDefaultUMLNamespaceURI();
	public Element getElementById(String id);
	public Element findElementById(String id);
	public Element getElementByName(String name);
	public Element findElementByName(String name);
	public Element getElementByQualifiedName(String qualifiedName);
	public Element findElementByQualifiedName(String qualifiedName);
	public Classifier getClassifier(String name);
	public Classifier findClassifier(String name);
	public Classifier getClassifierByName(String name);
	public Classifier getClassifierByQualifiedName(String qualifiedName);
	public Package getPackageByQualifiedName(String qualifiedName);
	public String getJavaPackageNameForClass(Classifier classifier);
	public String findJavaPackageNamePackageForClass(Classifier classifier);
	public boolean isIgnoredClassifier(Classifier classifier);
    public List<Extension> getExtensions(Element element);
    public List<Stereotype> getStereotypes(Element element);
    public List<Stereotype> getStereotypes(Class<?> clss);
    public List<Stereotype> getAllStereotypes();
	
	public RepositoryMapping getMapping();
	public void loadClass(Class_ clss);
		
	public int getElementCount(Class<? extends fUML.Syntax.Classes.Kernel.Element> clss);
	public String[] getElementNames(Class<? extends fUML.Syntax.Classes.Kernel.Element> clss);

}
