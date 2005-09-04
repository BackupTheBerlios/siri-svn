package org.iris.server.messagehandlers;

import java.io.*;

import org.apache.log4j.*;
import org.iris.server.messagehandlers.email.*;
import org.iris.server.util.xml.*;
import org.w3c.dom.*;

/**
 * 
 */
public abstract class MessageHandler
{
    public final static String EMAIL = "email";
    public final static String SMS = "sms";
    Logger myLogger = Logger.getLogger(MessageHandler.class.getName());

    abstract public void handleMessage(Document xmlDocument, EmailQueueItem mailQueueItem, Logger logger) throws MessageHandlerException;

    abstract public void handleMessage(InputStream instream, EmailQueueItem mailQueueItem, Logger logger) throws MessageHandlerException;

    protected static String backupDir;

    public void persistMessage(Document xmlDocument)
    {
        String tmpFilepath = "";
        Node firstchild = xmlDocument.getFirstChild();
        NamedNodeMap attributes = firstchild.getAttributes();
        Node messageName = attributes.getNamedItem("messagename");
        tmpFilepath = backupDir + messageName.getNodeValue() + "_" + System.currentTimeMillis() + "_" +
                      java.lang.Math.random() + ".xml";

        if (xmlDocument != null)
        {
            try
            {
                XMLHelper.writeXMLFile(tmpFilepath, xmlDocument);
            }
            catch (Exception ex1)
            {
                myLogger.error("Failed writing data to disk for backup", ex1);
            }
        }

    }

}