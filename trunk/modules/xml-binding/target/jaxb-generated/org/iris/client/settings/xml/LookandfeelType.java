//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.27 at 10:55:28 CEST 
//


package org.iris.client.settings.xml;


/**
 * Java content class for lookandfeelType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/myprojects/siri/modules/xml-binding/src/schema/irisclient.xsd line 29)
 * <p>
 * <pre>
 * &lt;complexType name="lookandfeelType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}lookandfeelthemepath"/>
 *         &lt;element ref="{}type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface LookandfeelType {


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
     * Gets the value of the lookandfeelthemepath property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getLookandfeelthemepath();

    /**
     * Sets the value of the lookandfeelthemepath property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setLookandfeelthemepath(java.lang.String value);

}
