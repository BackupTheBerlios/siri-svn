//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.10.03 at 10:59:53 CEST 
//


package org.siri.feederconfig.xmlbinding;


/**
 * Java content class for global-settingsType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/intellij_siri/modules/feeder/feederconfig/src/schema/feeder.xsd line 77)
 * <p>
 * <pre>
 * &lt;complexType name="global-settingsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ArchiveHandler" type="{}archivehandlerType"/>
 *         &lt;element name="emailserversettings" type="{}emailserversettingsType"/>
 *         &lt;element ref="{}servicerootpath"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface GlobalSettingsType {


    /**
     * Gets the value of the archiveHandler property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.ArchivehandlerType}
     */
    org.siri.feederconfig.xmlbinding.ArchivehandlerType getArchiveHandler();

    /**
     * Sets the value of the archiveHandler property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.ArchivehandlerType}
     */
    void setArchiveHandler(org.siri.feederconfig.xmlbinding.ArchivehandlerType value);

    /**
     * Gets the value of the emailserversettings property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.EmailserversettingsType}
     */
    org.siri.feederconfig.xmlbinding.EmailserversettingsType getEmailserversettings();

    /**
     * Sets the value of the emailserversettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.EmailserversettingsType}
     */
    void setEmailserversettings(org.siri.feederconfig.xmlbinding.EmailserversettingsType value);

    /**
     * Gets the value of the servicerootpath property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getServicerootpath();

    /**
     * Sets the value of the servicerootpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setServicerootpath(java.lang.String value);

}