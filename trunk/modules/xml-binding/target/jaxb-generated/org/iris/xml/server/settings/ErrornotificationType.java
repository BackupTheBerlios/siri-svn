//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.27 at 10:52:14 CEST 
//


package org.iris.xml.server.settings;


/**
 * Java content class for errornotificationType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/myprojects/siri/modules/xml-binding/src/schema/iris.xsd line 68)
 * <p>
 * <pre>
 * &lt;complexType name="errornotificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}notifyeveryintervallInMilliSeconds" minOccurs="0"/>
 *         &lt;element ref="{}numberoferrors" minOccurs="0"/>
 *         &lt;element name="emailnotification" type="{}emailnotificationType" minOccurs="0"/>
 *         &lt;element name="sms" type="{}smsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="errornotificationon" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ErrornotificationType {


    /**
     * Gets the value of the numberoferrors property.
     * 
     */
    short getNumberoferrors();

    /**
     * Sets the value of the numberoferrors property.
     * 
     */
    void setNumberoferrors(short value);

    /**
     * Gets the value of the notifyeveryintervallInMilliSeconds property.
     * 
     */
    long getNotifyeveryintervallInMilliSeconds();

    /**
     * Sets the value of the notifyeveryintervallInMilliSeconds property.
     * 
     */
    void setNotifyeveryintervallInMilliSeconds(long value);

    /**
     * Gets the value of the emailnotification property.
     * 
     * @return
     *     possible object is
     *     {@link org.iris.xml.server.settings.EmailnotificationType}
     */
    org.iris.xml.server.settings.EmailnotificationType getEmailnotification();

    /**
     * Sets the value of the emailnotification property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.iris.xml.server.settings.EmailnotificationType}
     */
    void setEmailnotification(org.iris.xml.server.settings.EmailnotificationType value);

    /**
     * Gets the value of the sms property.
     * 
     * @return
     *     possible object is
     *     {@link org.iris.xml.server.settings.SmsType}
     */
    org.iris.xml.server.settings.SmsType getSms();

    /**
     * Sets the value of the sms property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.iris.xml.server.settings.SmsType}
     */
    void setSms(org.iris.xml.server.settings.SmsType value);

    /**
     * Gets the value of the errornotificationon property.
     * 
     */
    boolean isErrornotificationon();

    /**
     * Sets the value of the errornotificationon property.
     * 
     */
    void setErrornotificationon(boolean value);

}
