package org.iris.client.settings;

import java.io.*;
import java.util.*;

import org.apache.log4j.*;
import org.iris.client.settings.xml.*;
import junit.framework.*;

public class TestSettingsFascade
    extends TestCase
{
    static Logger myLogger = Logger.getLogger(TestSettingsFascade.class.getName());
    String testDir = "c:/gepo/mycvsprojects/irisclient/src/test/";
    String irisclientxmltest = "irisclienttest.xml";

    public TestSettingsFascade(String name)
    {
        super(name);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        myLogger.info("Using xml file for test : " + testDir + irisclientxmltest);
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

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

    /*
            public void testValidate()
            {
                javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.
                    DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                Document document = null;
                try
                {
                    Validator v = jc.createValidator();
                    boolean valid = v.validateRoot(po);
                    System.out.println(valid);
                }
                catch (ValidationException ue)
                {
                    System.out.println("Caught ValidationException");
                }
                catch (JAXBException je)
                {
                    je.printStackTrace();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }*/

    public void testgetIrisclientSettings()
    {
        Irisclient result = null;
        try
        {
            result = SettingsFascade.getInstance().getIrisclientSettings(new File(testDir + irisclientxmltest));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            assertTrue(false);
        }
        assertEquals("gollum", result.getVersioninfo().getDeveloper());
        assertEquals("Iris client", result.getTitle());
        assertEquals("127.0.0.1", ( (IrisserverType) result.getIrisserver().get(0)).getAddress());
    }

    public void testgetIrisclientSettingsAsString()
    {
        Irisclient irisclient = null;
        try
        {
            irisclient = SettingsFascade.getInstance().getIrisclientSettings(new File(testDir + irisclientxmltest));

            String result = SettingsFascade.getInstance().getIrisclientSettingsAsString();

            myLogger.debug("result" + result);
        }
        catch (Exception ex)
        {
            assertTrue(false);
        }
    }

    public void teststoreIrisclientSettings()
    {
        Irisclient result = null;
        try
        {
            result = SettingsFascade.getInstance().getIrisclientSettings(new File(testDir + irisclientxmltest));
            SettingsFascade.getInstance().storeIrisclientSettings(result, new File(testDir + "testfile.xml"));

        }
        catch (Exception ex)
        {
            assertTrue(false);
        }
    }

}
