//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.3-b18-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2004.09.16 at 02:17:29 CEST
//


package org.iris.client.settings.xml;

/**
 * Java content class for versioninfoType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/mycvsprojects/iris/modules/swing-client/src/schema/irisclient.xsd line 77)
 * <p>
 * <pre>
 * &lt;complexType name="versioninfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}version"/>
 *         &lt;element ref="{}releasedate"/>
 *         &lt;element ref="{}developer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 */
public interface VersioninfoType
{

    /**
     * Gets the value of the developer property.
     *
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getDeveloper();

    /**
     * Sets the value of the developer property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setDeveloper(java.lang.String value);

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getVersion();

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setVersion(java.lang.String value);

    /**
     * Gets the value of the releasedate property.
     *
     * @return
     *     possible object is
     *     {@link java.util.Calendar}
     */
    java.util.Calendar getReleasedate();

    /**
     * Sets the value of the releasedate property.
     *
     * @param value
     *     allowed object is
     *     {@link java.util.Calendar}
     */
    void setReleasedate(java.util.Calendar value);

}
