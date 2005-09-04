package org.iris.server.messagehandlers;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.iris.server.errorhandler.ErrorMessageVO;
import org.iris.server.messagehandlers.email.EmailQueueItem;
import org.iris.server.util.config.ServerSystemConfigHandler;
import org.iris.server.util.filehelpers.FileCreateHelper;
import org.iris.server.util.xml.XMLHelper;
import org.iris.xml.messages.EmailmessageFascade;
import org.iris.xml.messages.email.generated.Emailmessage;
import org.iris.xml.server.settings.SmtpserverType;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * To send an email the InstantEmailMessageHandler class uses javamail. A xml Document or xml Inputstream can be used
 * to send emails.
 * InstantEmailMessageHandler also handles failover through a list of email server from configuration file - if failover
 * servers exist that is. Sif the message itself contains a mailserver it will use that mail server, if it fails using
 * this mail server it will gra b the next mail server from the general configuration of mail servers in iris.xml
 * configuration file.
 *
 * @author Georges Polyzois 
 */
public class InstantEmailMessageHandler
    extends MessageHandler
{
    Logger myLogger = Logger.getLogger(InstantEmailMessageHandler.class.getName());
    private static InstantEmailMessageHandler myMailMessageHandler;
    private javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    private Session session;
    private String[] smtpServers;

    public static InstantEmailMessageHandler getInstance()
    {
        if (myMailMessageHandler == null)
        {
            myMailMessageHandler = new InstantEmailMessageHandler();
        }
        return myMailMessageHandler;
    }

    public InstantEmailMessageHandler()
    {
        setUp();

    }

    private void setUp()
    {
        getEmailServers();

    }

    /**
     * Get email servers from the configuration and setup in an array. Backup servers are used for - that is right
     * backup servers for sending mails to backup server in case primary does not answer.
     */
    private void getEmailServers()
    {
        List lsSmtpServers =  ServerSystemConfigHandler.getInstance().getGlobalSettings().getEmailserversettings().getSmtp().getSmtpserver();
        Iterator iter = lsSmtpServers.iterator();
        smtpServers = new String[lsSmtpServers.size()];
        int i = 0;
        while (iter.hasNext())
        {
            SmtpserverType serverType =  (SmtpserverType) iter.next();
            smtpServers[ i ] = serverType.getValue();
            i++;
        }
    }


    static
    {
        ResourceBundle rb = null;
        try
        {
            rb = ResourceBundle.getBundle("log4j");
            Enumeration proplist = rb.getKeys();
            proplist = rb.getKeys();
            Properties props = new Properties();
            while (proplist.hasMoreElements())
            {
                String tKey = (String) proplist.nextElement();
                props.setProperty(tKey, rb.getString(tKey));
            }
            PropertyConfigurator.configure(props);
        }
        catch (MissingResourceException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Method handles the email docuemnt message by parsing it and calling sendmail.
     *
     * @param xmlDocument                   container for the email
     * @param mailQueueItem                          contains where to store error if sending fails
     * @throws MessageHandlerException      thrown if parsing fails
     */
    public void handleMessage(Document xmlDocument, EmailQueueItem mailQueueItem, Logger logger) throws MessageHandlerException
    {
        try
        {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            //javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            Emailmessage mailMessage = EmailmessageFascade.getInstance().getEmailmessage(xmlDocument);
            if (mailMessage != null)
            {
                List bccList = mailMessage.getBccaddress();
                List ccList = mailMessage.getCcaddress();
                List toList = mailMessage.getToaddress();
                String from = mailMessage.getSender();
                String body = mailMessage.getBodymessage();
                String subject = mailMessage.getSubject();
                String smtpServer = mailMessage.getSmtpserver();
                String content = mailMessage.getContenttype();
                sendEmail(toList, ccList, bccList, from, subject, body, smtpServer, content, logger);
            }
            else
            {
                throw new MessageHandlerException("Error unmarshalling xml structure", null);
            }
        }
        catch (Exception ex)
        {
            logger.fatal("Trying to send email failed", ex);
            try
            {
                // if not null then we should handle the error by storing in the error folder
                if ( mailQueueItem.getErrorFolder() != null )
                {
                    XMLHelper.writeXMLFile(mailQueueItem.getErrorFolder() + mailQueueItem.getMessageId(), xmlDocument);
                }
            }
            catch (Exception ex1)
            {
                logger.error("errorpath  " + mailQueueItem.getErrorFolder());
            }

            if ( mailQueueItem.getErrorInfoFolder()!=null )
            {
                // crate an info message on what went wrong when sending the email
                ErrorMessageVO error = new ErrorMessageVO(mailQueueItem.getErrorInfoFolder(), "Failed sending email message", ex,
                                   "ERROR", mailQueueItem.getMessageId());
                createErrorMessage(error);
            }

            throw new MessageHandlerException("Sending of email failed " ,ex);
        }

    }

    /**
     * Method handles the email inputstream message by parsing it and calling handlemessage with Document
     * instead.
     *
     * @param instream
     * @param mailQueueItem
     * @throws MessageHandlerException
     */
    public void handleMessage(InputStream instream, EmailQueueItem mailQueueItem, Logger logger) throws MessageHandlerException
    {

        Document document = null;
        try
        {
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource source = new InputSource(instream);
            document = builder.parse(source);
            this.handleMessage(document, mailQueueItem, logger);
        }
        catch (org.xml.sax.SAXException sxe)
        {
            throw new MessageHandlerException("Parse exception " , sxe);
        }
        catch (javax.xml.parsers.ParserConfigurationException sxe)
        {
            throw new MessageHandlerException("Parse exception " , sxe);
        }
        catch( MessageHandlerException ex )
        {
            throw ex;
        }
        catch (Exception sxe)
        {
            throw new MessageHandlerException("Parse exception or send exception " , sxe);
        }

    }

    /**
     * Method that takes the xmldocument and tries sending it using the smtp server in the xml message and then
     * atuomatically failovers to iris config smtp servers.
     * Throws an exception when it has no more smtp servers to use. Caller needs to handle exception an backup up
     * the message if needed.
     *
     * @param toList        mainrecipients, to address
     * @param ccList        cc recipients
     * @param bccList       bcc recipients
     * @param from          from address
     * @param subject       the subjec
     * @param body          the actual message
     * @param smtpServer    send to smtp server from message head
     * @param content       plain/text or text/html
     * @param serviceLogger the apllication logger for the service calling this method - if none then set to null
     */
    private void sendEmail(List toList, List ccList, List bccList, String from, String subject,
                           String body, String smtpServer, String content, Logger serviceLogger) throws Exception
    {
        Properties props = null;
        for (int retryCounter = 0; retryCounter <= smtpServers.length ; retryCounter++)
        {
            props = getSMTPServerProperties(smtpServer, retryCounter);
            session = Session.getInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            try
            {
                createMimeMessage(message, body, content, subject, toList, ccList, bccList, from);
                message.saveChanges(); // implicit with send()
                Transport transport = session.getTransport("smtp");
            //    myLogger.debug("Trying to send email using smtp server " + props.get( "mail.smtp.host" ));
                transport.send( message );
                transport.close();
        //        myLogger.info("Email sucessfully sent - subject " + subject);
                if (serviceLogger != null )
                {
                    serviceLogger.info( "Email sucessfully sent - subject " + subject );

                }
                return;
            }
            catch (Exception ex)
            {
                if ( retryCounter >= (smtpServers.length ) )
                {
                    serviceLogger.error("Failed sending email - retry over no more smtp servers configured" , ex);
                    throw ex;
                }
                else
                {
                    serviceLogger.error("Failed sending email - retrying with other smtp server" , ex);
                }
            }
        }
    }

    private void createMimeMessage(MimeMessage message, String body, String content, String subject, List toList, List ccList, List bccList, String from)
            throws MessagingException
    {
        message.setContent(body, content);
        message.setSubject(subject);
        Address toAddress;
        for (Iterator iter = toList.iterator(); iter.hasNext(); )
        {
            toAddress = new InternetAddress( (String) iter.next());
            message.addRecipient(Message.RecipientType.TO, toAddress);
        }

        Address ccAddress;
        if (ccList != null && ccList.size() > 0)
        {
            for (Iterator iter = ccList.iterator(); iter.hasNext(); )
            {
                ccAddress = new InternetAddress( (String) iter.next());
                message.addRecipient(Message.RecipientType.CC, ccAddress);
            }
        }

        Address bccAddress;
        if (ccList != null && ccList.size() > 0)
        {
            for (Iterator iter = bccList.iterator(); iter.hasNext(); )
            {
                bccAddress = new InternetAddress( (String) iter.next());
                message.addRecipient(Message.RecipientType.BCC, bccAddress);
            }
        }
        Address fromAddress = new InternetAddress(from);
        message.setFrom(fromAddress);
    }

    /**
     * Set javax mail property mail.smtp.host
     *
     * @param smtpServer            Smtp server specified in message header overrides the configuration server
     *                              in iris.xml. So if we have <emailmessage contenttype="text/html" smtpserver="127.0.0.1" ..... />
     *                              this smtp server will first be used and then we will fallback to other servers configured
     *                              in the config file.
     * @param retryCounter
     * @return
     * @throws Exception
     */
    private Properties getSMTPServerProperties(String smtpServer, int retryCounter)
            throws Exception
    {
        Properties result = new Properties();
        String useSMTPServer = null;
        // First try using message smtp server
        if ( retryCounter == 0 && smtpServer != null && !smtpServer.equalsIgnoreCase( "" ))
        {
            useSMTPServer = smtpServer;
        }
        else if ( retryCounter > 0 && smtpServers[retryCounter-1] != null )
        {
            // From retry number 2 and onward use config servers
            useSMTPServer = smtpServers[retryCounter-1 ];
        }
        result.put("mail.smtp.host", useSMTPServer );
        return result;
    }

    /**
     * Stores informatoin on what went wrong with sending of the email.
     *
     * @param error
     */
    protected void createErrorMessage(ErrorMessageVO error)
    {
        try
        {
            FileCreateHelper.createFile(error.getErrorMessage(),
                error.getErrorFolder()  + error.getIdErrorMessage());
        }
        catch (IOException ex1)
        {
            myLogger.error("Failed creating error info in error info folder : " + error.getErrorFolder() +
                error.getIdErrorMessage());
        }
    }

}