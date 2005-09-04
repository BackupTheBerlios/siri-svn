package org.iris.server.messagehandlers.email;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.iris.server.messagehandlers.MessageHandlerException;
import org.iris.server.messagehandlers.MessageHandlerFactory;
import org.iris.server.util.xml.XMLHelper;
import org.iris.server.util.filehelpers.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.TimerTask;

/**
 * This thread just pops messages from inqueue and calls the messagehandlerfactory to handle email type
 * of messages in this context.
 *
 * @author Georges Polyzois
 *
 */
public class EmailQueueHandler
        extends TimerTask
{
    Logger myMessageLogger;
    Logger myApplicationLogger;
    private EmailQueue myEmailQueue = new EmailQueue();
    private boolean errorHandlerOn;
    private String errorFolderName;
    private String ndcName;
    private long emailbatchSize;

    public EmailQueueHandler(boolean errorHandlerOn, String errorFolderName, String ndcName, long emailbatchSize,
                             Logger messageLogger, Logger applicationLogger)
    {
        this.errorHandlerOn = errorHandlerOn;
        this.errorFolderName = errorFolderName;
        this.ndcName = ndcName ;
        this.emailbatchSize = emailbatchSize;
        this.myMessageLogger = messageLogger;
        this.myApplicationLogger = applicationLogger;
    }

    /**
     * Implemented method in TimerTask which runs and pops a collection of email documents from our email queue.
     *
     */
    public void run()
    {
        NDC.push(ndcName);
        EmailQueueItem[] items = myEmailQueue.popCollection((int)emailbatchSize);
        if ( items.length == 0 )
        {
            myApplicationLogger.debug( "No messages for email sending" );
            NDC.pop();
            return;
        }
        for (int i = 0; i < items.length; i++)
        {
            onMessage(items[i]);
        }
        NDC.pop();
    }

    /**
     * Parses the message document and calls the MessageHandlerFactory to handle the email document.
     *
     * @param item
     */
    private void onMessage(EmailQueueItem item)
    {
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        Document document = null;
        try
        {
            factory.setNamespaceAware(false);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            myApplicationLogger.debug("Got message on inqueue "); //+ item.getMessage());
            InputSource source = new InputSource(new StringReader(item.getMessage()));
            document = builder.parse(source);
            Node firstchild = document.getFirstChild();
            myApplicationLogger.debug( firstchild );
            NamedNodeMap attributes = firstchild.getAttributes();
            Node messagetype = attributes.getNamedItem("messagetype");
            if ( messagetype != null && ! messagetype.equals(""))
            {
                //myApplicationLogger.debug( "messagetype : " + messagetype.getNodeValue() );
                MessageHandlerFactory.createHandler(messagetype.getNodeValue()).handleMessage(document, item, myApplicationLogger);
                myMessageLogger.info( "Email sucessfully sent\n" + XMLHelper.getXML( document ));
            }
        }
        catch (MessageHandlerException exe)
        {
            try
            {
                myApplicationLogger.error( "Email not sent. " +  exe.getMessage(),exe);
                myMessageLogger.info( "Email not sent\n"  + XMLHelper.getXML( document ));
            }
            catch (Exception e)
            {
                myApplicationLogger.debug("Caught exception: ", e);
                myMessageLogger.info( "Email not sent. " + exe.getMessage() +  "\n"  + item.getMessage() );
            }

        }
        catch (Exception exe)
        {

            try
            {
                myApplicationLogger.error( "Is content in email xml formatted? " + exe.getMessage(),exe);
                myMessageLogger.info( "Email not sent : " + exe.getMessage() +  "\n"  + XMLHelper.getXML( document ));
            }
            catch (Exception e)
            {
                myApplicationLogger.debug("Caught exception: ", e);
                myMessageLogger.info( "Email not sent. Is it xml based? : " +  "\n"  + item.getMessage() );
            }

        }
    }
    
    public EmailQueue getEmailQueue()
    {
        return myEmailQueue;
    }
}
