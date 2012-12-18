<?xml version="1.0" encoding="UTF-8"?>
<!--
-->
<xsl:stylesheet version="2.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:lxslt="http://xml.apache.org/xslt">

<xsl:output method="xml"
    indent="yes"/>
    
    <xsl:template match="ownedComment">
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="'ownedComment'"/>
        <xsl:for-each select="@*">
            <xsl:choose>
              <xsl:when test="name() != 'body'">
                <xsl:value-of select="' '"/>
                <xsl:value-of select="name()"/>
                <xsl:value-of select="'='"/>
                <xsl:text disable-output-escaping="yes">&quot;</xsl:text>
                <xsl:value-of select="."/>
                <xsl:text disable-output-escaping="yes">&quot;</xsl:text>
              </xsl:when>      
              <xsl:otherwise>                               
              </xsl:otherwise>                               
            </xsl:choose>   
        </xsl:for-each>       
        <xsl:text disable-output-escaping="yes">&gt;</xsl:text> 
        
        <xsl:text disable-output-escaping="yes">&lt;</xsl:text><xsl:value-of select="'body'"/><xsl:text disable-output-escaping="yes">&gt;</xsl:text> 
        <xsl:value-of select="@*[local-name() = 'body']"/>
        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="'body'"/><xsl:text disable-output-escaping="yes">&gt;</xsl:text>

        <xsl:text disable-output-escaping="yes">&lt;/</xsl:text><xsl:value-of select="'ownedComment'"/><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
    </xsl:template>
    
    <xsl:template match="*">
        <xsl:copy>
          <xsl:copy-of select="@*"/>
          <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>
        
</xsl:stylesheet>    
