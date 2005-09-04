package org.iris.xml.messages;

import org.apache.log4j.Logger;
import org.iris.xml.messages.email.generated.Emailmessage;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Used to validate operations on xml file of type email.xsd
 *
 */

public class TestEmailmessage
    extends XMLTests
{
    private Emailmessage emailmessage = null;
    Logger myLogger = Logger.getLogger(TestEmailmessage.class.getName());

    public TestEmailmessage(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(TestEmailmessage.class);
    }

    /**
     * Read valid xml file and unmarshal - if unmarshalling fails the this test fails.
     */
    public void testUnMarshalFromFileInputStream()
    {
        try
        {
            myLogger.info( "Unmarshalling from file");
            myLogger.info( "Using testDir + validSampleXML" + testDir + validSampleXML );
            JAXBContext jc = JAXBContext.newInstance(jaxbcontext);
            Unmarshaller u = jc.createUnmarshaller();
            Emailmessage po = (Emailmessage) u.unmarshal(new FileInputStream(testDir + validSampleXML));
            assertEquals(po.getBodymessage(), "HELLOTEST");
        }
        catch (JAXBException je)
        {
            myLogger.fatal( je );
			fail( "" );
        }
        catch (IOException ioe)
        {
			myLogger.fatal( ioe );
			fail("");
        }
    }

    /**
     * Read valid xml file with known xml elements and see if they are what we expect them to be.
     */
    public void testValidXMLDocument()
    {
        try
        {
            myLogger.info( "Unmarshalling from file");
            myLogger.info( "Using testDir + validSampleXML" + testDir + validSampleXML );
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            FileInputStream filestream = new FileInputStream(testDir + validSampleXML);
            InputSource source = new InputSource(filestream);
            Document document = builder.parse(source);
            JAXBContext jc = JAXBContext.newInstance(jaxbcontext);
            Unmarshaller u = jc.createUnmarshaller();
            Emailmessage po = (Emailmessage) u.unmarshal(document);
            assertEquals(po.getBodymessage(), "HELLOTEST");
        }
        catch (Exception exc)
        {
            assertTrue( false );
            myLogger.error( exc );
        }

    }

    /**
     * Read valid xml file with known xml elements and see if they are what we expect them to be.
     */
    public void testCreateEmailFromDcocument()
    {
        try
        {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();

            FileInputStream filestream = new FileInputStream(testDir + validSampleXML);
            InputSource source = new InputSource(filestream);
            Document document = builder.parse(source);

            Emailmessage msg = EmailmessageFascade.getInstance().getEmailmessage(document); // get getEmailmessage(document);

            assertEquals(msg.getCcaddress().get(0), "test_cc_address_1");
            assertEquals(msg.getBodymessage(), "HELLOTEST");

            Iterator iter = msg.getToaddress().iterator();
            assertEquals( iter.next(), "test_to_address_1");
            assertEquals( iter.next(), "test_to_address_2");

            iter = msg.getBccaddress().iterator();
            assertEquals( iter.next(), "test_bcc_address_1");
            assertEquals( iter.next(), "test_bcc_address_2");

            iter = msg.getCcaddress().iterator();
            assertEquals( iter.next(), "test_cc_address_1");
            assertEquals( iter.next(), "test_cc_address_2");

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}