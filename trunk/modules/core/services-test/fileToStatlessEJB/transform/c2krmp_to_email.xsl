<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
    <xsl:output method="xml"/>
    <xsl:template match="/">
        <emailmessage xmlns="http://www.descartes.com" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  messagetype="email" contenttype="text/plain" messagecreatedate="1999-05-31T13:20:00.000-05:00" sender="C2KTestInterCDMP" receiver="C2KTestInterCDMPSyntegra" smtpserver="tdvmail1" messagename="c2krmp_bacargo">
	<toaddress>georges.polyzois@tradevision.net</toaddress>
	<ccaddress>georges.polyzois@tradevision.net</ccaddress>
	<subject>TEST C2K RMP MESSAGE </subject> 
	<bodymessage>
	RMP,
	<xsl:value-of select="/C2kRouteMap/@version"/>,
	S,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/AirlinePrefix"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/AWBSerialNumber"/>,
	<xsl:value-of select="/C2kRouteMap/Ref/CDMP"/>,
	<xsl:value-of select="/C2kRouteMap/Ref/Reference"/>,
	<xsl:value-of select="/C2kRouteMap/Ref/Timestamp"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/forwarder"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/Stn_receipt"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/Stn_delivery"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/TotalPieces"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/Weight/Amount"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/Weight/Code"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/Volume/Amount"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/Volume/Code"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/ServiceLevel"/>,
	<xsl:value-of select="/C2kRouteMap/C2kRMPSummary/PlanNumber"/>,
	,<!-- product code not specified in schema-->

	<xsl:for-each select="C2kRouteMap/C2kRMPDetail">
	------------------- START DETAIL --------------------
	D,C2kRouteMapC2kRMPDetail
	<xsl:value-of select="Milestone"/>,
	<xsl:value-of select="ChangeStatus"/>,
	<xsl:value-of select="SetupCount"/>,
	<xsl:value-of select="FlightOrigin"/>,
	<xsl:value-of select="FlightDestination"/>,
	<xsl:value-of select="Carrier"/>,
	<xsl:value-of select="FlightNumber"/>,
	<xsl:value-of select="Pieces"/>,
	<xsl:value-of select="WeightWithTolerance/Weight/Amount"/>,
	<xsl:value-of select="WeightWithTolerance/Weight/Code"/>,
	<xsl:value-of select="WeightWithTolerance/WeightTolerance/Amount"/>,
	<xsl:value-of select="WeightWithTolerance/WeightTolerance/PercentOrValue"/>,
	<xsl:value-of select="WeightWithTolerance/WeightTolerance/ToleranceApplication"/>,
	<xsl:value-of select="VolumeWithTolerance/VolumeTolerance/Amount"/>,
	<xsl:value-of select="VolumeWithTolerance/VolumeTolerance/Code"/>,
	<xsl:value-of select="VolumeWithTolerance/VolumeTolerance/Amount"/>,
	<xsl:value-of select="VolumeWithTolerance/VolumeTolerance/PercentOrValue"/>,
	<xsl:value-of select="VolumeWithTolerance/VolumeTolerance/ToleranceApplication"/>,
	<xsl:value-of select="Leg"/>,
	<xsl:value-of select="PlanTime"/>
	------------------- END DETAIL --------------------

		</xsl:for-each>
	</bodymessage>
</emailmessage>
    </xsl:template>   
</xsl:stylesheet>

