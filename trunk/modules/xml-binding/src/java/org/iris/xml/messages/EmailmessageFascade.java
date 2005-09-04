package org.iris.xml.messages;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.iris.xml.messages.email.generated.Emailmessage;

/**
 * Hides all nasty jaxb stuff from user of xml based email messages.
 *
 */
public class EmailmessageFascade
{
    Logger myLogger = Logger.getLogger(EmailmessageFascade.class.getName());
    private javax.xml.parsers.DocumentBuilderFactory factory;
    private javax.xml.parsers.DocumentBuilder builder;
    private Unmarshaller unmarshaller;
    private static EmailmessageFascade myInstance;
    private final String JAXBGENERATED_PACKAGE = "org.iris.xml.messages.email.generated";

    private EmailmessageFascade()
    {
        try
        {
            factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware( true );
            builder = factory.newDocumentBuilder();
            JAXBContext jc = JAXBContext.newInstance( JAXBGENERATED_PACKAGE);
            unmarshaller = jc.createUnmarshaller();
        }
        catch (Exception ex)
        {
            myLogger.error( ex );
        }
        myInstance = this;
    }

    public static EmailmessageFascade getInstance()
    {
        if (myInstance == null)
        {
            myInstance = new EmailmessageFascade();
        }
        return myInstance;
    }


    /**
     * Unmarshal and get email message object created by jaxb. If some exception occurs it is logged
     * and a exception is thrown to caller.
     *
     * @param document  the xml document to be unmarshalled
     * @return  Emailmessage represetntation of xml document
     * @throws EmailMessageException  thrown when unmarshalling document fails
     */
    public Emailmessage getEmailmessage(Document document)    throws EmailMessageException
    {
        Emailmessage result = null;
        try
        {
            result = (Emailmessage) unmarshaller.unmarshal(document);
        }
        catch (Exception exc)
        {
            myLogger.debug( "Probably failed to unmarshal xml document", exc );
        }
        return result;
    }

}