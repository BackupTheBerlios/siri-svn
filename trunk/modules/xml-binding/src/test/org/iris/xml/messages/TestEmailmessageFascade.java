package org.iris.xml.messages;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.iris.xml.messages.email.generated.Emailmessage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TestEmailmessageFascade extends XMLTests
{
    private EmailmessageFascade emailmessageFascade = null;
    Logger myLogger = Logger.getLogger( TestEmailmessageFascade.class.getName());

    public TestEmailmessageFascade(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        emailmessageFascade = null;
    }

    protected void tearDown() throws Exception
    {
        emailmessageFascade = null;
        super.tearDown();
    }

    public void testUnmarshalInvalidXML()
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document document = null;
        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            FileInputStream filestream = new FileInputStream( testDir + inValidSampleXML );
            InputSource source = new InputSource(filestream);
            document = builder.parse(source);
            Emailmessage actualReturn = EmailmessageFascade.getInstance().getEmailmessage( document );
            assertNull(  actualReturn );
        }
        catch (Exception ex)
        {
            assertFalse( true );
            myLogger.debug( ex.getMessage(), ex );
        }
    }


    public void testUnmarshalValidXML()
    {
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document document = null;
        try
        {
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            FileInputStream filestream = new FileInputStream( testDir + validSampleXML );
            InputSource source = new InputSource(filestream);
            document = builder.parse(source);
            Emailmessage actualReturn = EmailmessageFascade.getInstance().getEmailmessage( document );
            assertNotNull(  actualReturn );
        }
        catch (Exception ex)
        {
            myLogger.debug( ex.getMessage(), ex );
        }
    }



/*
    public void testUnmarshalValidXMLString()
    {
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document document = null;
        String txtMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?><emailmessage id=\"email\" contenttype=\"text/html\" createdate=\"1999-05-31T13:20:00.000-05:00\" creator=\"pnlbooking\" smtpserver=\"tdvmail1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"C:\\gepo\\tradevision\\cvsrepos\\TDV_Messenger\\email.xsd\"><toaddress>test_to_address</toaddress> <ccaddress>test_cc_address</ccaddress>  <bccaddress>test_bcc_address</bccaddress> <subject>TESTSUBJECT </subject><bodymessage><![CDATA[HELLOTEST]]></bodymessage></emailmessage>";
        System.out.println("TextMessage \n" + txtMessage);
        InputSource source = new InputSource (new StringReader (txtMessage));
        //document = builder.parse( source );
        //Node firstchild = document.getFirstChild();
        //NamedNodeMap attributes = firstchild.getAttributes();
          //                  Node message = attributes.getNamedItem("id");
                            //System.out.println("In inqueue bean firstchild \n" + firstchild);
                            //System.out.println(" document " + document.r );
            //                Emailmessage test =  EmailmessageFascade.getInstance().getEmailmessage( document  );

        factory.setNamespaceAware(true);

        try
        {
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(source);
        }
        catch (Exception ex)
        {
            myLogger.debug( ex.getMessage() );
        }
        Emailmessage actualReturn = EmailmessageFascade.getInstance().getEmailmessage( document );
        assertNotNull(  actualReturn );
    }
*/

    static
    {
        System.out.println("===============================================================");
           ResourceBundle rb = null;
           try
           {
               rb = ResourceBundle.getBundle("log4j");
               Enumeration proplist = rb.getKeys();
               proplist = rb.getKeys();
               Properties props = new Properties();
               while (proplist.hasMoreElements())
               {
                   String tKey = (String)proplist.nextElement();
                   props.setProperty(tKey, rb.getString(tKey));
               }
               PropertyConfigurator.configure(props);
           }
           catch (MissingResourceException e)
           {
               e.printStackTrace();
           }

       }
}
