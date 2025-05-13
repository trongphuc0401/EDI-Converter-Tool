<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/EDIFACT">
        <Interchange>
            <xsl:apply-templates select="Segment[@name='UNB']"/>
            <Messages>
                <xsl:apply-templates select="Segment[@name='UNH']"/>
            </Messages>
            <xsl:apply-templates select="Segment[@name='UNZ']"/>
        </Interchange>
    </xsl:template>

    <xsl:template match="Segment[@name='UNB']">
        <Header>
            <SyntaxIdentifier><xsl:value-of select="substring-before(substring-after(., '+'), '+')"/></SyntaxIdentifier>
            <Sender><xsl:value-of select="substring-before(substring-after(., '+UNOC:3+'), '+')"/></Sender>
            <Receiver><xsl:value-of select="substring-before(substring-after(., '+UNOC:3+1234567890123:14+'), '+')"/></Receiver>
        </Header>
    </xsl:template>

    <xsl:template match="Segment[@name='UNH']">
        <Message>
            <Header>
                <ReferenceNumber><xsl:value-of select="substring-before(substring-after(., '+'), '+')"/></ReferenceNumber>
                <Type><xsl:value-of select="substring-after(., '+000000001+')"/></Type>
            </Header>
            <xsl:apply-templates select="following-sibling::Segment[preceding-sibling::Segment[@name='UNH'][1] = current() and @name != 'UNT']"/>
            <xsl:apply-templates select="following-sibling::Segment[@name='UNT'][preceding-sibling::Segment[@name='UNH'][1] = current()]"/>
        </Message>
    </xsl:template>

    <xsl:template match="Segment[@name='BGM']">
        <Document>
            <NameCode><xsl:value-of select="substring-before(substring-after(., '+'), '+')"/></NameCode>
            <Number><xsl:value-of select="substring-before(substring-after(., '+380+'), '+')"/></Number>
        </Document>
    </xsl:template>

    <xsl:template match="Segment[@name='UNT']">
        <Trailer>
            <SegmentCount><xsl:value-of select="substring-before(substring-after(., '+'), '+')"/></SegmentCount>
        </Trailer>
    </xsl:template>

    <xsl:template match="Segment[@name='UNZ']">
        <Trailer>
            <MessageCount><xsl:value-of select="substring-before(substring-after(., '+'), '+')"/></MessageCount>
        </Trailer>
    </xsl:template>

    <xsl:template match="Segment">
        <Segment name="{@name}"><xsl:value-of select="."/></Segment>
    </xsl:template>
</xsl:stylesheet>