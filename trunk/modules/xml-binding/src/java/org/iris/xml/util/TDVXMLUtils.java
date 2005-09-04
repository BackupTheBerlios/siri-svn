package org.iris.xml.util;

import java.io.*;

import org.apache.xml.serialize.*;
import org.w3c.dom.*;

public class TDVXMLUtils
{
    public TDVXMLUtils()
    {
    }

    public static void writeXMLFile(String name, Document doc) throws Exception
    {
        try
        {
            File outputFile = new File(name);
            FileOutputStream fos = new FileOutputStream(outputFile);
            OutputFormat format = new OutputFormat(doc);
            format.setIndent(3);
            XMLSerializer serial = new XMLSerializer(fos, format);
            serial.asDOMSerializer();
            serial.serialize(doc.getDocumentElement());
            fos.close();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    /*
        public static String readXMLFile()
        {
         try
           {
         javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
               factory.setNamespaceAware(true);
               javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
               FileInputStream filestream = new FileInputStream(testDir + validSampleXML);
               InputSource source = new InputSource(filestream);
               Document document = builder.parse(source);
               JAXBContext jc = JAXBContext.newInstance("org.iris.xml.email");
               // create an Unmarshaller
           }
        }
     */

}