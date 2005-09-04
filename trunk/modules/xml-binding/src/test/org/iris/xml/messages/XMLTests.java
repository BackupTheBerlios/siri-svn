package org.iris.xml.messages;

import junit.framework.*;

import java.util.ResourceBundle;
import java.util.Enumeration;
import java.util.Properties;
import java.util.MissingResourceException;

import org.apache.log4j.PropertyConfigurator;

public abstract class XMLTests extends TestCase
{
    protected static String  testDir =   "C:\\gepo\\mycvsprojects\\iris\\modules\\xml-binding\\src\\test-data\\messages\\"; //C:/gepo/mycvsprojects/irisxml/xml/test/";
    protected static String validSampleXML = "valid_email.xml";
    protected static String inValidSampleXML = "invalid_email.xml";
    protected static String jaxbcontext = "org.iris.xml.messages.email.generated";

    public XMLTests(String name)
    {
        super(name);
    }


    static
        {
            ResourceBundle resourceBundle = null;
            try
            {
                resourceBundle = ResourceBundle.getBundle("log4j");
                Enumeration proplist = resourceBundle.getKeys();
                proplist = resourceBundle.getKeys();
                Properties props = new Properties();
                while (proplist.hasMoreElements())
                {
                    String tKey = (String) proplist.nextElement();
                    props.setProperty(tKey, resourceBundle.getString(tKey));
                }
                PropertyConfigurator.configure(props);
                System.out.println("Logging configured (log4j.properties)");
            }
            catch (MissingResourceException e)
            {
                System.out.println("Missing resource : log4j.properties");
            }

        }



}
