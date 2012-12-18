<?xml version="1.0" encoding="UTF-8"?>
<!--
-->
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:lxslt="http://xml.apache.org/xslt"
  xmlns:xmi="http://www.omg.org/spec/XMI/20110701"
  xmlns:uml="http://www.omg.org/spec/UML/20110701"
  xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore">

<xsl:output method="text"
    indent="no"/>

<xsl:param name="pkg" />
<xsl:param name="cls" />
    
    <xsl:template match="/">
package <xsl:value-of select="$pkg" />;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fUML.Syntax.Classes.Kernel.Association;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Generalization;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.LiteralInteger;
import fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural;
import fUML.Syntax.Classes.Kernel.PrimitiveType;
import UMLPrimitiveTypes.UnlimitedNatural;
import fUML.Syntax.Classes.Kernel.ValueSpecification;
import fUML.Syntax.Classes.Kernel.Enumeration;
import fUML.Syntax.Classes.Kernel.EnumerationLiteral;
import fUML.Syntax.Classes.Kernel.Package;
import fUML.Syntax.Classes.Kernel.Property;

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
     <xsl:for-each select="//packagedElement[@xmi:type = 'uml:Package']">
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
        <xsl:for-each select="packageMerge">
        
         <xsl:variable name="localMergedPackage" select="@mergedPackage"/>                 
         <xsl:variable name="remoteMergedPackage" select="./mergedPackage/@href"/>                 
        <xsl:choose>                                                           
            <xsl:when test="$localMergedPackage != ''">            
        mapping.mapPackageMerge(pkg, "<xsl:value-of select="$localMergedPackage" />");
            </xsl:when>                                                        
            <xsl:otherwise>                                                    
        mapping.mapPackageMerge(pkg, "<xsl:value-of select="$remoteMergedPackage" />");
            </xsl:otherwise>                                                   
        </xsl:choose>
       
        </xsl:for-each>                                                          
    </xsl:for-each>
    }   

    private void constructPrimitiveTypes()
    {
        PrimitiveType type = null;
    <xsl:for-each select="//packagedElement[@xmi:type = 'uml:PrimitiveType']">
        <xsl:variable name="packageName">                 
          <xsl:call-template name="findPackageName">                    
            <xsl:with-param name="pkg" select="''"/>       
            <xsl:with-param name="clss" select="."/>       
          </xsl:call-template>                                                    
        </xsl:variable>                                                  
        // <xsl:value-of select="@name" />
    	type  = factory.createPrimitiveType("<xsl:value-of select="@name" />", "<xsl:value-of select="@xmi:id" />");
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
    		<xsl:value-of select="boolean(@readOnly)" />, <xsl:value-of select="boolean(@derived)" />, <xsl:value-of select="boolean(@derivedUnion)" />);    	
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
