//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.27 at 10:52:14 CEST 
//


package org.iris.xml.server.settings;


/**
 * Java content class for nameserviceType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/myprojects/siri/modules/xml-binding/src/schema/iris.xsd line 140)
 * <p>
 * <pre>
 * &lt;complexType name="nameserviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}iorpath"/>
 *         &lt;element ref="{}objectname"/>
 *         &lt;element ref="{}objectmethod"/>
 *         &lt;element ref="{}paramtype"/>
 *         &lt;element ref="{}reconnectToObjectInMilliSeconds"/>
 *         &lt;element ref="{}reconnectToObjectPolicy"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface NameserviceType {


    /**
     * Gets the value of the iorpath property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getIorpath();

    /**
     * Sets the value of the iorpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setIorpath(java.lang.String value);

    /**
     * Gets the value of the objectname property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getObjectname();

    /**
     * Sets the value of the objectname property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setObjectname(java.lang.String value);

    /**
     * Gets the value of the reconnectToObjectPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getReconnectToObjectPolicy();

    /**
     * Sets the value of the reconnectToObjectPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setReconnectToObjectPolicy(java.lang.String value);

    /**
     * Gets the value of the paramtype property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getParamtype();

    /**
     * Sets the value of the paramtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setParamtype(java.lang.String value);

    /**
     * Gets the value of the objectmethod property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getObjectmethod();

    /**
     * Sets the value of the objectmethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setObjectmethod(java.lang.String value);

    /**
     * Gets the value of the reconnectToObjectInMilliSeconds property.
     * 
     */
    long getReconnectToObjectInMilliSeconds();

    /**
     * Sets the value of the reconnectToObjectInMilliSeconds property.
     * 
     */
    void setReconnectToObjectInMilliSeconds(long value);

}
