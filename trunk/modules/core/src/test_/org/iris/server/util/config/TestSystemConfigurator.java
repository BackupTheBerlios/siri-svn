package org.iris.server.util.config;

import java.io.*;

import org.apache.log4j.*;
import org.iris.server.util.filehelpers.*;
import org.xml.sax.*;
import junit.framework.*;

public class TestSystemConfigurator
    extends TestCase
{
    Logger myLogger = Logger.getLogger(TestSystemConfigurator.class.getName());

    public TestSystemConfigurator(String name)
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


    public void testDummy()
    {
    }
    /*
        public void testParseServiceFileToFile()
        {
            String filelocation = "C:/gepo/mycvsprojects/iris/src/test/xml/iris_filetofile.xml";
            try
            {
                String xml = FileCreateHelper.readFile(filelocation);
                SystemConfigurator.initUsingXml(xml);
                Service[] ser =   SystemConfigurator.getArrOfServices();
                myLogger.debug( ser[0] );
                assertEquals( ser.length, 1 );
            }
            catch (SAXException ex)
            {
                assertFalse( true );
            }
            catch (IOException ex)
            {
                assertFalse( "Could not find file or error reading file : "  + filelocation,true );
            }
        }
        public void testParseServiceFileToEJB()
        {
            String filelocation = "C:/gepo/mycvsprojects/iris/src/test/xml/iris_filetoejb.xml";
            try
            {
                String xml = FileCreateHelper.readFile(filelocation);
                SystemConfigurator.initUsingXml(xml);
                Service[] ser =   SystemConfigurator.getArrOfServices();
                myLogger.debug( ser[0] );
                assertEquals( ser.length, 1 );
            }
            catch (SAXException ex)
            {
                assertFalse( true );
            }
            catch (IOException ex)
            {
                assertFalse( "Could not find file or error reading file : "  + filelocation,true );
            }
        }*/

    /*
    public void testParseServicePollEJB()
    {
        String filelocation = "C:/gepo/mycvsprojects/iris/src/test/xml/iris_poll.xml";
        try
        {
            String xml = FileCreateHelper.readFile(filelocation);
            SystemConfigurator.initUsingXml(xml);
            Service[] ser = SystemConfigurator.getArrOfServices();
            myLogger.debug(ser[0]);

            assertEquals("Expected node size differs ", 1, ser.length);
            assertEquals("Expected node size differs ", null, ser[0].getInfolderIntervall());
            assertTrue("Service not instanceof ServicePollEJB", ser[0] instanceof ServicePollEJB);
            assertEquals("Expected node size differs ", null, ser[0].getInfolderIntervall());

        }
        catch (SAXException ex)
        {
            assertFalse(true);
        }
        catch (IOException ex)
        {
            assertFalse("Could not find file or error reading file : " + filelocation, true);
        }
    } */

}
