//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.10.03 at 10:59:53 CEST 
//


package org.siri.feederconfig.xmlbinding;


/**
 * Java content class for backupType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/intellij_siri/modules/feeder/feederconfig/src/schema/feeder.xsd line 17)
 * <p>
 * <pre>
 * &lt;complexType name="backupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}backupfolderpath"/>
 *         &lt;element ref="{}archivefolderpath"/>
 *         &lt;element ref="{}sortalpha" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="archiveron" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="backuphandleron" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface BackupType {


    /**
     * Gets the value of the backupfolderpath property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getBackupfolderpath();

    /**
     * Sets the value of the backupfolderpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setBackupfolderpath(java.lang.String value);

    /**
     * Gets the value of the backuphandleron property.
     * 
     */
    boolean isBackuphandleron();

    /**
     * Sets the value of the backuphandleron property.
     * 
     */
    void setBackuphandleron(boolean value);

    /**
     * Gets the value of the archivefolderpath property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getArchivefolderpath();

    /**
     * Sets the value of the archivefolderpath property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setArchivefolderpath(java.lang.String value);

    /**
     * Gets the value of the sortalpha property.
     * 
     */
    boolean isSortalpha();

    /**
     * Sets the value of the sortalpha property.
     * 
     */
    void setSortalpha(boolean value);

    /**
     * Gets the value of the archiveron property.
     * 
     */
    boolean isArchiveron();

    /**
     * Sets the value of the archiveron property.
     * 
     */
    void setArchiveron(boolean value);

}
