<?xml version="1.0" encoding="UTF-8"?>
<!--
-->
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:lxslt="http://xml.apache.org/xslt"
  xmlns:xmi="http://www.omg.org/spec/XMI/20131001"
  xmlns:uml="http://www.omg.org/spec/XMI/20131001"
  xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore">

<xsl:output method="text"
    indent="no"/>

<xsl:param name="pkg" />
<xsl:param name="cls" />
    
    <xsl:template match="/">
package <xsl:value-of select="$pkg" />;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fuml.syntax.classification.Classifier;
import fuml.syntax.classification.Generalization;
import fuml.syntax.classification.Property;
import fuml.syntax.packages.Package;
import fuml.syntax.simpleclassifiers.Enumeration;
import fuml.syntax.simpleclassifiers.EnumerationLiteral;
import fuml.syntax.simpleclassifiers.PrimitiveType;
import fuml.syntax.structuredclassifiers.Association;
import fuml.syntax.structuredclassifiers.Class_;
import fuml.syntax.values.LiteralInteger;
import fuml.syntax.values.LiteralUnlimitedNatural;
import fuml.syntax.values.ValueSpecification;

import UMLPrimitiveTypes.UnlimitedNatural;

import org.modeldriven.fuml.repository.RepositoryArtifact;
import org.modeldriven.fuml.repository.RepositoryMapping;
import org.modeldriven.fuml.repository.Repository;

import org.modeldriven.fuml.repository.config.Artifact;

