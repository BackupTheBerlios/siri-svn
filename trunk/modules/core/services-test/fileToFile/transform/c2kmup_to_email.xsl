<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <emailmessage xmlns="http://www.descartes.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  messagetype="email" contenttype="text/plain" messagecreatedate="1999-05-31T13:20:00.000-05:00" sender="C2KTestInterCDMP" receiver="C2KTestInterCDMPSyntegra" smtpserver="tdvmail1" messagename="c2krmp_bacargo">
	<toaddress>georges.polyzois@tradevision.net</toaddress>
	<ccaddress>georges.polyzois@tradevision.net</ccaddress>
	<subject>TEST C2K MUP MESSAGE</subject>
	<bodymessage>MUP,<xsl:value-of select="/C2kMilestoneUpdate/@version"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/Ref/CDMP"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/Ref/Reference"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/Ref/Timestamp"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/AirlinePrefix"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/AWBSerialNumber"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/Milestone"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/MilestoneStation"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/Carrier"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/FlightNumber"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/PiecesReported"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/WeightReported/Amount"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/WeightReported/Code"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/VolumeReported/Amount"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/VolumeReported/Code"/>,	
	<xsl:value-of select="/C2kMilestoneUpdate/MessageReceiptTime"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/MilestoneReportedTime"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/CompletionIndicator"/>,
	<xsl:value-of select="/C2kMilestoneUpdate/StatusMessage"/>
	</bodymessage>
</emailmessage>
    </xsl:template>   
</xsl:stylesheet>