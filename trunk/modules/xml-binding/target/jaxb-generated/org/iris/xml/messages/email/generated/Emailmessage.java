//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.27 at 10:55:25 CEST 
//


package org.iris.xml.messages.email.generated;


/**
 * Java content class for emailmessage element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/gepo/myprojects/siri/modules/xml-binding/src/schema/email.xsd line 3)
 * <p>
 * <pre>
 * &lt;element name="emailmessage">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{}toaddress" maxOccurs="unbounded"/>
 *           &lt;element ref="{}ccaddress" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}bccaddress" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element ref="{}subject"/>
 *           &lt;element ref="{}bodymessage"/>
 *         &lt;/sequence>
 *         &lt;attribute name="contenttype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="messagecreatedate" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *         &lt;attribute name="messagename" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="messagetype" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="receiver" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="sender" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="smtpserver" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface Emailmessage
    extends javax.xml.bind.Element, org.iris.xml.messages.email.generated.EmailmessageType
{


}
