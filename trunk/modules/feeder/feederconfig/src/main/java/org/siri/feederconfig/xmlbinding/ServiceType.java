//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.10.03 at 10:59:53 CEST 
//


package org.siri.feederconfig.xmlbinding;


/**
 * Java content class for serviceType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/intellij_siri/modules/feeder/feederconfig/src/schema/feeder.xsd line 176)
 * <p>
 * <pre>
 * &lt;complexType name="serviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="poll" type="{}pollType" minOccurs="0"/>
 *         &lt;element name="infolder" type="{}infolderType" minOccurs="0"/>
 *         &lt;element name="emailsettings" type="{}emailsettingsType" minOccurs="0"/>
 *         &lt;element name="outfolder" type="{}outfolderType" minOccurs="0"/>
 *         &lt;element name="backup" type="{}backupType" minOccurs="0"/>
 *         &lt;element name="transform" type="{}transformType" minOccurs="0"/>
 *         &lt;element name="error" type="{}errorType"/>
 *         &lt;element name="inactivity" type="{}inactivityType"/>
 *         &lt;element name="nameservice" type="{}nameserviceType" minOccurs="0"/>
 *         &lt;element name="applicationlogger" type="{}loggingType"/>
 *         &lt;element name="messagelogger" type="{}loggingType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="createuniquename" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="usesrelativerootpath" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ServiceType {


    /**
     * Gets the value of the createuniquename property.
     * 
     */
    boolean isCreateuniquename();

    /**
     * Sets the value of the createuniquename property.
     * 
     */
    void setCreateuniquename(boolean value);

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getType();

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setType(java.lang.String value);

    /**
     * Gets the value of the emailsettings property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.EmailsettingsType}
     */
    org.siri.feederconfig.xmlbinding.EmailsettingsType getEmailsettings();

    /**
     * Sets the value of the emailsettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.EmailsettingsType}
     */
    void setEmailsettings(org.siri.feederconfig.xmlbinding.EmailsettingsType value);

    /**
     * Gets the value of the backup property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.BackupType}
     */
    org.siri.feederconfig.xmlbinding.BackupType getBackup();

    /**
     * Sets the value of the backup property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.BackupType}
     */
    void setBackup(org.siri.feederconfig.xmlbinding.BackupType value);

    /**
     * Gets the value of the usesrelativerootpath property.
     * 
     */
    boolean isUsesrelativerootpath();

    /**
     * Sets the value of the usesrelativerootpath property.
     * 
     */
    void setUsesrelativerootpath(boolean value);

    /**
     * Gets the value of the transform property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.TransformType}
     */
    org.siri.feederconfig.xmlbinding.TransformType getTransform();

    /**
     * Sets the value of the transform property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.TransformType}
     */
    void setTransform(org.siri.feederconfig.xmlbinding.TransformType value);

    /**
     * Gets the value of the poll property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.PollType}
     */
    org.siri.feederconfig.xmlbinding.PollType getPoll();

    /**
     * Sets the value of the poll property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.PollType}
     */
    void setPoll(org.siri.feederconfig.xmlbinding.PollType value);

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.ErrorType}
     */
    org.siri.feederconfig.xmlbinding.ErrorType getError();

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.ErrorType}
     */
    void setError(org.siri.feederconfig.xmlbinding.ErrorType value);

    /**
     * Gets the value of the messagelogger property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.LoggingType}
     */
    org.siri.feederconfig.xmlbinding.LoggingType getMessagelogger();

    /**
     * Sets the value of the messagelogger property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.LoggingType}
     */
    void setMessagelogger(org.siri.feederconfig.xmlbinding.LoggingType value);

    /**
     * Gets the value of the outfolder property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.OutfolderType}
     */
    org.siri.feederconfig.xmlbinding.OutfolderType getOutfolder();

    /**
     * Sets the value of the outfolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.OutfolderType}
     */
    void setOutfolder(org.siri.feederconfig.xmlbinding.OutfolderType value);

    /**
     * Gets the value of the inactivity property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.InactivityType}
     */
    org.siri.feederconfig.xmlbinding.InactivityType getInactivity();

    /**
     * Sets the value of the inactivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.InactivityType}
     */
    void setInactivity(org.siri.feederconfig.xmlbinding.InactivityType value);

    /**
     * Gets the value of the applicationlogger property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.LoggingType}
     */
    org.siri.feederconfig.xmlbinding.LoggingType getApplicationlogger();

    /**
     * Sets the value of the applicationlogger property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.LoggingType}
     */
    void setApplicationlogger(org.siri.feederconfig.xmlbinding.LoggingType value);

    /**
     * Gets the value of the nameservice property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.NameserviceType}
     */
    org.siri.feederconfig.xmlbinding.NameserviceType getNameservice();

    /**
     * Sets the value of the nameservice property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.NameserviceType}
     */
    void setNameservice(org.siri.feederconfig.xmlbinding.NameserviceType value);

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setId(java.lang.String value);

    /**
     * Gets the value of the infolder property.
     * 
     * @return
     *     possible object is
     *     {@link org.siri.feederconfig.xmlbinding.InfolderType}
     */
    org.siri.feederconfig.xmlbinding.InfolderType getInfolder();

    /**
     * Sets the value of the infolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.siri.feederconfig.xmlbinding.InfolderType}
     */
    void setInfolder(org.siri.feederconfig.xmlbinding.InfolderType value);

}
