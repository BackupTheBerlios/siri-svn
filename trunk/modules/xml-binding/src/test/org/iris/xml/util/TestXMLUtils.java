package org.iris.xml.util;

import junit.framework.*;
import org.w3c.dom.*;

import java.io.FileInputStream;
import org.xml.sax.InputSource;
import org.iris.xml.util.TDVXMLUtils;
import org.iris.xml.messages.XMLTests;

public class TestXMLUtils
    extends XMLTests
{
    private TDVXMLUtils xMLUtils = null;

    public TestXMLUtils(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        xMLUtils = new TDVXMLUtils();
    }

    protected void tearDown() throws Exception
    {
        xMLUtils = null;
        super.tearDown();
    }

    public void testWriteXMLFile()
    {
        try
        {
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
         /*   builder.setEntityResolver( new EntityResolver(){
                public InputSource resolveEntity(String in, String in2)
                {
                    System.out.println(""+in);
                    System.out.println(""+in2);
                    return null;
                };
            } );
*/

            FileInputStream filestream = new FileInputStream(testDir + validSampleXML);
            InputSource source = new InputSource(filestream);
            Document document = builder.parse(source);
            xMLUtils.writeXMLFile( "./tmp/generated_name.xml", document);
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            fail("Parse error");


        }
    }

}
