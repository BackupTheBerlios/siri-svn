//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.10.03 at 10:59:53 CEST 
//


package org.siri.feederconfig.xmlbinding;


/**
 * Java content class for emailsettingsType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/intellij_siri/modules/feeder/feederconfig/src/schema/feeder.xsd line 36)
 * <p>
 * <pre>
 * &lt;complexType name="emailsettingsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}emailbatchsize"/>
 *         &lt;element ref="{}emailsendintervallInMilliSeconds"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface EmailsettingsType {


    /**
     * Gets the value of the emailbatchsize property.
     * 
     */
    short getEmailbatchsize();

    /**
     * Sets the value of the emailbatchsize property.
     * 
     */
    void setEmailbatchsize(short value);

    /**
     * Gets the value of the emailsendintervallInMilliSeconds property.
     * 
     */
    long getEmailsendintervallInMilliSeconds();

    /**
     * Sets the value of the emailsendintervallInMilliSeconds property.
     * 
     */
    void setEmailsendintervallInMilliSeconds(long value);

}