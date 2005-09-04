package org.iris.server.messagehandlers;

import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.*;
import junit.framework.*;

public class TestMailMessageHandler extends TestCase
{
    private String emailMsgStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?><emailmessage id=\"email\" " +
            "contenttype=\"text/html\" createdate=\"1999-05-31T13:20:00.000-05:00\" creator=\"pnlbooking\" " +
            "smtpserver=\"tdvmail1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:noNamespaceSchemaLocation=\"C:\\gepo\\tradevision\\cvsrepos\\TDV_Messenger\\email.xsd\"><toaddress>test_to_address" +
            "</toaddress> <ccaddress>test_cc_address</ccaddress>  <bccaddress>test_bcc_address</bccaddress> " +
            "<subject>TESTSUBJECT </subject><bodymessage><![CDATA[HELLOTEST]]></bodymessage></emailmessage>";

    public TestMailMessageHandler(String s)
    {
        super(s);
    }

    protected void setUp()
    {
    }

    protected void tearDown()
    {
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(TestMailMessageHandler.class);
    }


    public void testDummy()
    {
    
    }

    /*
    public void testHandleFileinputstream()
    {
        InstantEmailMessageHandler mailmessagehandler = InstantEmailMessageHandler.getInstance();

        try
        {
            FileInputStream filin = new FileInputStream("email.xml");
            mailmessagehandler.handleMessage(filin, null,null);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    } */

    /*
      public void testHandleXmlDocument() {
        MailMessageHandler mailmessagehandler = MailMessageHandler.getInstance();
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.
            DocumentBuilderFactory.newInstance();
        Document document;
        try {
          factory.setNamespaceAware(true);
          javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
          InputSource source = new InputSource(new FileInputStream("email.xml"));
          document = builder.parse(source);
          Node firstchild = document.getFirstChild();
          System.out.println("first " + firstchild);
          mailmessagehandler.handleMessage(document);
        }
        catch (org.xml.sax.SAXException sxe) {
          Exception x = sxe;
          if (sxe.getException() != null)
            x = sxe.getException();
          x.printStackTrace();
        }
        catch (javax.xml.parsers.ParserConfigurationException pce) {
          pce.printStackTrace();
        }
        catch (Exception pce) {
          pce.printStackTrace();
        }
      }
     */

    /*
    public void testCreateEmailFromString()
    {
        String txtMessage = emailMsgStr;
        javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        Document document;
        try
        {
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            System.out.println("TextMessage " + txtMessage);
            InputSource source = new InputSource(new StringReader(txtMessage));
            document = builder.parse(source);
            Node firstchild = document.getFirstChild();
            NamedNodeMap attributes = firstchild.getAttributes();
            Node message = attributes.getNamedItem("id");
            System.out.println("In inqueue bean first " + firstchild);
            System.out.println(" document " + document.toString());
            MessageHandlerFactory.createHandler(message.getNodeValue()).handleMessage(document, null,null);
        }
        catch (org.xml.sax.SAXException sxe)
        {
            Exception x = sxe;
            if (sxe.getException() != null)
            {
                x = sxe.getException();
            }
            x.printStackTrace();
        }
        catch (javax.xml.parsers.ParserConfigurationException pce)
        {
            pce.printStackTrace();
        }
        catch (Exception pce)
        {
            pce.printStackTrace();
        }

    }
    */

}