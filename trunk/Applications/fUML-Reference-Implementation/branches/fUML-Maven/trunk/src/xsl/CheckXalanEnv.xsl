<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:xalan="http://xml.apache.org/xalan"
    exclude-result-prefixes="xalan">
<xsl:output indent="yes"/>

<xsl:template match="/">
  <out>
    <xsl:copy-of select="xalan:checkEnvironment()"/>
  </out>
</xsl:template>
</xsl:stylesheet>
