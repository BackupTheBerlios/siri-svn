//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.10.03 at 10:59:53 CEST 
//


package org.siri.feederconfig.xmlbinding;


/**
 * Java content class for archivehandlerType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/intellij_siri/modules/feeder/feederconfig/src/schema/feeder.xsd line 12)
 * <p>
 * <pre>
 * &lt;complexType name="archivehandlerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}CronJob"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ArchivehandlerType {


    /**
     * Gets the value of the cronJob property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getCronJob();

    /**
     * Sets the value of the cronJob property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setCronJob(java.lang.String value);

}