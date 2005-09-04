package org.iris.server.messagehandlers.email;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.iris.server.messagehandlers.InstantEmailMessageHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Class handles
 * <p/>
 * User: gepo
 * Date: 2004-apr-26
 * Time: 14:02:11
 */
public class SMTPServerTester
{
    Logger myLogger = Logger.getLogger( SMTPServerTester.class.getName());
    private Session session;

    public static void main(String[] args)
    {
        new SMTPServerTester();
    }

    public SMTPServerTester()
    {

        try
        {
            sendEmail( );
        }
        catch (Exception e)
        {
            myLogger.debug("Caught exception: ", e);

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


    private void sendEmail() throws Exception
    {
        Properties props = null;
        props = getMailProperties("tdvmail1");
        //props = getMailProperties("tdvms01");
        session = Session.getInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        try
        {
            createMimeMessage(message);
            message.saveChanges(); // implicit with send()
            Transport transport = session.getTransport("smtp");
            myLogger.debug("Trying to send email using smtp server " + props.get( "mail.smtp.host" ));
            transport.send( message );
            transport.close();
            myLogger.info("Email sucessfully sent");
            return;
        }
        catch (Exception ex)
        {
            myLogger.error("Failed sending email ", ex);
        }

    }

    private void createMimeMessage(MimeMessage message)
            throws MessagingException
    {
        message.setContent("TEST from development ", "text/plain");
        message.setSubject("subject");
       // Address toAddress = new InternetAddress( "gpolyzois@descartes.com");
        Address toAddress2 = new InternetAddress( "georges.polyzois@tradevision.net");
       // Address toAddress3 = new InternetAddress( "gepo01@home.se");
       // message.addRecipient(Message.RecipientType.TO, toAddress);
        message.addRecipient(Message.RecipientType.TO, toAddress2);
       // message.addRecipient(Message.RecipientType.TO, toAddress3);

        Address fromAddress = new InternetAddress("gpolyzois@descartes.com");
        message.setFrom(fromAddress);
    }


    private Properties getMailProperties(String smtpServer)
            throws Exception
    {
        Properties contactProperties = new Properties();
        myLogger.debug( "Using Mailserver : " + smtpServer);
        contactProperties.put("mail.smtp.host", smtpServer );
        return contactProperties;
    }



}
