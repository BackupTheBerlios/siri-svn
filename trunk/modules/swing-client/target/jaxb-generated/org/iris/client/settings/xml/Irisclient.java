//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.3-b18-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2004.09.16 at 02:17:29 CEST
//


package org.iris.client.settings.xml;

/**
 * Java content class for irisclient element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/mycvsprojects/iris/modules/swing-client/src/schema/irisclient.xsd line 16)
 * <p>
 * <pre>
 * &lt;element name="irisclient">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="versioninfo" type="{}versioninfoType"/>
 *           &lt;element ref="{}title"/>
 *           &lt;element ref="{}pingservices"/>
 *           &lt;element name="lookandfeel" type="{}lookandfeelType" maxOccurs="unbounded"/>
 *           &lt;element name="irisserver" type="{}irisserverType" maxOccurs="unbounded"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 *
 */
public interface Irisclient
    extends javax.xml.bind.Element, org.iris.client.settings.xml.IrisclientType
{

}
