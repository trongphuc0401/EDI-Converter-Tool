<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>

    <xsl:template match="/Interchange">
        <xsl:text>UNA:+.? '</xsl:text>
        <xsl:apply-templates select="Header"/>
        <xsl:apply-templates select="Messages/Message"/>
        <xsl:apply-templates select="Trailer"/>
    </xsl:template>

    <xsl:template match="Header">
        <xsl:text>UNB+</xsl:text>
        <xsl:value-of select="SyntaxIdentifier"/>
        <xsl:text>+</xsl:text>
        <xsl:value-of select="Sender"/>
        <xsl:text>+</xsl:text>
        <xsl:value-of select="Receiver"/>
        <xsl:text>+250513:1430+INV001++1'</xsl:text>
    </xsl:template>

    <xsl:template match="Message">
        <xsl:text>UNH+</xsl:text>
        <xsl:value-of select="Header/ReferenceNumber"/>
        <xsl:text>+</xsl:text>
        <xsl:value-of select="Header/Type"/>
        <xsl:text>'</xsl:text>
        <xsl:apply-templates select="Document"/>
        <xsl:apply-templates select="Trailer"/>
    </xsl:template>

    <xsl:template match="Document">
        <xsl:text>BGM+</xsl:text>
        <xsl:value-of select="NameCode"/>
        <xsl:text>+</xsl:text>
        <xsl:value-of select="Number"/>
        <xsl:text>+9'</xsl:text>
    </xsl:template>

    <xsl:template match="Message/Trailer">
        <xsl:text>UNT+</xsl:text>
        <xsl:value-of select="SegmentCount"/>
        <xsl:text>+</xsl:text>
        <xsl:value-of select="../Header/ReferenceNumber"/>
        <xsl:text>'</xsl:text>
    </xsl:template>

    <xsl:template match="Interchange/Trailer">
        <xsl:text>UNZ+</xsl:text>
        <xsl:value-of select="MessageCount"/>
        <xsl:text>+INV001'</xsl:text>
    </xsl:template>
</xsl:stylesheet>