// this needs to read the artifact namespace info from the content
public class <xsl:value-of select="$cls" /> extends ModelAssembler 
    implements RepositoryArtifact
{

    private static Log log = LogFactory.getLog(<xsl:value-of select="$cls" />.class);
    private ModelFactory factory;

    public <xsl:value-of select="$cls" />(Artifact artifact, RepositoryMapping mapping, Repository model) {
        super(artifact, mapping, model);
        this.factory = new ModelFactory(mapping, model);
        construct();
    }

    private void construct() {
        log.info("initializing...");
        constructPackages();
        constructPrimitiveTypes();
        constructEnumerations();
        constructClasses();
        constructProperties();
        constructGeneralizations();
        constructAssociations();
    } 

    public String getURN() {
        return this.artifact.getUrn();
    }
    
    public String getNamespaceURI() {
        return this.artifact.getNamespaceURI();
    }
    
    private void constructPackages()
    {
        Package pkg = null;
     <xsl:for-each select="//*[@xmi:type = 'uml:Package']">
        <xsl:variable name="packageName">                 
          <xsl:call-template name="findPackageName">                    
            <xsl:with-param name="pkg" select="''"/>       
            <xsl:with-param name="clss" select="."/>       
          </xsl:call-template>                                                    
        </xsl:variable>                                                  
        <xsl:variable name="qualifiedPackageName">
            <xsl:choose>                                                           
                <xsl:when test="$packageName != ''"> 
                    <xsl:value-of select="concat(concat($packageName, '.'), @name)"/>        
                </xsl:when>                                                    
                <xsl:otherwise>                                                
                    <xsl:value-of select="@name"/>        
                </xsl:otherwise>                                               
            </xsl:choose>                                   
        </xsl:variable>    
                     
        // <xsl:value-of select="$qualifiedPackageName"/>
        <xsl:choose>                                                           
            <xsl:when test="$packageName = ''">            
    	pkg  = factory.createPackage("<xsl:value-of select="@name" />", "<xsl:value-of select="$qualifiedPackageName" />", "<xsl:value-of select="@xmi:id" />", this); // root package
    	mapping.mapPackage(pkg, null, this); 
            </xsl:when>                                                        
            <xsl:otherwise>                                                    
    	pkg  = factory.createPackage("<xsl:value-of select="@name" />", "<xsl:value-of select="$qualifiedPackageName" />", "<xsl:value-of select="@xmi:id" />", pkg, this);
    	mapping.mapPackage(pkg, "<xsl:value-of select="$packageName" />", this); 
            </xsl:otherwise>                                                   
        </xsl:choose>
	 </xsl:for-each>
     }   

    private void constructPrimitiveTypes()
    {
    Package pkg = null;
    String packageId = null;
    PrimitiveType type = null;
    <xsl:for-each select="//packagedElement[@xmi:type = 'uml:PrimitiveType']">
        <xsl:variable name="packageName">                 
          <xsl:call-template name="findPackageName">                    
            <xsl:with-param name="pkg" select="''"/>       
            <xsl:with-param name="clss" select="."/>       
          </xsl:call-template>                                                    
        </xsl:variable>                                                  
        packageId = this.artifact.getUrn() + "#" + "<xsl:value-of select="../@xmi:id"/>";   
        
        // <xsl:value-of select="$packageName" />.<xsl:value-of select="@name" /> 
        pkg = (Package)model.getElementById(packageId).getDelegate();       
        
        // <xsl:value-of select="@name" />
    	type  = factory.createPrimitiveType("<xsl:value-of select="@name" />", "<xsl:value-of select="@xmi:id" />", pkg);
    	mapping.mapPrimitiveType(type, "<xsl:value-of select="$packageName" />", this); 
    </xsl:for-each>
    }   
       
    private void constructClasses()
    {
        Package pkg = null;
        String packageId = null;
        Class_ clss = null;
    <xsl:for-each select="//packagedElement[@xmi:type = 'uml:Class']">
        <xsl:variable name="classVar"
            select="concat('the', translate(@xmi:id,'-',''))">
        </xsl:variable>    

        <xsl:variable name="packageName">                 
          <xsl:call-template name="findPackageName">                    
            <xsl:with-param name="pkg" select="''"/>       
            <xsl:with-param name="clss" select="."/>       
          </xsl:call-template>                                                    
        </xsl:variable>                                                  
        packageId = this.artifact.getUrn() + "#" + "<xsl:value-of select="../@xmi:id"/>";   
                      
        // <xsl:value-of select="$packageName" />.<xsl:value-of select="@name" /> 
        pkg = (Package)model.getElementById(packageId).getDelegate();       
        //pkg = model.getPackageByQualifiedName("<xsl:value-of select="$packageName" />").getDelegate();       
    	clss  = factory.createClass("<xsl:value-of select="@name" />", "<xsl:value-of select="@xmi:id" />", pkg);
    	clss.isAbstract = <xsl:value-of select="boolean(@isAbstract)" />;
    	((Classifier)clss).isAbstract = <xsl:value-of select="boolean(@isAbstract)" />;
    	mapping.mapClass(clss, "<xsl:value-of select="$packageName" />", this); 
    </xsl:for-each>
    }   

    private void constructEnumerations()
    {
        Enumeration enumeration = null;
        EnumerationLiteral literal = null;
    <xsl:for-each select="//packagedElement[@xmi:type = 'uml:Enumeration']">
        <xsl:variable name="packageName">                 
          <xsl:call-template name="findPackageName">                    
            <xsl:with-param name="pkg" select="''"/>       
            <xsl:with-param name="clss" select="."/>       
          </xsl:call-template>                                                    
        </xsl:variable>                                                  
        // <xsl:value-of select="@name" />
    	enumeration  = factory.createEnumeration("<xsl:value-of select="@name" />", "<xsl:value-of select="@xmi:id" />");
    	mapping.mapEnumeration(enumeration, "<xsl:value-of select="$packageName" />", this); 
        <xsl:for-each select="ownedLiteral">
        literal = factory.createEnumerationLiteral(enumeration, 
            "<xsl:value-of select="@name" />",
            "<xsl:value-of select="@xmi:id" />");
    	mapping.mapEnumerationLiteral(literal, "<xsl:value-of select="$packageName" />", this); 
        </xsl:for-each>
    </xsl:for-each>
    }   

    private void constructProperties()
    {
        Class_ clss = null;
        Property prop = null;
        
    <xsl:for-each select="//packagedElement[@xmi:type = 'uml:Class']">
        // <xsl:value-of select="@name" />
    	clss  = (Class_)model.getElementById("<xsl:value-of select="@xmi:id" />").getDelegate();
    	<xsl:for-each select="ownedAttribute">
    	<xsl:variable name="typeAttribute">
    	    <xsl:value-of select="@type" />
    	</xsl:variable>
    	<xsl:variable name="typeElement">
    	    <xsl:value-of select="type/@href"/>
    	</xsl:variable>    	
    	<xsl:variable name="hasLowerValue">
    	    <xsl:value-of select="boolean(count(lowerValue)=1)"/>
    	</xsl:variable>    	
    	<xsl:variable name="hasUpperValue">
    	    <xsl:value-of select="boolean(count(upperValue)=1)"/>
    	</xsl:variable>    	
    	prop = factory.createProperty(clss, "<xsl:value-of select="@name" />", 
    	    "<xsl:value-of select="@xmi:id" />",
    	    "<xsl:if test="$typeAttribute != ''">
    	    <xsl:value-of select="$typeAttribute" />
    	</xsl:if>
    	<xsl:if test="$typeElement != ''">
    	    <xsl:value-of select="$typeElement" />
    	</xsl:if>",
    	    "<xsl:value-of select="@redefinedProperty" />",    	    
    		<xsl:value-of select="boolean(@isReadOnly)" />, <xsl:value-of select="boolean(@isDerived)" />, <xsl:value-of select="boolean(@isDerivedUnion)" />);    	
        factory.createLowerValue(prop, <xsl:value-of select="$hasLowerValue" />, "<xsl:value-of select="normalize-space(lowerValue/@value)" />");
        factory.createUpperValue(prop, <xsl:value-of select="$hasUpperValue" />, "<xsl:value-of select="normalize-space(upperValue/@value)" />");
    	mapping.mapProperty(clss, prop, this);    
     	
    	<xsl:for-each select="defaultValue">
            <xsl:variable name="val">
            <xsl:choose>                                                           
                <xsl:when test="@xmi:type = 'uml:LiteralBoolean'">new Boolean(<xsl:value-of select="boolean(@value)" />)</xsl:when>                                                        
                <xsl:when test="@xmi:type = 'uml:LiteralInteger'">new Integer(<xsl:choose>
                    <xsl:when test="@value != ''">
                        <xsl:value-of select="@value" />
                    </xsl:when>
                    <xsl:otherwise><xsl:value-of select="'0'" /></xsl:otherwise>
                    </xsl:choose>)
                </xsl:when>                                                        
                <xsl:when test="@xmi:type = 'uml:OpaqueExpression'">
                    <xsl:value-of select="./body/text()" />            
                </xsl:when>                                                        
                <xsl:when test="@xmi:type = 'uml:LiteralString'">new String("<xsl:value-of select="@value" />")</xsl:when>                                                        
                <xsl:otherwise>new String("<xsl:value-of select="@value" />")</xsl:otherwise>                                                   
            </xsl:choose>
            </xsl:variable>
     	factory.createDefault(prop,
    	   <xsl:value-of select="$val" />,
    	   "<xsl:value-of select="@instance" />",
    	   "<xsl:value-of select="@xmi:id" />",  
    	   "<xsl:value-of select="@xmi:type" />",  
    	   "<xsl:value-of select="@type" />");
    	</xsl:for-each>
    	
    	</xsl:for-each> <!-- ownedAttribute -->
    	
    </xsl:for-each>
    }
    
    private void constructGeneralizations()
    {
        Class_ clss = null;
        
    <xsl:for-each select="//packagedElement[@xmi:type = 'uml:Class']">
        // <xsl:value-of select="@name" />
    	clss  = (Class_)model.getElementById("<xsl:value-of select="@xmi:id" />").getDelegate();
    	<xsl:for-each select="generalization">
    	factory.createGeneralization(clss, "<xsl:value-of select="@general" />");
    	</xsl:for-each>    	
    	
    </xsl:for-each>
    }

    private void constructAssociations()
    {
        Package pkg = null;
        String packageId = null;
        Association assoc = null;
        Property prop = null;
        
    <xsl:for-each select="//packagedElement[@xmi:type = 'uml:Association']">
        <xsl:variable name="packageName">                 
          <xsl:call-template name="findPackageName">                    
            <xsl:with-param name="pkg" select="''"/>       
            <xsl:with-param name="clss" select="."/>       
          </xsl:call-template>                                                    
        </xsl:variable>                                                  
        packageId = this.artifact.getUrn() + "#" + "<xsl:value-of select="../@xmi:id"/>";   
                      
        // <xsl:value-of select="$packageName" />.<xsl:value-of select="@name" /> 
        pkg = (Package)model.getElementById(packageId).getDelegate();       
     	assoc  = factory.createAssociation("<xsl:value-of select="@name" />", "<xsl:value-of select="@xmi:id" />");
        mapping.mapAssociation(assoc, "<xsl:value-of select="$packageName" />", this);
        // create owned ends
    	<xsl:for-each select="ownedEnd">
    	<xsl:variable name="typeAttribute">
    	    <xsl:value-of select="@type" />
    	</xsl:variable>
    	<xsl:variable name="typeElement">
    	    <xsl:value-of select="type/@href"/>
    	</xsl:variable>    	
    	<xsl:variable name="hasLowerValue">
    	    <xsl:value-of select="boolean(count(lowerValue)=1)"/>
    	</xsl:variable>    	
    	<xsl:variable name="hasUpperValue">
    	    <xsl:value-of select="boolean(count(upperValue)=1)"/>
    	</xsl:variable>    	
    	prop = factory.createProperty(assoc, "<xsl:value-of select="@name" />", 
    	    "<xsl:value-of select="@xmi:id" />",
    	    "<xsl:if test="$typeAttribute != ''">
    	    <xsl:value-of select="$typeAttribute" />
    	</xsl:if>
    	<xsl:if test="$typeElement != ''">
    	    <xsl:value-of select="$typeElement" />
    	</xsl:if>",
    	    "<xsl:value-of select="@subsettedProperty" />",    	    
    	    "<xsl:value-of select="@redefinedProperty" />",    	    
    		<xsl:value-of select="boolean(@readOnly)" />, <xsl:value-of select="boolean(@derived)" />, <xsl:value-of select="boolean(@derivedUnion)" />);    	
        factory.createLowerValue(prop, <xsl:value-of select="$hasLowerValue" />, "<xsl:value-of select="normalize-space(lowerValue/@value)" />");
        factory.createUpperValue(prop, <xsl:value-of select="$hasUpperValue" />, "<xsl:value-of select="normalize-space(upperValue/@value)" />");
    	mapping.mapProperty(assoc, prop, this);
    	</xsl:for-each>    
        <xsl:choose>
            <xsl:when  test="@memberEnd">
        factory.createAssociationEnds(assoc, "<xsl:value-of select="@memberEnd" />");
            </xsl:when>
            <xsl:otherwise>
        factory.createAssociationEnds(assoc, "<xsl:for-each select="./memberEnd/@xmi:idref"><xsl:value-of select="concat(.,' ')"/> </xsl:for-each>");
            </xsl:otherwise>
        </xsl:choose>
    </xsl:for-each>
    }

}
    
    </xsl:template> <!-- root -->

    <xsl:template name="findPackageName">                                        
      <xsl:param name="pkg" />                                                   
      <xsl:param name="clss" />                                                  
                                                                                 
      <xsl:if test="$clss/../@xmi:type = 'uml:Package' or name($clss/..) = 'uml:Model'">                         
          <xsl:variable name="pkgvar">                                           
              <xsl:value-of select="concat($clss/../@name, $pkg)" />             
          </xsl:variable>                                                        
          <xsl:choose>                                                           
              <xsl:when test="$clss/../../@xmi:type = 'uml:Package' or name($clss/../..) = 'uml:Model'">            
                  <xsl:call-template name="findPackageName">                     
                    <xsl:with-param name="pkg" select="concat('.', $pkgvar)"/>   
                    <xsl:with-param name="clss" select="$clss/.."/>              
                  </xsl:call-template>                                                
              </xsl:when>                                                        
              <xsl:otherwise>                                                    
                  <xsl:value-of select="$pkgvar"/>                               
              </xsl:otherwise>                                                   
          </xsl:choose>                                                          
      </xsl:if>                                                                  
    </xsl:template>                                                              

    
</xsl:stylesheet>    
